package labb3;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.regex.*;


/** Please never touch the horrible mess that is this code.
 *   really don't.
 *   for the love of god turn away now.
 */
public class DirTree extends JFrame implements ActionListener {

    private static String rootName = "Liv";
    private static final String closeString = " Close ";
    private static final String showString = " Show Details ";

    private JCheckBox box;
    private JTree tree;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JPanel controls;

    public DirTree() {
        MyNode root;

        //Creates tree from XML-file
        try {
            Scanner fileScan = new Scanner(new BufferedReader
                    (new FileReader("/home/felix/Documents/Liv.xml")));
            fileScan.useDelimiter("\n");
            root = readNode(fileScan);
            treeModel = new DefaultTreeModel(root);
            tree = new JTree(treeModel);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Invalid xml format");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException xD");
        }


        //Adds mouselistener to display additional information on mouse click
        //when a checkbox is selected
        MouseListener ml = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (box.isSelected())
                    showDetails(tree.getPathForLocation(e.getX(),
                            e.getY()));
            }
        };
        tree.addMouseListener(ml);

        Container c = getContentPane();

        //panel the JFrame to hold controls and the tree
        controls = new JPanel();
        box = new JCheckBox(showString);
        init(); //set colors, fonts, etc. and add buttons
        c.add(controls, BorderLayout.NORTH);
        c.add(tree, BorderLayout.CENTER);
        setVisible(true);
    }

    private void init() {
        tree.setFont(new Font("Dialog", Font.BOLD, 12));
        controls.add(box);
        addButton(closeString); //Button is added to controls (!)
        controls.setBackground(Color.lightGray);
        controls.setLayout(new FlowLayout());
        setSize(400, 400);
    }


    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals(closeString))
            dispose();
    }

    //Adds a button to the JPanel controls
    private void addButton(String n) {
        JButton b = new JButton(n);
        b.setFont(new Font("Dialog", Font.BOLD, 12));
        b.addActionListener(this);
        controls.add(b);
    }


    //Displays additional information about each node in a message dialog.
    private void showDetails(TreePath p) {
        if (p == null) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        if (p.getLastPathComponent() instanceof MyNode) {
            MyNode n = (MyNode) p.getLastPathComponent();

            sb.append(n.level + ": " + n.toString() + "\n" + n.text
                    + "\n" + "men allt som ");
            sb.append("är " + n.toString());

            while (n.getParent() != null && n.getParent() instanceof MyNode) {
                n = (MyNode) n.getParent();
                sb.append(" är " + n.toString());
            }

            JOptionPane.showMessageDialog(this, sb.toString());
        } else {
            throw new ClassCastException("Tree node not of correct type.");
        }
    }

    private void buildTree() {
        String[] categories = new String[3];
        categories[0] = "Växter";
        categories[1] = "Djur";
        categories[2] = "Svampar";

        String[] subCategories = new String[3];
        subCategories[0] = "Familjer";
        subCategories[1] = "Släkten";
        subCategories[2] = "Arter";

        for (int i = 0; i < 3; i++){
            DefaultMutableTreeNode nextNode =
                    new DefaultMutableTreeNode(categories[i]);
            root.add(nextNode);
            for (int j = 0; j < 3; j++) {
                nextNode.add(new DefaultMutableTreeNode(subCategories[j]));
            }
        }
    }

    private void buildTree(File f, DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode child =
                new DefaultMutableTreeNode(f.toString());
        parent.add(child);
        if (f.isDirectory()) {
            String list[] = f.list();
            for (int i = 0; i < list.length; i++)
                buildTree( new File(f,list[i]), child);
        }
    }

    private String getAttributes(File f) {
        String t = "";
        if (f.isDirectory())
            t += "Directory";
        else
            t += "Nondirectory file";
        t += "\n   ";
        if (!f.canRead())
            t += "not ";
        t += "Readable\n   ";
        if (!f.canWrite())
            t += "not ";
        t += "Writeable\n  ";
        if (!f.isDirectory())
            t += "Size in bytes: " + f.length() + "\n   ";
        else {
            t += "Contains files: \n     ";
            String[ ] contents = f.list();
            for (int i = 0; i < contents.length; i++)
                t += contents[i] + ", ";
            t += "\n";
        }
        return t;
    }


    static final Pattern startTagFormat = Pattern.compile("<(\\S+) namn=\"(.+?)\"> (.+)"); //regex for start tag format


    //Builds tree by parsing from XML-file
    private MyNode readNode(Scanner fileScan) throws IOException {
        String currentLine = fileScan.nextLine();

        //checks for xml processing instruction which is not to be read
        if (currentLine.startsWith("<?")) {
            currentLine = fileScan.nextLine();
        }

        Matcher startTagMatcher = startTagFormat.matcher(currentLine);

        if (startTagMatcher.matches()) {
            //Retrieves node info from tag
            String level = startTagMatcher.group(1);
            String nodeName = startTagMatcher.group(2);
            String text = startTagMatcher.group(3);

            String requiredEndTag = "</" + level + ">";  //The end tag matching current start tag can ONLY be this

            MyNode retNode = new MyNode(nodeName, text, level);

            //Recursively calls function on start tag lines, and builds tree until end tag
            while (fileScan.hasNext(startTagFormat)) {
                MyNode nextNode = readNode(fileScan);
                retNode.add(nextNode);
            }

            //Advances scanner to next line and checks for necessary end tag.
            if (!fileScan.nextLine().equals(requiredEndTag)) {
                throw new IOException();
            }

            return retNode;
        }

        //If file doesn't follow correct start and end tag formatting we throw an exception
        else {
            throw new IOException();
        }

    }


    public static void main(String[] args) {
        new DirTree();
    }

}