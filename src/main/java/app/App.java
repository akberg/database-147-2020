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
        String dbHost;
        String dbName;
        String dbUser;
        String dbPassword;
        // Get db credentials
        if (args.length > 0) {
            try {
                dbHost = args[0];
                dbName = args[1];
                dbUser = args[2];
                dbPassword = args[3];
                ctrl = new ContentController(dbHost, dbName, dbUser, dbPassword);
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                // System.out.println("Det skjedde en feil. Forventede argumenter er <vert> <database> <bruker> <passord>");
                // System.out.println("Sørg også for å ha en database etter vedlagt skript ferdig laget.");
                // System.out.println("" + e + e.getStackTrace());
                // System.exit(0);
            }
        } else {
            try {
                System.out.println("Host: localhost");
                dbHost = "localhost"; 
                //dbHost = in.nextLine();
                System.out.println("Database name: moviedb");
                dbName = "moviedb"; 
                //dbName = in.nextLine();
                System.out.println("Username: root");
                dbUser = "root"; 
                //dbUser = in.nextLine();
                System.out.println("Password: ****");
                dbPassword = "3619"; 
                //dbPassword = in.nextLine();
                ctrl = new ContentController(dbHost, dbName, dbUser, dbPassword);
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                // System.out.println("Det skjedde en feil, vennligst prøv igjen");
                // System.out.println("" + e + e.getStackTrace());
                // main(args);
            }
            
        }

        


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
                    break;
                case 3:
                    categoryView(1);
                    break;
                case 4:
                    personView(2);
                    break;
                case 5:
                    filmView(null, null, 4);
                    break;
                case 6:
                    seriesView(null, 3);
                    break;
                case 7:
                    musicView(2);
                default:
                    break;
            }

            System.out.println("------------------");
            System.out.println("VELKOMMEN TIL JMDB");
            System.out.println("------------------");
            System.out.println("\nVelg handling ved å skrive inn tilhørende tall\n");

            int i = 0;
            System.out.println(i++ + ") Avslutt");
            System.out.println(i++ + ") Søk på person"); // Noter at "søk" bare kan finne nøyaktig match
            System.out.println(i++ + ") Søk på film eller serie");
            System.out.println(i++ + ") Søk på kategori");
            System.out.println(i++ + ") Legg til person");
            System.out.println(i++ + ") Legg til spillefilm");
            System.out.println(i++ + ") Legg til serie");
            System.out.println(i++ + ") Legg til musikk");

            x = getChoice();
        }

        System.exit(0);
    }

    private static void musicView(int x) {
        Music m;
        //while (x != 0) {
            switch (x) {
                case 2:
                    // Add music
                    System.out.println("Tittel på musikk:");
                    String title = in.nextLine();
                    System.out.println("Komponert eller skrevet av:");
                    String composer = in.nextLine();
                    System.out.println("Fremført av:");
                    String performer = in.nextLine();
                    ctrl.addMusic(title, composer, performer);

                    System.out.println("La til " + title + " av " + composer + " i databasen");
                    break;
            
                default:
                    break;
            }
        //}
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
            System.out.println(i++ + ") Tilbake");
            System.out.println(i++ + ") Søk på en annen person");
            System.out.println(i++ + ") Legg til en person");

            x = getChoice();
        }
    }

    public static void seriesFilmView() {
        seriesFilmView(1);
    }

    public static void seriesFilmView(int x) {
        Series s;
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

    public static void seriesView(Series s) {
        seriesView(s, -1);
    }

    public static void seriesView(Series s, int x) {
        while (x != 0) {
            switch (x) {
                case 1:
                    seriesFilmView();
                    break;
                case 2:
                    // Add episode
                    filmView(null, s, 3);
                    break;
                case 3:
                    // New series
                    System.out.println("Seriens navn:");
                    String title = in.nextLine();
                    Company c = null;
                    while (c == null) {
                        System.out.println("Eierselskap:");
                        c = ctrl.getCompany(in.nextLine());
                    }
                    ctrl.insertSeries(title, c);

                    // Show newly added series
                    try {
                        s = ctrl.seriesLookup(title);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        homeView();
                    }
                    break;
                default:
                    int offset = 3 + 1;
                    if (offset <= x && x - offset < s.getEpisodes().size()) {
                        Film f = s.getEpisodes().get(x - offset);
                        System.out.println(
                                "Detalsjer for E" + f.getEpisode() + "S" + f.getSeason() + ": " + f.getTitle());
                        System.out.println("Lengde: " + f.getRunlength() + " min");
                        System.out.println("Utgitt: " + f.getPub_year());
                        System.out.println("Handling:\n" + f.getStoryline());

                        System.out.println("\nRegissert av\n---------");
                        f.getDirectors().stream().forEach(p -> System.out.println(p.getName()));
                        System.out.println("\nSkrevet av\n-----------");
                        f.getWriters().stream().forEach(p -> System.out.println(p.getName()));
                        System.out.println("\nMusikk\n-------------");
                        f.getSoundtrack().stream().forEach(System.out::println);
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
            System.out.println(i++ + ") Tilbake");
            System.out.println(i++ + ") Søk på en annen serie eller film");
            System.out.println(i++ + ") Legg til episode");
            System.out.println(i++ + ") Legg til ny serie");
            for (Film f : s.getEpisodes())
                System.out.println(i++ + ") Se detaljer om episode " + f.getEpisode() + " sesong " + f.getSeason()
                        + " - " + f.getTitle());

            x = getChoice();
        }
        homeView();
    }

    public static void filmView(Film f) {
        filmView(f, f.getSeries(), -1);
    }

    public static void filmView(Film f, Series s, int x) {
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
                    } catch (Exception e) {
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
                    break;
                case 3: // Add episode to series or new movie
                    System.out.println("Tittel på " + (s != null ? "episode" : "film"));
                    String title = in.nextLine();
                    System.out.println("Lengde i minutter: ");
                    int runlength = getChoice();
                    Date rd = null;
                    while (rd == null) {
                        try {
                            System.out.println("Utgivelsesdato (format yyyy-mm-dd):");
                            rd = Date.valueOf(in.nextLine());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Skrev du riktig format? Prøv igjen.");
                        }
                    }
                    int year = rd.getYear() + 1900;
                    System.out.println("Handling: ");
                    String storyline = in.nextLine();

                    if (s == null) {
                        Company c = null;
                        while (c == null) {
                            System.out.println("Eierselskap:");
                            c = ctrl.getCompany(in.nextLine());
                        }
                        // Create Movie
                        f = new Film(title, year, rd, storyline, runlength, c.getID());
                        
                    } else {
                        int season;
                        int episode;
                        while (true) {
                            System.out.println("Sesong:");
                            season = getChoice();
                            System.out.println("Episode:");
                            episode = getChoice();
                            if (s.hasEpisode(season, episode)) {
                                System.out.println("Den episoden finnes allerede!");
                            } else { break; }
                        }
                        // Create Episode
                        f = new Film(title, s.getID(), season, episode, year, rd, storyline, runlength);
                    }
                    f.save();
                    try {
                        ctrl.seriesLookup(f.getSeries().getTitle());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        homeView();
                    }
                    break;
                case 4: // Add director
                case 5: // Add writer
                case 6: // Add actor
                    Person p = null;
                    while (p == null) {
                        System.out.println("Navn:");
                        p = ctrl.getPerson(in.nextLine());
                    }
                    if (x == 4) {
                        f.addDirector(p);
                    }
                    else if (x == 5) {
                        f.addWriter(p);
                    } else {
                        System.out.println("Navn på rolle spilt av " + p.getName());
                        f.addActor(p, in.nextLine());
                    }

                    try {
                        ctrl.seriesLookup(f.getSeries().getTitle());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        homeView();
                    }
                    break;
                case 7: // Add soundtrack
                    Music m = null;
                    while (m == null) {
                        System.out.println("Musikkstykke:");
                        m = ctrl.getMusic(in.nextLine());
                    } 
                    f.addMusic(m);
                    try {
                        ctrl.seriesLookup(f.getSeries().getTitle());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        homeView();
                    }
                default:
                    break;
            }

            int i = 0;
            // Choices
            System.out.println(i++ +") Tilbake");
            System.out.println(i++ +") Søk på en annen serie eller film");
            System.out.println(i++ +") Skriv anmeldelse");
            System.out.println(i++ +") Legg til en film");
            System.out.println(i++ +") Legg til regissør");
            System.out.println(i++ +") Legg til forfatter");
            System.out.println(i++ +") Legg til skuespiller");
            System.out.println(i++ +") Legg til musikk");

            x = getChoice();
        }
    }

    public static void categoryView(int x) {
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
