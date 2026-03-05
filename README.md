# 📚 Library Management System

A web-based library management system that allows users to manage a book collection — add, remove, search, borrow, and return books in real time.

Built by **Sneh Desai** — Computer Science Student & SaskTel Employee, University of Regina

---

## 🌐 Live Demo

👉 [Click here to view the live project](https://snehdesai04-ai.github.io/Library-Management-System/library_index.html)

---

## 📸 Features

- ➕ Add new books with title, author, and genre
- 🗑️ Remove books from the collection
- 🔍 Search books by title or author
- 📖 Borrow available books
- 🔄 Return borrowed books
- 🏷️ Filter by All / Available / Borrowed
- 📊 Real-time summary cards (Total, Available, Borrowed)
- 📱 Responsive design — works on desktop and mobile

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| HTML | Page structure and layout |
| CSS | Advanced dark theme styling and responsive design |
| JavaScript | Logic, data handling, and DOM updates |
| SQL | Database schema for backend integration |
| Java | Console-based version using Object-Oriented Programming |

---

## 📁 Project Structure

```
library-management-system/
│
├── 🌐 WEB VERSION
│   ├── library_index.html   → HTML structure (the skeleton)
│   ├── library_style.css    → CSS dark theme styling (the design)
│   ├── library_script.js    → JavaScript logic (the brain)
│   └── library_schema.sql   → SQL database schema (the data layer)
│
└── ☕ JAVA VERSION
    ├── Main.java             → Entry point, interactive menu
    ├── Library.java          → Core logic (add, remove, search, borrow, return)
    └── Book.java             → Book class blueprint (OOP)
```

---

## 🗄️ SQL Schema Highlights

The `library_schema.sql` file includes:
- `CREATE TABLE books` — defines the books table
- `CREATE TABLE borrowers` — tracks who borrowed which book
- `FOREIGN KEY` — relationship between books and borrowers
- `INSERT` — adds sample books
- `SELECT / WHERE / LIKE` — search and filter queries
- `UPDATE` — borrow and return operations
- `DELETE` — remove books
- `GROUP BY` — genre summary reports

---

## ☕ Java Version Highlights

The Java console version demonstrates **Object-Oriented Programming (OOP)**:
- `Book.java` — Blueprint class with fields, constructor, getters/setters
- `Library.java` — Business logic using `ArrayList<Book>`
- `Main.java` — Interactive menu using `Scanner` and `switch` statements

---

## 🚀 How to Run Locally

**Web Version:**
1. Clone or download this repository
2. Open the folder in **VS Code**
3. Right-click `library_index.html` → **Open with Live Server**
4. The app opens in your browser automatically

**Java Version:**
1. Open terminal in the project folder
2. Compile: `javac *.java`
3. Run: `java Main`
4. Use the menu by typing numbers (1-6)

---

## 👨‍💻 About the Developer

**Sneh Desai**
- 📍 Regina, SK, Canada
- 🎓 Diploma in Computer Science — University of Regina (Expected April 2026)
- 💼 Service Representative — SaskTel
- 📧 snehdesai04@icloud.com
- 🐙 [GitHub](https://github.com/snehdesai04-ai)
