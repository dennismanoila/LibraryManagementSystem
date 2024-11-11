import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {

    public static List<Book> loadBooksFromFile(String filename) {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    int bookId = Integer.parseInt(parts[2].trim());
                    books.add(new Book(title, author, bookId));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Book file not found. Please ensure " + filename + " exists.");
        } catch (IOException e) {
            System.out.println("Error reading from book file.");
        }
        return books;
    }

    public static List<LibrarySubscriber> loadSubscribersFromFile(String filename) {
        List<LibrarySubscriber> subscribers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String firstName = parts[0].trim();
                    String lastName = parts[1].trim();
                    int borrowedBooksCount = Integer.parseInt(parts[2].trim());
                    LibrarySubscriber subscriber = new LibrarySubscriber(new Library("City Library"), firstName, lastName);
                    for (int i = 0; i < borrowedBooksCount; i++) {
                        subscriber.addBook(new Book("Sample Title", "Sample Author", 0));
                    }
                    subscribers.add(subscriber);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Subscriber file not found. Please ensure " + filename + " exists.");
        } catch (IOException e) {
            System.out.println("Error reading from subscriber file.");
        }
        return subscribers;
    }

    public static void saveBooksToFile(List<Book> books, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getBookId());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books to file.");
        }
    }

    public static void saveSubscribersToFile(List<LibrarySubscriber> subscribers, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (LibrarySubscriber subscriber : subscribers) {
                writer.write(subscriber.getFirstName() + "," + subscriber.getLastName() + "," + subscriber.getBorrowedBookCount());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving subscribers to file.");
        }
    }

    public static void clearFiles(String bookFilename, String subscriberFilename) {
        try {
            new PrintWriter(bookFilename).close();
            new PrintWriter(subscriberFilename).close();
            System.out.println("Files cleared. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error clearing files.");
        }
    }
}
