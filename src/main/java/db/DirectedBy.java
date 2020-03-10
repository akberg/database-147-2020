package db;

import java.sql.*;
import java.util.*;

public class DirectedBy extends ActiveDomainObject {
    private int film_id;
    private int pid;

    public DirectedBy (int pid, int film_id) {
        this.pid = pid;
        this.film_id = film_id;
    }
    public int getFilmId () {
        return this.film_id;
    }
    public int getPid () {
        return this.pid;
    }

    @Override
    public void initialize(Connection conn) {
        // TODO Auto-generated method stub
        initialize (conn);

    }

    @Override
    public void refresh(Connection conn) {
        // TODO Auto-generated method stub
        initialize (conn);

    }

    @Override
    public void save(Connection conn) {
        // TODO Auto-generated method stub

    }
}