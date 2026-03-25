import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue
 *
 * Demonstrates a first-come-first-served booking request intake system.
 * Booking requests are queued in arrival order for fair processing.
 * Inventory is not modified at this stage.
 *
 * @author YourName
 * @version 5.0
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

// Queue manager for booking requests
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to the queue
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Process requests in FIFO order (for demonstration)
    public void displayQueue() {
        System.out.println("\nCurrent Booking Queue:");
        if (queue.isEmpty()) {
            System.out.println("No pending booking requests.");
            return;
        }
        for (Reservation res : queue) {
            res.displayRequest();
        }
    }

    // Remove the next request (simulating processing)
    public Reservation nextRequest() {
        return queue.poll();
    }
}

// Main class
public class  HotelBookingApp  {

    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println(" Hotel Booking System - Booking Queue ");
        System.out.println(" Version: 5.0 ");
        System.out.println("=======================================\n");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create some booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Suite Room");
        Reservation r3 = new Reservation("Charlie", "Double Room");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display current queue
        bookingQueue.displayQueue();

        System.out.println("\nThank you for using Hotel Booking System!");
    }
}