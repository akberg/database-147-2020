
package db;

import java.sql.*;
import java.util.*;

public class Film extends ActiveDomainObject{
    private int film_id;
    private int series_id;
    private String title;
    private int pub_year;
    private String pub_date;
    private String storyline;
    private int runlength;

    public Film (int film_id) {
        this.film_id = film_id;
    }
    public int getFilmID () {
        return this.film_id;
    }
    
    public void refresh (Connection conn) {
        initialize (conn);
    }

    @Override
    public void initialize(Connection conn) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(Connection conn) {
        // TODO Auto-generated method stub

    }


}