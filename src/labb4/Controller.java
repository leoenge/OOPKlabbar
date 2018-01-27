package labb4;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Controller extends JPanel implements ChangeListener, ActionListener {
    private double delta = 50;
    private Timer timer;
    private String filename;
    private StringBuilder stringBuilder;
    private int time = 0;
    private int noOfSteps = 0;

    private Model model;
    private View view;
    private JSlider LSlider;
    private JSlider deltaSlider;

    private static final int LsliderMax = 10;
    private static final int LsliderMin = 0;
    private static final int LsliderInit = 1;

    private static final int deltaSliderMax = 100;
    private static final int deltaSliderMin = 1;
    private static final int deltaSliderInit = 50;

    private static boolean logData;
    private JToggleButton startStopButton;

    public Controller(Model modelIn, View viewIn) {
        model = modelIn;
        view = viewIn;

        stringBuilder = new StringBuilder();

        filename = "data.cvs";

        int deltaInt = (int) Math.round(delta);

        timer =  new Timer(deltaInt, this);
        timer.start();

        LSlider = new JSlider(JSlider.VERTICAL, LsliderMin, LsliderMax, LsliderInit);
        LSlider.addChangeListener(this);

        deltaSlider = new JSlider(JSlider.VERTICAL, deltaSliderMin, deltaSliderMax, deltaSliderInit);
        deltaSlider.addChangeListener(this);

        logData = false;
        startStopButton = new JToggleButton("Not logging", false);
        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logData = !logData;
                JToggleButton b = (JToggleButton) e.getSource();

                if (b.isSelected()) {
                    b.setText("Logging");
                }

                else {
                    b.setText("Not Logging");
                }
            }
        });


        this.add(LSlider);
        this.add(deltaSlider);
        this.add(startStopButton);
        this.setVisible(true);

    }

    public void saveData(String fileName) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(stringBuilder.toString());
        writer.close();
    }

    public void stateChanged(ChangeEvent e) {
        JSlider sourceSlider = (JSlider) e.getSource();

        if (sourceSlider == LSlider) {
            if (!sourceSlider.getValueIsAdjusting()) {
                double L = (double) LSlider.getValue()/100;
                model.setL(L);
            }
        } else {
            if (!sourceSlider.getValueIsAdjusting()) {
                delta = sourceSlider.getValue();
                int deltaInt = (int) Math.round(delta);
                timer.setDelay(deltaInt);
            }
        }

    }

    public void actionPerformed(ActionEvent e) {
        model.updateAllPositions();
        view.repaint();
        time += delta;
        if (noOfSteps < 100 && logData) {
            noOfSteps++;
            stringBuilder.append(time);
            stringBuilder.append(" , ");
            for (double d : model.getAllPositions()) {
                stringBuilder.append(String.format("%.2f , ", d));
            }

            stringBuilder.append("\n");
        }
    }
}
