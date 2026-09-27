package org.example.Findit.service;

import org.example.Findit.DB_Connection;
import org.example.Findit.Model.Admin;
import org.example.Findit.Model.User;
import org.example.Findit.dao.UserDAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authservice {
    private UserDAOImpl userDAO;

    public Authservice() {
        this.userDAO = new UserDAOImpl();
    }


    public boolean registerUser(User user) throws SQLException {
        User existing = userDAO.getUserByEmail(user.getEmail());
        if (existing != null) {
            return false; // email already registered
        }
        userDAO.addUser(user);
        return true;
    }

    public User loginUser(String email, String password) throws SQLException {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


    public Admin loginAdmin(String email, String password) throws SQLException {
        String sql = "SELECT * FROM admins WHERE email = ? AND password = ?";
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                            rs.getInt("admin_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }
        }
        return null;
    }
}
