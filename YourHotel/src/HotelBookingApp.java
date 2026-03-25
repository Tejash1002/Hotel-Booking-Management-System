import java.util.*;
import java.util.concurrent.*;

/**
 * UseCase11ConcurrentBookingSimulation
 *
 * Demonstrates thread-safe concurrent booking simulation in hotel booking system.
 * Multiple guests submit booking requests simultaneously, and inventory updates
 * are performed safely using synchronized blocks.
 *
 * Author: YourName
 * Version: 11.0
 */

// Represents a booking request
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Manages thread-safe room inventory
class ThreadSafeInventory {
    private Map<String, Integer> inventory;

    public ThreadSafeInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Allocate a room safely
    public synchronized boolean allocateRoom(String roomType, String guestName) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available <= 0) {
            System.out.println("No rooms available for " + roomType + " | Guest: " + guestName);
            return false;
        }
        inventory.put(roomType, available - 1);
        System.out.println("Room allocated: " + roomType + " | Guest: " + guestName + " | Remaining: " + (available - 1));
        return true;
    }

    public synchronized void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Worker thread to process booking requests
class BookingWorker implements Runnable {
    private BookingRequest request;
    private ThreadSafeInventory inventory;

    public BookingWorker(BookingRequest request, ThreadSafeInventory inventory) {
        this.request = request;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        inventory.allocateRoom(request.getRoomType(), request.getGuestName());
    }
}

// Main class
public class  HotelBookingApp {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=======================================");
        System.out.println(" Hotel Booking System - Concurrent Booking Simulation ");
        System.out.println(" Version: 11.0 ");
        System.out.println("=======================================\n");

        ThreadSafeInventory inventory = new ThreadSafeInventory();

        // Sample booking requests from multiple guests
        BookingRequest[] requests = {
                new BookingRequest("Alice", "Single Room"),
                new BookingRequest("Bob", "Suite Room"),
                new BookingRequest("Charlie", "Double Room"),
                new BookingRequest("Diana", "Single Room"),
                new BookingRequest("Eve", "Suite Room"),
                new BookingRequest("Frank", "Single Room"),
                new BookingRequest("Grace", "Double Room"),
                new BookingRequest("Hank", "Suite Room") // Likely to fail if suites exhausted
        };

        // Thread pool to simulate concurrent processing
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (BookingRequest req : requests) {
            executor.submit(new BookingWorker(req, inventory));
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        // Display final inventory state
        inventory.displayInventory();

        System.out.println("\nThank you for using Hotel Booking System!");
    }
}