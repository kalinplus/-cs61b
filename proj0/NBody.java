public class NBody {
    public static double readRadius(String file) {
        In in = new In(file);
        int amount = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String file) {
        In in = new In(file);
        int amount = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[amount];
        for (int i = 0; amount > 0; amount--, i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            Planet p = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
            planets[i] = p;
        }
        return planets;
    }

    public static void main(String[] args) {
        // get time, radius and planets
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double uniRadius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        int len = planets.length;

        // Draw static pictures
        StdDraw.setScale(-uniRadius, uniRadius);
        StdDraw.picture(0, 0, "images/starfield.jpg");
        for (Planet p: planets) {
            p.draw();
        }

        // Draw animation
        StdDraw.enableDoubleBuffering();
        for (double time = 0; time < T; time += dt) {
            double[] xForces = new double[len];
            double[] yForces = new double[len];
            for (int i = 0; i < len; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < len; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.setScale(-uniRadius, uniRadius);
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p: planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        // print final state
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", UniRadius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                          planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }
}
