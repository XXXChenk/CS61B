public class NBody {
    public static double readRadius(String filename){
        In in = new In(filename);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String filename){
        In in = new In(filename);
        int n = in.readInt();
        in.readDouble();
        Planet[] P = new Planet[n];
        for (int i=0;i<n;i++){
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            P[i] = new Planet(xP, yP, xV, yV, m, img);
        }
        return P;
    }

    public static void main (String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double R = readRadius(filename);
        Planet[] P = readPlanets(filename);
        StdDraw.setScale(-R, R);
        StdDraw.enableDoubleBuffering();
        double time = 0;
        while (time < T)  {
            double[] yForces = new double[P.length];
            double[] xForces = new double[P.length];


            for (int i = 0; i < P.length; i++) {
                    xForces[i] = P[i].calcNetForceExertedByX(P);
                    yForces[i] = P[i].calcNetForceExertedByY(P);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p : P) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            for (int i = 0; i < P.length; i++) {
                P[i].update(dt, xForces[i], yForces[i]);
            }
            time += dt;
        }
        
        StdOut.printf("%d\n", P.length);
        StdOut.printf("%.2e\n", R);
        for (int i = 0; i < P.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    P[i].xxPos, P[i].yyPos, P[i].xxVel,
                    P[i].yyVel, P[i].mass, P[i].imgFileName);
        }
    }
}


