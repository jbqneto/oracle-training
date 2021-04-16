package io.jbqneto.oracle.university.learning.domain;

public enum Rating {

    NOT_RATED(""),
    ONE_STAR(""),
    TWO_STARS(""),
    THREE_STARS(""),
    FOUR_STARS(""),
    FIVE_STARS("");

    private String stars;

    private Rating(String stars) {
        this.stars = stars;
    }
}
