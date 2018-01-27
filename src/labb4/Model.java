package labb4;

import java.awt.*;
import java.lang.Math;
import java.util.HashSet;
import java.util.ArrayList;

public class Model {
    Particle[] particles;

    //The width of the window which the particles are in in pixels.
    public final int windowWidth = 700;

    //Number of rows and columns in the grid which we check particle collisions in.
    private final int gridDimension = 50;

    //List so that we only check for collisions between particles in the same cell on the grid for efficiency.
    private ArrayList<HashSet<Particle>> particleGridCells
            = new ArrayList<>(gridDimension * gridDimension);
    private ArrayList<HashSet<Particle>> stuckParticleGridCells
            = new ArrayList<>(gridDimension * gridDimension);

    private double L = 0.01;

    public Model(int noOfParticles) {

        for (int i = 0; i < gridDimension * gridDimension; i++) {
            particleGridCells.add(new HashSet<>());
            stuckParticleGridCells.add(new HashSet<>());
        }

        //particlePositions = new double[2 * noOfParticles];
        particles = new Particle[noOfParticles];

        for (int i = 0; i < noOfParticles; i++) {
            Particle currentParticle = new Particle();
            particles[i] = currentParticle;

            //Iterates over the grid cells and places the particle in the correct cell.
            //We think of the grid as an m by n matrix.
            particleGridCells.get(particleGridIndex(currentParticle)).add(currentParticle);
        }
    }

    private static boolean particleCollided(Particle particle1, Particle particle2) {

        //Checks if distance between the two particles is less than the diameter of the particles.
        //Diameter of particles are given in pixels so we need to divide the diameter in pixels by
        //the width of the screen.
        return Math.pow(particle1.x - particle2.x, 2) + Math.pow(particle1.y - particle2.y, 2)
                < Math.pow(particle1.diameter / (double) 700, 2);
    }

    private boolean resolveStuckOnWall(Particle particle) {

        boolean stuck = false;
        //Particle positions are given from 0 to 1 and then scaled to the
        //width and height of the window, so if x or y is greater than 1 or less than 0
        //the particle is outside the window.
        if (particle.x <= 0 || particle.y <= 0) {
            particle.x = Math.max(particle.x, 0);
            particle.y = Math.max(particle.y, 0);

            particle.stuck = true;
            particle.color = Color.red;

            stuck = true;
        }

        if (particle.x >= 1 || particle.y >= 1) {
            particle.x = Math.min(particle.x, 1);
            particle.y = Math.min(particle.y, 1);

            particle.stuck = true;
            particle.color = Color.red;

            stuck = true;
        }

        //Add a circular "wall" which the particles can collide with in the middle
        double circleRadius = 0.2;

        //If the particle touches the circle it becomes stuck.
        if (Math.pow(particle.x - 0.5, 2) + Math.pow(particle.y - 0.5, 2)
                < Math.pow(circleRadius + particle.diameter / ((double) 2 * windowWidth), 2)
                && Math.pow(particle.x - 0.5, 2) + Math.pow(particle.y - 0.5, 2)
                > Math.pow(circleRadius - particle.diameter / ((double) 2 * windowWidth), 2)) {
            particle.stuck = true;
            particle.color = Color.red;

            stuck = true;
        }

        return stuck;
    }

    private boolean collisionCheck(Particle particle) {
        int gridIndex = particleGridIndex(particle);
        int n = gridIndex % gridDimension; //current column in the grid particle is in.
        int m = (gridIndex - n) / gridDimension; //current row in the grid particle is in

        for (Particle stuckParticle : stuckParticleGridCells.get(gridIndex)) {
            if (particleCollided(particle, stuckParticle)) {
                particle.stuck = true;
                particle.color = Color.red;

                return true;
            }
        }

        /* If the particle is within one radius of a neighbouring cell we need to check for collisions in
        the next cell too. These if-else statements checks for cells to the left, right, above and below.
         */

        //Checks if the particle is close to a neighboring cell to the left.
        if (particle.x - n / (double) gridDimension < particle.diameter / ((double) 2 * windowWidth)
                && n != 0) {

            for (Particle stuckParticle : stuckParticleGridCells.get(gridIndex - 1)) {
                if (particleCollided(particle, stuckParticle)) {
                    particle.stuck = true;
                    particle.color = Color.red;

                    return true;
                }
            }
        }

        //Checks if the particle is close to a neighboring cell to the right.
        else if ((n + 1) / (double) gridDimension - particle.x < particle.diameter / ((double) 2 * windowWidth)
                    && n != gridDimension - 1) {

            for (Particle stuckParticle : stuckParticleGridCells.get(gridIndex + 1)) {
                if (particleCollided(particle, stuckParticle)) {
                    particle.stuck = true;
                    particle.color = Color.red;

                    return true;
                }
            }
        }


        //Checks if the particle is close to a neighboring cell above it.
        else if (particle.y - m / (double) gridDimension < particle.diameter / ((double) 2 * windowWidth)
                    && m != 0) {

            for (Particle stuckParticle : stuckParticleGridCells.get(gridIndex - gridDimension)) {
                if (particleCollided(particle, stuckParticle)) {
                    particle.stuck = true;
                    particle.color = Color.red;

                    return true;
                }
            }
        }

        //Checks if the particle is close to a neighboring cell below it.
        else if ((m + 1) / (double) gridDimension - particle.y < particle.diameter / ((double) 2 * windowWidth)
                && m != gridDimension - 1) {

            for (Particle stuckParticle : stuckParticleGridCells.get(gridIndex + gridDimension)) {
                if (particleCollided(particle, stuckParticle)) {
                    particle.stuck = true;
                    particle.color = Color.red;

                    return true;
                }
            }
        }

        return false;
    }

    private int particleGridIndex(Particle particle) {

        //Special cases when if the particles x or y coordinates are 1, since the normal formula
        //will give an index that is out of bounds.
        if (particle.x == 1 || particle.y == 1) {
            if (particle.x == 1 && particle.y == 1) {
                return (int) Math.floor((particle.y - 1 / ((double) (2 * gridDimension))) * gridDimension) *     gridDimension
                        + (int) Math.floor((particle.x - 1 / ((double) (2 * gridDimension))) * gridDimension);
            }

            else if (particle.x == 1) {
                return (int) Math.floor(particle.y * gridDimension) * gridDimension
                        + (int) Math.floor((particle.x - 1 / ((double) (2 * gridDimension))) * gridDimension);
            }

            else {
                return (int) Math.floor((particle.y - 1 / ((double) (2 * gridDimension))) * gridDimension)
                        * gridDimension + (int) Math.floor(particle.x * gridDimension);
            }
        }

        //Otherwise just use the regular formula.
        else {
            return (int) Math.floor(particle.y * gridDimension) * gridDimension
                    + (int) Math.floor(particle.x * gridDimension);
        }
    }


    //Updates positions and particlePositions array.
    public void updateAllPositions() {

        int gridIndex = 0;

        //Keeps track of particles to be removed from cell due to going outside the cells boundaries
        HashSet<Particle> tempRemoveSet;
        HashSet<Particle> tempAddSet = new HashSet<>();

        for (HashSet<Particle> gridCell: particleGridCells) {
            tempRemoveSet = new HashSet<>();

            for (Particle particle : gridCell) {
                if (!particle.stuck) {
                    particle.updatePosition();

                    //ResolveStuckOnWall returns true if the particle gets stuck on the wall.
                    boolean newStuckParticle = resolveStuckOnWall(particle);

                    //Checks if the particle ended up in a new gridcell
                    if (particleGridIndex(particle) != gridIndex) {

                        //Sets to keep track of which particles ended up in a new grid cell.
                        tempRemoveSet.add(particle);
                        tempAddSet.add(particle);
                    }

                    if (!newStuckParticle) {

                        //collisionCheck returns true if the particle collides and gets stuck.
                        newStuckParticle = collisionCheck(particle);
                    }

                    if (newStuckParticle) {
                        stuckParticleGridCells.get(particleGridIndex(particle)).add(particle);
                    }
                }
            }

            if (!tempRemoveSet.isEmpty()) {
                for (Particle movedParticle: tempRemoveSet) {
                    particleGridCells.get(gridIndex).remove(movedParticle);
                }
            }

            gridIndex++;

        }

        for (Particle movedParticle: tempAddSet) {
            particleGridCells.get(particleGridIndex(movedParticle)).add(movedParticle);
        }
    }

    /*
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
                particle.x = Math.min(1, particle.x);
                particle.y = Math.min(1 , particle.y);

                particle.stuck = true;
                particle.color = Color.red;
            }

= particles[i].y;
        }            else if (particle.x <= 0 || particle.y <= 0) {
                particle.x = Math.max(0, particle.x);
                particle.y = Math.max(0, particle.y);

                particle.stuck = true;
                particle.color = Color.red;
            }


            //Update positions as normal
            particlePositions[2*i] = particles[i].x;
            particlePositions[2*i + 1] = particles[i].y;
            i += 1;
        }
    }
    */


    public double[] getAllPositions() {
        double[] res = new double[2 * particles.length];

        for (int i = 0; i < particles.length; i++) {
            res[2 * i] = particles[i].x;
            res[2 * i + 1] = particles[i].y;
        }

        return res;
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

        private double x;
        private double y;
        private Color color = Color.blue;
        private boolean stuck = false;
        private final int diameter = 3;

        public Particle(double xIn, double yIn) {
            x = xIn;
            y = yIn;
        }

        public Particle() {
            this(getRandCoord(), getRandCoord());
        }

        public void updatePosition() {
            x = x + L * Math.cos(2 * Math.PI * Math.random());
            y = y + L * Math.sin(2 * Math.PI * Math.random());
        }

        public Color getColor() {
            return color;
        }

        public int getDiameter() {
            return diameter;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}


