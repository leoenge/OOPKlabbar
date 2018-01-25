package labb4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MyFrame extends JFrame {

    JPanel mainPanel = new JPanel();
    Controller mainController;
    String fileName = "data.cvs";

    public MyFrame() {
        mainPanel.setLayout(new FlowLayout());
        Model myModel = new Model(10000);
        View myView = new View(myModel);

        mainController = new Controller(myModel, myView);

        mainPanel.add(myView);
        mainPanel.add(mainController);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    mainController.saveData(fileName);
                } catch (IOException ex){
                    System.out.println("Kan inte skriva till filen");
                }

                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {

        MyFrame myFrame = new MyFrame();
    }
}