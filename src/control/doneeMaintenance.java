/*
author: Lee Zhi Hua
 */
package control;

import adt.*;
import dao.*;
import boundary.*;
import entity.*;
import utility.*;

public class doneeMaintenance {

    private ListInterface<Donee> doneeList = new ArrayList<>();
    private LinkedListInterface<Donation> donationList = new LinkedList<>();
    public HashMap<String, Donee> doneeMap = new HashMap<>();
    private DoneeDAO doneeDAO = new DoneeDAO();
    private DoneeUI doneeUI = new DoneeUI();

    public doneeMaintenance() {
        loadAllFromFile();
    }

    private void loadAllFromFile() {
        doneeList = doneeDAO.retrieveDonees();
        donationList = doneeDAO.retrieveDonations(doneeList);
        doneeMap = doneeDAO.loadDoneesIntoMap();
    }

    public void runDoneeMaintenance() {
        int choice = 0;
        do {
            choice = doneeUI.menu();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    addDonee();
                    break;
                case 2:
                    remove();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    searchDonee();
                    break;
                case 5:
                    listAll();
                    break;
                case 6:
                    filterDonee();
                    break;
                case 7:
                    generateDoneeSummaryReport();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public boolean addDonee() {

        loadAllFromFile();
        Donee donee = doneeUI.addDonee(doneeList, donationList);
        doneeMap.put(donee.getId(), donee);
        return doneeDAO.saveDonees(doneeList);
    }

    public boolean remove() {
        doneeUI.removeDonee();
        return true;
    }

    public Donee remove(String doneeID) {
        Donee donee = doneeMap.removeValue(doneeID);

        if (donee != null) {

            doneeList.remove(donee);

            LinkedListInterface<Donation> doneeDonations = donee.getDonations();

            for (int i = 0; i < doneeDonations.size(); i++) {
                Donation donation = doneeDonations.getEntry(i);
                donationList.add(donation);

            }

            doneeDAO.saveDonees(doneeList);

        }

        return donee;
    }

    public void update() {
        loadAllFromFile();
        donationList = doneeDAO.retrieveDonations(doneeList);
        doneeUI.updateDoneeDetails(doneeMap, donationList);
    }

    public boolean updateDonee(String doneeId, String name, String type, String contactNo, LinkedList<Donation> selectedDonations) {
        doneeMap = doneeDAO.loadDoneesIntoMap();
        Donee donee = doneeMap.get(doneeId);

        if (donee != null) {
            donee.setName(name);
            donee.setType(type);
            donee.setContactNo(contactNo);
            donee.setDonations(selectedDonations);

            for (int i = 0; i < doneeList.size(); i++) {
                if (doneeList.getEntry(i).getId().equals(doneeId)) {
                    doneeList.replace(i, donee);
                    break;
                }
            }

            return doneeDAO.saveDonees(doneeList);
        }

        return false;
    }

    public void searchDonee() {
        loadAllFromFile();
        doneeUI.searchDonee(doneeMap);
    }

    public void listAll() {
        loadAllFromFile();
        doneeUI.listDoneesWithDonations(doneeList);
    }

    public void filterDonee() {
        int choice = doneeUI.filterDonee();

        switch (choice) {
            case 1:
                runFilterDonee();
                break;
            case 2:
                doneeUI.filterDoneesByDonationAmount();
                break;
            case 3:
                runSortDonee();
                break;
        }
    }

    public void runFilterDonee() {

        int choice = 0;
        do {
            choice = doneeUI.displaySortMenu();
            switch (choice) {
                case 1:
                    displaySortedDoneesByType("Individual");
                    break;
                case 2:
                    displaySortedDoneesByType("Organisation");
                    break;
                case 3:
                    displaySortedDoneesByType("Family");
                    break;
                case 0:
                    System.out.println("Exiting sorting menu.");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }

    public void runSortDonee() {
        int choice = doneeUI.sortByType();

        switch (choice) {
            case 1:
                displaySortedDoneesByType();
                break;
            case 2:
                reverseDoneeList();
                break;
            case 0:
                break;

        }

    }

    public void reverseDoneeList() {
        doneeList.reverse();
        System.out.println("\nDonee list has been reversed:");
        doneeUI.printDoneesTable(doneeList);
        runSortDonee();
    }

    public void displaySortedDoneesByType() {
        sortDoneeListByType();
        System.out.println("\nSorted Donees by Type:");
        doneeUI.printDoneesTable(doneeList);
        runSortDonee();

    }

    private int getTypePriority(String type) {
        switch (type) {
            case "Individual":
                return 1;
            case "Family":
                return 2;
            case "Organization":
                return 3;
            default:
                return 4;
        }
    }

    public void sortDoneeListByType() {
        for (int i = 0; i < doneeList.size() - 1; i++) {
            for (int j = 0; j < doneeList.size() - 1 - i; j++) {
                Donee donee1 = doneeList.getEntry(j);
                Donee donee2 = doneeList.getEntry(j + 1);
                if (getTypePriority(donee1.getType()) > getTypePriority(donee2.getType())) {
                    doneeList.replace(j, donee2);
                    doneeList.replace(j + 1, donee1);
                }
            }
        }
    }

    public void displaySortedDoneesByType(String type) {
        ListInterface<Donee> filteredDonees = filterDoneesByType(type);
        sortDoneeListByType(filteredDonees);
        System.out.println("\nSorted Donees by Type: " + type);
        doneeUI.printDoneesTable(filteredDonees);
    }

    private void sortDoneeListByType(ListInterface<Donee> donees) {
        int n = donees.size();
        boolean swapped;

        // Bubble Sort algorithm
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                Donee donee1 = donees.getEntry(j);
                Donee donee2 = donees.getEntry(j + 1);

                // Compare and swap if necessary
                if (donee1.getType().compareToIgnoreCase(donee2.getType()) > 0) {
                    donees.replace(j, donee2);
                    donees.replace(j + 1, donee1);
                    swapped = true;
                }
            }
            // If no elements were swapped, the list is sorted
            if (!swapped) {
                break;
            }
        }
    }

    public ListInterface<Donee> filterDoneesByType(String type) {
        ListInterface<Donee> filteredList = new ArrayList<>();

        for (int i = 0; i < doneeList.size(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getType().equalsIgnoreCase(type)) {
                filteredList.add(donee);
            }
        }

        return filteredList;
    }

    public void reverseDonees() {
        doneeList.reverse();
    }

    public void displayDonees() {
        for (int i = 0; i < doneeList.size(); i++) {
            Donee donee = doneeList.getEntry(i);
            System.out.println(donee);
        }

    }

    public boolean getDonee(String doneeId) {
        doneeMap = doneeDAO.loadDoneesIntoMap();
        if (doneeMap.get(doneeId) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void filterDoneesByDonationAmount(double minAmount, double maxAmount) {
        ListInterface<Donee> filteredList = new ArrayList<>();

        for (int i = 0; i < doneeList.size(); i++) {
            Donee donee = doneeList.getEntry(i);
            double totalDonationAmount = 0.0;

            LinkedListInterface<Donation> donations = donee.getDonations();
            for (int j = 0; j < donations.size(); j++) {
                Donation donation = donations.getEntry(j);
                totalDonationAmount += donation.getCashAmount();
            }

            if (totalDonationAmount >= minAmount && totalDonationAmount <= maxAmount) {
                filteredList.add(donee);
            }
        }

        if (filteredList.size() > 0) {
            System.out.println("Filtered Donees by Donation Amount (RM" + minAmount + " - RM" + maxAmount + "):");
            doneeUI.printDoneesTable(filteredList);
        } else {
            System.out.println("No donees found with donations in the specified range.");
        }
    }

    public void generateDoneeSummaryReport() {

        loadAllFromFile();
        doneeUI.generateDoneeSummaryReport(doneeList);

    }

    public static void main(String[] args) {
        doneeMaintenance dm = new doneeMaintenance();
        dm.runDoneeMaintenance();

    }

}
