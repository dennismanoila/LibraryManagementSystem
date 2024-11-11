import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LibrarySubscriber extends LibraryMember implements Serializable, Borrowable, Comparable<LibrarySubscriber> {
    private List<Book> borrowedBooks;

    public LibrarySubscriber(Library library, String firstName, String lastName) {
        super(library, firstName, lastName);
        this.borrowedBooks = new ArrayList<>();
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public void borrowItem() {
        System.out.println("Subscriber " + getFullName() + " is borrowing an item.");
    }

    public void addBook(Book book) {
        borrowedBooks.add(book);
    }

    public void displayBorrowedBooks() {
        System.out.println("Borrowed Books by " + getFullName() + ":");
        for (Book book : borrowedBooks) {
            System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Book ID: " + book.getBookId());
        }
    }

    public int getBorrowedBookCount() {
        return borrowedBooks.size();
    }

    @Override
    public int compareTo(LibrarySubscriber other) {
        return this.getFullName().compareTo(other.getFullName());
    }
}
