import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryManager libraryManager = new LibraryManager();
        Scanner scanner = new Scanner(System.in);

        String bookFile = "books.txt";
        String subscriberFile = "subscribers.txt";

        String choice;
        while (true) {
            System.out.println("Do you want to load existing data or start fresh?");
            System.out.print("Enter 'load' to load data or 'new' to start fresh: ");
            choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("load")) {
                libraryManager.loadBooksFromFile(bookFile);
                libraryManager.loadSubscribersFromFile(subscriberFile);
                System.out.println("Data loaded successfully.");
                break;
            } else if (choice.equals("new")) {
                libraryManager.clearFiles(bookFile, subscriberFile);
                System.out.println("Starting with a fresh library.");
                break;
            } else {
                System.out.println("Invalid input. Please enter 'load' or 'new'.");
            }
        }

        while (true) {
            displayMainMenu();

            System.out.print("Choose an option: ");
            int option;
            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            clearScreen();

            switch (option) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    int bookId;
                    while (true) {
                        System.out.print("Enter book ID (positive integer): ");
                        try {
                            bookId = scanner.nextInt();
                            if (bookId <= 0) {
                                System.out.println("Book ID must be a positive integer.");
                                continue;
                            }
                            scanner.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a positive integer.");
                            scanner.nextLine();
                        }
                    }
                    libraryManager.addBook(title, author, bookId);
                    libraryManager.saveBooksToFile(bookFile);
                    break;
                case 2:
                    String firstName, lastName;
                    do {
                        System.out.print("Enter subscriber first name: ");
                        firstName = scanner.nextLine().trim();
                        if (firstName.isEmpty()) {
                            System.out.println("First name cannot be empty.");
                        }
                    } while (firstName.isEmpty());

                    do {
                        System.out.print("Enter subscriber last name: ");
                        lastName = scanner.nextLine().trim();
                        if (lastName.isEmpty()) {
                            System.out.println("Last name cannot be empty.");
                        }
                    } while (lastName.isEmpty());

                    libraryManager.addSubscriber(firstName, lastName);
                    libraryManager.saveSubscribersToFile(subscriberFile);
                    break;
                case 3:
                    libraryManager.userBorrowsBook(scanner);
                    libraryManager.saveSubscribersToFile(subscriberFile);
                    break;
                case 4:
                    libraryManager.displayBooks();
                    break;
                case 5:
                    libraryManager.displaySubscribers();
                    break;
                case 6:
                    libraryManager.sortBooksByTitle();
                    break;
                case 7:
                    libraryManager.sortSubscribersByName();
                    break;
                case 8:
                    libraryManager.groupSubscribersByBorrowedBooks();
                    break;
                case 9:
                    System.out.println("Exiting. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }

            promptReturnToMenu();
            clearScreen();
        }
    }

    public static void displayMainMenu() {
        System.out.println("Library Management System");
        System.out.println("1. Add Book");
        System.out.println("2. Add Subscriber");
        System.out.println("3. User Borrows Book");
        System.out.println("4. Display Books");
        System.out.println("5. Display Subscribers");
        System.out.println("6. Sort Books by Title");
        System.out.println("7. Sort Subscribers by Name");
        System.out.println("8. Group Subscribers by Borrowed Books");
        System.out.println("9. Exit");
    }

    public static void promptReturnToMenu() {
        System.out.println("\nPress any key to go back to the main menu...");
        try {
            System.in.read();
        } catch (Exception e) {
            System.out.println("Error reading input.");
        }
    }

    public static void clearScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}
