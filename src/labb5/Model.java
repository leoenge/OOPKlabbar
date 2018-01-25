package labb5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

/**
 * Model for storing information about webpages, such as URL and browsing history.
 *
 * @author Felix Liu, Leo Enge
 */


public class Model {
    String strUrl;
    String HTML;
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
        HTML = "";
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

    static String fetchWebHTML(String urlStr) throws IOException{
        URL webUrl = new URL(urlStr);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(webUrl.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;
        while((inputLine = in.readLine()) != null){
            stringBuilder.append(inputLine);
        }
        return stringBuilder.toString();
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
        /*try{
            HTML = fetchWebHTML(strUrl);
        } catch (IOException e){
            System.out.println("IOException");
            return;
        }*/
    }
    public String getHTML(){return HTML;}
}