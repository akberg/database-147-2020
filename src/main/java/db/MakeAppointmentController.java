package db;

import java.sql.*;
import java.util.*;

public class MakeAppointmentController extends DBConn {
    private Appointment appointment;
    private int startPossibleTime;
    private int sluttPossibleTime;

    public MakeAppointmentController () {
        connect();
        // La laging av avtale vÃƒÂ¦re en transaksjon
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("db error during setAuoCommit of LagAvtaleCtrl="+e);
            return;
        }
    }

    public void lagAvtale (int startMTid, int sluttMTid){
        appointment = new Appointment (startMTid,0,Appointment.MEETING);
        this.startPossibleTime = startMTid;
        this.sluttPossibleTime = sluttMTid;
    }
    public void velgBruker (int bid) {
        appointment.regBruker (bid, conn);
        // Vis hvilke tider som er aktuelle, gjÃƒÂ¸r noe her .....
    }
    public void velgTidspunkt (int startTid, int antall) {
        appointment.regTid(startTid, antall);
    }
    public void lagAlarm (String alarmTekst) {
        
    }
    public void finishDeal () {
        appointment.save(conn);
        try {
            conn.commit();
        } catch (SQLException e) {
            System.out.println("db error during commit of LagAvtaleCtrl="+e);
            return;
        }
    }
}