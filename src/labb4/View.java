package labb4;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;


public class View extends JPanel {
    private Model model;
    double[] particlePositions;
    private int xwidth;
    private int ywidth;

    public View(Model modelIn) {
        super.setPreferredSize(new Dimension(modelIn.windowWidth, modelIn.windowWidth));
        model = modelIn;
        xwidth = modelIn.windowWidth;
        ywidth = modelIn.windowWidth;
        //Positions are given as alternating x and y coordinates.
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < model.particles.length; i++) {
            int particleDiameter = model.particles[i].getDiameter();

            g2d.setColor(model.particles[i].getColor());
            g2d.fill(new Ellipse2D.Double(xwidth * model.particles[i].getX() - Math.ceil(particleDiameter/2),
                    ywidth * model.particles[i].getY() - Math.ceil(particleDiameter/2),
                    particleDiameter, particleDiameter));
        }
    }
}

