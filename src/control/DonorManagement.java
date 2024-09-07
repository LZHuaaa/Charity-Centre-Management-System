package control;

import adt.ArrayList;
import entity.Donor;
import adt.ListInterface;
import dao.DonorDAO;
import entity.DonatedItem;
import entity.Donation;
import boundary.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DonorManagement {

    private ListInterface<Donor> donorList;
    private DonorDAO donorDAO;

    public DonorManagement(ListInterface<Donor> donorList) {
        this.donorList = donorList;
        this.donorDAO = new DonorDAO(); // Initialize DonorDAO
        // Load existing donors from file
        this.donorList = donorDAO.loadDonors();
    }

    // Method to validate donor ID
    public void validateDonorId(String donorId) {
        if (donorId == null || donorId.trim().isEmpty()) {
            throw new IllegalArgumentException("Donor ID cannot be null or empty");
        }
        for (int i = 0; i < donorList.size(); i++) {
            Donor existingDonor = donorList.getEntry(i);
            if (existingDonor.getDonorId().equals(donorId)) {
                // Donor already exists, handle the case where user can add a donation
                throw new IllegalArgumentException("\nDonor ID already exists: " + existingDonor.getDonorId()
                        + "\nDonor Name: " + existingDonor.getName() + "\nWould you like to add a donation instead?");
            }
        }
    }

    // Method to validate donor name
    public void validateDonorName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Donor name cannot be null or empty");
        }
        if (!Pattern.matches("^[a-zA-Z\\s]+$", name)) {
            throw new IllegalArgumentException("Donor name must contain only letters and spaces");
        }
    }

    // Method to validate donor type
    public void validateDonorType(int type) {
        if (type < 1 || type > 3) {
            throw new IllegalArgumentException("Invalid donor type");
        }
    }

    // Method to validate donor age
    public void validateDonorAge(int age) {
        if (age < 21) {
            throw new IllegalArgumentException("Donor age must be at least 21");
        }
    }

    public void validateDonationId(String donationId) {
        if (donationId == null || donationId.trim().isEmpty()) {
            throw new IllegalArgumentException("\nDonation ID cannot be null or empty\n");
        }
    }

    public void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("\nDescription cannot be null or empty\n");
        }
    }

    public void validateCashAmount(double cashAmount) {
        if (cashAmount < 0) {
            throw new IllegalArgumentException("\nCash amount cannot be negative\n");
        }
    }

    public void validateItemName(String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("\nItem name cannot be null or empty\n");
        }
    }

    public void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("\nQuantity cannot be negative\n");
        }
    }

    // Helper method to sort donorList by donor ID
    private void sortDonorListById() {
        int n = donorList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String id1 = donorList.getEntry(j).getDonorId();
                String id2 = donorList.getEntry(j + 1).getDonorId();
                if (id1.compareTo(id2) > 0) {
                    Donor temp = donorList.getEntry(j);
                    donorList.replace(j, donorList.getEntry(j + 1));
                    donorList.replace(j + 1, temp);
                }
            }
        }
    }

    public String getDonorTypeString(int type) {
        switch (type) {
            case 1:
                return "government";
            case 2:
                return "private";
            case 3:
                return "public";
            default:
                throw new IllegalArgumentException("\nInvalid donor type");
        }
    }

    // Method to search donors by type
    public ListInterface<Donor> filterDonorsByType(int type) {
        
        
        ListInterface<Donor> result = new ArrayList<>();

        try {
            // Validate and get donor type string
            String typeString = getDonorTypeString(type);

            // Filter donors based on the type
            for (int i = 0; i < donorList.size(); i++) {
                Donor donor = donorList.getEntry(i);
                if (donor.getDonorType().equalsIgnoreCase(typeString)) {
                    result.add(donor);
                }
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());  // Display "Invalid donor type"
        }
        return result;
    }

    public void saveDonorList() {
        donorDAO.saveDonors(donorList);  // Save the donor list to the file
    }

    // Method to add a new donor
    public void addDonor(Donor donor) {
        if (donor == null) {
            throw new IllegalArgumentException("Donor cannot be null");
        }

        // Add the new donor to the list
        donorList.add(donor);
        // Sort the donorList by donor ID
        sortDonorListById();
        // Save to file
        donorDAO.saveDonors(donorList);
    }

    // Method to remove a donor
    public boolean removeDonor(String donorId) {
        if (donorId == null || donorId.trim().isEmpty()) {
            throw new IllegalArgumentException("\nDonor ID cannot be null or empty\n");
        }

        Donor donorToRemove = null;
        for (int i = 0; i < donorList.size(); i++) {
            Donor donor = donorList.getEntry(i);
            if (donor.getDonorId().equals(donorId)) {
                donorToRemove = donor;
                break;
            }
        }

        if (donorToRemove != null) {
            donorList.remove(donorToRemove);
            // Save to file
            donorDAO.saveDonors(donorList);
            return true;
        } else {
            return false;
        }
    }

    // Method to update donor details
    public void updateDonor(String donorId) {
        Donor donorToUpdate = null;
        for (int i = 0; i < donorList.size(); i++) {
            Donor donor = donorList.getEntry(i);
            if (donor.getDonorId().equals(donorId)) {
                donorToUpdate = donor;
                break;
            }
        }

        if (donorToUpdate == null) {
            throw new IllegalArgumentException("Donor not found");
        }

        // Proceed with update
        // Prompt for new details and validate
        Scanner scanner = new Scanner(System.in);

        // Validate Name
        String name = "";
        boolean valid = false;
        while (!valid) {
            System.out.print("Enter new name: ");
            name = scanner.nextLine();
            try {
                validateDonorName(name);
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage() + "\n");
            }
        }
        donorToUpdate.setName(name);

        // Validate Age
        int age = 0;
        valid = false;
        while (!valid) {
            System.out.print("Enter new age: ");
            try {
                age = Integer.parseInt(scanner.nextLine());
                validateDonorAge(age);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for age. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage() + "\n");
            }
        }
        donorToUpdate.setAge(age);

        // Validate Type
        int type = 0;
        valid = false;
        while (!valid) {
            System.out.print("Enter new donor type (1= government, 2= private, 3= public): ");
            type = Integer.parseInt(scanner.nextLine());
            try {
                validateDonorType(type);
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage() + "\n");
            }
        }

        donorToUpdate.setDonorType(getDonorTypeString(type));

        // Save changes to the donor list
        donorDAO.saveDonors(donorList);
        System.out.println("\nDonor updated successfully. Continue?");
    }

    //list
    public ListInterface<Donor> getDonorList() {
        return donorList;
    }

    // Method to list all donors
    public void listDonors() {
        if (donorList.isEmpty()) {
            System.out.println("\nNo donors available.\n");
        } else {
            System.out.println("\nDonors list - ");
            for (int i = 0; i < donorList.size(); i++) {
                Donor donor = donorList.getEntry(i);
                System.out.println("\nDonor ID: " + donor.getDonorId());
                System.out.println("Name: " + donor.getName());
                System.out.println("Age: " + donor.getAge());
                System.out.println("Type: " + donor.getDonorType());

                ListInterface<Donation> donations = donor.getDonations();
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
                                System.out.printf("| %-30s | %-18s | %-20s | %-10d  |\n",
                                        donation.getDescription(),
                                        donation.getCashAmount() == 0 ? "" : String.format("%.2f", donation.getCashAmount()),
                                        item.getName(),
                                        item.getQuantity()
                                );
                            }
                        } else {
                            System.out.printf("| %-30s | %-18.2f | %-20s | %-10s  |\n",
                                    donation.getDescription(),
                                    donation.getCashAmount() == 0 ? "" : String.format("%.2f", donation.getCashAmount()),
                                    "N/A",
                                    "N/A"
                            );
                        }
                    }
                    System.out.print("|------------------------------------------------------------------------------------------|\n");
                } else {
                    System.out.println("No donations available.");
                }
            }
            System.out.println("\nAll donors are listed.");
        }
    }

    public ListInterface<Donor> getAllDonors() {
        return donorList;
    }

    public Donor findDonorById(String donorId) {
        for (int i = 0; i < donorList.size(); i++) {
            Donor donor = donorList.getEntry(i);
            if (donor.getDonorId().equalsIgnoreCase(donorId)) {
                return donor; // Return the donor if found
            }
        }
        return null; // Return null if no donor is found with the given ID
    }
}
