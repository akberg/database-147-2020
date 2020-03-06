package db;

import java.sql.*;

public class Series extends ActiveDomainObject {

    private int series_id;
    private int comp_id;
    private String title;

    public Series(int series_id, int comp_id, String title) {
        this.series_id = series_id;
        this.comp_id = comp_id;
        this.title = title;
    }

    public int getSeries_id() {
        return series_id;
    }

    public void setSeries_id(int series_id) {
        this.series_id = series_id;
    }

    public int getComp_id() {
        return comp_id;
    }

    public void setComp_id(int comp_id) {
        this.comp_id = comp_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void initialize(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Series where series_id=" + series_id);
            while (rs.next()) {
                title = rs.getString("title");
                comp_id = rs.getInt("comp_id");
            }

        } catch (Exception e) {
            System.out.println("db error during select of user= "+e);
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
            ResultSet rs = stmt.executeQuery("update Series set title="+title+", comp_id="+comp_id+" where series_id="+series_id);
        } catch (Exception e) {
            System.out.println("db error during update of user="+e);
            return;
        }
    }

    
}