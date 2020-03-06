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
        PreparedStatement stmt = conn.prepareStatement(
            "insert into Film (name, country, birthdate) values (?, ?, ?);"
        );
        stmt.setString(1, name);
        stmt.setString(2, country);
        stmt.setDate(3, birthdate);
        stmt.execute();
        conn.commit();
    }

    public Person getPerson(int id) throws SQLException {
        Person p = new Person(id);
        p.initialize(conn);
        return p;
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