package app;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

import db.*;


/**
 * Main program
 */
public final class App {

    /**
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        // Get db credentials
        Scanner in = new Scanner(System.in);
        System.out.println("Host: localhost");
        String dbHost = "localhost"; //in.nextLine();
        System.out.println("Database name: moviedb");
        String dbName = "moviedb"; //in.nextLine();
        System.out.println("Username: root");
        String dbUser = "root"; //in.nextLine();
        System.out.println("Password: ****");
        String dbPassword = "3619"; //in.nextLine();
        in.close();
        ContentController ctrl = new ContentController(dbHost, dbName, dbUser, dbPassword);
        try {
            ctrl.personLookup("Kristoffer Joner");
            ctrl.personLookup("Steve Carell");

        } catch (Exception e) {
            System.out.println("db query failed: "+e);
        }

    }
}
