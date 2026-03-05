/**
 * LIBRARY MANAGEMENT SYSTEM
 * Built by: Sneh Desai
 * -----------------------------------------------
 * FILE 1 of 4: Book.java
 *
 * This file defines what a BOOK is.
 * In Java, we use a "class" as a blueprint/template.
 *
 * Think of it like a form you fill out for every book:
 *   - What is the ID?
 *   - What is the title?
 *   - Who is the author?
 *   - What genre is it?
 *   - Is it currently borrowed or available?
 *
 * Every book in the library will be created using this blueprint.
 */

public class Book {

    // ==============================
    // FIELDS (the data each book holds)
    // These are like columns in a SQL table.
    // "private" means only this class can access them directly —
    // other classes must use getters/setters below.
    // ==============================
    private int     id;          // unique number for each book (like SQL PRIMARY KEY)
    private String  title;       // title of the book
    private String  author;      // author's name
    private String  genre;       // e.g. "Fiction", "Science", "History"
    private boolean isBorrowed;  // true = borrowed, false = available


    // ==============================
    // CONSTRUCTOR
    // This runs automatically when you create a new Book.
    // Like filling in a form for a new book entry.
    //
    // Example usage in Library.java:
    //   Book b = new Book(1, "Harry Potter", "J.K. Rowling", "Fantasy");
    // ==============================
    public Book(int id, String title, String author, String genre) {
        this.id         = id;
        this.title      = title;
        this.author     = author;
        this.genre      = genre;
        this.isBorrowed = false;  // every new book starts as available
    }


    // ==============================
    // GETTERS — used to READ the book's data
    // Like SQL SELECT — reading a column value.
    // "public" means other classes can call these.
    // ==============================
    public int     getId()      { return id; }
    public String  getTitle()   { return title; }
    public String  getAuthor()  { return author; }
    public String  getGenre()   { return genre; }
    public boolean isBorrowed() { return isBorrowed; }


    // ==============================
    // SETTER — used to UPDATE borrowed status
    // Like SQL: UPDATE books SET is_borrowed = true WHERE id = ?
    // ==============================
    public void setBorrowed(boolean borrowed) {
        this.isBorrowed = borrowed;
    }


    // ==============================
    // toString() METHOD
    // Controls how a Book looks when printed.
    // Java calls this automatically when you use System.out.println(book).
    // ==============================
    @Override
    public String toString() {
        String status = isBorrowed ? "❌ Borrowed  " : "✅ Available";
        return String.format(
            "[ID: %2d] %-30s | Author: %-20s | Genre: %-12s | %s",
            id, title, author, genre, status
        );
    }
}
