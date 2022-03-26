import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

public class Menu extends JPanel //implements ActionListener {

{
    public Menu() {
        super(new GridLayout(1, 1));

        JTabbedPane tabbedPane = new JTabbedPane();
        JComponent panel1 = makeFillerPanel("PANEL FOR USER MANUAL");
        tabbedPane.addTab("How To Use", panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = makeFillerPanel("PANEL FOR ENTERING WORD FOR PREDICTION");
        tabbedPane.addTab("Word Prediction!", panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent panel3 = makeFillerPanel("PANEL FOR CHANGING SETTINGS ON LIMIT OF WORD SUGGESTIONS");
        tabbedPane.addTab("Change Word Suggestions Limits", panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        JComponent panel4 = makeFillerPanel("PANEL FOR REMOVING WORDS");
        tabbedPane.addTab("Remove Words", panel4);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        JComponent panel5 = makeFillerPanel("PANEL FOR LOOKING FOR WORD");
        tabbedPane.addTab("Search For Word", panel5);
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);

        JComponent panel6 = makeFillerPanel("PANEL FOR DICTIONARY DISPLAY");
        tabbedPane.addTab("Display Dictionary", panel6);
        tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);

        JComponent panel7 = makeFillerPanel("PANEL FOR LOADING AND TESTING");
        tabbedPane.addTab("Testing", panel7);
        tabbedPane.setMnemonicAt(6, KeyEvent.VK_7);

        add(tabbedPane);
        //allows scrolling tab usage
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    //all panels require making, as each panel will have different layouts
    //and textfields/buttons/etc.
    protected JComponent makeFillerPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    //reminder on user manual panel

    protected JComponent makePredictionPanel() {
        JPanel panel = new JPanel(false);

        panel.setLayout(new GridLayout(2, 2));
        
        return panel;
    }

    //reminder on change settings(?) panel
    //reminder on removing words panel
    //reminder on looking up word panel
    //reminder on display dictionary panel
    //reminder on load and testing panel

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Prediction");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new Menu(), BorderLayout.CENTER);

        frame.setSize(850,600);
        frame.setVisible(true);

        //reminder to do JMenuBar, JMenu and JMenuItems
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
