package boundary;

import control.VolunteerMaintenance;
import entity.Event;
import entity.Volunteer;
import adt.*;
import utility.MessageUI;
import java.util.Scanner;
import java.util.regex.Pattern;
import dao.*;

public class VolunteerUI {

    private VolunteerMaintenance volunteerMaintenance;
    private Scanner scanner;
    private int volunteerCounter = 1; // To generate new IDs like V001, V002

    public VolunteerUI() {
        volunteerMaintenance = new VolunteerMaintenance();
        scanner = new Scanner(System.in);
    }

    public void start() {
        int choice = -1;
        while (choice != 0) {
            displayMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addVolunteer();
                        break;
                    case 2:
                        removeVolunteer();
                        break;
                    case 3:
                        searchVolunteer();
                        break;
                    case 4:
                        assignEventToVolunteer();
                        break;
                    case 5:
                        searchEventsUnderVolunteer();
                        break;
                    case 6:
                        listAllVolunteers();
                        break;
                    case 7:
                        filterVolunteers();
                        break;
                    case 8:
                        generateSummaryReports();
                        break;
                    case 0:
                        MessageUI.showInfo("Exiting...");
                        break;
                    default:
                        MessageUI.showWarning("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                MessageUI.showWarning("Invalid input. Please enter a number.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nVolunteer Management System");
        System.out.println("1. Add New Volunteer");
        System.out.println("2. Remove Volunteer");
        System.out.println("3. Search Volunteer");
        System.out.println("4. Assign Event to Volunteer");
        System.out.println("5. Search Events Under a Volunteer");
        System.out.println("6. List All Volunteers");
        System.out.println("7. Filter Volunteers");
        System.out.println("8. Generate Summary Reports");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void addVolunteer() {
        boolean continueAdding = true;
        while (continueAdding) {
            String volunteerId = generateVolunteerId();
            String name = "";
            String contactNumber = "";
            String email = "";

            // Name validation
            while (name.isEmpty()) {
                System.out.print("\nEnter Name: ");
                name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    MessageUI.showError("Name cannot be empty. Please enter a valid name.");
                }
            }

            // Contact number validation
            while (contactNumber.isEmpty()) {
                System.out.print("Enter Contact Number: ");
                contactNumber = scanner.nextLine().trim();
                if (contactNumber.isEmpty()) {
                    MessageUI.showError("Contact Number cannot be empty. Please enter a valid contact number.\n");
                }
            }

            // Email validation with basic format check
            while (email.isEmpty() || !isValidEmail(email)) {
                System.out.print("Enter Email: ");
                email = scanner.nextLine().trim();
                if (email.isEmpty()) {
                    MessageUI.showError("Email cannot be empty. Please enter a valid email.\n");
                } else if (!isValidEmail(email)) {
                    MessageUI.showError("Invalid email format. Please enter a valid email.\n");
                }
            }

            Volunteer volunteer = new Volunteer(volunteerId, name, contactNumber, email);
            volunteerMaintenance.addVolunteer(volunteer);

            MessageUI.showSuccess("Volunteer added successfully.");
            displayVolunteerDetails(volunteer);

            continueAdding = promptContinue("\nDo you want to add another volunteer? (yes/no): ");
        }
    }

    private String generateVolunteerId() {
        return String.format("V%03d", volunteerCounter++);
    }

    private void displayVolunteerDetails(Volunteer volunteer) {
        System.out.println("\nVolunteer details:");
        System.out.println("Volunteer ID   : " + volunteer.getVolunteerId());
        System.out.println("Name           : " + volunteer.getName());
        System.out.println("Contact Number : " + volunteer.getContactNumber());
        System.out.println("Email          : " + volunteer.getEmail());
    }

    private void removeVolunteer() {
        // Check if there are any volunteers in the system initially
        if (volunteerMaintenance.listAllVolunteers().isEmpty()) {
            MessageUI.showInfo("\nNo volunteers available to remove. Please add a new volunteer first.");
            if (promptContinue("\nWould you like to add a new volunteer now? (yes/no): ")) {
                addVolunteer();
            }
            MessageUI.showInfo("Returning to the main menu...");
            return; // Exit the method if no volunteers are available
        } else {
        }

        boolean continueRemoving = true;
        while (continueRemoving) {
            // Check if there are volunteers before asking for ID
            if (!volunteerMaintenance.listAllVolunteers().isEmpty()) {
                String id = getInput("\nEnter Volunteer ID: ").trim();

                Volunteer volunteer = volunteerMaintenance.searchVolunteer(id);
                if (volunteer != null) {
                    displayVolunteerDetails(volunteer);

                    if (promptContinue("\nAre you sure you want to remove this volunteer? (yes/no): ")) {
                        boolean success = volunteerMaintenance.removeVolunteer(id);
                        if (success) {
                            MessageUI.showSuccess("Volunteer removed successfully.");
                        } else {
                            MessageUI.showError("Failed to remove volunteer.");
                        }
                    } else {
                        MessageUI.showInfo("Volunteer removal canceled.");
                    }
                } else {
                    MessageUI.showError("Volunteer not found. Please try again.");
                }
            } else {
                // When no more volunteers are available
                MessageUI.showInfo("\nNo more volunteers available to remove.");
                if (promptContinue("\nWould you like to add a new volunteer now? (yes/no): ")) {
                    addVolunteer();
                }
                MessageUI.showInfo("Returning to the main menu...");
                return; // Exit the method after volunteer is added or declined
            }

            continueRemoving = promptContinue("\nDo you want to remove another volunteer? (yes/no): ");
        }
    }

    private void searchVolunteer() {
        // Check if there are any volunteers in the system
        if (volunteerMaintenance.listAllVolunteers().isEmpty()) {
            MessageUI.showInfo("\nNo volunteers available to search. Please add a new volunteer first.");
            if (promptContinue("\nWould you like to add a new volunteer now? (yes/no): ")) {
                addVolunteer();
            }
            MessageUI.showInfo("Returning to the main menu...");
            return;
        }

        boolean continueSearching = true;
        while (continueSearching) {
            String id = getInput("\nEnter Volunteer ID: ").trim();

            Volunteer volunteer = volunteerMaintenance.searchVolunteer(id);
            if (volunteer != null) {
                displayVolunteerDetails(volunteer);
            } else {
                MessageUI.showError("Volunteer not found.");
                if (promptContinue("\nNo volunteer found. Would you like to add a new volunteer? (yes/no): ")) {
                    addVolunteer();
                    return; // After adding a volunteer, return to the main menu
                }
            }

            continueSearching = promptContinue("\nDo you want to search for another volunteer? (yes/no): ");
        }
    }

    /* private void assignEventToVolunteer() {
        if (volunteerMaintenance.listAllVolunteers().isEmpty()) {
            MessageUI.showInfo("\nNo volunteers available. Please add a new volunteer first.");
            if (promptContinue("\nWould you like to add a new volunteer now? (yes/no): ")) {
                addVolunteer();
            }
            MessageUI.showInfo("Returning to the main menu...");
            return;
        }

        boolean continueAssigning = true;
        while (continueAssigning) {
            String volunteerId = "";

            while (volunteerId.isEmpty()) {
                System.out.print("\nEnter Volunteer ID: ");
                volunteerId = scanner.nextLine().trim();
                if (volunteerId.isEmpty()) {
                    MessageUI.showError("Volunteer ID cannot be empty. Please enter a valid Volunteer ID.\n");
                }
            }

            Volunteer volunteer = volunteerMaintenance.searchVolunteer(volunteerId);
            if (volunteer != null) {
                boolean continueAddingEvents = true;
                do {
                    String eventId = "";
                    String eventName = "";
                    String eventDate = "";
                    String eventLocation = "";
                    String eventDescription = "";

                    while (eventId.isEmpty()) {
                        System.out.print("Enter Event ID: ");
                        eventId = scanner.nextLine().trim();
                        if (eventId.isEmpty()) {
                            MessageUI.showError("Event ID cannot be empty. Please enter a valid Event ID.\n");
                        }
                    }

                    while (eventName.isEmpty()) {
                        System.out.print("Enter Event Name: ");
                        eventName = scanner.nextLine().trim();
                        if (eventName.isEmpty()) {
                            MessageUI.showError("Event Name cannot be empty. Please enter a valid Event Name.\n");
                        }
                    }

                    while (eventDate.isEmpty()) {
                        System.out.print("Enter Event Date: ");
                        eventDate = scanner.nextLine().trim();
                        if (eventDate.isEmpty()) {
                            MessageUI.showError("Event Date cannot be empty. Please enter a valid Event Date.\n");
                        }
                    }

                    while (eventLocation.isEmpty()) {
                        System.out.print("Enter Event Location: ");
                        eventLocation = scanner.nextLine().trim();
                        if (eventLocation.isEmpty()) {
                            MessageUI.showError("Event Location cannot be empty. Please enter a valid Event Location.\n");
                        }
                    }

                    while (eventDescription.isEmpty()) {
                        System.out.print("Enter Event Description: ");
                        eventDescription = scanner.nextLine().trim();
                        if (eventDescription.isEmpty()) {
                            MessageUI.showError("Event Description cannot be empty. Please enter a valid Event Description.\n");
                        }
                    }

                    Event event = new Event(eventId, eventName, eventDate, eventLocation, eventDescription);
                    volunteerMaintenance.assignEventToVolunteer(volunteerId, event);
                MessageUI.showSuccess("Event assigned to volunteer successfully.");

                // Prompt to add more events
                continueAddingEvents = promptContinue("\nDo you want to add more events to this volunteer? (yes/no): ");
                 System.out.println();
            } while (continueAddingEvents);
        } else {
            MessageUI.showError("Volunteer ID not found.");
            if (promptContinue("\nNo volunteer found. Would you like to add a new volunteer? (yes/no): ")) {
                addVolunteer();
                return; // After adding a volunteer, return to the main menu
            }
        }

        // Prompt to assign events to another volunteer
        continueAssigning = promptContinue("Do you want to assign events to another volunteer? (yes/no): ");
    }
}*/
    private void assignEventToVolunteer() {

        VolunteerDAO da = new VolunteerDAO();
        ListInterface<Event> events = da.loadEventsFromFile();

        // Ensure there are volunteers before proceeding
        if (volunteerMaintenance.listAllVolunteers().isEmpty()) {
            MessageUI.showInfo("\nNo volunteers available. Please add a new volunteer first.");
            if (promptContinue("\nWould you like to add a new volunteer now? (yes/no): ")) {
                addVolunteer();
            }
            MessageUI.showInfo("Returning to the main menu...");
            return;
        }

        // Load already assigned events
        HashSet<String> assignedEvents = da.loadAssignedEvents();

        boolean continueAssigning = true;
        while (continueAssigning) {
            String volunteerId = "";

            while (volunteerId.isEmpty()) {
                System.out.print("\nEnter Volunteer ID: ");
                volunteerId = scanner.nextLine().trim();
                if (volunteerId.isEmpty()) {
                    MessageUI.showError("Volunteer ID cannot be empty. Please enter a valid Volunteer ID.\n");
                }
            }

            Volunteer volunteer = volunteerMaintenance.searchVolunteer(volunteerId);

            if (volunteer != null) {
                boolean continueAddingEvents = true;
                while (continueAddingEvents) {
                    // Display the list of available events

                    System.out.println("\nAvailable Events:");
                    for (int i = 0; i < events.size(); i++) {
                        Event e = events.getEntry(i);
                        // Only display events that are not already assigned
                        if (!assignedEvents.contains(e.getEventId())) {
                            System.out.println(e.getEventId() + ": " + e.getName() + " on " + e.getDate() + " at " + e.getLocation());
                        }
                    }

                    String eventId = "";
                    while (eventId.isEmpty()) {
                        System.out.print("Enter Event ID from the list above: ");
                        eventId = scanner.nextLine().trim();
                        if (eventId.isEmpty()) {
                            MessageUI.showError("Event ID cannot be empty. Please enter a valid Event ID.\n");
                        }
                    }

                    // Check if the event is already assigned
                    if (assignedEvents.contains(eventId)) {
                        MessageUI.showError("This event has already been assigned. Please choose another event.");
                        continue;
                    }

                    Event event = findEventById(eventId);
                    if (event != null) {
                        // Assign event to volunteer
                        volunteerMaintenance.assignEventToVolunteer(volunteerId, event);
                        MessageUI.showSuccess("Event assigned to volunteer successfully.");

                        // Save the assignment to event_volunteer.txt
                        da.saveEventVolunteerAssignment(volunteerId, eventId);
                        // Add the assigned event to the set to avoid assigning it again
                        assignedEvents.add(eventId);
                    } else {
                        MessageUI.showError("Event ID not found. Please select a valid Event ID.");
                    }

                    // Prompt to add more events
                    continueAddingEvents = promptContinue("\nDo you want to assign more events to this volunteer? (yes/no): ");
                    System.out.println();
                }
            } else {
                MessageUI.showError("Volunteer ID not found.");
                if (promptContinue("\nNo volunteer found. Would you like to add a new volunteer? (yes/no): ")) {
                    addVolunteer();
                    return; // After adding a volunteer, return to the main menu
                }
            }

            // Prompt to assign events to another volunteer
            continueAssigning = promptContinue("Do you want to assign events to another volunteer? (yes/no): ");
        }
    }

// Display the list of available events
    private void displayEventsList() {
        VolunteerDAO da = new VolunteerDAO();
        ListInterface<Event> events = da.loadEventsFromFile(); // Assuming this method exists
        if (events == null || events.isEmpty()) {
            MessageUI.showInfo("No events available. Please add events first.");
            return;
        }

        System.out.println("\nAvailable Events:");
        System.out.println(String.format("%-10s %-25s %-15s %-20s %-30s", "Event ID", "Event Name", "Event Date", "Location", "Description"));
        System.out.println("-----------------------------------------------------------------------------------");

        for (int i = 0; i < events.size(); i++) {
            Event event = events.getEntry(i);
            System.out.println(String.format("%-10s %-25s %-15s %-20s %-30s",
                    event.getEventId(),
                    event.getName(),
                    event.getDate(),
                    event.getLocation(),
                    event.getDescription()));
        }
    }

// Find an event by ID
    private Event findEventById(String eventId) {
        VolunteerDAO da = new VolunteerDAO();
        ListInterface<Event> events = da.loadEventsFromFile();// Assuming this method exists
        for (int i = 0; i < events.size(); i++) {
            Event event = events.getEntry(i);
            if (event.getEventId().equals(eventId)) {
                return event;
            }
        }
        return null;
    }

    private void searchEventsUnderVolunteer() {
        // Check if there are any volunteers in the system
        if (volunteerMaintenance.listAllVolunteers().isEmpty()) {
            MessageUI.showInfo("\nNo volunteers available. Please add a new volunteer first.");
            if (promptContinue("\nWould you like to add a new volunteer now? (yes/no): ")) {
                addVolunteer();
            }
            MessageUI.showInfo("Returning to the main menu...");
            return;
        }

        boolean continueSearching = true;
        while (continueSearching) {
            String volunteerId = getInput("\nEnter Volunteer ID: ");

            ListInterface<Event> events = volunteerMaintenance.searchEventsUnderVolunteer(volunteerId);
            if (events != null && !events.isEmpty()) {
                MessageUI.showInfo("\nEvents under volunteer:");
                for (int i = 0; i < events.size(); i++) {
                    Event event = events.getEntry(i);
                    MessageUI.showInfo("Event ID: " + event.getEventId());
                    MessageUI.showInfo("Event Name: " + event.getName());
                    MessageUI.showInfo("Event Date: " + event.getDate());
                    MessageUI.showInfo("Event Location: " + event.getLocation());
                    MessageUI.showInfo("Event Description: " + event.getDescription());
                    MessageUI.showInfo("---------------------------");
                }
            } else {
                MessageUI.showError("No events found for this volunteer.");
                if (promptContinue("\nNo events found. Would you like to add a new volunteer? (yes/no): ")) {
                    addVolunteer();
                    return; // After adding a volunteer, return to the main menu
                }
            }

            continueSearching = promptContinue("\nDo you want to search for events under another volunteer? (yes/no): ");
        }
    }

    private void listAllVolunteers() {
        ListInterface<Volunteer> volunteers = volunteerMaintenance.listAllVolunteers();
        if (volunteers.isEmpty()) {
            MessageUI.showInfo("\nNo volunteers available. Please add a new volunteer.");
            if (promptContinue("\nWould you like to add a new volunteer now? (yes/no): ")) {
                addVolunteer();
            }
            MessageUI.showInfo("Returning to the main menu...");
        } else {
            // Prepare the columnar design header
            StringBuilder reportBuilder = new StringBuilder();
            reportBuilder.append("\nVolunteer List\n");
            reportBuilder.append("-----------------------------------------------------------------------\n");
            reportBuilder.append(String.format("%-15s %-20s %-15s %-30s\n", "Volunteer ID", "Name", "Contact Number", "Email"));
            reportBuilder.append("-----------------------------------------------------------------------\n");

            // Loop through the volunteers and format the details into columns
            for (int i = 0; i < volunteers.size(); i++) {
                Volunteer volunteer = volunteers.getEntry(i);
                reportBuilder.append(String.format("%-15s %-20s %-15s %-30s\n",
                        volunteer.getVolunteerId(),
                        volunteer.getName(),
                        volunteer.getContactNumber(),
                        volunteer.getEmail()));
            }
            reportBuilder.append("-----------------------------------------------------------------------\n");

            // Display the formatted volunteer list
            MessageUI.showInfo(reportBuilder.toString());
        }
    }

    private void filterVolunteers() {
        ListInterface<Volunteer> volunteers = volunteerMaintenance.listAllVolunteers();
        if (volunteers.isEmpty()) {
            MessageUI.showInfo("\nNo volunteers available. Please add a new volunteer.");
            if (promptContinue("\nWould you like to add a new volunteer now? (yes/no): ")) {
                addVolunteer();
            }
            MessageUI.showInfo("Returning to the main menu...");
            return; // Exit the method
        }

        boolean continueFiltering = true;
        while (continueFiltering) {
            String criteria = getInput("\nEnter criteria (name/email): ");

            ListInterface<Volunteer> filteredVolunteers = volunteerMaintenance.filterVolunteers(criteria);
            if (filteredVolunteers.isEmpty()) {
                MessageUI.showInfo("No volunteers match the criteria.");
            } else {
                MessageUI.showInfo("\nFiltered Volunteers:");
                for (int i = 0; i < filteredVolunteers.size(); i++) {
                    Volunteer volunteer = filteredVolunteers.getEntry(i);
                    displayVolunteerDetails(volunteer);
                    MessageUI.showInfo("---------------------------");
                }
            }

            continueFiltering = promptContinue("\nDo you want to filter volunteers again? (yes/no): ");
        }
    }

    private void generateSummaryReports() {
        ListInterface<Volunteer> volunteers = volunteerMaintenance.listAllVolunteers();
        if (volunteers.isEmpty()) {
            MessageUI.showInfo("\nNo volunteers available. Please add a new volunteer.");
            if (promptContinue("\nWould you like to add a new volunteer now? (yes/no): ")) {
                addVolunteer();
            }
            MessageUI.showInfo("Returning to the main menu...");
        } else {
            volunteerMaintenance.generateDetailedReport();
        }
    }

    private boolean promptContinue(String message) {
        String response;
        do {
            System.out.print(message);
            response = scanner.nextLine().trim().toLowerCase();

            // Handle various input formats
            if (response.equals("yes") || response.equals("y") || response.equals("YES")) {
                return true;
            } else if (response.equals("no") || response.equals("n") || response.equals("NO")) {
                return false;
            } else {
                MessageUI.showWarning("Invalid input. Please enter 'yes', 'no', 'y', or 'n'.");
            }
        } while (true);
    }

    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private boolean isValidEmail(String email) {
        // Basic email validation pattern
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailPattern, email);
    }
}
