package io.jbqneto.oracle.university.learning.application;

import io.jbqneto.oracle.university.learning.domain.Food;
import io.jbqneto.oracle.university.learning.domain.Product;
import io.jbqneto.oracle.university.learning.domain.ProductManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.jbqneto.oracle.university.learning.domain.Rating.*;

public class Application {
    public static void main(String[] args) {

        ProductManager pm = new ProductManager();

        Product tea = pm.createProduct(1, "Tea", BigDecimal.valueOf(1.99), NOT_RATED);
        Product cake = pm.createProduct(2, "Cake", BigDecimal.valueOf(2.50), FOUR_STARS, LocalDate.now());
        Product coffee = pm.createProduct(3, "Coffee", BigDecimal.valueOf(1.25), NOT_RATED);
        Product cheese = pm.createProduct(4, "Cheese", BigDecimal.valueOf(3.00), TWO_STARS, LocalDate.now());

        Product clone = coffee;
        Product cheeseCopy = pm.createProduct(4, "Cheese", BigDecimal.valueOf(3.00), TWO_STARS, LocalDate.now());

        coffee = coffee.applyRating(FIVE_STARS);

        Food betterCake = (Food) cake.applyRating(FIVE_STARS);

        List<Product> productList = List.of(tea, cake, coffee, cheese);

        System.out.println(productList);
        System.out.println("is coffee clone equals? (after applyRating) " + clone.equals(coffee));
        System.out.println("Is cheese Copy equals ? " + cheeseCopy.equals(cheese));
        System.out.println("Is Cake equals to better cake ? " + betterCake.equals(cake));
    }
}
