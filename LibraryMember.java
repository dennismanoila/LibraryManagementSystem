public class LibraryMember {
    private Library library;
    private String firstName;
    private String lastName;

    public LibraryMember(Library library, String firstName, String lastName) {
        this.library = library;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Library getLibrary() {
        return library;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void displayInfo() {
        System.out.println("Library Member Name: " + getFullName() + " in " + library.getLibraryName());
    }
}
