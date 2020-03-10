package db;

import java.sql.*;

/**
 * Abstract class extended by database models
 */
public abstract class ActiveDomainObject {
    protected static Connection conn;                // Instance db connection
    protected int ID = -1;

    public int getID() {
        return ID;
    }

    /**
     * Save a new object or any changes done to an existing object
     */
    public abstract void save();

    /**
     * Set db connection for all objects
     */
    public static void setConnection(Connection conn) {
        ActiveDomainObject.conn = conn;
    }
}