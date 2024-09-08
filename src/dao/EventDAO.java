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

    private ListInterface<Event> eventList = new ArrayList<>();
    private SetInterface<Event> events = new HashSet<>(); 
    private MapInterface<String, Event> eventMap = new HashMap<>(); 
    private final String filePath; 

    public EventDAO() {
        this.filePath = "events.txt"; 
        this.events = new HashSet<>(); 
        this.eventMap = new HashMap<>();   
        loadIntoHashSet(); 
    }

    // Constructor  
    public EventDAO(String filePath) {
        this.filePath = filePath; 
        this.events = new HashSet<>(); 
        this.eventMap = new HashMap<>(); 
    }

    // Load events into a HashSet   
    public SetInterface<Event> loadIntoHashSet() {
        events.clear(); // Clear existing events  
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(", ");
                if (eventData.length == 5) { // Ensure proper data length  
                    Event event = new Event(eventData[0], eventData[1], eventData[2], eventData[3], eventData[4]);
                    events.add(event); 
                } else {
                    System.out.println("Invalid data length for line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return events; 
    }

    // Load events into a HashMap 
    public MapInterface<String, Event> loadIntoHashMap() {
        eventMap.clear(); 
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(", ");
                if (eventData.length == 5) {  
                    Event event = new Event(eventData[0], eventData[1], eventData[2], eventData[3], eventData[4]);
                    eventMap.put(event.getEventId(), event);  
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return eventMap; 
    }

    // Save events from HashSet 
    public void save(SetInterface<Event> eventsToSave) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Event event : eventsToSave) { 
                String line = String.join(", ", event.getEventId(), event.getName(), event.getDate(), event.getLocation(), event.getDescription());
                bw.write(line);
                bw.newLine(); 
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    // Retrieve events into an ArrayList  
    public ListInterface<Event> loadIntoArrayList() {
        ListInterface<Event> eventList = new ArrayList<>(); 
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(", ");
                if (eventData.length == 5) { 
                    Event event = new Event(eventData[0], eventData[1], eventData[2], eventData[3], eventData[4]);
                    eventList.add(event);  
                } else {
                    System.out.println("Invalid data length for line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return eventList; 
    }

}
