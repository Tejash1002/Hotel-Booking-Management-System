import java.util.*;

/**
 * UseCase9ErrorHandlingValidation
 *
 * Demonstrates structured error handling and validation in hotel booking system.
 * Invalid input and inconsistent states are detected and handled gracefully.
 *
 * @author YourName
 * @version 9.0
 */

// Custom exception for invalid bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Represents a reservation request
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) throws InvalidBookingException {
        validateGuestName(guestName);
        validateRoomType(roomType);

        this.guestName = guestName;
        this.roomType = roomType;
    }

    private void validateGuestName(String name) throws InvalidBookingException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }
    }

    private void validateRoomType(String roomType) throws InvalidBookingException {
        List<String> validTypes = Arrays.asList("Single Room", "Double Room", "Suite Room");
        if (!validTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation Request: Guest = " + guestName + ", Room Type = " + roomType);
    }
}

// Manages inventory with validation
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Validate and allocate room
    public void allocateRoom(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Room type does not exist: " + roomType);
        }
        int available = inventory.get(roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
        inventory.put(roomType, available - 1);
        System.out.println("Room allocated: " + roomType + " | Remaining: " + inventory.get(roomType));
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main class
public class  HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println(" Hotel Booking System - Error Handling & Validation ");
        System.out.println(" Version: 9.0 ");
        System.out.println("=======================================\n");

        RoomInventory inventory = new RoomInventory();

        // Sample booking attempts
        String[][] requests = {
                {"Alice", "Single Room"},
                {"", "Double Room"},        // Invalid guest name
                {"Bob", "Suite Room"},
                {"Charlie", "Triple Room"}, // Invalid room type
                {"Diana", "Suite Room"},
                {"Eve", "Suite Room"}       // Should fail if no suites left
        };

        for (String[] req : requests) {
            try {
                Reservation res = new Reservation(req[0], req[1]);
                res.displayReservation();
                inventory.allocateRoom(res.getRoomType());
            } catch (InvalidBookingException e) {
                System.out.println("Booking Error: " + e.getMessage() + "\n");
            } catch (Exception e) {
                System.out.println("Unexpected Error: " + e.getMessage() + "\n");
            }
        }

        // Display final inventory
        inventory.displayInventory();

        System.out.println("\nThank you for using Hotel Booking System!");
    }
}
