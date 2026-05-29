/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains the Lion class for CSE 11 PA7. A Lion walks in a
 * clockwise square pattern, becomes hungry after winning a fight, and
 * sleeps with a reversed display name.
 */

import java.awt.Color;

/**
 * Represents a Lion critter. Lions walk in a clockwise square pattern,
 * eat only after winning a fight, and have their display name reversed
 * while sleeping.
 */
public class Lion extends Feline {
    private static final String LION_NAME = "Lion";
    private static final String SLEEPING_NAME = "noiL";
    private static final int MOVES_PER_SIDE = 5;
    private static final int SIDES_OF_SQUARE = 4;
    private static final int PHASE_EAST = 0;
    private static final int PHASE_SOUTH = 1;
    private static final int PHASE_WEST = 2;

    private int fightWins;
    private int squareStep;

    /**
     * Default constructor for a Lion.
     */
    public Lion() {
        super();
        displayName = LION_NAME;
        this.fightWins = 0;
        this.squareStep = 0;
    }

    /**
     * @return the Lion's color, yellow
     */
    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    /**
     * Lions move in a clockwise square: 5 east, 5 south, 5 west,
     * 5 north, then repeat.
     *
     * @return the next Direction in the square pattern
     */
    @Override
    public Direction getMove() {
        // Each side of the square lasts 5 moves over 4 sides total
        int phase = squareStep / MOVES_PER_SIDE;
        squareStep = (squareStep + 1) % (MOVES_PER_SIDE * SIDES_OF_SQUARE);
        if (phase == PHASE_EAST) {
            return Direction.EAST;
        }
        if (phase == PHASE_SOUTH) {
            return Direction.SOUTH;
        }
        if (phase == PHASE_WEST) {
            return Direction.WEST;
        }
        return Direction.NORTH;
    }

    /**
     * A Lion eats only when it is hungry, which happens after winning
     * at least one fight since the last meal or nap. Eating clears the
     * hunger.
     *
     * @return true if the Lion has won a fight since last eating or
     *         sleeping, false otherwise
     */
    @Override
    public boolean eat() {
        // Only eat when hungry from a win, and eating clears the hunger
        if (fightWins > 0) {
            fightWins = 0;
            return true;
        }
        return false;
    }

    /**
     * When the Lion sleeps, its win counter resets and its display name
     * is reversed.
     */
    @Override
    public void sleep() {
        fightWins = 0;
        displayName = SLEEPING_NAME;
    }

    /**
     * When the Lion wakes up, its display name is restored.
     */
    @Override
    public void wakeup() {
        displayName = LION_NAME;
    }

    /**
     * Tracks fight wins so the Lion becomes hungry.
     */
    @Override
    public void win() {
        fightWins++;
    }
}
