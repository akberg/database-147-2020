package db;

/**
 * Film review
 */
public class Review {
    private Film film;
    private User user;
    private int rating;
    private String comment;

    public Review (Film film, User user, int rating, String comment) {
        this.film = film;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
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
    public String getComment(){
        return this.comment;
    }
}