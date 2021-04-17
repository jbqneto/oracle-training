package io.jbqneto.oracle.university.learning.domain;

@FunctionalInterface
public interface Rateable <T> {

    public static final Rating DEFAULT_RATING = Rating.NOT_RATED;

    T applyRating(Rating rating);

    public default  T applyRating(int stars) {
        return applyRating(convert(stars));
    }

    public default Rating getRating() {
        return DEFAULT_RATING;
    }

    public static Rating convert(int stars) {
        if (stars > -1 && stars < Rating.values().length)
            return Rating.values()[stars];
        else
            throw new IllegalArgumentException("Stars size is lower then passed.");
    }
}
