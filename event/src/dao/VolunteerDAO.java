/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import adt.*;  
import entity.*;  
import java.io.*;  
/**
 *
 * @author eyong
 */
public class VolunteerDAO {  

    private HashSet<Volunteer> volunteers=new HashSet<>(); // HashSet to store volunteers  
    private HashMap<String, Volunteer> volunteerMap=new HashMap<>(); // HashMap to map volunteer IDs to Volunteer objects  
    private final String filePath; // Path to the file where volunteer data is stored  

    public VolunteerDAO() {  
        this.filePath = "volunteer.txt"; // Default file path  
        this.volunteers = new HashSet<>(); // Initialize the HashSet  
        this.volunteerMap = new HashMap<>(); // Initialize the HashMap  
        loadIntoHashSet(); // Load volunteers from the default file  
    }
    // Constructor  
    public VolunteerDAO(String filePath) {  
        this.filePath = filePath; // Initialize the filePath with the provided one  
        this.volunteers = new HashSet<>(); // Initialize the HashSet  
        this.volunteerMap = new HashMap<>(); // Initialize the HashMap  
    }  

    // Load volunteers into a HashSet from the predefined file  
    public HashSet<Volunteer> loadIntoHashSet() {  
        volunteers.clear(); // Clear existing volunteers  
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {  
            String line;  
            while ((line = br.readLine()) != null) {  
                String[] volunteerData = line.split(", "); // Split the line by comma and space  
                if (volunteerData.length == 4) { // Ensure there are enough data fields  
                    Volunteer volunteer = new Volunteer(volunteerData[0], volunteerData[1], volunteerData[2], volunteerData[3]);  
                    volunteers.add(volunteer); // Add the volunteer to the HashSet  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace(); // Handle any IO exceptions  
        }  
        return volunteers; // Return the loaded volunteers  
    }  

    // Load volunteers into a HashMap from the predefined file  
    public HashMap<String, Volunteer> loadIntoHashMap() {  
        volunteerMap.clear(); // Clear existing volunteers in the map  
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {  
            String line;  
            while ((line = br.readLine()) != null) {  
                
                String[] volunteerData = line.split(", "); // Split the line by comma and space  
                if (volunteerData.length == 4) { // Ensure there are enough data fields  
                    Volunteer volunteer = new Volunteer(volunteerData[0], volunteerData[1], volunteerData[2], volunteerData[3]);  
                    volunteerMap.put(volunteer.getId(), volunteer); // Map volunteer ID to the Volunteer object 
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace(); // Handle any IO exceptions  
        }  
        return volunteerMap; // Return the populated map of volunteers  
    }  

    // Save volunteers from HashSet to the predefined file  
    public void save() {  
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {  
            for (Volunteer volunteer : volunteers) { // Iterate over each volunteer  
                String line = String.join(", ", volunteer.getId(), volunteer.getName(), volunteer.getEmail(), volunteer.getPhone()); // Construct the line from volunteer fields  
                bw.write(line); // Write the constructed line to the file  
                bw.newLine(); // Add a new line  
            }  
        } catch (IOException e) {  
            e.printStackTrace(); // Handle any IO exceptions  
        }  
    }  

    // Retrieve volunteers into an ArrayList  
    public ArrayList<Volunteer> retrieveToArrayList() {  
        ArrayList<Volunteer> volunteerList = new ArrayList<>(); // Create an instance of your custom ArrayList  
        for (Volunteer volunteer : volunteers) { // Use a for-each loop to iterate through volunteers  
            volunteerList.add(volunteer); // Add each volunteer to the ArrayList  
        }  
        return volunteerList; // Return the populated ArrayList  
    }  
}  