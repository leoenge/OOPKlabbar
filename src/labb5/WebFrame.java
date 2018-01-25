package labb5;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * WebFrame is a JFrame which stores two different JPanels, one for displaying webpages and
 * one for holding address field and buttons.
 *
 * @author Felix Liu, Leo Enge
 */

public class WebFrame extends JFrame{
    JPanel mainPanel;
    Controller controller;
    View view;
    Model model;

    /**
     * Constructor for WebFrame. Adds a JPanel for displaying webpages and one for displaying user controls.
     * Displays immediately upon creation.
     */

    public WebFrame(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        model = new Model();
        try{
            view = new View(model);
        }catch (IOException e){
            System.exit(-2);
        }
        controller = new Controller(model, view);
        mainPanel.add(controller);
        mainPanel.add(view);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        WebFrame frame = new WebFrame();
    }

}