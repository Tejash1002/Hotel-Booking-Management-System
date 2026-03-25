import java.util.*;

/**
 * UseCase10BookingCancellation
 *
 * Demonstrates safe cancellation of confirmed bookings in a hotel booking system.
 * Inventory counts and booking history are restored using rollback logic.
 *
 * Author: YourName
 * Version: 10.0
 */

// Represents a confirmed reservation
class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void displayReservation() {
        System.out.println("Reservation: " + guestName + " | Room: " + roomType + " | ID: " + roomId);
    }
}

// Manages room inventory
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Allocate a room
    public boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available <= 0) return false;
        inventory.put(roomType, available - 1);
        return true;
    }

    // Rollback a room allocation
    public void releaseRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Tracks confirmed bookings
class BookingHistory {
    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedBookings.add(reservation);
        System.out.println("Booking confirmed for " + reservation.getGuestName());
    }

    public boolean removeReservation(String roomId) {
        Iterator<Reservation> iterator = confirmedBookings.iterator();
        while (iterator.hasNext()) {
            Reservation r = iterator.next();
            if (r.getRoomId().equals(roomId)) {
                iterator.remove();
                System.out.println("Booking cancelled for " + r.getGuestName());
                return true;
            }
        }
        return false;
    }

    public Reservation getReservationByRoomId(String roomId) {
        for (Reservation r : confirmedBookings) {
            if (r.getRoomId().equals(roomId)) return r;
        }
        return null;
    }

    public void displayAllBookings() {
        if (confirmedBookings.isEmpty()) {
            System.out.println("No confirmed bookings.");
            return;
        }
        System.out.println("\n--- Confirmed Bookings ---");
        for (Reservation r : confirmedBookings) {
            r.displayReservation();
        }
        System.out.println("--- End of List ---\n");
    }
}

// Handles booking cancellations
class CancellationService {
    private RoomInventory inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack; // Tracks recently released room IDs

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String roomId) {
        Reservation res = history.getReservationByRoomId(roomId);
        if (res == null) {
            System.out.println("Cancellation Error: No booking found for Room ID " + roomId + "\n");
            return;
        }

        // Remove from booking history
        history.removeReservation(roomId);

        // Restore inventory
        inventory.releaseRoom(res.getRoomType());

        // Track rollback
        rollbackStack.push(roomId);

        System.out.println("Cancellation processed: Room ID " + roomId + " rolled back successfully.\n");
    }

    public void displayRollbackStack() {
        System.out.println("Rollback Stack (most recent cancellations on top): " + rollbackStack);
    }
}

// Main class
public class  HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println(" Hotel Booking System - Booking Cancellation & Rollback ");
        System.out.println(" Version: 10.0 ");
        System.out.println("=======================================\n");

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancellationService = new CancellationService(inventory, history);

        // Sample confirmed reservations
        Reservation r1 = new Reservation("Alice", "Single Room", "SI101");
        Reservation r2 = new Reservation("Bob", "Suite Room", "SU102");
        Reservation r3 = new Reservation("Charlie", "Double Room", "DO103");

        // Add to history and allocate rooms
        history.addReservation(r1); inventory.allocateRoom(r1.getRoomType());
        history.addReservation(r2); inventory.allocateRoom(r2.getRoomType());
        history.addReservation(r3); inventory.allocateRoom(r3.getRoomType());

        // Display current bookings and inventory
        history.displayAllBookings();
        inventory.displayInventory();

        // Perform cancellations
        cancellationService.cancelBooking("SU102"); // Bob cancels
        cancellationService.cancelBooking("SI101"); // Alice cancels
        cancellationService.cancelBooking("XX999"); // Invalid cancellation

        // Display updated bookings, inventory, and rollback stack
        history.displayAllBookings();
        inventory.displayInventory();
        cancellationService.displayRollbackStack();

        System.out.println("Thank you for using Hotel Booking System!");
    }
}