package boundary;

import entity.*;
import adt.*;
import dao.*;
import java.util.Scanner;

public class EventManagementUI {

    private EventDAO eventDAO = new EventDAO();
    private Scanner scanner = new Scanner(System.in);
//    private final HashMap<String, Volunteer> volunteerMap = new HashMap<>();

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
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline  
        System.out.println();
        return choice;
    }

    public Event addEvent(HashSet<Event> eventSet, String eventId) {
        System.out.println("--- Please enter the event details ---");

        System.out.println("Event ID: " + eventId);

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

        // Create a new Event and return it  
        Event newEvent = new Event(eventId, name, date, location, description);
        return newEvent; // Return the new event  
    }

    public String removeEvent(HashMap<String, Event> eventMap) {
        boolean found = false;
        String eventIdToRemove = null; // Variable to hold the event ID to remove  
        while (!found) {
            System.out.print("Enter the event ID to remove: ");
            String eventId = scanner.nextLine();

            // Find the event in the HashMap  
            Event eventToRemove = eventMap.get(eventId);

            if (eventToRemove != null) {
                // Remove from HashMap  
                eventMap.remove(eventId);
                eventIdToRemove = eventId; // Store the event ID to return later  
                System.out.println("\nEvent " + eventToRemove.getName() + " removed successfully!");
                found = true;
            } else {
                System.out.println("\nEvent not found, please try again.");
            }
        }
        return eventIdToRemove; // Return the event ID that was removed  
    }

    public String updateEvent(HashMap<String, Event> eventMap) {
        Event event = null;
        String eventId;

        System.out.print("Enter Event ID to update (Enter 1 to exit): ");
        eventId = scanner.nextLine().trim();

        if ("1".equals(eventId)) {
            return null; // Return null if the user chooses to exit  
        }

        event = eventMap.get(eventId);

        if (event == null) {
            System.out.println("No event found with the given ID. Please try again.\n");
            return null;
        }

        System.out.println("\n--Event Found--");
        System.out.println("ID: " + event.getEventId());
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
        return eventId; // Return the ID of the updated event  
    }

    public void searchEvent(HashMap<String, Event> eventMap) {
        System.out.print("Enter Event ID to search: ");
        String eventId = scanner.nextLine();
        Event event = eventMap.get(eventId);

        if (event != null) {
            System.out.println("\n--Event Found--");
            System.out.println("ID: " + event.getEventId());
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
            System.out.println("\n|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.println("|                                                                       Event List                                                                                    |");
            System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.printf("| %-10s | %-35s | %-10s | %-20s | %-75s  |\n", "Event ID", "Name", "Date", "Location", "Description");
            System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

            for (int i = 0; i < eventList.size(); i++) {
                Event event = eventList.getEntry(i);
                System.out.printf("| %-10s | %-35s | %-10s | %-20s | %-75s  |\n",
                        event.getEventId(),
                        event.getName(),
                        event.getDate(),
                        event.getLocation(),
                        event.getDescription()
                );
            }

            System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        }
    }

    public void listEventsForVolunteer(Volunteer volunteer, HashSet<Event> events) {
        // Step 1: Display events for the selected volunteer  
        System.out.println("Events for Volunteer ID: " + volunteer.getVolunteerId());

        // Check if events is null or empty  
        if (events == null || events.isEmpty()) {
            System.out.println("No events found for Volunteer ID: " + volunteer.getVolunteerId());
        } else {
            // Use your custom `adt.ArrayList` for displaying  
            adt.ArrayList<Event> eventList = new adt.ArrayList<>(); // Custom ADT  

            // Add events to the custom ArrayList  
            for (Event event : events) {
                eventList.add(event);
            }

            // List events using ArrayList  
            listEvents(eventList); // Pass the custom ADT `eventList`  
        }
    }

    public Volunteer selectVolunteer(HashMap<String, Volunteer> volunteerMap) {
        System.out.print("Enter Volunteer ID to select: ");
        String volunteerId = scanner.nextLine();

        Volunteer volunteer = volunteerMap.get(volunteerId); // Ensure the correct map type  

        if (volunteer != null) {
            return volunteer;
        } else {
            System.out.println("Volunteer not found.");
            return null;
        }
    }

    public void removeEventFromVolunteer(HashMap<Volunteer, HashSet<Event>> volunteerEvents, Volunteer volunteer) {
        if (volunteer != null) {
            System.out.print("Enter Event ID to remove from Volunteer ID " + volunteer.getVolunteerId() + ": ");
            String eventId = scanner.nextLine();
            HashSet<Event> events = volunteerEvents.get(volunteer);

            if (events != null) { // Check if the volunteer has any events  
                Event eventToRemove = null;
                for (Event event : events) {
                    if (event.getEventId().equals(eventId)) {
                        eventToRemove = event;
                        break;
                    }
                }

                if (eventToRemove != null) {
                    events.remove(eventToRemove);
                    // System.out.println("Event removed from volunteer successfully.");
                } else {
                    System.out.println("Event not found for this volunteer.");
                }
            } else {
                System.out.println("This volunteer has no associated events.");
            }
        } else {
            System.out.println("Volunteer not found.");
        }
    }

    public void generateEventSummaryReport(ArrayList<Event> eventList, HashMap<String, Integer> eventParticipants, int totalVolunteers, int totalEvents, double averageEventsPerVolunteer) {
        // Header for the Event Summary Report  
        System.out.println("\t\t\t\t\t\t\t\t\t\tEvent Summary Report");
        System.out.println("\n|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|                                                                                 Event List                                                                                                  |");
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("| %-10s | %-35s | %-10s | %-20s | %-75s | %-20s |\n", "Event ID", "Name", "Date", "Location", "Description", "Number Of Participants");
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        // Iterate through the events and print their details  
        for (Event event : eventList) {
            String eventId = event.getEventId();
            int participantCount = eventParticipants.getOrDefault(eventId, 0); // Get number of participants  
            System.out.printf("| %-10s | %-35s | %-10s | %-20s | %-75s | %-22d |\n",
                    eventId,
                    event.getName(),
                    event.getDate(),
                    event.getLocation(),
                    event.getDescription(),
                    participantCount);
        }

        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

        int totalParticipants = 0;
        for (int count : eventParticipants.values()) {
            totalParticipants += count; // Sum up the participant counts
        }

        // Print total volunteers, total events, and average events per volunteer  
        System.out.printf("| Total Volunteers: %-169d |\n", totalVolunteers);
        System.out.printf("| Total Events: %-173d |\n", totalEvents);
        System.out.printf("| Total Participants: %-167d |\n", totalParticipants);
        System.out.printf("| Average Events per Volunteer: %-157.2f |\n", averageEventsPerVolunteer);
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
    }
}
