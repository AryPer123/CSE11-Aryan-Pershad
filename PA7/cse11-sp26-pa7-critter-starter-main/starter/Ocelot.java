/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains the Ocelot class for CSE 11 PA7. An Ocelot is a
 * Leopard variant that uses a more targeted attack pattern: SCRATCH
 * against other felines, POUNCE against everything else.
 */

import java.awt.Color;

/**
 * Represents an Ocelot critter. Inherits Leopard movement, eating, and
 * the outer getAttack rules, but overrides generateAttack to scratch
 * felines and pounce everything else.
 */
public class Ocelot extends Leopard {
    private static final String SPECIES_NAME = "Oce";
    private static final String LION_NAME = "Lion";
    private static final String LION_SLEEPING_NAME = "noiL";
    private static final String FELINE_NAME = "Fe";
    private static final String LEOPARD_NAME = "Lpd";

    /**
     * Default constructor for an Ocelot.
     */
    public Ocelot() {
        super();
        displayName = SPECIES_NAME;
    }

    /**
     * @return the Ocelot's color, light gray
     */
    @Override
    public Color getColor() {
        return Color.LIGHT_GRAY;
    }

    /**
     * Ocelots scratch other felines (Lion in either name form, Feline,
     * Leopard) and pounce all other opponents.
     *
     * @param opponent the display string of the opposing critter
     * @return Attack.SCRATCH against felines, Attack.POUNCE otherwise
     */
    @Override
    protected Attack generateAttack(String opponent) {
        // Scratch any feline cousin, including a sleeping Lion
        if (opponent.equals(LION_NAME)
                || opponent.equals(LION_SLEEPING_NAME)
                || opponent.equals(FELINE_NAME)
                || opponent.equals(LEOPARD_NAME)) {
            return Attack.SCRATCH;
        }
        return Attack.POUNCE;
    }
}
