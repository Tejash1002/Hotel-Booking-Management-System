import java.util.*;

/**
 * UseCase6RoomAllocationService
 *
 * Demonstrates reservation confirmation and safe room allocation
 * while preventing double-booking in a Hotel Booking System.
 *
 * @author YourName
 * @version 6.0
 */

// Represents a guest booking request
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRequest() {
        System.out.println("Guest: " + guestName + " | Room Requested: " + roomType);
    }
}

// Centralized inventory management
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get current availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Decrement availability after allocation
    public boolean allocateRoom(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        } else {
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Booking queue to store requests (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    public Reservation nextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking service to allocate rooms safely
class BookingService {
    private RoomInventory inventory;
    private BookingRequestQueue requestQueue;
    private HashMap<String, Set<String>> allocatedRooms; // roomType -> allocated IDs
    private int roomIdCounter;

    public BookingService(RoomInventory inventory, BookingRequestQueue queue) {
        this.inventory = inventory;
        this.requestQueue = queue;
        this.allocatedRooms = new HashMap<>();
        this.roomIdCounter = 100; // Starting room ID
    }

    // Process all requests in FIFO order
    public void processBookings() {
        System.out.println("\nProcessing booking requests...\n");

        while (!requestQueue.isEmpty()) {
            Reservation res = requestQueue.nextRequest();
            String type = res.getRoomType();

            // Check inventory
            if (inventory.getAvailability(type) > 0) {
                // Allocate room
                boolean success = inventory.allocateRoom(type);
                if (success) {
                    String roomId = generateRoomId(type);

                    // Record allocation
                    allocatedRooms.putIfAbsent(type, new HashSet<>());
                    allocatedRooms.get(type).add(roomId);

                    // Confirm reservation
                    System.out.println("Reservation confirmed for " + res.getGuestName());
                    System.out.println("Room Type: " + type + " | Assigned Room ID: " + roomId + "\n");
                }
            } else {
                System.out.println("Sorry, " + res.getGuestName() + ". No " + type + " available.\n");
            }
        }
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        roomIdCounter++;
        return roomType.substring(0, 2).toUpperCase() + roomIdCounter;
    }

    // Display all allocated rooms
    public void displayAllocatedRooms() {
        System.out.println("Allocated Room IDs:");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Main class
public class HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println(" Hotel Booking System - Room Allocation ");
        System.out.println(" Version: 6.0 ");
        System.out.println("=======================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize booking queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Add some booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Suite Room"));
        queue.addRequest(new Reservation("Charlie", "Double Room"));
        queue.addRequest(new Reservation("Diana", "Suite Room"));
        queue.addRequest(new Reservation("Eve", "Suite Room")); // Should fail if only 2 suites

        // Create booking service
        BookingService service = new BookingService(inventory, queue);

        // Process bookings
        service.processBookings();

        // Show allocated rooms
        service.displayAllocatedRooms();

        // Show updated inventory
        inventory.displayInventory();

        System.out.println("\nThank you for using Hotel Booking System!");
    }
}