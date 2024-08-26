/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;


import java.util.Objects;
import adt.*;
import dao.*;
import control.*;

/**
 *
 * @author leezh
 */
public class Donee {
    
    private String id;
    private String name;
    private String type;
    private String contactNo;
    private ArrayList<Donation> donations;

    public Donee(String id, String name, String type, String contactNo) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.contactNo = contactNo;
        this.donations = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public ArrayList<Donation> getDonations() {
        return donations;
    }

    public void setDonations(ArrayList<Donation> donations) {
        this.donations = donations;
    }

    // Methods
    public void addDonation(Donation donation) {
        donations.add(donation);
    }

    public void removeDonation(Donation donation) {
        donations.remove(donation);
    }

    public void updateDonation(Donation oldDonation, Donation newDonation) {
        int index = -1;
        for (int i = 0; i < donations.size(); i++) {
            if (donations.getEntry(i).equals(oldDonation)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            donations.remove(oldDonation);
            donations.add(newDonation);
        }
    }

    @Override
    public String toString() {
        return "Donee{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", type='" + type + '\''
                + ", contactInfo='" + contactNo + '\''
                + ", donations=" + donations
                + '}';
    }
}
