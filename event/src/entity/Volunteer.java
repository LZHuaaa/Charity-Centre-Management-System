/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author eyong
 */
import java.util.Objects;  
import adt.*;

public class Volunteer implements Comparable<Volunteer> {  
    private String id;  
    private String name;  
    private String phoneNumber;  
    private String email;  
    private ArrayList<Event> events; // List of events the volunteer is associated with  

    public Volunteer() {  
        this.events = new ArrayList<>();  
    }  

    public Volunteer(String id, String name, String phoneNumber, String email) {  
        this.id = id;  
        this.name = name;  
        this.phoneNumber = phoneNumber;  
        this.email = email;  
        this.events = new ArrayList<>();  
    }  
    
    public Volunteer(String id) {
        this.id = id;
        this.events = new ArrayList<>();
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

    public String getPhoneNumber() {  
        return phoneNumber;  
    }  

    public void setPhoneNumber(String phoneNumber) {  
        this.phoneNumber = phoneNumber;  
    }  

    public String getEmail() {  
        return email;  
    }  

    public void setEmail(String email) {  
        this.email = email;  
    }  

    public ArrayList<Event> getEvents() {  
        return events;  
    }  

    public void setEvents(ArrayList<Event> events) {  
        this.events = events;  
    }  

    // Methods  
    public void addEvent(Event event) {  
        events.add(event);  
    }  

    public void removeEvent(Event event) {  
        events.remove(event);  
    }  

    public void updateEvent(Event oldEvent, Event newEvent) {  
        int index = events.indexOf(oldEvent);  
        if (index != -1) {  
            events.replace(index, newEvent);  
        }  
    }  

    @Override  
    public String toString() {  
        return "Volunteer{" +  
                "id='" + id + '\'' +  
                ", name='" + name + '\'' +  
                ", phoneNumber='" + phoneNumber + '\'' +  
                ", email='" + email + '\'' +  
                ", events=" + events +  
                '}';  
    }  

    @Override  
    public boolean equals(Object obj) {  
        if (this == obj) return true;  
        if (obj == null || getClass() != obj.getClass()) return false;  
        Volunteer volunteer = (Volunteer) obj;  
        return id.equals(volunteer.id);  
    }  

    @Override  
    public int hashCode() {  
        return Objects.hash(id);  
    }  

    @Override  
    public int compareTo(Volunteer o) {  
        return this.name.compareTo(o.name);  
    }  
}  