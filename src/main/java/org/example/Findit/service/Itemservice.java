package org.example.Findit.service;

import org.example.Findit.Model.Founditem;
import org.example.Findit.Model.Lostitem;
import org.example.Findit.dao.ItemDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class Itemservice {
    private ItemDAOImpl itemDAO;

    public Itemservice() {
        this.itemDAO = new ItemDAOImpl();
    }

    public void reportLostItem(Lostitem item) throws SQLException {
        itemDAO.addLostItem(item);
    }

    public void reportFoundItem(Founditem item) throws SQLException {
        itemDAO.addFoundItem(item);
    }

    public Lostitem getLostItemById(int id) throws SQLException {
        return itemDAO.getLostItemById(id);
    }

    public Founditem getFoundItemById(int id) throws SQLException {
        return itemDAO.getFoundItemById(id);
    }

    public List<Lostitem> getAllLostItems() throws SQLException {
        return itemDAO.getAllLostItems();
    }

    public List<Founditem> getAllFoundItems() throws SQLException {
        return itemDAO.getAllFoundItems();
    }

    // Overload 1: search by keyword only
    public List<Lostitem> searchLostItems(String keyword) throws SQLException {
        return itemDAO.searchLostItems(keyword);
    }

    // Overload 2: search by keyword AND category
    public List<Lostitem> searchLostItems(String keyword, String category) throws SQLException {
        return itemDAO.searchLostItems(keyword, category);
    }

    public List<Founditem> searchFoundItems(String keyword) throws SQLException {
        return itemDAO.searchFoundItems(keyword);
    }

    public List<Founditem> searchFoundItems(String keyword, String category) throws SQLException {
        return itemDAO.searchFoundItems(keyword, category);
    }

    public void markItemAsClaimed(int itemId, boolean isLostItem) throws SQLException {
        itemDAO.updateItemStatus(itemId, isLostItem, "CLAIMED");
    }

    public List<Lostitem> getMyLostItems(int userId) throws SQLException {
        return itemDAO.getLostItemsByUser(userId);
    }

    public List<Founditem> getMyFoundItems(int userId) throws SQLException {
        return itemDAO.getFoundItemsByUser(userId);
    }
}
