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

public class Event implements Comparable<Event> {  
    private String id;  
    private String name;  
    private String date; // Consider using LocalDate for more robust date handling  
    private String location;  
    private String description;  

    public Event() {  
    }  

    public Event(String id, String name, String date, String location, String description) {  
        this.id = id;  
        this.name = name;  
        this.date = date;  
        this.location = location;  
        this.description = description;  
    }  
    public Event(String id) {
        this.id = id;
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

    public String getDate() {  
        return date;  
    }  

    public void setDate(String date) {  
        this.date = date;  
    }  

    public String getLocation() {  
        return location;  
    }  

    public void setLocation(String location) {  
        this.location = location;  
    }  

    public String getDescription() {  
        return description;  
    }  

    public void setDescription(String description) {  
        this.description = description;  
    }  

    @Override  
    public String toString() {  
        return "Event{" +  
                "id='" + id + '\'' +  
                ", name='" + name + '\'' +  
                ", date='" + date + '\'' +  
                ", location='" + location + '\'' +  
                ", description='" + description + '\'' +  
                '}';  
    }  

    @Override  
    public boolean equals(Object obj) {  
        if (this == obj) return true;  
        if (obj == null || getClass() != obj.getClass()) return false;  
        Event event = (Event) obj;  
        return id.equals(event.id);  
    }  

    @Override  
    public int hashCode() {  
        return Objects.hash(id);  
    }  

    @Override  
    public int compareTo(Event o) {  
        return this.date.compareTo(o.date); // Compare by date  
    }  
}  