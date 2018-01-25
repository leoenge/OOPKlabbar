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
    List<String> history;
    int historyPointer;
    URL url;

    /**
     * Constructor for Model. Set the current url and creates the an ArrayList for storing browsing history
     */


    public Model(){
        strUrl = "http://info.cern.ch/hypertext/WWW/TheProject.html";

        try{
            url = new URL(strUrl);
        } catch (MalformedURLException e){
            url = null;
        }
        history = new ArrayList<>();
        history.add(strUrl);
        historyPointer = 0;
    }

    /**
     * Sets the url field to newUrl. Also adds the url to browsing history.
     * @param newUrl url given as a string.
     */

    public void setStrUrl(String newUrl){
        strUrl = newUrl;
        history.add(strUrl);
        historyPointer = history.size() - 1;

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
    }
}