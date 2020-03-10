package db;

import java.sql.*;
import java.util.*;

public class RoleBy extends ActiveDomainObject {
    private int film_id;
    private int pid;
    private String _role;

    public RoleBy (int pid, int film_id, String _role) {
        this.pid = pid;
        this.film_id = film_id;
        this._role = _role;
    }
    public int getFilmId () {
        return this.film_id;
    }
    public int getPid () {
        return this.pid;
    }
    public String getRole () {
        return this._role;
    }

    @Override
    public void initialize(Connection conn) {
        // TODO Auto-generated method stub


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