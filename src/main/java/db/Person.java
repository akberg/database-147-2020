package db;

import java.sql.*;

public class Person extends ActiveDomainObject {
    private int person_id;
    private String full_name;
    private Date birthdate;
    private String country;

    public Person(int person_id, String name, Date birthdate, String country) {
        this.person_id = person_id;
        this.full_name = name;
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
        return full_name;
    }

    public void setName(String name) {
        this.full_name = name;
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
                full_name =  rs.getString("full_name");
                country = rs.getString("country");
                birthdate = rs.getDate("birth_date");
            }

        } catch (Exception e) {
            System.out.println("db error during select of person= "+e);
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
            ResultSet rs = stmt.executeQuery("update Person set full_name="+full_name+"where person_id="+person_id);
        } catch (Exception e) {
            System.out.println("db error during update of bruker="+e);
            return;
        }
    }

    
}