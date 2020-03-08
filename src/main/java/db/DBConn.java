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
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); //when you are using MySQL 8.0.     
        
	        // Properties for user and password.
            Properties p = new Properties();
            p.put("user", user);
            p.put("password", password);           
            System.out.println("jdbc:mysql://" + host + "/" + db + "?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true");
            conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db + "?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",p);
        } catch (Exception e)
    	{
            throw new RuntimeException("Unable to connect", e);
    	}
    }
}