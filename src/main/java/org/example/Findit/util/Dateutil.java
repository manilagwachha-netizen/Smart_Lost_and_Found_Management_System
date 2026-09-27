package org.example.Findit.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Dateutil {
    public static LocalDate readDate(Scanner scanner, String label) {
        while (true) {
            System.out.print(label + " (format: yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Example: 2026-09-24. Try again.");
            }
        }
    }
}
