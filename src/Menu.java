import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.JTabbedPane;
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

public class Menu extends JPanel implements ActionListener 
{
    JLabel predictLabel, wordLimitLabel, removeWordLabel, searchWordLabel, currentLanguageLabel, currentAddSettingLabel;
    JTextField predictTextField, wordLimitTextField, removeWordTextField, searchWordTextField;
    JTextArea predictTextArea, wordLimitTextArea, displayDictTextArea;
    JButton predictButton, wordLimitButton, removeWordButton, searchWordButton, displayNext, displayBack, saveDictButton, changeLanguageButton, addWordButton;

    public Menu() 
    {
        super(new GridLayout(1, 1));

        JTabbedPane tabbedPane = new JTabbedPane();
        JComponent panel1 = makeFillerPanel("PANEL FOR USER MANUAL");
        tabbedPane.addTab("How To Use", panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = makePredictionPanel();
        tabbedPane.addTab("Word Prediction!", panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent panel3 = changeSettingPanel();
        tabbedPane.addTab("Settings", panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        JComponent panel4 = removeWordPanel();
        tabbedPane.addTab("Remove Words", panel4);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        JComponent panel5 = searchWordPanel();
        tabbedPane.addTab("Search For Word", panel5);
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);

        JComponent panel6 = displayDictPanel();
        tabbedPane.addTab("Display Dictionary", panel6);
        tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);

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

    //User manual panel
    protected JComponent makeManualPanel() 
    {
        JPanel panel = new JPanel();

        return panel;
    }

    //Word prediction panel
    protected JComponent makePredictionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        predictLabel = new JLabel("Please enter text here: ");
        predictTextField = new JTextField();
        predictTextField.setColumns(65);
        predictButton = new JButton("Predict!");
        saveDictButton = new JButton("Save Current Dictionary");

        topPanel.add(predictLabel, SwingConstants.CENTER);
        topPanel.add(predictTextField);
        topPanel.add(predictButton);
        predictButton.addActionListener(this);

        panel.add(topPanel, BorderLayout.NORTH);
        predictTextArea = new JTextArea();
        panel.add(predictTextArea, BorderLayout.CENTER);
        panel.add(saveDictButton, BorderLayout.SOUTH);

        return panel;
    }

    //Update word limit panel
    protected JComponent changeSettingPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        Integer[] wordLimitOptions = {1,2,3,4,5,6,7,8,9,10};
        JComboBox<Integer> wordLimitBox = new JComboBox<>(wordLimitOptions);
        wordLimitBox.setBounds(80, 50, 50, 20);
        wordLimitButton  = new JButton("Set Word Limit");
        wordLimitButton.setBounds(140, 50, 120, 20);
        wordLimitLabel = new JLabel("Default Word Limit: -");
        wordLimitLabel.setBounds(270, 50, 150, 20);

        changeLanguageButton = new JButton("Change language of dictionary");
        changeLanguageButton.setBounds(80, 110, 210, 20);
        String[] language = {"English", "Italian"};
        JComboBox<String> languageComboBox = new JComboBox<>(language);
        languageComboBox.setBounds(300, 110, 80, 20);
        currentLanguageLabel = new JLabel("Current Language: English");
        currentLanguageLabel.setBounds(390, 110, 200, 20);

        addWordButton = new JButton("Toggle add word setting");
        addWordButton.setBounds(150, 170, 200, 20);
        String[] setting = {"Off", "On"};
        JComboBox<String> addWordSettingBox = new JComboBox<>(setting);
        addWordSettingBox.setBounds(80, 170, 60, 20);
        currentAddSettingLabel = new JLabel("Off");
        currentAddSettingLabel.setBounds(260, 170, 150, 20);


        panel.add(wordLimitBox);
        panel.add(wordLimitButton);
        panel.add(wordLimitLabel);
        panel.add(languageComboBox);
        panel.add(changeLanguageButton);
        panel.add(currentLanguageLabel);
        panel.add(addWordSettingBox);
        panel.add(addWordButton);
        panel.add(currentAddSettingLabel);

        wordLimitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            String selectedNumber = "Word Limit: " + wordLimitBox.getItemAt(wordLimitBox.getSelectedIndex());
            wordLimitLabel.setText(selectedNumber);

            //UPDATE WITH WORD LIMIT SETTING FUNCTIONS
            }
        });

        changeLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            String selectedLanguage = "Current Language: " + languageComboBox.getItemAt(languageComboBox.getSelectedIndex());
            currentLanguageLabel.setText(selectedLanguage);

            //UPDATE WITH LANGUAGE SETTING FUNCTIONS
            }
        });

        addWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            String setting = "Current Setting: " + addWordSettingBox.getItemAt(addWordSettingBox.getSelectedIndex());
            currentAddSettingLabel.setText(setting);

            //UPDATE WITH LANGUAGE SETTING FUNCTIONS
            }
        });

        return panel;
    }

    protected JComponent removeWordPanel()
    {
        JPanel panel = new JPanel();
        JPanel topPanel = new JPanel();
        JLabel text = new JLabel("Enter word to remove: ");
        
        removeWordLabel = new JLabel("-");
        removeWordTextField = new JTextField();
        removeWordTextField.setColumns(65);
        removeWordButton = new JButton("Remove");

        topPanel.add(text);
        topPanel.add(removeWordTextField);
        topPanel.add(removeWordButton);
        removeWordButton.addActionListener(this);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(removeWordLabel, BorderLayout.CENTER);

        return panel;
    }

    protected JComponent searchWordPanel()
    {
        JPanel panel = new JPanel();
        JPanel topPanel = new JPanel();
        JLabel text = new JLabel("Enter word to search for in dictionary: ");
        
        searchWordLabel = new JLabel("-");
        searchWordTextField = new JTextField();
        searchWordTextField.setColumns(56);
        searchWordButton = new JButton("Search");

        topPanel.add(text);
        topPanel.add(searchWordTextField);
        topPanel.add(searchWordButton);
        searchWordButton.addActionListener(this);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(searchWordLabel, BorderLayout.CENTER);

        return panel;
    }

    protected JComponent displayDictPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel text = new JLabel("Displaying dictionary contents:", SwingConstants.CENTER);

        displayDictTextArea = new JTextArea();
        displayNext = new JButton("Next");
        displayBack = new JButton("Back");

        panel.add(text, BorderLayout.NORTH);
        panel.add(displayDictTextArea, BorderLayout.CENTER);
        panel.add(displayBack, BorderLayout.WEST);
        panel.add(displayNext, BorderLayout.EAST);

        displayBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            //UPDATE WITH DISPLAY BACK FUNCTION (MAYBE SWAP THIS TO MAIN
            //ACTIONLISTENER METHOD)
            }
        });

        displayNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            //UPDATE WITH DISPLAY NEXT FUNCTION
            }
        });

        return panel;
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Prediction");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,600);

        frame.add(new Menu(), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == predictButton)
        {
            //UPDATE WITH PREDICTED WORDS LIST STUFFS
            String src = predictTextField.getText();
            predictTextArea.append(src + "\n");
        } else if (e.getSource() == removeWordButton)
        {
            String text = "Replace with either removed or word not found here";
            removeWordLabel.setText(text);

            //UPDATE WITH REMOVE WORD FUNCTIONS STUFFS
        } else if (e.getSource() == searchWordButton)
        {
            String text = "Replace with either word found or not found in dictionary here";
            searchWordLabel.setText(text);
        } else if (e.getSource() == saveDictButton)
        {
            //UPDATE WITH SAVING DICTIONARY BUTTON STUFFS
        }
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

