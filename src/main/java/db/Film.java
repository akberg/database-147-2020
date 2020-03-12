package db;

import java.sql.*;
import java.util.*;
import java.sql.Date;

/**
 * Film objects represents a feature length movie or an episode
 * Attributes that only the full series would have are managed
 * in Series object
 */
public class Film extends ActiveDomainObject{
    private int series_id;
    private int pub_year;
    private Date pub_date;
    private String storyline;
    private int runlength;

    // Series specific variables
    private boolean isEpisode;
    private String title;
    private int season;
    private int episode;

    // Constructors

    public Film(int id, int series_id, String title, int pub_year, Date pub_date, String storyline, int runlength, boolean isEpisode, int season, int episode) {
        if (series_id == -1) {
            throw new IllegalStateException("Serie må være lagret i databasen.");
        } 
        this.ID = id;
        this.series_id = series_id;
        this.title = title;
        this.pub_year = pub_year;
        this.pub_date = pub_date;
        this.storyline = storyline;
        this.runlength = runlength;

        this.isEpisode = isEpisode;
        this.season = season;
        this.episode = episode;
    }

    public Film(int series_id, String title, int pub_year, Date pub_date, String storyline, int runlength, boolean isEpisode, int season, int episode) {
        this(-1, series_id, title, pub_year, pub_date, storyline, runlength, isEpisode, season, episode);
    }

    // Movie
    public Film(String title, int pub_year, Date pub_date, String storyline, int runlength, int comp_id) {
        Series s = new Series(comp_id, title);
        s.save();
        this.ID = -1;
        this.series_id = s.getID();
        this.title = title;
        this.pub_year = pub_year;
        this.pub_date = pub_date;
        this.storyline = storyline;
        this.runlength = runlength;
        
        this.isEpisode = false;
        this.season = 0;
        this.episode = 0;
    }

    // New episode
    public Film(String title, int series_id, int season, int episode, int pub_year, Date pub_date, String storyline, int runlength) {
        this(-1, series_id, title, pub_year, pub_date, storyline, runlength, true, season, episode);
    }

    // public Film(int id, Series s, String title, int pub_year, Date pub_date, String storyline, int runlength) {
    //     this(s.getID(), title, pub_year, pub_date, storyline, runlength);
    // }
    
    // public Film(Series s, String title, int pub_year, Date pub_date, String storyline, int runlength) {
    //     this(-1, s.getID(), title, pub_year, pub_date, storyline, runlength);
    // }

    public Film(int id) {
        this.ID = id;
    }

    public Film() {
        this.ID = -1;
    }
    
    public int getSeries_id() {
        return series_id;
    }

    /**
     * Get Series object assosiated with Film
     * @return Series
     */
    public Series getSeries() {
        return Series.get("series_id=" + this.series_id);
    }

    public void setSeries_id(int series_id) {
        this.series_id = series_id;
    }

    public void setSeries(Series s) {
        this.series_id = s.getID();
    }

    public String getTitle() {
        if (isEpisode) {
            return this.title;
        } else {
            return this.getSeries().getTitle();
        }
    }

    public void setTitle(String title) {
        if (isEpisode) {
            this.title = title;
        } else {
            Series s = this.getSeries();
            s.setTitle(title);
            s.save();
        }

    }

    public boolean isEpisode() {
        return isEpisode;
    }

    public int getPub_year() {
        return pub_year;
    }

    public void setPub_year(int pub_year) {
        this.pub_year = pub_year;
    }

    public Date getPub_date() {
        return pub_date;
    }

    public void setPub_date(Date pub_date) {
        this.pub_date = pub_date;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public int getRunlength() {
        return runlength;
    }

    public void setRunlength(int runlength) {
        this.runlength = runlength;
    }

    public int getEpisode() {
        return episode;
    }

    public int getSeason() {
        return season;
    }

    /**
     * Get all actors and roles registered on this film
     * @return
     */
    public List<Role> getActors() {
        List<Role> res = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from RoleBy where film_id=" + this.ID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Person p = Person.get("person_id=" + rs.getInt("person_id"));
                res.add(
                    new Role(this, p, rs.getString("role_name"))
                );
            }
        } catch (SQLException e) {
            System.out.println("Feil under databaseoperasjon: " + e);
        }
        return res;
    }

    private List<Person> getDirWri(String table) {
        List<Person> res = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("select * from " + table + " where film_id=" + this.ID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Person p = Person.get("person_id=" + rs.getInt("person_id"));
                res.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Feil under databaseoperasjon: " + e);
        }

        return res;
    }
    public List<Person> getDirectors() {
        return this.getDirWri("DirectedBy");
    }
    public List<Person> getWriters() {
        return this.getDirWri("WrittenBy");
    }

    /**
     * Add an Actor relation between Person p and Film
     * @param p Person
     * @param role Character played by p in this film
     */
    public void addActor(Person p, String role) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into RoleBy (person_id, film_id, role_name) values (?, ?, ?)");
            stmt.setInt(1, p.getID());
            stmt.setInt(2, this.ID);
            stmt.setString(3, role);
            stmt.execute();
        } catch(SQLException e) {
            System.out.println("Feil under databaseoperasjon: " + e);
        }
    }
    /**
     * Add an Actor relation between Person p and Film
     * @param r Role representing Person p and the character played by p
     */
    public void addActor(Role r) {
        addActor(r.getActor(), r.getRole());
    }

    /**
     * Add a Director relation between Person p and Film
     * @param p Person
     */
    public void addDirector(Person p) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into DirectedBy (person_id, film_id) values (?, ?)");
            stmt.setInt(1, p.getID());
            stmt.setInt(2, this.ID);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Feil under databaseoperasjon: " + e);
        }
    }

    /**
     * Add a Writer relation between Person p and Film
     * @param p
     */
    public void addWriter(Person p) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into Written (person_id, film_id) values (?, ?)");
            stmt.setInt(1, p.getID());
            stmt.setInt(2, this.ID);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Feil under databaseoperasjon: " + e);
        }
    }

    /**
     * Get all reviews of a Film
     * @return
     */
    public List<Review> getReviews() {
        List<Review> res = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from Review where film_id=" + this.ID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                res.add(
                    new Review(
                        Film.get("film_id="+rs.getInt("film_id")),
                        User.get("user_id="+rs.getInt("user_id")),
                        rs.getInt("rating"),
                        rs.getString("review_text")
                    )
                );
            }
        } catch (SQLException e) {
            System.out.println("Feil under databaseoperasjon: " + e);
        }
        return res;
    }

    public void addReview(User user, int rating, String comment) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into Review (film_id, user_id, rating, review_text) values (?, ?, ?, ?)");
            stmt.setInt(1, this.ID);
            stmt.setInt(2, user.getID());
            stmt.setInt(3, rating);
            stmt.setString(4, comment);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Feil under databaseoperasjon: " + e);
        }
    }

    /**
     * Get a Film object matching the given constraints
     * @param constraint an expression e.g. "film_id=1"
     * @return object of type Film
     * @throws NoSuchElementException if there were no or mutliple matches
     * @throws SQLException database error
     */
    public static Film get(String constraint) throws NoSuchElementException, SQLException {
        Film res;
        try {
            Statement stmt = conn.createStatement();
            // Select all from an object satisfying contstraints
            ResultSet rs = stmt.executeQuery("select * from Film where " + constraint);

            if (!rs.next()) {
                throw new NoSuchElementException("Ingen treff.");
            }
            int season = rs.getInt("season");
            res = new Film(
                rs.getInt("film_id"),
                rs.getInt("series_id"),
                rs.getString("title"),
                rs.getInt("pub_year"),
                rs.getDate("pub_date"),
                rs.getString("storyline"),
                rs.getInt("runlength"),
                season != 0,
                season,
                rs.getInt("episode")
            );

            if (rs.next()) {
                throw new NoSuchElementException("Mer enn ett treff!");
            }

            return res;

        } catch (SQLException e) {
            System.out.println("Feil under databaseoperasjon: "+e);
            return null;
        }
    }

    @Override
    public void save() {
        try {
            PreparedStatement stmt;
            if (ID == -1) {
                stmt = conn.prepareStatement(
                    "insert into Film " +
                    "(series_id, title, pub_year, pub_date, storyline, runlength, season, episode) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
                );
            } else {
                // Only update if object already exists in db
                stmt = conn.prepareStatement(
                    "update Film set " + 
                    "series_id=?, title=?, pub_year=?, pub_date=?, storyline=?, runlength=?, season=?, episode=? " +
                    "where film_id=" + this.ID
                );
            }
            stmt.setInt(1, this.series_id);
            stmt.setString(2, this.title);
            stmt.setInt(3, this.pub_year);
            stmt.setDate(4, this.pub_date);
            stmt.setString(5, this.storyline);
            stmt.setInt(6, this.runlength);
            stmt.setInt(7, this.season);
            stmt.setInt(8, this.episode);
            stmt.executeUpdate();
            conn.commit();

            if (ID == -1) {
                // Update with autogenerated pk
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                this.ID = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("db error during update of bruker="+e);
        }
    }
}