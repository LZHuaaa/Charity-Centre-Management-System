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

    private SetInterface<Volunteer> volunteers = new HashSet<>(); // HashSet to store volunteers  
    private MapInterface<String, Volunteer> volunteerMap = new HashMap<>(); // HashMap to map volunteer IDs to Volunteer objects  
    private final String filePath; // Path to the file  

    public VolunteerDAO() {
        this.filePath = "volunteer.txt"; 
        this.volunteers = new HashSet<>();
        this.volunteerMap = new HashMap<>(); 
        loadIntoHashSet(); 
    }

    // Constructor  
    public VolunteerDAO(String filePath) {
        this.filePath = filePath;   
        this.volunteers = new HashSet<>(); 
        this.volunteerMap = new HashMap<>(); 
    }

    // Load volunteers into a HashSet
    public SetInterface<Volunteer> loadIntoHashSet() {
        volunteers.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] volunteerData = line.split(", ");   
                if (volunteerData.length == 4) { 
                    Volunteer volunteer = new Volunteer(volunteerData[0], volunteerData[1], volunteerData[2], volunteerData[3]);
                    volunteers.add(volunteer); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return volunteers; 
    }

    // Load volunteers into a HashMap
    public MapInterface<String, Volunteer> loadIntoHashMap() {
        volunteerMap.clear(); 
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] volunteerData = line.split(", ");  
                if (volunteerData.length == 4) {  
                    Volunteer volunteer = new Volunteer(volunteerData[0], volunteerData[1], volunteerData[2], volunteerData[3]);
                    if (!volunteerMap.containsKey(volunteer.getId())) {
                        volunteerMap.put(volunteer.getId(), volunteer); 
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return volunteerMap; 
    }

    // Retrieve volunteers into an ArrayList  
    public ListInterface<Volunteer> retrieveToArrayList() {
        ListInterface<Volunteer> volunteerList = new ArrayList<>();
        for (Volunteer volunteer : volunteers) { 
            volunteerList.add(volunteer);  
        }
        return volunteerList;
    }
}
