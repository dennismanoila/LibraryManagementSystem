import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LibraryManager {
    private List<Book> books;
    private List<LibrarySubscriber> subscribers;

    public LibraryManager() {
        books = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<LibrarySubscriber> getSubscribers() {
        return subscribers;
    }

    public void loadBooksFromFile(String filename) {
        books = DataStorage.loadBooksFromFile(filename);
    }

    public void loadSubscribersFromFile(String filename) {
        subscribers = DataStorage.loadSubscribersFromFile(filename);
    }

    public void saveBooksToFile(String filename) {
        DataStorage.saveBooksToFile(books, filename);
    }

    public void saveSubscribersToFile(String filename) {
        DataStorage.saveSubscribersToFile(subscribers, filename);
    }

    public void clearFiles(String bookFilename, String subscriberFilename) {
        DataStorage.clearFiles(bookFilename, subscriberFilename);
    }

    public void addBook(String title, String author, int bookId) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Error: Book with this title already exists.");
                return;
            }
        }
        books.add(new Book(title, author, bookId));
        System.out.println("Book added.");
    }

    public void addSubscriber(String firstName, String lastName) {
        for (LibrarySubscriber subscriber : subscribers) {
            if (subscriber.getFirstName().equalsIgnoreCase(firstName) &&
                    subscriber.getLastName().equalsIgnoreCase(lastName)) {
                System.out.println("Error: Subscriber with this name already exists.");
                return;
            }
        }
        subscribers.add(new LibrarySubscriber(new Library("City Library"), firstName, lastName));
        System.out.println("Subscriber added.");
    }

    public void displayBooks() {
        System.out.println("Books in Library:");
        for (Book book : books) {
            System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Book ID: " + book.getBookId());
        }
    }

    public void displaySubscribers() {
        System.out.println("Library Subscribers:");
        for (LibrarySubscriber subscriber : subscribers) {
            System.out.println("Name: " + subscriber.getFullName());
        }
    }

    public void sortBooksByTitle() {
        Collections.sort(books);
        displayBooks();
    }

    public void sortSubscribersByName() {
        Collections.sort(subscribers);
        displaySubscribers();
    }

    public void groupSubscribersByBorrowedBooks() {
        subscribers.sort(Comparator.comparingInt(LibrarySubscriber::getBorrowedBookCount).reversed());
        System.out.println("Subscribers grouped and sorted by number of borrowed books:");
        for (LibrarySubscriber subscriber : subscribers) {
            System.out.println("Subscriber Name: " + subscriber.getFullName() + ", Borrowed Books: " + subscriber.getBorrowedBookCount());
        }
    }

    public void userBorrowsBook(Scanner scanner) {
        if (subscribers.isEmpty()) {
            System.out.println("No subscribers available to borrow a book.");
            return;
        }

        if (books.isEmpty()) {
            System.out.println("No books available to borrow.");
            return;
        }

        try {
            System.out.println("Select a subscriber:");
            for (int i = 0; i < subscribers.size(); i++) {
                System.out.println((i + 1) + ". " + subscribers.get(i).getFullName());
            }

            System.out.print("Enter the number of the subscriber: ");
            int subscriberIndex = scanner.nextInt() - 1;
            scanner.nextLine();

            if (subscriberIndex < 0 || subscriberIndex >= subscribers.size()) {
                System.out.println("Invalid subscriber selection.");
                return;
            }

            LibrarySubscriber selectedSubscriber = subscribers.get(subscriberIndex);

            System.out.println("Select a book:");
            for (int i = 0; i < books.size(); i++) {
                System.out.println((i + 1) + ". " + books.get(i).getTitle() + " by " + books.get(i).getAuthor());
            }

            System.out.print("Enter the number of the book: ");
            int bookIndex = scanner.nextInt() - 1;
            scanner.nextLine();

            if (bookIndex < 0 || bookIndex >= books.size()) {
                System.out.println("Invalid book selection.");
                return;
            }

            Book selectedBook = books.get(bookIndex);
            if (selectedSubscriber.getBorrowedBooks().contains(selectedBook)) {
                throw new BookNotAvailableException("This book has already been borrowed by the subscriber.");
            }

            selectedSubscriber.addBook(selectedBook);
            System.out.println(selectedSubscriber.getFullName() + " has borrowed \"" + selectedBook.getTitle() + "\" by " + selectedBook.getAuthor());

        } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please enter a valid number.");
            scanner.nextLine();
        } catch (BookNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
