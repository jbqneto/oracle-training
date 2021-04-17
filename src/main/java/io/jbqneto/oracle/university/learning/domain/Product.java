package io.jbqneto.oracle.university.learning.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static java.math.RoundingMode.HALF_UP;

public abstract class Product implements Rateable<Product>{

    public static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.1);

    private final int id;
    private final String name;
    private final BigDecimal price;
    private final Rating rating;

    Product(int id, String name, BigDecimal price, Rating rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    Product(int id, String name, BigDecimal price) {
        this(id, name, price, Rating.NOT_RATED);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getBestBefore() {
        return LocalDate.now();
    }

    public BigDecimal getDiscount() {
        return this.price.multiply(DISCOUNT_RATE).setScale(2, HALF_UP);
    }

    @Override
    public Rating getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discount=" + this.getDiscount() +
                ", rating=" + this.getRating() +
                ", bestBefore=" + this.getBestBefore() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof Product) {
            return this.id == ((Product) o).getId();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, rating);
    }
}
