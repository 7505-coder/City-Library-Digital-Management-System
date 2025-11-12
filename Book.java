// Book.java
import java.io.Serializable;

public class Book implements Serializable, Comparable<Book> {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private boolean isIssued;

    public Book(int bookId, String title, String author, String category) {
        this.bookId = bookId;
        this.title = title.trim();
        this.author = author.trim();
        this.category = category.trim();
        this.isIssued = false;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public boolean isIssued() { return isIssued; }

    public void markAsIssued() { isIssued = true; }
    public void markAsReturned() { isIssued = false; }

    public String toDataString() {
        // CSV line: id|title|author|category|isIssued
        return bookId + "|" + escape(title) + "|" + escape(author) + "|" + escape(category) + "|" + isIssued;
    }

    public static Book fromDataString(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 5) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            String title = unescape(parts[1]);
            String author = unescape(parts[2]);
            String category = unescape(parts[3]);
            boolean issued = Boolean.parseBoolean(parts[4]);
            Book b = new Book(id, title, author, category);
            if (issued) b.markAsIssued();
            return b;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String escape(String s) {
        return s.replace("|", "\\|");
    }
    private static String unescape(String s) {
        return s.replace("\\|", "|");
    }

    @Override
    public String toString() {
        return "ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Category: " + category + ", Issued: " + isIssued;
    }

    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }
}
