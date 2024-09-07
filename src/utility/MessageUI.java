package utility;

/**
 *
 * @author Kat Tan
 */
public class MessageUI {
  
  public static void displayInvalidChoiceMessage() {
    System.out.println("Invalid choice. Please select again.");
  }

  public static void displayExitMessage() {
    System.out.println("Existing to main system...");
  }
  public static void showInfo(String message) {
    System.out.println(message);
}

public static void showSuccess(String message) {
    System.out.println("SUCCESS: " + message);
}

public static void showError(String message) {
    System.out.println("ERROR: " + message);
}

public static void showWarning(String message) {
    System.out.println("WARNING: " + message);
}
  
}
