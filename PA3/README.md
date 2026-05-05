# CSE 11 Spring 2026 PA3 - Grade File Parser
**Due date: Thursday, April 23 @ 11:59PM PDT**

## Provided Files
- `data.txt` - a sample data file of student grade records you can use
  while testing your parser

## Goal
In this assignment we will practice working with `String`s and writing
reusable `static` methods. You will build a small "grade record parser"
from the bottom up: starting with tiny helpers that work on one
`key=value` pair, then helpers that work on a single record,
and finally to methods that answer questions about the whole file's worth
of records.

Everything in this assignment uses only things we have seen in class so
far:
- primitive types, reference types, variables, and console output
- boolean logic and `if` / `else`
- `while`, `for`, and `do`-`while` loops; `break` and `continue`
- `String` methods like `length`, `charAt`, `indexOf`, `substring`, `equals`
- `static` methods, method parameters, and return values

**You do not need (and should not use) arrays, `ArrayList`, or any other
data structure.** The whole data file is passed around as a single
`String`.

## Overview

- Parser [Gradescope, 100 points]
    - Implementation [95 points]
    - Style [5 points]

## The Data File Format [Background]

Your `Parser` operates on plain text data. **Each line is one record.** A
**record** is a list of `key=value` pairs (fields) separated by semicolons. *Every*
record starts with an `id` field. Every `id` is **unique** (no duplicates) — note that this `id` identifies the record, not the student (a student can appear in multiple records, each with its own `id`).

Example (`data.txt`):
```
id=1;student=Alice;course=CSE11;assignment=PA1;grade=85
id=2;student=Alice;course=CSE11;assignment=PA2;grade=92
id=3;student=Alice;course=CSE12;assignment=PA1;grade=88
id=4;student=Bob;course=CSE11;assignment=PA1;grade=78
id=5;student=Bob;course=CSE11;assignment=PA2;grade=65
id=6;student=Carol;course=CSE11;assignment=PA1;grade=95
id=7;student=Carol;course=CSE12;assignment=PA1;grade=100
id=8;student=David;course=CSE12;assignment=PA1;grade=72
```

Each record represents one assignment submission by one student in one
course. In the file above, the **fields** are `id`, `student`, `course`, `assignment`, and `grade`.

In your code you will treat the whole file as one big `String`, with
`'\n'` between records. In [Testing](#testing), we explain how you will pass in this file as input.

## What you need to write

Create a single file, `Parser.java`, containing a `public class Parser`.
The class must contain all of the static methods listed below. You may
add `private` helper methods of your own. Do **not** rename, remove, or
change the signature of any of the required methods.

## Implementation of Parser

Make sure to do Parts 1, 2, and 3 in-order! Parts 1 and 2 have you implement methods which will assist with the implementation of Part 3.

### Part 1 - Accessing `key=value` pairs

```java
public static String getKey(String pair)
public static String getValue(String pair)
```

<table>
  <tr>
   <td><strong>Method Name</strong>
   </td>
   <td><strong>Description</strong>
   </td>
  </tr>
  <tr>
   <td><code>public static String getKey(String pair)</code>
   </td>
   <td> Returns the <code>key</code> from a key-value pair (e.g. <code>student</code> from <code>student=Frank</code>). If the key is the empty string <code>""</code> (e.g. <code>=Frank</code>), return <code>""</code>.
   </td>
  </tr>
  <tr>
   <td><code>public static String getValue(String pair)</code>
   </td>
   <td> Returns the <code>value</code> from a key-value pair (e.g. <code>Frank</code> from <code>student=Frank</code>). If the value is the empty string <code>""</code> (e.g. <code>student=</code>), return <code>""</code>.
   </td>
  </tr>
</table>

- If the pair contains no `=`, both methods should return `""`.
- You cannot assume that the pairs will always have a key, value, and/or "=".

**Examples**
- `getKey("student=Alice")` returns `"student"`.
- `getValue("student=Alice")` returns `"Alice"`.

⚠️ *You will find [`String.indexOf`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#indexOf(int)) and [`String.substring`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#substring(int)) useful.*

### Part 2 - Accessing Records

```java
public static String findFieldVal(String record, String key)
```

<table>
  <tr>
   <td><strong>Method Name</strong>
   </td>
   <td><strong>Description</strong>
   </td>
  </tr>
  <tr>
   <td><code>public static String findFieldVal(String record, String key)</code>
   </td>
   <td> Returns the value of a key-value pair from a single <code>record</code> based on the given <code>key</code>. If <code>key</code> does not exist in the record, return the empty string. If <code>key</code> is the empty string <code>""</code>, follow the handling described in the Part 1 instructions.
   </td>
  </tr>
</table>

**Examples**
- `findFieldVal("id=1;student=Alice;grade=", "student")` returns `"Alice"`.
- `findFieldVal("id=1;student=Alice;grade=", "grade")` returns `""`.

> ⚠️ **Watch out for empty values.** Recall that a record can contain a field whose
> value is the empty string, e.g. `id=1;student=Alice;grade=`. In this
> case the key `grade` *is* present, but its value is `""`. Your
> `findFieldVal` should still return `""` here - the same thing it returns
> when the key is missing entirely. Don't try to distinguish the two
> cases.
>
> *Hint:* Once you locate the `key=value` chunk, don't forget about the
> methods you implemented in Part 1! Remember, one reason why methods
> exist is to prevent rewriting code.

*You will find [`String.indexOf`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#indexOf(int)) and [`String.substring`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#substring(int)) useful.*

### Part 3 - The Whole Data File

```java
public static String query(String data, String id, String field)
public static int    countField(String data, String field, String value)
public static String maxField(String data, String field)
```

<table>
  <tr>
   <td><strong>Method Name</strong>
   </td>
   <td><strong>Description</strong>
   </td>
  </tr>
  <tr>
   <td><code>public static String query(String data, String id, String field)</code>
   </td>
   <td> Given a <code>data</code> file, the <code>id</code> of a record, and a <code>field</code>, return the corresponding value. Return <code>"not found"</code> if no record has that <code>id</code>, or if the record is missing <code>field</code>.
   </td>
  </tr>
  <tr>
   <td><code>public static int countField(String data, String field, String value)</code>
   </td>
   <td> Given a <code>data</code> file, return the number of records with <code>field</code> equal to <code>value</code>. 
   </td>
  </tr>
  <tr>
   <td><code>public static String maxField(String data, String field)</code>
   </td>
   <td> Given a <code>data</code> file, return the <code>id</code> of the record with the largest value of <code>field</code>. Return <code>"not found"</code> if no record has that <code>field</code>. If there is no associated value then skip it. Otherwise, you may assume the value of <code>field</code> will always be a valid integer.
   </td>
  </tr>
</table>

⚠️ **Remember to use your Part 2 methods (when applicable)!**

**Examples (using `data.txt`)**
- `query(data, "3", "student")` returns `"Alice"`.
- `countField(data, "course", "CSE11")` returns 5 because that is the number of records with `course=CSE11`.
- `maxField(data, "grade")` returns 7 because that is the `id` of the record with the
  largest `grade`.

*You will find [`String.indexOf`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#indexOf(int)), [`String.substring`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#substring(int)), and [`Integer.parseInt`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Integer.html#parseInt(java.lang.String)) useful.*

### Additional Examples

Using the data file above:

| Call                                              | Returns             |
|---------------------------------------------------|---------------------|
| `query(data, "1", "student")`                     | `"Alice"`           |
| `query(data, "7", "grade")`                       | `"100"`             |
| `query(data, "99", "student")`                    | `"not found"`       |
| `countField(data, "course", "CSE11")`             | `5`                 |
| `countField(data, "student", "Alice")`            | `3`                 |
| `maxField(data, "grade")`                         | `"7"`               |

## Testing

`Parser` is a library class — all of its methods are `static` and are
called directly by the autograder (there is no required `main`). However, to test
your code locally, you will need to add a `main` method to `Parser.java` that
calls your methods using:
1. hardcoded inputs e.g.
```java
public static void main(String[] args) {
    System.out.println(Parser.getKey("student=Alice"));
    System.out.println(Parser.getValue("student=Alice"));
    System.out.println(Parser.findFieldVal("id=1;grade=85", "grade"));

    String data = "id=1;student=Alice;grade=85\n"
                + "id=2;student=Bob;grade=92\n";
    System.out.println(Parser.query(data, "2", "grade"));
    System.out.println(Parser.countField(data, "student", "Alice"));
    System.out.println(Parser.maxField(data, "grade"));
}
```
2. and/or *input redirection* (might be more convenient) e.g.
```
// In the terminal
$ java Parser < data.txt    // "<" takes the file on the right and "redirects" it into your program as input
```
```java
// In your code
public static void main(String[] args) {
    System.out.println(Parser.getKey("student=Alice"));
    System.out.println(Parser.getValue("student=Alice"));
    System.out.println(Parser.findFieldVal("id=1;grade=85", "grade"));

    // Input redirection brings the file in from System.in (same place your terminal input goes through)
    Scanner sc = new Scanner(System.in);
    sc.useDelimiter("\\A"); // This makes sure we read in the whole file with one next() call
    String data = sc.next();
    sc.close();

    System.out.println(Parser.query(data, "2", "grade"));
    System.out.println(Parser.countField(data, "student", "Alice"));
    System.out.println(Parser.maxField(data, "grade"));
}
```

Either way, you can check if the printed results match your expectations.

- Above are just some straightforward examples. You should test your code more thoroughly.
- Feel free to reuse the Scanner code above if you'd like, ⚠️ **but make sure to remove/comment out it and your Scanner import before you submit so it isn't flagged by the autograder!**

You may leave a `main` method in your final submission — the autograder
ignores it. You may **not** use any `import` statements or use
`String.split` in your Gradescope submission — both defeat the point of walking the String yourself.
Use `indexOf`, `substring`, and `charAt` instead.

### What the tests cover

The autograder runs two groups of tests: **public tests** (visible right
away, so you can use them to iterate) and **private tests**. Both groups
are worth points. The private tests are
not meant to trick you — they just exercise the same methods on more
data and on the edge cases listed below, so make sure your implementation
handles them.

| Method          | Public tests focus on…                                   | Private tests additionally check…                                                                      |
|-----------------|----------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| `getKey`        | normal `key=value` pairs                                 | empty input, input with no `=`, input that starts or ends with `=`, inputs with multiple `=` signs     |
| `getValue`      | normal `key=value` pairs                                 | empty input, input with no `=`, input where the value is the empty string, inputs with multiple `=` signs |
| `findFieldVal`     | looking up fields that are present and absent            | records with empty-string values, duplicated keys, keys that are a prefix of another key, empty records |
| `query`         | looking up existing records and existing fields          | ids that do not exist (including non-integer-looking ones), fields that are missing from the record     |
| `countField`    | counting values that appear in the data                  | values that never appear, fields that are missing entirely, case sensitivity                             |
| `maxField`      | finding the id of the largest numeric field              | fields that are missing from every record (should return `"not found"`)                                 |

A few general rules to keep in mind while coding:

- All `String` comparisons are case-sensitive (`"CSE11"` and `"cse11"`
  are different).
- Returning `null` is never the right answer - return `""` or
  `"not found"` as specified.
- Your code should never throw an exception on any of the inputs above.
  If the data is missing a field, skip it; don't crash.

## Grading

The autograder exercises each method independently. You do **not** need
to implement the later parts using the methods from the previous parts, but doing
so keeps your code short (and is the point of the assignment!).

- Part 1 (`getKey`, `getValue`) - 15 pts
- Part 2 (`findFieldVal`) - 20 pts
- Part 3 (`query`, `countField`, `maxField`) - 60 pts
- Style - 5 pts

### Style [5 Points]
Coding style is an important part of ensuring readability and maintainability of your code. We will grade your code style in all submitted code files according to the style guidelines. Namely, there are a few things you must have in each file/class/method:

1. File header
2. Class header
3. Method header(s)
4. Inline comments
5. Proper indentation
6. Descriptive variable names
7. No magic numbers
8. Reasonably short methods (if you have implemented each method according to specification in this write-up, you’re fine). This is not enforced as strictly.
9. Lines shorter than 80 characters
10. Javadoc conventions (@param, @return tags, /** comments */, etc.)

A full style guide can be found [here](https://github.com/CaoAssignments/guides/blob/main/README.md) and a sample styled file can be found [here](https://github.com/CaoAssignments/guides/blob/main/resources/SampleFile.java). If you need any clarifications, feel free to ask on Piazza.
