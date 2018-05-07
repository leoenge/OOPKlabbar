package labb5;

import javax.swing.*;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;

/**
 * View contains a JPanel with an editorPane for displaying webpages. Also contains as a nested class a
 * ActivatedHyperLinkListener.
 *
 * @author Felix Liu, Leo Enge
 */

public class View extends JPanel {

    Model model;

    JEditorPane editorPane;
    JScrollPane scrollPane;
    JPanel controlPanel;

    JTextField textField;
    JButton goButton;
    JButton backButton;
    JButton fwdButton;
    JButton historyButton;

    /**
     * Constructor for View object. Adds an editorPane in a scrollPane to View. Also sets the current webpage displayed
     * to the URL in model.
     *
     * @param inModel Model object which stores the current URL and browsing history.
     * @throws IOException Thrown when model.url isn't resolvable from JEditorPane's setPage method.
     */

    public View(Model inModel) throws IOException{
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        model = inModel;

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        textField = new JTextField();
        //textField.setSize(50, 5);

        goButton = new JButton();
        goButton.setText("Go");

        backButton = new JButton();
        backButton.setText("Back");

        fwdButton = new JButton();
        fwdButton.setText("Fwd");

        historyButton = new JButton();
        historyButton.setText("History");

        controlPanel.add(textField);
        controlPanel.add(goButton);
        controlPanel.add(backButton);
        controlPanel.add(fwdButton);
        controlPanel.add(historyButton);

        editorPane = new JEditorPane();
        editorPane.setEditable(false);

        updateView();

        editorPane.setPreferredSize(new Dimension(500, 500));
        scrollPane = new JScrollPane(editorPane);

        this.add(controlPanel);
        this.add(scrollPane);
        setVisible(true);
    }

    /**
     * Updates View to the URL in model
     */
    public void updateView() {
        textField.setText(model.strUrl);
        if(model.url == null){
            JOptionPane.showMessageDialog(this, "Det där gick inge bra. Antingen är URL:en lite konstig" +
                    "eller så är din internetuppkoppling inte hundra hundra.");
            return;
        }
        try{
            editorPane.setPage(model.url);
        }catch (IOException e){
            JOptionPane.showMessageDialog(this, "Det där gick inge bra. Antingen är URL:en lite konstig" +
                    "eller så är din internetuppkoppling inte hundra hundra.");
            return;
        }

        if(model.canMoveBackHistory()){backButton.setEnabled(true);}
        else{backButton.setEnabled(false);}
        if(model.canMoveFwdHistory()){fwdButton.setEnabled(true);}
        else{fwdButton.setEnabled(false);}
    }


}