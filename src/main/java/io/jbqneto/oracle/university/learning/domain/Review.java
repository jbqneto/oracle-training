package io.jbqneto.oracle.university.learning.domain;

public class Review implements Comparable<Review> {
    private Rating rating;
    private String comment;

    public Review(Rating rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Rating getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public int compareTo(Review r) {
        return r.getRating().ordinal() - this.getRating().ordinal();
    }
}
