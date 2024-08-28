/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import dao.*;
import boundary.*;
import entity.Donee;
import utility.*;

public class doneeMaintenance {

    //private static final long serialVersionUID = 1L;
    private ListInterface<Donee> doneeList = new ArrayList<>();
    private DoneeDAO doneeDAO = new DoneeDAO();
    private DoneeUI doneeUI = new DoneeUI();
    private HashMap<String,Donee> doneeMap = new HashMap<>();
    

    public doneeMaintenance() {
    

        doneeList = doneeDAO.retrieveDonees();
        doneeDAO.retrieveDonations(doneeList);
        
        doneeMap = doneeDAO.loadDoneesIntoMap();
        doneeDAO.loadDonationsIntoDonees(doneeMap);
    }

    public void runDoneeMaintenance() {
        int choice = 0;
        do {
            choice = doneeUI.menu();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    addDonee();
                    break;
                case 2:
                    //remove
                    break;
                case 3:
                    //update
                    break;
                case 4:
                    searchDonee(doneeMap);
                    break;
                case 5:
                    listAll();
                    break;
                case 6:
                    //report
                    break;

                default:
                MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public boolean addDonee() {
        Donee donee = doneeUI.addDonee(doneeList);
        doneeMap.put(donee.getId(), donee);

        return doneeDAO.saveDonees(doneeList);
    }

    public void listAll() {
        doneeUI.listDoneesWithDonations(doneeList);
    }
    
     public void searchDonee(HashMap<String, Donee> doneeMap) {
        doneeUI.searchDonee(doneeMap);
    }

    public void reverseDonees() {
        doneeList.reverse();
    }


    public void displayDonees() {
        for (int i = 0; i < doneeList.size(); i++) {
            Donee donee = doneeList.getEntry(i);
            System.out.println(donee);
        }

    }

    public static void main(String[] args) {
        doneeMaintenance dm = new doneeMaintenance();
        dm.runDoneeMaintenance();
    }
}
