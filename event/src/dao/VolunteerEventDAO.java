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

    private SetInterface<VolunteerEvent> volunteerEvents = new HashSet<>(); // HashSet to store volunteer-event relationships  
    private MapInterface<String, VolunteerEvent> volunteerEventMap = new HashMap<>(); // Map to find events by volunteer ID  
    private final String filePath; // Path to the volunteer-event file  

    public VolunteerEventDAO() {
        this.filePath = "volunteer_event.txt"; 
        loadIntoHashSet(); 
    }

    // Constructor with filepath  
    public VolunteerEventDAO(String filePath) {
        this.filePath = filePath; 
    }

    // Load volunteer-event pairs into a HashSet 
    public SetInterface<VolunteerEvent> loadIntoHashSet() {
        volunteerEvents.clear(); 
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(", ");
                if (eventData.length == 2) { 
                    String volunteerId = eventData[0];
                    String eventId = eventData[1];
                    Volunteer volunteer = new Volunteer(volunteerId, "", "", ""); 
                    Event event = new Event(eventId, "", "", "", ""); 
                    VolunteerEvent volunteerEvent = new VolunteerEvent(volunteer);
                    volunteerEvent.addEvent(event); 
                    volunteerEvents.add(volunteerEvent); 
                } else {
                    System.out.println("Invalid data length for line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return volunteerEvents; 
    }

    // Load volunteer-event pairs into a HashMap 
    public MapInterface<String, VolunteerEvent> loadIntoHashMap() {
        volunteerEventMap.clear(); 
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(", ");
                if (eventData.length == 2) { 
                    String volunteerId = eventData[0];
                    String eventId = eventData[1];

                    Volunteer volunteer = new Volunteer(volunteerId, "", "", ""); 
                    Event event = new Event(eventId, "", "", "", ""); 
                    VolunteerEvent volunteerEvent = new VolunteerEvent(volunteer);
                    volunteerEvent.addEvent(event);  

                    if (volunteerEventMap.containsKey(volunteerId)) {
                        volunteerEventMap.get(volunteerId).addEvent(event); 
                    } else {
                        volunteerEventMap.put(volunteerId, volunteerEvent); 
                    }
                } else {
                    System.out.println("Invalid data length for line: " + line); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  
        }
        return volunteerEventMap; 
    }

    // Save all volunteer-event pairs from HashSet
    public void save(MapInterface<Volunteer, SetInterface<Event>> volunteerEventsToSave) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            Set<Volunteer> volunteers = volunteerEventsToSave.keySet();
            for (Volunteer volunteer : volunteers) {
                SetInterface<Event> events = volunteerEventsToSave.get(volunteer);
                if (events != null) {
                    for (Event event : events) {   
                        String line = String.join(", ", volunteer.getId(), event.getId());
                        bw.write(line);  
                        bw.newLine(); 
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieve volunteer-event pairs into an ArrayList  
    public ListInterface<VolunteerEvent> loadIntoArrayList() {
        ListInterface<VolunteerEvent> volunteerEventList = new ArrayList<>(); 
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] eventData = line.split(", ");
                if (eventData.length == 2) {
                    String volunteerId = eventData[0];
                    String eventId = eventData[1];
                    Volunteer volunteer = new Volunteer(volunteerId, "", "", ""); 
                    Event event = new Event(eventId, "", "", "", "");   
                    VolunteerEvent volunteerEvent = new VolunteerEvent(volunteer);
                    volunteerEvent.addEvent(event); 
                    volunteerEventList.add(volunteerEvent); 
                } else {
                    System.out.println("Invalid data length for line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();   
        }
        return volunteerEventList;
    }
}
