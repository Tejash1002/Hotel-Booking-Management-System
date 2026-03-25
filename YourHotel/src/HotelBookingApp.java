import java.io.*;
import java.util.*;

/**
 * UseCase12DataPersistenceRecovery
 *
 * Demonstrates data persistence and system recovery for hotel booking system.
 * Inventory and booking history are saved to a file and restored upon restart.
 *
 * Author: YourName
 * Version: 12.0
 */

// Represents a confirmed reservation
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "ReservationID: " + reservationId + " | Guest: " + guestName + " | RoomType: " + roomType;
    }
}

// Inventory class with persistence support
class PersistentInventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> inventory;

    public PersistentInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available <= 0) {
            return false;
        }
        inventory.put(roomType, available - 1);
        return true;
    }

    public void releaseRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public Map<String, Integer> getInventoryMap() {
        return inventory;
    }

    public void setInventoryMap(Map<String, Integer> map) {
        inventory = map;
    }
}

// Service to save/load system state
class PersistenceService {
    private static final String FILE_NAME = "booking_system_data.ser";

    public static void saveState(PersistentInventory inventory, List<Reservation> history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(history);
            System.out.println("\nSystem state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadState(PersistentInventory inventory, List<Reservation> history) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No saved state found. Starting fresh.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            PersistentInventory loadedInventory = (PersistentInventory) ois.readObject();
            List<Reservation> loadedHistory = (List<Reservation>) ois.readObject();
            inventory.setInventoryMap(loadedInventory.getInventoryMap());
            history.clear();
            history.addAll(loadedHistory);
            System.out.println("System state restored successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading system state: " + e.getMessage());
        }
    }
}

// Main application class
public class  HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println(" Hotel Booking System - Data Persistence & Recovery ");
        System.out.println(" Version: 12.0 ");
        System.out.println("=======================================\n");

        PersistentInventory inventory = new PersistentInventory();
        List<Reservation> bookingHistory = new ArrayList<>();

        // Load previous system state if available
        PersistenceService.loadState(inventory, bookingHistory);

        // Simulate some bookings
        Reservation r1 = new Reservation("R001", "Alice", "Single Room");
        if (inventory.allocateRoom(r1.getRoomType())) bookingHistory.add(r1);

        Reservation r2 = new Reservation("R002", "Bob", "Suite Room");
        if (inventory.allocateRoom(r2.getRoomType())) bookingHistory.add(r2);

        // Display current state
        inventory.displayInventory();
        System.out.println("\nBooking History:");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }

        // Save state before application exits
        PersistenceService.saveState(inventory, bookingHistory);

        System.out.println("\nThank you for using Hotel Booking System!");
    }
}
