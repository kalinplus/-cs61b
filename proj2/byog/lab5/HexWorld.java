package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
import java.util.Random;

/**
 * Draws a world consisting of 19 hexagon regions, and all of them consist of a larger hexagon
 * 1. A hexagon is a chunk of tiles with a special pattern, and each row is determined by
 * a. xPos
 * b. length of row
 *
 * 2. write helper method to find the xPos and length of each row. These is easy to test,
 * while they are fundenmental, so just write it!
 *
 * 3. write helper method to add a row, with calculated x and len
 *
 * 4. Summarize above helper methods to wrtie addHexagon method to draw it. Because
 * it is hard to test, so we just use main() to see if its result is proper
 *
 * 5. change x and y into position so that it is easier get start position of other hexagon
 *
 * 6. get the start position of the bottom hexagon with distance d. Return the right,
 * for the left, minus the x. Also test this for its simplicity and importance
 *
 * 7. add a column of hexagons, starting from the position get in 6.
 *
 * 5. Use column adder in 7 to draw a tesselation, 19 hexagons
 */
public class HexWorld {

    /**
     * Draw a tesselation of 19 hexagons, all of them form a larger hexagon
     * @param world background of our hexagons
     * @param t tile to fill in the hexagons
     * @param o origin postion of bottom-most hexagon
     * @param s size of each hexagon
     * @param n number of hexagon in the middle column
     */
    public static void addTesselationHexagons(TETile[][] world, TETile t, Position o, int s, int n) {
        // magic number is 3 so that we can draw 5 columns, 19 hexagons in total
        int magic = 3;
        int i = 0;
        while (i < magic) {
            Position pi = getHexStartPos(o, i, s);
            addColumnHexs(pi, n, s, world, t);
            // symmetical to central column
            if (i != 0) {
                Position spi = new Position(2 * o.x - pi.x ,pi.y);
                addColumnHexs(spi, n, s, world, t);
            }
            i += 1;
            n -= 1;
        }
    }
    /**
     * get the start position of hexagon that has distance of d
     * @param origin start position of most-bottom origin hexagon
     * @param d numbers of distance of hexagon with origin. E.g, for two adjacent hexagon is 1
     * @param s size of hexagon
     * @return
     */
    private static Position getHexStartPos(Position origin, int d, int s) {
         int absXOffset = d * (2 * s - 1);
         int yOffset = d * s;
         Position right = new Position(origin.x + absXOffset, origin.y + yOffset);
         return right;
    }

    /**
     * add a column of hexagons, n in total
     * @param p start position of most-bottom hexagon
     * @param n numbers of hexagons to add in this column
     * @param s size of each hexagon
     */
    private static void addColumnHexs(Position p, int n, int s, TETile[][] world, TETile t) {
        int h = 2 * s;
        for (int i = 0; i < n; i += 1) {
            Position pi = new Position(p.x, p.y + i * h);
            addHexagon(pi, s, world, t);
        }
    }

    /**
     *
     * @param p position of start point
     * @param s size of hexagon
     * @param world background for drawing hexagon
     * @param t tile to fill in the hexagon
     */
    public static void addHexagon(Position p, int s, TETile[][] world, TETile t) {
        for (int i = 0; i < 2 * s; i += 1) {
            int xOffset = getXOffset(s, i);
            int len = getRowLength(s, i);
            Position row = new Position(p.x + xOffset, p.y + i);
            addRow(world, t, row, len);
        }
    }
    /**
     * Helper class to store the start position of each hexagon
     */
    private static class Position {
        private int x;
        private int y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    /**
     * Calculate the xPos of row i
     * @param s size of hexagon
     * @param i row number, assuming the bottom is 0
     * @return
     */
    private static int getXOffset(int s, int i) {
        int effI = i;
        if (i >= s) {
            effI = 2 * s - 1 - effI;
        }
        return -effI;
    }

    /**
     * Calculate row length of row i
     * @param s size of hexagon
     * @param i row number, assuming the bottom is 0
     * @return
     */
    private static int getRowLength(int s, int i) {
        int effI = i;
        if (i >= s) {
            effI = 2 * s - 1 - effI;
        }
        return s + 2 * effI;
    }

    /**
     * Draw a row of hexagon
     * @param world background of drawing
     * @param t tile used to fill the hexagon
     * @param p start position
     * @param len length of this row
     */
    private static void addRow(TETile[][] world, TETile t, Position p, int len) {
        for (int xi = 0; xi < len; xi += 1) {
            Random RANDOM = new Random();
            world[p.x + xi][p.y] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    @Test
    public void testgetXpos() {
        assertEquals(-2, getXOffset(3, 2));
        assertEquals(-2, getXOffset(3, 3));
        assertEquals(-1, getXOffset(3, 4));
    }

    @Test
    public void testgetRowLength() {
        assertEquals(3, getRowLength(3, 0));
        assertEquals(7, getRowLength(3, 2));
        assertEquals(7, getRowLength(3, 3));
        assertEquals(5, getRowLength(3, 4));
    }

    @Test
    public void testgetHexStartPos() {
        Position distance1 = new Position(5, 3);
        Position distance2 = new Position(10, 6);
        Position origin = new Position(0, 0);
        assertEquals(distance1.x, getHexStartPos(origin, 1, 3).x);
        assertEquals(distance1.y, getHexStartPos(origin, 1, 3).y);
        assertEquals(distance2.x, getHexStartPos(origin, 2, 3).x);
        assertEquals(distance2.y, getHexStartPos(origin, 2, 3).y);
    }
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    public static void main(String[] args) {
       TERenderer ter = new TERenderer();
       ter.initialize(WIDTH, HEIGHT);

       TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING; // Fill with a default tile, assumed to exist in Tileset
            }
        }

        Position o = new Position(27, 0);
        int s = 3;
        int n = 5;
        TETile t = new TETile('@', Color.GREEN, Color.WHITE, "Grasy Plain");
        Position tmp1 = new Position(13, 1);
        addTesselationHexagons(world, t, o, s, n);
        ter.renderFrame(world);
    }
}
