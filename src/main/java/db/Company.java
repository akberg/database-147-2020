package db;

import java.sql.*;

public class Company extends ActiveDomainObject {

    private int comp_id;
    private String comp_name;
    private String country;
    private String address;
    private String url;

    public Company(int comp_id, String comp_name, String country, String address, String url) {
        this.comp_id = comp_id;
        this.comp_name = comp_name;
        this.country = country;
        this.address = address;
        this.url = url;
    }

    @Override
    public void initialize(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Company where comp_id=" + comp_id);
            while (rs.next()) {
                comp_name =  rs.getString("comp_name");
                country = rs.getString("country");
                address = rs.getString("address");
                url = rs.getString("url");
            }

        } catch (Exception e) {
            System.out.println("db error during select of user= "+e);
            return;
        }
    }

    @Override
    public void refresh(Connection conn) {
        // TODO Auto-generated method stub
        initialize(conn);
    }

    @Override
    public void save(Connection conn) {
        try {
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(
                "update Company set comp_name="+comp_name+
                ", country="+country+
                ", address="+address+
                ", url="+url+
                "where comp_id="+comp_id
                );
        } catch (Exception e) {
            System.out.println("db error during update of user="+e);
            return;
        }
    }



}