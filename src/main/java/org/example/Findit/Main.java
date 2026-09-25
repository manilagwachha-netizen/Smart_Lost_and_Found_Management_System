package org.example.Findit;


import org.example.Findit.Model.*;
import org.example.Findit.exception.ClaimNotFoundException;
import org.example.Findit.service.*;
import org.example.Findit.util.Dateutil;
import org.example.Findit.util.Inputvalidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Authservice authService = new Authservice();
    private static Itemservice itemService = new Itemservice();
    private static Adminservice adminService = new Adminservice();

    public static void main(String[] args) throws SQLException {
        new DB_Connection();
        Connection con = DB_Connection.getConnection();

        boolean running = true;

        while (running) {
            System.out.println("\n=== FindIt - Smart Lost & Found Management System ===");
            System.out.println("1. Register");
            System.out.println("2. Login as User");
            System.out.println("3. Login as Admin");
            System.out.println("4. Exit");

            int choice = Inputvalidator.readInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    loginAdmin();
                    break;
                case 4:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void registerUser() {
        System.out.println("\n--- Register ---");
        String name = Inputvalidator.readNonEmptyString(scanner, "Name: ");
        String email = Inputvalidator.readNonEmptyString(scanner, "Email: ");
        String password = Inputvalidator.readNonEmptyString(scanner, "Password: ");
        String phone = Inputvalidator.readNonEmptyString(scanner, "Phone: ");

        User newUser = new User(0, name, email, password, phone);

        try {
            boolean success = authService.registerUser(newUser);
            if (success) {
                System.out.println("Registration successful! You can now log in.");
            } else {
                System.out.println("Registration failed: that email is already registered.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void loginUser() {
        System.out.println("\n--- User Login ---");
        String email = Inputvalidator.readNonEmptyString(scanner, "Email: ");
        String password = Inputvalidator.readNonEmptyString(scanner, "Password: ");

        try {
            User user = authService.loginUser(email, password);
            if (user != null) {
                System.out.println("Login successful. Welcome, " + user.getName() + "!");
                userDashboard(user);
            } else {
                System.out.println("Invalid email or password.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void loginAdmin() {
        System.out.println("\n--- Admin Login ---");
        String email = Inputvalidator.readNonEmptyString(scanner, "Email: ");
        String password = Inputvalidator.readNonEmptyString(scanner, "Password: ");

        try {
            Admin admin = authService.loginAdmin(email, password);
            if (admin != null) {
                System.out.println("Admin login successful. Welcome, " + admin.getName() + "!");
                adminDashboard(admin);
            } else {
                System.out.println("Invalid email or password.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void userDashboard(User user) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n=== User Dashboard (" + user.getName() + ") ===");
            System.out.println("1. Report Lost Item");
            System.out.println("2. Report Found Item");
            System.out.println("3. Search Items");
            System.out.println("4. View Possible Matches");
            System.out.println("5. My Reports");
            System.out.println("6. Submit Claim");
            System.out.println("7. Notifications");
            System.out.println("8. Logout");

            int choice = Inputvalidator.readInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    reportLostItem(user);
                    break;
                case 2:
                    System.out.println("Report Found Item -- built in the next step.");
                    break;
                case 3:
                    System.out.println("Search Items -- built in a later step.");
                    break;
                case 4:
                    System.out.println("View Possible Matches -- built in a later step.");
                    break;
                case 5:
                    System.out.println("My Reports -- built in a later step.");
                    break;
                case 6:
                    System.out.println("Submit Claim -- built in a later step.");
                    break;
                case 7:
                    System.out.println("Notifications -- built in a later step.");
                    break;
                case 8:
                    loggedIn = false;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void reportLostItem(User user) {
        System.out.println("\n--- Report Lost Item ---");
        String category = Inputvalidator.readNonEmptyString(scanner, "Category (e.g. Electronics): ");
        String itemName = Inputvalidator.readNonEmptyString(scanner, "Item name/brand: ");
        String color = Inputvalidator.readNonEmptyString(scanner, "Color: ");
        String location = Inputvalidator.readNonEmptyString(scanner, "Last seen location: ");
        LocalDate itemDate = Dateutil.readDate(scanner, "Date lost");
        String description = Inputvalidator.readNonEmptyString(scanner, "Description: ");

        Lostitem item = new Lostitem(0, user.getId(), category, itemName, color,
                location, itemDate, description, "ACTIVE");

        try {
            itemService.reportLostItem(item);
            System.out.println("Lost item reported successfully!");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    private static void reportFoundItem(User user) {
        System.out.println("\n--- Report Found Item ---");
        String category = Inputvalidator.readNonEmptyString(scanner, "Category (e.g. Electronics): ");
        String itemName = Inputvalidator.readNonEmptyString(scanner, "Item name/brand: ");
        String color = Inputvalidator.readNonEmptyString(scanner, "Color: ");
        String location = Inputvalidator.readNonEmptyString(scanner, "Location found: ");
        LocalDate itemDate = Dateutil.readDate(scanner, "Date found");
        String description = Inputvalidator.readNonEmptyString(scanner, "Description: ");

        Founditem item = new Founditem(0, user.getId(), category, itemName, color,
                location, itemDate, description, "ACTIVE");

        try {
            itemService.reportFoundItem(item);
            System.out.println("Found item reported successfully!");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    private static void searchItems() {
        System.out.println("\n--- Search Items ---");
        System.out.println("1. Search Lost Items");
        System.out.println("2. Search Found Items");
        int type = Inputvalidator.readInt(scanner, "Choose search type: ");

        String keyword = Inputvalidator.readNonEmptyString(scanner, "Enter keyword: ");
        String filterChoice = Inputvalidator.readNonEmptyString(scanner, "Filter by category too? (y/n): ");

        try {
            if (type == 1) {
                List<Lostitem> results;
                if (filterChoice.equalsIgnoreCase("y")) {
                    String category = Inputvalidator.readNonEmptyString(scanner, "Category: ");
                    results = itemService.searchLostItems(keyword, category); // overload with category
                } else {
                    results = itemService.searchLostItems(keyword); // overload with keyword only
                }

                if (results.isEmpty()) {
                    System.out.println("No lost items matched your search.");
                } else {
                    for (Lostitem li : results) {
                        System.out.println("[ID:" + li.getId() + "] " + li.getSummary());
                    }
                }

            } else if (type == 2) {
                List<Founditem> results;
                if (filterChoice.equalsIgnoreCase("y")) {
                    String category = Inputvalidator.readNonEmptyString(scanner, "Category: ");
                    results = itemService.searchFoundItems(keyword, category);
                } else {
                    results = itemService.searchFoundItems(keyword);
                }

                if (results.isEmpty()) {
                    System.out.println("No found items matched your search.");
                } else {
                    for (Founditem fi : results) {
                        System.out.println("[ID:" + fi.getId() + "] " + fi.getSummary());
                    }
                }

            } else {
                System.out.println("Invalid choice.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void viewPossibleMatches(User user) {
        System.out.println("\n--- View Possible Matches (Smart Match) ---");
        int lostItemId = Inputvalidator.readInt(scanner,
                "Enter your Lost Item ID (check My Reports or Search to find it): ");

        try {
            Lostitem lostItem = itemService.getLostItemById(lostItemId);

            if (lostItem == null) {
                System.out.println("No lost item found with that ID.");
                return;
            }
            if (lostItem.getUserId() != user.getId()) {
                System.out.println("That lost item doesn't belong to you.");
                return;
            }

            List<Founditem> allFoundItems = itemService.getAllFoundItems();
            List<Matchresult> matches = Matchingservice.findMatches(lostItem, allFoundItems);

            if (matches.isEmpty()) {
                System.out.println("No found items exist yet to compare against.");
                return;
            }

            System.out.println("\nPossible matches for: " + lostItem.getSummary());
            for (Matchresult m : matches) {
                System.out.println("[Found ID:" + m.getMatchedItem().getId() + "] "
                        + m.getMatchedItem().getItemName() + " - "
                        + m.getMatchScore() + "% - " + m.getMatchLabel());
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void myReports(User user) {
        System.out.println("\n--- My Reports ---");

        try {
            List<Lostitem> myLostItems = itemService.getMyLostItems(user.getId());
            List<Founditem> myFoundItems = itemService.getMyFoundItems(user.getId());

            System.out.println("\nLost Items You've Reported:");
            if (myLostItems.isEmpty()) {
                System.out.println("  (none yet)");
            } else {
                for (Lostitem li : myLostItems) {
                    System.out.println("  [ID:" + li.getId() + "] " + li.getSummary()
                            + " | Status: " + li.getStatus());
                }
            }

            System.out.println("\nFound Items You've Reported:");
            if (myFoundItems.isEmpty()) {
                System.out.println("  (none yet)");
            } else {
                for (Founditem fi : myFoundItems) {
                    System.out.println("  [ID:" + fi.getId() + "] " + fi.getSummary()
                            + " | Status: " + fi.getStatus());
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void submitClaim(User user) {
        System.out.println("\n--- Submit Claim ---");
        int lostItemId = Inputvalidator.readInt(scanner, "Enter your Lost Item ID: ");
        int foundItemId = Inputvalidator.readInt(scanner, "Enter the Found Item ID you believe matches: ");

        try {
            Lostitem lostItem = itemService.getLostItemById(lostItemId);
            if (lostItem == null || lostItem.getUserId() != user.getId()) {
                System.out.println("Invalid lost item ID, or it doesn't belong to you.");
                return;
            }

            Founditem foundItem = itemService.getFoundItemById(foundItemId);
            if (foundItem == null) {
                System.out.println("No found item exists with that ID.");
                return;
            }

            double matchScore = Matchingservice.calculateMatchScore(lostItem, foundItem);

            Claimservice.submitClaim(lostItemId, foundItemId, user.getId(), matchScore);
            System.out.println("Claim submitted successfully! Match score: " + matchScore
                    + "%. Status: PENDING. An admin will review it.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    private static void viewNotifications(User user) {
        System.out.println("\n--- Notifications ---");

        try {
            List<Notification> notifications = Notificationservice.getNotificationsByUser(user.getId());

            if (notifications.isEmpty()) {
                System.out.println("You have no notifications.");
                return;
            }

            for (Notification n : notifications) {
                String readTag = n.isRead() ? "[Read]" : "[Unread]";
                System.out.println(readTag + " [ID:" + n.getNotificationId() + "] " + n.getMessage());
            }

            String markChoice = Inputvalidator.readNonEmptyString(scanner,
                    "Mark a notification as read? Enter its ID, or 'n' to skip: ");

            if (!markChoice.equalsIgnoreCase("n")) {
                try {
                    int notifId = Integer.parseInt(markChoice);
                    Notificationservice.markAsRead(notifId);
                    System.out.println("Marked as read.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID, skipping.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    //ADMIN DASHBOARD

    private static void adminDashboard(Admin admin) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n=== Admin Dashboard (" + admin.getName() + ") ===");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Lost/Found Items");
            System.out.println("3. Manage Claims");
            System.out.println("4. View Statistics");
            System.out.println("5. Logout");

            int choice = Inputvalidator.readInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    manageUsers();
                    break;
                case 2:
                    manageItems();
                    break;
                case 3:
                    manageClaims();
                    break;
                case 4:
                    viewStatistics();
                    break;
                case 5:
                    loggedIn = false;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageUsers() {
        System.out.println("\n--- All Users ---");
        try {
            List<User> users = adminService.viewAllUsers();
            if (users.isEmpty()) {
                System.out.println("No users found.");
                return;
            }
            for (User u : users) {
                System.out.println("[ID:" + u.getId() + "] " + u.getName()
                        + " - " + u.getEmail() + " - " + u.getPhone());
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void manageItems() {
        try {
            System.out.println("\n--- Lost Items ---");
            List<Lostitem> lostItems = adminService.viewAllLostItems();
            if (lostItems.isEmpty()) {
                System.out.println("  (none)");
            } else {
                for (Lostitem li : lostItems) {
                    System.out.println("  [ID:" + li.getId() + "] " + li.getSummary()
                            + " | Status: " + li.getStatus());
                }
            }

            System.out.println("\n--- Found Items ---");
            List<Founditem> foundItems = adminService.viewAllFoundItems();
            if (foundItems.isEmpty()) {
                System.out.println("  (none)");
            } else {
                for (Founditem fi : foundItems) {
                    System.out.println("  [ID:" + fi.getId() + "] " + fi.getSummary()
                            + " | Status: " + fi.getStatus());
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void manageClaims() {
        System.out.println("\n--- Manage Claims ---");

        try {
            List<Claim> claims = adminService.viewAllClaims();
            if (claims.isEmpty()) {
                System.out.println("No claims found.");
                return;
            }
            for (Claim c : claims) {
                System.out.println("[ID:" + c.getClaimId() + "] Lost#" + c.getLostItemId()
                        + " <-> Found#" + c.getFoundItemId() + " | Claimant User#" + c.getClaimantId()
                        + " | Score: " + c.getMatchScore() + "% | Status: " + c.getStatus());
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return;
        }

        String action = Inputvalidator.readNonEmptyString(scanner,
                "Enter Claim ID to review, or 'n' to go back: ");
        if (action.equalsIgnoreCase("n")) {
            return;
        }

        try {
            int claimId = Integer.parseInt(action);
            Claim claim = Claimservice.getClaimById(claimId); // throws ClaimNotFoundException if missing

            String decision = Inputvalidator.readNonEmptyString(scanner, "Approve or Reject? (a/r): ");

            if (decision.equalsIgnoreCase("a")) {
                adminService.approveClaim(claimId);
                Notificationservice.sendNotification(claim.getClaimantId(),
                        "Your claim #" + claimId + " has been approved!");
                System.out.println("Claim approved and user notified.");
            } else if (decision.equalsIgnoreCase("r")) {
                adminService.rejectClaim(claimId);
                Notificationservice.sendNotification(claim.getClaimantId(),
                        "Your claim #" + claimId + " has been rejected.");
                System.out.println("Claim rejected and user notified.");
            } else {
                System.out.println("Invalid decision.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid claim ID.");
        } catch (ClaimNotFoundException e) {
            System.out.println("Claim not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void viewStatistics() {
        System.out.println("\n--- Statistics ---");
        try {
            Map<String, Integer> stats = adminService.getStatistics();
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}