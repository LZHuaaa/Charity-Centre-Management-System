/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import adt.HashSet;
/**
 *
 * @author eyong
 */
import java.util.Objects;  

public class VolunteerEvent {  
    private Volunteer volunteer;  
    private HashSet<Event> events;  

    public VolunteerEvent(Volunteer volunteer) {  
        this.volunteer = volunteer;  
        this.events = new HashSet<>();  
    }  

    public Volunteer getVolunteer() {  
        return volunteer;  
    }  

    public HashSet<Event> getEvents() {  
        return events;  
    }  

    public void addEvent(Event event) {  
        events.add(event);  
    }  

    public void removeEvent(Event event) {  
        events.remove(event);  
    }  

    public boolean hasEvent(Event event) {  
        return events.contains(event);  
    }  

    @Override  
    public String toString() {  
        return "VolunteerEvent{" +  
                "volunteer=" + volunteer +  
                ", events=" + events +  
                '}';  
    }  

    @Override  
    public boolean equals(Object obj) {  
        if (this == obj) return true;  
        if (obj == null || getClass() != obj.getClass()) return false;  
        VolunteerEvent that = (VolunteerEvent) obj;  
        return volunteer.equals(that.volunteer) && events.equals(that.events);  
    }  

    @Override  
    public int hashCode() {  
        return Objects.hash(volunteer, events);  
    }  
}  