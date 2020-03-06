package db;

import java.sql.*;
import java.util.*;

public class Appointment extends ActiveDomainObject {
    private ArrayList<User> users;
    private int aid;
    private int startTime;
    private int hours;
    private int type;
    public static final int MEETING=1;
    public static final int PROGRAM=2;
    private static final int NOID = -1;
    
    public Appointment (int startTid, int timer, int type) {
        aid = NOID;
        this.startTime = startTid;
        this.hours = timer;
        this.type = type;
        users = new ArrayList<User>();
    }

    public void regBruker (int bid, Connection conn) {
        User b = new User(bid);
        b.initialize (conn);
        users.add(b);
    }
    
    public void regTid (int startTid, int hours) {
        this.startTime = startTid;
        this.hours = hours;
    }
    
    @Override
    public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select starttid, timer, alarmtype from Avtale where aid=" + aid);
            while (rs.next()) {
                startTime =  rs.getInt("starttid");
                hours = rs.getInt("timer");
                type = rs.getInt("avtaletype");
            }

        } catch (Exception e) {
            System.out.println("db error during select of avtale= "+e);
            return;
        }
    
    }
    
    @Override
    public void refresh (Connection conn) {
        initialize (conn);
    }
    
    @Override
    public void save (Connection conn) {
        try {    
            Statement stmt = conn.createStatement(); 
            stmt.executeUpdate("insert into Avtale values (NULL,"+startTime+","+hours+","+type+")");
        } catch (Exception e) {
            System.out.println("db error during insert of Avtale="+e);
            return;
        }
        for (int i=0;i<users.size();i++) {
            try {    
                Statement stmt = conn.createStatement(); 
                stmt.executeUpdate("insert into HarAvtale values ("+users.get(i).getUid()+",LAST_INSERT_ID())");
            } catch (Exception e) {
                System.out.println("db error during insert of HarAvtale="+e);
                return;
            }
        }
    }
}