package org.example.Findit.service;

import org.example.Findit.Model.Founditem;
import org.example.Findit.Model.Lostitem;
import org.example.Findit.Model.Matchresult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matchingservice {

    private Map<String, Integer> weights;

    public Matchingservice() {
        weights = new HashMap<>();
        weights.put("category", 20);
        weights.put("itemName", 25);
        weights.put("color", 15);
        weights.put("location", 15);
        weights.put("date", 10);
        weights.put("description", 15);
    }

    public List<Matchresult> findMatches(Lostitem lostItem, List<Founditem> foundItems) {
        List<Matchresult> results = new ArrayList<>();

        for (Founditem found : foundItems) {
            double score = calculateScore(lostItem, found);
            String label = classify(score);
            results.add(new Matchresult(found, score, label));
        }

        results.sort(new Comparator<Matchresult>() {
            @Override
            public int compare(Matchresult a, Matchresult b) {
                return Double.compare(b.getMatchScore(), a.getMatchScore());
            }
        });

        return results;
    }

    public double calculateMatchScore(Lostitem lost, Founditem found) {
        return calculateScore(lost, found);
    }

    private double calculateScore(Lostitem lost, Founditem found) {
        double score = 0;

        if (equalsIgnoreCaseSafe(lost.getCategory(), found.getCategory())) {
            score += weights.get("category");
        }
        if (containsIgnoreCaseSafe(lost.getItemName(), found.getItemName())) {
            score += weights.get("itemName");
        }
        if (equalsIgnoreCaseSafe(lost.getColor(), found.getColor())) {
            score += weights.get("color");
        }
        if (equalsIgnoreCaseSafe(lost.getLocation(), found.getLocation())) {
            score += weights.get("location");
        }
        if (lost.getItemDate() != null && found.getItemDate() != null) {
            long daysBetween = Math.abs(
                    lost.getItemDate().toEpochDay() - found.getItemDate().toEpochDay());
            if (daysBetween <= 3) {
                score += weights.get("date");
            }
        }
        if (containsIgnoreCaseSafe(lost.getDescription(), found.getDescription())) {
            score += weights.get("description");
        }

        return score;
    }

    private String classify(double score) {
        if (score >= 90) {
            return "Very Strong Match";
        } else if (score >= 75) {
            return "Strong Match";
        } else if (score >= 50) {
            return "Possible Match";
        } else {
            return "Weak Match";
        }
    }

    private boolean equalsIgnoreCaseSafe(String a, String b) {
        if (a == null || b == null) return false;
        return a.trim().equalsIgnoreCase(b.trim());
    }

    private boolean containsIgnoreCaseSafe(String a, String b) {
        if (a == null || b == null) return false;
        return a.toLowerCase().contains(b.toLowerCase())
                || b.toLowerCase().contains(a.toLowerCase());
    }
}