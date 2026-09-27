package org.example.Findit.service;

import org.example.Findit.Model.Claim;
import org.example.Findit.Model.Founditem;
import org.example.Findit.Model.Lostitem;
import org.example.Findit.Model.User;
import org.example.Findit.dao.ClaimDAOImpl;
import org.example.Findit.dao.ItemDAOImpl;
import org.example.Findit.dao.UserDAOImpl;
import org.example.Findit.exception.ClaimNotFoundException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adminservice {
    private UserDAOImpl userDAO;
    private ItemDAOImpl itemDAO;
    private ClaimDAOImpl claimDAO;
    private Claimservice claimService;

    public Adminservice() {
        this.userDAO = new UserDAOImpl();
        this.itemDAO = new ItemDAOImpl();
        this.claimDAO = new ClaimDAOImpl();
        this.claimService = new Claimservice();
    }

    public List<User> viewAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    public List<Lostitem> viewAllLostItems() throws SQLException {
        return itemDAO.getAllLostItems();
    }

    public List<Founditem> viewAllFoundItems() throws SQLException {
        return itemDAO.getAllFoundItems();
    }

    public List<Claim> viewAllClaims() throws SQLException {
        return claimDAO.getAllClaims();
    }

    public void approveClaim(int claimId) throws SQLException, ClaimNotFoundException {
        claimService.approveClaim(claimId);
    }

    public void rejectClaim(int claimId) throws SQLException, ClaimNotFoundException {
        claimService.rejectClaim(claimId);
    }

    public Map<String, Integer> getStatistics() throws SQLException {
        Map<String, Integer> stats = new HashMap<>();

        stats.put("Total Users", userDAO.getAllUsers().size());
        stats.put("Total Lost Items", itemDAO.getAllLostItems().size());
        stats.put("Total Found Items", itemDAO.getAllFoundItems().size());
        stats.put("Total Claims", claimDAO.getAllClaims().size());
        stats.put("Pending Claims", claimDAO.getClaimsByStatus("PENDING").size());
        stats.put("Approved Claims", claimDAO.getClaimsByStatus("APPROVED").size());
        stats.put("Rejected Claims", claimDAO.getClaimsByStatus("REJECTED").size());

        return stats;
    }
}