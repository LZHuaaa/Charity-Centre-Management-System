/**
 *
 * @author Lee Zhi Hua
 */
package boundary;

import entity.*;
import java.util.Scanner;
import adt.*;
import control. doneeMaintenance;
import dao.*;

public class DoneeUI {

    private DoneeDAO doneeDAO = new DoneeDAO();
    Scanner scanner = new Scanner(System.in);

    public int menu() {
        System.out.println("\nDonee Management System");
        System.out.println("1. Add new donee");
        System.out.println("2. Remove donee");
        System.out.println("3. Update donee details");
        System.out.println("4. Search donee details");
        System.out.println("5. List donee with all the donation received");
        System.out.println("6. Filter donee based on criteria");
        System.out.println("7. Generate summary report");
        System.out.println("0. Exit ");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public Donee addDonee(ListInterface<Donee> doneeList, ListInterface<Donation> availableDonations) {
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
                scanner.nextLine(); // Consume newline
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

        // Ask if they want to add donations
        String addDonationResponse;
        ListInterface<Donation> selectedDonations = new LinkedList<>();

        if (availableDonations.isEmpty()) {
            System.out.println("No donations can be added.");
        } else {

            do {
                System.out.println("\n---Available Donations---");
                System.out.printf("\n%-10s %-10s %-30s %-20s %-30s %-10s%n", "No", "ID", "Description", "Cash Amount(RM)", "Item", "Qty");
                System.out.println("------------------------------------------------------------------------------------------------------------");

                for (int i = 0; i < availableDonations.size(); i++) {
                    Donation donation = availableDonations.getEntry(i);
                    String donationId = donation.getDonationId();
                    String description = donation.getDescription();
                    double amount = donation.getCashAmount();

                    // Display donation details
                    System.out.printf("%-10s %-10s %-30s %-20.2f", i + 1, donationId, description, amount);

                    // Display items associated with this donation
                    ListInterface<DonatedItem> items = donation.getItems();
                    if (items.size() > 0) {
                        for (int j = 0; j < items.size(); j++) {
                            DonatedItem item = items.getEntry(j);

                            if (j == 0) {
                                System.out.printf(" %-30s %-10d%n", item.getName(), item.getQuantity());
                            } else {
                                System.out.printf("%-73s", "");
                                System.out.printf(" %-30s %-10d%n", item.getName(), item.getQuantity());
                            }
                        }
                    } else {
                        System.out.println();
                    }

                }

                boolean moreDonations = true;
                while (moreDonations) {
                    System.out.print("\nEnter the number of the donation to add: ");
                    int donationChoice = -1;
                    if (scanner.hasNextInt()) {
                        donationChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (donationChoice > 0 && donationChoice <= availableDonations.size()) {
                            Donation selectedDonation = availableDonations.getEntry(donationChoice - 1);
                            selectedDonations.add(selectedDonation);
                            availableDonations.remove(donationChoice - 1);
                            break;
                        } else {
                            System.out.println("Invalid choice. Please select again.");
                        }
                    }
                }

                if (availableDonations.isEmpty()) {

                    break;
                }

                System.out.print("\nDo you want to add more donations? (Y/N): ");
                addDonationResponse = scanner.nextLine().trim();

                if (!addDonationResponse.equalsIgnoreCase("y") && !addDonationResponse.equalsIgnoreCase("n")) {
                    System.out.println("Invalid choice. Please select again.");
                    addDonationResponse = "";
                }

            } while (!addDonationResponse.equalsIgnoreCase("n"));
        }

        Donee newDonee = new Donee(newId, name, type, contactNo);

        newDonee.setDonations((LinkedList<Donation>) selectedDonations);
        doneeList.add(newDonee);

        System.out.println("\nDonee added successfully!");

        return newDonee;
    }

    public void removeDonee() {
        boolean exist = false;
         doneeMaintenance dm = new  doneeMaintenance();
        Scanner scanner = new Scanner(System.in);

        while (!exist) {
            System.out.print("Enter the donee ID to remove (0 = exit): ");
            String doneeId = scanner.nextLine();

            if ("0".equals(doneeId)) {
                break;  
            }

            Donee donee = dm.doneeMap.get(doneeId);

            if (donee != null) {
 
                System.out.println("\nDonee Details:");
                System.out.println("Donee ID: " + donee.getId());
                System.out.println("Name: " + donee.getName());
                System.out.println("Type: " + donee.getType());
                System.out.println("Contact Number: " + donee.getContactNo());

     
                System.out.print("\nDo you want to remove this donee? (y/n): ");
                String confirmation = scanner.nextLine();

                if ("y".equalsIgnoreCase(confirmation)) {
                    donee = dm.remove(doneeId);
                    System.out.println("\nDonee " + donee.getName() + " removed successfully!");

                    ListInterface<Donation> donations = donee.getDonations();
                    for (int i = 0; i < donations.size(); i++) {
                        Donation donation = donations.getEntry(i);

                    }
                    System.out.println("Associated donations have been returned to the donation list.");
                    exist = true;
                } else {

                    System.out.println("Donee removal canceled.");
                    break;
                }
            } else {
                System.out.println("\nDonee not found, please try again.");
            }
        }
    }

    public void updateDoneeDetails(HashMap<String, Donee> doneeMap, ListInterface<Donation> availableDonations) {

        Donee donee = new Donee();
        String doneeId;

        do {
            System.out.print("Enter Donee ID to update (0 = exit): ");
            doneeId = scanner.nextLine().trim();

            if ("0".equals(doneeId)) {
                return;
            }

            donee = doneeMap.get(doneeId);

            if (donee == null) {
                System.out.println("No donee found with the given ID. Please try again.\n");
            }
        } while (donee == null);

        ListInterface<Donation> donations = donee.getDonations();

        System.out.println("\n--Donee Found--");
        System.out.println("ID: " + donee.getId());
        System.out.println("Name: " + donee.getName());
        System.out.println("Type: " + donee.getType());
        System.out.println("Contact Number: " + donee.getContactNo());

        System.out.print("\nEnter new name: ");
        String newName = scanner.nextLine().trim();

        System.out.println("\nSelect the new donee type:");
        System.out.println("1. Individual");
        System.out.println("2. Organisation");
        System.out.println("3. Family");
        String type = "";
        int choice = -1;
        while (choice < 1 || choice > 3) {
            System.out.print("Enter choice (1, 2, or 3): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
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

        System.out.print("\nPlease enter the new contact number (e.g. 012-3456789): ");
        String contactNo = scanner.nextLine().trim();
        while (!contactNo.matches("\\d{3}-\\d{7}")) {
            System.out.print("\nInvalid format. Please enter the contact number (e.g. 012-3456789): ");
            contactNo = scanner.nextLine().trim();
        }

        // Ask if they want to remove a donation
        if (!donations.isEmpty()) {
            System.out.print("\nDo you want to remove a donation? (Y/N): ");
            String removeDonationResponse = scanner.nextLine().trim();

            if (removeDonationResponse.equalsIgnoreCase("y")) {

                do {
                    System.out.println("\n---Current Donations---");
                    System.out.printf("\n%-10s %-10s %-30s %-20s %-30s %-10s%n", "No", "ID", "Description", "Cash Amount(RM)", "Item", "Qty");
                    System.out.println("------------------------------------------------------------------------------------------------------------");

                    for (int i = 0; i < donations.size(); i++) {
                        Donation donation = donations.getEntry(i);
                        String id = donation.getDonationId();
                        String description = donation.getDescription();
                        double amount = donation.getCashAmount();

                        // Display donation details
                        System.out.printf("%-10s %-10s %-30s %-20.2f", i + 1, id, description, amount);

                        // Display items associated with this donation
                        ListInterface<DonatedItem> items = donation.getItems();
                        if (items.size() > 0) {
                            for (int j = 0; j < items.size(); j++) {
                                DonatedItem item = items.getEntry(j);

                                if (j == 0) {
                                    System.out.printf(" %-30s %-10d%n", item.getName(), item.getQuantity());
                                } else {
                                    System.out.printf("%-73s", "");
                                    System.out.printf(" %-30s %-10d%n", item.getName(), item.getQuantity());
                                }
                            }
                        } else {
                            System.out.println(); // Move to next line if no items
                        }
                    }

                    System.out.print("\nEnter the number of the donation to remove: ");
                    int donationChoice = -1;
                    if (scanner.hasNextInt()) {
                        donationChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (donationChoice > 0 && donationChoice <= donations.size()) {
                            Donation removedDonation = donations.remove(donationChoice - 1);

                            availableDonations.add(removedDonation);

                            System.out.println("Donation removed successfully.");
                        } else {
                            System.out.println("Invalid choice. Please select again.");
                        }
                    }

                    if (donations.isEmpty()) {
                        break;
                    }

                    System.out.print("\nDo you want to remove another donation? (Y/N): ");
                    removeDonationResponse = scanner.nextLine().trim();

                } while (removeDonationResponse.equalsIgnoreCase("y"));

            }
        }

        // Ask if they want to add donations
        String addDonationResponse;
        ListInterface<Donation> selectedDonations = new LinkedList<>();

        if (!availableDonations.isEmpty()) {

            System.out.print("\nDo you want to add more donations (Y/N)? ");
            String response = scanner.nextLine().trim();

            if (response.equalsIgnoreCase("y")) {

                do {
                    System.out.println("\n---Available Donations---");
                    System.out.printf("\n%-10s %-10s %-30s %-20s %-30s %-10s%n", "No", "ID", "Description", "Cash Amount(RM)", "Item", "Qty");
                    System.out.println("------------------------------------------------------------------------------------------------------------");

                    for (int i = 0; i < availableDonations.size(); i++) {
                        Donation donation = availableDonations.getEntry(i);
                        String id = donation.getDonationId();
                        String description = donation.getDescription();
                        double amount = donation.getCashAmount();

                        // Display donation details
                        System.out.printf("%-10s %-10s %-30s %-20.2f", i + 1, id, description, amount);

                        // Display items associated with this donation
                        ListInterface<DonatedItem> items = donation.getItems();
                        if (items.size() > 0) {
                            for (int j = 0; j < items.size(); j++) {
                                DonatedItem item = items.getEntry(j);

                                if (j == 0) {
                                    System.out.printf(" %-30s %-10d%n", item.getName(), item.getQuantity());
                                } else {
                                    System.out.printf("%-73s", "");
                                    System.out.printf(" %-30s %-10d%n", item.getName(), item.getQuantity());
                                }
                            }
                        } else {
                            System.out.println(); // Move to next line if no items
                        }
                    }

                    System.out.print("\nEnter the number of the donation to add: ");
                    int donationChoice = -1;
                    if (scanner.hasNextInt()) {
                        donationChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (donationChoice > 0 && donationChoice <= availableDonations.size()) {
                            Donation selectedDonation = availableDonations.getEntry(donationChoice - 1);
                            donations.add(selectedDonation);
                            availableDonations.remove(donationChoice - 1);
                        } else {
                            System.out.println("Invalid choice. Please select again.");
                        }
                    }

                    if (availableDonations.isEmpty()) {
                        break;
                    }

                    System.out.print("\nDo you want to add more donations? (Y/N): ");
                    addDonationResponse = scanner.nextLine().trim();

                } while (addDonationResponse.equalsIgnoreCase("y"));
            }
        }

         doneeMaintenance dm = new  doneeMaintenance();

        boolean updated = dm.updateDonee(doneeId, newName, type, contactNo, (LinkedList<Donation>) donations);

        if (updated) {
            System.out.println("Donee details updated successfully.");
        } else {
            System.out.println("Failed to update donee details.");
        }
    }

    public void searchDonee(HashMap<String, Donee> doneeMap) {
        System.out.print("Enter Donee ID to search (0 = exit): ");
        String doneeId = scanner.nextLine();
        Donee donee = doneeMap.get(doneeId);

        if ("0".equals(doneeId)) {
            return;
        }

        if (donee != null) {
            System.out.println("\n--Donee Found--");
            System.out.println("ID: " + donee.getId());
            System.out.println("Name: " + donee.getName());
            System.out.println("Type: " + donee.getType());
            System.out.println("Contact Number: " + donee.getContactNo());

            // Ask user if they want to display donation details
            String response;
            do {
                System.out.print("\nWould you like to display donations for this donee? (Y/N): ");
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
        //ListInterface<Donation> donations = donee.getDonations();
        ListInterface<Donation> donations = donee.getDonations();

        if (donations != null && !donations.isEmpty()) {
            System.out.println("\n|------------------------------------------------------------------------------------------------------|");
            System.out.println("|                                           Donations                                                  |");
            System.out.println("|------------------------------------------------------------------------------------------------------|");
            System.out.printf("|%-10s | %-30s | %-18s | %-20s | %-10s  |\n", "ID", "Description", "Cash Amount (RM)", "Item Name", "Quantity");
            System.out.println("|------------------------------------------------------------------------------------------------------|");

            for (int j = 0; j < donations.size(); j++) {
                Donation donation = donations.getEntry(j);
                ListInterface<DonatedItem> donatedItems = donation.getItems();

                if (donatedItems != null && !donatedItems.isEmpty()) {
                    for (int k = 0; k < donatedItems.size(); k++) {
                        DonatedItem item = donatedItems.getEntry(k);
                        // Using format specifier "%.2f" for cash amount
                        System.out.printf("|%-10s | %-30s | %-18s | %-20s | %-10d  |\n",
                                donation.getDonationId(),
                                donation.getDescription(),
                                donation.getCashAmount() == 0 ? "" : String.format("%.2f", donation.getCashAmount()),
                                item.getName(),
                                item.getQuantity()
                        );

                        donation.setDonationId("");
                        donation.setDescription("");
                        donation.setCashAmount(0);
                    }
                } else {
                    // Handle case where there are no donated items
                    System.out.printf("|%-10s | %-30s | %-18s | %-20s | %-10s  |\n",
                            donation.getDonationId(),
                            donation.getDescription(),
                            donation.getCashAmount() == 0 ? "" : String.format("%.2f", donation.getCashAmount()),
                            "N/A",
                            "N/A"
                    );
                }
            }
            System.out.println("|------------------------------------------------------------------------------------------------------|");
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
                    System.out.println("\n|------------------------------------------------------------------------------------------------------|");
                    System.out.println("|                                           Donations                                                  |");
                    System.out.println("|------------------------------------------------------------------------------------------------------|");
                    System.out.printf("|%-10s | %-30s | %-18s | %-20s | %-10s  |\n", "ID", "Description", "Cash Amount (RM)", "Item Name", "Quantity");
                    System.out.println("|------------------------------------------------------------------------------------------------------|");

                    for (int j = 0; j < donations.size(); j++) {
                        Donation donation = donations.getEntry(j);
                        ListInterface<DonatedItem> donatedItems = donation.getItems();

                        if (donatedItems != null && !donatedItems.isEmpty()) {
                            for (int k = 0; k < donatedItems.size(); k++) {
                                DonatedItem item = donatedItems.getEntry(k);
                                System.out.printf("|%-10s | %-30s | %-18s | %-20s | %-10d  |\n",
                                        donation.getDonationId(),
                                        donation.getDescription(),
                                        donation.getCashAmount() == 0 ? "" : String.format("%.2f", donation.getCashAmount()),
                                        item.getName(),
                                        item.getQuantity()
                                );
                                donation.setDonationId("");
                                donation.setDescription("");
                                donation.setCashAmount(0);
                            }
                        } else {

                            System.out.printf("|%-10s | %-30s | %-18.2f | %-20s | %-10s  |\n",
                                    "",
                                    donation.getDescription(),
                                    donation.getCashAmount() == 0 ? "" : String.format("%.2f", donation.getCashAmount()),
                                    "N/A",
                                    "N/A"
                            );
                        }
                    }
                    System.out.println("|------------------------------------------------------------------------------------------------------|");
                } else {
                    System.out.println("No donations available.");
                }
                System.out.println();
            }
        }
    }

    public int filterDonee() {

        System.out.println("Filter Donees");
        System.out.println("1. Filter by Donee Type");
        System.out.println("2. Filter by Donation");
        System.out.println("3. Sort by Donee Type (Ascending / Descending order)");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        int choice = -1;
        while (choice < 0 || choice > 3) {

            choice = scanner.nextInt();
            if (choice < 0 || choice > 3) {
                System.out.println("Invalid choice. Please select again.");
            }
        }
        return choice;

    }

    public int sortByType() {
        System.out.println("\nSelect the sorting order");
        System.out.println("1. Ascending order");
        System.out.println("2. Descending order");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        int choice = -1;
        while (choice < 0 || choice > 2) {

            choice = scanner.nextInt();
            if (choice < 0 || choice > 2) {
                System.out.println("Invalid choice. Please select again.");
            }
        }
        return choice;

    }

    public int displaySortMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nFilter Donees by Type:");
        System.out.println("1. Individual");
        System.out.println("2. Organisation");
        System.out.println("3. Family");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    public void filterDoneesByDonationAmount() {
        double minAmount = -1;
        double maxAmount = -1;

        do {

            System.out.print("\nEnter minimum donation amount: RM");

            try {
                minAmount = scanner.nextDouble();

                if (minAmount < 0) {
                    System.out.println("Invalid input. Minimum amount cannot be negative. Please enter a positive value.");
                    minAmount = -1;
                }

            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (minAmount == -1);

        do {
            System.out.print("Enter maximum donation amount: RM");
            try {
                maxAmount = scanner.nextDouble();

                if (maxAmount < minAmount) {
                    System.out.printf("The maximum amount should bigger than minimum amount. Please try again.\n\n");
                    maxAmount = -1;
                }

            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (maxAmount == -1);

         doneeMaintenance dm = new  doneeMaintenance();

        dm.filterDoneesByDonationAmount(minAmount, maxAmount);

    }

    public double getDoubleInput() {
        double input = -1;
        boolean valid = false;

        while (!valid) {

            try {
                input = scanner.nextDouble();
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }

        return input;
    }

    public void printDoneesTable(ListInterface<Donee> donees) {
        System.out.println("\n| ID        | Name                | Type           | Contact No     |");
        System.out.println("|-----------|---------------------|----------------|----------------|");

        for (int i = 0; i < donees.size(); i++) {
            Donee donee = donees.getEntry(i);
            System.out.printf("| %-9s | %-19s | %-14s | %-14s |\n",
                    donee.getId(),
                    donee.getName(),
                    donee.getType(),
                    donee.getContactNo());
        }

        System.out.println("|-----------|---------------------|----------------|----------------|\n");
    }

    public void generateDoneeSummaryReport(ListInterface<Donee> doneeList) {
        System.out.println("Donee Summary Report");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-15s | %-15s | %-18s | %-15s | %-13s |\n",
                "ID", "Name", "Type", "Contact No", "Donations Received", "Total Cash (RM)", "Total Items");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < doneeList.size(); i++) {
            Donee donee = doneeList.getEntry(i);
            ListInterface<Donation> donations = donee.getDonations();

            int donationCount = donations.size();
            double totalCash = 0.0;
            int totalItems = 0;

            for (int j = 0; j < donations.size(); j++) {
                Donation donation = donations.getEntry(j);
                totalCash += donation.getCashAmount();

                ListInterface<DonatedItem> donatedItems = donation.getItems();
                if (donatedItems != null) {
                    totalItems += donatedItems.size();
                }
            }

            System.out.printf("| %-10s | %-20s | %-15s | %-15s | %-18d | %-15.2f | %-13d |\n",
                    donee.getId(),
                    donee.getName(),
                    donee.getType(),
                    donee.getContactNo(),
                    donationCount,
                    totalCash,
                    totalItems
            );
        }

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
    }

}
