/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package control;

import boundary.*;
import utility.*;

/**
 *
 * @author leezh
 */
public class MainMaintenance {

    MainUI ui = new MainUI();

    public void runMain() {
        int choice = 0;
        do {
            choice = ui.menu();
            switch (choice) {
                case 0:
                    System.out.println("\nExiting...Thank you for using our system.");
                    break;
                case 1:
                    new DonorUI().run();
                    break;
                case 2:
                    new doneeMaintenance().runDoneeMaintenance();
                    break;
                case 3:
                    new VolunteerUI().start();
                    break;
                case 4:
                    EventManagement eventManagement = new EventManagement("volunteer_event.txt", "events.txt", "volunteer.txt");
                    eventManagement.runEventManagement();
                    break;

                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public static void main(String[] args) {
        new MainMaintenance().runMain();

    }

}
