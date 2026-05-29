/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains the Starfish class for CSE 11 PA7. A Starfish is
 * a stationary critter named Patrick that never moves and forfeits all
 * fights.
 */

import java.awt.Color;

/**
 * Represents a Starfish critter named Patrick. Patrick does not move
 * from his rock, does not eat, and forfeits when attacked.
 */
public class Starfish extends Critter {
    private static final String SPECIES_NAME = "Patrick";

    /**
     * Default constructor - creates critter with name Patrick.
     */
    public Starfish() {
        super(SPECIES_NAME);
    }

    /**
     * Patrick stays put under his rock.
     *
     * @return Direction.CENTER (no movement)
     */
    @Override
    public Direction getMove() {
        return Direction.CENTER;
    }

    /**
     * Returns the color of the Starfish.
     *
     * @return Color pink
     */
    @Override
    public Color getColor() {
        return Color.PINK;
    }
}
