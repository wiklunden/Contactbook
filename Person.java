package se.miun.projekt;

/**
 * Class which is called when implementing a new person
 * Uses all methods to form a specific person with a unique signature along an address
 */
public class Person {

    private String firstName;
    private String lastName;
    private String signature;
    private int height;
    private Address address;

    public Person() {
        this("unknown", "unknown", -1, new Address());
    }

    public Person(String firstName, String lastName, int height, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.address = address;

        this.signature = generateSignature(firstName, lastName);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSignature() {
        return signature;
    }

    public int getHeight() {
        return height;
    }

    public Address getFullAddress() {
        return address;
    }

    public String generateSignature(String firstName, String lastName) {

        firstName = checkName(firstName);
        lastName = checkName(lastName);

        int firstNameChars = checkChar(firstName);
        int lastNameChars = checkChar(lastName);

        return firstName.toLowerCase().substring(0, firstNameChars) +
                lastName.toLowerCase().substring(0, lastNameChars);
    }

    public String checkName(String name) {
        if(name.contains(" ")) {
            String[] split = name.split(" ");

            name = split[0];
        }

        int nameChars = checkChar(name);

        if(nameChars == 2) name = name.concat("x");
        else if(nameChars == 1) name = name.concat("xx");

        return name;
    }

    public int checkChar(String name) {
        return Math.min(name.length(), 3);
    }

    public String heightToString() {
        double heightInMeters = (double) getHeight() / 100; // Converts centimeters to meters

        // Limits height to two decimals
        return String.format("%.2f", heightInMeters).replace(",", ".");
    }

    @Override
    public String toString() {
        String fullName = getFirstName() + " " + getLastName();

        // Aligns all length indentations during printout
        return String.format("%-12s %-26s %1s", "  " + getSignature(), fullName, " " + heightToString());
    }
}
