/*
  LIBRARY MANAGEMENT SYSTEM
  Built by: Sneh Desai
  -----------------------------------------------
  FILE 3 of 4: script.js
  This file contains ONLY the logic (JavaScript).

  This is the web version of the Java library system.
  The same logic that was in Library.java is here,
  translated into JavaScript for the browser.

  Functions:
  - addBook()       → adds a new book (like Library.addBook())
  - removeBook()    → removes a book (like Library.removeBook())
  - searchBooks()   → searches books (like Library.searchBook())
  - borrowBook()    → borrows a book (like Library.borrowBook())
  - returnBook()    → returns a book (like Library.returnBook())
  - renderBooks()   → displays books on screen
  - updateSummary() → updates the 3 cards at the top
*/


// ==============================
// DATA — works like our ArrayList<Book> in Java
// Each object = one book (one row in the SQL table)
// ==============================
let books   = [];
let nextId  = 1;
let currentFilter = 'all';

// Tracks what action the modal is confirming
// e.g. { type: 'borrow', id: 3 }
let pendingAction = null;


// ==============================
// ADD A BOOK
// SQL: INSERT INTO books (title, author, genre) VALUES (?, ?, ?)
// Java equivalent: library.addBook(title, author, genre)
// ==============================
function addBook() {
  const title  = document.getElementById('title').value.trim();
  const author = document.getElementById('author').value.trim();
  const genre  = document.getElementById('genre').value;

  // Validate
  if (!title)  { showFlash('⚠️ Please enter a book title!',  '#e67e22'); return; }
  if (!author) { showFlash('⚠️ Please enter an author name!', '#e67e22'); return; }

  // Create book object — like new Book(id, title, author, genre) in Java
  const book = {
    id:         nextId++,
    title:      title,
    author:     author,
    genre:      genre,
    isBorrowed: false   // every new book starts as available
  };

  books.push(book);

  // Clear the form
  document.getElementById('title').value  = '';
  document.getElementById('author').value = '';

  renderBooks();
  updateSummary();
  showFlash('✅ "' + title + '" added successfully!', '#27ae60');
}


// ==============================
// REMOVE A BOOK
// SQL: DELETE FROM books WHERE id = ?
// Java equivalent: library.removeBook(id)
// ==============================
function removeBook(id) {
  const book = findById(id);
  if (!book) return;

  if (book.isBorrowed) {
    showFlash('⚠️ Cannot remove "' + book.title + '" — it is currently borrowed!', '#e74c3c');
    return;
  }

  // Show confirmation modal before deleting
  pendingAction = { type: 'delete', id: id };
  showModal(
    '🗑️ Remove Book',
    'Are you sure you want to remove "' + book.title + '" by ' + book.author + '?'
  );
}


// ==============================
// SEARCH BOOKS
// SQL: SELECT * FROM books WHERE title LIKE '%keyword%' OR author LIKE '%keyword%'
// Java equivalent: library.searchBook(keyword)
// ==============================
function searchBooks() {
  const keyword = document.getElementById('search').value.trim().toLowerCase();

  if (!keyword) {
    showFlash('⚠️ Please enter a search term!', '#e67e22');
    return;
  }

  // Filter books array — like SQL WHERE clause
  const results = books.filter(b =>
    b.title.toLowerCase().includes(keyword) ||
    b.author.toLowerCase().includes(keyword)
  );

  renderBooks(results);

  if (results.length === 0) {
    showFlash('❌ No books found for "' + keyword + '"', '#e74c3c');
  } else {
    showFlash('🔍 Found ' + results.length + ' result(s) for "' + keyword + '"', '#2d6a9f');
  }
}


// ==============================
// CLEAR SEARCH — show all books again
// ==============================
function clearSearch() {
  document.getElementById('search').value = '';
  renderBooks();
  showFlash('📋 Showing all books', '#2d6a9f');
}


// ==============================
// BORROW A BOOK
// SQL: UPDATE books SET is_borrowed = TRUE WHERE id = ?
// Java equivalent: library.borrowBook(id)
// ==============================
function borrowBook(id) {
  const book = findById(id);
  if (!book) return;

  if (book.isBorrowed) {
    showFlash('⚠️ "' + book.title + '" is already borrowed!', '#e74c3c');
    return;
  }

  // Show confirmation modal
  pendingAction = { type: 'borrow', id: id };
  showModal(
    '📖 Borrow Book',
    'Would you like to borrow "' + book.title + '" by ' + book.author + '?'
  );
}


// ==============================
// RETURN A BOOK
// SQL: UPDATE books SET is_borrowed = FALSE WHERE id = ?
// Java equivalent: library.returnBook(id)
// ==============================
function returnBook(id) {
  const book = findById(id);
  if (!book) return;

  if (!book.isBorrowed) {
    showFlash('⚠️ "' + book.title + '" is already in the library!', '#e67e22');
    return;
  }

  // Show confirmation modal
  pendingAction = { type: 'return', id: id };
  showModal(
    '🔄 Return Book',
    'Return "' + book.title + '" to the library?'
  );
}


// ==============================
// CONFIRM ACTION (from modal)
// Runs when user clicks "Confirm" in the modal
// ==============================
function confirmAction() {
  if (!pendingAction) return;

  const book = findById(pendingAction.id);

  if (pendingAction.type === 'borrow') {
    book.isBorrowed = true;
    showFlash('✅ You borrowed "' + book.title + '"!', '#27ae60');

  } else if (pendingAction.type === 'return') {
    book.isBorrowed = false;
    showFlash('✅ "' + book.title + '" returned successfully!', '#27ae60');

  } else if (pendingAction.type === 'delete') {
    // Remove from array — like SQL DELETE
    books = books.filter(b => b.id !== pendingAction.id);
    showFlash('🗑️ Book removed successfully!', '#e74c3c');
  }

  pendingAction = null;
  closeModal();
  renderBooks();
  updateSummary();
}


// ==============================
// RENDER BOOKS TO THE SCREEN
// SQL: SELECT * FROM books (with optional WHERE clause for filter)
// ==============================
function renderBooks(booksToShow) {

  // If no specific list passed in, use full list with current filter
  if (!booksToShow) {
    if (currentFilter === 'available') {
      booksToShow = books.filter(b => !b.isBorrowed);
    } else if (currentFilter === 'borrowed') {
      booksToShow = books.filter(b => b.isBorrowed);
    } else {
      booksToShow = books;
    }
  }

  const list = document.getElementById('book-list');

  if (booksToShow.length === 0) {
    list.innerHTML = `
      <div class="empty-state">
        <div>📭</div>
        No books found.
      </div>`;
    return;
  }

  // Build HTML for each book
  list.innerHTML = booksToShow.map(b => {
    const statusClass  = b.isBorrowed ? 'borrowed-item'    : 'available-item';
    const badgeClass   = b.isBorrowed ? 'status-borrowed'  : 'status-available';
    const badgeText    = b.isBorrowed ? '❌ Borrowed'       : '✅ Available';

    // Show Borrow button if available, Return button if borrowed
    const actionBtn = b.isBorrowed
      ? `<button class="return-btn" onclick="returnBook(${b.id})">Return</button>`
      : `<button class="borrow-btn" onclick="borrowBook(${b.id})">Borrow</button>`;

    return `
      <div class="book-item ${statusClass}">
        <div class="b-info">
          <div class="b-title">${b.title}</div>
          <div class="b-meta">by ${b.author} · ${b.genre} · ID: ${b.id}</div>
        </div>
        <span class="b-status ${badgeClass}">${badgeText}</span>
        <div class="b-actions">
          ${actionBtn}
          <button class="delete-btn" onclick="removeBook(${b.id})">Remove</button>
        </div>
      </div>
    `;
  }).join('');
}


// ==============================
// UPDATE SUMMARY CARDS
// SQL: SELECT COUNT(*), SUM(CASE WHEN...) FROM books
// ==============================
function updateSummary() {
  const total     = books.length;
  const available = books.filter(b => !b.isBorrowed).length;
  const borrowed  = books.filter(b =>  b.isBorrowed).length;

  document.getElementById('total-books').textContent    = total;
  document.getElementById('available-books').textContent = available;
  document.getElementById('borrowed-books').textContent  = borrowed;
}


// ==============================
// FILTER BUTTONS
// ==============================
function setFilter(filter, btn) {
  currentFilter = filter;
  document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
  btn.classList.add('active');
  renderBooks();
}


// ==============================
// MODAL HELPERS
// ==============================
function showModal(title, body) {
  document.getElementById('modal-title').textContent = title;
  document.getElementById('modal-body').textContent  = body;
  document.getElementById('modal-overlay').classList.add('show');
}

function closeModal() {
  document.getElementById('modal-overlay').classList.remove('show');
  pendingAction = null;
}


// ==============================
// FLASH NOTIFICATION
// ==============================
function showFlash(message, color) {
  const f = document.getElementById('flash');
  f.textContent    = message;
  f.style.background = color;
  f.style.display  = 'block';
  setTimeout(() => f.style.display = 'none', 2500);
}


// ==============================
// HELPER: findById()
// SQL: SELECT * FROM books WHERE id = ? LIMIT 1
// ==============================
function findById(id) {
  return books.find(b => b.id === id) || null;
}


// ==============================
// SAMPLE DATA — loaded on page start
// SQL: INSERT INTO books (title, author, genre) VALUES (...)
// ==============================
const sampleBooks = [
  { title: 'The Pragmatic Programmer', author: 'David Thomas',          genre: 'Technology' },
  { title: 'Clean Code',               author: 'Robert C. Martin',      genre: 'Technology' },
  { title: 'Harry Potter',             author: 'J.K. Rowling',          genre: 'Fantasy'    },
  { title: 'The Great Gatsby',         author: 'F. Scott Fitzgerald',   genre: 'Classic'    },
  { title: 'Sapiens',                  author: 'Yuval Noah Harari',     genre: 'History'    },
  { title: 'Atomic Habits',            author: 'James Clear',           genre: 'Self-Help'  },
];

sampleBooks.forEach(b => {
  books.push({ id: nextId++, title: b.title, author: b.author, genre: b.genre, isBorrowed: false });
});

// Mark one as borrowed so it doesn't look empty on first load
books[2].isBorrowed = true;

// Initial render
renderBooks();
updateSummary();
