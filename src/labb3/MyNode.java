package labb3;

import javax.swing.tree.DefaultMutableTreeNode;
//REEEEEEEEEE

public class MyNode extends DefaultMutableTreeNode {
    String level;
    String text;

    public MyNode(String nodeName, String textIn, String levelIn) {
        super(nodeName);
        level = levelIn;
        text = textIn;
    }

    /*
    public MyNode(String xmlLine, MyNode parent) throws Exception {
        Scanner lineReader = new Scanner(new StringReader(xmlLine));
        lineReader.useDelimiter(">");
        Pattern matcher
        if (!lineReader.hasNext() || !lineReader.next().startsWith("<")) {
            throw new Exception();
        }
        else {
            String betweenTags = lineReader.next();
        }
    }
    */
}
