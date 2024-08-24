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
 * @author leezh
 */
public class DoneeDAO {

    public String fileName;

    public DoneeDAO() {

    }

    public DoneeDAO(String fileName) {
        this.fileName = fileName;
    }

    public void saveToFile(ListInterface<Donee> doneeList) {
        try (ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            ooStream.writeObject(doneeList);
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found: " + fileName);
        } catch (IOException ex) {
            System.out.println("\nCannot save to file: " + ex.getMessage());
        }
    }

    public ListInterface<Donee> retrieveFromFile() {
        ListInterface<Donee> doneeList = new ArrayList<>(); // Use your custom list implementation

        try (ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(fileName))) {
            doneeList = (ListInterface<Donee>) oiStream.readObject();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file: " + fileName);
        } catch (IOException ex) {
            System.out.println("\nCannot read from file: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found: " + ex.getMessage());
        }

        return doneeList;
    }

    public String generateNewId() {
        ListInterface<Donee> doneeList = retrieveFromFile();
        String newId = "DE001";
        int maxId = 0;

        for (int i = 0; i < doneeList.size(); i++) {
            Donee donee = doneeList.getEntry(i);
            String id = donee.getId();

            if (id.startsWith("DE")) {
                try {
                    int num = Integer.parseInt(id.substring(2));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException e) {

                    System.out.println("Error parsing ID: " + id);
                }
            }
        }

        maxId++;
        newId = String.format("DE%03d", maxId);
        return newId;
    }

    
}
