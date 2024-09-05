package boundary;  

import entity.*;  
import adt.*;
import control.EventManagement; 
import dao.*; 
import java.util.Scanner;

public class EventManagementUI {  

    private EventDAO eventDAO = new EventDAO();  
    private Scanner scanner = new Scanner(System.in);  

    public int menu() {  
        System.out.println("\nEvent Management System");  
        System.out.println("1. Add new event");  
        System.out.println("2. Remove event");  
        System.out.println("3. Update event details");  
        System.out.println("4. Search event details");  
        System.out.println("5. List all events");  
        System.out.println("6. Generate summary report");  
        System.out.println("7. Remove event from Volunteer");  
        System.out.println("8. List events for Volunteer");  
        System.out.print("Enter choice (0 = Exit): ");  
        int choice = scanner.nextInt();  
        scanner.nextLine(); // Consume newline  
        System.out.println();  
        return choice;  
    }  

    public Event addEvent(HashSet<Event> eventSet) {  
    System.out.println("--- Please enter the event details ---");  
    
    String newId = eventDAO.generateNewId(eventSet); // Generate a new ID  
    System.out.println("Event ID: " + newId);  
    
    System.out.print("Enter event name: ");  
    String name = scanner.nextLine().trim();  
    while (name.isEmpty()) {  
        System.out.print("\nName cannot be empty. Please enter the event name: ");  
        name = scanner.nextLine().trim();  
    }  

    System.out.print("Enter event date (YYYY-MM-DD): ");  
    String date = scanner.nextLine().trim();  
    while (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {  
        System.out.print("\nInvalid format. Please enter the event date (YYYY-MM-DD): ");  
        date = scanner.nextLine().trim();  
    }  

    System.out.print("Enter event location: ");  
    String location = scanner.nextLine().trim();  

    System.out.print("Enter event description: ");  
    String description = scanner.nextLine().trim();  

    // Create a new Event and add it to the set  
    Event newEvent = new Event(newId, name, date, location, description);  
    eventSet.add(newEvent);  
    System.out.println("\nEvent added successfully!");  
    
    return newEvent; // Return the new event  
}   

    public void removeEvent(HashSet<Event> eventSet) {  
        boolean exist = false;  

        while (!exist) {  
            System.out.print("Enter the event ID to remove: ");  
            String eventId = scanner.nextLine();  

            for (Event event : eventSet) {  
                if (event.getId().equals(eventId)) {  
                    eventSet.remove(event);  
                    System.out.println("\nEvent " + event.getName() + " removed successfully!");  
                    exist = true;  
                    break;  
                }  
            }  

            if (!exist) {  
                System.out.println("\nEvent not found, please try again.");  
            }  
        }  
    }  

    public void updateEventDetails(TreeMap<String, Event> eventMap) {  
        Event event = null;  
        String eventId;  

        do {  
            System.out.print("Enter Event ID to update (Enter 1 to exit): ");  
            eventId = scanner.nextLine().trim();  

            if ("1".equals(eventId)) {  
                return;  
            }  

            event = eventMap.get(eventId);  

            if (event == null) {  
                System.out.println("No event found with the given ID. Please try again.\n");  
            }  
        } while (event == null);  

        System.out.println("\n--Event Found--");  
        System.out.println("ID: " + event.getId());  
        System.out.println("Name: " + event.getName());  
        System.out.println("Date: " + event.getDate());  
        System.out.println("Location: " + event.getLocation());  
        System.out.println("Description: " + event.getDescription());  

        System.out.print("\nEnter new name: ");  
        String newName = scanner.nextLine().trim();  

        System.out.print("Enter new date (YYYY-MM-DD): ");  
        String newDate = scanner.nextLine().trim();  
        while (!newDate.matches("\\d{4}-\\d{2}-\\d{2}")) {  
            System.out.print("\nInvalid format. Please enter the event date (YYYY-MM-DD): ");  
            newDate = scanner.nextLine().trim();  
        }  

        System.out.print("Enter new location: ");  
        String newLocation = scanner.nextLine().trim();  

        System.out.print("Enter new description: ");  
        String newDescription = scanner.nextLine().trim();  

        event.setName(newName);  
        event.setDate(newDate);  
        event.setLocation(newLocation);  
        event.setDescription(newDescription);  

        System.out.println("Event details updated successfully.");  
    }  

    public void searchEvent(TreeMap<String, Event> eventMap) {  
        System.out.print("Enter Event ID to search: ");  
        String eventId = scanner.nextLine();  
        Event event = eventMap.get(eventId);  

        if (event != null) {  
            System.out.println("\n--Event Found--");  
            System.out.println("ID: " + event.getId());  
            System.out.println("Name: " + event.getName());  
            System.out.println("Date: " + event.getDate());  
            System.out.println("Location: " + event.getLocation());  
            System.out.println("Description: " + event.getDescription());  
        } else {  
            System.out.println("No event found with ID: " + eventId);  
        }  
    }  

    public void listEvents(ArrayList<Event> eventList) {  
        if (eventList.isEmpty()) {  
            System.out.println("No events available.");  
        } else {  
            for (Event event : eventList) {  
                System.out.println("Event ID: " + event.getId());  
                System.out.println("Name: " + event.getName());  
                System.out.println("Date: " + event.getDate());  
                System.out.println("Location: " + event.getLocation());  
                System.out.println("Description: " + event.getDescription());  
                System.out.println();  
            }  
        }  
    }  

    public void removeEventFromVolunteer(HashMap<Volunteer, HashSet<Event>> volunteerEvents) {  
        System.out.print("Enter Volunteer ID to remove an event from: ");  
        String volunteerId = scanner.nextLine();  

        Volunteer volunteer = null;  
        for (Volunteer v : volunteerEvents.keySet()) {  
            if (v.getId().equals(volunteerId)) {  
                volunteer = v;  
                break;  
            }  
        }  

        if (volunteer != null) {  
            System.out.print("Enter Event ID to remove: ");  
            String eventId = scanner.nextLine();  
            HashSet<Event> events = volunteerEvents.get(volunteer);  

            Event eventToRemove = null;  
            for (Event event : events) {  
                if (event.getId().equals(eventId)) {  
                    eventToRemove = event;  
                    break;  
                }  
            }  

            if (eventToRemove != null) {  
                events.remove(eventToRemove);  
                System.out.println("Event removed from volunteer successfully.");  
            } else {  
                System.out.println("Event not found for this volunteer.");  
            }  
        } else {  
            System.out.println("Volunteer not found.");  
        }  
    }  

    public void listEventsForVolunteer(HashMap<Volunteer, HashSet<Event>> volunteerEvents) {  
        System.out.print("Enter Volunteer ID to list events: ");  
        String volunteerId = scanner.nextLine();  

        Volunteer volunteer = null;  
        for (Volunteer v : volunteerEvents.keySet()) {  
            if (v.getId().equals(volunteerId)) {  
                volunteer = v;  
                break;  
            }  
        }  

        if (volunteer != null) {  
            HashSet<Event> events = volunteerEvents.get(volunteer);  
            if (events.isEmpty()) {  
                System.out.println("No events found for this volunteer.");  
            } else {  
                System.out.println("Events for Volunteer ID: " + volunteerId);  
                for (Event event : events) {  
                    System.out.println("Event ID: " + event.getId());  
                    System.out.println("Name: " + event.getName());  
                    System.out.println("Date: " + event.getDate());  
                    System.out.println("Location: " + event.getLocation());  
                    System.out.println("Description: " + event.getDescription());  
                    System.out.println();  
                }  
            }  
        } else {  
            System.out.println("Volunteer not found.");  
        }  
    }  

    public void generateEventSummaryReport(ArrayList<Event> eventList) {  
        System.out.println("Event Summary Report");  
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");  
        System.out.printf("| %-10s | %-20s | %-15s | %-15s | %-20s |\n", "ID", "Name", "Date", "Location", "Description");  
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");  

        for (Event event : eventList) {  
            System.out.printf("| %-10s | %-20s | %-15s | %-15s | %-20s |\n",  
                    event.getId(),  
                    event.getName(),  
                    event.getDate(),  
                    event.getLocation(),  
                    event.getDescription()  
            );  
        }  

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");  
    }  
}  