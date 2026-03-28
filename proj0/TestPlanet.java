/**
 * Aggregated tests for the Planet class.
 */
public class TestPlanet {
    private static int passCount = 0;
    private static int failCount = 0;

    public static void main(String[] args) {
        testConstructors();
        testCalcDistance();
        testCalcForceExertedBy();
        testCalcForceExertedByXY();
        testCalcNetForceExertedByXY();
        testUpdate();

        System.out.println();
        System.out.println("Total PASS: " + passCount);
        System.out.println("Total FAIL: " + failCount);
    }

    private static void checkApprox(double actual, double expected, String label, double eps) {
        double tolerance = eps * Math.max(1.0, Math.max(Math.abs(expected), Math.abs(actual)));
        if (Math.abs(expected - actual) <= tolerance) {
            passCount += 1;
            System.out.println("PASS: " + label + " | expected=" + expected + ", actual=" + actual);
        } else {
            failCount += 1;
            System.out.println("FAIL: " + label + " | expected=" + expected + ", actual=" + actual);
        }
    }

    private static void checkStringEquals(String actual, String expected, String label) {
        if (expected.equals(actual)) {
            passCount += 1;
            System.out.println("PASS: " + label + " | expected=" + expected + ", actual=" + actual);
        } else {
            failCount += 1;
            System.out.println("FAIL: " + label + " | expected=" + expected + ", actual=" + actual);
        }
    }

    private static void testConstructors() {
        System.out.println("=== testConstructors ===");
        Planet p = new Planet(1.0, 2.0, 3.0, 4.0, 5.0, "jupiter.gif");
        checkApprox(p.xxPos, 1.0, "ctor xxPos", 1e-12);
        checkApprox(p.yyPos, 2.0, "ctor yyPos", 1e-12);
        checkApprox(p.xxVel, 3.0, "ctor xxVel", 1e-12);
        checkApprox(p.yyVel, 4.0, "ctor yyVel", 1e-12);
        checkApprox(p.mass, 5.0, "ctor mass", 1e-12);
        checkStringEquals(p.imgFileName, "jupiter.gif", "ctor imgFileName");

        Planet copy = new Planet(p);
        checkApprox(copy.xxPos, p.xxPos, "copy xxPos", 1e-12);
        checkApprox(copy.yyPos, p.yyPos, "copy yyPos", 1e-12);
        checkApprox(copy.xxVel, p.xxVel, "copy xxVel", 1e-12);
        checkApprox(copy.yyVel, p.yyVel, "copy yyVel", 1e-12);
        checkApprox(copy.mass, p.mass, "copy mass", 1e-12);
        checkStringEquals(copy.imgFileName, p.imgFileName, "copy imgFileName");
    }

    private static void testCalcDistance() {
        System.out.println("=== testCalcDistance ===");
        Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p2 = new Planet(2.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p3 = new Planet(4.0, 5.0, 3.0, 4.0, 5.0, "jupiter.gif");

        checkApprox(p1.calcDistance(p2), 1.0, "calcDistance p1->p2", 0.01);
        checkApprox(p1.calcDistance(p3), 5.0, "calcDistance p1->p3", 0.01);
    }

    private static void testCalcForceExertedBy() {
        System.out.println("=== testCalcForceExertedBy ===");
        Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p2 = new Planet(2.0, 1.0, 3.0, 4.0, 4e11, "jupiter.gif");
        Planet p3 = new Planet(4.0, 5.0, 3.0, 4.0, 5.0, "jupiter.gif");

        checkApprox(p1.calcForceExertedBy(p2), 133.4, "calcForceExertedBy p1<-p2", 0.01);
        checkApprox(p1.calcForceExertedBy(p3), 6.67e-11, "calcForceExertedBy p1<-p3", 0.01);
    }

    private static void testCalcForceExertedByXY() {
        System.out.println("=== testCalcForceExertedByXY ===");
        Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p2 = new Planet(2.0, 1.0, 3.0, 4.0, 4e11, "jupiter.gif");
        Planet p3 = new Planet(4.0, 5.0, 3.0, 4.0, 5.0, "jupiter.gif");

        checkApprox(p1.calcForceExertedByX(p2), 133.4, "calcForceExertedByX p1<-p2", 0.01);
        checkApprox(p1.calcForceExertedByX(p3), 4.002e-11, "calcForceExertedByX p1<-p3", 0.01);
        checkApprox(p1.calcForceExertedByY(p2), 0.0, "calcForceExertedByY p1<-p2", 0.01);
        checkApprox(p1.calcForceExertedByY(p3), 5.336e-11, "calcForceExertedByY p1<-p3", 0.01);
    }

    private static void testCalcNetForceExertedByXY() {
        System.out.println("=== testCalcNetForceExertedByXY ===");
        Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p2 = new Planet(2.0, 1.0, 3.0, 4.0, 4e11, "jupiter.gif");
        Planet p3 = new Planet(4.0, 5.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p4 = new Planet(3.0, 2.0, 3.0, 4.0, 5.0, "jupiter.gif");

        Planet[] planetsWithoutSelf = {p2, p3, p4};
        checkApprox(p1.calcNetForceExertedByX(planetsWithoutSelf), 133.4,
                "calcNetForceExertedByX without self", 0.01);
        checkApprox(p1.calcNetForceExertedByY(planetsWithoutSelf), 0.0,
                "calcNetForceExertedByY without self", 0.01);

        Planet[] planetsWithSelf = {p1, p2, p3, p4};
        checkApprox(p1.calcNetForceExertedByX(planetsWithSelf), 133.4,
                "calcNetForceExertedByX with self", 0.01);
        checkApprox(p1.calcNetForceExertedByY(planetsWithSelf), 0.0,
                "calcNetForceExertedByY with self", 0.01);
    }

    private static void testUpdate() {
        System.out.println("=== testUpdate ===");
        Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        p1.update(2.0, 1.0, -0.5);

        checkApprox(p1.xxVel, 3.4, "update xxVel", 0.01);
        checkApprox(p1.yyVel, 3.8, "update yyVel", 0.01);
        checkApprox(p1.xxPos, 7.8, "update xxPos", 0.01);
        checkApprox(p1.yyPos, 8.6, "update yyPos", 0.01);
    }
}

