import java.util.concurrent.RecursiveTask;

public class Planet {
    public static final double G = 6.67e-11;

    /** double letters, e.g. xxPos rather than xPos is to reduce the chance of typos.*/
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /** Creates a copy */
    public Planet(Planet P) {
        xxPos = P.xxPos;
        yyPos = P.yyPos;
        xxVel = P.xxVel;
        yyVel = P.yyVel;
        mass = P.mass;
        imgFileName = P.imgFileName;
    }

    /** Calculate the distance between two Planets.*/
    public double calcDistance(Planet P) {
        double dx = this.xxPos - P.xxPos;
        double dy = this.yyPos - P.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }


    /** Calculate the force exerted on this planet by the given planet.*/
    public double calcForceExertedBy(Planet P) {
        double r = this.calcDistance(P);
        return G * this.mass * P.mass / (r * r);
    }

    /** Calculate the force exerted in the X direction.*/
    public double calcForceExertedByX(Planet P) {
        double F = this.calcForceExertedBy(P);
        return F * (P.xxPos- this.xxPos)/calcDistance(P);
    }

    /** Calculate the force exerted in the Y direction. */
    public double calcForceExertedByY(Planet P) {
        double F = this.calcForceExertedBy(P);
        return F * (P.yyPos- this.yyPos)/calcDistance(P);
    }

    /** Calculate the net X force exerted by all planets in the array upon the current Planet. */
    public double calcNetForceExertedByX(Planet[] allPlanets){
        double F = 0;
        for(Planet P : allPlanets){
            if (this.equals(P)){
                continue;
            }
            F += calcForceExertedByX(P);
        }
        return F;
    }

    /** Calculate the net Y force exerted by all planets in the array upon the current Planet. */
    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double F = 0;
        for (Planet P : allPlanets) {
            if (this.equals(P)) {
                continue;
            }
            F += calcForceExertedByY(P);
        }
        return F;
    }

    /** Updates the planet’s position and velocity instance variables. */
    public void update(double dt, double fX, double fY) {
        double aX = fX/this.mass;
        double aY = fY/this.mass;
        this.xxVel += dt * aX;
        this.yyVel += dt * aY;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }
}

