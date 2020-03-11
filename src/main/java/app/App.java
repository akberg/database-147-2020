package app;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

import db.*;


/**
 * Main program
 */
public final class App {
    static Scanner in = new Scanner(System.in);
    static ContentController ctrl;

    public static int getChoice() {
        try {
            return Integer.valueOf(in.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        // Get db credentials
        System.out.println("Host: localhost");
        String dbHost = "localhost"; //in.nextLine();
        System.out.println("Database name: moviedb");
        String dbName = "moviedb"; //in.nextLine();
        System.out.println("Username: root");
        String dbUser = "root"; //in.nextLine();
        System.out.println("Password: ****");
        String dbPassword = "3619"; //in.nextLine();

        ctrl = new ContentController(dbHost, dbName, dbUser, dbPassword);

        homeView();
    }

    public static void homeView() {
        int x = -1;
        while (x != 0) {
            switch (x) {
                case 1:
                    personView();
                    break;
                case 2:
                    seriesFilmView();
                default:
                    break;
            }

            System.out.println("------------------");
            System.out.println("VELKOMMEN TIL JMDB");
            System.out.println("------------------");
            System.out.println("\nVelg handling ved å skrive inn tilhørende tall\n");

            int i = 0;
            System.out.println(i++ +") Avslutt");
            System.out.println(i++ +") Søk på person");             // Noter at "søk" bare kan finne nøyaktig match
            System.out.println(i++ +") Søk på film eller serie");
            System.out.println(i++ +") Søk på kategori");
            System.out.println(i++ +") Legg til person");
            System.out.println(i++ +") Legg til film");

            x = getChoice();
        }

        System.exit(0);
    }

    public static void personView() {
        int x = 1;
        Person p;
        while (x != 0) {
            switch (x) {
                case 1:
                    System.out.println("Søk på et navn: ");
                    try {
                        p = ctrl.personLookup(in.nextLine());
                    } catch (Exception e) {
                        System.out.println("Det skjedde en feil: " + e);
                        return;
                    }
                    break;
            
                default:
                    break;
            }
            System.out.println("\nVelg handling ved å skrive inn tilhørende tall\n");

            int i = 0;
            System.out.println(i++ +") Tilbake");
            System.out.println(i++ +") Søk på en annen person");

            x = getChoice();
        }
    }

    public static void seriesFilmView() {
        int x = 1;
        Series s;
        while (x != 0) {
            switch (x) {
                case 1:
                    try {
                        System.out.println("Søk på en serie eller film: ");
                        s = ctrl.seriesLookup(in.nextLine());
                        if (s.isMovie()) {
                            filmView(Film.get("series_id=" + s.getID()));
                        } else {
                            seriesView(s);
                        }
                    } catch (Exception e) {
                        System.out.println("Det skjedde en feil: " + e);
                        homeView();
                    }
                    break;
            
                default:
                    break;
            }
        }
    }

    public static void seriesView(Series s) {
        int x = -1;
        switch (x) {
            case 1:
                seriesFilmView();
                break;
        
            default:
                break;
        }

        int i = 0;
        // Choices
        System.out.println(i++ +") Tilbake");
        System.out.println(i++ +") Søk på en annen serie eller film");
        System.out.println(i++ +") ");

        x = getChoice();
    }

    public static void filmView(Film f) {
        int x = -1;
        switch (x) {
            case 1:
                seriesFilmView();
                break;
        
            default:
                break;
        }

        int i = 0;
        // Choices
        System.out.println(i++ +") Tilbake");
        System.out.println(i++ +") Søk på en annen serie eller film");
        System.out.println(i++ +") ");

        x = getChoice();
    }

    public static void categoryView() {
        int x = 1;
        switch (x) {
            case 1:
                System.out.println("Søk på en kategori:");
                ctrl.categoryLookup(in.nextLine());
                break;
        
            default:
                break;
        }

        int i = 0;
        // Choices
        System.out.println(i++ +") Tilbake");
        System.out.println(i++ +") Søk på en annen kategori");

        x = getChoice();
    }
}
