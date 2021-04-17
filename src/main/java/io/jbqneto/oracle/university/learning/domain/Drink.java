package io.jbqneto.oracle.university.learning.domain;

import java.math.BigDecimal;
import java.time.LocalTime;

public final class Drink extends Product {

    Drink(int id, String name, BigDecimal price, Rating rating) {
        super(id, name, price, rating);
    }

    @Override
    public BigDecimal getDiscount() {
        LocalTime now = LocalTime.now();
        boolean isHappyHourTime = now.isAfter(LocalTime.of(16, 30))
                && now.isBefore(LocalTime.of(20, 0));

        return (isHappyHourTime ? super.getDiscount() : BigDecimal.ZERO);
    }

    @Override
    public Product applyRating(Rating rating) {
        return new Drink(getId(), getName(), getPrice(), rating);
    }

    @Override
    public String toString() {
        return "Drink" + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o))
            return false;

        if (!getClass().equals(o.getClass()))
            return false;

        return true;
    }
}