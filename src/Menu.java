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
    JLabel savedLabel, addWordLabel, spellingLabel, predictLabel, wordLimitLabel, removeWordLabel, searchWordLabel, currentLanguageLabel, currentAddSettingLabel;
    JTextField predictTextField, wordLimitTextField, removeWordTextField, searchWordTextField;
    JTextArea predictTextArea, wordLimitTextArea, displayDictTextArea;
    JButton addButton, spellCheckButton, predictButton, wordLimitButton, removeWordButton, searchWordButton, displayNext, displayBack, saveDictButton, changeLanguageButton, addWordButton;
    JRadioButton addOnRadio, addOffRadio;
    ButtonGroup group;

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

    //User manual panel (TO BE COMPLETED)
    protected JComponent makeManualPanel() 
    {
        JPanel panel = new JPanel();

        return panel;
    }

    //Word prediction panel
    protected JComponent makePredictionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        spellingLabel = new JLabel("-");
        spellingLabel.setBounds(630, 10, 250, 20);
        addWordLabel = new JLabel("-");
        addWordLabel.setBounds(630, 40, 250, 20);
        addButton = new JButton("Add Word (if enabled)");
        addButton.setBounds(270, 70, 170, 20);
        saveDictButton = new JButton("Save Current Dictionary");
        saveDictButton.setBounds(450, 70, 175, 20);
        savedLabel = new JLabel("-");
        savedLabel.setBounds(630, 70, 250, 20);
        predictLabel = new JLabel("Please enter text below: ");
        predictLabel.setBounds(10, 9, 150, 20);
        predictTextField = new JTextField();
        predictTextField.setBounds(10, 40, 615, 20);
        predictButton = new JButton("Predict!");
        predictButton.setBounds(10, 70, 120, 20);
        spellCheckButton = new JButton("Check spelling");
        spellCheckButton.setBounds(140, 70, 120, 20);
        predictTextArea = new JTextArea();
        predictTextArea.setBounds(10, 100, 860, 420);

        predictButton.addActionListener(this); //this could also be implemented with the enter key perhaps?
        spellCheckButton.addActionListener(this);
        addButton.addActionListener(this);
        saveDictButton.addActionListener(this);

        panel.add(spellingLabel);
        panel.add(spellCheckButton);
        panel.add(saveDictButton);
        panel.add(predictLabel);
        panel.add(predictTextField);
        panel.add(predictButton);
        panel.add(predictTextArea);
        panel.add(addButton);
        panel.add(addWordLabel);
        panel.add(savedLabel);

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

        JLabel text = new JLabel("Toggle Add Word Setting: ");
        text.setBounds(80, 170, 150, 20);
        addOnRadio = new JRadioButton("On");
        addOffRadio = new JRadioButton("Off");
        addOnRadio.setBounds(80, 190, 200, 20);
        addOffRadio.setBounds(80, 210, 200, 20);
        //addOffRadio.setSelected(true);
        group = new ButtonGroup();
        group.add(addOnRadio);
        group.add(addOffRadio);
        addOnRadio.addActionListener(this);
        addOffRadio.addActionListener(this);
        currentAddSettingLabel = new JLabel("Off");
        currentAddSettingLabel.setBounds(235, 170, 50, 20);


        //addword but in previous interface using combobox, delete in final draft
        //addWordButton = new JButton("Toggle add word setting");
        //addWordButton.setBounds(150, 170, 200, 20);
        //String[] setting = {"Off", "On"};
        //JComboBox<String> addWordSettingBox = new JComboBox<>(setting);
        //addWordSettingBox.setBounds(80, 170, 60, 20);
        //currentAddSettingLabel = new JLabel("Off");
        //currentAddSettingLabel.setBounds(260, 170, 150, 20);

        panel.add(text);
        panel.add(wordLimitBox);
        panel.add(wordLimitButton);
        panel.add(wordLimitLabel);
        panel.add(languageComboBox);
        panel.add(changeLanguageButton);
        panel.add(currentLanguageLabel);
        panel.add(addOnRadio);
        panel.add(addOffRadio);
        panel.add(currentAddSettingLabel);
        //panel.add(addWordSettingBox);
        //panel.add(addWordButton);

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

            String text = "Saved!";
            savedLabel.setText(text);
        } else if (e.getSource() == spellCheckButton)
        {
            String text = "Replace with corrent or incorrect spelling";
            spellingLabel.setText(text);
            //UPDATE WITH SPELLCHECK STUFFS
        } else if (e.getSource() == addButton)
        {
            String text = "Replace with word added confirmation";
            addWordLabel.setText(text);
            //UPDATE WITH ADD WORD FROM CURRENT WORD IN TEXT FIELD IN PREDICT PANEL
        } else if (e.getSource() == addOnRadio)
        {
            currentAddSettingLabel.setText("On");
            //UPDATE WITH SETTING FOR ADDING WORDS TO TURN ON
        } else if (e.getSource() == addOffRadio)
        {
            currentAddSettingLabel.setText("Off");
            //UPDATE WITH SETTING FOR ADDING WORDS TO TURN OFF
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
