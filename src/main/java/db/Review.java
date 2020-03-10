package db;

import java.sql.*;
import java.util.*;

public class Review extends ActiveDomainObject {
    private int film_id;
    private int user_id;
    private int rating;
    private String review_text;

    public Review (int film_id, int user_id, int rating, String review_text) {
        this.film_id = film_id;
        this.user_id = user_id;
        this.rating = rating;
        this.review_text = review_text;
    }
    public int getFilmId () {
        return this.film_id;
    }
    public int getUserId () {
        return this.user_id;
    }
    public int getRating () {
        return this.rating;
    }
    public String getReviewText(){
        return this.review_text;
    }

    @Override
    public void initialize(Connection conn) {
        // TODO Auto-generated method stub

    }

    @Override
    public void refresh(Connection conn) {
        // TODO Auto-generated method stub
        initialize (conn);

    }

    @Override
    public void save(Connection conn) {
        // TODO Auto-generated method stub

    }
    
    


}