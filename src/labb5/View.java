package labb5;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
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

    /**
     * Constructor for View object. Adds an editorPane in a scrollPane to View. Also sets the current webpage displayed
     * to the URL in model.
     *
     * @param inModel Model object which stores the current URL and browsing history.
     * @throws IOException Thrown when model.url isn't resolvable from JEditorPane's setPage method.
     */

    public View(Model inModel) throws IOException{
        model = inModel;
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        //editorPane.setContentType("text/html");
        HyperlinkListener hyperlinkListener = new ActivatedHyperLinkListener(editorPane);
        editorPane.addHyperlinkListener(hyperlinkListener);

        try{
            editorPane.setPage(model.url);
            editorPane.setText(model.HTML);
        }catch (IOException e){
            System.exit(-3);
        }
        editorPane.setPreferredSize(new Dimension(500, 500));
        scrollPane = new JScrollPane(editorPane);
        this.add(scrollPane);
        setVisible(true);
    }

    /**
     * Updates View to the URL in model
     */
    public void updateView(){
        try{
            editorPane.setPage(model.url);
            //editorPane.setText(model.HTML);
        } catch (IOException e){
            System.out.println("Konstig URL");;
        }

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
                updateView();
            }
        }
    }
}