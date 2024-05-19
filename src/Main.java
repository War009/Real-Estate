import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Utility class for file handling
class FileUtils {
    public static void writeToFile(String filename, String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(data);
            writer.newLine();
        }
    }

    public static List<String> readFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}

// Interface for user operations
interface UserOperations {
    void register() throws IOException;
    boolean login(String username, String password);
    void logout();
    void updateProfile(String username, String email, String password);
}

// Abstract class for User
abstract class User implements UserOperations {
    protected int userId;
    protected String username;
    protected String email;
    protected String password;

    public User(int userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

// Interface for property management
interface PropertyManagement {
    void addProperty(Property property) throws IOException;
    void removeProperty(Property property) throws IOException;
    List<Property> searchProperty(String criteria);
}

// Concrete class for Seller
class Seller extends User implements PropertyManagement {
    private String contactInfo;
    private int rating;
    private int activeListingsCount;
    private List<Property> properties;

    public Seller(int userId, String username, String email, String password, String contactInfo, int rating) {
        super(userId, username, email, password);
        this.contactInfo = contactInfo;
        this.rating = rating;
        this.activeListingsCount = 0;
        this.properties = new ArrayList<>();
    }

    public List<Property> getProperties() {
        return properties;
    }

    @Override
    public void register() throws IOException {
        FileUtils.writeToFile("users.txt", "Seller registered: " + username);
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public void logout() {
        System.out.println("Seller logged out: " + username);
    }

    @Override
    public void updateProfile(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        System.out.println("Seller profile updated: " + username);
    }

    @Override
    public void addProperty(Property property) throws IOException {
        properties.add(property);
        activeListingsCount++;
        FileUtils.writeToFile("properties.txt", "Property added by seller: " + property.getPropertyId());
    }

    @Override
    public void removeProperty(Property property) throws IOException {
        properties.remove(property);
        activeListingsCount--;
        FileUtils.writeToFile("properties.txt", "Property removed by seller: " + property.getPropertyId());
    }

    @Override
    public List<Property> searchProperty(String criteria) {
        List<Property> result = new ArrayList<>();
        for (Property property : properties) {
            if (property.getLocation().contains(criteria)) {
                result.add(property);
            }
        }
        return result;
    }
}

// Concrete class for Buyer
class Buyer extends User {
    private List<String> wishlist;
    private int budgetRange;
    private String locationWanted;

    public Buyer(int userId, String username, String email, String password, int budgetRange, String locationWanted) {
        super(userId, username, email, password);
        this.wishlist = new ArrayList<>();
        this.budgetRange = budgetRange;
        this.locationWanted = locationWanted;
    }

    @Override
    public void register() throws IOException {
        FileUtils.writeToFile("users.txt", "Buyer registered: " + username);
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public void logout() {
        System.out.println("Buyer logged out: " + username);
    }

    @Override
    public void updateProfile(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        System.out.println("Buyer profile updated: " + username);
    }

    public void addToWishlist(String property) {
        wishlist.add(property);
        System.out.println("Property added to wishlist: " + property);
    }

    public void setBudgetRange(int budgetRange) {
        this.budgetRange = budgetRange;
        System.out.println("Budget range set to: " + budgetRange);
    }

    public List<Property> searchProperty(PropertyManagementSystem pms, String criteria) {
        System.out.println("Searching properties with criteria: " + criteria);
        List<Property> result = new ArrayList<>();
        for (Property property : pms.getPropertyList()) {
            if (property.getLocation().contains(criteria)) {
                result.add(property);
            }
        }
        return result;
    }

    public void viewDetails(Property property) {
        System.out.println("Viewing details for property: " + property.getPropertyId());
    }
}

// Property class
class Property {
    private int propertyId;
    private String location;
    private int price;
    private String type;
    private boolean available;

    public Property(int propertyId, String location, int price, String type) {
        this.propertyId = propertyId;
        this.location = location;
        this.price = price;
        this.type = type;
        this.available = true;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public String getLocation() {
        return location;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void updateProperty(String location, int price, String type) throws IOException {
        this.location = location;
        this.price = price;
        this.type = type;
        FileUtils.writeToFile("properties.txt", "Property updated: " + propertyId);
    }

    public void removeProperty() throws IOException {
        FileUtils.writeToFile("properties.txt", "Property removed: " + propertyId);
    }

    public static List<Property> searchProperty(String criteria, List<Property> propertyList) {
        List<Property> properties = new ArrayList<>();
        for (Property property : propertyList) {
            if (property.getLocation().toLowerCase().contains(criteria.toLowerCase()) ||
                    property.getType().toLowerCase().contains(criteria.toLowerCase())) {
                properties.add(property);
            }
        }
        return properties;
    }
}

// Booking class
class Booking {
    private int bookingId;
    private int propertyId;
    private String bookingDate;
    private String checkInDate;
    private String checkOutDate;

    public Booking(int bookingId, int propertyId, String bookingDate, String checkInDate, String checkOutDate) {
        this.bookingId = bookingId;
        this.propertyId = propertyId;
        this.bookingDate = bookingDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void cancelBooking() throws IOException {
        FileUtils.writeToFile("bookings.txt", "Booking cancelled: " + bookingId);
    }

    public boolean checkAvailability() {
        return true;
    }

    public void confirmBooking() throws IOException {
        FileUtils.writeToFile("bookings.txt", "Booking confirmed: " + bookingId);
    }
}

// Property Management System class
class PropertyManagementSystem {
    List<Property> propertyList;
    private List<User> userList;
    private List<Booking> bookingList;

    public PropertyManagementSystem() {
        this.propertyList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.bookingList = new ArrayList<>();
    }
    // Getter methods
    public List<Property> getPropertyList() {
        return propertyList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void addProperty(Property property) throws IOException {
        propertyList.add(property);
        FileUtils.writeToFile("properties.txt", "Property added to system: " + property.getPropertyId());
    }

    public void removeProperty(Property property) throws IOException {
        propertyList.remove(property);
        FileUtils.writeToFile("properties.txt", "Property removed from system: " + property.getPropertyId());
    }

    public void registerUser(User user) throws IOException {
        userList.add(user);
        user.register();
    }

    public User authenticateUser(String username, String password) {
        for (User user : userList) {
            if (user.login(username, password)) {
                System.out.println("User authenticated: " + username);
                return user;
            }
        }
        System.out.println("Authentication failed for user: " + username);
        return null;
    }

    public void processBooking(Booking booking) throws IOException {
        if (booking.checkAvailability()) {
            booking.confirmBooking();
            bookingList.add(booking);
        } else {
            System.out.println("Booking failed for property: " + booking.getPropertyId());
        }
    }

    public void cancelBooking(Booking booking) throws IOException {
        booking.cancelBooking();
        bookingList.remove(booking);
    }
}


// Main class to run the system
public class Main {
    private static PropertyManagementSystem pms = new PropertyManagementSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            while (true) {
                System.out.println("Welcome to the Property Management System");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        registerUser();
                        break;
                    case 2:
                        loginUser();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void registerUser() throws IOException {
        System.out.println("Register as: 1. Seller  2. Buyer");
        int userType = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userType == 1) {
            System.out.print("Enter contact info: ");
            String contactInfo = scanner.nextLine();
            System.out.print("Enter rating: ");
            int rating = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Seller seller = new Seller(pms.getUserList().size() + 1, username, email, password, contactInfo, rating);
            pms.registerUser(seller);
            System.out.println("Seller registered successfully!");

        } else if (userType == 2) {
            System.out.print("Enter budget range: ");
            int budgetRange = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter location wanted: ");
            String locationWanted = scanner.nextLine();

            Buyer buyer = new Buyer(pms.getUserList().size() + 1, username, email, password, budgetRange, locationWanted);
            pms.registerUser(buyer);
            System.out.println("Buyer registered successfully!");

        } else {
            System.out.println("Invalid user type. Registration failed.");
        }
    }

    private static void loginUser() throws IOException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = pms.authenticateUser(username, password);
        if (user != null) {
            if (user instanceof Seller) {
                sellerMenu((Seller) user);
            } else if (user instanceof Buyer) {
                buyerMenu((Buyer) user);
            }
        }
    }

    private static void sellerMenu(Seller seller) throws IOException {
        while (true) {
            System.out.println("Seller Menu");
            System.out.println("1. Add Property");
            System.out.println("2. Remove Property");
            System.out.println("3. Search Property");
            System.out.println("4. Update Profile");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter property ID: ");
                    int propertyId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter location: ");
                    String location = scanner.nextLine();
                    System.out.print("Enter price: ");
                    int price = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter type: ");
                    String type = scanner.nextLine();

                    Property property = new Property(propertyId, location, price, type);
                    seller.addProperty(property);
                    pms.addProperty(property);
                    System.out.println("Property added successfully!");
                    break;

                case 2:
                    System.out.print("Enter property ID to remove: ");
                    propertyId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    property = new Property(propertyId, "", 0, "");
                    seller.removeProperty(property);
                    pms.removeProperty(property);
                    System.out.println("Property removed successfully!");
                    break;

                case 3:
                    System.out.print("Enter search criteria: ");
                    String criteria = scanner.nextLine();
                    List<Property> properties = seller.searchProperty(criteria);
                    System.out.println("Search results:");
                    for (Property prop : properties) {
                        System.out.println("ID: " + prop.getPropertyId() + ", Location: " + prop.getLocation() + ", Price: " + prop.getPrice() + ", Type: " + prop.getType());
                    }
                    break;

                case 4:
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    seller.updateProfile(newUsername, newEmail, newPassword);
                    System.out.println("Profile updated successfully!");
                    break;

                case 5:
                    seller.logout();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void buyerMenu(Buyer buyer) throws IOException {
        while (true) {
            System.out.println("Buyer Menu");
            System.out.println("1. Search Property");
            System.out.println("2. View Property Details");
            System.out.println("3. Add Property to Wishlist");
            System.out.println("4. Update Profile");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter search criteria: ");
                    String criteria = scanner.nextLine();
                    List<Property> properties = buyer.searchProperty(pms, criteria);
                    System.out.println("Search results:");
                    for (Property property : properties) {
                        System.out.println("ID: " + property.getPropertyId() + ", Location: " + property.getLocation() + ", Price: " + property.getPrice() + ", Type: " + property.getType());
                    }
                    break;

                case 2:
                    System.out.print("Enter property ID to view details: ");
                    int propertyId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Property propertyToView = null;
                    for (Property property : pms.propertyList) {
                        if (property.getPropertyId() == propertyId) {
                            propertyToView = property;
                            break;
                        }
                    }
                    if (propertyToView != null) {
                        buyer.viewDetails(propertyToView);
                    } else {
                        System.out.println("Property not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter property ID to add to wishlist: ");
                    propertyId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    buyer.addToWishlist("Property ID: " + propertyId);
                    break;

                case 4:
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    buyer.updateProfile(newUsername, newEmail, newPassword);
                    System.out.println("Profile updated successfully!");
                    break;

                case 5:
                    buyer.logout();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}