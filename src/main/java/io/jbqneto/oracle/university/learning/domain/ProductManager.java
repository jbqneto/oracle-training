package io.jbqneto.oracle.university.learning.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class ProductManager {
    private static Map<Long, Product> productList;

    public Product createProduct(int id, String name, BigDecimal price,
                                 Rating rating, LocalDate bestBefore) {
        return new Food(id, name, price, rating, bestBefore);
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
        return new Drink(id, name, price, rating);
    }
}
