/*  
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license  
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template  
 */
package control;

/**
 *
 * @author eyong
 */
import adt.ArrayList;
import adt.HashMap;
import adt.HashSet;
import dao.*;
import boundary.EventManagementUI;
import entity.*;

import java.util.Iterator;

public class EventManagement {

    private ArrayList<Event> eventList = new ArrayList<>();
    private HashSet<Event> eventSet = new HashSet<>(); // For adding and removing events  
    private HashMap<String, Event> eventMap = new HashMap<>(); // For searching and updating events  
    public HashMap<String, Volunteer> volunteerMap = new HashMap<>(); // For searching and updating events  
    private HashMap<String, VolunteerEvent> volunteerEventMap = new HashMap<>();
    private HashSet<VolunteerEvent> volunteerSet = new HashSet<>();
    private HashMap<Volunteer, HashSet<Event>> volunteerEvents = new HashMap<>();
    private final VolunteerEventDAO volunteerEventDAO;
    private final EventDAO eventDAO;
    private final VolunteerDAO volunteerDAO;
    private final EventManagementUI eventManagementUI;
    private ArrayList<String> availableIDs = new ArrayList<>();

    public EventManagement(String volunteerFilePath, String eventFilePath, String volunteerFile) {
        this.volunteerEventDAO = new VolunteerEventDAO(volunteerFilePath);
        this.eventDAO = new EventDAO(eventFilePath);
        this.volunteerDAO = new VolunteerDAO(volunteerFile);
        this.eventManagementUI = new EventManagementUI();

        loadAllEvents();
        loadVolunteerEvents();
    }

    private void loadAllEvents() {
        eventList = eventDAO.loadIntoArrayList();
        eventSet = eventDAO.loadIntoHashSet();
        eventMap = eventDAO.loadIntoHashMap();
        volunteerEventMap = volunteerEventDAO.loadIntoHashMap();
        volunteerSet = volunteerEventDAO.loadIntoHashSet();
        volunteerMap = volunteerDAO.loadIntoHashMap();
    }

    private void loadVolunteerEvents() {
        for (String volunteerId : volunteerEventMap.keySet()) {
            VolunteerEvent volunteerEvent = volunteerEventMap.get(volunteerId);
            Volunteer volunteer = volunteerMap.get(volunteerId); // Get Volunteer object  
            if (volunteer != null && volunteerEvent != null) {
                HashSet<Event> events = volunteerEvent.getEvents();
                volunteerEvents.put(volunteer, events);
            }
        }
    }

//    private void loadVolunteerEvents() {
//        HashMap<Volunteer, HashSet<Event>> loadedVolunteerEventMap = volunteerEventDAO.retrieveVolunteerEvents();
//        volunteerEventMap.putAll(loadedVolunteerEventMap);
//    }
    public void runEventManagement() {
        int choice;
        do {
            choice = eventManagementUI.menu();
            switch (choice) {
                case 0:
                    System.out.println("Exiting Event Management System.");
                    break;
                case 1:
                    addEvent();
                    break;
                case 2:
                    removeEvent();
                    break;
                case 3:
                    updateEventDetails();
                    break;
                case 4:
                    searchEvent();
                    break;
                case 5:
                    listAllEvents();
                    break;
                case 6:
                    generateEventSummaryReport();
                    break;
                case 7:
                    removeEventFromVolunteer();
                    break;
                case 8:
                    listEventsForVolunteer();
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }

    public void addEvent() {
        // Generate a unique event ID based on the current size of the eventSet  
        String newId = generateEventID();

        // Pass the new ID to the UI method to create a new event  
        Event newEvent = eventManagementUI.addEvent(eventSet, newId);

        if (newEvent != null) {
            eventSet.add(newEvent); // Add to the HashSet  
            eventMap.put(newEvent.getId(), newEvent); // Add to the HashMap
            eventDAO.save(eventSet); // Save to the data store if needed  
            System.out.println("\nEvent added successfully!");
        }
    }

    public String generateEventID() {
        if (!availableIDs.isEmpty()) {
            return availableIDs.remove(availableIDs.size() - 1); // Use recycled ID  
        }

        // Create a new ID based on the highest current ID  
        int newIdNumber = 1; // Start from 1  
        while (true) {
            String newId = String.format("E%03d", newIdNumber);
            if (!eventMap.containsKey(newId)) { // Check if the ID is already in use  
                return newId; // Return the new unique ID  
            }
            newIdNumber++; // Increment to try the next ID  
        }
    }

    public void removeEvent() {
        String eventId = eventManagementUI.removeEvent(eventMap);
        if (eventId != null) {
            Event eventToRemove = null;

            // Find the event to remove based on the eventId  
            for (Event event : eventSet) {
                if (event.getId().equals(eventId)) {
                    eventToRemove = event; // Store the event to remove  
                    break; // Exit the loop once found  
                }
            }

            // If the event was found, remove it using the custom remove method  
            if (eventToRemove != null) {
                boolean removed = eventSet.remove(eventToRemove); // Use the custom remove method  
                if (removed) {
                    eventDAO.save(eventSet); // Save the updated set to the data store  
                    System.out.println("\nEvent removed successfully!");
                } else {
                    System.out.println("\nFailed to remove the event.");
                }
            } else {
                System.out.println("\nEvent ID not found.");
            }
        }
    }

    public void updateEventDetails() {
        String eventId = eventManagementUI.updateEvent(eventMap);
        if (eventId != null) {
            Event event = eventMap.get(eventId);
            if (event != null) {
                // Remove the old event from the eventSet  
                eventSet.remove(event);

                // The event object is already updated through the UI method  
                // Add the updated event back to the eventSet  
                eventSet.add(event);

                // Save the updated event set to the data store  
                eventDAO.save(eventSet);
                System.out.println("Event updated successfully!");
            } else {
                System.out.println("Event not found.");
            }
        } else {
            System.out.println("Update operation canceled.");
        }
    }

    public void searchEvent() {

        eventManagementUI.searchEvent(eventMap);
    }

    public void listAllEvents() {
        // Load all events using eventDAO (which includes complete details)
        ArrayList<Event> eventList = eventDAO.loadIntoArrayList();

        // Sort the event list based on event IDs using available methods (getEntry, replace)
        for (int i = 0; i < eventList.size() - 1; i++) {
            for (int j = 0; j < eventList.size() - 1 - i; j++) {
                Event event1 = eventList.getEntry(j);
                Event event2 = eventList.getEntry(j + 1);

                // Compare event IDs
                if (event1.getId().compareTo(event2.getId()) > 0) {
                    // Swap the events using replace method
                    eventList.replace(j, event2);
                    eventList.replace(j + 1, event1);
                }
            }
        }

        // Pass the sorted eventList to the UI
        eventManagementUI.listEvents(eventList);
    }

    public void generateEventSummaryReport() {
        HashMap<String, Integer> eventParticipants = new HashMap<>(); // Use your custom map

        // Iterate through the volunteerEventMap to count participants for each event
        for (String volunteerId : volunteerEventMap.keySet()) {
            VolunteerEvent volunteerEvent = volunteerEventMap.get(volunteerId);
            for (Event event : volunteerEvent.getEvents()) {
                String eventId = event.getId();
                int currentCount = eventParticipants.getOrDefault(eventId, 0); // Get the current count (0 if it doesn't exist)
                eventParticipants.put(eventId, currentCount + 1); // Increment the count
            }
        }

        // Load all events using eventDAO (which includes complete details)
        ArrayList<Event> completeEventList = eventDAO.loadIntoArrayList();

        // Sort the event list based on event IDs using available methods (getEntry, replace)
        for (int i = 0; i < completeEventList.size() - 1; i++) {
            for (int j = 0; j < completeEventList.size() - 1 - i; j++) {
                Event event1 = completeEventList.getEntry(j);
                Event event2 = completeEventList.getEntry(j + 1);

                // Compare event IDs
                if (event1.getId().compareTo(event2.getId()) > 0) {
                    // Swap the events using replace method
                    completeEventList.replace(j, event2);
                    completeEventList.replace(j + 1, event1);
                }
            }
        }

        int totalVolunteers = volunteerMap.size(); // Total number of volunteers
        int totalEvents = completeEventList.size(); // Total number of events

        // Calculate total participants
        int totalParticipants = 0;
        for (String eventId : eventParticipants.keySet()) {
            totalParticipants += eventParticipants.get(eventId);
        }

        double averageEventsPerVolunteer = totalVolunteers > 0 ? (double) totalParticipants / totalVolunteers : 0;

        // Call the boundary method to generate the report
        eventManagementUI.generateEventSummaryReport(completeEventList, eventParticipants, totalVolunteers, totalEvents, averageEventsPerVolunteer);
    }

    public void removeEventFromVolunteer() {
        Volunteer volunteer = eventManagementUI.selectVolunteer(volunteerMap); // Step 1: Select a volunteer  
        if (volunteer != null) {
            // Step 2: Call the boundary method to remove the event  
            eventManagementUI.removeEventFromVolunteer(volunteerEvents, volunteer); // Pass the volunteerEvents map and the selected volunteer  

            // Step 3: Save changes to the DAO (no need to update the map again as it's done already)  
            volunteerEventDAO.save(volunteerEvents); // Pass the volunteerEvents directly to save  
        } else {
            System.out.println("No volunteer selected."); // Output message directly  
        }
    }

    public void listEventsForVolunteer() {
        Volunteer volunteer = eventManagementUI.selectVolunteer(volunteerMap); // Step 1: Select a volunteer  
        if (volunteer != null) {
            // Step 2: Retrieve event IDs for the selected volunteer using the volunteerEvents map  
            HashSet<Event> eventIDs = volunteerEvents.get(volunteer); // Get the events based on volunteer  

            // Step 3: Check if the retrieved events are null or empty  
            if (eventIDs == null || eventIDs.isEmpty()) {
                System.out.println("No events found for Volunteer ID: " + volunteer.getId()); // Output message directly  
            } else {
                // Prepare a HashSet to hold full event details  
                HashSet<Event> completeEventSet = new HashSet<>();

                // Step 4: Populate the complete event details from the local eventSet  
                for (Event event : eventIDs) {
                    // Find the event in the eventSet (assuming eventSet contains all event details)  
                    for (Event fullEvent : eventSet) {
                        if (fullEvent.getId().equals(event.getId())) {
                            completeEventSet.add(fullEvent); // Add the full event details to the HashSet  
                            break; // Stop searching once the event is found  
                        }
                    }
                }

                // Step 5: Display the complete events using the UI  
                eventManagementUI.listEventsForVolunteer(volunteer, completeEventSet); // Pass the selected volunteer and their full event details  
            }
        } else {
            System.out.println("No volunteer selected."); // Output message directly  
        }
    }

    public static void main(String[] args) {
        // Passing file paths for volunteer and event data files:  
        EventManagement eventManagement = new EventManagement("volunteer_event.txt", "event.txt", "volunteer.txt");
        eventManagement.runEventManagement();
    }
}
