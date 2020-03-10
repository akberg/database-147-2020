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
        AddContentController ctrl = new AddContentController(dbHost, dbName, dbUser, dbPassword);
        try {
            System.out.println("Inserting value");        
            //ctrl.insertPerson("Inger-Ane", "Norway", new Date(0));
            ctrl.testSeriesCompanyFilm();

        } catch (Exception e) {
            System.out.println("db query failed: "+e);
        }

    }
}
