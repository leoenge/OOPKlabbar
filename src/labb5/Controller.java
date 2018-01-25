package labb5;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
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

        view.goButton.addActionListener(this);
        view.backButton.addActionListener(this);
        view.fwdButton.addActionListener(this);

        HyperlinkListener hyperlinkListener = new ActivatedHyperLinkListener(view.editorPane);
        view.editorPane.addHyperlinkListener(hyperlinkListener);

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
        if(src.equals(view.goButton)){
            goButtonAction();
        }
        else if(src.equals(view.backButton)){
            backButtonAction();
        }
        else if(src.equals(view.fwdButton)){
            fwdButtonAction();
        }
    }

    /**
     * Sets the url in model to the one in the textField and updates model and view.
     */

    private void goButtonAction(){
        model.setStrUrl(view.textField.getText());
        System.out.println(model.strUrl);
        model.updateModel();
        view.updateView();

    }
    private void backButtonAction(){

    }
    private void fwdButtonAction(){

    }

    /**
     * HyperLinkListener which listens to clicked links in Views JEditorPane.
     * Updates model and view when a link is clicked.
     */
    private class ActivatedHyperLinkListener implements HyperlinkListener {

        JEditorPane listenerPane;

        private ActivatedHyperLinkListener(JEditorPane editorPanein) {
            listenerPane = editorPanein;
        }

        public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent) {
            if (hyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                model.strUrl = hyperlinkEvent.getURL().toString();
                model.updateModel();
                view.updateView();
            }
        }
    }
}