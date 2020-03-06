package db;

import java.sql.*;

public class Person extends ActiveDomainObject {
    private int person_id;
    private String name;
    private Date birthdate;
    private String country;

    public Person(int person_id, String name, Date birthdate, String country) {
        this.person_id = person_id;
        this.name = name;
        this.birthdate = birthdate;
        this.country = country;
    }

    public Person(int person_id) {
        this.person_id = person_id;
    };

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public void initialize(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Person where person_id=" + person_id);
            while (rs.next()) {
                name =  rs.getString("name");
                country = rs.getString("country");
                birthdate = rs.getDate("birthdate");
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
            ResultSet rs = stmt.executeQuery("update Person set name="+name+"where person_id="+person_id);
        } catch (Exception e) {
            System.out.println("db error during update of bruker="+e);
            return;
        }
    }

    
}