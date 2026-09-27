package org.example.Findit.service;

import org.example.Findit.dao.ClaimDAOImpl;
import org.example.Findit.dao.ItemDAOImpl;
import org.example.Findit.Model.Claim;
import org.example.Findit.exception.ClaimNotFoundException;

import java.sql.SQLException;
import java.util.List;

public class Claimservice {

    private ClaimDAOImpl claimDAO;
    private ItemDAOImpl itemDAO;

    public Claimservice() {
        this.claimDAO = new ClaimDAOImpl();
        this.itemDAO = new ItemDAOImpl();
    }

    public void submitClaim(int lostItemId, int foundItemId, int claimantId, double matchScore) throws SQLException {
        Claim claim = new Claim(0, lostItemId, foundItemId, claimantId, matchScore, "PENDING");
        claimDAO.addClaim(claim);
    }

    public Claim getClaimById(int claimId) throws SQLException, ClaimNotFoundException {
        return claimDAO.getClaimById(claimId);
    }

    public List<Claim> getAllClaims() throws SQLException {
        return claimDAO.getAllClaims();
    }

    public List<Claim> getPendingClaims() throws SQLException {
        return claimDAO.getClaimsByStatus("PENDING");
    }

    public List<Claim> getClaimsByUser(int userId) throws SQLException {
        return claimDAO.getClaimsByUser(userId);
    }

    public void approveClaim(int claimId) throws SQLException, ClaimNotFoundException {
        Claim claim = claimDAO.getClaimById(claimId);
        claimDAO.updateClaimStatus(claimId, "APPROVED");
        itemDAO.updateItemStatus(claim.getLostItemId(), true, "CLAIMED");
        itemDAO.updateItemStatus(claim.getFoundItemId(), false, "CLAIMED");
    }

    public void rejectClaim(int claimId) throws SQLException, ClaimNotFoundException {
        claimDAO.getClaimById(claimId);
        claimDAO.updateClaimStatus(claimId, "REJECTED");
    }
}