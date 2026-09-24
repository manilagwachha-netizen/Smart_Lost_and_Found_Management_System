package org.example.Findit.Model;
import java.time.LocalDate;

public abstract class Item {
    private int id;
    private int userId;       // who reported this item
    private String category;
    private String itemName;
    private String color;
    private String location;
    private LocalDate itemDate;
    private String description;
    private String status;    // ACTIVE, CLAIMED, CLOSED

    public Item(int id, int userId, String category, String itemName, String color,
                String location, LocalDate itemDate, String description, String status) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.itemName = itemName;
        this.color = color;
        this.location = location;
        this.itemDate = itemDate;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getItemDate() {
        return itemDate;
    }

    public void setItemDate(LocalDate itemDate) {
        this.itemDate = itemDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract String getSummary();

}

