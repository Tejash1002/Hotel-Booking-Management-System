abstract class Room{
    String roomType;
    int beds;
    double price;

    //constructor
    public Room(String roomType, int beds, double price){
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    //method to display room details
    public void displayDetails(){
        System.out.println("Room Type  : " + roomType);
        System.out.println("Beds       : " + beds);
        System.out.println("Price      : " + price);
    }

}

//inheritence  extends th abstract class
//single room class
class SingleRoom extends Room{
    public SingleRoom(){
        super("Single Room",1,1000.0);
    }
}

//double room class
class DoubleRoom extends Room{
    public DoubleRoom(){
        super("Double Room",2,2000.0);
    }
}

//suite room class
class SuiteRoom extends Room{
    public SuiteRoom(){
        super("Suit Room",3,5000.0);
    }
}

//main class
public class HotelBookingApp {
    public static void main(String[] args) {

        // Display welcome message
        System.out.println("=======================================");
        System.out.println(" Welcome to Hotel Booking System ");
        System.out.println(" Version: 2.0 ");
        System.out.println("=======================================" + "\n");

        //creating obj using polymorphism
        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        //availability variable
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable  = 2;

        //display single room
        System.out.println("---- Single Room ----");
        r1.displayDetails();
        System.out.println("Available : " + singleAvailable + "\n");

        // Display Double Room details
        System.out.println("---- Double Room ----");
        r2.displayDetails();
        System.out.println("Available : " + doubleAvailable + "\n");

        // Display Suite Room details
        System.out.println("---- Suite Room ----");
        r3.displayDetails();
        System.out.println("Available : " + suiteAvailable + "\n");

        System.out.println("====================Thank you for using Hotel Booking System!=====================");

        // Application ends here
    }
}