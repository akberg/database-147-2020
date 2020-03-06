package db;

import java.sql.*;
import java.util.*;

public class User extends ActiveDomainObject {
    private int uid;
    private String name;

    public User (int uid) {
        this.uid = uid;
    }
    public int getUid () {
        return uid;
    }
    public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select name from User where uid=" + uid);
            while (rs.next()) {
                name =  rs.getString("name");
            }

        } catch (Exception e) {
            System.out.println("db error during select of user= "+e);
            return;
        }

    }
    
    public void refresh (Connection conn) {
        initialize (conn);
    }
    
    public void save (Connection conn) {
        try {
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery("update Bruker set navn="+name+"where bid="+uid);
        } catch (Exception e) {
            System.out.println("db error during update of bruker="+e);
            return;
        }
    }
    
}