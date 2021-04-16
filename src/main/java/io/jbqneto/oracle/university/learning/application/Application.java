package io.jbqneto.oracle.university.learning.application;

import io.jbqneto.oracle.university.learning.domain.Product;
import io.jbqneto.oracle.university.learning.domain.Rating;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static io.jbqneto.oracle.university.learning.domain.Rating.FIVE_STARS;
import static io.jbqneto.oracle.university.learning.domain.Rating.FOUR_STARS;

public class Application {
    public static void main(String[] args) {

        Product tea = new Product(1, "Tea", BigDecimal.valueOf(1.99));
        Product cake = new Product(2, "Cake", BigDecimal.valueOf(2.50), FOUR_STARS);
        Product coffee = new Product(3, "Coffee", BigDecimal.valueOf(1.25));
        Product empty = new Product();

        coffee = coffee.applyRating(FIVE_STARS);

        List<Product> productList = List.of(tea, cake, coffee, empty);

        System.out.print(productList);
    }
}
