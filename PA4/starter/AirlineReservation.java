/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains an AirlineReservation class that implements a simple
 * airline reservation system allowing users to book, cancel, and upgrade
 * tickets on a 1D array-based airplane representation.
 */

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * AirlineReservation class that manages airplane seat reservations using
 * a 1D array structure. Supports booking, cancellation, upgrades, and
 * passenger lookup operations across three travel classes.
 */
public class AirlineReservation {
    /* Delimiters and Formatters */
    private static final String CSV_DELIMITER = ",";
    private static final String COMMAND_DELIMITER = " ";
    private static final String PLANE_FORMAT = "%d\t | %s | %s \n";

    /* Travel Classes */
    private static final int FIRST_CLASS = 0;
    private static final int BUSINESS_CLASS = 1;
    private static final int ECONOMY_CLASS = 2;
    private static final String[] CLASS_LIST = new String[] {"F", "B", "E"};

    /* Ticket Prices per Travel Class */
    private static final int ECONOMY_CLASS_PRICE = 100;
    private static final int BUSINESS_CLASS_PRICE = 200;
    private static final int FIRST_CLASS_PRICE = 400;
    private static final String[] CLASS_FULLNAME_LIST = new String[] {
        "First Class", "Business Class", "Economy Class"};

    /* Commands */
    private static final String[] COMMANDS_LIST = new String[] { "book", 
        "cancel", "lookup", "availabletickets", "upgrade", "print","exit"};
    private static final int BOOK_IDX = 0;
    private static final int CANCEL_IDX = 1;
    private static final int LOOKUP_IDX = 2;
    private static final int AVAI_TICKETS_IDX = 3;
    private static final int UPGRADE_IDX = 4;
    private static final int PRINT_IDX = 5;
    private static final int EXIT_IDX = 6;
    private static final int BOOK_UPGRADE_NUM_ARGS = 3;
    private static final int CANCEL_LOOKUP_NUM_ARGS = 2;

    /* Strings for main */
    private static final String USAGE_HELP =
            "Available commands:\n" +
            "- book <travelClass(F/B/E)> <passengerName>\n" +
            "- book <rowNumber> <passengerName>\n" +
            "- cancel <passengerName>\n" +
            "- lookup <passengerName>\n" +
            "- availabletickets\n" +
            "- upgrade <travelClass(F/B)> <passengerName>\n" +
            "- print\n" +
            "- exit";
    private static final String CMD_INDICATOR = "> ";
    private static final String INVALID_COMMAND = "Invalid command.";
    private static final String INVALID_ARGS = "Invalid number of arguments.";
    private static final String INVALID_ROW = 
        "Invalid row number %d, failed to book.\n";
    private static final String DUPLICATE_BOOK =
        "Passenger %s already has a booking and cannot book multiple seats.\n";
    private static final String BOOK_SUCCESS = 
        "Booked passenger %s successfully.\n";
    private static final String BOOK_FAIL = "Could not book passenger %s.\n";
    private static final String CANCEL_SUCCESS = 
        "Canceled passenger %s's booking successfully.\n";
    private static final String CANCEL_FAIL = 
        "Could not cancel passenger %s's booking, do they have a ticket?\n";
    private static final String UPGRADE_SUCCESS = 
        "Upgraded passenger %s to %s successfully.\n";
    private static final String UPGRADE_FAIL = 
        "Could not upgrade passenger %s to %s.\n";
    private static final String LOOKUP_SUCCESS = 
            "Passenger %s is in row %d.\n";
    private static final String LOOKUP_FAIL = "Could not find passenger %s.\n";
    private static final String AVAILABLE_TICKETS_FORMAT = "%s: %d\n";
    
    /* Static variables - DO NOT add any additional static variables */
    static String [] passengers;
    static int planeRows;
    static int firstClassRows;
    static int businessClassRows;

    /**
     * Runs the command-line interface for our Airline Reservation System.
     * Prompts user to enter commands, which correspond to different functions.
     * @param args args[0] contains the filename to the csv input
     * @throws FileNotFoundException if the filename args[0] is not found
     */
    public static void main (String[] args) throws FileNotFoundException {
        // If there are an incorrect num of args, print error message and quit
        if (args.length != 1) {
            System.out.println(INVALID_ARGS);
            return;
        }
        initPassengers(args[0]); // Populate passengers based on csv input file
        System.out.println(USAGE_HELP);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(CMD_INDICATOR);
            String line = scanner.nextLine().trim();

            // Exit
            if (line.toLowerCase().equals(COMMANDS_LIST[EXIT_IDX])) {
                scanner.close();
                return;
            }

            String[] splitLine = line.split(COMMAND_DELIMITER);
            splitLine[0] = splitLine[0].toLowerCase(); 

            // Check for invalid commands
            boolean validFlag = false;
            for (int i = 0; i < COMMANDS_LIST.length; i++) {
                if (splitLine[0].toLowerCase().equals(COMMANDS_LIST[i])) {
                    validFlag = true;
                }
            }
            if (!validFlag) {
                System.out.println(INVALID_COMMAND);
                continue;
            }

            // Book
            if (splitLine[0].equals(COMMANDS_LIST[BOOK_IDX])) {
                if (splitLine.length < BOOK_UPGRADE_NUM_ARGS) {
                    System.out.println(INVALID_ARGS);
                    continue;
                }
                String[] contents = line.split(COMMAND_DELIMITER, 
                        BOOK_UPGRADE_NUM_ARGS);
                String passengerName = contents[contents.length - 1];
                try {
                    // book row <passengerName>
                    int row = Integer.parseInt(contents[1]);
                    if (row < 0 || row >= passengers.length) {
                        System.out.printf(INVALID_ROW, row);
                        continue;
                    }
                    // Do not allow duplicate booking
                    boolean isDuplicate = false;
                    for (int i = 0; i < passengers.length; i++) {
                        if (passengerName.equals(passengers[i])) {
                            isDuplicate = true;
                        }
                    }
                    if (isDuplicate) {
                        System.out.printf(DUPLICATE_BOOK, passengerName);
                        continue;
                    }
                    if (book(row, passengerName)) {
                        System.out.printf(BOOK_SUCCESS, passengerName);
                    } else {
                        System.out.printf(BOOK_FAIL, passengerName);
                    }
                } catch (NumberFormatException e) {
                    // book <travelClass(F/B/E)> <passengerName>
                    validFlag = false;
                    contents[1] = contents[1].toUpperCase();
                    for (int i = 0; i < CLASS_LIST.length; i++) {
                        if (CLASS_LIST[i].equals(contents[1])) {
                            validFlag = true;
                        }
                    }
                    if (!validFlag) {
                        System.out.println(INVALID_COMMAND);
                        continue;
                    }
                    // Do not allow duplicate booking
                    boolean isDuplicate = false;
                    for (int i = 0; i < passengers.length; i++) {
                        if (passengerName.equals(passengers[i])) {
                            isDuplicate = true;
                        }
                    }
                    if (isDuplicate) {
                        System.out.printf(DUPLICATE_BOOK, passengerName);
                        continue;
                    }
                    int travelClass = FIRST_CLASS;
                    if (contents[1].equals(CLASS_LIST[BUSINESS_CLASS])) {
                        travelClass = BUSINESS_CLASS;
                    } else if (contents[1].equals(
                                CLASS_LIST[ECONOMY_CLASS])) {
                        travelClass = ECONOMY_CLASS;
                    }
                    if (book(passengerName, travelClass)) {
                        System.out.printf(BOOK_SUCCESS, passengerName);
                    } else {
                        System.out.printf(BOOK_FAIL, passengerName);
                    }
                }
            }

            // Upgrade 
            if (splitLine[0].equals(COMMANDS_LIST[UPGRADE_IDX])) {
                if (splitLine.length < BOOK_UPGRADE_NUM_ARGS) {
                    System.out.println(INVALID_ARGS);
                    continue;
                }
                String[] contents = line.split(COMMAND_DELIMITER, 
                        BOOK_UPGRADE_NUM_ARGS);
                String passengerName = contents[contents.length - 1];
                validFlag = false;
                contents[1] = contents[1].toUpperCase();
                for (int i = 0; i < CLASS_LIST.length; i++) {
                    if (CLASS_LIST[i].equals(contents[1])) {
                        validFlag = true;
                    }
                }
                if (!validFlag) {
                    System.out.println(INVALID_COMMAND);
                    continue;
                }
                int travelClass = FIRST_CLASS;
                if (contents[1].equals(CLASS_LIST[BUSINESS_CLASS])) {
                    travelClass = BUSINESS_CLASS;
                } else if (contents[1].equals(CLASS_LIST[ECONOMY_CLASS])) {
                    travelClass = ECONOMY_CLASS;
                }
                if (upgrade(passengerName, travelClass)) {
                    System.out.printf(UPGRADE_SUCCESS, passengerName, 
                            CLASS_FULLNAME_LIST[travelClass]);
                } else {
                    System.out.printf(UPGRADE_FAIL, passengerName, 
                            CLASS_FULLNAME_LIST[travelClass]);
                }
            }

            // Cancel
            if (splitLine[0].equals(COMMANDS_LIST[CANCEL_IDX])) {
                if (splitLine.length < CANCEL_LOOKUP_NUM_ARGS) {
                    System.out.println(INVALID_ARGS);
                    continue;
                }
                String[] contents = line.split(COMMAND_DELIMITER, 
                        CANCEL_LOOKUP_NUM_ARGS);
                String passengerName = contents[contents.length - 1];
                if (cancel(passengerName)) {
                    System.out.printf(CANCEL_SUCCESS, passengerName);
                } else {
                    System.out.printf(CANCEL_FAIL, passengerName);
                }
            }

            // Lookup
            if (splitLine[0].equals(COMMANDS_LIST[LOOKUP_IDX])) {
                if (splitLine.length < CANCEL_LOOKUP_NUM_ARGS) {
                    System.out.println(INVALID_ARGS);
                    continue;
                }
                String[] contents = line.split(COMMAND_DELIMITER, 
                        CANCEL_LOOKUP_NUM_ARGS);
                String passengerName = contents[contents.length - 1];
                if (lookUp(passengerName) == -1) {
                    System.out.printf(LOOKUP_FAIL, passengerName);
                } else {
                    System.out.printf(LOOKUP_SUCCESS, passengerName, 
                            lookUp(passengerName));
                }
            }

            // Available tickets
            if (splitLine[0].equals(COMMANDS_LIST[AVAI_TICKETS_IDX])) {
                int[] numTickets = availableTickets();
                for (int i = 0; i < CLASS_FULLNAME_LIST.length; i++) {
                    System.out.printf(AVAILABLE_TICKETS_FORMAT, 
                            CLASS_FULLNAME_LIST[i], numTickets[i]);
                }
            }

            // Print
            if (splitLine[0].equals(COMMANDS_LIST[PRINT_IDX])) {
                printPlane();
            }
        }
    }

    /**
     * Initializes passengers, planeRows, firstClassRows, and
     * businessClassRows by reading from the given CSV file.
     *
     * @param fileName the path to the CSV input file
     * @throws FileNotFoundException if the file at fileName does not exist
     */
    private static void initPassengers(String fileName) throws
            FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(fileName));
        String[] firstLine = fileScanner.nextLine().split(CSV_DELIMITER);
        planeRows = Integer.parseInt(firstLine[0]);
        firstClassRows = Integer.parseInt(firstLine[1]);
        businessClassRows = Integer.parseInt(firstLine[2]);
        passengers = new String[planeRows];
        while (fileScanner.hasNextLine()) {
            String[] parts = fileScanner.nextLine().split(CSV_DELIMITER);
            int row = Integer.parseInt(parts[0]);
            passengers[row] = parts[1];
        }
        fileScanner.close();
    }

    /**
     * Returns the travel class associated with the given row.
     *
     * @param row the row number to inspect
     * @return FIRST_CLASS, BUSINESS_CLASS, or ECONOMY_CLASS for the row,
     *         or -1 if the row does not exist on the plane
     */
    private static int findClass(int row) {
        if (row < 0 || row >= planeRows) {
            return -1;
        }
        if (row < firstClassRows) {
            return FIRST_CLASS;
        }
        if (row < firstClassRows + businessClassRows) {
            return BUSINESS_CLASS;
        }
        return ECONOMY_CLASS;
    }

    /**
     * Returns the first row in the given travel class.
     *
     * @param travelClass FIRST_CLASS, BUSINESS_CLASS, or ECONOMY_CLASS
     * @return the index of the first row of travelClass, or -1 if
     *         travelClass is not a valid class
     */
    private static int findFirstRow(int travelClass) {
        if (travelClass == FIRST_CLASS) {
            return 0;
        }
        if (travelClass == BUSINESS_CLASS) {
            return firstClassRows;
        }
        if (travelClass == ECONOMY_CLASS) {
            return firstClassRows + businessClassRows;
        }
        return -1;
    }

    /**
     * Returns the last row in the given travel class.
     *
     * @param travelClass FIRST_CLASS, BUSINESS_CLASS, or ECONOMY_CLASS
     * @return the index of the last row of travelClass, or -1 if
     *         travelClass is not a valid class
     */
    private static int findLastRow(int travelClass) {
        if (travelClass == FIRST_CLASS) {
            return firstClassRows - 1;
        }
        if (travelClass == BUSINESS_CLASS) {
            return firstClassRows + businessClassRows - 1;
        }
        if (travelClass == ECONOMY_CLASS) {
            return planeRows - 1;
        }
        return -1;
    }

    /**
     * Books passengerName in the first available row of travelClass.
     *
     * @param passengerName the passenger's name
     * @param travelClass   FIRST_CLASS, BUSINESS_CLASS, or ECONOMY_CLASS
     * @return true if booked successfully, false if no seat is available
     *         or passengerName is null
     */
    public static boolean book(String passengerName, int travelClass) {
        if (passengerName == null) {
            return false;
        }
        int first = findFirstRow(travelClass);
        int last = findLastRow(travelClass);
        for (int i = first; i <= last; i++) {
            if (passengers[i] == null) {
                passengers[i] = passengerName;
                return true;
            }
        }
        return false;
    }

    /**
     * Books passengerName at the given row. If the row is already taken,
     * books in the first available row in the same travel class as row.
     *
     * @param row           the requested row number
     * @param passengerName the passenger's name
     * @return true if booked successfully, false otherwise
     */
    public static boolean book(int row, String passengerName) {
        if (passengerName == null) {
            return false;
        }
        if (passengers[row] == null) {
            passengers[row] = passengerName;
            return true;
        }
        return book(passengerName, findClass(row));
    }

    /**
     * Cancels the booking for passengerName by removing them from the
     * passengers array.
     *
     * @param passengerName the passenger to cancel
     * @return true if the passenger was found and removed, false otherwise
     */
    public static boolean cancel(String passengerName){
        if (passengerName == null) {
            return false;
        }
        for (int i = 0; i < passengers.length; i++) {
            if (passengerName.equals(passengers[i])) {
                passengers[i] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Looks up the row number assigned to passengerName.
     *
     * @param passengerName the passenger to find
     * @return the row number of passengerName, or -1 if not found or null
     */
    public static int lookUp(String passengerName) {
        if (passengerName == null) {
            return -1;
        }
        for (int i = 0; i < passengers.length; i++) {
            if (passengerName.equals(passengers[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Counts the available (unbooked) seats in each travel class.
     *
     * @return an int[] of length 3 in the format
     *         [firstClassAvail, businessAvail, economyAvail]
     */
    public static int[] availableTickets() {
        int[] counts = new int[CLASS_LIST.length];
        for (int i = 0; i < passengers.length; i++) {
            if (passengers[i] == null) {
                counts[findClass(i)]++;
            }
        }
        return counts;
    }

    /**
     * Upgrades passengerName to the first available row in upgradeClass.
     * Fails if passengerName is null, not found, upgradeClass is not of
     * higher status than the passenger's current class, or upgradeClass
     * has no available seats. The passenger stays in their original row
     * when the upgrade fails.
     *
     * @param passengerName the passenger to upgrade
     * @param upgradeClass  the target travel class
     * @return true if the upgrade succeeded, false otherwise
     */
    public static boolean upgrade(String passengerName, int upgradeClass) {
        if (passengerName == null) {
            return false;
        }
        int currentRow = lookUp(passengerName);
        if (currentRow == -1) {
            return false;
        }
        int currentClass = findClass(currentRow);
        if (upgradeClass >= currentClass) {
            return false;
        }
        int first = findFirstRow(upgradeClass);
        int last = findLastRow(upgradeClass);
        for (int i = first; i <= last; i++) {
            if (passengers[i] == null) {
                passengers[i] = passengerName;
                passengers[currentRow] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Greedily assigns passengers in toBook to the best class within
     * budget, maximizing First Class count first, then Business Class,
     * then Economy. Bookings are performed in the order passengers
     * appear in toBook.
     *
     * @param budget the maximum total ticket cost
     * @param toBook the passenger names to book
     * @return an int[] of length 3 in the format
     *         [firstClassCount, businessClassCount, economyClassCount]
     */
    public static int[] greedyAssign(int budget, String[] toBook) {
        int n = toBook.length;
        int[] avail = availableTickets();
        int remaining = budget - n * ECONOMY_CLASS_PRICE;
        int firstExtra = FIRST_CLASS_PRICE - ECONOMY_CLASS_PRICE;
        int businessExtra = BUSINESS_CLASS_PRICE - ECONOMY_CLASS_PRICE;

        int firstCount = remaining / firstExtra;
        if (firstCount > avail[FIRST_CLASS]) {
            firstCount = avail[FIRST_CLASS];
        }
        if (firstCount > n) {
            firstCount = n;
        }
        remaining -= firstCount * firstExtra;

        int businessCount = remaining / businessExtra;
        if (businessCount > avail[BUSINESS_CLASS]) {
            businessCount = avail[BUSINESS_CLASS];
        }
        if (businessCount > n - firstCount) {
            businessCount = n - firstCount;
        }

        int economyCount = n - firstCount - businessCount;

        int idx = 0;
        for (int i = 0; i < firstCount; i++) {
            book(toBook[idx], FIRST_CLASS);
            idx++;
        }
        for (int i = 0; i < businessCount; i++) {
            book(toBook[idx], BUSINESS_CLASS);
            idx++;
        }
        for (int i = 0; i < economyCount; i++) {
            book(toBook[idx], ECONOMY_CLASS);
            idx++;
        }

        int[] result = new int[CLASS_LIST.length];
        result[FIRST_CLASS] = firstCount;
        result[BUSINESS_CLASS] = businessCount;
        result[ECONOMY_CLASS] = economyCount;
        return result;
    }

    /**
     * Prints out the names of each of the passengers according to their booked
     * seat row. No name is printed for an empty (currently available) seat.
     */
    public static void printPlane() {
        for (int i = 0; i < passengers.length; i++) {
            System.out.printf(PLANE_FORMAT, i, CLASS_LIST[findClass(i)], 
                    passengers[i] == null ? "" : passengers[i]);
        }
    }
}
