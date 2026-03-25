import java.util.*;

/**
 * UseCase8BookingHistoryReport
 *
 * Demonstrates booking history tracking and reporting for a hotel booking system.
 * Confirms reservations are stored in order and can be reviewed by an admin.
 *
 * @author YourName
 * @version 8.0
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
        System.out.println("Guest: " + guestName + " | Room: " + roomType + " | Room ID: " + roomId);
    }
}

// Stores confirmed booking history
class BookingHistory {
    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }

    // Add a confirmed reservation
    public void addReservation(Reservation reservation) {
        confirmedBookings.add(reservation);
        System.out.println("Booking recorded for " + reservation.getGuestName());
    }

    // Retrieve all bookings
    public List<Reservation> getAllBookings() {
        return new ArrayList<>(confirmedBookings); // return a copy to prevent modification
    }
}

// Generates reports from booking history
class BookingReportService {
    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Display all confirmed bookings
    public void displayAllBookings() {
        System.out.println("\n--- Booking History Report ---");
        List<Reservation> bookings = history.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings have been confirmed yet.");
            return;
        }
        for (Reservation r : bookings) {
            r.displayReservation();
        }
        System.out.println("--- End of Report ---\n");
    }

    // Generate a summary report (count per room type)
    public void displaySummaryByRoomType() {
        System.out.println("--- Booking Summary by Room Type ---");
        Map<String, Integer> summary = new HashMap<>();
        for (Reservation r : history.getAllBookings()) {
            summary.put(r.getRoomType(), summary.getOrDefault(r.getRoomType(), 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " bookings");
        }
        System.out.println("--- End of Summary ---\n");
    }
}

// Main class
public class HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println(" Hotel Booking System - Booking History & Reporting ");
        System.out.println(" Version: 8.0 ");
        System.out.println("=======================================\n");

        // Initialize booking history
        BookingHistory history = new BookingHistory();

        // Sample confirmed reservations
        Reservation r1 = new Reservation("Alice", "Single Room", "SI101");
        Reservation r2 = new Reservation("Bob", "Suite Room", "SU102");
        Reservation r3 = new Reservation("Charlie", "Double Room", "DO103");
        Reservation r4 = new Reservation("Diana", "Suite Room", "SU104");

        // Add reservations to history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);
        history.addReservation(r4);

        // Create report service
        BookingReportService reportService = new BookingReportService(history);

        // Display all bookings
        reportService.displayAllBookings();

        // Display summary by room type
        reportService.displaySummaryByRoomType();

        System.out.println("Thank you for using Hotel Booking System!");
    }
}
