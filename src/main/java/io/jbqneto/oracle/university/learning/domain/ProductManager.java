package io.jbqneto.oracle.university.learning.domain;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

import static io.jbqneto.oracle.university.learning.domain.Rating.NOT_RATED;

public class ProductManager {

    private ResourceFormatter formatter;
    private Map<Product, List<Review>> products = new HashMap<>();

    private static Map<String, ResourceFormatter> formatters = Map.of(
            "en-US", new ResourceFormatter(Locale.US),
            "en-GB", new ResourceFormatter(Locale.UK),
            "pt-BR", new ResourceFormatter(new Locale("pt-BR"))
    );

    public ProductManager(Locale locale) {
        this(locale.toLanguageTag());
    }

    public ProductManager(String language) {
        changeLocale(language);
    }

    public void changeLocale(String language) {
        formatter = formatters.getOrDefault(language, formatters.get("en-US"));
    }

    public Set<String> getSuportedLocales() {
        return formatters.keySet();
    }

    public Product createProduct(int id, String name, BigDecimal price, LocalDate bestBefore) {
        Product product = new Food(id, name, price, NOT_RATED, bestBefore);
        products.putIfAbsent(product, new ArrayList<>());

        return product;
    }

    public Product createProduct(int id, String name, BigDecimal price) {
        Product product = new Drink(id, name, price, NOT_RATED);
        products.putIfAbsent(product, new ArrayList<>());

        return product;
    }

    public Product reviewProduct(int id, Rating rating, String comments) {
        Product product = findById(id);
        if (product != null) {
            return reviewProduct(product, rating, comments);
        }

        return null;
    }

    private Product reviewProduct(Product product, Rating rating, String comments) {

        List<Review> reviews = products.get(product);

        products.remove(product, reviews);

        reviews.add(new Review(rating, comments));
        int sum = 0;

        for (Review review: reviews) {
            sum += review.getRating().ordinal();
        }

        product = product.applyRating( Math.round((float) sum / reviews.size()));

        products.put(product, reviews);

        return product;
    }

    public Product findById(int id) {
        Product result = null;
        for (Product product: products.keySet()) {
            if (product.getId() == id) {
                result = product;
                break;
            }
        }

        return result;
    }

    public void printProductReport(int id) {
        Product product = findById(id);

        if (product != null)
            printProductReport(findById(id));
    }

    private void printProductReport(Product product) {
        StringBuilder sb = new StringBuilder();
        List<Review> reviews = products.get(product);

        sb.append(formatter.formatProduct(product));
        sb.append("\n\t");

        if (!reviews.isEmpty()) {
            Collections.sort(reviews);

            reviews.forEach((review) -> {
                sb.append(formatter.formatReview(review));
            });
        } else {
            sb.append(formatter.getText("no.reviews"));
        }

        System.out.println(sb.toString());
    }

    public void printProductReport() {
        products.keySet().forEach((product) -> printProductReport(product));
    }

    public void printProductReport(Comparator<Product> sorter) {
        List<Product> productList = new ArrayList<>(this.products.keySet());
        productList.sort(sorter);
        productList.forEach(this::printProductReport);
    }

    private static class ResourceFormatter {

        private ResourceBundle resources;
        private Locale locale;
        private DateTimeFormatter dateFormatter;
        private NumberFormat moneyFormat;

        private ResourceFormatter(Locale locale) {
            this.locale = locale;
            this.resources = ResourceBundle.getBundle("resource", locale);
            this.moneyFormat = NumberFormat.getCurrencyInstance(locale);
            this.dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                    .localizedBy(locale);
        }

        private String formatReview(Review review) {
            return MessageFormat.format(
                    resources.getString("review"),
                    review.getRating(),
                    review.getComment()
            );
        }

        private String formatProduct(Product product) {
            return MessageFormat.format(
                    resources.getString("product"),
                    product.getName(),
                    moneyFormat.format(product.getPrice()),
                    product.getRating(),
                    dateFormatter.format(product.getBestBefore())
            );
        }

        private String getText(String key) {
            return resources.getString(key);
        }

    }
}
