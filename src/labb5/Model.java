package labb5;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for storing information about webpages, such as URL and browsing history.
 *
 * @author Felix Liu, Leo Enge
 */


public class Model {
    String strUrl;
    ArrayList<String> completeHistory;
    ArrayList<String> dynamicHistory;
    int historyPointer;
    URL url;

    /**
     * Constructor for Model. Set the current url and creates the an ArrayList for storing browsing history
     **/

    public Model(){
        strUrl = "http://info.cern.ch/hypertext/WWW/TheProject.html";
        try{
            url = new URL(strUrl);
        } catch (MalformedURLException e){
            url = null;
        }
        completeHistory = new ArrayList<>();
        dynamicHistory = new ArrayList<>();
        completeHistory.add(strUrl);
        dynamicHistory.add(strUrl);
        historyPointer = 0;
    }

    /**
     * Sets the url field to newUrl. Also adds the url to browsing history.
     * @param newUrl url given as a string. Does not update the model.
     */

    public void setStrUrl(String newUrl){
        strUrl = newUrl;
        completeHistory.add(strUrl);

        while(historyPointer < dynamicHistory.size() - 1){
            dynamicHistory.remove(dynamicHistory.size()-1);
        }

        dynamicHistory.add(strUrl);
        historyPointer = dynamicHistory.size() - 1;
    }

    /**
     * Retruns true if it is possible to move back in history, otherwise false.
     * @return
     */
    public boolean canMoveBackHistory() {
        if(historyPointer > 0) {return true;}
        return false;
    }


    /**
     * Retruns true if it is possible to move forward in history, otherwise false.
     * @return
     */
    public boolean canMoveFwdHistory() {
        if(historyPointer < dynamicHistory.size() - 1) {return true;}
        return false;
    }

    /**
     * Moves in the history either one step back or one step forward dependeing on the parameter step,
     * which can be either 1 och -1. Does not update the model.
     * @param step
     */
    public void moveInHistory(int step) {
        if(Math.abs(step) != 1) {return;}
        try{
            strUrl = dynamicHistory.get(historyPointer + step);
        }catch (IndexOutOfBoundsException e){
            return;
        }
        historyPointer = historyPointer + step;
    }

    /**
     * updates the models url field to the current strUrl.
     */

    public void updateModel(){
        try{
            url = new URL(strUrl);
        }catch (MalformedURLException e) {
            url = null;
        }
        System.out.println("==========");
        System.out.println("Complete history:" + completeHistory.toString());
        System.out.println("Dynamic history" + dynamicHistory.toString());
    }
}