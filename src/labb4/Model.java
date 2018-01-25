package labb4;

import java.lang.Math;

public class Model {
    private Particle[] particles;
    private double L = 0.01;

    //positions are given as alternating x and y coordinates for each particle.
    //We update it every time positions are updated.
    private static double[] particlePositions;

    public Model(int noOfParticles) {
        particlePositions = new double[2*noOfParticles];
        particles = new Particle[noOfParticles];

        for (int i = 0; i < noOfParticles; i++) {
            particles[i] = new Particle();
            particlePositions[2*i] = particles[i].x;
            particlePositions[2*i + 1] = particles[i].y;
        }

    }
    //Updates positions and particlePositions array.
    public void updateAllPositions() {
        int i = 0;

        for (Particle particle:particles) {
            //Updates position when particle.stuck is not true.
            particle.updatePosition();

            //Particle positions are given from 0 to 1 and then scaled to the
            //width and height of the window, so if x or y is greater than 1
            //the particle is outside the window.
            if (particle.x >= 1 || particle.y >= 1) {

                //This checks which one of the two coordinates are outside the window and sets that
                //coordinate to 1.
                particlePositions[2*i] = java.lang.Math.min(1, particle.x);
                particlePositions[2*i + 1] = java.lang.Math.min(1, particle.y);

                particle.stuck = true;
                particle.color = "red";
            }

            else if (particle.x <= 0 || particle.y <= 0) {
                particlePositions[2*i] = java.lang.Math.max(0, particle.x);
                particlePositions[2*i + 1] = java.lang.Math.max(0, particle.y);

                particle.stuck = true;
                particle.color = "red";
            }

            //Else update positions as normal
            else {
                particlePositions[2 * i] = particles[i].x;
                particlePositions[2 * i + 1] = particles[i].y;
                i += 1;
            }
        }
    }


    public double[] getAllPositions() {
        return particlePositions;
    }

    private static double getRandCoord() {
        return Math.random();
    }

    public void setL(double Lin) {
        L = Lin;
    }

    public double getL() {
        return L;
    }

    public class Particle {

        //Fix so the color changes when the particles get stuck
        private double x;
        private double y;
        private String color = "Blue";
        private boolean stuck = false;

        public Particle(double xIn, double yIn) {
            x = xIn;
            y = yIn;
        }

        public Particle() {
            this(getRandCoord(), getRandCoord());
        }

        public void updatePosition() {
            if (!stuck) {
                x = x + L * Math.cos(2 * Math.PI * Math.random());
                y = y + L * Math.sin(2 * Math.PI * Math.random());
            }
        }
    }
}
