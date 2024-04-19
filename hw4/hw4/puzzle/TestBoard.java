package hw4.puzzle;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestBoard {
    // temp test for rxyTo1d
//    @Test
//    public void testrxyTo1d() {
//        int[][] tiles = new int[4][4];
//        Board b = new Board(tiles);
//
//        Board.Pos p1 = b.rxyTo1d(1);
//        Board.Pos p2 = b.rxyTo1d(7);
//        Board.Pos p3 = b.rxyTo1d(14);
//        assertEquals(0, p1.x);
//        assertEquals(0, p1.y);
//        assertEquals(1, p2.x);
//        assertEquals(2, p2.y);
//        assertEquals(3, p3.x);
//        assertEquals(1, p3.y);
//    }

    @Test
    public void verifyImmutability() {
        int r = 2;
        int c = 2;
        int[][] x = new int[r][c];
        int cnt = 0;
        for (int i = 0; i < r; i += 1) {
            for (int j = 0; j < c; j += 1) {
                x[i][j] = cnt;
                cnt += 1;
            }
        }
        Board b = new Board(x);
        assertEquals("Your Board class is not being initialized with the right values.", 0, b.tileAt(0, 0));
        assertEquals("Your Board class is not being initialized with the right values.", 1, b.tileAt(0, 1));
        assertEquals("Your Board class is not being initialized with the right values.", 2, b.tileAt(1, 0));
        assertEquals("Your Board class is not being initialized with the right values.", 3, b.tileAt(1, 1));

        x[1][1] = 1000;
        assertEquals("Your Board class is mutable and you should be making a copy of the values in the passed tiles array. Please see the FAQ!", 3, b.tileAt(1, 1));
    }
} 
