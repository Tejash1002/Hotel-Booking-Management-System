import java.util.*;

/**
 * UseCase7AddOnServiceSelection
 *
 * Demonstrates add-on service selection for hotel reservations.
 * Each reservation can have multiple optional services.
 * Core booking and inventory logic remain unchanged.
 *
 * @author YourName
 * @version 7.0
 */

// Represents a guest reservation
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

// Represents an optional add-on service
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println("Service: " + name + " | Cost: ₹" + cost);
    }
}

// Manages add-on services for reservations
class AddOnServiceManager {
    // Maps reservation ID to list of services
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add a service to a reservation
    public void addService(Reservation res, Service service) {
        reservationServices.putIfAbsent(res.getRoomId(), new ArrayList<>());
        reservationServices.get(res.getRoomId()).add(service);
        System.out.println("Added service '" + service.getName() + "' for " + res.getGuestName());
    }

    // Display all services for a reservation
    public void displayServices(Reservation res) {
        List<Service> services = reservationServices.get(res.getRoomId());
        if (services == null || services.isEmpty()) {
            System.out.println(res.getGuestName() + " has no add-on services.");
            return;
        }
        System.out.println("Add-On Services for " + res.getGuestName() + ":");
        double totalCost = 0;
        for (Service s : services) {
            s.displayService();
            totalCost += s.getCost();
        }
        System.out.println("Total Additional Cost: ₹" + totalCost + "\n");
    }
}

// Main class
public class  HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println(" Hotel Booking System - Add-On Services ");
        System.out.println(" Version: 7.0 ");
        System.out.println("=======================================\n");

        // Sample reservations (as if already allocated)
        Reservation r1 = new Reservation("Alice", "Single Room", "SI101");
        Reservation r2 = new Reservation("Bob", "Suite Room", "SU102");

        // Initialize add-on service manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Sample services
        Service breakfast = new Service("Breakfast", 300);
        Service airportPickup = new Service("Airport Pickup", 500);
        Service spa = new Service("Spa Session", 1200);

        // Guests select services
        serviceManager.addService(r1, breakfast);
        serviceManager.addService(r1, airportPickup);
        serviceManager.addService(r2, spa);

        // Display reservations and their selected services
        r1.displayReservation();
        serviceManager.displayServices(r1);

        r2.displayReservation();
        serviceManager.displayServices(r2);

        System.out.println("Thank you for using Hotel Booking System!");
    }
}