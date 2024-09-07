package entity;

import adt.*;
import java.util.Objects;

public class Donation {

    private String donationId;
    private String description;
    private double cashAmount;
    private ListInterface<DonatedItem> items;

    public Donation(String id, String description, double cashAmount) {
        this.donationId = id;
        this.description = description;
        this.cashAmount = cashAmount;
        this.items = new ArrayList<>();
    }

    public String getDonationId() {
        return donationId;
    }

    public void setDonationId(String id) {
        this.donationId = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public ListInterface<DonatedItem> getItems() {
        return items;
    }

    public void setItems(ListInterface<DonatedItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Donation{"
                + "description='" + description + '\''
                + ", cashAmount=" + cashAmount
                + ", items=" + items
                + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Donation donation = (Donation) obj;
        return donationId.equals(donation.donationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(donationId);
    }

}
