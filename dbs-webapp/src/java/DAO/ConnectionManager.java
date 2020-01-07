package DAO;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that manages connections to the database. It also has a utility
 * method that close connections, statements and resultsets
 */
public class ConnectionManager {
    
    private static final String PROPS_FILENAME = "connection.properties";
    private static String dbUser;
    private static String dbPassword;
    private static String dbURL;
    static {
        readDatabaseProperties();
        
        initDBDriver();
    }

      /**
   * Reads the properties of the Database, and fills in the dbUser, dbPassword and dbURL
   *
   * 
   * 
   */
  private static void readDatabaseProperties() {
    InputStream is = null;
    try {
      // Retrieve properties from connection.properties via the CLASSPATH
      // WEB-INF/classes is on the CLASSPATH
      is = ConnectionManager.class.getResourceAsStream(PROPS_FILENAME);
      Properties props = new Properties();
      props.load(is);

      // load database connection details
      String host = props.getProperty("db.host");
      String port = props.getProperty("db.port");
      String dbName = props.getProperty("db.name");
      dbUser = props.getProperty("db.user");
      String osName = System.getProperty("os.name");
            if (osName.equals("Linux")) {
                // in production environment, use aws.db.password
                dbPassword = props.getProperty("aws.db.password").trim();
            } else {
                // in local environment, use db.password
                dbPassword = props.getProperty("db.password").trim();
            }
      dbURL = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
      
    } catch (IOException ex) {
      // unable to load properties file
      String message = "Unable to load '" + PROPS_FILENAME + "'.";

      Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, message, ex);
      throw new RuntimeException(message, ex);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException ex) {
          Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING, "Unable to close connection.properties", ex);
        }
      }
    }
  }
  
    /**
   * Creates a new instance of JDBC Driver
   *
   * 
   * @throws SQLException if an error occurs when connecting
   */
  private static void initDBDriver() {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (Exception ex) {
      // unable to load properties file
      String message = "Unable to find JDBC driver for MySQL.";

      Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, message, ex);
      throw new RuntimeException(message, ex);
    }
  }

  /**
   * Gets a connection to the database
   *
   * @return the connection
   * @throws SQLException if an error occurs when connecting
   */
  public static Connection getConnection() throws SQLException {
    String message = "dbURL: " + dbURL
            + "  , dbUser: " + dbUser
            + "  , dbPassword: " + dbPassword;
    Logger.getLogger(ConnectionManager.class.getName()).log(Level.INFO, message);

    return DriverManager.getConnection(dbURL, dbUser, dbPassword);

  }

  /**
   * close the given connection, statement and resultset
   *
   * @param conn the connection object to be closed
   * @param stmt the statement object to be closed
   * @param rs the resultset object to be closed
   */
  public static void close(Connection conn, Statement stmt, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING,
              "Unable to close ResultSet", ex);
    }
    try {
      if (stmt != null) {
        stmt.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING,
              "Unable to close Statement", ex);
    }
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING,
              "Unable to close Connection", ex);
    }
  }
  
        /**
     *
     * @param conn connection
     * @param stmt statement
     */
    public static void close(Connection conn, Statement stmt) {
  
    try {
      if (stmt != null) {
        stmt.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING,
              "Unable to close Statement", ex);
    }
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING,
              "Unable to close Connection", ex);
    }
  }
}
