package db;

/**
 * Film review
 */
public class Review {
    private Film film;
    private User user;
    private int rating;
    private String review_text;

    public Review (Film film, User user, int rating, String comment) {
        this.film = film;
        this.user = user;
        this.rating = rating;
        this.review_text = review_text;
    }
    public Film getFilm() {
        return this.film;
    }
    public User getUser() {
        return this.user;
    }
    public int getRating() {
        return this.rating;
    }
    public String getReviewText(){
        return this.review_text;
    }

    @Override
    public String toString() {
        return user.getUsername() + " ga " + rating + " poeng: \"" + comment + "\"";
    }
}