package db;

import java.sql.*;

/**
 * Abstract class extended by database models
 */
public abstract class ActiveDomainObject {
    private static Connection defaultConn;  // Default DB connection
    private static String modelName;

    private Connection conn;                // Instance db connection
    private int ID = -1;                    // Object id, -1 if objects does not yet exist in the db

    public ActiveDomainObject() {
        conn = defaultConn;
    }

    public abstract void initialize(Connection conn);
    public abstract void refresh(Connection conn);
    /**
     * Save a new object or any changes done to an existing object
     */
    public abstract void save();

    public static ActiveDomainObject get(String constraint) {
        try {
            PreparedStatement stmt = defaultConn.prepareStatement("select * from " + modelName + " where " + constraint);
            ResultSet rs = stmt.executeQuery();
        } catch (SQLException e) {}
    }
    
    /**
     * Set db connection for ActiveDomainObject
     * @param conn connection
     */
    public static void setDefaultConnection(Connection conn) {
        ActiveDomainObject.defaultConn = conn;
    }
}