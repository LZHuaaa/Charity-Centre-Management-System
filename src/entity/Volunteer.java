/**
 *
 * @author Chia Yuxuan
 */
package entity;

import adt.ArrayList;
import adt.ListInterface;
import java.util.Objects;

public class Volunteer implements Comparable<Volunteer>{
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

    public void setEvents(ListInterface<Event> assignedEvents) {
        this.events = assignedEvents; // Store the assigned events in the Volunteer object   
    }   
    
    @Override  
    public String toString() {  
        return "Volunteer{" +  
                "id='" + volunteerId + '\'' +  
                ", name='" + name + '\'' +  
                ", phoneNumber='" + contactNumber + '\'' +  
                ", email='" + email + '\'' +  
                '}';  
    }  

    @Override  
    public boolean equals(Object obj) {  
        if (this == obj) return true;  
        if (obj == null || getClass() != obj.getClass()) return false;  
        Volunteer volunteer = (Volunteer) obj;  
        return volunteerId.equals(volunteer.volunteerId);  
    }  

    @Override  
    public int hashCode() {  
        return Objects.hash(volunteerId);  
    }  

    @Override  
    public int compareTo(Volunteer o) {  
        return this.name.compareTo(o.name);  
    }  
}