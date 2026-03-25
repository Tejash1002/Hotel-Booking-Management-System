import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3InventorySetup
 *
 * This class demonstrates centralized inventory management using HashMap.
 * It replaces scattered availability variables with a single source of truth.
 *
 * @author YourName
 * @version 3.1
 */

// Inventory class to manage room availability
class RoomInventory {

    // HashMap to store room type and availability
    private HashMap<String, Integer> inventory;

    // Constructor to initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Method to get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Method to display all inventory
    public void displayInventory() {
        System.out.println("Current Room Availability:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main class (Entry Point)
public class HotelBookingApp {

    public static void main(String[] args) {

        // Header
        System.out.println("=======================================");
        System.out.println(" Hotel Booking System ");
        System.out.println(" Version: 3.1 ");
        System.out.println("=======================================\n");

        // Create inventory object
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Update availability (example)
        System.out.println("\nUpdating Single Room availability...\n");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nThank you for using Hotel Booking System!");
public class HotelBookingApp {
    public static void main(String args[]){
        System.out.println("Welcome to Your Hotel");
        System.out.println("Version: 1.0");
        System.out.println("Application Started Successfully");
    }
}
