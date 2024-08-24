/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import dao.*;
import boundary.*;
import entity.Donee;

public class doneeMaintenance {

    private ListInterface<Donee> doneeList = new ArrayList<>();
    private DoneeDAO doneeDAO = new DoneeDAO();
    private DoneeUI doneeUI = new DoneeUI();
    
    
    public doneeMaintenance() {
        //doneeList = new ArrayList<>();
        DoneeDAO doneeDao = new DoneeDAO("donee.dat");
        doneeList = doneeDAO.retrieveFromFile();
    }

    public boolean addDonee(Donee donee) {   
        return doneeList.add(donee);
    }
}
