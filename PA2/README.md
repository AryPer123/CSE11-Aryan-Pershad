# CSE 11 Spring 2026 PA2 - Password Security
**Due date: Thursday, April 16 @ 11:59PM PDT**

## Provided Files
- PasswordSecurity.java

## Goal
Programming Assignment 2 is an introduction to loops and Strings in Java. You will use loops, String methods, and other programming techniques to complete the assignment.

## Overview

- Password Security [Gradescope, 100 points]
    - Implementation [95 points]
    - Style [5 points]


## Password Security [100 Points]
As the world becomes more and more digital, many of us have an increasing number of accounts and passwords to manage. Having strong and unique passwords is an important step to protect our accounts.

In this programming assignment, we will evaluate the strength of passwords using a simplified model which will only calculate a password's strength based on the presence of uppercase letters, lowercase letters, numbers, and symbols. You can learn more about how password strengths are assessed in the real world [here](https://www.uic.edu/apps/strong-password/).

### Your Task
Write a program called `PasswordSecurity` that
- Reads a user's password 
- Evaluates the strength of the password
- Suggests a stronger password if their password is not strong enough

### Implementation [95 Points]

Your file should be named as `PasswordSecurity.java` and should contain a single class `PasswordSecurity` which has only 1 method: `main`.

#### Input
The program reads user inputs from `System.in` using a [Scanner](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Scanner.html). When an user runs `java PasswordSecurity`, they will be prompted to type a password into our program, ended by a newline character. *(See examples below on how your input and output should be formatted.)*

You may safely assume that the characters in the password are within the [ASCII](https://www.rapidtables.com/code/text/ascii-table.html) range [33,126] (from `!` to `~`, inclusive) and the length of the entered password is $\leq$ 128.

#### Password Strength Evaluation

First, we need to validate the length of the entered password. If the password is less than 8 characters long, print `Password is too short` and exit the program.

Then, determine the presense of the following categories in the input password: *(you may find it helpful to count and store the number of characters for later)*
- Uppercase letters
- Lowercase letters
- Numbers
- Symbols

Which map to the following strengths:
- Very weak: having exactly 1 of the above categories
- Weak: having exactly 2 of the above categories
- Medium: having exactly 3 of the above categories
- Strong: having exactly all 4 of the above categories

For example, if a password only has uppercase and lowercase letters, it would be considered "Weak". A password that has uppercase, lowercase, numbers, and symbols would be considered "Strong".

Print the calculated password strength to standard output. *(See examples below on how you should format your output.)*

You may find methods in the [Character class](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Character.html) helpful for determining which category a character falls under. For this PA, all characters in the ASCII range [33,126] that are not letters or numbers are considered as symbols.

#### Password Suggestion

If the user's password is not considered "Strong" by our evaluation model, we will suggest a stronger password for the user by applying these rules in the following order:
1. If there fewer than 2 letters, **append** `Cse` to the current password. If this rule is applied, skip Rule 2 and Rule 3.
2. If there are no lowercase letters, change the **first uppercase letter to lowercase**. If this rule is applied, skip Rule 3.
3. If there are no uppercase letters, change the **last occurrence** of the lowest-ASCII-valued lowercase character to uppercase.
4. If there are no numbers, insert $k$ into the **current password** every 2 characters, where $k$ is the **original length** of the password *(before ANY rules were applied)* mod 10.
*If the current password length (before this rule is applied) is divisible by 2,* $k$ *should be inserted at the very end as well.*
5. If there are no symbols, **prepend** `#@` to the current password.

*Note that only up to one of Rules 1-3 should apply to a given password.*

**Examples:**

**Note:** `$` is the symbol at the start of common Linux terminals. Depends on which terminal you use you may or may not see the same thing. For example, `javac PasswordSecurity.java` and `java PasswordSecurity` is what you put into the command line to compile and run your java file, you **don't type** the `$`.

#### Example 1:
```
$ java PasswordSecurity
Please enter a password: 12345678
Password strength: very weak
Here is a suggested stronger password: #@12345678Cse
```
*Rules 1 and 5 were applied.*

#### Example 2:
```
$ java PasswordSecurity
Please enter a password: paulcao<3
Password strength: medium
Here is a suggested stronger password: paulcAo<3
```
*Only Rule 3 was applied, the letter `a` is the lowest-ASCII-value lowercase character.*

#### Example 3:
```
$ java PasswordSecurity
Please enter a password: PASSWORD???
Password strength: weak
Here is a suggested stronger password: pA1SS1WO1RD1??1?
```
*Rules 2 and 4 were applied.
Since the original password's length is 11,* $k=11$ $mod$ $10=1$
*The digit* $1$ *is inserted every 2 characters.*


#### Additional Implementation Requirements
- Write the output to the standard output. There should be a **newline character** at the end of each line. 
**Note:** `System.out.println()` method prints the text on the console and the cursor remains at the start of the next line at the console (i.e. it appends a newline character at the end of the line, so you **do not** need to add a newline character by yourself if you are using `System.out.println`). 
**Please follow the exact output format, or otherwise you will not get any credit.**
- You should not import anything other than `java.util.Scanner`. If you import any other libraries, your autograder will not execute.
- You **should not** use `System.exit`. Doing so will cause Autograder to throw exceptions and you'll get "EXCEPT" for all of your test cases.
- You **should not** use any static variables for this PA. Using `private static final String` is acceptable.
- You do not need to deal with cases where the suggested password MAY exceed 128 characters.


### Style [5 Points]
Coding style is an important part of ensuring readability and maintainability of your code. We will grade your code style in all submitted code files according to the style guidelines. Namely, there are a few things you must have in each file/class/method:

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


### Additional Examples

#### Example 4: (Password length < 8)
```
$ java PasswordSecurity
Please enter a password: cse11
Password is too short
```
#### Example 5: (Strong password, no suggestion made)
```
$ java PasswordSecurity
Please enter a password: e&@!Ksn#6$vq59DV
Password strength: strong
```

#### Example 6 (Rule 4):
```
$ java PasswordSecurity
Please enter a password: StartEarlyStartOften!!
Password strength: medium
Here is a suggested stronger password: St2ar2tE2ar2ly2St2ar2tO2ft2en2!!2
```

#### Example 7 (Rules 1, 4):
```
$ java PasswordSecurity
Please enter a password: J&*&*&*&*&*&*&*
Password strength: weak
Here is a suggested stronger password: J&5*&5*&5*&5*&5*&5*&5*C5se5
```
#### Example 8 (Rules 3, 4, 5):
```
$ java PasswordSecurity
Please enter a password: qwertyuiop
Password strength: very weak
Here is a suggested stronger password: #@qw0Er0ty0ui0op0
```


## Testing
Starting with this programming assignment, there will be some testers that are initially hidden. To help you test your code, the following table shows the public and private test cases. It is your responsibility to test your program comprehensively. 

| **Test Cases** | **Public Tests**                                                                                                                                                             | **Private Tests**                                                                                                                               |
|----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| TestExamples   | `12345678`<br>`paulcao<3`<br>`PASSWORD???`<br>`cse11`<br>`e&@!Ksn#6$vq59DV`<br>`StartEarlyStartOften!!`<br>`J&*&*&*&*&*&*&*`<br>`qwertyuiop`                                                 | N/A                                                                                                                                             |
| TestShort      | `xLi7t%$`                                                                                                                                                                      | `%`<br>Empty String (directly hitting enter)                                                                                                      |
| TestStrong     | `mDWqcYF99qu4@3ojt#2&`<br>`thequ1ckbrownfoxjumpedoverthel@zyDOG`                                                                                                                 | `aaaaaaaaaaaaaaaaaaaaaaaaaaaaaZ&0`<br>`ZZZZZZ00000~~~~~~~aaaaaaaaaa`                                                               |
| TestMedium     | `password123!`<br>`PASSWORD123!`<br>`Password!`<br>`Password123`<br>`a1234567890{}`<br>`*22searz`<br>`yxw22228&`<br>`brisk88ball$`<br>`U1234567890{}`<br>`GIRAFFE311#`<br>`sand+Man`<br>`Fifteen==length` | `12345a67890{}`<br>`1234567890{}a`<br>`100bananana<`<br>`fool55!!`<br>`1234567U890{}`<br>`1234567890{}U`<br>`1234567890{}UU`<br> `!1234BREAK431`<br>`5067^BARBEQUE`<br>`zebraLuvrrr<<` <br> `twenty-fiveIsStringLength`|
| TestWeak       | `2cool4school`<br>`prettyplease!!!`<br>`A1234567`<br>`STARTEARLYSTARTOFTEN!!!`                                                                                                       | `1x000000`<br>`a!!!!!!!!`<br>`AB123456`<br>`A!@#$%^&`<br>`10+50=60`                                                                                       |
| TestVeryWeak   | `PASSWORD`                                                                                                                                                                     | `password`<br>`!?!?!?!?`<br>`9999999999999999999999999999999`                                                                                         |

## Submission
Submit the following file(s) to Gradescope by **Thursday, April 16 @ 11:59PM PDT**.
 - `PasswordSecurity.java`

Even if your code does not pass all the tests, you will still be able to submit your work to receive partial points for the tests that you passed. Make sure your code compiles in order to receive partial credit.

### How your assignment will be evaluated

* **Correctness** [95 points] You will earn points based on the autograder tests that your code passes. If the autograder tests are not able to run (e.g., your code does not compile or it does not match the specifications in this writeup), you may not earn any credit.
* **Coding Style** [5 points]
