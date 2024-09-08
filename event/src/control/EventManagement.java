/*  
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license  
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template  
 */
package control;

/**
 *
 * @author eyong
 */
import adt.*;
import dao.*;
import boundary.EventManagementUI;
import entity.*;


public class EventManagement {

    private SetInterface<Event> eventSet = new HashSet<>(); 
    private MapInterface<String, Event> eventMap = new HashMap<>();
    public MapInterface<String, Volunteer> volunteerMap = new HashMap<>(); 
    private MapInterface<String, VolunteerEvent> volunteerEventMap = new HashMap<>();
    private MapInterface<Volunteer, SetInterface<Event>> volunteerEvents = new HashMap<>();
    private final VolunteerEventDAO volunteerEventDAO;
    private final EventDAO eventDAO;
    private final VolunteerDAO volunteerDAO;
    private final EventManagementUI eventManagementUI;
    private ListInterface<String> availableIDs = new ArrayList<>();

    public EventManagement(String volunteerFilePath, String eventFilePath, String volunteerFile) {
        this.volunteerEventDAO = new VolunteerEventDAO(volunteerFilePath);
        this.eventDAO = new EventDAO(eventFilePath);
        this.volunteerDAO = new VolunteerDAO(volunteerFile);
        this.eventManagementUI = new EventManagementUI();
        loadAllEvents();
        loadVolunteerEvents();
    }

    private void loadAllEvents() {
        eventSet = eventDAO.loadIntoHashSet();
        eventMap = eventDAO.loadIntoHashMap();
        volunteerEventMap = volunteerEventDAO.loadIntoHashMap();
        volunteerMap = volunteerDAO.loadIntoHashMap();
    }

    private void loadVolunteerEvents() {
        for (String volunteerId : volunteerEventMap.keySet()) {
            VolunteerEvent volunteerEvent = volunteerEventMap.get(volunteerId);
            Volunteer volunteer = volunteerMap.get(volunteerId); 
            if (volunteer != null && volunteerEvent != null) {
                SetInterface<Event> events = volunteerEvent.getEvents();
                volunteerEvents.put(volunteer, events);
            }
        }
    }

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
        // Generate event ID   
        String newId = generateEventID();

        Event newEvent = eventManagementUI.addEvent(eventSet, newId);

        if (newEvent != null) {
            eventSet.add(newEvent); // Add to the HashSet  
            eventMap.put(newEvent.getId(), newEvent); // Add to the HashMap
            eventDAO.save((HashSet<Event>) eventSet); // store data
            System.out.println("\nEvent added successfully!");
        }
    }

    public String generateEventID() {
        if (!availableIDs.isEmpty()) {
            return availableIDs.remove(availableIDs.size() - 1); // Use recycled ID  
        }
        int newIdNumber = 1; 
        while (true) {
            String newId = String.format("E%03d", newIdNumber);
            if (!eventMap.containsKey(newId)) { 
                return newId; // Return the new unique ID  
            }
            newIdNumber++; 
        }
    }

    public void removeEvent() {
        String eventId = eventManagementUI.removeEvent(eventMap);
        if (eventId != null) {
            Event eventToRemove = null;
            // Find the event 
            for (Event event : eventSet) {
                if (event.getId().equals(eventId)) {
                    eventToRemove = event; 
                    break; 
                }
            }
            if (eventToRemove != null) {
                boolean removed = eventSet.remove(eventToRemove);
                if (removed) {
                    eventDAO.save((HashSet<Event>) eventSet); 
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
                eventSet.remove(event);
                eventSet.add(event);
                eventDAO.save((HashSet<Event>) eventSet);
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
        ListInterface<Event> eventList = eventDAO.loadIntoArrayList();
        // Sort
        for (int i = 0; i < eventList.size() - 1; i++) {
            for (int j = 0; j < eventList.size() - 1 - i; j++) {
                Event event1 = eventList.getEntry(j);
                Event event2 = eventList.getEntry(j + 1);

                // Compare event IDs
                if (event1.getId().compareTo(event2.getId()) > 0) {
                    eventList.replace(j, event2);
                    eventList.replace(j + 1, event1);
                }
            }
        }
        eventManagementUI.listEvents(eventList);
    }

    public void generateEventSummaryReport() {
        MapInterface<String, Integer> eventParticipants = new HashMap<>(); 

        for (String volunteerId : volunteerEventMap.keySet()) {
            VolunteerEvent volunteerEvent = volunteerEventMap.get(volunteerId);
            for (Event event : volunteerEvent.getEvents()) {
                String eventId = event.getId();
                int currentCount = eventParticipants.getOrDefault(eventId, 0);
                eventParticipants.put(eventId, currentCount + 1); 
            }
        }

        ListInterface<Event> completeEventList = eventDAO.loadIntoArrayList();

        for (int i = 0; i < completeEventList.size() - 1; i++) {
            for (int j = 0; j < completeEventList.size() - 1 - i; j++) {
                Event event1 = completeEventList.getEntry(j);
                Event event2 = completeEventList.getEntry(j + 1);

                if (event1.getId().compareTo(event2.getId()) > 0) {
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
        eventManagementUI.generateEventSummaryReport(completeEventList, eventParticipants, totalVolunteers, totalEvents, averageEventsPerVolunteer);
    }

    public void removeEventFromVolunteer() {
        Volunteer volunteer = eventManagementUI.selectVolunteer(volunteerMap);
        if (volunteer != null) {
            eventManagementUI.removeEventFromVolunteer(volunteerEvents, volunteer); 
            volunteerEventDAO.save(volunteerEvents); 
        } else {
            System.out.println("No volunteer selected."); 
        }
    }

    public void listEventsForVolunteer() {
        Volunteer volunteer = eventManagementUI.selectVolunteer(volunteerMap); 
        if (volunteer != null) {
            SetInterface<Event> eventIDs = volunteerEvents.get(volunteer);  

            if (eventIDs == null || eventIDs.isEmpty()) {
                System.out.println("No events found for Volunteer ID: " + volunteer.getId()); 
            } else {
                SetInterface<Event> completeEventSet = new HashSet<>();

                for (Event event : eventIDs) {
                    for (Event fullEvent : eventSet) {
                        if (fullEvent.getId().equals(event.getId())) {
                            completeEventSet.add(fullEvent);  
                            break; 
                        }
                    }
                }
                eventManagementUI.listEventsForVolunteer(volunteer, completeEventSet);
            }
        } else {
            System.out.println("No volunteer selected."); 
        }
    }

    public static void main(String[] args) {
        // Passing file paths 
        EventManagement eventManagement = new EventManagement("volunteer_event.txt", "event.txt", "volunteer.txt");
        eventManagement.runEventManagement();
    }
}
