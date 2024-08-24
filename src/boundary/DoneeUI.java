/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.*;
import java.util.Scanner;
import adt.*;
import dao.*;

/**
 *
 * @author leezh
 */
public class DoneeUI {

    private DoneeDAO doneeDAO;
    Scanner scanner = new Scanner(System.in);

    public int menu() {
        System.out.println("\nDonee Management System");
        System.out.println("1. Add new donee");
        System.out.println("2. Remove donee");
        System.out.println("3. Update donee details");
        System.out.println("4. Search donee details");
        System.out.println("5. List donee with all the donation made");
        System.out.println("6. Filter donee based on criteria");
        System.out.println("7. Generate summary report");
        System.out.println("Enter choice > ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public Donee addDonee() {

        System.out.println("Please enter the donee details.");

        String newId = doneeDAO.generateNewId();

        System.out.print("Enter donee name: ");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("Name cannot be empty. Please enter the donee name: ");
            name = scanner.nextLine().trim();
        }

        System.out.println("Select the donee type:");
        System.out.println("1. Individual");
        System.out.println("2. Organisation");
        System.out.println("3. Family");
        String type = "";
        int choice = -1;
        while (choice < 1 || choice > 3) {
            System.out.print("Enter choice (1, 2, or 3): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        type = "Individual";
                        break;
                    case 2:
                        type = "Organisation";
                        break;
                    case 3:
                        type = "Family";
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid choice.");
                        type = "Unknown";
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        System.out.print("Please enter the contact number (e.g. 012-3456789): ");
        String contactNo = scanner.nextLine().trim();
        while (!contactNo.matches("\\d{3}-\\d{7}")) {
            System.out.print("Invalid format. Please enter the contact number (e.g. 012-3456789): ");
            contactNo = scanner.nextLine().trim();
        }

        ListInterface<Donation> donations = new ArrayList<>();
        boolean moreDonations = true;

        while (moreDonations) {
            System.out.println("Add donation details:");

            System.out.print("Enter donation description: ");
            String description = scanner.nextLine().trim();
            while (description.isEmpty()) {
                System.out.print("Description cannot be empty. Enter donation description: ");
                description = scanner.nextLine().trim();
            }

            double cashAmount = -1;
            while (cashAmount < 0) {
                System.out.print("Enter cash amount: ");
                if (scanner.hasNextDouble()) {
                    cashAmount = scanner.nextDouble();
                    scanner.nextLine();
                    if (cashAmount < 0) {
                        System.out.println("Cash amount cannot be negative. Please enter a valid amount.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid cash amount.");
                    scanner.next();
                }

            }

            ListInterface<DonatedItem> donatedItems = new ArrayList<>();
            boolean moreItems = true;

            while (moreItems) {
                System.out.println("Add donated item details:");

                System.out.print("Enter item name: ");
                String itemName = scanner.nextLine().trim();
                while (itemName.isEmpty()) {
                    System.out.print("Item name cannot be empty. Enter item name: ");
                    itemName = scanner.nextLine().trim();
                }

                int quantity = -1;
                while (quantity <= 0) {
                    System.out.print("Enter item quantity: ");
                    if (scanner.hasNextInt()) {
                        quantity = scanner.nextInt();
                        scanner.nextLine();
                        if (quantity <= 0) {
                            System.out.println("Quantity must be positive. Please enter a valid quantity.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid quantity.");
                        scanner.next();
                    }

                    DonatedItem donatedItem = new DonatedItem(itemName, quantity);
                    donatedItems.add(donatedItem);

                    System.out.print("Do you want to add another item? (yes/no): ");
                    String response = scanner.nextLine();
                    moreItems = response.equalsIgnoreCase("yes");
                }

                Donation donation = new Donation(description, cashAmount);
                donation.setItems(donatedItems);
                donations.add(donation);

                System.out.print("Do you want to add another donation? (yes/no): ");
                String response = scanner.nextLine().trim();
                moreDonations = response.equalsIgnoreCase("yes");
            }

        }

        Donee donee = new Donee(newId, name, type, contactNo);
        donee.setDonations((ArrayList<Donation>) donations);

        return donee;
    }
}
