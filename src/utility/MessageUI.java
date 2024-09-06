/**
 *
 * @author Chia Yuxuan
 */
package utility;

public class MessageUI {
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