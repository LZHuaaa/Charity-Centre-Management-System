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
import adt.TreeMap;  
import dao.EventDAO;  
import boundary.EventManagementUI;  
import entity.Event;  
import entity.Volunteer;  

public class EventManagement {  

    private HashSet<Event> eventSet = new HashSet<>(); // For adding and removing events  
    private TreeMap<String, Event> eventMap = new TreeMap<>(); // For searching and amending events  
    private HashMap<Volunteer, HashSet<Event>> volunteerEventMap = new HashMap<>(); // For managing events per volunteer  
    private EventDAO eventDAO = new EventDAO();  
    private EventManagementUI eventManagementUI = new EventManagementUI();  

    public EventManagement() {  
        loadAllEvents();  
    }  

    private void loadAllEvents() {  
        ArrayList<Event> events = eventDAO.retrieveEvents();  
        for (int i = 0; i < events.size(); i++) {  
            Event event = events.getEntry(i);  
            eventSet.add(event);  
            eventMap.put(event.getId(), event);  
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
                    addEvent(); // ADT: HashSet<Event>  
                    break;  
                case 2:  
                    removeEvent(); // ADT: HashSet<Event>  
                    break;  
                case 3:  
                    updateEventDetails(); // ADT: TreeMap  
                    break;  
                case 4:  
                    searchEvent(); // ADT: TreeMap  
                    break;  
                case 5:  
                    listAllEvents(); // ADT: ArrayList<Event>  
                    break;  
                case 6:  
                    generateEventSummaryReport(); // ADT: ArrayList<Event>  
                    break;  
                case 7:  
                    removeEventFromVolunteer(); // ADT: HashMap<Volunteer, HashSet<Event>>  
                    break;  
                case 8:  
                    listEventsForVolunteer(); // ADT: HashMap<Volunteer, HashSet<Event>>  
                    break;  
                default:  
                    System.out.println("Invalid choice! Please try again.");  
            }  
        } while (choice != 0);  
    }  

    public void addEvent() {  
    Event newEvent = eventManagementUI.addEvent(eventSet);  
    if (newEvent != null) {  
        eventDAO.saveEvents(eventSet); // Save to the data store if needed  
        System.out.println("Event added successfully!");  
    }  
}  

    public void removeEvent() {  
        String eventId = eventManagementUI.removeEvent();  
        Event removedEvent = eventMap.remove(eventId);  
        if (removedEvent != null) {  
            eventSet.remove(removedEvent);  
            eventDAO.saveEvents(eventSet);  
            System.out.println("Event removed successfully!");  
        } else {  
            System.out.println("Event not found.");  
        }  
    }  

    public void updateEventDetails() {  
        String eventId = eventManagementUI.updateEvent();  
        Event event = eventMap.get(eventId);  
        if (event != null) {  
            eventManagementUI.updateEventDetails(event);  
            eventDAO.saveEvents(eventSet);  
            System.out.println("Event updated successfully!");  
        } else {  
            System.out.println("Event not found.");  
        }  
    }  

    public void searchEvent() {  
        String eventId = eventManagementUI.searchEvent();  
        Event event = eventMap.get(eventId);  
        if (event != null) {  
            eventManagementUI.displayEventDetails(event);  
        } else {  
            System.out.println("Event not found.");  
        }  
    }  

    public void listAllEvents() {  
        ArrayList<Event> eventsList = new ArrayList<>();  
        for (Event event : eventSet) {  
            eventsList.add(event);  
        }  
        eventManagementUI.listEvents(eventsList); // ADT: ArrayList<Event>  
    }  

    public void generateEventSummaryReport() {  
        ArrayList<Event> eventsList = new ArrayList<>(eventSet); // ADT: ArrayList<Event>  
        eventManagementUI.generateEventSummaryReport(eventsList);  
    }  

    public void removeEventFromVolunteer() {  
        Volunteer volunteer = eventManagementUI.selectVolunteer();  
        String eventId = eventManagementUI.removeEventFromVolunteer();  
        HashSet<Event> volunteerEvents = volunteerEventMap.get(volunteer);  
        if (volunteerEvents != null) {  
            Event eventToRemove = eventMap.get(eventId);  
            if (eventToRemove != null && volunteerEvents.remove(eventToRemove)) {  
                System.out.println("Event removed from volunteer successfully!");  
            } else {  
                System.out.println("Event not found for this volunteer.");  
            }  
        } else {  
            System.out.println("No events found for this volunteer.");  
        }  
    }  

    public void listEventsForVolunteer() {  
        Volunteer volunteer = eventManagementUI.selectVolunteer();  
        HashSet<Event> volunteerEvents = volunteerEventMap.get(volunteer);  
        if (volunteerEvents != null) {  
            eventManagementUI.listEvents(volunteerEvents); // ADT: HashSet<Event>  
        } else {  
            System.out.println("No events found for this volunteer.");  
        }  
    }  

    public static void main(String[] args) {  
        EventManagement eventManagement = new EventManagement();  
        eventManagement.runEventManagement();  
    }  
}  