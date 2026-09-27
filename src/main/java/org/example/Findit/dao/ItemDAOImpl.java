package org.example.Findit.dao;

import org.example.Findit.DB_Connection;
import org.example.Findit.Model.Founditem;
import org.example.Findit.Model.Lostitem;
import org.example.Findit.Model.User;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ItemDAOImpl {
    public void addLostItem(Lostitem item) throws SQLException {
        String sql = "INSERT INTO lost_items (user_id, category, item_name, color, location, "
                + "item_date, description, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getUserId());
            stmt.setString(2, item.getCategory());
            stmt.setString(3, item.getItemName());
            stmt.setString(4, item.getColor());
            stmt.setString(5, item.getLocation());
            stmt.setDate(6, Date.valueOf(item.getItemDate()));
            stmt.setString(7, item.getDescription());
            stmt.setString(8, item.getStatus());
            stmt.executeUpdate();
        }
    }


    public void addFoundItem(Founditem item) throws SQLException {
        String sql = "INSERT INTO found_items (user_id, category, item_name, color, location, "
                + "item_date, description, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getUserId());
            stmt.setString(2, item.getCategory());
            stmt.setString(3, item.getItemName());
            stmt.setString(4, item.getColor());
            stmt.setString(5, item.getLocation());
            stmt.setDate(6, Date.valueOf(item.getItemDate()));
            stmt.setString(7, item.getDescription());
            stmt.setString(8, item.getStatus());
            stmt.executeUpdate();
        }
    }


    public Lostitem getLostItemById(int id) throws SQLException {
        String sql = "SELECT * FROM lost_items WHERE item_id = ?";
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToLostitem(rs);
                }
            }
        }
        return null;
    }


    public Founditem getFoundItemById(int id) throws SQLException {
        String sql = "SELECT * FROM found_items WHERE item_id = ?";
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToFounditem(rs);
                }
            }
        }
        return null;
    }


    public List<Lostitem> getAllLostItems() throws SQLException {
        String sql = "SELECT * FROM lost_items";
        List<Lostitem> items = new ArrayList<>();
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                items.add(mapRowToLostitem(rs));
            }
        }
        return items;
    }


    public List<Founditem> getAllFoundItems() throws SQLException {
        String sql = "SELECT * FROM found_items";
        List<Founditem> items = new ArrayList<>();
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                items.add(mapRowToFounditem(rs));
            }
        }
        return items;
    }


    public List<Lostitem> searchLostItems(String keyword) throws SQLException {
        String sql = "SELECT * FROM lost_items WHERE item_name LIKE ? OR description LIKE ?";
        List<Lostitem> items = new ArrayList<>();
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRowToLostitem(rs));
                }
            }
        }
        return items;
    }


    public List<Lostitem> searchLostItems(String keyword, String category) throws SQLException {
        String sql = "SELECT * FROM lost_items WHERE (item_name LIKE ? OR description LIKE ?) AND category = ?";
        List<Lostitem> items = new ArrayList<>();
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, category);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRowToLostitem(rs));
                }
            }
        }
        return items;
    }


    public List<Founditem> searchFoundItems(String keyword) throws SQLException {
        String sql = "SELECT * FROM found_items WHERE item_name LIKE ? OR description LIKE ?";
        List<Founditem> items = new ArrayList<>();
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRowToFounditem(rs));
                }
            }
        }
        return items;
    }


    public List<Founditem> searchFoundItems(String keyword, String category) throws SQLException {
        String sql = "SELECT * FROM found_items WHERE (item_name LIKE ? OR description LIKE ?) AND category = ?";
        List<Founditem> items = new ArrayList<>();
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, category);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRowToFounditem(rs));
                }
            }
        }
        return items;
    }


    public void updateItemStatus(int itemId, boolean isLostItem, String newStatus) throws SQLException {
        String table = isLostItem ? "lost_items" : "found_items";
        String sql = "UPDATE " + table + " SET status = ? WHERE item_id = ?";
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, itemId);
            stmt.executeUpdate();
        }
    }

    private Lostitem mapRowToLostitem(ResultSet rs) throws SQLException {
        return new Lostitem(
                rs.getInt("item_id"),
                rs.getInt("user_id"),
                rs.getString("category"),
                rs.getString("item_name"),
                rs.getString("color"),
                rs.getString("location"),
                rs.getDate("item_date").toLocalDate(),
                rs.getString("description"),
                rs.getString("status")
        );
    }

    private Founditem mapRowToFounditem(ResultSet rs) throws SQLException {
        return new Founditem(
                rs.getInt("item_id"),
                rs.getInt("user_id"),
                rs.getString("category"),
                rs.getString("item_name"),
                rs.getString("color"),
                rs.getString("location"),
                rs.getDate("item_date").toLocalDate(),
                rs.getString("description"),
                rs.getString("status")
        );
    }

    public List<Lostitem> getLostItemsByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM lost_items WHERE user_id = ?";
        List<Lostitem> items = new ArrayList<>();
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRowToLostitem(rs));
                }
            }
        }
        return items;
    }

    public List<Founditem> getFoundItemsByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM found_items WHERE user_id = ?";
        List<Founditem> items = new ArrayList<>();
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRowToFounditem(rs));
                }
            }
        }
        return items;
    }
}
