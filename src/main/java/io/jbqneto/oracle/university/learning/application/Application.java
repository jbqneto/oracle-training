package io.jbqneto.oracle.university.learning.application;

import io.jbqneto.oracle.university.learning.domain.Product;
import io.jbqneto.oracle.university.learning.domain.ProductManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Locale;

import static io.jbqneto.oracle.university.learning.domain.Rating.THREE_STARS;
import static io.jbqneto.oracle.university.learning.domain.Rating.TWO_STARS;

public class Application {
    public static void main(String[] args) {

        Locale localeUS = Locale.US;

        ProductManager pm = new ProductManager(localeUS);

        pm.createProduct(1, "Tea", BigDecimal.valueOf(1.99));
        pm.createProduct(2, "Cake", BigDecimal.valueOf(2.50), LocalDate.now());

        pm.createProduct(3, "Coffee", BigDecimal.valueOf(1.25));
        pm.createProduct(4, "Cheese", BigDecimal.valueOf(3.00), LocalDate.now());

        pm.reviewProduct(3, THREE_STARS, "Nice Coffee");
        pm.reviewProduct(1, TWO_STARS, "Could be a better tea, but i don like tea anyway.");

        pm.printProductsReport();

        pm.changeLocale(Locale.UK.toLanguageTag());

        System.out.println("\n --> All prods sorted: \n\t");

        Comparator<Product> ratingSorter = (p1, p2) -> p1.getRating().ordinal() - p2.getRating().ordinal();

        /*
        pm.printProductsReport(ratingSorter);

        System.out.println("\n --> All prods by price: \n\t");
        pm.printProductsReport(Comparator.comparing(Product::getPrice).reversed());

        System.out.println("\n --> All prods by rating and price: \n\t");
        */

        pm.getDiscounts().forEach((key, value) -> System.out.print(key + " = " + value));

    }
}
