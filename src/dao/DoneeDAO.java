/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.*;
import java.io.*;

/**
 *
 * @author leezh
 */
public class DoneeDAO {

    public String fileName = "donee.txt";

    public DoneeDAO() {

    }

    public DoneeDAO(String fileName) {
        this.fileName = fileName;
    }

    public boolean saveDonees(ListInterface<Donee> doneeList) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("donee.txt"))) {
            for (int i = 0; i < doneeList.size(); i++) {
                Donee donee = doneeList.getEntry(i);
                writer.write(donee.getId() + "," + donee.getName() + "," + donee.getType() + "," + donee.getContactNo());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing donees to file: " + e.getMessage());
            return false;
        }

        return saveDonations(doneeList);
    }

    public boolean saveDonations(ListInterface<Donee> doneeList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("donation.txt"))) {
            for (int i = 0; i < doneeList.size(); i++) {
                Donee donee = doneeList.getEntry(i);
                for (int j = 0; j < donee.getDonations().size(); j++) {
                    Donation donation = donee.getDonations().getEntry(j);
                    writer.write(donee.getId() + "," + donation.getDescription() + "," + donation.getCashAmount());
                    writer.newLine();
                    for (int k = 0; k < donation.getItems().size(); k++) {
                        DonatedItem item = donation.getItems().getEntry(k);
                        writer.write("    " + item.getName() + "," + item.getQuantity());
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing donations to file: " + e.getMessage());
            return false;
        }
        return true;
    }

    public ListInterface<Donee> retrieveDonees() {
        ListInterface<Donee> doneeList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("donee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] doneeData = line.split(",");
                if (doneeData.length == 4) {
                    Donee donee = new Donee(doneeData[0], doneeData[1], doneeData[2], doneeData[3]);
                    doneeList.add(donee);
                }
            }

            
        } catch (IOException e) {
            System.out.println("Error reading donees from file: " + e.getMessage());
        }
        return doneeList;
    }

    public void retrieveDonations(ListInterface<Donee> doneeList) {
        try (BufferedReader reader = new BufferedReader(new FileReader("donation.txt"))) {
            String line;
            Donee currentDonee = null;
            Donation currentDonation = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (!line.startsWith("    ")) {
                    // Read Donation
                    String[] donationData = line.split(",");
                    if (donationData.length == 3) {
                        String doneeId = donationData[0];
                        String description = donationData[1];
                        double cashAmount = Double.parseDouble(donationData[2]);

                        // Find Donee
                        currentDonee = findDoneeById(doneeList, doneeId);
                        if (currentDonee != null) {
                            // Create and add Donation to Donee
                            currentDonation = new Donation(description, cashAmount);
                            currentDonee.addDonation(currentDonation);
                        }

                    }

                    if (donationData.length == 2) {
                        String itemName = donationData[0];
                        int quantity = Integer.parseInt(donationData[1]);
                        DonatedItem item = new DonatedItem(itemName, quantity);

                        // Add item to the current Donation
                        currentDonation.getItems().add(item);
                    }

                }
            }
        } catch (IOException e) {
            System.out.println("Error reading donations from file: " + e.getMessage());
        }
    }

    public HashMap<String, Donee> loadDoneesIntoMap() {

        HashMap<String, Donee> doneeMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] doneeData = line.split(",");
                if (doneeData.length == 4) {
                    Donee donee = new Donee(doneeData[0], doneeData[1], doneeData[2], doneeData[3]);
                    doneeMap.put(donee.getId(), donee);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading donees from file: " + e.getMessage());
        }

        return doneeMap;
    }

    public void loadDonationsIntoDonees(HashMap<String, Donee> doneeMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader("donation.txt"))) {
            String line;
            Donee currentDonee = null;
            Donation currentDonation = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (!line.startsWith("    ")) {
                    String[] donationData = line.split(",");
                    if (donationData.length == 3) {
                        String doneeId = donationData[0];
                        String description = donationData[1];
                        double cashAmount = Double.parseDouble(donationData[2]);

                        currentDonee = doneeMap.get(doneeId);
                        if (currentDonee != null) {
                            currentDonation = new Donation(description, cashAmount);
                            currentDonee.addDonation(currentDonation);
                        }
                    } else if (donationData.length == 2) {
                        String itemName = donationData[0];
                        int quantity = Integer.parseInt(donationData[1]);
                        DonatedItem item = new DonatedItem(itemName, quantity);

                        if (currentDonation != null) {
                            currentDonation.getItems().add(item);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading donations from file: " + e.getMessage());
        }
    }

    private Donee findDoneeById(ListInterface<Donee> doneeList, String id) {
        for (int i = 0; i < doneeList.size(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getId().equals(id)) {
                return donee;
            }
        }
        return null;
    }

    public String generateNewId(ListInterface<Donee> doneeList) {

        String prefix = "DE";
        int maxId = 0;

        if (doneeList.size() == 0) {
            return "DE001";
        }

        for (int i = 0; i < doneeList.size(); i++) {
            Donee donee = doneeList.getEntry(i);
            String currentId = donee.getId();

            String numericPart = currentId.replace(prefix, "");

            try {
                int idNum = Integer.parseInt(numericPart);
                if (idNum > maxId) {
                    maxId = idNum;
                }
            } catch (NumberFormatException e) {

                System.out.println("Error parsing ID: " + currentId);
            }
        }

        int newIdNum = maxId + 1;

        String newId = prefix + String.format("%03d", newIdNum);

        return newId;
    }

}
