package org.example.Findit.Model;

public class Claim {
    private int claimId;
    private int lostItemId;
    private int foundItemId;
    private int claimantId;   // user_id of the person filing the claim
    private double matchScore;
    private String status;    // PENDING, APPROVED, REJECTED

    public Claim(int claimId, int lostItemId, int foundItemId, int claimantId,
                 double matchScore, String status) {
        this.claimId = claimId;
        this.lostItemId = lostItemId;
        this.foundItemId = foundItemId;
        this.claimantId = claimantId;
        this.matchScore = matchScore;
        this.status = status;
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public int getLostItemId() {
        return lostItemId;
    }

    public void setLostItemId(int lostItemId) {
        this.lostItemId = lostItemId;
    }

    public int getFoundItemId() {
        return foundItemId;
    }

    public void setFoundItemId(int foundItemId) {
        this.foundItemId = foundItemId;
    }

    public int getClaimantId() {
        return claimantId;
    }

    public void setClaimantId(int claimantId) {
        this.claimantId = claimantId;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
