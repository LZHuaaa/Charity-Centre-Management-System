/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author eyong
 */
import adt.*;
import entity.*;
import java.io.*;

public class VolunteerEventDAO {

    private HashMap<Volunteer, HashSet<Event>> volunteerEventMap = new HashMap<>();
    private static final String FILE_PATH = "volunteer_event.txt";

    public VolunteerEventDAO() {
        volunteerEventMap = new HashMap<>();
        loadVolunteerEvents(); // Load events from file during initialization
    }

    // Function 1: Load (Retrieve) Volunteer Events from the File
    public void loadVolunteerEvents() {
        volunteerEventMap.clear(); // Clear any existing data
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    String volunteerId = parts[0].trim();
                    String eventId = parts[1].trim();

                    // Create Volunteer and Event objects using ID
                    Volunteer volunteer = new Volunteer(volunteerId);
                    Event event = new Event(eventId);

                    // Add the volunteer and event to the HashMap
                    if (!volunteerEventMap.containsKey(volunteer)) {
                        volunteerEventMap.put(volunteer, new HashSet<>());
                    }
                    volunteerEventMap.get(volunteer).add(event);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading volunteer events: " + e.getMessage());
        }
    }

    // Function 2: Retrieve the Map (for use elsewhere in the system)
    public HashMap<Volunteer, HashSet<Event>> retrieveVolunteerEvents() {
        return volunteerEventMap;
    }

    // Function 3: Save Volunteer Events to the File
    public void saveVolunteerEvents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Volunteer volunteer : volunteerEventMap.keySet()) {
                for (Event event : volunteerEventMap.get(volunteer)) {
                    writer.write(volunteer.getId() + ", " + event.getId());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving volunteer events: " + e.getMessage());
        }
    }

    // Additional helper functions (if needed):
    // Check if a volunteer is already mapped to an event
    public boolean isVolunteerMappedToEvent(Volunteer volunteer, Event event) {
        return volunteerEventMap.containsKey(volunteer) && volunteerEventMap.get(volunteer).contains(event);
    }

    // Remove a specific event for a volunteer
    public boolean removeEventFromVolunteer(Volunteer volunteer, Event event) {
        if (volunteerEventMap.containsKey(volunteer)) {
            HashSet<Event> events = volunteerEventMap.get(volunteer);
            return events.remove(event);
        }
        return false;
    }

//    // Save volunteer-event mappings to a file
//    public boolean saveVolunteerEvents() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//            for (Map.Entry<Volunteer, HashSet<Event>> entry : volunteerEventMap.entrySet()) {
//                Volunteer volunteer = entry.getKey();
//                HashSet<Event> events = entry.getValue();
//                for (Event event : events) {
//                    writer.write(volunteer.getId() + "," + event.getId());
//                    writer.newLine();
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Error writing volunteer events to file: " + e.getMessage());
//            return false;
//        }
//        return true;
//    }
//
//    // Retrieve volunteer-event mappings from a file
//    public void retrieveVolunteerEvents() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length == 2) {
//                    String volunteerId = parts[0];
//                    String eventId = parts[1];
//                    
//                    // Assuming that getVolunteerById and getEventById methods are available
//                    Volunteer volunteer = getVolunteerById(volunteerId);
//                    Event event = getEventById(eventId);
//                    
//                    if (volunteer != null && event != null) {
//                        HashSet<Event> events = volunteerEventMap.get(volunteer);
//                        if (events == null) {
//                            events = new HashSet<>();
//                            volunteerEventMap.put(volunteer, events);
//                        }
//                        events.add(event);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading volunteer events from file: " + e.getMessage());
//        }
//    }
//
//    // Helper method to get Volunteer by ID
//    private Volunteer getVolunteerById(String id) {
//        // Implement logic to retrieve Volunteer object by ID
//        return null;
//    }
//
//    // Helper method to get Event by ID
//    private Event getEventById(String id) {
//        // Implement logic to retrieve Event object by ID
//        return null;
//    }
//    
//    // Add a volunteer-event mapping
//    public void addVolunteerEvent(Volunteer volunteer, Event event) {
//        HashSet<Event> events = volunteerEventMap.get(volunteer);
//        if (events == null) {
//            events = new HashSet<>();
//            volunteerEventMap.put(volunteer, events);
//        }
//        events.add(event);
//    }
//
//    // Remove a volunteer-event mapping
//    public void removeVolunteerEvent(Volunteer volunteer, Event event) {
//        HashSet<Event> events = volunteerEventMap.get(volunteer);
//        if (events != null) {
//            events.remove(event);
//            if (events.isEmpty()) {
//                volunteerEventMap.remove(volunteer);
//            }
//        }
//    }
//
//    // List all events for a volunteer
//    public HashSet<Event> listEventsForVolunteer(Volunteer volunteer) {
//        return volunteerEventMap.getOrDefault(volunteer, new HashSet<>());
//    }
//    
}
