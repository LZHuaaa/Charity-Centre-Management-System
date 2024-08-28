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

    private DoneeDAO doneeDAO = new DoneeDAO();
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
        System.out.print("Enter choice > ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public Donee addDonee(ListInterface<Donee> doneeList) {
        System.out.println("---Please enter the donee details---");

        String newId = doneeDAO.generateNewId(doneeList);
        System.out.println("Donee ID: " + newId);

        System.out.print("Enter donee name: ");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("\nName cannot be empty. Please enter the donee name: ");
            name = scanner.nextLine().trim();
        }

        System.out.println("\nSelect the donee type:");
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
                        System.out.println("\nInvalid choice. Please enter a valid choice.");
                }
            } else {
                System.out.println("\nInvalid input. Please enter a number.");
                scanner.next();
            }
        }

        System.out.print("\nPlease enter the contact number (e.g. 012-3456789): ");
        String contactNo = scanner.nextLine().trim();
        while (!contactNo.matches("\\d{3}-\\d{7}")) {
            System.out.print("\nInvalid format. Please enter the contact number (e.g. 012-3456789): ");
            contactNo = scanner.nextLine().trim();
        }

        // Now ask if they want to add a donation
        String addDonationResponse;
        do {
            System.out.print("\nDo you want to add a donation? (y/n): ");
            addDonationResponse = scanner.nextLine().trim();

            if (!addDonationResponse.equalsIgnoreCase("y") && !addDonationResponse.equalsIgnoreCase("n")) {
                System.out.println("Invalid choice. Please select again.");
            }

        } while (!addDonationResponse.equalsIgnoreCase("y") && !addDonationResponse.equalsIgnoreCase("n"));

        ListInterface<Donation> donations = new ArrayList<>();

        if (addDonationResponse.equalsIgnoreCase("y")) {
            boolean moreDonations = true;

            while (moreDonations) {
                System.out.println("\n---Add donation details---");

                System.out.print("Enter donation description: ");
                String description = scanner.nextLine().trim();
                while (description.isEmpty()) {
                    System.out.print("\nDescription cannot be empty. Enter donation description: ");
                    description = scanner.nextLine().trim();
                }

                double cashAmount = -1;
                while (cashAmount < 0) {
                    System.out.print("Enter cash amount: RM");
                    if (scanner.hasNextDouble()) {
                        cashAmount = scanner.nextDouble();
                        scanner.nextLine();
                        if (cashAmount < 0) {
                            System.out.println("\nCash amount cannot be negative. Please enter a valid amount.");
                        }
                    } else {
                        System.out.println("\nInvalid input. Please enter a valid cash amount.");
                        scanner.next();
                    }
                }

                // Ask if they want to add donated items
                String addItemsResponse;
                do {
                    System.out.print("\nDo you want to add donated items? (y/n): ");
                    addItemsResponse = scanner.nextLine().trim();

                    if (!addItemsResponse.equalsIgnoreCase("y") && !addItemsResponse.equalsIgnoreCase("n")) {
                        System.out.println("Invalid choice. Please select again.");
                    }

                } while (!addItemsResponse.equalsIgnoreCase("y") && !addItemsResponse.equalsIgnoreCase("n"));

                ListInterface<DonatedItem> donatedItems = new ArrayList<>();

                if (addItemsResponse.equalsIgnoreCase("y")) {
                    boolean moreItems = true;

                    while (moreItems) {
                        System.out.println("\n---Add donated item details---");

                        System.out.print("Enter item name: ");
                        String itemName = scanner.nextLine().trim();
                        while (itemName.isEmpty()) {
                            System.out.print("\nItem name cannot be empty. Enter item name: ");
                            itemName = scanner.nextLine().trim();
                        }

                        int quantity = -1;
                        while (quantity <= 0) {
                            System.out.print("Enter item quantity: ");
                            if (scanner.hasNextInt()) {
                                quantity = scanner.nextInt();
                                scanner.nextLine();
                                if (quantity <= 0) {
                                    System.out.println("\nQuantity must be positive. Please enter a valid quantity.");
                                }
                            } else {
                                System.out.println("\nInvalid input. Please enter a valid quantity.");
                                scanner.next();
                            }
                        }

                        DonatedItem donatedItem = new DonatedItem(itemName, quantity);
                        donatedItems.add(donatedItem);

                        String response;
                        do {
                            System.out.print("\nDo you want to add another item? (y/n): ");
                            response = scanner.nextLine().trim();

                            if (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
                                System.out.println("Invalid choice. Please select again.");
                            }

                        } while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n"));

                        moreItems = response.equalsIgnoreCase("y");
                    }
                }

                Donation donation = new Donation(description, cashAmount);
                donation.setItems(donatedItems);
                donations.add(donation);

                String response;
                do {
                    System.out.print("\nDo you want to add another donation? (y/n): ");
                    response = scanner.nextLine().trim();
                    moreDonations = response.equalsIgnoreCase("y");

                    if (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
                        System.out.println("Invalid choice. Please select again");
                    }
                } while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n"));
            }

        }

        Donee newDonee = new Donee(newId, name, type, contactNo);
        newDonee.setDonations((ArrayList<Donation>) donations);
        doneeList.add(newDonee);

        System.out.println("\nDonee added successfully!");

        return newDonee;
    }

    public void searchDonee(HashMap<String, Donee> doneeMap) {
        System.out.print("Enter Donee ID to search: ");
        String doneeId = scanner.nextLine();
        Donee donee = doneeMap.get(doneeId);

        if (donee != null) {
            System.out.println("\n--Donee Found--");
            System.out.println("ID: " + donee.getId());
            System.out.println("Name: " + donee.getName());
            System.out.println("Type: " + donee.getType());
            System.out.println("Contact Number: " + donee.getContactNo());

            // Ask user if they want to display donation details
            String response;
            do {
                System.out.print("\nWould you like to display donations for this donee? (y/n): ");
                response = scanner.nextLine().trim().toLowerCase();

                if (response.equals("y")) {
                    displayDonations(donee);

                } else if (!response.equals("n")) {
                    System.out.println("Invalid choice. Please select again.");
                }

            } while (!response.equals("n") && !response.equals("y"));

        } else {
            System.out.println("No donee found with ID: " + doneeId);
        }
    }

    private void displayDonations(Donee donee) {

        ListInterface<Donation> donations = donee.getDonations();

        if (donations != null && !donations.isEmpty()) {
            System.out.println("\n|------------------------------------------------------------------------------------------|");
            System.out.println("|                                     Donations                                            |");
            System.out.println("|------------------------------------------------------------------------------------------|");
            System.out.printf("| %-30s | %-18s | %-20s | %-10s  |\n", "Description", "Cash Amount (RM)", "Item Name", "Quantity");
            System.out.println("|------------------------------------------------------------------------------------------|");

            for (int j = 0; j < donations.size(); j++) {
                Donation donation = donations.getEntry(j);
                ListInterface<DonatedItem> donatedItems = donation.getItems();

                if (donatedItems != null && !donatedItems.isEmpty()) {
                    for (int k = 0; k < donatedItems.size(); k++) {
                        DonatedItem item = donatedItems.getEntry(k);
                        System.out.printf("| %-30s | %-18.2f | %-20s | %-10d  |\n",
                                donation.getDescription(),
                                donation.getCashAmount(),
                                item.getName(),
                                item.getQuantity()
                        );
                    }
                } else {
                    System.out.printf("| %-30s | %-18.2f | %-20s | %-10s  |\n",
                            donation.getDescription(),
                            donation.getCashAmount(),
                            "N/A",
                            "N/A"
                    );
                }
            }
            System.out.println("|------------------------------------------------------------------------------------------|");
        } else {
            System.out.println("No donations available.");
        }
    }

    public void listDoneesWithDonations(ListInterface<Donee> doneeList) {
        if (doneeList.isEmpty()) {
            System.out.println("No donees available.");
        } else {
            for (int i = 0; i < doneeList.size(); i++) {
                Donee donee = doneeList.getEntry(i);
                System.out.println("Donee ID: " + donee.getId());
                System.out.println("Name: " + donee.getName());
                System.out.println("Type: " + donee.getType());
                System.out.println("Contact Number: " + donee.getContactNo());

                ListInterface<Donation> donations = donee.getDonations();
                if (donations != null && !donations.isEmpty()) {
                    System.out.println("\n|------------------------------------------------------------------------------------------|");
                    System.out.println("|                                     Donations                                            |");
                    System.out.println("|------------------------------------------------------------------------------------------|");
                    System.out.printf("| %-30s | %-18s | %-20s | %-10s  |\n", "Description", "Cash Amount (RM)", "Item Name", "Quantity");
                    System.out.println("|------------------------------------------------------------------------------------------|");

                    for (int j = 0; j < donations.size(); j++) {
                        Donation donation = donations.getEntry(j);
                        ListInterface<DonatedItem> donatedItems = donation.getItems();

                        if (donatedItems != null && !donatedItems.isEmpty()) {
                            for (int k = 0; k < donatedItems.size(); k++) {
                                DonatedItem item = donatedItems.getEntry(k);
                                System.out.printf("| %-30s | %-18.2f | %-20s | %-10d  |\n",
                                        donation.getDescription(),
                                        donation.getCashAmount(),
                                        item.getName(),
                                        item.getQuantity()
                                );

                                donation.setDescription("");
                                donation.setCashAmount(0);
                            }
                        } else {

                            System.out.printf("| %-30s | %-18.2f | %-20s | %-10s  |\n",
                                    donation.getDescription(),
                                    donation.getCashAmount(),
                                    "N/A",
                                    "N/A"
                            );
                        }
                    }
                    System.out.println("|------------------------------------------------------------------------------------------|");
                } else {
                    System.out.println("No donations available.");
                }
                System.out.println();
            }
        }
    }

}
