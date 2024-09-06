/**
 *
 * @author Chia Yuxuan
 */
package entity;

public class Event {
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
}