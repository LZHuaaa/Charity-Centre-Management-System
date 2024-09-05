/*
 * @author Lee Zhi Hua
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
public class Donee implements Comparable<Donee> {

    private String id;
    private String name;
    private String type;
    private String contactNo;
    private LinkedList<Donation> donations;

    public Donee() {

    }

    public Donee(String id, String name, String type, String contactNo) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.contactNo = contactNo;
        this.donations = new LinkedList<>();

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

    public LinkedList<Donation> getDonations() {
        return donations;
    }

    public void setDonations(LinkedList<Donation> donations) {
        this.donations = donations;
    }

    // Methods
    public void addDonation(Donation donation) {
        donations.add(donation);
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Donee donee = (Donee) obj;
        return id.equals(donee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Donee o) {
        return this.type.compareTo(o.type);
    }

}
