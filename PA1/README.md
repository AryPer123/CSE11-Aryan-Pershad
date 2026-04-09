# CSE 11 Spring 2026 PA1 - Grade Calculator
**Due date: Thursday, April 9th @ 11:59PM PST**

## Goal:
Programming Assignment 1 is an introduction to Java programming. In this PA, you will get exposure to primitive data types, variables, keyboard input, console output, and arithmetic operations.

## Grade Calculator [100 points]

Write a program called `GradeCalculator` that
- reads a student's scores for programming assignments, quizzes, participation, midterm, and final exam
- calculates the overall score by applying CSE 11's actual grading scheme
- outputs the overall score

### Implementation Details
Your file should be named as `GradeCalculator.java` and contains a single class `GradeCalculator` which has only 1 method: `main`.

There are a few restrictions on the implementation.
- You should not import anything other than `java.util.Scanner`. If you import any other libraries, you may get a 0 for this part, even though if you had passed all the test cases.
- Please do not create multiple `Scanner` objects for `System.in`. They'll fight each other to get the input and cause all of your test cases to fail.
- You should not use `System.exit`. Doing so will cause Autograder to throw exceptions and you'll get "EXCEPT" for all of your test cases.
- You should not print any prompt (e.g. "please enter your score").
- **Do not declare instance variables or non-final static variables.** All your variables should be declared inside the `main` method (local variables). If you want to use constants, they must be declared as `private static final`.

To get started on this PA, create a folder for "PA1" at the location of your choice and open that folder in Visual Studio Code (VS Code). Then create a class named `GradeCalculator` with a `main` method and save it as `GradeCalculator.java`.

#### Input

The program reads user inputs from `System.in` using a [Scanner](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Scanner.html) (*remember to import it*). When a user runs `java GradeCalculator`, the input should be typed through the terminal and should contain exactly 5 lines. You can safely assume that all our inputs will follow this format.

**Scanner Usage Example:**
```java
import java.util.Scanner;  // At the top of your file

Scanner scanner = new Scanner(System.in);  // Create Scanner object
double num1 = scanner.nextDouble();        // Read a double
double num2 = scanner.nextDouble();        // Read another double
double result = num1 + num2;               // Use the values
scanner.close();                           // Close when done
```

**Line 1:** 8 space-separated numbers representing PA scores
- Format: `pa1 pa2 pa3 pa4 pa5 pa6 pa7 pa8`
- Each PA is out of 100 points (treated as a percentage)

**Line 2:** 3 space-separated numbers representing quiz scores
- Format: `quiz1 quiz2 quiz3`
- Each quiz is out of 100 points (treated as a percentage)

**Line 3:** 1 number representing participation points earned
- Range: 0-10 points
- For grading, participation percentage = `(participation / 10) * 100`

**Line 4:** 1 number representing midterm score (as a percentage)
- Range: 0-100

**Line 5:** 1 number representing final exam score (as a percentage)
- Range: 0-100

#### Calculation

Follow these steps to calculate the final grade:

1. **Calculate PA Percentage:**
   - Average all 8 PA scores
   - PA percentage = `(pa1 + pa2 + pa3 + pa4 + pa5 + pa6 + pa7 + pa8) / 8`

2. **Calculate Quiz Percentage:**
   - Average all 3 quiz scores
   - Quiz percentage = `(quiz1 + quiz2 + quiz3) / 3`

3. **Calculate Participation Percentage:**
   - Participation percentage = `(participation / 10) * 100`

4. **Calculate Overall Score using the Grade Breakdown:**

    | Component               | Weight |
    | ----------------------- | ------ |
    | Class Participation     | 10%    |
    | Programming Assignments | 40%    |
    | Quizzes                 | 10%    |
    | Midterm                 | 15%    |
    | Final Exam              | 25%    |

    Overall Score = `(participation_pct * 0.10) + (pa_pct * 0.40) + (quiz_pct * 0.10) + (midterm * 0.15) + (finalExam * 0.25)`

    Note: All components are percentages (0-100):
    - `participation_pct` and `pa_pct` are calculated as percentages from the inputs above
    - `quiz_pct`, `midterm`, and `finalExam` are already percentages from input

#### Output

Write the output to the standard output, which prints the overall score (formatted to **exactly 2 decimal places**).

To print a `double` to exactly 2 decimal places, use `System.out.printf`:
```java
System.out.printf("%.2f%n", overallScore);
```
- `%.2f` formats the number to 2 decimal places (e.g., `83.5` becomes `83.50`)
- `%n` prints a newline at the end (equivalent to what `System.out.println` normally adds)

**Please follow the exact output format, or otherwise you will not get any credit.**

### Example Input and Output

#### Example 1 - Perfect Student

- Input
    ```
    > java GradeCalculator
    100 100 100 100 100 100 100 100
    100 100 100
    10
    100
    100
    ```
    - Line 1: 8 PA scores, all 100
    - Line 2: 3 quiz scores, all 100
    - Line 3: Participation = 10 (maximum)
    - Line 4: Midterm = 100
    - Line 5: Final = 100

- Output
    ```
    100.00
    ```
    - PA percentage: 100%, Quiz percentage: 100%, Participation: 100%, Midterm: 100, Final: 100
    - Overall: 100 * 0.1 + 100 * 0.4 + 100 * 0.1 + 100 * 0.15 + 100 * 0.25 = 100.0

#### Example 2 - Mixed Student

- Input
    ```
    > java GradeCalculator
    80 90 70 85 95 75 88 92
    85 90 80
    8
    75
    88
    ```
    - Line 1: 8 PA scores
      - PA average = (80 + 90 + 70 + 85 + 95 + 75 + 88 + 92) / 8 = 675 / 8 = 84.375%
    - Line 2: 3 quiz scores
      - Quiz average = (85 + 90 + 80) / 3 = 85%
    - Line 3: Participation = 8 → (8/10) * 100 = 80%
    - Line 4: Midterm = 75
    - Line 5: Final = 88

- Output
    ```
    83.50
    ```
    - Overall: 80 * 0.1 + 84.375 * 0.4 + 85 * 0.1 + 75 * 0.15 + 88 * 0.25
    - = 8.0 + 33.75 + 8.5 + 11.25 + 22.0 = 83.50

### Testing

#### Testing with Keyboard Input
You can test your program by typing input manually in the terminal:
```bash
java GradeCalculator
# Then type your inputs line by line
```

#### Testing with File Redirection (Recommended)
Instead of typing inputs manually every time, you can use **file redirection** to read input from a file. This is much more efficient for testing!

**How to use file redirection:**
1. Create a text file (e.g., `test.in`) with your test inputs
2. Run your program with the `<` operator:
   ```bash
   java GradeCalculator < test.in
   ```

**Example test files** are provided in the `starter/` directory:
- `test_perfect.in` - Perfect student (should output `100.00`)
- `test_mixed.in` - Student with varied scores (should output `83.50`)
- `test_decimals.in` - Test with decimal scores (should output `88.20`)

Try the example inputs described above. Do you get the same results as their corresponding outputs? Now try some of your own inputs, do you get the results you would expect?

### Style [0 Points]
Coding style is an important part of ensuring readability and maintainability of your code. We will grade your code style in all submitted code files according to the style guidelines. **For PA1 only, you won't be graded on Style.** Namely, there are a few things you must have in each file/class/method:

* File header
* Class header
* Method header(s)
* Inline comments
* Proper indentation
* Descriptive variable names
* No magic numbers (Exception: Magic numbers can be used for testing.)
* Reasonably short methods (if you have implemented each method according to the specification in this write-up, you’re fine). This is not enforced as strictly.
* Lines shorter than 80 characters, **including comments**
* Javadoc conventions (`@param`, `@return` tags, `/** comments */`, etc.)

A full style guide can be found [here](https://github.com/CaoAssignments/style-guide/blob/main/README.md) and a sample styled file can be found [here](https://github.com/CaoAssignments/guides/blob/main/resources/SampleFile.java). If you need any clarifications, feel free to ask on Piazza.

## Submission
Submit all of the following files to Gradescope by **Thursday, April 9th @ 11:59PM PST**.
 - `GradeCalculator.java`


You can submit as many times as you want on Gradescope, and you will see the results right after each submission. **Only for PA1**, you are able to see all the test cases and results when you submit your assignment to Gradescope. It is your responsibility to test your program comprehensively.

**Important:** Even if your code does not pass all the tests, you will still be able to submit your homework to receive partial points for the tests that you passed. **Make sure your code compiles on Autograder in order to receive partial credit. The name of the file needs to be `GradeCalculator.java`, otherwise you will receive no points.**


### How your assignment will be evaluated [100 points]


* **Correctness** [100 points] You will earn points based on the autograder tests that your code passes. If the autograder tests are not able to run (e.g., your code does not compile or it does not match the specifications in this writeup), you may not earn any credit.
