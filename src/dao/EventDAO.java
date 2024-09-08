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

public class EventDAO {

    private ArrayList<Event> eventList = new ArrayList<>();
    private HashSet<Event> events = new HashSet<>(); // Ensure this is declared as a class field  
    private HashMap<String, Event> eventMap = new HashMap<>(); // HashMap to map event IDs to events  
    private final String filePath; // Path to the events file  

    public EventDAO() {
        this.filePath = "events.txt"; // Default file path  
        this.events = new HashSet<>(); // Initialize the HashSet  
        this.eventMap = new HashMap<>(); // Initialize the HashMap  
        loadIntoHashSet(); // Load events from the default file  
    }

    // Constructor  
    public EventDAO(String filePath) {
        this.filePath = filePath; // Initialize the filePath with the provided one  
        this.events = new HashSet<>(); // Initialize the HashSet  
        this.eventMap = new HashMap<>(); // Initialize the HashMap  
    }

    // Load events into a HashSet from the predefined file  
    public HashSet<Event> loadIntoHashSet() {
        events.clear(); // Clear existing events  
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(", ");
                if (eventData.length == 5) { // Ensure proper data length  
                    Event event = new Event(eventData[0], eventData[1], eventData[2], eventData[3], eventData[4]);
                    events.add(event); // Add event to HashSet  
                } else {
                    System.out.println("Invalid data length for line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions  
        }
        return events; // Return loaded events  
    }

    // Load events into a HashMap from the predefined file  
    public HashMap<String, Event> loadIntoHashMap() {
        eventMap.clear(); // Clear existing events in the map  
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(", ");
                if (eventData.length == 5) { // Ensure proper data length  
                    Event event = new Event(eventData[0], eventData[1], eventData[2], eventData[3], eventData[4]);
                    eventMap.put(event.getEventId(), event); // Map event ID to the event  
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions  
        }
        return eventMap; // Return the populated map  
    }

    // Save events from HashSet to the predefined file  
    public void save(HashSet<Event> eventsToSave) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Event event : eventsToSave) { // Iterate through each event in the provided HashSet  
                String line = String.join(", ", event.getEventId(), event.getName(), event.getDate(), event.getLocation(), event.getDescription());
                bw.write(line); // Write the event line to the file  
                bw.newLine(); // Add a new line  
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions  
        }
    }

    // Retrieve events into an ArrayList  
    public ArrayList<Event> loadIntoArrayList() {
        ArrayList<Event> eventList = new ArrayList<>(); // Create an instance of ArrayList  
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(", ");
                if (eventData.length == 5) { // Ensure proper data length  
                    Event event = new Event(eventData[0], eventData[1], eventData[2], eventData[3], eventData[4]);
                    eventList.add(event); // Add event to the ArrayList  
                } else {
                    System.out.println("Invalid data length for line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions  
        }
        return eventList; // Return the populated ArrayList  
    }

}
