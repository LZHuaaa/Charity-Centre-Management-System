/**
 *
 * @author Chia Yuxuan
 */
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
                        System.out.println();
                        MessageUI.displayExitMessage();
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
        System.out.print("Select an option: ");
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

            //MessageUI.showSuccess("Volunteer added successfully.");
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
        if (volunteerMaintenance.listAllVolunteers().isEmpty()) {
            MessageUI.showInfo("\nNo volunteers available to remove. Please add a new volunteer first.");
            if (promptContinue("\nWould you like to add a new volunteer now? (yes/no): ")) {
                addVolunteer();
            }
            MessageUI.showInfo("Returning to the main menu...");
            return; 
        } else {
    }

        boolean continueRemoving = true;
        while (continueRemoving) {
            if (!volunteerMaintenance.listAllVolunteers().isEmpty()) {
                String id = getInput("\nEnter Volunteer ID: ").trim();

                Volunteer volunteer = volunteerMaintenance.searchVolunteer(id);
                if (volunteer != null) {
                    displayVolunteerDetails(volunteer);

                    if (promptContinue("\nAre you sure you want to remove this volunteer? (yes/no): ")) {
                        boolean success = volunteerMaintenance.removeVolunteer(id);
                        if (success) {
                            //MessageUI.showSuccess("Volunteer removed successfully.");
                        } else {
                            MessageUI.showError("Failed to remove volunteer.");
                        }
                    } else {
                        MessageUI.showInfo("Volunteer removal canceled.");
                    }
                } 
            } else {
                MessageUI.showInfo("\nNo more volunteers available to remove.");
                if (promptContinue("\nWould you like to add a new volunteer now? (yes/no): ")) {
                    addVolunteer();
                }
                MessageUI.showInfo("Returning to the main menu...");
                return; 
            }

            continueRemoving = promptContinue("\nDo you want to remove another volunteer? (yes/no): ");
        }
    }

    private void searchVolunteer() {
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
            }

            continueSearching = promptContinue("Do you want to search for another volunteer? (yes/no): ");
        }
    }

    private void assignEventToVolunteer() {
        VolunteerDAO da = new VolunteerDAO();
        ListInterface<Event> events = da.loadEventsFromFile(); // Load all events from the file

        // Ensure there are volunteers before proceeding
        if (volunteerMaintenance.listAllVolunteers().isEmpty()) {
            MessageUI.showInfo("\nNo volunteers available. Please add a new volunteer first.");
            if (promptContinue("\nWould you like to add a new volunteer now? (yes/no): ")) {
                addVolunteer();
            }
            MessageUI.showInfo("Returning to the main menu...");
            return;
        }

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
                // Load the events already assigned to this specific volunteer
                ListInterface<Event> assignedEventsForVolunteer = da.loadEventsForVolunteer(volunteerId);

                boolean continueAddingEvents = true;
                while (continueAddingEvents) {
                    // Display the list of available events in columnar format
                    System.out.println("\nAvailable Events:");
                    System.out.printf("%-15s %-30s %-12s %-20s %-50s%n", "Event ID", "Event Name", "Event Date", "Event Location", "Event Description");
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");

                    for (int i = 0; i < events.size(); i++) {
                        Event e = events.getEntry(i);
                        // Only display events not yet assigned to this specific volunteer
                        if (!isEventAssignedToVolunteer(e.getEventId(), assignedEventsForVolunteer)) {
                            System.out.printf("%-15s %-30s %-12s %-20s %-50s%n",
                                    e.getEventId(),
                                    e.getName(),
                                    e.getDate(),
                                    e.getLocation(),
                                    e.getDescription());
                        }
                    }

                    String eventId = "";
                    while (eventId.isEmpty()) {
                        System.out.print("\nEnter Event ID from the list above: ");
                        eventId = scanner.nextLine().trim();
                        if (eventId.isEmpty()) {
                            MessageUI.showError("Event ID cannot be empty. Please enter a valid Event ID.\n");
                        }
                    }

                    Event event = da.findEventById(eventId);

                    if (volunteer.getEvents() != null && volunteer.getEvents().contains(event)) {
                        MessageUI.showError("This event has already been assigned to this volunteer. Please choose another event.");
                        continue;
                    }

                    if (event != null) {
                        if (isEventAssignedToVolunteer(eventId, assignedEventsForVolunteer)) {
                            MessageUI.showError("This event is already assigned to this volunteer. Please choose another event.");
                        } else {
                            volunteerMaintenance.assignEventToVolunteer(volunteerId, event);

                            da.saveEventVolunteerAssignment(volunteerId, eventId);

                            assignedEventsForVolunteer.add(event);

                            System.out.println();
                            MessageUI.showSuccess("Event assigned to volunteer successfully.");
                        }
                    } else {
                        MessageUI.showError("Event ID not found. Please select a valid Event ID.");
                    }

                    continueAddingEvents = promptContinue("\nDo you want to assign more events to this volunteer? (yes/no): ");
                    System.out.println();
                }
            } else {
                MessageUI.showError("Volunteer ID not found.");
                if (promptContinue("\nNo volunteer found. Would you like to add a new volunteer? (yes/no): ")) {
                    addVolunteer();
                    return; 
                }
            }

            continueAssigning = promptContinue("Do you want to assign events to another volunteer? (yes/no): ");
        }
    }

    // Utility method to check if an event is already assigned to a volunteer
    private boolean isEventAssignedToVolunteer(String eventId, ListInterface<Event> assignedEvents) {
        if (assignedEvents == null) return false;
        for (int i = 0; i < assignedEvents.size(); i++) {
            if (assignedEvents.getEntry(i).getEventId().equals(eventId)) {
                return true;
            }
        }
        return false;
    }

    private void searchEventsUnderVolunteer() {
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
                System.out.printf("%-15s %-30s %-12s %-20s %-50s%n", "Event ID", "Event Name", "Event Date", "Event Location", "Event Description");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");

                for (int i = 0; i < events.size(); i++) {
                    Event event = events.getEntry(i);
                    System.out.printf("%-15s %-30s %-12s %-20s %-50s%n",
                            event.getEventId(),
                            event.getName(),
                            event.getDate(),
                            event.getLocation(),
                            event.getDescription());
                }
            }

            continueSearching = promptContinue("\nError: Volunteer ID is not found. Do you want to search for events under another volunteer? (yes/no): ");
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
            StringBuilder reportBuilder = new StringBuilder();
            reportBuilder.append("\nVolunteer List\n");
            reportBuilder.append("-----------------------------------------------------------------------\n");
            reportBuilder.append(String.format("%-15s %-20s %-15s %-30s\n", "Volunteer ID", "Name", "Contact Number", "Email"));
            reportBuilder.append("-----------------------------------------------------------------------\n");

            for (int i = 0; i < volunteers.size(); i++) {
                Volunteer volunteer = volunteers.getEntry(i);
                reportBuilder.append(String.format("%-15s %-20s %-15s %-30s\n",
                        volunteer.getVolunteerId(),
                        volunteer.getName(),
                        volunteer.getContactNumber(),
                        volunteer.getEmail()));
            }
            reportBuilder.append("-----------------------------------------------------------------------\n");

            MessageUI.showInfo(reportBuilder.toString()); // Display the formatted volunteer list
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
            return; 
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
                    MessageUI.showInfo("-------------------------------------");
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
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailPattern, email);
    }
}