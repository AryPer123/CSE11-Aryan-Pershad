/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains the Feline class for CSE 11 PA7. A Feline picks a
 * random direction each five moves, eats every third food encountered,
 * and always pounces in a fight.
 */

/**
 * Represents a Feline critter. Felines move in a randomly chosen
 * direction for five turns at a time, eat only every third encounter
 * with food, and always attack with POUNCE.
 */
public class Feline extends Critter {
    private static final String SPECIES_NAME = "Fe";
    private static final int MOVES_PER_DIRECTION = 5;
    private static final int EAT_FREQUENCY = 3;

    private int moveCount;
    private int eatCount;
    private Direction currDir;

    /**
     * Default constructor for a Feline. Sets up counters so the first
     * call to getMove picks a random direction and the first call to
     * eat returns false.
     */
    public Feline() {
        super(SPECIES_NAME);
        this.moveCount = 0;
        this.eatCount = 0;
        this.currDir = null;
    }

    /**
     * Picks a new random direction (excluding CENTER) every fifth move
     * and continues moving in that direction.
     *
     * @return the chosen Direction
     */
    @Override
    public Direction getMove() {
        // Pick a new direction on the first move and every 5th move
        if (moveCount == 0 || moveCount == MOVES_PER_DIRECTION) {
            Direction[] dirs = {Direction.NORTH, Direction.SOUTH,
                    Direction.EAST, Direction.WEST};
            currDir = dirs[random.nextInt(dirs.length)];
            moveCount = 0;
        }
        moveCount++;
        return currDir;
    }

    /**
     * Felines eat every third encounter with food.
     *
     * @return true on every third call, false otherwise
     */
    @Override
    public boolean eat() {
        // Eat only when the count hits a multiple of 3
        eatCount++;
        return eatCount % EAT_FREQUENCY == 0;
    }

    /**
     * Felines always attack with POUNCE.
     *
     * @param opponent the display string of the attacking critter
     * @return Attack.POUNCE
     */
    @Override
    public Attack getAttack(String opponent) {
        return Attack.POUNCE;
    }
}
