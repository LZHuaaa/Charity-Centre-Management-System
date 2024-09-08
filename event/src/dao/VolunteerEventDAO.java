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

    private HashSet<VolunteerEvent> volunteerEvents = new HashSet<>(); // HashSet to store volunteer-event relationships  
    private HashMap<String, VolunteerEvent> volunteerEventMap = new HashMap<>(); // Map to find events by volunteer ID  
    private final String filePath; // Path to the volunteer-event file  

    public VolunteerEventDAO() {
        this.filePath = "volunteer_event.txt"; // Default file path  
        loadIntoHashSet(); // Load volunteer-event relationships from the default file  
    }

    // Constructor with filepath  
    public VolunteerEventDAO(String filePath) {
        this.filePath = filePath; // Initialize the filePath with the provided one  
    }

    // Load volunteer-event pairs into a HashSet from the predefined file  
    public HashSet<VolunteerEvent> loadIntoHashSet() {
        volunteerEvents.clear(); // Clear existing relationships  
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(", ");
                if (eventData.length == 2) { // Ensure proper data length  
                    String volunteerId = eventData[0];
                    String eventId = eventData[1];
                    Volunteer volunteer = new Volunteer(volunteerId, "", "", ""); // Create a Volunteer object (name, phone, email can be empty)  
                    Event event = new Event(eventId, "", "", "", ""); // Create an Event object (name, date, location, description can be empty)  
                    VolunteerEvent volunteerEvent = new VolunteerEvent(volunteer);
                    volunteerEvent.addEvent(event); // Associate the event with the volunteer  
                    volunteerEvents.add(volunteerEvent); // Add relationship to HashSet  
                    System.out.println("Loaded Volunteer-Event: " + volunteerId + " - " + eventId);
                } else {
                    System.out.println("Invalid data length for line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions  
        }
        System.out.println("Total volunteer-events loaded: " + volunteerEvents.size()); // Print total loaded Volunteer-Events  
        return volunteerEvents; // Return loaded Volunteer-Events  
    }

    // Load volunteer-event pairs into a HashMap for quick access  
    public HashMap<String, VolunteerEvent> loadIntoHashMap() {
        volunteerEventMap.clear(); // Clear existing relationships in the map  
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(", ");
                if (eventData.length == 2) { // Ensure proper data length  
                    String volunteerId = eventData[0];
                    String eventId = eventData[1];

                    Volunteer volunteer = new Volunteer(volunteerId, "", "", ""); // Create a Volunteer object  
                    Event event = new Event(eventId, "", "", "", ""); // Create an Event object  
                    VolunteerEvent volunteerEvent = new VolunteerEvent(volunteer);
                    volunteerEvent.addEvent(event); // Associate the event with the volunteer  

                    // Check if the volunteer already exists in the map  
                    if (volunteerEventMap.containsKey(volunteerId)) {
                        volunteerEventMap.get(volunteerId).addEvent(event); // Add event to existing VolunteerEvent  
                    } else {
                        volunteerEventMap.put(volunteerId, volunteerEvent); // Map volunteer ID to VolunteerEvent  
                    }
                } else {
                    System.out.println("Invalid data length for line: " + line); // Debug print for invalid lines  
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions  
        }
        return volunteerEventMap; // Return the populated map  
    }

    // Save all volunteer-event pairs from HashSet to the predefined file  
    public void save(HashMap<Volunteer, HashSet<Event>> volunteerEventsToSave) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Get the set of keys (volunteers)  
            Set<Volunteer> volunteers = volunteerEventsToSave.keySet();

            for (Volunteer volunteer : volunteers) {
                HashSet<Event> events = volunteerEventsToSave.get(volunteer); // Retrieve the events for the volunteer  
                if (events != null) {
                    for (Event event : events) { // Iterate through events associated with the volunteer  
                        String line = String.join(", ", volunteer.getId(), event.getId());
                        bw.write(line); // Write the volunteer-event line to the file  
                        bw.newLine(); // Add a new line  
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions  
        }
    }

    // Retrieve volunteer-event pairs into an ArrayList  
    public ArrayList<VolunteerEvent> loadIntoArrayList() {
        ArrayList<VolunteerEvent> volunteerEventList = new ArrayList<>(); // Create an instance of ArrayList  
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(", ");
                if (eventData.length == 2) { // Ensure proper data length  
                    String volunteerId = eventData[0];
                    String eventId = eventData[1];
                    Volunteer volunteer = new Volunteer(volunteerId, "", "", ""); // Create a Volunteer object  
                    Event event = new Event(eventId, "", "", "", ""); // Create an Event object  
                    VolunteerEvent volunteerEvent = new VolunteerEvent(volunteer);
                    volunteerEvent.addEvent(event); // Associate the event with the volunteer  
                    volunteerEventList.add(volunteerEvent); // Add relationship to the ArrayList  
                    System.out.println("Loaded Volunteer-Event into ArrayList: " + volunteerId + " - " + eventId);
                } else {
                    System.out.println("Invalid data length for line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions  
        }
        System.out.println("Total volunteer-events loaded into ArrayList: " + volunteerEventList.size()); // Print total loaded volunteer-events  
        return volunteerEventList; // Return the populated ArrayList  
    }
}
