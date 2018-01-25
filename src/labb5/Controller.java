package labb5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for displaying webpages. Contains a textfield for entering URL:s, a go button to update to the
 * URL in the textfield and a back and forward button to move around in the browsing history.
 *
 * @author Felix Liu, Leo Enge
 */


public class Controller extends JPanel implements ActionListener {
    JTextField textField;
    JButton goButton;
    JButton backButton;
    JButton fwdButton;
    Model model;
    View view;


    /**
     * Constructor for Controller. Creates a JTextField, a go button, and a back and forward button and adds them to
     * Controller.
     *
     * @param inModel Model object storing information about browsing history and current URL.
     * @param inView View object for displaying the current webpage.
     */

    public Controller(Model inModel, View inView){
        model = inModel;
        view = inView;

        textField = new JTextField();
        textField.setText(model.strUrl);
        //textField.setSize(50, 5);

        goButton = new JButton();
        goButton.addActionListener(this);
        goButton.setText("Go");

        backButton = new JButton();
        backButton.addActionListener(this);
        backButton.setText("Back");

        fwdButton = new JButton();
        fwdButton.addActionListener(this);
        fwdButton.setText("Fwd");

        this.setLayout(new FlowLayout());
        this.add(textField);
        this.add(goButton);
        this.add(backButton);
        this.add(fwdButton);

    }

    /**
     * Listens to ActionEvents from the go, back and forward button and decides what to do
     * depending on which button was pressed.
     *
     * @param e ActionEvent from a JButton.
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src.equals(goButton)){
            goButtonAction();
        }
        else if(src.equals(backButton)){
            backButtonAction();
        }
        else if(src.equals(fwdButton)){
            fwdButtonAction();
        }
    }

    /**
     * Sets the url in model to the one in the textField and updates model and view.
     */

    private void goButtonAction(){
        model.setStrUrl(textField.getText());
        System.out.println(model.strUrl);
        model.updateModel();
        view.updateView();

    }
    private void backButtonAction(){

    }
    private void fwdButtonAction(){

    }
}