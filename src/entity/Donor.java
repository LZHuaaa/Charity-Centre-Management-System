package entity;

import adt.*;
import java.util.Objects;
import java.io.Serializable;

public class Donor implements Serializable {

    private String donorId;
    private String name;
    private int age;
    private String donorType; // government, private, public
    private ListInterface<Donation> donations;

    public Donor(String donorId, String name, String donorType, int age) {
        if (donorId == null || name == null || donorType == null) {
            throw new IllegalArgumentException("Donor ID, name, and type cannot be null");
        }
        if (!donorType.equals("government") && !donorType.equals("private") && !donorType.equals("public")) {
            throw new IllegalArgumentException("Invalid donor type");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.donorId = donorId;
        this.name = name;
        this.donorType = donorType;
        this.age = age;
        this.donations = new ArrayList<>(); // Initialize with ArrayList
    }

    // Getter and setter methods for all attributes
    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        if (donorId == null) {
            throw new IllegalArgumentException("Donor ID cannot be null");
        }
        this.donorId = donorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    public String getDonorType() {
        return donorType;
    }

    public void setDonorType(String donorType) {
        if (donorType == null || (!donorType.equals("government") && !donorType.equals("private") && !donorType.equals("public"))) {
            throw new IllegalArgumentException("Invalid donor type");
        }
        this.donorType = donorType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }

    public void addDonation(String donationId, String description, double cashAmount, String itemName, int quantity) {
        if (donationId == null || description == null || itemName == null) {
            throw new IllegalArgumentException("Donation ID, description, and item name cannot be null");
        }
        if (cashAmount < 0) {
            throw new IllegalArgumentException("Cash amount cannot be negative");
        }

        // Create a new DonatedItem
        DonatedItem item = new DonatedItem(itemName, quantity);

        // Create a new Donation and add the item to it
        Donation donation = new Donation(donationId, description, cashAmount);
        donation.getItems().add(item); // Assuming add method is available in ListInterface

        // Add the donation to the donor's donations list
        donations.add(donation); // Assuming add method is available in ListInterface
    }

    public ListInterface<Donation> getDonations() {
        return donations;
    }

    public void setDonations(ListInterface<Donation> donations) {
        if (donations == null) {
            throw new IllegalArgumentException("Donations list cannot be null");
        }
        this.donations = donations;
    }

    @Override
    public String toString() {
        return "\nDonor - \n"
                + "Id: [" + donorId + ']'
                + ", Name: [" + name + ']'
                + ", Age: " + "[" + age + "]"
                + ", Donor Type: [" + donorType + ']';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Donor donor = (Donor) obj;
        return donorId.equals(donor.donorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(donorId);
    }
}
