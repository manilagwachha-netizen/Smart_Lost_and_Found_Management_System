package org.example.Findit.dao;

import org.example.Findit.DB_Connection;
import org.example.Findit.Model.Claim;
import org.example.Findit.exception.ClaimNotFoundException;

import java.sql.*;
import java.sql.Date;
import java.util.*;


public class ClaimDAOImpl {
    public void addClaim(Claim claim) throws SQLException {
        String sql = "INSERT INTO claims (lost_item_id, found_item_id, claimant_id, match_score, status) "
                + "VALUES (?, ?, ?, ?, ?)";
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, claim.getLostItemId());
            stmt.setInt(2, claim.getFoundItemId());
            stmt.setInt(3, claim.getClaimantId());
            stmt.setDouble(4, claim.getMatchScore());
            stmt.setString(5, claim.getStatus());
            stmt.executeUpdate();
        }
    }


    public Claim getClaimById(int claimId) throws SQLException, ClaimNotFoundException {
        String sql = "SELECT * FROM claims WHERE claim_id = ?";
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, claimId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToClaim(rs);
                }
            }
        }

        throw new ClaimNotFoundException("No claim found with ID: " + claimId);
    }


    public List<Claim> getAllClaims() throws SQLException {
        String sql = "SELECT * FROM claims";
        List<Claim> claims = new ArrayList<>();
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                claims.add(mapRowToClaim(rs));
            }
        }
        return claims;
    }


    public List<Claim> getClaimsByStatus(String status) throws SQLException {
        String sql = "SELECT * FROM claims WHERE status = ?";
        List<Claim> claims = new ArrayList<>();
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    claims.add(mapRowToClaim(rs));
                }
            }
        }
        return claims;
    }


    public List<Claim> getClaimsByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM claims WHERE claimant_id = ?";
        List<Claim> claims = new ArrayList<>();
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    claims.add(mapRowToClaim(rs));
                }
            }
        }
        return claims;
    }


    public void updateClaimStatus(int claimId, String newStatus) throws SQLException {
        String sql = "UPDATE claims SET status = ? WHERE claim_id = ?";
        Connection conn = DB_Connection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, claimId);
            stmt.executeUpdate();
        }
    }

    private Claim mapRowToClaim(ResultSet rs) throws SQLException {
        return new Claim(
                rs.getInt("claim_id"),
                rs.getInt("lost_item_id"),
                rs.getInt("found_item_id"),
                rs.getInt("claimant_id"),
                rs.getDouble("match_score"),
                rs.getString("status")
        );
    }
}
