/**
 *
 * @author Chia Yuxuan
 */
package control;

import adt.*;
import dao.VolunteerDAO;
import entity.Volunteer;
import entity.Event;
import utility.MessageUI;
import boundary.VolunteerUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VolunteerMaintenance {
    private VolunteerDAO volunteerDAO;

    public VolunteerMaintenance() {
        volunteerDAO = new VolunteerDAO(); // Initializes VolunteerDAO and loads from file
    }
    
    public void addVolunteer(Volunteer volunteer) {
        volunteerDAO.add(volunteer);
        MessageUI.showInfo("\n\nVolunteer added successfully!");
    }

    public boolean removeVolunteer(String volunteerId) {
        boolean success = volunteerDAO.remove(volunteerId);
        if (success) {
            MessageUI.showInfo("\nVolunteer removed successfully!");
        } else {
            MessageUI.showError("Volunteer ID not found.");
        }
        return success;
    }

    public Volunteer searchVolunteer(String volunteerId) {
        Volunteer volunteer = volunteerDAO.get(volunteerId);
        if (volunteer != null) {
            return volunteer;
        } else {
            MessageUI.showError("Volunteer ID not found.");
            return null;
        }
    }

    public void assignEventToVolunteer(String volunteerId, Event event) {
        Volunteer volunteer = volunteerDAO.get(volunteerId);
        if (volunteer != null) {
            volunteer.addEvent(event);
            volunteerDAO.update(volunteer); // Save updated volunteer details
        } 
    }
    
    public ListInterface<Event> searchEventsUnderVolunteer(String volunteerId) {
    ListInterface<Event> assignedEvents = new ArrayList<>(); // Initialize a new list to store assigned events

    try (BufferedReader reader = new BufferedReader(new FileReader("volunteer_event.txt"))) {
        String line;

        // Loop through volunteer_event.txt to find the volunteer's assigned events
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String fileVolunteerId = parts[0].trim();
                String eventId = parts[1].trim();

                // Check if this line corresponds to the volunteerId we are looking for
                if (fileVolunteerId.equals(volunteerId)) {
                    // Fetch the Event object based on eventId
                    Event event = findEventById(eventId);
                    if (event != null) {
                        assignedEvents.add(event); // Add the event to the list
                    }
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return assignedEvents;
}

// Helper method to find an Event by its ID (from events.txt or in-memory list)
public Event findEventById(String eventId) {
    ListInterface<Event> allEvents = volunteerDAO.loadEventsFromFile(); // Load all events from file or memory
    for (int i = 0; i < allEvents.size(); i++) {
        Event event = allEvents.getEntry(i);
        if (event.getEventId().equals(eventId)) {
            return event; // Return the matching event
        }
    }
    return null; // Return null if no event is found
}

    public ListInterface<Volunteer> listAllVolunteers() {
        return volunteerDAO.getAllVolunteers();
    }

    public ListInterface<Volunteer> filterVolunteers(String criteria) {
        return volunteerDAO.filter(criteria);
    }

    public void generateDetailedReport() {
        ListInterface<Volunteer> volunteers = volunteerDAO.getAllVolunteers();
        if (volunteers.isEmpty()) {
            MessageUI.showInfo("No volunteers available to generate report.");
            return;
        }

        int totalVolunteers = volunteers.size();
        int totalEvents = 0;
        StringBuilder reportBuilder = new StringBuilder();

        reportBuilder.append("\nSummary Report\n");
        reportBuilder.append("---------------------------------------------------------------------------------\n");
        reportBuilder.append(String.format("%-15s %-20s %-15s %-30s\n", "Volunteer ID", "Name", "Contact Number", "Email"));
        reportBuilder.append("---------------------------------------------------------------------------------\n");

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.getEntry(i);
            
             // Load the volunteer's assigned events from the volunteer_event.txt file
            ListInterface<Event> assignedEvents = searchEventsUnderVolunteer(volunteer.getVolunteerId());
            volunteer.setEvents(assignedEvents); // Set the events in the volunteer object
            
            totalEvents += volunteer.getEvents().size();
            reportBuilder.append(String.format("%-15s %-20s %-15s %-30s\n", 
                volunteer.getVolunteerId(), 
                volunteer.getName(), 
                volunteer.getContactNumber(), 
                volunteer.getEmail()));
        }

        reportBuilder.append("\n---------------------------------------------------------------------------------\n");
        reportBuilder.append("Total Volunteers: ").append(totalVolunteers).append("\n");
        reportBuilder.append("Total Events Assigned: ").append(totalEvents).append("\n");
        reportBuilder.append("---------------------------------------------------------------------------------\n\n");

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.getEntry(i);
            reportBuilder.append(String.format("Volunteer ID: %-15s Name: %-20s\n", 
                volunteer.getVolunteerId(), 
                volunteer.getName()));

            ListInterface<Event> events = volunteer.getEvents();
            if (events != null && !events.isEmpty()) {
                reportBuilder.append("Assigned Events:\n");
                reportBuilder.append(String.format("%-10s %-25s %-15s %-20s %-30s\n", 
                    "Event ID", "Event Name", "Event Date", "Location", "Description"));
                for (int j = 0; j < events.size(); j++) {
                    Event event = events.getEntry(j);
                    reportBuilder.append(String.format("%-10s %-25s %-15s %-20s %-30s\n", 
                        event.getEventId(), 
                        event.getName(), 
                        event.getDate(), 
                        event.getLocation(), 
                        event.getDescription()));
                }
            } else {
                reportBuilder.append("No events assigned.\n");
            }
            reportBuilder.append("-------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }

        MessageUI.showInfo(reportBuilder.toString());
    }

    public static void main(String[] args) {
        VolunteerUI volunteerUI = new VolunteerUI();
        volunteerUI.start();
    }
}