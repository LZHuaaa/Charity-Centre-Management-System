package entity;

import adt.*;


public class Donation  {

    private String description;
    private double cashAmount;
    private ListInterface<DonatedItem> items;

    public Donation(String description, double cashAmount) {

        this.description = description;
        this.cashAmount = cashAmount;
        this.items = new ArrayList<>();
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

}
