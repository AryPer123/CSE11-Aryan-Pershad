/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains the starter file for CSE 11 PA2: PasswordSecurity
 * This an introduction to loops and Strings in Java.
 */

import java.util.Scanner;

/**
 * The class prompts and reads a password from user input. Then prints its
 * strength along with a suggested password if it is not "string" enough
 * according to a set of rules.
 */
public class PasswordSecurity {
    private static final String PROMPT_MESSAGE = "Please enter a password: ";
    private static final String TOO_SHORT_MESSAGE = "Password is too short";
    private static final String VERY_WEAK_MESSAGE =
            "Password strength: very weak";
    private static final String WEAK_MESSAGE = "Password strength: weak";
    private static final String MEDIUM_MESSAGE = "Password strength: medium";
    private static final String STRONG_MESSAGE = "Password strength: strong";
    private static final String SUGGESTION_MESSAGE =
            "Here is a suggested stronger password: ";
    private static final String APPEND_TEXT = "Cse";
    private static final String PREPEND_SYMBOLS = "#@";

    private static final int MINIMUM_LENGTH = 8;
    private static final int NUM_CATEGORIES_VERY_WEAK = 1;
    private static final int NUM_CATEGORIES_WEAK = 2;
    private static final int NUM_CATEGORIES_MEDIUM = 3;
    private static final int NUM_CATEGORIES_STRONG = 4;
    private static final int MIN_LETTER_COUNT = 2;
    private static final int INSERT_INTERVAL = 2;
    private static final int MODULO_VALUE = 10;
    private static final int HIGHEST_ASCII_LOWER = 122;

    /**
     * The main method for PasswordSecurity. It prompts user input and
     * prints the input password strength and a suggested password if
     * applicable.
     *
     * @param args command line args (not used)
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print(PROMPT_MESSAGE);
        String password = input.nextLine();
        int originalLength = password.length();

        if (originalLength < MINIMUM_LENGTH) {
            System.out.println(TOO_SHORT_MESSAGE);
            return;
        }

        int upperCount = 0;
        int lowerCount = 0;
        int numberCount = 0;
        int symbolCount = 0;
        for (int i = 0; i < originalLength; i++) {
            char currentChar = password.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                upperCount++;
            } else if (Character.isLowerCase(currentChar)) {
                lowerCount++;
            } else if (Character.isDigit(currentChar)) {
                numberCount++;
            } else {
                symbolCount++;
            }
        }

        int categoryCount = 0;
        if (upperCount > 0) {
            categoryCount++;
        }
        if (lowerCount > 0) {
            categoryCount++;
        }
        if (numberCount > 0) {
            categoryCount++;
        }
        if (symbolCount > 0) {
            categoryCount++;
        }

        if (categoryCount == NUM_CATEGORIES_STRONG) {
            System.out.println(STRONG_MESSAGE);
            return;
        } else if (categoryCount == NUM_CATEGORIES_MEDIUM) {
            System.out.println(MEDIUM_MESSAGE);
        } else if (categoryCount == NUM_CATEGORIES_WEAK) {
            System.out.println(WEAK_MESSAGE);
        } else if (categoryCount == NUM_CATEGORIES_VERY_WEAK) {
            System.out.println(VERY_WEAK_MESSAGE);
        }

        String suggestion = password;
        boolean rule1Applied = false;
        boolean rule2Applied = false;

        if (upperCount + lowerCount < MIN_LETTER_COUNT) {
            suggestion = suggestion + APPEND_TEXT;
            rule1Applied = true;
        }

        if (!rule1Applied && lowerCount == 0) {
            String updated = "";
            boolean changed = false;
            for (int i = 0; i < suggestion.length(); i++) {
                char currentChar = suggestion.charAt(i);
                if (!changed && Character.isUpperCase(currentChar)) {
                    updated = updated + Character.toLowerCase(currentChar);
                    changed = true;
                } else {
                    updated = updated + currentChar;
                }
            }
            suggestion = updated;
            rule2Applied = true;
        }

        if (!rule1Applied && !rule2Applied && upperCount == 0) {
            int lowestCode = HIGHEST_ASCII_LOWER + 1;
            for (int i = 0; i < suggestion.length(); i++) {
                char currentChar = suggestion.charAt(i);
                if (Character.isLowerCase(currentChar)
                        && currentChar < lowestCode) {
                    lowestCode = currentChar;
                }
            }
            int lastIndex = 0;
            for (int i = 0; i < suggestion.length(); i++) {
                if (suggestion.charAt(i) == lowestCode) {
                    lastIndex = i;
                }
            }
            String updated = "";
            for (int i = 0; i < suggestion.length(); i++) {
                char currentChar = suggestion.charAt(i);
                if (i == lastIndex) {
                    updated = updated + Character.toUpperCase(currentChar);
                } else {
                    updated = updated + currentChar;
                }
            }
            suggestion = updated;
        }

        if (numberCount == 0) {
            int insertDigit = originalLength % MODULO_VALUE;
            int currentLen = suggestion.length();
            String updated = "";
            for (int i = 0; i < currentLen; i++) {
                updated = updated + suggestion.charAt(i);
                if ((i + 1) % INSERT_INTERVAL == 0) {
                    updated = updated + insertDigit;
                }
            }
            suggestion = updated;
        }

        if (symbolCount == 0) {
            suggestion = PREPEND_SYMBOLS + suggestion;
        }

        System.out.println(SUGGESTION_MESSAGE + suggestion);
    }
}
