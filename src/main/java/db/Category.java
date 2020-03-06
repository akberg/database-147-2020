package db;

import java.sql.*;

public class Category extends ActiveDomainObject {

    private int cat_id;
    private String cat_name;

    public Category(int id, String name) {
        this.cat_id = id;
        this.cat_name = name;
    }

    /**
     * @return the cat_id
     */
    public int getCat_id() {
        return cat_id;
    }

    /**
     * @return the cat_name
     */
    public String getCat_name() {
        return cat_name;
    }

    @Override
    public void initialize(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Category where cat_id="+this.cat_id);

            while (rs.next()) {
                cat_name = rs.getString("cat_name");
            }
        } catch(Exception e) {
            System.out.println("Loading table User failed: " + e);
            return;
        }
    }

    @Override
    public void refresh(Connection conn) {
        initialize(conn);
    }

    @Override
    public void save(Connection conn) {
        try {
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery("update Category set cat_name=" + cat_name + "where cat_id=" + cat_id);
        } catch (Exception e) {
            System.out.println("db error during update of user="+e);
            return;
        }
    }
    
}