/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains the Leopard class for CSE 11 PA7. Leopards share
 * a static confidence value that grows with each win and shrinks with
 * each loss, and the confidence drives both their eating and attack
 * choice.
 */

import java.awt.Color;

/**
 * Represents a Leopard critter. Leopards prefer to move toward food or
 * Starfish, eat probabilistically based on shared confidence, and pick
 * their attacks based on the opponent and confidence level.
 */
public class Leopard extends Feline {
    protected static int confidence = 0;

    private static final String SPECIES_NAME = "Lpd";
    private static final String FOOD = ".";
    private static final String STARFISH_NAME = "Patrick";
    private static final String TURTLE_NAME = "Tu";
    private static final int CONFIDENCE_MAX = 10;
    private static final int CONFIDENCE_MIN = 0;
    private static final int CONFIDENCE_THRESHOLD = 5;
    private static final int EAT_PERCENT_PER_CONFIDENCE = 10;
    private static final int PERCENT_MAX = 100;

    /**
     * Default constructor for a Leopard.
     */
    public Leopard() {
        super();
        displayName = SPECIES_NAME;
    }

    /**
     * @return the Leopard's color, red
     */
    @Override
    public Color getColor() {
        return Color.RED;
    }

    /**
     * Leopards check their four neighbors in NORTH, SOUTH, EAST, WEST
     * order. If any has food or a Starfish, the Leopard moves toward
     * the first such neighbor. Otherwise it picks a random direction.
     *
     * @return the Direction the Leopard should move
     */
    @Override
    public Direction getMove() {
        // Scan neighbors in order and grab the first food or Starfish
        Direction[] dirs = {Direction.NORTH, Direction.SOUTH,
                Direction.EAST, Direction.WEST};
        for (int i = 0; i < dirs.length; i++) {
            String neighbor = info.getNeighbor(dirs[i]);
            if (neighbor.equals(FOOD) || neighbor.equals(STARFISH_NAME)) {
                return dirs[i];
            }
        }
        // No target around, just go a random direction
        return dirs[random.nextInt(dirs.length)];
    }

    /**
     * Leopards eat with a (confidence * 10)% probability.
     *
     * @return true with the computed probability, false otherwise
     */
    @Override
    public boolean eat() {
        // chance is the percent likelihood out of 100
        int chance = confidence * EAT_PERCENT_PER_CONFIDENCE;
        return random.nextInt(PERCENT_MAX) < chance;
    }

    /**
     * Winning raises every Leopard's shared confidence by one, up to
     * the maximum.
     */
    @Override
    public void win() {
        // Bump shared confidence but stop at the cap
        if (confidence < CONFIDENCE_MAX) {
            confidence++;
        }
    }

    /**
     * Losing lowers every Leopard's shared confidence by one, down to
     * the minimum.
     */
    @Override
    public void lose() {
        // Drop shared confidence but stay at zero or above
        if (confidence > CONFIDENCE_MIN) {
            confidence--;
        }
    }

    /**
     * Leopards POUNCE Turtles and POUNCE when confidence is high.
     * Otherwise they pick an attack with generateAttack.
     *
     * @param opponent the display string of the opposing critter
     * @return the chosen Attack
     */
    @Override
    public Attack getAttack(String opponent) {
        // Always pounce Turtles or when feeling very confident
        if (opponent.equals(TURTLE_NAME)
                || confidence > CONFIDENCE_THRESHOLD) {
            return Attack.POUNCE;
        }
        return generateAttack(opponent);
    }

    /**
     * Picks an attack randomly from POUNCE, SCRATCH, ROAR, except that
     * Starfish are always forfeited to.
     *
     * @param opponent the display string of the opposing critter
     * @return the chosen Attack
     */
    protected Attack generateAttack(String opponent) {
        // Patrick gets a free pass
        if (opponent.equals(STARFISH_NAME)) {
            return Attack.FORFEIT;
        }
        // Otherwise pick randomly from the three real attacks
        Attack[] attacks = {Attack.POUNCE, Attack.SCRATCH, Attack.ROAR};
        return attacks[random.nextInt(attacks.length)];
    }

    /**
     * Resets shared confidence back to zero when the world is reset.
     */
    @Override
    public void reset() {
        confidence = 0;
    }
}
