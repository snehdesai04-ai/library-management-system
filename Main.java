/**
 * LIBRARY MANAGEMENT SYSTEM
 * Built by: Sneh Desai
 * -----------------------------------------------
 * FILE 3 of 4: Main.java
 *
 * This is the STARTING POINT of the program.
 * In Java, every program starts from a method called main().
 * Think of this as the front door of the application.
 *
 * This file:
 *   1. Creates the Library object (from Library.java)
 *   2. Shows a menu to the user
 *   3. Reads user input
 *   4. Calls the right method based on what user selects
 *   5. Keeps looping until user chooses to exit
 */

// ==============================
// IMPORTS
// Scanner lets us read keyboard input from the user
// ==============================
import java.util.Scanner;

public class Main {

    // ==============================
    // main() METHOD
    // This is where Java starts running the program.
    // Every Java program MUST have this exact method signature.
    // ==============================
    public static void main(String[] args) {

        // Create a Scanner to read keyboard input
        // Like a listener waiting for the user to type something
        Scanner scanner = new Scanner(System.in);

        // Create a Library object — this sets up the library with sample books
        // Uses the Library blueprint from Library.java
        Library library = new Library("Regina Public Library");

        // Welcome message
        printWelcome();

        // ==============================
        // MAIN LOOP
        // Keeps showing the menu until the user types 0 to exit.
        // This is called a "do-while" loop —
        // it always runs at least once before checking the condition.
        // ==============================
        int choice = -1;

        do {
            // Show the menu
            printMenu();

            // Read user's choice
            System.out.print("  Enter your choice: ");

            // Check if input is a valid number
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // clear the leftover newline character
            } else {
                System.out.println("  ⚠️  Please enter a number from the menu.");
                scanner.nextLine(); // clear invalid input
                continue;          // go back to top of loop
            }

            System.out.println(); // blank line for readability

            // ==============================
            // SWITCH STATEMENT
            // Runs different code based on user's choice.
            // Like a series of if/else if statements.
            // ==============================
            switch (choice) {

                case 1:
                    // --- DISPLAY ALL BOOKS ---
                    library.displayAll();
                    break;

                case 2:
                    // --- ADD A BOOK ---
                    System.out.println("  ➕ ADD A NEW BOOK");
                    System.out.println("  " + "-".repeat(40));

                    System.out.print("  Enter title:  ");
                    String title = scanner.nextLine().trim();

                    System.out.print("  Enter author: ");
                    String author = scanner.nextLine().trim();

                    System.out.print("  Enter genre:  ");
                    String genre = scanner.nextLine().trim();

                    library.addBook(title, author, genre);
                    break;

                case 3:
                    // --- REMOVE A BOOK ---
                    System.out.println("  🗑️  REMOVE A BOOK");
                    System.out.println("  " + "-".repeat(40));

                    library.displayAll(); // show list so user can see IDs

                    System.out.print("  Enter book ID to remove: ");
                    if (scanner.hasNextInt()) {
                        int removeId = scanner.nextInt();
                        scanner.nextLine();
                        library.removeBook(removeId);
                    } else {
                        System.out.println("  ⚠️  Invalid ID. Please enter a number.");
                        scanner.nextLine();
                    }
                    break;

                case 4:
                    // --- SEARCH FOR A BOOK ---
                    System.out.println("  🔍 SEARCH BOOKS");
                    System.out.println("  " + "-".repeat(40));

                    System.out.print("  Enter title or author to search: ");
                    String keyword = scanner.nextLine().trim();
                    library.searchBook(keyword);
                    break;

                case 5:
                    // --- BORROW A BOOK ---
                    System.out.println("  📖 BORROW A BOOK");
                    System.out.println("  " + "-".repeat(40));

                    library.displayAvailable(); // show only available books

                    System.out.print("  Enter book ID to borrow: ");
                    if (scanner.hasNextInt()) {
                        int borrowId = scanner.nextInt();
                        scanner.nextLine();
                        library.borrowBook(borrowId);
                    } else {
                        System.out.println("  ⚠️  Invalid ID. Please enter a number.");
                        scanner.nextLine();
                    }
                    break;

                case 6:
                    // --- RETURN A BOOK ---
                    System.out.println("  🔄 RETURN A BOOK");
                    System.out.println("  " + "-".repeat(40));

                    System.out.print("  Enter book ID to return: ");
                    if (scanner.hasNextInt()) {
                        int returnId = scanner.nextInt();
                        scanner.nextLine();
                        library.returnBook(returnId);
                    } else {
                        System.out.println("  ⚠️  Invalid ID. Please enter a number.");
                        scanner.nextLine();
                    }
                    break;

                case 0:
                    // --- EXIT ---
                    System.out.println("  👋 Thank you for using the Library System. Goodbye!");
                    break;

                default:
                    // Runs if user enters a number not on the menu
                    System.out.println("  ⚠️  Invalid choice. Please choose a number from the menu.");
            }

            System.out.println(); // blank line between actions

        } while (choice != 0); // keep looping until user enters 0

        // Close the scanner when done (good practice — like closing a file)
        scanner.close();
    }


    // ==============================
    // HELPER METHOD: printWelcome()
    // Prints the welcome banner when program starts.
    // "private static" because it's only used in this class
    // and doesn't need a Library or Book object to work.
    // ==============================
    private static void printWelcome() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════╗");
        System.out.println("  ║      📚 LIBRARY MANAGEMENT SYSTEM        ║");
        System.out.println("  ║         Built by: Sneh Desai              ║");
        System.out.println("  ║         University of Regina              ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
        System.out.println();
    }


    // ==============================
    // HELPER METHOD: printMenu()
    // Prints the options menu every loop iteration.
    // ==============================
    private static void printMenu() {
        System.out.println("  ┌─────────────────────────────┐");
        System.out.println("  │         MAIN MENU           │");
        System.out.println("  ├─────────────────────────────┤");
        System.out.println("  │  1. 📋 View All Books       │");
        System.out.println("  │  2. ➕ Add a Book           │");
        System.out.println("  │  3. 🗑️  Remove a Book       │");
        System.out.println("  │  4. 🔍 Search Books         │");
        System.out.println("  │  5. 📖 Borrow a Book        │");
        System.out.println("  │  6. 🔄 Return a Book        │");
        System.out.println("  │  0. 👋 Exit                 │");
        System.out.println("  └─────────────────────────────┘");
    }
}
