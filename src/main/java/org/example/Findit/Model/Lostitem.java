package org.example.Findit.Model;

import java.time.LocalDate;



public class Lostitem extends Item {
    public Lostitem(int id, int userId, String category, String itemName, String color,
                    String location, LocalDate itemDate, String description, String status) {
        super(id, userId, category, itemName, color, location, itemDate, description, status);
    }

    @Override
    public String getSummary() {
        return "Lost: " + getItemName() + " (" + getCategory() + ") - last seen at "
                + getLocation() + " on " + getItemDate();
    }
}
