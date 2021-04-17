package io.jbqneto.oracle.university.learning.domain;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

        double avg = reviews.stream()
                .mapToInt(review -> review.getRating().ordinal())
                .average()
                .orElse(0);

        product = product.applyRating(Math.round((float) avg));

        products.put(product, reviews);

        return product;
    }

    public Product findById(int id) {
        return products.keySet().stream()
                .filter(product -> product.getId() == id)
                .findFirst().orElse(null);
    }

    public void printProductsReport(int id) {
        Product product = findById(id);

        if (product != null)
            printProductsReport(findById(id));
    }

    private void printProductsReport(Product product) {
        StringBuilder sb = new StringBuilder();
        List<Review> reviews = products.get(product);

        sb.append(formatter.formatProduct(product));

        if (!reviews.isEmpty()) {
            Collections.sort(reviews);

            sb.append(reviews.stream()
                    .map(r -> formatter.formatReview(r) + "\n")
                    .collect(Collectors.joining()));
        } else {
            sb.append(formatter.getText("no.reviews"));
        }

        System.out.println(sb.toString());
    }

    public Map<String, String> getDiscounts() {
        return products.keySet().stream()
                .collect(Collectors.groupingBy(
                        product -> product.getRating().getStars(),
                        Collectors.collectingAndThen(
                                Collectors.summingDouble(prod -> prod.getDiscount().doubleValue()),
                                discount -> formatter.moneyFormat.format(discount)
                        )
                    )
                );
    }

    public void printProductsReport() {
        products.keySet().forEach((product) -> printProductsReport(product));
    }

    public void printProductsReport(Predicate<Product> filter, Comparator<Product> sorter) {
        StringBuilder sb = new StringBuilder();

        products.keySet().stream()
                .filter(filter)
                .sorted(sorter)
                .forEach(p -> sb.append(formatter.formatProduct(p) + "\n"));

        System.out.println(sb);
    }

    private static class ResourceFormatter {

        private ResourceBundle resources;
        private DateTimeFormatter dateFormatter;
        private NumberFormat moneyFormat;

        private ResourceFormatter(Locale locale) {
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
