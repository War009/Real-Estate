#Real Estate Management System
##Overview
The Real Estate Management System is a Java-based application designed to facilitate property management, booking, and user interactions in the real estate domain. This README provides an overview of the project's code logic, architecture, and approaches used in its development.

###Code Logic Overview
The system comprises several classes and interfaces that handle different aspects of property management, user interactions, and bookings. Key components include:

User: Abstract class representing a user with common attributes and methods for registration, login, and profile management.
Seller and Buyer: Concrete subclasses of User, representing sellers and buyers respectively, with additional functionalities such as property management and property search.
Property: Class representing a real estate property with attributes like location, price, and availability status. Includes methods for property management and search.
Booking: Class representing a booking made by a buyer for a specific property, including booking dates and status.
PropertyManagementSystem: Main class orchestrating property management, user authentication, and booking processes. Manages lists of properties, users, and bookings.
####Approaches Used
Object-Oriented Design: The project follows object-oriented design principles to ensure modularity, encapsulation, and code reusability.
Interface Segregation: Interfaces like UserOperations and PropertyManagement are used to define specific sets of operations, promoting loose coupling and flexibility.
File Handling: Java's file handling capabilities are utilized for reading and writing user, property, and booking data to persistent storage.
User Authentication: Authentication of users is achieved by matching provided credentials with stored user data, ensuring secure access to the system.
#####Technologies Used
Java
Java File I/O
UML for system design visualization
######Usage Instructions
Clone the repository to your local machine.
Compile the Java files using a Java compiler.
Run the Main class to start the Real Estate Management System.
Follow the on-screen prompts to register as a seller or buyer, manage properties, search for properties, and make bookings.
