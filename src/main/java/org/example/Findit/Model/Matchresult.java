package org.example.Findit.Model;

public class Matchresult {
    private Founditem matchedItem;
    private double matchScore;
    private String matchLabel;

    public Matchresult(Founditem matchedItem, double matchScore, String matchLabel) {
        this.matchedItem = matchedItem;
        this.matchScore = matchScore;
        this.matchLabel = matchLabel;
    }

    public Founditem getMatchedItem() {
        return matchedItem;
    }

    public void setMatchedItem(Founditem matchedItem) {
        this.matchedItem = matchedItem;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }

    public String getMatchLabel() {
        return matchLabel;
    }

    public void setMatchLabel(String matchLabel) {
        this.matchLabel = matchLabel;
    }
}
