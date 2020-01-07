package DAO;

import Entity.User;
import Utility.BCrypt;
import Utility.CustomDate;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private static Connection conn;
    private static PreparedStatement stmt;
    private static ResultSet rs;

    public static ArrayList<User> getUsers() {
        ArrayList<User> userMap = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select * from users");
            rs = stmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String accountType = rs.getString("accountType");
                String email = rs.getString("email");
                String creationDate = rs.getString("creationDate");
                userMap.add(new User(username, password, accountType, email, creationDate));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return userMap;
    }

    /**
     * Returns the user with the username.
     *
     * @param username the input username to find the user in the database
     * @return the user with the given input username that is found in the
     * database, otherwise return null
     */
    public static User getUser(String username) {
        User user = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"), rs.getString("accountType"), rs.getString("email"), rs.getString("creationDate"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return user;
    }

    /**
     * Returns the user with the username.
     *
     * @param username the input username to find the user in the database
     * @return the user with the given input username that is found in the
     * database, otherwise return null
     */
    public static User getUserByEmail(String email) {
        User user = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"), rs.getString("accountType"), rs.getString("email"), rs.getString("creationDate"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return user;
    }

    public static String getUsernameByEmail(String email) {
        String username = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            while (rs.next()) {
                username = rs.getString("username");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return username;
    }

    public static boolean validateUserToken(String email, String token, String currentTime) {
        boolean validated = false;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select * from users where token = ? and email = ? and tokenExpiration >= ?");
            stmt.setString(1, token);
            stmt.setString(2, email);
            stmt.setString(3, currentTime);
            rs = stmt.executeQuery();
            while (rs.next()) {
                validated = true;
                System.out.println("validated");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return validated;
    }

    public static boolean updateUserToken(User user, String token, String expiry) {
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("update users set token = ?, tokenExpiration = ? where username = ?");
            stmt.setString(1, token);
            stmt.setString(2, expiry);
            stmt.setString(3, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return true;
    }

    public static boolean createUser(String username, String password, String accountType, String email) {
        if (getUser(username) != null) {
            return false;
        }
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("insert into users values(?, ?, ?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            stmt.setString(3, accountType);
            stmt.setString(4, email);
            stmt.setString(5, CustomDate.getDateTime());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return true;
    }

    public static boolean deleteUser(String username) {
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("delete from users where username = ?");
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return true;
    }

    public static boolean changePasswordbyEmail(String email, String password) {
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("update users set password = ? where email = ?");
            stmt.setString(1, BCrypt.hashpw(password, BCrypt.gensalt()));
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return true;
    }

    public static boolean changePassword(String username, String password) {
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("update users set password = ? where username = ?");
            stmt.setString(1, BCrypt.hashpw(password, BCrypt.gensalt()));
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return true;
    }
}
