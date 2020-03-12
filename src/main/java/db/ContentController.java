package db;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ContentController extends DBConn {

    /**
     * Create controller with a conected host to add content
     * 
     * @param host
     * @param db
     * @param user
     * @param password
     */
    public ContentController(String host, String db, String user, String password) {
        connect(host, db, user, password);
        // Let creating a film be one transaction
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("db error during setAuoCommit of LagAvtaleCtrl=" + e);
            return;
        }
        ActiveDomainObject.setConnection(conn);
    }

    /**
     * Add movie to database
     * 
     * @param title
     * @param series_id
     * @param pub_year
     * @param pub_date
     * @param storyline
     * @param runlength
     * @throws SQLException
     */
    public void insertMovie(String title, int pub_year, Date pub_date, String storyline, int runlength)
            throws SQLException {
        // TODO: implement
        Film f;
    }

    void insertEpisode() {
        // TODO: implement
    }

    public void insertPerson(String name, String country, Date birthdate) throws SQLException {
        Person p = new Person(name, birthdate, country);
        p.save();
    }

    public Person getPerson(String name) throws SQLException {
        Person p = Person.get("name=" + name);
        return p;
    }

    public void insertCompany(String name, String country, String address, String url) {
        Company c = new Company(name, country, address, url);
        c.save();
    }

    public void insertSeries(String name, Company comp) {
        Series s = new Series(comp.getID(), name);
        s.save();
    }

    public void testSeriesCompanyFilm() {
        try {
            Film f = new Film("Frozen 2", 2019, new Date(119, 9, 10), "Dobbelt så frossent!", 112,
                    Company.get("comp_name='Disney'").getID());
            f.save();
        } catch (SQLException e) {
            System.out.println("Feil: " + e);
        }
    }

    public Person personLookup(String name) throws Exception {
        Person p = Person.get("full_name='" + name + "'");
        System.out.println("\n" + p.getName());
        System.out.println("Født: " + p.getBirthdate());
        System.out.println("Filmer:\n---------------------------------------------");
        p.getFilmsAsActor().stream().forEach(r -> {
            try {
                System.out.println(r.getRole() + "\t| " + r.getFilm().getTitle());
            } catch (SQLException e) {
                System.out.println("Det skjedde en feil!"+e);
                e.printStackTrace();
            }
        });
        return p;
    }

    public Series seriesLookup(String name) throws Exception {
        Series s = Series.get("title='" + name + "'");
        System.out.println("\n" + s.getTitle());
        System.out.println("Eies av: " + s.getCompany().getName());
        System.out.println(s.getID());
        List<Film> episodes = s.getEpisodes();
        if (episodes.isEmpty()) {
            System.out.println("Ingen episoder ennå.");
        }
        else if (s.isMovie()) {
            Film f = episodes.get(0);
            System.out.println("Lengde: " + f.getRunlength() + " min");
            System.out.println("Utgitt: " + f.getPub_year());
            System.out.println("Handling:\n" + f.getStoryline());

            System.out.println("\nRegissert av\n---------");
            f.getDirectors().stream().forEach(p -> System.out.println(p.getName()));
            System.out.println("\nSkrevet av\n-----------");
            f.getWriters().stream().forEach(p -> System.out.println(p.getName()));
            System.out.println("\nRoller\n-----------");
            f.getActors().stream().forEach(System.out::println);

            System.out.println("\nAnmeldelser\n----------------");
            f.getReviews().stream().forEach(System.out::println);
            
        } else {
            System.out.println("Utgitt fra " + episodes.get(0).getPub_year() + " til " + episodes.get(episodes.size() - 1).getPub_year());

            System.out.println("Episoder:\n---------------------");
            for (Film f : episodes) {
                System.out.println("E"+f.getEpisode()+"S"+f.getSeason()+": "+f.getTitle());
                System.out.println("Lengde: " + f.getRunlength() + " min");
                System.out.println("Handling:\n" + f.getStoryline() + "\n");
            }
        }
        finish();
        return s;
    }

    public Category categoryLookup(String name) throws Exception {
        Category c = Category.get("cat_name='" + name + "'");
        System.out.println(c.getCat_name());
        System.out.println("\nFilmer i denne kategorien:\n---------------------------------");
        c.getTagged().stream().forEach(s -> System.out.println(s.getTitle()));
        System.out.println("\nTopselskaper i denne kategorien\n--------------------------------------");

        // SQL spørring for å telle antall filmer i kategorien for hvert selskap
        // GROUP BY comp_id for å telle antall til hvert selskap
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT COUNT(title) AS num, comp_id "+
            "FROM Category NATURAL JOIN Tag NATURAL JOIN Series "+
            "WHERE cat_name='" + name + "' "+
            "GROUP BY comp_id "+
            "ORDER BY num desc;"
        );
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Company comp = Company.get("comp_id=" + rs.getInt("comp_id"));
            System.out.println(comp.getName() + ": " + rs.getInt("num") + " filmer eller serier");
        }
        finish();
        return c;
    }

    public void addReview(Film f, User u, int rating, String comment) {
        f.addReview(u, rating, comment);
        finish();
    }

    public void finish() {
        try {
            conn.commit();
        } catch (SQLException e) {
            System.out.println("db error during commit of LagAvtaleCtrl="+e);
            return;
        }
    }
}