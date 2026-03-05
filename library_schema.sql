-- ================================================
-- LIBRARY MANAGEMENT SYSTEM
-- Built by: Sneh Desai
-- ------------------------------------------------
-- FILE 4 of 4: schema.sql
-- This is how the data would be stored in a real
-- SQL database connected to this web application.
-- ================================================

CREATE DATABASE IF NOT EXISTS library_system;
USE library_system;

-- BOOKS TABLE
-- Each column matches a field in script.js book objects
CREATE TABLE books (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(100)  NOT NULL,
    author      VARCHAR(100)  NOT NULL,
    genre       VARCHAR(50)   NOT NULL,
    is_borrowed BOOLEAN       DEFAULT FALSE,
    added_on    TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
);

-- BORROWERS TABLE
-- Tracks who borrowed which book and when
CREATE TABLE borrowers (
    id            INT PRIMARY KEY AUTO_INCREMENT,
    book_id       INT          NOT NULL,
    borrower_name VARCHAR(100) NOT NULL,
    borrow_date   DATE         NOT NULL,
    return_date   DATE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

-- SAMPLE DATA (matches sampleBooks in script.js)
INSERT INTO books (title, author, genre) VALUES
('The Pragmatic Programmer', 'David Thomas',        'Technology'),
('Clean Code',               'Robert C. Martin',    'Technology'),
('Harry Potter',             'J.K. Rowling',        'Fantasy'),
('The Great Gatsby',         'F. Scott Fitzgerald', 'Classic'),
('Sapiens',                  'Yuval Noah Harari',   'History'),
('Atomic Habits',            'James Clear',         'Self-Help');

-- COMMON QUERIES (matching script.js functions)

-- addBook()
-- INSERT INTO books (title, author, genre) VALUES (?, ?, ?);

-- removeBook()
-- DELETE FROM books WHERE id = ? AND is_borrowed = FALSE;

-- searchBooks()
-- SELECT * FROM books WHERE title LIKE '%keyword%' OR author LIKE '%keyword%';

-- borrowBook()
-- UPDATE books SET is_borrowed = TRUE WHERE id = ?;

-- returnBook()
-- UPDATE books SET is_borrowed = FALSE WHERE id = ?;

-- updateSummary()
SELECT
  COUNT(*)                                              AS total,
  SUM(CASE WHEN is_borrowed = FALSE THEN 1 ELSE 0 END) AS available,
  SUM(CASE WHEN is_borrowed = TRUE  THEN 1 ELSE 0 END) AS borrowed
FROM books;

-- Books by genre
SELECT genre, COUNT(*) AS total FROM books GROUP BY genre ORDER BY total DESC;
