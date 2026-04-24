/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains the starter file for CSE 11 PA3: Parser.
 * This is an introduction to String parsing and static method design
 * in Java. You will implement a small library of static methods that
 * parse a record-based data file of student grade records and answer
 * queries about it using basic String operations.
 */

/**
 * Parser - a small library of static methods for parsing a record-based
 * data file about student grades.
 *
 * Data format: the entire file is passed around as one big String. Each
 * record is one line, terminated by '\n'. Each record is a list of
 * "key=value" pairs separated by ';'. For example:
 *
 *     id=1;student=Alice;course=CSE11;assignment=PA1;grade=85
 *
 * The methods in this class are organized in three tiers. Each tier
 * builds on the previous one:
 *   Tier 1: operations on one "key=value" pair
 *   Tier 2: operations on one record (one line of the file)
 *   Tier 3: operations on the whole data String (many records)
 */
public class Parser {

    public static final String EMPTY_STRING = "";
    public static final String NOT_FOUND = "not found";

    public static final char KV_SEPARATOR = '=';
    public static final char FIELD_SEPARATOR = ';';
    public static final char RECORD_SEPARATOR = '\n';

    public static final String ID_KEY = "id";

    public static final int DECIMAL_BASE = 10;
    public static final char ZERO_CHAR = '0';

    // =====================================================================
    // Tier 1: operations on a single "key=value" pair
    // =====================================================================

    /**
     * Returns the key portion of a "key=value" pair.
     * For example, getKey("student=Alice") returns "student".
     * If the pair contains no '=', returns "".
     *
     * @param pair a String of the form "key=value"
     * @return the key, or "" if there is no '=' in pair
     */
    public static String getKey(String pair) {
        int eqIndex = pair.indexOf(KV_SEPARATOR);
        if (eqIndex == -1) {
            return EMPTY_STRING;
        }
        return pair.substring(0, eqIndex);
    }

    /**
     * Returns the value portion of a "key=value" pair.
     * For example, getValue("student=Alice") returns "Alice".
     * If the pair contains no '=', returns "".
     *
     * @param pair a String of the form "key=value"
     * @return the value, or "" if there is no '=' in pair
     */
    public static String getValue(String pair) {
        int eqIndex = pair.indexOf(KV_SEPARATOR);
        if (eqIndex == -1) {
            return EMPTY_STRING;
        }
        return pair.substring(eqIndex + 1);
    }

    // =====================================================================
    // Tier 2: operations on a single record (one line)
    // =====================================================================

    /**
     * Returns the value associated with the given key inside one record.
     * For example, findFieldVal("id=1;student=Alice", "student") returns
     * "Alice". Returns "" if the key is not present.
     *
     * @param record one record (a line of the form "k1=v1;k2=v2;...")
     * @param key    the key to look up
     * @return the value associated with key, or "" if the key is missing
     */
    public static String findFieldVal(String record, String key) {
        int pairStart = 0;
        int recordLen = record.length();
        while (pairStart < recordLen) {
            int pairEnd = record.indexOf(FIELD_SEPARATOR, pairStart);
            if (pairEnd == -1) {
                pairEnd = recordLen;
            }
            String pair = record.substring(pairStart, pairEnd);
            if (getKey(pair).equals(key)) {
                return getValue(pair);
            }
            pairStart = pairEnd + 1;
        }
        return EMPTY_STRING;
    }

    // =====================================================================
    // Tier 3: operations on the whole data String
    //
    // The whole file is passed in as one String, with records separated
    // by '\n'. These methods walk the data one record at a time using
    // indexOf and substring (no arrays).
    // =====================================================================

    /**
     * Finds the record whose id equals the given id and returns the value
     * of field in that record. Returns "not found" if no such record
     * exists, or if the record is missing that field.
     *
     * @param data  the full file contents
     * @param id    the record id to look up
     * @param field the field to return the value of
     * @return the value, or "not found"
     */
    public static String query(String data, String id, String field) {
        int recordStart = 0;
        int dataLen = data.length();
        while (recordStart < dataLen) {
            int recordEnd = data.indexOf(RECORD_SEPARATOR, recordStart);
            if (recordEnd == -1) {
                recordEnd = dataLen;
            }
            String record = data.substring(recordStart, recordEnd);
            if (findFieldVal(record, ID_KEY).equals(id)) {
                String value = findFieldVal(record, field);
                if (value.equals(EMPTY_STRING)) {
                    return NOT_FOUND;
                }
                return value;
            }
            recordStart = recordEnd + 1;
        }
        return NOT_FOUND;
    }

    /**
     * Counts the number of records whose given field equals the given
     * value.
     *
     * @param data  the full file contents
     * @param field the field to inspect
     * @param value the value to match
     * @return the number of matching records
     */
    public static int countField(String data, String field, String value) {
        int count = 0;
        int recordStart = 0;
        int dataLen = data.length();
        while (recordStart < dataLen) {
            int recordEnd = data.indexOf(RECORD_SEPARATOR, recordStart);
            if (recordEnd == -1) {
                recordEnd = dataLen;
            }
            String record = data.substring(recordStart, recordEnd);
            if (findFieldVal(record, field).equals(value)) {
                count++;
            }
            recordStart = recordEnd + 1;
        }
        return count;
    }

    /**
     * Returns the id of the record with the largest numeric value in the
     * given field. If no record has the field, returns "not found".
     *
     * @param data  the full file contents
     * @param field the numeric field to maximize
     * @return the id of the maximum record, or "not found"
     */
    public static String maxField(String data, String field) {
        int maxValue = 0;
        String maxId = NOT_FOUND;
        boolean found = false;
        int recordStart = 0;
        int dataLen = data.length();
        while (recordStart < dataLen) {
            int recordEnd = data.indexOf(RECORD_SEPARATOR, recordStart);
            if (recordEnd == -1) {
                recordEnd = dataLen;
            }
            String record = data.substring(recordStart, recordEnd);
            String valueStr = findFieldVal(record, field);
            if (!valueStr.equals(EMPTY_STRING)) {
                int value = Integer.parseInt(valueStr);
                if (!found || value > maxValue) {
                    maxValue = value;
                    maxId = findFieldVal(record, ID_KEY);
                    found = true;
                }
            }
            recordStart = recordEnd + 1;
        }
        return maxId;
    }
}
