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
 * @author Lee Zhi Hua
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
                StringBuilder donationIds = new StringBuilder();

                
                ListInterface<Donation> donations = donee.getDonations();
                for (int j = 0; j < donations.size(); j++) {
                    Donation donation = donations.getEntry(j);
                    donationIds.append(donation.getDonationId());
                    if (j < donations.size() - 1) {
                        donationIds.append(";");
                    }
                }

                writer.write(donee.getId() + "," + donee.getName() + "," + donee.getType() + "," + donee.getContactNo() + "," + donationIds.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing donees to file: " + e.getMessage());
            return false;
        }

        return true;
    }

    //retrieve donee(array)
    public ListInterface<Donee> retrieveDonees() {
        ListInterface<Donee> doneeList = new ArrayList<>();
        HashMapInterface<String, Donation> donationMap = retrieveDonations();

        try (BufferedReader reader = new BufferedReader(new FileReader("donee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] doneeData = line.split(",");
                if (doneeData.length >= 4) {
                    Donee donee = new Donee(doneeData[0], doneeData[1], doneeData[2], doneeData[3]);

                    // Check if there are any donation IDs
                    if (doneeData.length > 4 && !doneeData[4].trim().isEmpty()) {
                        String[] donationIDs = doneeData[4].split(";");

                        // Retrieve donations from the map and add them to the donee
                        for (String donationID : donationIDs) {
                            Donation donation = donationMap.get(donationID.trim());
                            if (donation != null) {
                                donee.addDonation(donation);
                            }
                        }
                    }

                    doneeList.add(donee);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading donees from file: " + e.getMessage());
        }

        return doneeList;
    }
    
    //retrieve donee(hashMap)
     public HashMap<String, Donee> loadDoneesIntoMap() {

        HashMapInterface<String, Donation> donationMap = retrieveDonations();
        HashMap<String, Donee> doneeMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("donee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] doneeData = line.split(",");
                if (doneeData.length >= 4) {
                    Donee donee = new Donee(doneeData[0], doneeData[1], doneeData[2], doneeData[3]);

                    
                    if (doneeData.length > 4 && !doneeData[4].trim().isEmpty()) {
                        String[] donationIDs = doneeData[4].split(";");
                        for (String donationID : donationIDs) {
          
                             Donation donation = donationMap.get(donationID.trim());
                            if (donation != null) {
                                donee.addDonation(donation);
                            }
                        }
                    }

                    doneeMap.put(donee.getId(), donee); // Add donee to the map
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading donees from file: " + e.getMessage());
        }

        return doneeMap;
    }

    //retrieve donation(HashMap)
    public HashMapInterface<String, Donation> retrieveDonations() {
        HashMapInterface<String, Donation> donationMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("donation.txt"))) {
            String line;
            String currentDonationId = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data.length == 3) {
                    // Donation record
                    String donationId = data[0];
                    String description = data[1];
                    double cashAmount = Double.parseDouble(data[2]);
                    currentDonationId = donationId;

                    // Create or update donation record
                    Donation donation = donationMap.get(donationId);
                    if (donation == null) {
                        donation = new Donation(donationId, description, cashAmount);
                        donationMap.put(donationId, donation);
                    } else {
                        // Update description and amount if donation already exists
                        donation.setDescription(description);
                        donation.setCashAmount(cashAmount);
                    }
                } else if (data.length == 2) {
                    // Donated item
                    String itemName = data[0];
                    int quantity = Integer.parseInt(data[1]);

                    // Find existing donation
                    Donation donation = donationMap.get(currentDonationId);
                    if (donation != null) {
                        DonatedItem item = new DonatedItem(itemName, quantity);
                        donation.getItems().add(item);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading donations from file: " + e.getMessage());
        }

        return donationMap;
    }

    //Retrieve Donation(Array)
    public ListInterface<Donation> retrieveDonations(ListInterface<Donee> doneeList) {
        ListInterface<Donation> donationList = new LinkedList<>();
        HashMapInterface<String, Donation> donationMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("donation.txt"))) {
            String line;
            String existId = null;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data.length == 3) {
                    String donationId = data[0];
                    String description = data[1];
                    double cashAmount = Double.parseDouble(data[2]);
                    existId = donationId;

                    // Create or update donation record
                    Donation donation = donationMap.get(donationId);
                    if (donation == null) {
                        donation = new Donation(donationId, description, cashAmount);
                        donationList.add(donation);
                        donationMap.put(donationId, donation);
                    } else {
                        // Update description and amount if donation already exists
                        donation.setDescription(description);
                        donation.setCashAmount(cashAmount);
                    }
                } else if (data.length == 2) {
           
                    String itemName = data[0];
                    int quantity = Integer.parseInt(data[1]);

                    // Find existing donation
                    Donation donation = donationMap.get(existId);
                    if (donation != null) {
                        DonatedItem item = new DonatedItem(itemName, quantity);
                        donation.getItems().add(item);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading donations from file: " + e.getMessage());
        }

        //Remove donations already taken by donees
        for (int i = 0; i < doneeList.size(); i++) {
            Donee donee = doneeList.getEntry(i);
            //ListInterface<Donation> doneeDonations = donee.getDonations();
            ListInterface<Donation> doneeDonations = donee.getDonations();
            for (int j = 0; j < doneeDonations.size(); j++) {
                Donation doneeDonation = doneeDonations.getEntry(j);
                if (doneeDonation != null) {
                    donationMap.remove(doneeDonation.getDonationId());
                    donationList.remove(doneeDonation);
                }
            }
        }

        return donationList;
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
