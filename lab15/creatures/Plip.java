package creatures;
import edu.princeton.cs.algs4.ST;
import huglife.*;

import java.awt.Color;
import java.util.Map;
import java.util.List;

/** An implementation of a motile pacifist photosynthesizer.
 *  @author Josh Hug
 */
public class Plip extends Creature {

    private static final double MOVELOSS = 0.15;
    private static final double STAYGAIN = 0.2;
    private static final double MOVEPROSIBILTY = 0.5;
    private static final double energyUpperBound = 2.0;
    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    /** creates plip with energy equal to E. */
    public Plip(double e) {
        super("plip");
        r = 99;
        g = color().getGreen();
        b = 76;
        energy = e;
    }

    /** creates a plip with energy equal to 1. */
    public Plip() {
        this(1);
    }

    @Override
    public String name() {
        return "plip";
    }

    /** Should return a color with red = 99, blue = 76, and green that varies
     *  linearly based on the energy of the Plip. If the plip has zero energy,
     *  it should have a green value of 63. If it has max energy, it should
     *  have a green value of 255. The green value should vary with energy
     *  linearly in between these two extremes. It's not absolutely vital
     *  that you get this exactly correct.
     */
    public Color color() {
        g = (int) (63 + (255 - 63) * (energy / 2));
        return color(r, g, b);
    }

    /** Do nothing with C, Plips are pacifists. */
    public void attack(Creature c) {
    }

    /** Plips should lose 0.15 units of energy when moving. If you want to
     *  avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= MOVELOSS;
    }


    /** Plips gain 0.2 energy when staying due to photosynthesis. */
    public void stay() {
        energy += STAYGAIN;
        if (energy > energyUpperBound) {
            energy = energyUpperBound;
        }
    }

    /** Plips and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Plip.
     */
    public Plip replicate() {
        energy /= 2;
        return new Plip(energy);
    }

    /** Plips take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if energy >= 1, REPLICATE.
     *  3. Otherwise, if any Cloruses, MOVE with 50% probability.
     *  4. Otherwise, if nothing else, STAY
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> cloruses = getNeighborsOfType(neighbors, "cloruses");
        if (empties.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else if (energy >= 1.0) {
            Direction rpDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, rpDir);
        } else if (!cloruses.isEmpty()) {
            if (HugLifeUtils.random() < MOVEPROSIBILTY) {
                Direction mvDir = HugLifeUtils.randomEntry(empties);
                return new Action(Action.ActionType.MOVE, mvDir);
            }
        }

        return new Action(Action.ActionType.STAY);
    }

}
