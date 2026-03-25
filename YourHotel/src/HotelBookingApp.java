import java.util.HashMap;
import java.util.Map;

/**
 * UseCase4RoomSearch
 *
 * This class demonstrates a read-only room search system for the Hotel Booking App.
 * Guests can view available rooms and their details without modifying inventory.
 *
 * @author YourName
 * @version 4.0
 */

// Abstract Room class
abstract class Room {
    private String roomType;
    private int beds;
    private double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price);
    }
}

// Concrete room types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2000.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000.0);
    }
}

// Inventory class (centralized)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // Simulate unavailable
        inventory.put("Suite Room", 2);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return new HashMap<>(inventory); // Return a copy to prevent modification
    }
}

// Search service for guests
class RoomSearchService {
    private RoomInventory inventory;
    private Room[] rooms;

    public RoomSearchService(RoomInventory inventory, Room[] rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void displayAvailableRooms() {
        System.out.println("Available Rooms:");
        boolean anyAvailable = false;
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available : " + available + "\n");
                anyAvailable = true;
            }
        }
        if (!anyAvailable) {
            System.out.println("Sorry, no rooms are available at the moment.");
        }
    }
}

// Main class
public class  HotelBookingApp{
    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println(" Hotel Booking System - Room Search ");
        System.out.println(" Version: 4.0 ");
        System.out.println("=======================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize rooms
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // Create search service
        RoomSearchService searchService = new RoomSearchService(inventory, rooms);

        // Display available rooms
        searchService.displayAvailableRooms();

        System.out.println("Thank you for using Hotel Booking System!");
    }
}