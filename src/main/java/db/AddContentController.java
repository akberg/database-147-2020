package db;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AddContentController extends DBConn {

    /**
     * Create controller with a conected host to add content
     * @param host
     * @param db
     * @param user
     * @param password
     */
    public AddContentController (String host, String db, String user, String password) {
        connect(host, db, user, password);
        // Let creating a film be one transaction
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("db error during setAuoCommit of LagAvtaleCtrl="+e);
            return;
        }
        ActiveDomainObject.setConnection(conn);
    }

    /**
     * Add movie to database
     * @param title
     * @param series_id
     * @param pub_year
     * @param pub_date
     * @param storyline
     * @param runlength
     * @throws SQLException
     */
    // public void insertFilm(String title, int pub_year, Date pub_date, String storyline, int runlength) throws SQLException {
    //     PreparedStatement stmt = conn.prepareStatement(
    //         "insert into Film (title, series_id, pub_year, pub_date, storyline, runlength) values (?, ?, ?, ?, ?, ?);"
    //         );
    //     stmt.setString(1, title);
    //     stmt.setInt(2, series_id);
    //     stmt.setInt(3, pub_year);
    //     stmt.setDate(4, pub_date);
    //     stmt.setString(5, storyline);
    //     stmt.setInt(6, runlength);
    //     stmt.executeUpdate();
    //     conn.commit();
    // }

    public void insertPerson(String name, String country, Date birthdate) throws SQLException {
        Person p = new Person(name, birthdate, country);
        p.save();
        // PreparedStatement stmt = conn.prepareStatement(
        //     "insert into Person (full_name, country, birth_date) values (?, ?, ?);"
        // );
        // stmt.setString(1, name);
        // stmt.setString(2, country);
        // stmt.setDate(3, birthdate);
        // stmt.execute();
        // conn.commit();
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
        Company c = new Company("Fox", "USA", "20th Fox st", "fox.com");
        Series s;
        try {
            s = new Series(c.getID(), "Frozen 2");
            System.out.println("FEIL!!");
        } catch (IllegalStateException e) {
            System.out.println(e);
            c.save();
            s = new Series(c.getID(), "Frozen 2");
            s.save();
        }
        Film f = new Film(s, s.getTitle(), 2019, new Date(119, 9, 10), "Dobbelt så frossent!", 112);
        f.save();
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