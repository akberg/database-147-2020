package app;

import java.sql.SQLException;
import java.sql.Date;
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
                    personView(1);
                    break;
                case 2:
                    seriesFilmView();
                case 3:
                    categoryView();
                case 4:
                    personView(2);
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

    public static void personView(int x) {
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
                case 2:
                    System.out.println("Personens navn:");
                    String name = in.nextLine();
                    Date bd = null;
                    while (bd == null) {
                        try {
                            System.out.println("Fødselsdato (format yyyy-mm-dd):");
                            bd = Date.valueOf(in.nextLine());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Skrev du riktig format? Prøv igjen.");
                        }
                    }
                    System.out.println("Personens hjemland:");
                    String country = in.nextLine();
                    try {
                        ctrl.insertPerson(name, country, bd);
                    } catch (SQLException e) {
                        System.out.println();
                    }
                    System.out.println("La til " + name + " i databasen");
                    break;
                default:
                    break;
            }
            System.out.println("\nVelg handling ved å skrive inn tilhørende tall\n");

            int i = 0;
            System.out.println(i++ +") Tilbake");
            System.out.println(i++ +") Søk på en annen person");
            System.out.println(i++ +") Legg til en person");

            x = getChoice();
        }
    }

    public static void seriesFilmView() { seriesFilmView(1);}
    public static void seriesFilmView(int x) {
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

    public static void seriesView(Series s) { seriesView(s, -1); }
    public static void seriesView(Series s, int x) {
        while (x != 0) {
            switch (x) {
                case 1:
                    seriesFilmView();
                    break;
                case 2:
                    // Add episode

                default:
                    int offset = 2 + 1;
                    if (offset <= x && x - offset < s.getEpisodes().size()) {
                        Film f = s.getEpisodes().get(x - offset);
                        System.out.println("Detalsjer for E"+f.getEpisode()+"S"+f.getSeason()+": "+f.getTitle());
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
                        filmView(f);
                    }
                    break;
            }
    
            int i = 0;
            // Choices
            System.out.println(i++ +") Tilbake");
            System.out.println(i++ +") Søk på en annen serie eller film");
            System.out.println(i++ +") Legg til episode");
            for (Film f : s.getEpisodes())
                System.out.println(i++ +") Se detaljer om episode "+f.getEpisode()+" sesong "+f.getSeason()+" - "+f.getTitle());
    
            x = getChoice();
        }
        homeView();
    }

    public static void filmView(Film f) { filmView(f, -1); }
    public static void filmView(Film f, int x) {
        while (x != 0) {
            switch (x) {
                case 1:
                    seriesFilmView(1);
                    break;
                case 2:
                    System.out.println("Brukernavn:");
                    String username = in.nextLine();
                    User u;
                    try {
                        u = User.get("username=" + username);
                    } catch(Exception e) {
                        System.out.println("Bruker " + username + " finnes ikke, oppretter ny bruker.");
                        u = new User(username);
                        u.save();
                    }
                    int rating = -1;
                    while (rating < 1 || 5 < rating) {
                        System.out.println("Gi en vurdering (1-5):");
                        rating = getChoice();
                    }

                    System.out.println("Gi en begrunnelse (én linje):");
                    String comment = in.nextLine();
                    ctrl.addReview(f, u, rating, comment);
                default:
                    break;
            }

            int i = 0;
            // Choices
            System.out.println(i++ +") Tilbake");
            System.out.println(i++ +") Søk på en annen serie eller film");
            System.out.println(i++ +") Skriv anmeldelse");

            x = getChoice();
        }
    }

    public static void categoryView() {
        int x = 1;
        while (x != 0) {
            switch (x) {
                case 1:
                    System.out.println("Søk på en kategori:");
                    try {
                        ctrl.categoryLookup(in.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("Det skjedde en feil: " + e);
                        homeView();
                    }
            
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
}
