public class LibraryEmployee extends LibraryMember implements Displayable {

    public LibraryEmployee(Library library, String firstName, String lastName) {
        super(library, firstName, lastName);
    }

    @Override
    public void displayInfo() {
        System.out.println("Library Employee Name: " + getFullName() + " in " + getLibrary().getLibraryName());
    }

    public void manageLibrary() {
        System.out.println("Managing library operations...");
    }
}
