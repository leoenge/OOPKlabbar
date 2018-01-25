package labb4;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;


public class View extends JPanel {
    Model model;
    double[] particlePositions;
    private int xwidth = 500;
    private int ywidth = 500;

    public View(Model modelIn) {
        super.setPreferredSize(new Dimension(xwidth, ywidth));
        model = modelIn;
        //Positions are given as alternating x and y coordinates.
        particlePositions = modelIn.getAllPositions();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < particlePositions.length; i += 2) {
            g2d.draw(new Ellipse2D.Double(xwidth * particlePositions[i],
                    ywidth * particlePositions[i + 1], 2, 2));
        }
    }
}
