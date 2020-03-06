package db;

import java.sql.*;
import java.util.*;

public class Review extends ActiveDomainObject {
    private int film_id;
    private int pid;
    private int rating;
    private String comment;

    public Review (int pid, int film_id, int rating, String comment) {
        this.pid = pid;
        this.film_id = film_id;
        this.rating = rating;
        this.comment = comment;
    }
    public int getFilmId () {
        return this.film_id;
    }
    public int getPid () {
        return this.pid;
    }
    public int getRating () {
        return this.rating;
    }
    public String getComment(){
        return this.comment;
    }

    @Override
    public void initialize(Connection conn) {
        // TODO Auto-generated method stub

    }

    @Override
    public void refresh(Connection conn) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(Connection conn) {
        // TODO Auto-generated method stub

    }
    
    


}