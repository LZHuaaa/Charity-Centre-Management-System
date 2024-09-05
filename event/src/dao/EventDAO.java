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

    private String fileName = "event.txt"; // File to store events  

    public EventDAO() {  
    }  

    public EventDAO(String fileName) {  
        this.fileName = fileName;  
    }  

    // Save events to a file  
    public boolean saveEvents(HashSet<Event> eventSet) {  
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {  
            for (Event event : eventSet) {  
                writer.write(event.getId() + "," + event.getName() + "," + event.getDate() + "," + event.getLocation() + "," + event.getDescription());  
                writer.newLine();  
            }  
        } catch (IOException e) {  
            System.out.println("Error writing events to file: " + e.getMessage());  
            return false;  
        }  
        return true;  
    }  

    // Retrieve events from a file (returns a ListInterface of events)  
    public ArrayList<Event> retrieveEvents() {  
        ArrayList<Event> eventList = new ArrayList<>();  
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {  
            String line;  
            while ((line = reader.readLine()) != null) {  
                String[] eventData = line.split(",");  
                if (eventData.length >= 5) {  
                    Event event = new Event(eventData[0], eventData[1], eventData[2], eventData[3], eventData[4]);  
                    eventList.add(event);  
                }  
            }  
        } catch (IOException e) {  
            System.out.println("Error reading events from file: " + e.getMessage());  
        }  
        return eventList;  
    }  

    // Load events into a TreeMap for quick access via ID  
    public TreeMap<String, Event> loadEventsIntoMap() {  
        TreeMap<String, Event> eventMap = new TreeMap<>();  
        ArrayList<Event> events = retrieveEvents();  

        for (int i = 0; i < events.size(); i++) {  
            Event event = events.getEntry(i);  
            eventMap.put(event.getId(), event);  
        }  

        return eventMap;  
    }  

    // Generate a new ID for events  
    public String generateNewId(HashSet<Event> eventSet) {
        int highestId = 0;

        // Using the iterator to loop through the HashSet  
        for (Event event : eventSet) {
            try {
                int currentId = Integer.parseInt(event.getId()); // Assume ID is numeric  
                if (currentId > highestId) {
                    highestId = currentId;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid event ID format: " + event.getId());
            }
        }

        return String.valueOf(highestId + 1); // Generate new ID by incrementing the highest  
    }
}  