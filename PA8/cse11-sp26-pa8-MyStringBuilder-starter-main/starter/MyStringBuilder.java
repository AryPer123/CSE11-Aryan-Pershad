/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains the MyStringBuilder class for CSE 11 PA8. It
 * implements a simple linked list of CharNodes and supports building,
 * appending, copying, and extracting substrings.
 */

/**
 * A simple string builder backed by a singly linked list of CharNodes.
 * Tracks the first node, the last node, and the current length.
 */
public class MyStringBuilder {
    private CharNode start;
    private CharNode end;
    private int length;

    /**
     * Builds a MyStringBuilder containing a single char.
     *
     * @param ch the char to store
     */
    public MyStringBuilder(char ch) {
        // Single node where start and end both point to it
        CharNode node = new CharNode(ch);
        this.start = node;
        this.end = node;
        this.length = 1;
    }

    /**
     * Builds a MyStringBuilder from the chars in a String. An empty
     * String produces an empty MyStringBuilder.
     *
     * @param str the String to copy from
     * @throws NullPointerException if str is null
     */
    public MyStringBuilder(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        // Start empty and let append walk through the chars
        this.start = null;
        this.end = null;
        this.length = 0;
        append(str);
    }

    /**
     * Deep copies the contents of another MyStringBuilder so that the
     * new object owns its own CharNodes.
     *
     * @param other the MyStringBuilder to copy from
     * @throws NullPointerException if other is null
     */
    public MyStringBuilder(MyStringBuilder other) {
        if (other == null) {
            throw new NullPointerException();
        }
        this.start = null;
        this.end = null;
        this.length = 0;
        // Walk other's list and append each char to make new nodes
        CharNode curr = other.start;
        while (curr != null) {
            append(curr.getData());
            curr = curr.getNext();
        }
    }

    /**
     * @return the number of chars currently in this MyStringBuilder
     */
    public int length() {
        return length;
    }

    /**
     * Appends a single char to the end of this MyStringBuilder.
     *
     * @param ch the char to append
     * @return this MyStringBuilder after the append
     */
    public MyStringBuilder append(char ch) {
        CharNode node = new CharNode(ch);
        // If the list is empty, the new node becomes both start and end
        if (start == null) {
            start = node;
            end = node;
        } else {
            end.setNext(node);
            end = node;
        }
        length++;
        return this;
    }

    /**
     * Appends every char of a String to this MyStringBuilder in order.
     * An empty String leaves this unchanged.
     *
     * @param str the String to append
     * @return this MyStringBuilder after the append
     * @throws NullPointerException if str is null
     */
    public MyStringBuilder append(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        // Reuse the single char append for every char in str
        for (int i = 0; i < str.length(); i++) {
            append(str.charAt(i));
        }
        return this;
    }

    /**
     * Walks the linked list and joins the chars into a String.
     *
     * @return a String of the chars in this MyStringBuilder
     */
    @Override
    public String toString() {
        String result = "";
        CharNode curr = start;
        while (curr != null) {
            result = result + curr.getData();
            curr = curr.getNext();
        }
        return result;
    }

    /**
     * Returns the substring from startIdx to the end of this builder.
     *
     * @param startIdx the inclusive starting index
     * @return the String from startIdx to the last char
     * @throws IndexOutOfBoundsException if startIdx is invalid
     */
    public String subString(int startIdx) {
        return subString(startIdx, length);
    }

    /**
     * Returns the substring from startIdx (inclusive) to endIdx
     * (exclusive). Returns an empty String when startIdx equals endIdx.
     *
     * @param startIdx the inclusive starting index
     * @param endIdx   the exclusive ending index
     * @return the substring between the two indices
     * @throws IndexOutOfBoundsException if the indices are invalid
     */
    public String subString(int startIdx, int endIdx) {
        // Reject any out of range or reversed index pair
        if (startIdx < 0 || startIdx > length
                || endIdx > length || endIdx < startIdx) {
            throw new IndexOutOfBoundsException();
        }
        String result = "";
        CharNode curr = start;
        int idx = 0;
        // Walk the list and collect chars only while inside the window
        while (curr != null && idx < endIdx) {
            if (idx >= startIdx) {
                result = result + curr.getData();
            }
            curr = curr.getNext();
            idx++;
        }
        return result;
    }
}
