/**
 *
 * @author Chia Yuxuan
 */
package dao;

import adt.*;
import entity.*;
import java.io.*;
import java.util.Scanner;
import utility.MessageUI;

public class VolunteerDAO {

    private ListInterface<Volunteer> volunteers;
    private ListInterface<Event> events;
    private final String FILE_NAME = "volunteer.txt";
    private final String ID_PREFIX = "V";
    private SetInterface<Volunteer> volunteers1 = new HashSet<>(); // HashSet to store volunteers  
    private MapInterface<String, Volunteer> volunteerMap = new HashMap<>(); // HashMap to map volunteer IDs to Volunteer objects  
    private final String filePath; // Path to the file  

    public VolunteerDAO() {
        volunteers = new ArrayList<>();
        events = new ArrayList<>();
        loadFromFile(); // Load volunteers from the file when the system starts
        this.filePath = "volunteer.txt";   
        this.volunteers1 = new HashSet<>(); 
        this.volunteerMap = new HashMap<>();   
        loadIntoHashSet();   
    }

    public VolunteerDAO(String filePath) {
        this.filePath = filePath; 
        this.volunteers1 = new HashSet<>();  
        this.volunteerMap = new HashMap<>();  
    }

    public void add(Volunteer volunteer) {
        volunteer.setVolunteerId(generateNewId());
        volunteers.add(volunteer);
        saveToFile();
    }

    public boolean remove(String volunteerId) {
        Volunteer volunteer = get(volunteerId);
        if (volunteer != null) {
            volunteers.remove(volunteer);
            saveToFile();
            return true;
        }
        return false;
    }

    public Volunteer get(String volunteerId) {
        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.getEntry(i);
            if (volunteer.getVolunteerId().equals(volunteerId)) {
                return volunteer;
            }
        }
        return null;
    }

    public ListInterface<Volunteer> getAllVolunteers() {
        return volunteers;
    }

    public void update(Volunteer volunteer) {
        saveToFile();
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < volunteers.size(); i++) {
                Volunteer volunteer = volunteers.getEntry(i);
                writer.write(volunteer.getVolunteerId() + ","
                        + volunteer.getName() + ","
                        + volunteer.getContactNumber() + ","
                        + volunteer.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load volunteer data from file
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] data = line.split(",");
                    if (data.length == 4) {
                        // Assuming Volunteer constructor takes (ID, Name, Contact, Email)
                        volunteers.add(new Volunteer(data[0], data[1], data[2], data[3]));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Filter volunteers based on criteria
    public ListInterface<Volunteer> filter(String criteria) {
        ListInterface<Volunteer> filteredVolunteers = new ArrayList<>();

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.getEntry(i);
            if (volunteer.getName().contains(criteria)
                    || volunteer.getEmail().contains(criteria)) {
                filteredVolunteers.add(volunteer);
            }
        }

        return filteredVolunteers;
    }

    private String generateNewId() {
        int maxId = 0;

        // Iterate through the list to find the highest existing ID
        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.getEntry(i);
            String currentId = volunteer.getVolunteerId();

            // Extract numeric part from ID
            String numericPart = currentId.replace(ID_PREFIX, "");

            try {
                int idNum = Integer.parseInt(numericPart);
                if (idNum > maxId) {
                    maxId = idNum;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing ID: " + currentId);
            }
        }

        int newIdNum = maxId + 1;

        return ID_PREFIX + String.format("%03d", newIdNum);
    }

    public ListInterface<Event> loadEventsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("events.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by comma
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String eventId = parts[0].trim();
                    String name = parts[1].trim();
                    String date = parts[2].trim();
                    String location = parts[3].trim();
                    String description = parts[4].trim();

                    Event event = new Event(eventId, name, date, location, description);
                    events.add(event);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }

    public HashSet<String> loadAssignedEvents() {
        HashSet<String> assignedEvents = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("volunteer_event.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by comma
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String eventId = parts[1].trim();  // Event ID is the second element
                    assignedEvents.add(eventId);  // Add event ID to the set
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            MessageUI.showError("Error loading assigned events.");
        }
        return assignedEvents;
    }

    public void saveEventVolunteerAssignment(String volunteerId, String eventId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("volunteer_event.txt", true))) {
            writer.write(volunteerId + ", " + eventId);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ListInterface<Event> loadEventsForVolunteer(String volunteerId) {
        ListInterface<Event> assignedEvents = new ArrayList<>(); // Initialize a new list to store assigned events

        try (BufferedReader reader = new BufferedReader(new FileReader("volunteer_event.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String fileVolunteerId = parts[0].trim();
                    String eventId = parts[1].trim();

                    if (fileVolunteerId.equals(volunteerId)) {
                        // Fetch the Event object based on eventId
                        Event event = findEventById(eventId);
                        if (event != null) {
                            assignedEvents.add(event); // Add the event to the list
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return assignedEvents;
    }

    // Method to find event by eventId
    public Event findEventById(String eventId) {
        for (int i = 0; i < events.size(); i++) {
            Event event = events.getEntry(i);
            if (event.getEventId().equals(eventId)) {
                return event;
            }
        }
        return null;
    }

    //kevin
    public SetInterface<Volunteer> loadIntoHashSet() {
        volunteers1.clear();  
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] volunteerData = line.split(","); 
                if (volunteerData.length == 4) { 
                    Volunteer volunteer = new Volunteer(volunteerData[0], volunteerData[1], volunteerData[2], volunteerData[3]);
                    volunteers1.add(volunteer);   
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  
        }
        return volunteers1;  
    }

    // Load volunteers into a HashMap 
    public MapInterface<String, Volunteer> loadIntoHashMap() {
        volunteerMap.clear(); 
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] volunteerData = line.split(","); 
                if (volunteerData.length == 4) { 
                    Volunteer volunteer = new Volunteer(volunteerData[0], volunteerData[1], volunteerData[2], volunteerData[3]);

                    
                    if (!volunteerMap.containsKey(volunteer.getVolunteerId())) {
                        volunteerMap.put(volunteer.getVolunteerId(), volunteer); 
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions  
        }
        return volunteerMap; // Return the populated map of volunteers  
    }

    /*public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Volunteer volunteer : volunteers1) { // Iterate over each volunteer  
                String line = String.join(",", volunteer.getVolunteerId(), volunteer.getName(), volunteer.getEmail(), volunteer.getContactNumber()); // Construct the line from volunteer fields  
                bw.write(line); // Write the constructed line to the file  
                bw.newLine(); // Add a new line  
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions  
        }
    }*/

    // Retrieve volunteers1 into an ArrayList  
    public ListInterface<Volunteer> retrieveToArrayList() {
        ListInterface<Volunteer> volunteerList = new ArrayList<>();
        for (Volunteer volunteer : volunteers1) { 
            volunteerList.add(volunteer); 
        }
        return volunteerList;  
    }
}
