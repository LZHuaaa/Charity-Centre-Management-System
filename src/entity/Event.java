/**
 *
 * @author Chia Yuxuan
 */
package entity;
import java.util.Objects;  

public class Event implements Comparable<Event> {

    private String eventId;
    private String name;
    private String date;
    private String location;
    private String description;

    public Event(String eventId, String name, String date, String location, String description) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public Event(String eventId) {
        if (eventId == null || eventId.isEmpty()) {
            throw new IllegalArgumentException("Event ID cannot be null or empty.");
        }
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
    
     public void setId(String id) {  
        this.eventId = id;  
    }  

 
    public void setName(String name) {  
        this.name = name;  
    }  


    public void setDate(String date) {  
        this.date = date;  
    }  


    public void setLocation(String location) {  
        this.location = location;  
    }   

    public void setDescription(String description) {  
        this.description = description;  
    } 

    @Override
    public String toString() {
        return "Event{"
                + "id='" + eventId + '\''
                + ", name='" + name + '\''
                + ", date='" + date + '\''
                + ", location='" + location + '\''
                + ", description='" + description + '\''
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
        Event event = (Event) obj;
        return eventId.equals(event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    @Override
    public int compareTo(Event o) {
        return this.date.compareTo(o.date); // Compare by date  
    }
}
