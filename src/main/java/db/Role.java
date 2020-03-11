package db;

/**
 * Class representing a Person's role in a film
 */
public class Role {
    private Film film;
    private Person actor;
    private String role;

    public Role(Film film, Person actor, String role) {
        this.film = film;
        this.actor = actor;
        this.role = role;
    }

    public Film getFilm() {
        return film;
    }

    // public void setFilm(Film film) {
    //     this.film = film;
    // }

    public Person getActor() {
        return actor;
    }

    // public void setActor(Person actor) {
    //     this.actor = actor;
    // }

    public String getRole() {
        return role;
    }

    // public void setRole(String role) {
    //     this.role = role;
    // }

    @Override
    public String toString() {
        return getActor().getName() + "\t| " + getRole();
    }
}
