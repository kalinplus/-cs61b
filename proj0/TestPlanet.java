import java.math.*;

public class TestPlanet {
    public static void main(String[] args) {
        checkForce();
    }

    /**
     *  Checks whether or not two Doubles are equal and prints the result.
     *
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  label       Label for the 'test' case
     *  @param  eps         Tolerance for the double comparison.
     */

    private static void checkEquals(double expected, double actual, String label, double eps) {
        if (Math.abs(expected - actual) <= eps * Math.max(expected, actual)) {
            System.out.println("PASS: " + label + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label + ": Expected " + expected + " and you gave " + actual);
        }
    }
    private static void checkForce() {
        System.out.println("Checking pairwise force...");
        Planet p1 = new Planet(0.0, 0.0, 0.0, 0.0, 1.0, "jupiter.gif");
        Planet p2 = new Planet(1.0, 0.0, 0.0, 0.0, 1.0, "jupiter.gif");
        checkEquals(6.67e-11, p1.calcForceExertedBy(p2), "calcForceExertBy()", 0.01);
    }
}