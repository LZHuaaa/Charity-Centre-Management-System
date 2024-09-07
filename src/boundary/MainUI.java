package boundary;

import java.util.Scanner;
import java.util.InputMismatchException;

public class MainUI {

    private Scanner scanner = new Scanner(System.in);

    public int menu() {
        int choice = -1;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.println("\nCharity Centre Management System ");
                System.out.println("1. Donor Management Subsystem");
                System.out.println("2. Donee Management Subsystem");
                System.out.println("3. Volunteer Management Subsystem");
                System.out.println("4. Event Management Subsystem");
                System.out.println("0. Exit ");
                System.out.print("Select an option: ");
                
                choice = scanner.nextInt();

                scanner.nextLine();

                valid = true;

            } catch (InputMismatchException e) {

                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }
        return choice;

    }
}
