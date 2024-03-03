public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double rx = xxPos - p.xxPos;
        double ry = yyPos - p.yyPos;
        double r = Math.sqrt(rx * rx + ry * ry);
        return r;
    }
    
    public double calcForceExertedBy(Planet p) {
        double r = calcDistance(p);
        double force = (G * mass * p.mass) / (r * r);
        return force;
    }

    public double calcForceExertedByX(Planet p) {
        double r = calcDistance(p);
        double x = p.xxPos - xxPos;
        double force = calcForceExertedBy(p);
        double fx = force * (x / r);
        return fx;
    }

    public double calcForceExertedByY(Planet p) {
        double r = calcDistance(p);
        double y = p.yyPos - yyPos;
        double force = calcForceExertedBy(p);
        double fy = force * (y / r);
        return fy;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double netfx = 0;
        for (Planet p: allPlanets) {
            if (this.equals(p)) {
                continue;
            } else {
                netfx += calcForceExertedByX(p);
            }
        }
        return netfx;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double netfy = 0;
        for (Planet p: allPlanets) {
            if (this.equals(p)) {
                continue;
            } else {
                netfy += calcForceExertedByY(p);
            }
        }
        return netfy;
    }

    public void update(double dt, double fX, double fY) {
        double ax = fX / mass;
        double ay = fY / mass;
        xxVel += dt * ax;
        yyVel += dt * ay;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    public void draw() {
        String path = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, path);
    }
}