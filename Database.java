package se.miun.projekt;

import java.io.*;
import java.util.*;

/**
 * Database dealing with person information in a list
 */
public class Database {

    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Person> list = new ArrayList<>();
    private static final Actions action = new Actions();
    private static final String delim = "DELIM";

    private static void enter() {
        System.out.println("Press [Enter] to continue.");
        input.nextLine();
    }

    /**
     * Contains the menu interface
     */
    private static void menu() {
        System.out.println("\n ***** MENU *****");
        System.out.println(" Number of people in list: " + list.size());

        System.out.println("\n\t1.\tAdd Person");
        System.out.println("\t2.\tPrint list");
        System.out.println("\t3.\tSearch for person");
        System.out.println("\t4.\tRemove person");
        System.out.println("\t5.\tSort list by last name");
        System.out.println("\t6.\tSort list by signature");
        System.out.println("\t7.\tSort list by height");
        System.out.println("\t8.\tSort list by random");
        System.out.println("\t9.\tSave list to file");
        System.out.println("\t10.\tRead list from file");
        System.out.println("\t0.\tExit");
        System.out.print("\t> \t");
    }

    /**
     * Prints out menu and asks user for input
     * @throws IOException Exception thrown if choice is invalid
     */
    public static void start() throws IOException {
        menu(); // Calls the menu printout

        int min = 0, max = 10; // Sets variables for the choice's limited range
        int choice = action.verifyChoice(min, max); // Verifies choice input

        // Continues looping while choice is in range
        while(choice >= min && choice <= max) {
            switch (choice) {
                case 1 -> addPerson();
                case 2 -> printList();
                case 3 -> searchPerson();
                case 4 -> removePerson();
                case 5 -> sortListByLastName();
                case 6 -> sortListBySignature();
                case 7 -> sortListByHeight();
                case 8 -> sortListByRandom();
                case 9 -> saveListToFile();
                case 10 -> readListFromFile();
                case 0 -> exit();
            }
            menu();
            choice = action.verifyChoice(min, max);
        }
    }

    /**
     * Starts the program by calling the menu and asking user for a choice
     * @param args Contains argument(s) from command-line as parameter
     * @throws IOException Exception thrown if choice is invalid
     */
    public static void main(String[] args) throws IOException {

        start(); // Calls the starting method and asks user for input

    }

    /**
     * Adds a new person to the list
     * Multiple uses of class 'Actions.java' to verify or assign crucial information to prevent the program from crashing
     */
    public static void addPerson() {
        System.out.print("\n***** ADD NEW PERSON *****");
        Person person = new Person();

        action.createNewPerson(person);
        boolean isReturning = action.verifyPerson(person, list);

        // Goes into statement if person already exists and user wants to cancel input
        if(isReturning) {
            System.out.println("\nReturning to the menu!");
            enter();
            return;
        }

        String address, zipcode, city;
        Address addressObject = new Address();

        System.out.print("\nEnter address\n> ");
        address = action.verifyInput();
        addressObject.setAddress(address);

        System.out.print("\nEnter zipcode\n> ");
        zipcode = action.verifyInput();
        addressObject.setZipcode(zipcode);

        System.out.print("\nEnter city\n> ");
        city = action.verifyInput();
        addressObject.setCity(city);

        person.setAddress(addressObject);
        list.add(person);

        System.out.println("\n" + person.getFirstName() + " " + person.getLastName() + " was added to the list!");
        enter();
    }

    /**
     * Prints the list to the system
     * Limits printout by 20 people per segment
     */
    public static void printList() {
        if(list.isEmpty()) {
            System.out.println("\nList is empty!");
            enter();
        }
        else {
            // Sets the printout limit to 20
            final int limit = 20;
            int listLimit = Math.min(list.size(), limit);

            System.out.println("\n ***** NAME LIST *****");
            System.out.println(" Number of people in list: " + list.size());

            System.out.println("\nNr\t Sign\t\tName\t\t\t\t\t Height [m]");

            // Prints out all people in the list
            for(int p = 0; p < listLimit; p++) {
                System.out.printf("%2d", (p + 1)); // Aligns numbering to the right
                System.out.print("." + list.get(p) + "\n");
            }

            if(list.size() > limit) {
                System.out.println();
                enter();

                int size = list.size();
                int rest = list.size() / limit;
                int restMinimum = 2;
                int personIndex = limit;

                while(size > limit && restMinimum < rest) {
                    for(; personIndex < restMinimum * limit; personIndex++) {
                        System.out.println(" " + (personIndex + 1) + "." + list.get(personIndex));
                    }

                    size -= limit;
                    restMinimum++;

                    System.out.println();
                    enter();
                }

                for(; personIndex < list.size(); personIndex++) {
                    System.out.println(" " + (personIndex + 1) + "." + list.get(personIndex));
                }
            }
            System.out.println();
            enter();
        }
    }

    /**
     * Lets user search for a person's signature
     * If found, prints out person's information to the system
     */
    public static void searchPerson() {
        if(list.isEmpty()) {
            System.out.println("\nList is empty!");
            enter();
        }
        else {
            System.out.println("\n ***** SEARCH *****");
            System.out.print("\nSearch for signature: ");

            String signature = input.nextLine();
            int personFoundCounter = 0;

            for(Person p : list) {
                if(p.getSignature().contains(signature)) {
                    personFoundCounter++;

                    System.out.println("\nPerson found in list!\n");
                    printPerson(p);

                    enter();
                }
            }
            if(personFoundCounter == 0) {
                System.out.println("\nPerson not found in list!");
                enter();
            }
        }
    }

    /**
     * Lets user search for a person's signature
     * If found, asks user to remove person
     */
    public static void removePerson() {
        if(list.isEmpty()) {
            System.out.println("\nList is empty!");
            enter();
        }
        else {
            System.out.println("\n[Remove person]");
            System.out.print("Search for signature: ");

            ArrayList<Person> personToRemove = new ArrayList<>();
            String signature = input.nextLine();
            int personFoundCounter = 0;

            for(Person p : list) {
                if(p.getSignature().contains(signature)) {
                    personFoundCounter++;

                    System.out.println("\nPerson found in list!");
                    printPerson(p);

                    System.out.print("\nWould you like to remove this person? (1/0): ");
                    int choice = input.nextInt();
                    input.nextLine();

                    if(choice == 1) {
                        personToRemove.add(p);
                        System.out.println("\nPerson was successfully removed!");
                        enter();
                    }
                    else {
                        System.out.println("\nPerson was not removed!");
                        enter();
                    }
                }
            }
            if(personFoundCounter == 0) {
                System.out.println("\nPerson not found in list!");
                enter();
            }

            list.removeAll(personToRemove);
        }
    }

    /**
     * Prints information about a person in the list
     * @param p Takes a Person-object as parameter
     */
    public static void printPerson(Person p) {
        String name, signature, height;
        String address, zipcode, city;

        name = p.getFirstName() + " " + p.getLastName();
        signature = "(" + p.getSignature() + ")";
        height = p.heightToString() + " m";

        address = p.getFullAddress().getAddress();
        zipcode = p.getFullAddress().getZipcode();
        city = p.getFullAddress().getCity();

        System.out.println(name + " " + signature + ", " + height);
        System.out.println(address + "\n" + zipcode + ", " + city);
    }

    /**
     * Sorts the list by either last name or first name
     */
    public static void sortListByLastName() {
        if(list.isEmpty()) System.out.println("\nList is empty!");
        else {
            list.sort(new NameComparator());
            System.out.println("\nList sorted by last name!");
        }
        enter();
    }

    /**
     * Compares list by either last name or first name
     */
    public static class NameComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            int result = p1.getLastName().compareTo(p2.getLastName());

            // If the last names are equal, compare first names instead
            if(result == 0) return p1.getFirstName().compareTo(p2.getFirstName());
            else return result;
        }
    }

    /**
     * Sorts the list by signature in alphabetical order
     */
    public static void sortListBySignature() {
        if(list.isEmpty()) System.out.println("\nList is empty!");
        else {
            list.sort(new SignatureComparator());
            System.out.println("\nList sorted by signature!");
        }
        enter();
    }

    /**
     * Compares list by signature in alphabetical order
     */
    public static class SignatureComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            return p1.getSignature().compareTo(p2.getSignature());
        }
    }

    /**
     * Sorts list by height, either in descending or ascending order
     */
    public static void sortListByHeight() {
        if(list.isEmpty()) System.out.println("\nList is empty!");
        else {
            int choice, min = 0, max = 1;
            System.out.print("\nSort by descending (1) or ascending (0)?: ");

            while(true) {
                choice = input.nextInt();
                input.nextLine(); // Clears buffer

                if(choice < min || choice > max) {
                    System.out.print("Invalid input!\nNew number: ");
                    continue;
                }
                break;
            }

            list.sort(new HeightComparator()); // Sorts list by Comparator below

            if(choice == 1) {
                Collections.reverse(list);
                System.out.println("\nList sorted by height in descending order!");
            }
            if(choice == 0) System.out.println("\nList sorted by height in ascending order!");
        }
        enter();
    }

    /**
     * Compares list by height
     */
    public static class HeightComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            return p1.getHeight() - p2.getHeight();
        }
    }

    /**
     * Sorts list by random
     */
    public static void sortListByRandom() {
        if(list.isEmpty()) System.out.println("\nList is empty!");
        else {
            List<Person> randomOrdering = new ArrayList<>(list);

            Collections.shuffle(randomOrdering);
            list.sort(Comparator.comparingInt(randomOrdering::indexOf));

            System.out.println("\nList sorted by random!");
        }
        enter();
    }

    /**
     * Saves encrypted list to new or existing file
     * @throws IOException Throws an exception regarding either naming of file or writing to file
     */
    public static void saveListToFile() throws IOException {
        if(list.isEmpty()) System.out.println("\nList is empty!");
        else {
            String fileName = "";
            boolean done = false;

            while(!done) {
                do {
                    System.out.print("Enter file name (with file type): ");
                    fileName = input.nextLine();

                    if(fileName.contains(".txt")) done = true;
                    else System.out.println("\nIncorrect file format!");

                } while(!fileName.contains(".txt"));
            }

            FileWriter out = new FileWriter(fileName);
            PrintWriter output = new PrintWriter(out);

            System.out.print("Amount of letters to shift: ");
            int shift = input.nextInt();

            output.println(shift); // Saves amount of times to shift letters by to first line of text file

            input.nextLine(); // Clears buffer

            for(Person person : list) {
                String firstName, lastName, signature;
                String address, zipcode, city;
                int height;

                firstName = person.getFirstName();
                lastName = person.getLastName();
                signature = person.getSignature();
                height = person.getHeight();

                address = person.getFullAddress().getAddress();
                zipcode = person.getFullAddress().getZipcode();
                city = person.getFullAddress().getCity();

                output.print(action.encrypt(firstName + delim, shift));
                output.print(action.encrypt(lastName + delim, shift));
                output.print(action.encrypt(signature + delim, shift));
                output.print(action.encrypt(height + delim, shift));

                output.print(action.encrypt(address + delim, shift));
                output.print(action.encrypt(zipcode + delim, shift));
                output.print(action.encrypt(city, shift) + "\n");
            }
            output.close();
            System.out.println("\n" + fileName + " successfully written to!");
        }
        enter();
    }

    /**
     * Reads a new file into the system and decrypts it
     * @throws FileNotFoundException Exception thrown if file name is invalid
     */
    public static void readListFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(action.verifyFileName());
        ArrayList<Person> listFromFile = new ArrayList<>();

        // Stores first value of text file as shift-number
        int shift = Integer.parseInt(scanner.nextLine());

        // Dissects text file into multiple parts and stores in ArrayList 'list'.
        while(scanner.hasNext()) {
            Person person = new Person();
            Address address = new Address();

            // Encrypts "DELIM" by 'shift' times.
            // Makes it possible to find the encrypted "DELIM" string in the file.
            String decrDelim = action.encrypt(delim, shift);

            String linesFromFile = scanner.nextLine();
            String[] split = linesFromFile.split(decrDelim);

            String decrFirstName, decrLastName, decrSignature;
            String decrAddress, decrZipcode, decrCity;
            int decrHeight;

            // Reads the 7 first rows of the file and decrypts them
            decrFirstName = action.decrypt(split[0], shift);
            decrLastName = action.decrypt(split[1], shift);
            decrSignature = action.decrypt(split[2], shift);
            decrHeight = Integer.parseInt(action.decrypt(split[3], shift));

            decrAddress = action.decrypt(split[4], shift);
            decrZipcode = action.decrypt(split[5], shift);
            decrCity = action.decrypt(split[6], shift);

            // Stores all 7 rows in object variables
            person.setFirstName(decrFirstName);
            person.setLastName(decrLastName);
            person.setSignature(decrSignature);
            person.setHeight(decrHeight);

            address.setAddress(decrAddress);
            address.setZipcode(decrZipcode);
            address.setCity(decrCity);

            person.setAddress(address); // Collects all elements from Address and attaches to Person
            listFromFile.add(person); // Adds complete person to ArrayList 'newList'

            action.verifySignature(person); // Checks and assigns all signatures
        }

        list = listFromFile; // Overwrites 'list' with list from file

        System.out.println("\nList successfully read!");
        enter();
    }

    /**
     * Shuts off the system
     */
    public static void exit() {
        System.out.println("\nWould you like to exit? (1/0):");
        int min = 0, max = 1, choice = action.verifyChoice(min, max);

        if(choice == 1) {
            System.out.println("\nExiting...");
            enter();
            System.exit(0);
        }
        System.out.println("\nReturning to the menu!");
        enter();
    }
}