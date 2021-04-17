package io.jbqneto.oracle.university.learning.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public final class Food extends Product {

    private LocalDate bestBefore;

    Food(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        super(id, name, price, rating);
        this.bestBefore = bestBefore;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    @Override
    public BigDecimal getDiscount() {
        if (bestBefore.isEqual(LocalDate.now()))
            return super.getDiscount();

        return BigDecimal.ZERO;
    }

    @Override
    public Product applyRating(Rating rating) {
        return new Food(getId(), getName(), getPrice(), rating, getBestBefore());
    }

    @Override
    public String toString() {
        return "Food" + super.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bestBefore);
    }
}
