package db;

import java.util.List;

public class Music extends ActiveDomainObject {

    String title;
    String composer;
    String performer;

    // Constructors

    public Music(int id, String title, String composer, String performer) {
        // TODO: implement
    }

    public Music(String title, String composer, String performer) {
        this(-1, title, composer, performer);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    /**
     * Returns all Film objects using this music in their soundtrack
     * @return list of Film objects
     */
    public List<Film> getFilms() {
        // TODO: implement
    }

    public static Music get(String constraint) {
        // TODO: implement
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub

    }
}