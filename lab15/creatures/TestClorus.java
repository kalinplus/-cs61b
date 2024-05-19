package creatures;

import org.junit.Test;
import java.awt.Color;
import java.util.HashMap;

import huglife.Action;
import huglife.Direction;
import huglife.Impassible;
import huglife.Occupant;
import huglife.Empty;
import static org.junit.Assert.*;

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.005);
        c.stay();
        assertEquals(1.98, c.energy(), 0.005);
    }
    @Test
    public void testChoose() {
        Clorus c1 = new Clorus(2);
        HashMap<Direction, Occupant> surrounded1 = new HashMap<>();
        surrounded1.put(Direction.TOP, new Impassible());
        surrounded1.put(Direction.BOTTOM, new Impassible());
        surrounded1.put(Direction.LEFT, new Impassible());
        surrounded1.put(Direction.RIGHT, new Impassible());

        Action actual1 = c1.chooseAction(surrounded1);
        Action expected1 = new Action(Action.ActionType.STAY);
        assertEquals(expected1, actual1);

//        Clorus c2 = new Clorus(2);
//        HashMap<Direction, Occupant> surround2 = new HashMap<>();
//        surround2.put(Direction.TOP, new Empty());
//        surround2.put(Direction.BOTTOM, new Empty());
//        surround2.put(Direction.LEFT, new Empty());
//        surround2.put(Direction.RIGHT, new Plip(2));
//
//        Action actual2 = c2.chooseAction(surround2);
//        Action expected2 = new Action(Action.ActionType.ATTACK, Direction.RIGHT);
//        assertEquals(expected2, actual2);
//        assertEquals(4.0, c2.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(2);
        Clorus babyc = c.replicate();
        assertNotSame(c, babyc);
        assertEquals(1.0, c.energy(), 0.01);
        assertEquals(1.0, babyc.energy(), 0.01);
    }
}
