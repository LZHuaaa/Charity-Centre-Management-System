//author Chia Yuxuan
package entity;

import adt.ArrayList;
import adt.ListInterface;

public class Volunteer {
    private String volunteerId;
    private String name;
    private String contactNumber;
    private String email;
    private ListInterface<Event> events; // List of events assigned to the volunteer

    public Volunteer(String volunteerId, String name, String contactNumber, String email) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.events = new ArrayList<>(); // Initialize the list of events
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ListInterface<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event); // Add event to the list
    }
    
}




/**
package entity;

import adt.ListInterface;
import adt.ArrayList;

public class Volunteer {
    private String volunteerId;
    private String name;
    private String contactNumber;
    private String email;
    private ListInterface<Event> events;

    public Volunteer(String volunteerId, String name, String contactNumber, String email) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.events = new ArrayList<>();
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public ListInterface<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }
}
**/