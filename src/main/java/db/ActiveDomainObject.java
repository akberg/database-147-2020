package db;

import java.sql.*;

/**
 * Abstract class extended by database models
 */
public abstract class ActiveDomainObject {
    public abstract void initialize (Connection conn);
    public abstract void refresh (Connection conn);
    public abstract void save (Connection conn);
}