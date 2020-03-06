package db;

import java.sql.*;
import java.util.Properties;

/**
 * Abstract class extended by controller classes
 */
public abstract class DBConn {

    protected Connection conn;

    public void connect(String host, String db, String user, String password) {
    	try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // Class.forName("com.mysql.cj.jdbc.Driver"); when you are using MySQL 8.0.     
        
	        // Properties for user and password.
            Properties p = new Properties();
            p.put("user", user);
            p.put("password", password);           
            System.out.println("jdbc:mysql://" + host + "/" + db + "?autoReconnect=true&useSSL=false");
            conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db + "?autoReconnect=true&useSSL=false",p);
        } catch (Exception e)
    	{
            throw new RuntimeException("Unable to connect", e);
    	}
    }
}