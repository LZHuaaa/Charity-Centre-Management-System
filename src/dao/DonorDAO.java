package dao;

import entity.*;
import adt.*;
import java.io.*;

public class DonorDAO {

    private static final String FILE_NAME = "donor.txt";

    // Save donor data to the text file
    public void saveDonors(ListInterface<Donor> donorList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < donorList.size(); i++) {
                Donor donor = donorList.getEntry(i);
                writer.write(donor.getDonorId() + "," + donor.getName() + "," + donor.getDonorType() + "," + donor.getAge() + "\n");

                // Save donations
                ListInterface<Donation> donations = donor.getDonations();
                for (int j = 0; j < donations.size(); j++) {
                    Donation donation = donations.getEntry(j);
                    writer.write(donation.getDonationId() + "," + donation.getDescription() + "," + donation.getCashAmount() + "\n");

                    // Save donated items
                    ListInterface<DonatedItem> items = donation.getItems();
                    for (int k = 0; k < items.size(); k++) {
                        DonatedItem item = items.getEntry(k);
                        writer.write(item.getName() + "," + item.getQuantity() + "\n");
                    }
                    writer.write("END_OF_DONATION\n");
                }
                writer.write("END_OF_DONOR\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving donor data: " + e.getMessage());
        }
    }

    // Load donor data from the text file
    public ListInterface<Donor> loadDonors() {
        ListInterface<Donor> donorList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            Donor donor = null;
            ListInterface<Donation> donations = null;
            ListInterface<DonatedItem> items = null;
            Donation donation = null;

            while ((line = reader.readLine()) != null) {
                if (line.equals("END_OF_DONOR")) {
                    if (donor != null) {
                        donor.setDonations(donations); // Set the donations list to the donor
                        donorList.add(donor); // Add the donor to the list
                    }
                    donor = null;
                } else if (line.equals("END_OF_DONATION")) {
                    if (donation != null) {
                        donation.setItems(items); // Set the items list to the donation
                        donations.add(donation); // Add the donation to the donations list
                        donation = null;
                    }
                } else {
                    String[] parts = line.split(",");
                    if (parts.length == 4 && donor == null) {
                        // This is a Donor line
                        donor = new Donor(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
                        donations = new ArrayList<>();
                    } else if (parts.length == 3 && donation == null) {
                        // This is a Donation line
                        donation = new Donation(parts[0], parts[1], Double.parseDouble(parts[2]));
                        items = new ArrayList<>();
                    } else if (parts.length == 2 && donation != null) {
                        // This is a DonatedItem line
                        DonatedItem item = new DonatedItem(parts[0], Integer.parseInt(parts[1]));
                        items.add(item);
                    }
                }
            }

            // Add the last donor in case the file ends without "END_OF_DONOR"
            if (donor != null) {
                donor.setDonations(donations);
                donorList.add(donor);
            }

        } catch (IOException e) {
            System.out.println("Error loading donor data: " + e.getMessage());
        }
        return donorList;
    }
}
