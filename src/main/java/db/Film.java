
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
    private int season;
    private int episode;

    public Film(int film_id, int series_id, String title, int pub_year, String pub_date, String storyline,
            int runlength, int season, int episode) {
        this.film_id = film_id;
        this.series_id = series_id;
        this.title = title;
        this.pub_year = pub_year;
        this.pub_date = pub_date;
        this.storyline = storyline;
        this.runlength = runlength;
        this.season = season;
        this.episode = episode;
    }

    public int getFilm_id() {
        return film_id;
    }



    public int getSeries_id() {
        return series_id;
    }



    public String getTitle() {
        return title;
    }


    public int getPub_year() {
        return pub_year;
    }



    public String getPub_date() {
        return pub_date;
    }



    public String getStoryline() {
        return storyline;
    }



    public int getRunlength() {
        return runlength;
    }

 
    public int getSeason() {
        return season;
    }



    public int getEpisode() {
        return episode;
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