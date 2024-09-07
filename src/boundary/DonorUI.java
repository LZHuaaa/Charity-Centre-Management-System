package boundary;

import entity.*;
import adt.*;
import control.*;
import java.util.Scanner;
import utility.MessageUI;

public class DonorUI {

    private DonorManagement donorManagement;
    private Scanner scanner;
    private ListInterface<Donor> donorList = new ArrayList<>();
    private boolean exitToMainSystem = false;

    public DonorUI() {
        ListInterface<Donor> donorList = new ArrayList<>();
        this.donorManagement = new DonorManagement(donorList);
        this.scanner = new Scanner(System.in);
    }

    // Method to display menu options
    private void displayMenu() {
        System.out.println("\nDonor Management System");
        System.out.println("1. Add a new donor");
        System.out.println("2. Remove a donor");
        System.out.println("3. Update donor details");
        System.out.println("4. Search donor details");
        System.out.println("5. List all donors");
        System.out.println("6. Filter donors");
        System.out.println("7. Generate summary report");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    public void pauseAndPrompt() {
        if (exitToMainSystem) {
            MessageUI.displayExitMessage();
            return;
        }

        System.out.println("\n1. Return to menu");
        System.out.println("2. Exit Donor Management System...");
        System.out.print("Select an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (option == 2) {
            exitToMainSystem = true;
        }
    }

    // Method to add a new donor
    private void addDonor() {
        String id = "";
        String name = "";
        int type = 0;
        int age = 0;
        boolean valid = false;
        boolean donorExists = false;
        Donor existingDonor = null;

        // Validate ID
        while (!valid) {
            try {
                System.out.print("\nAdd a new donor - ");
                System.out.print("\nEnter donor ID (xxx to back): ");
                id = scanner.nextLine();

                if (id.equals("xxx") || id.equals("XXX")) {
                    pauseAndPrompt();
                    return;
                }

                donorManagement.validateDonorId(id);
                valid = true;
            } catch (IllegalArgumentException e) {
                if (e.getMessage().contains("Donor ID already exists")) {
                    System.out.println(e.getMessage());
                    // Prompt user to continue adding donation
                    System.out.print("\nDo you want to continue by adding a donation to the existing donor? (Y/N): ");
                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("y")) {
                        // Find the existing donor and allow adding donation
                        existingDonor = donorManagement.findDonorById(id);
                        donorExists = true;
                        break; // Exit the loop and skip the rest of the donor creation process
                    } else {
                        System.out.println("Operation cancelled.");
                        return; // Exit the add donor process
                    }
                } else {
                    System.out.println("\nError: " + e.getMessage());
                }
            }
        }

        if (!donorExists) {
            // Continue with donor creation if the donor doesn't already exist
            valid = false;
            // Validate Name
            while (!valid) {
                try {
                    System.out.print("Enter donor name: ");
                    name = scanner.nextLine();
                    donorManagement.validateDonorName(name);
                    valid = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("\nError: " + e.getMessage() + "\n");
                }
            }

            valid = false;
            // Validate Age
            while (!valid) {
                try {
                    System.out.print("Enter donor age: ");
                    age = Integer.parseInt(scanner.nextLine());
                    donorManagement.validateDonorAge(age);
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid input for age. Please enter a valid number.\n");
                } catch (IllegalArgumentException e) {
                    System.out.println("\nError: " + e.getMessage() + "\n");
                }
            }

            valid = false;
            // Validate Type
            while (!valid) {
                try {
                    System.out.print("Enter donor type (1 = government, 2 = private, 3 = public): ");
                    type = Integer.parseInt(scanner.nextLine());
                    donorManagement.validateDonorType(type);
                    valid = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("\nError: " + e.getMessage() + "\n");
                }
            }

            // Convert type to string
            String donorTypeString = donorManagement.getDonorTypeString(type);

            // If all validations pass, create and add donor
            Donor donor = new Donor(id, name, donorTypeString, age);
            donorManagement.addDonor(donor);
        }

        // Proceed with adding donation details for the existing donor or new donor
        addDonationDetails(existingDonor != null ? existingDonor : donorManagement.findDonorById(id));
    }

    private void addDonationDetails(Donor donor) {
        String donationId = "";
        String description = "";
        double cashAmount = 0;
        String itemName = "";
        int quantity = 0;

        boolean validDonationId = false;
        while (!validDonationId) {
            System.out.print("Enter donation ID: ");
            donationId = scanner.nextLine();
            try {
                donorManagement.validateDonationId(donationId);
                validDonationId = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        boolean validDescription = false;
        while (!validDescription) {
            System.out.print("Enter description: ");
            description = scanner.nextLine();
            try {
                donorManagement.validateDescription(description);
                validDescription = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        boolean validCashAmount = false;
        while (!validCashAmount) {
            System.out.print("Enter cash amount: ");
            try {
                cashAmount = Double.parseDouble(scanner.nextLine());
                donorManagement.validateCashAmount(cashAmount);
                validCashAmount = true;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input for cash amount. Please enter a valid number.\n");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        boolean validItemName = false;
        while (!validItemName) {
            System.out.print("Enter item name: ");
            itemName = scanner.nextLine();
            try {
                donorManagement.validateItemName(itemName);
                validItemName = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        boolean validQuantity = false;
        while (!validQuantity) {
            System.out.print("Enter quantity: ");
            try {
                quantity = Integer.parseInt(scanner.nextLine());
                donorManagement.validateQuantity(quantity);
                validQuantity = true;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input for quantity. Please enter a valid number.\n");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Add the donation to the donor
        donor.addDonation(donationId, description, cashAmount, itemName, quantity);
        System.out.println("\nDonation added successfully to donor: " + donor.getName());
        donorManagement.saveDonorList();
        pauseAndPrompt();
    }

    // Method to remove a donor
    private void removeDonor() {
        displayAllDonors();
        System.out.print("\nEnter donor ID to remove (xxx to back): ");
        String donorId = scanner.nextLine();

        if (donorId.equals("xxx") || donorId.equals("XXX")) {
            pauseAndPrompt();
            return;
        }

        boolean success = donorManagement.removeDonor(donorId);

        if (success) {
            System.out.println("\nDonor removed successfully.");
        } else {
            System.out.println("\nDonor not found.");
        }

        // Pause and prompt user to return to the main menu or exit
        pauseAndPrompt();
    }

    // Method to update donor details
    private void updateDonor() {
        displayAllDonors();
        System.out.print("Enter donor ID to update (xxx to back): ");
        String donorId = scanner.nextLine();

        if (donorId.equals("xxx") || donorId.equals("XXX")) {
            pauseAndPrompt();
            return;
        }

        try {
            donorManagement.updateDonor(donorId);
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }

        // Pause and prompt user to return to the main menu or exit
        pauseAndPrompt();
    }

    // Method to search donor details
    public void searchDonor() {
        System.out.print("\nSearch donor details - ");
        System.out.print("\nEnter the Donor ID to search (xxx to back): ");
        String donorId = scanner.next(); // Get the donor ID input from the user

        if (donorId.equals("xxx") || donorId.equals("XXX")) {
            pauseAndPrompt();
            return;
        }

        // Call DonorManagement to search for the donor by ID
        Donor donor = donorManagement.findDonorById(donorId);

        if (donor != null) {
            // If donor is found, display the details
            System.out.println("\nDonor found:");
            System.out.println("Donor ID: " + donor.getDonorId());
            System.out.println("Name: " + donor.getName());
            System.out.println("Age: " + donor.getAge());
            System.out.println("Type: " + donor.getDonorType());

            System.out.println("\nAll donors with ID: " + donor.getDonorId() + " are displayed.");
        } else {
            // If donor is not found, display a message
            System.out.println("\nNo donor found with ID: " + donorId);
        }
        // Pause and prompt user to return to the main menu or exit
        pauseAndPrompt();
    }

    // Method to list all donors
    public void listAllDonors() {
        donorManagement.listDonors();
        // Pause and prompt user to return to the main menu or exit
        pauseAndPrompt();
    }

    // Method to filter donors based on criteria
    public void filterDonors() {
        int type = categorizeDonors();

        if (type == 100) {
            return;
        }

        ListInterface<Donor> results = donorManagement.filterDonorsByType(type);

        if (results.isEmpty()) {
            System.out.println("\nNo donors found for the given type.");
        } else {
            int z = 0;
            System.out.println("\nFilter result - ");
            System.out.println("\nDonors found:");
            for (int i = 0; i < results.size(); i++) {
                Donor donor = results.getEntry(i);
                System.out.println("Donor ID: " + donor.getDonorId());
                System.out.println("Name: " + donor.getName());
                System.out.println("Age: " + donor.getAge());
                System.out.println("Type: " + donor.getDonorType());
                System.out.println();

                z += i;
            }
            Donor donor = results.getEntry(z);

            String donorType = null;
            
            switch (type) {
                case 1:
                    donorType = "government";
                    break;
                case 2:
                    donorType = "private";
                    break;
                case 3:
                    donorType = "public";
                    break;
            }

            System.out.println("All donors of " + donorType+ " are displayed.");
        }
        pauseAndPrompt();
    }

    // Method to categorize donors
    public int categorizeDonors() {
        int type;

        // Loop until a valid donor type is entered
        while (true) {
            System.out.print("\nEnter the donor category to search \n(1: Government, 2: Private, 3: Public, 100: Exit): ");
            type = scanner.nextInt();

            // Validate the input
            if (type == 1 || type == 2 || type == 3 || type == 100) {
                break;  // Valid input, exit the loop
            } else {
                System.out.println("\nInvalid donor type.");
            }
        }
        return type;
    }

    // Method to display all donors in a table format
    public void displayAllDonors() {
        ListInterface<Donor> allDonors = donorManagement.getAllDonors(); // Assuming this method returns all donors
        if (allDonors.isEmpty()) {
            System.out.println("\nNo donors available.");
            return;
        }

        System.out.print("\nAll donors information - ");
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-10s %-15s\n", "Donor ID", "Name", "Age", "Donor Type");
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < allDonors.size(); i++) {
            Donor donor = allDonors.getEntry(i);
            System.out.printf("%-15s %-20s %-10d %-15s\n", donor.getDonorId(), donor.getName(), donor.getAge(), donor.getDonorType());
        }

        System.out.println("--------------------------------------------------------------------------------");
    }

    // Method to generate summary report
    public void generateSummaryReport() {
        donorList = donorManagement.getAllDonors();

        if (donorList.isEmpty()) {
            System.out.println("\nNo donors available for the report.");
            return;
        }

        // Create a format for table display
        String tableFormat = "| %-10s | %-20s | %-3s | %-11s | %-15s | %-15s | %-12s |\n";
        String separator = "+------------+----------------------+-----+-------------+-----------------+-----------------+--------------+\n";

        // Print table header
        System.out.println("\nSummary Report - ");
        System.out.print(separator);
        System.out.printf(tableFormat, "Donor ID", "Name", "Age", "Type", "Donations Made", "Total Cash (RM)", "Total Items");
        System.out.println(separator);

        // Calculate and display donor information
        for (int i = 0; i < donorList.size(); i++) {
            Donor donor = donorList.getEntry(i);
            ListInterface<Donation> donations = donor.getDonations();

            int donationsMade = (donations != null) ? donations.size() : 0;
            double totalCash = 0.0;
            int totalItems = 0;

            if (donations != null) {
                for (int j = 0; j < donations.size(); j++) {
                    Donation donation = donations.getEntry(j);
                    totalCash += donation.getCashAmount();
                    ListInterface<DonatedItem> items = donation.getItems();
                    if (items != null) {
                        for (int k = 0; k < items.size(); k++) {
                            totalItems += items.getEntry(k).getQuantity();
                        }
                    }
                }
            }

            // Print donor information
            System.out.printf(tableFormat,
                    donor.getDonorId(),
                    donor.getName(),
                    donor.getAge(),
                    donor.getDonorType(),
                    donationsMade,
                    String.format("%.2f", totalCash),
                    totalItems
            );
        }

        // Print table footer
        System.out.println(separator);
        System.out.println("Want to Go Back? (Y/N)");
        String back = scanner.next();
        if (!back.equalsIgnoreCase("N")) {
            return;
        } else {
            exitToMainSystem = true;
        }
    }

    // Main method to run the UI
    public void run() {
        int option;
        do {
            displayMenu();
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    addDonor();
                    break;
                case 2:
                    removeDonor();
                    break;
                case 3:
                    updateDonor();
                    break;
                case 4:
                    searchDonor();
                    break;
                case 5:
                    listAllDonors();
                    break;
                case 6:
                    filterDonors();
                    break;
                case 7:
                    generateSummaryReport();
                    break;
                case 0:
                    exitToMainSystem = true;
                    break;
                default:
                    System.out.println("\nInvalid option. Please try again.");
                    break;
            }
        } while (!exitToMainSystem);
        System.out.println("\nExiting to main system...");
    }

    // Main method for demonstration
    public static void main(String[] args) {
        DonorUI donorUI = new DonorUI();
        donorUI.run();
    }
}
