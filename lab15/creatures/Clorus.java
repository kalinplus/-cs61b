package creatures;
import huglife.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class Clorus extends Creature {
    private static final double moveLoss = 0.03;
    private static final double stayGain = 0.01;
    private int r;
    private int g;
    private int b;

    public Clorus(double e) {
        super("Clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    @Override
    public void move() {
        energy -= moveLoss;
    }
    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }
    @Override
    public void stay() {
        energy += stayGain;
    }

    @Override
    public Clorus replicate() {
        energy /= 2;
        return new Clorus(energy);
    }

    public Color color() {
        return new Color(r, g, b);
    }

    @Override
    public String name() {
        return "Clorus";
    }

    /**
     * Cloruses should obey exactly the following behavioral rules:
     *
     * 1. If there are no empty squares,
     *    the Clorus will STAY (even if there are Plips nearby they could attack).
     * 2. Otherwise, if any Plips are seen,
     *    the Clorus will ATTACK one of them randomly.
     * 3. Otherwise, if the Clorus has energy greater than or equal to one,
     *    it will REPLICATE to a random empty square.
     * 4. Otherwise, the Clorus will MOVE to a random empty square.
     *
     * @param neighbors neighbors of Clorus, determine its behaviour
     * @return action
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (empties.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else if (!plips.isEmpty()) {
            Direction atDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, atDir);
        } else if (energy >= 1.0) {
            Direction rpDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, rpDir);
        }
        Direction mvDir = HugLifeUtils.randomEntry(empties);
        return new Action(Action.ActionType.MOVE, mvDir);
    }
}
