package boundary;

import entity.*;
import adt.*;
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
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public Event addEvent(SetInterface<Event> eventSet, String eventId) {
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

    public String removeEvent(MapInterface<String, Event> eventMap) {
        boolean found = false;
        String eventIdToRemove = null; // hold the event ID to remove  
        while (!found) {
            System.out.print("Enter the event ID to remove: ");
            String eventId = scanner.nextLine();

            Event eventToRemove = eventMap.get(eventId);

            if (eventToRemove != null) {
                // Remove from HashMap  
                eventMap.remove(eventId);
                eventIdToRemove = eventId;
                System.out.println("\nEvent " + eventToRemove.getName() + " removed successfully!");
                found = true;
            } else {
                System.out.println("\nEvent not found, please try again.");
            }
        }
        return eventIdToRemove;
    }

    public String updateEvent(MapInterface<String, Event> eventMap) {
        Event event = null;
        String eventId;

        System.out.print("Enter Event ID to update (Enter 1 to exit): ");
        eventId = scanner.nextLine().trim();

        if ("1".equals(eventId)) {
            return null;
        }

        event = eventMap.get(eventId);

        if (event == null) {
            System.out.println("No event found with the given ID. Please try again.\n");
            return null;
        }

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
        return eventId; // Return the ID of updated event  
    }

    public void searchEvent(MapInterface<String, Event> eventMap) {
        System.out.print("Enter Event ID to search: ");
        String eventId = scanner.nextLine();
        Event event = eventMap.get(eventId);

        if (event != null) {
            System.out.println("\n--Event Found--");

            System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.printf("| %-10s | %-35s | %-10s | %-20s | %-75s  |\n", "Event ID", "Name", "Date", "Location", "Description");
            System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.printf("| %-10s | %-35s | %-10s | %-20s | %-75s  |\n",
                    event.getId(),
                    event.getName(),
                    event.getDate(),
                    event.getLocation(),
                    event.getDescription());
            System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        } else {
            System.out.println("No event found with ID: " + eventId);
        }
    }

    public void listEvents(ListInterface<Event> eventList) {
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
                        event.getId(),
                        event.getName(),
                        event.getDate(),
                        event.getLocation(),
                        event.getDescription()
                );
            }

            System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        }
    }

    public void listEventsForVolunteer(Volunteer volunteer, SetInterface<Event> events) {
        System.out.println("Events for Volunteer ID: " + volunteer.getId());

        if (events == null || events.isEmpty()) {
            System.out.println("No events found for Volunteer ID: " + volunteer.getId());
        } else {
            adt.ArrayList<Event> eventList = new adt.ArrayList<>(); // Custom ADT  

            for (Event event : events) {
                eventList.add(event);
            }
            listEvents(eventList);
        }
    }

    public Volunteer selectVolunteer(MapInterface<String, Volunteer> volunteerMap) {
        System.out.print("Enter Volunteer ID to select: ");
        String volunteerId = scanner.nextLine();

        Volunteer volunteer = volunteerMap.get(volunteerId);

        if (volunteer != null) {
            return volunteer;
        } else {
            System.out.println("Volunteer not found.");
            return null;
        }
    }

    public void removeEventFromVolunteer(MapInterface<Volunteer, SetInterface<Event>> volunteerEvents, Volunteer volunteer) {
        if (volunteer != null) {
            System.out.print("Enter Event ID to remove from Volunteer ID " + volunteer.getId() + ": ");
            String eventId = scanner.nextLine();
            SetInterface<Event> events = volunteerEvents.get(volunteer);

            if (events != null) {
                Event eventToRemove = null;
                for (Event event : events) {
                    if (event.getId().equals(eventId)) {
                        eventToRemove = event;
                        break;
                    }
                }

                if (eventToRemove != null) {
                    events.remove(eventToRemove);
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

    public void generateEventSummaryReport(
            ListInterface<Event> eventList,
            MapInterface<String, Integer> eventParticipants,
            int totalVolunteers,
            int totalEvents,
            double averageEventsPerVolunteer
    ) {
        System.out.println("\t\t\t\t\t\t\t\t\t\tEvent Summary Report");
        System.out.println("\n|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|                                                                                 Event List                                                                                                  |");
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("| %-10s | %-35s | %-10s | %-20s | %-75s | %-20s |\n", "Event ID", "Name", "Date", "Location", "Description", "Number Of Participants");
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

        for (int i = 0; i < eventList.size(); i++) {
            Event event = eventList.getEntry(i);  
            String eventId = event.getId();
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

        // Calculate total participants
        int totalParticipants = 0;
        for (int count : eventParticipants.values()) {
            totalParticipants += count; // Sum up the participant counts
        }
        System.out.printf("| Total Volunteers: %-169d |\n", totalVolunteers);
        System.out.printf("| Total Events: %-173d |\n", totalEvents);
        System.out.printf("| Total Participants: %-167d |\n", totalParticipants);
        System.out.printf("| Average Events per Volunteer: %-157.2f |\n", averageEventsPerVolunteer);
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
    }
}
