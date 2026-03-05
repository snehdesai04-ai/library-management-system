/**
 * LIBRARY MANAGEMENT SYSTEM
 * Built by: Sneh Desai
 * -----------------------------------------------
 * FILE 2 of 4: Library.java
 *
 * This is the BRAIN of the system.
 * It contains all the logic for managing books:
 *   - addBook()      → adds a new book
 *   - removeBook()   → removes a book by ID
 *   - searchBook()   → searches by title or author
 *   - borrowBook()   → marks a book as borrowed
 *   - returnBook()   → marks a book as returned
 *   - displayAll()   → shows all books
 *
 * It uses an ArrayList to store books in memory.
 * In a real app, this would connect to the SQL database (schema.sql).
 */

// ==============================
// IMPORTS
// These are Java built-in tools we need.
// Like importing a library in Python.
// ==============================
import java.util.ArrayList;   // ArrayList = a resizable list (like our transactions array in JS)
import java.util.List;        // List = the interface ArrayList is based on

public class Library {

    // ==============================
    // FIELDS
    // ==============================

    // ArrayList stores all our Book objects — like a database table in memory
    // SQL equivalent: SELECT * FROM books  →  this list holds all rows
    private ArrayList<Book> books;

    // Tracks the next ID to assign — like SQL AUTO_INCREMENT
    private int nextId;

    // The library's name
    private String libraryName;


    // ==============================
    // CONSTRUCTOR
    // Sets up the library with a name and some sample books
    // ==============================
    public Library(String libraryName) {
        this.libraryName = libraryName;
        this.books       = new ArrayList<>();
        this.nextId      = 1;

        // Add sample books so the library isn't empty on first run
        // SQL equivalent: INSERT INTO books (title, author, genre) VALUES (...)
        addBook("The Pragmatic Programmer", "David Thomas",    "Technology");
        addBook("Clean Code",               "Robert C. Martin","Technology");
        addBook("Harry Potter",             "J.K. Rowling",    "Fantasy");
        addBook("The Great Gatsby",         "F. Scott Fitzgerald", "Classic");
        addBook("Sapiens",                  "Yuval Noah Harari","History");
        addBook("Atomic Habits",            "James Clear",     "Self-Help");
    }


    // ==============================
    // ADD A BOOK
    // Creates a new Book object and adds it to the list.
    // SQL equivalent: INSERT INTO books (title, author, genre) VALUES (?, ?, ?)
    // ==============================
    public void addBook(String title, String author, String genre) {

        // Validate — make sure nothing is empty
        if (title.trim().isEmpty() || author.trim().isEmpty() || genre.trim().isEmpty()) {
            System.out.println("  ⚠️  All fields (title, author, genre) are required!");
            return;
        }

        // Create a new Book using the Book blueprint (Book.java)
        Book newBook = new Book(nextId, title, author, genre);

        // Add it to our list
        books.add(newBook);

        System.out.println("  ✅ Book added successfully!");
        System.out.println("     → " + newBook);

        // Increment the ID counter for the next book
        nextId++;
    }


    // ==============================
    // REMOVE A BOOK
    // Finds a book by ID and removes it from the list.
    // SQL equivalent: DELETE FROM books WHERE id = ?
    // ==============================
    public void removeBook(int id) {

        // Loop through all books to find the one with matching ID
        // SQL equivalent: WHERE id = ?
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);

            if (book.getId() == id) {
                // Check if currently borrowed — can't remove a borrowed book
                if (book.isBorrowed()) {
                    System.out.println("  ⚠️  Cannot remove \"" + book.getTitle() + "\" — it is currently borrowed!");
                    return;
                }

                // Remove from list
                books.remove(i);
                System.out.println("  ✅ Removed: \"" + book.getTitle() + "\"");
                return;
            }
        }

        // If we get here, no book matched the ID
        System.out.println("  ❌ No book found with ID: " + id);
    }


    // ==============================
    // SEARCH FOR A BOOK
    // Searches title AND author for the keyword.
    // SQL equivalent: SELECT * FROM books
    //                 WHERE title LIKE '%keyword%'
    //                 OR author LIKE '%keyword%'
    // ==============================
    public void searchBook(String keyword) {

        // Convert to lowercase for case-insensitive search
        String kw = keyword.toLowerCase().trim();

        // This list will hold matching results
        List<Book> results = new ArrayList<>();

        // Loop through every book and check if keyword matches
        for (Book book : books) {
            boolean titleMatch  = book.getTitle().toLowerCase().contains(kw);
            boolean authorMatch = book.getAuthor().toLowerCase().contains(kw);

            if (titleMatch || authorMatch) {
                results.add(book);
            }
        }

        // Display results
        if (results.isEmpty()) {
            System.out.println("  ❌ No books found matching: \"" + keyword + "\"");
        } else {
            System.out.println("  🔍 Found " + results.size() + " result(s) for \"" + keyword + "\":");
            System.out.println("  " + "-".repeat(85));
            for (Book book : results) {
                System.out.println("  " + book);
            }
            System.out.println("  " + "-".repeat(85));
        }
    }


    // ==============================
    // BORROW A BOOK
    // Marks a book as borrowed if it's available.
    // SQL equivalent: UPDATE books SET is_borrowed = true WHERE id = ?
    // ==============================
    public void borrowBook(int id) {

        Book book = findById(id);  // helper method below

        if (book == null) {
            System.out.println("  ❌ No book found with ID: " + id);
            return;
        }

        if (book.isBorrowed()) {
            System.out.println("  ⚠️  \"" + book.getTitle() + "\" is already borrowed. Please check back later.");
            return;
        }

        // Mark as borrowed using setter from Book.java
        book.setBorrowed(true);
        System.out.println("  ✅ You have successfully borrowed: \"" + book.getTitle() + "\"");
        System.out.println("     Please return it when you're done!");
    }


    // ==============================
    // RETURN A BOOK
    // Marks a book as available again.
    // SQL equivalent: UPDATE books SET is_borrowed = false WHERE id = ?
    // ==============================
    public void returnBook(int id) {

        Book book = findById(id);

        if (book == null) {
            System.out.println("  ❌ No book found with ID: " + id);
            return;
        }

        if (!book.isBorrowed()) {
            System.out.println("  ⚠️  \"" + book.getTitle() + "\" is already in the library. It wasn't borrowed.");
            return;
        }

        // Mark as available
        book.setBorrowed(false);
        System.out.println("  ✅ Thank you! \"" + book.getTitle() + "\" has been returned successfully.");
    }


    // ==============================
    // DISPLAY ALL BOOKS
    // Shows every book in the library.
    // SQL equivalent: SELECT * FROM books ORDER BY id ASC
    // ==============================
    public void displayAll() {

        if (books.isEmpty()) {
            System.out.println("  📭 The library has no books yet. Add some!");
            return;
        }

        // Count available vs borrowed
        long available = books.stream().filter(b -> !b.isBorrowed()).count();
        long borrowed  = books.stream().filter(b ->  b.isBorrowed()).count();

        System.out.println("  " + "=".repeat(85));
        System.out.println("  📚 " + libraryName + " — All Books");
        System.out.println("  Total: " + books.size() + "  |  ✅ Available: " + available + "  |  ❌ Borrowed: " + borrowed);
        System.out.println("  " + "=".repeat(85));

        for (Book book : books) {
            System.out.println("  " + book);
        }

        System.out.println("  " + "=".repeat(85));
    }


    // ==============================
    // DISPLAY ONLY AVAILABLE BOOKS
    // SQL equivalent: SELECT * FROM books WHERE is_borrowed = false
    // ==============================
    public void displayAvailable() {
        System.out.println("  " + "-".repeat(85));
        System.out.println("  ✅ Available Books:");
        System.out.println("  " + "-".repeat(85));

        boolean found = false;
        for (Book book : books) {
            if (!book.isBorrowed()) {
                System.out.println("  " + book);
                found = true;
            }
        }

        if (!found) System.out.println("  No books currently available.");
        System.out.println("  " + "-".repeat(85));
    }


    // ==============================
    // HELPER METHOD: findById()
    // Finds and returns a Book by its ID.
    // Used internally by borrowBook() and returnBook().
    // SQL equivalent: SELECT * FROM books WHERE id = ? LIMIT 1
    // "private" because it's only used inside this class.
    // ==============================
    private Book findById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;  // found it — return immediately
            }
        }
        return null;  // not found
    }
}
