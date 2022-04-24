package se.miun.projekt;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Various actions for main-class 'Main.java'
 */
public class Actions {

    private final Scanner input = new Scanner(System.in);
    private static final ArrayList<String> signatureList = new ArrayList<>();

    /**
     * Try-catch to see if entered choice either is an integer or inside specified range
     * @return Returns choice
     */
    public int verifyChoice(int min, int max) {
        int choice = 0;
        boolean done = false;

        while(!done) {
            try{
                String userInput = input.nextLine();
                choice = Integer.parseInt(userInput);

                if(choice < min || choice > max) throw new Exception();

                done = true;
            } catch (Exception e) {
                System.err.print("Incorrect input\n> ");
            }
        }
        return choice;
    }

    public void verifySignature(Person person) {
        String signature, firstName, lastName;
        int counter = 1, index = 6;

        firstName = person.getFirstName();
        lastName = person.getLastName();

        signature = person.generateSignature(firstName, lastName);
        signature = signature.substring(0, index) + "0" + counter;

        if(!signatureList.isEmpty()) {
            for(String s : signatureList) {
                if(signature.equals(s)) {
                    String end;

                    counter++;

                    if(counter < 10) end = "0" + counter;
                    else end = "" + counter;

                    signature = signature.substring(0, index) + end;
                }
            }
        }

        person.setSignature(signature);
        signatureList.add(signature);
    }

    public Person createNewPerson(Person person) {
        String name, firstName, lastName;

        while(true) {
            System.out.print("\nEnter first and last name\n> ");
            name = input.nextLine();

            String[] split = name.split(" ");

            if (split.length < 2) {
                System.err.println("Please enter both first and last name!\n");
                continue;
            } else if (split.length == 3) {
                firstName = split[0];
                lastName = split[1] + " " + split[2];
            } else if (split.length == 4) {
                firstName = split[0] + " " + split[1];
                lastName = split[2] + " " + split[3];
            } else if (split.length > 4) {
                System.err.println("Please only enter first and last name!\n");
                continue;
            } else {
                firstName = split[0];
                lastName = split[1];
            }
            break;
        }
        person.setFirstName(firstName);
        person.setLastName(lastName);

        int height = verifyHeight(); // Try-catch to see if height is an integer or not
        person.setHeight(height);

        return person;
    }

    /**
     * Checks to see if entered height is an integer
     */
    public int verifyHeight() {
        String height;
        while(true) {
            System.out.print("\nEnter height (cm)\n> ");
            height = input.nextLine();
            try{
                Integer.parseInt(height);
                break;
            } catch (NumberFormatException e) {
                System.err.print("Please enter a valid height!\n\n");
            }
        }
        return Integer.parseInt(height);
    }

    public boolean verifyPerson(Person person, ArrayList<Person> list) {
        boolean isReturning = false;

        if(!isUnique(person, list)) {
            boolean isNotUnique = false;

            while(!isNotUnique) {
                String name = person.getFirstName() + " " + person.getLastName();
                String height = person.heightToString() + " m";

                System.out.println("\n" + name + ", " + height + " already exists!");

                System.out.print("Change or cancel input? (1/0): ");
                int min = 0, max = 1;
                int choice = verifyChoice(min, max);

                if(choice == 1) {
                    person = createNewPerson(person);

                    isNotUnique = isUnique(person, list);
                }
                if(choice == 0) {
                    isReturning = true;
                    break;
                }
            }
        }

        verifySignature(person);
        person.setFirstName(person.getFirstName());
        person.setLastName(person.getLastName());
        person.setHeight(person.getHeight());

        return isReturning;
    }

    private boolean isUnique(Person person, ArrayList<Person> list) {
        String firstName, lastName;
        int height;
        boolean isUnique = true;

        firstName = person.getFirstName().toLowerCase();
        lastName = person.getLastName().toLowerCase();
        height = person.getHeight();

        for(Person p : list) {
            String checkFirstName = p.getFirstName().toLowerCase();
            String checkLastName = p.getLastName().toLowerCase();
            int checkHeight = p.getHeight();

            if (firstName.equals(checkFirstName) && lastName.equals(checkLastName) && (height == checkHeight)) {
                isUnique = false;
                break;
            }
        }

        return isUnique;
    }

    public String verifyInput() {
        String userInput;

        while(true) {
            userInput = input.nextLine();

            if(userInput.equals("")) {
                System.err.print("\nInvalid input!\n> ");
                continue;
            }

            break;
        }
        return userInput;
    }

    /**
     * Checks to see if the name of a file is correct
     * @return Returns the file
     */
    public File verifyFileName() {
        File file;

        do {
            System.out.print("\nEnter file name (with file type): ");
            String fileName = input.nextLine();
            file = new File(fileName);

            if(!file.exists()) {
                System.err.println("File not found!");
            }
        } while(!file.exists());

        return file;
    }

    /**
     * Encrypts every element of a Person-object
     * @param string Takes part of a Person-object + a delimiter as parameter
     * @param shift Takes amount of letters to shift by as parameter
     * @return Returns each encrypted element
     */
    public String encrypt(String string, int shift) {
        char[] chars = string.toCharArray();

        String encryptedString = "";
        for(char c : chars) {
            c += shift;
            encryptedString = encryptedString.concat(String.valueOf(c));
        }
        return encryptedString;
    }

    /**
     * Decrypts every row of a text file
     * @param string Takes part of the text file as parameter
     * @param shift Takes amount of letters to shift back by as parameter
     * @return Returns each decrypted element
     */
    public String decrypt(String string, int shift) {
        char[] chars = string.toCharArray();

        String encryptedString = "";
        for(char c : chars) {
            c -= shift;
            encryptedString = encryptedString.concat(String.valueOf(c));
        }
        return encryptedString;
    }
}
