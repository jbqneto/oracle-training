package io.jbqneto.oracle.university.learning.domain;

public enum Rating {

    NOT_RATED("rating-not-rated"),
    ONE_STAR("rating-one-star"),
    TWO_STARS("rating-two-stars"),
    THREE_STARS("rating-three-stars"),
    FOUR_STARS("rating-four-stars"),
    FIVE_STARS("rating-five-stars");

    private String stars;

    public String getStars() {
        return this.stars;
    }

    private Rating(String stars) {
        this.stars = stars;
    }
}
