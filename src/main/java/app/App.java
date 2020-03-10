package app;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

import db.*;


/**
 * Main program
 */
public final class App {
    Scanner in = new Scanner(System.in);

    public static void personLookup() {
        int x = 1;
        while (x != -1) {
            switch (x) {
                case 1:
                    System.out.println("Søk på et navn: ");
                    String name = in.nextLine();
                    // Person() --> select * from Person where name=name
                    // Hvis ingen treff, skriv ut "Ingen treff"
                    try {
                        Person p;
                        System.out.println(
                            p.getName() + "\n" +
                            p.getBirthdate() + "\n" +
                            p.getCountry() +"\n-------------------------------\n" +
                            "Spiller i: "
                            // Film som Rolle
                        )
                    } catch (Exception e) {
                        System.out.println("Noe gikk galt! Ingen treff? " + e);
                    }
                    break;
            
                default:
                System.out.println("Ikke et gyldig alternativ");
                    break;
            }
            System.out.println(
                "Hva vil du gjøre nå?\n" +
                "0) Tilbake\n" +
                "1) Nytt søk"
            );
        }
    }

    public static void categoryLookup() {
        int x = 1;
        while (x != -1) {
            switch (x) {
                case 1:
                    System.out.println("Søk på en kategori: ");
                    String name = in.nextLine();
                    // Category() --> select * from category where name=name
                    // Hvis ingen treff, skriv ut "Ingen treff"
                    try {
                        Category p;
                        // List selskap med flest filmer i kategori
                        // evt: List filmer i denne kategorien
                        System.out.println(
                            p.getName() + "\n" +
                            p.getBirthdate() + "\n" +
                            p.getCountry() +"\n-------------------------------\n" +
                            "Spiller i: "
                            // Film som Rolle
                        )
                    } catch (Exception e) {
                        System.out.println("Noe gikk galt! Ingen treff? " + e);
                    }
                    break;
            
                default:
                System.out.println("Ikke et gyldig alternativ");
                    break;
            }
            System.out.println(
                "Hva vil du gjøre nå?\n" +
                "0) Tilbake\n" +
                "1) Nytt kategorisøk" 
            );
        }
    }
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
        AddContentController ctrl = new AddContentController(dbHost, dbName, dbUser, dbPassword);

        int x = -1;
        while (x != 0) {
            System.out.println(
                "VELKOMMEN til JMDB\n\n" +
                "0) Avslutt\n" +
                "1) Se person\n"
            );

            x = in.nextInt();

            switch (x) {
                case 1:
                    // Funksjon for å se person
                    personLookup();
                    break;
            
                default:
                    System.out.println("Ikke et gyldig alternativ");
                    break;
            }
        }


        try {
            System.out.println("Inserting value");        
            ctrl.insertPerson("Inger-Ane", "Norway", new Date(0));

        } catch (SQLException e) {
            System.out.println("db query failed: "+e);
        }
        in.close();

    }
}
