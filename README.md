Overview
This is a Java-based gym class booking system designed to handle user registrations, class bookings, cancellations, and waitlisting. It includes functionalities for both regular users and administrators, and it demonstrates object-oriented design principles and concurrency management.

Features
User Management: Users can sign up, log in, and manage their class bookings.
Class Management: Administrators can create and cancel classes.
Booking and Waitlisting: Users can book classes, join waiting lists, and manage bookings. When a class is full, users can be added to a waitlist.
Concurrency Management: Utilizes locks to handle simultaneous class bookings.
Project Structure
Models: Defines the data structure for User, Class, and UserTier.
Repositories: Provides data storage and retrieval functionality for User and Class entities.
Services: Contains business logic for user and admin operations.
Controllers: Implements functionalities for interacting with users and classes.
Main: The entry point of the application, providing a terminal-based interface for users to interact with the system.
Getting Started
Prerequisites
Java Development Kit (JDK) 11 or higher
IDE (e.g., IntelliJ IDEA, Eclipse) or a text editor
Installation
Clone the Repository:

bash
Copy code
git clone https://github.com/yourusername/gym-class-booking-system.git
Navigate to Project Directory:

bash
Copy code
cd gym-class-booking-system
Compile and Run:

Compile the project:

bash
Copy code
javac -d out src/**/*.java
Run the main class:

bash
Copy code
java -cp out Main
Usage
Start the Application:

Launch the application using the command above.
Follow the terminal prompts to interact with the system.
Available Options:

1. Signup: Register a new user.
2. Login: Log in to the system.
3. Book class: Book a class.
4. Cancel class: Cancel a class booking.
5. Admin login: Log in as an administrator.
6. Admin create class: Create a new class (admin only).
7. Admin cancel class: Cancel a class (admin only).
8. Show class details: View details of a specific class.
9. Show all classes details: View details of all classes.
10. Join waiting list: Join the waitlist for a class.
11. Exit: Exit the application.
Code Structure
Models:

User
Class
UserTier
UserTierConfig
Repositories:

UserRepository
ClassRepository
Services:

UserService
AdminService
ClassesService
Main:

The entry point for the application, handling user interactions through the terminal.
Concurrency Management
The system uses ReentrantLock to handle concurrent access to critical sections of the code, ensuring thread safety during class bookings.

Example
Create a Class (Admin Only):

Log in as an admin.
Create a new class with a maximum capacity.
Book a Class:

Log in as a user.
Book a class, provided it’s not full and the user hasn’t reached their booking limit.
Join the Waiting List:

Log in as a user.
Join the waiting list for a full class.
Troubleshooting
UnsupportedOperationException: Ensure lists are initialized as ArrayList to avoid immutable list issues.
IllegalArgumentException: Verify inputs and ensure that operations are performed on valid entities.
License
This project is licensed under the MIT License - see the LICENSE file for details.

Acknowledgments
Inspired by real-world gym management systems.
Uses Java concurrency utilities for thread safety.
