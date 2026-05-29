/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains the Elephant class for CSE 11 PA7. All Elephants
 * share a single goal coordinate they walk toward, always eat, and
 * level up by two each time they mate.
 */

import java.awt.Color;

/**
 * Represents an Elephant critter. All Elephants share a goal coordinate
 * and walk toward it along whichever axis is farther from the goal,
 * picking a new random goal once they arrive.
 */
public class Elephant extends Critter {
    protected static int goalX;
    protected static int goalY;

    private static final String SPECIES_NAME = "El";
    private static final int MATE_LEVEL_INCREMENT = 2;

    /**
     * Default constructor for an Elephant. Initializes the shared goal
     * to (0, 0).
     */
    public Elephant() {
        super(SPECIES_NAME);
        goalX = 0;
        goalY = 0;
    }

    /**
     * @return the Elephant's color, gray
     */
    @Override
    public Color getColor() {
        return Color.GRAY;
    }

    /**
     * Elephants move toward the shared goal along whichever axis they
     * are farther from the goal. If the distances are equal, they move
     * along the x-axis. If they have reached the goal, a new random
     * goal is chosen before computing the move.
     *
     * @return the Direction the Elephant should move
     */
    @Override
    public Direction getMove() {
        int x = info.getX();
        int y = info.getY();
        // If we have arrived, pick a fresh random goal
        if (x == goalX && y == goalY) {
            goalX = random.nextInt(info.getWidth());
            goalY = random.nextInt(info.getHeight());
        }
        int dx = goalX - x;
        int dy = goalY - y;
        // Move along the axis where we are farther from the goal
        // Ties go to the x axis
        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx > 0) {
                return Direction.EAST;
            }
            return Direction.WEST;
        }
        if (dy > 0) {
            return Direction.SOUTH;
        }
        return Direction.NORTH;
    }

    /**
     * Elephants are always hungry.
     *
     * @return true
     */
    @Override
    public boolean eat() {
        return true;
    }

    /**
     * Mating raises this Elephant's level by two.
     */
    @Override
    public void mate() {
        incrementLevel(MATE_LEVEL_INCREMENT);
    }

    /**
     * Resets the shared goal back to (0, 0).
     */
    @Override
    public void reset() {
        goalX = 0;
        goalY = 0;
    }
}
