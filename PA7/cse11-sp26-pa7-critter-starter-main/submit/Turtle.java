/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains the Turtle class for CSE 11 PA7. A Turtle always
 * moves west, eats only when no hostile neighbors are around, and
 * roars half of the time when attacked.
 */

import java.awt.Color;

/**
 * Represents a Turtle critter. Turtles move west each turn, are
 * cautious eaters, and split their attacks between roar and forfeit.
 */
public class Turtle extends Critter {
    private static final String SPECIES_NAME = "Tu";
    private static final String EMPTY_SQUARE = " ";
    private static final String FOOD = ".";
    private static final int ATTACK_CHOICES = 2;

    /**
     * Default constructor for a Turtle.
     */
    public Turtle() {
        super(SPECIES_NAME);
    }

    /**
     * @return the Turtle's color, green
     */
    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    /**
     * @return Direction.WEST every turn
     */
    @Override
    public Direction getMove() {
        return Direction.WEST;
    }

    /**
     * Turtles only eat when none of their four neighbors are hostile.
     * A neighbor is hostile if it is not empty, food, or another Turtle.
     *
     * @return true if the Turtle should eat the food, false otherwise
     */
    @Override
    public boolean eat() {
        // Check each of the four neighbors for hostiles
        Direction[] dirs = {Direction.NORTH, Direction.SOUTH,
                Direction.EAST, Direction.WEST};
        for (int i = 0; i < dirs.length; i++) {
            String neighbor = info.getNeighbor(dirs[i]);
            // A neighbor is safe only if it is empty, food, or a Turtle
            if (!neighbor.equals(EMPTY_SQUARE)
                    && !neighbor.equals(FOOD)
                    && !neighbor.equals(SPECIES_NAME)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Turtles roar half of the time and forfeit the other half.
     *
     * @param opponent the display string of the attacking critter
     * @return Attack.ROAR or Attack.FORFEIT
     */
    @Override
    public Attack getAttack(String opponent) {
        // Coin flip: roar half of the time, forfeit the other half
        if (random.nextInt(ATTACK_CHOICES) == 0) {
            return Attack.ROAR;
        }
        return Attack.FORFEIT;
    }
}
