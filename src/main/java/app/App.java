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
        System.out.println("Host: ");
        String dbHost = in.nextLine();
        System.out.println("Database name: ");
        String dbName = in.nextLine();
        System.out.println("Username: ");
        String dbUser = in.nextLine();
        System.out.println("Password: ");
        String dbPassword = in.nextLine();
        in.close();

        AddContentController ctrl = new AddContentController(dbHost, dbName, dbUser, dbPassword);
        try {        
            ctrl.insertPerson("Inger-Ane", "Norway", new Date(0));
            
        } catch (SQLException e) {
            System.out.println("db query failed: "+e);
        }

    }
}
