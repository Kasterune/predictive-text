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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu extends JPanel implements ActionListener 
{
    JLabel savedLabel, addWordLabel, spellingLabel, predictLabel, wordLimitLabel, removeWordLabel, searchWordLabel, currentLanguageLabel, currentAddSettingLabel;
    JTextField predictTextField, wordLimitTextField, removeWordTextField, searchWordTextField;
    JTextArea predictTextArea, wordLimitTextArea, searchWordTextArea;
    JButton addButton, spellCheckButton, predictButton, wordLimitButton, removeWordButton, searchWordButton, saveDictButton, changeLanguageButton, addWordButton;
    JRadioButton addOnRadio, addOffRadio;
    ButtonGroup group;

    Dictionary dictionary, dict_en, dict_it;
    Prediction prediction;

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

        add(tabbedPane);
        //allows scrolling tab usage
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // Create the new dictionaries in each language
		dict_en = new Dictionary(Dictionary.Language.ENGLISH);
		dict_it = new Dictionary(Dictionary.Language.ITALIAN);
		
		// Create a new prediction
		prediction = new Prediction();
		
		// Select the current dictionary
		if (prediction.getLanguage().equals(Dictionary.Language.ENGLISH))
			dictionary = dict_en;
		else
			dictionary = dict_it;


    }

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

        wordLimitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            String selectedNumber = "Word Limit: " + wordLimitBox.getItemAt(wordLimitBox.getSelectedIndex());
            wordLimitLabel.setText(selectedNumber);
            prediction.setMaxCompletions(wordLimitBox.getItemAt(wordLimitBox.getSelectedIndex()));
            }
        });

        changeLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            String selectedLanguage = "Current Language: " + languageComboBox.getItemAt(languageComboBox.getSelectedIndex());
            currentLanguageLabel.setText(selectedLanguage);
            if(selectedLanguage.equals("English"))
            {
                prediction.setLanguage(Dictionary.Language.ENGLISH); 
            }
            else
            {
                prediction.setLanguage(Dictionary.Language.ITALIAN);
            }
            

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
        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        JLabel text = new JLabel("Enter word to search for in dictionary: ");
        
        searchWordTextField = new JTextField();
        searchWordTextField.setColumns(56);
        searchWordButton = new JButton("Search");
        searchWordTextArea = new JTextArea();

        topPanel.add(text);
        topPanel.add(searchWordTextField);
        topPanel.add(searchWordButton);
        searchWordButton.addActionListener(this);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(searchWordTextArea, BorderLayout.CENTER);

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
            predictText();

        } else if (e.getSource() == removeWordButton)
        {
            deleteUserEnteredWord();

        } else if (e.getSource() == searchWordButton)
        {
            displayTest();

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
            changeAddWordSetting();

        } else if (e.getSource() == addOffRadio)
        {
            currentAddSettingLabel.setText("Off");
            changeAddWordSetting();
        }
    }


    public void predictText()
    {
        predictTextArea.setText("");
            String textToComplete = predictTextField.getText();

            prediction.resetCompletions();
    
            boolean empty = dictionary.wordEnteredIsNull(textToComplete);
    
            if(empty == true)
            {
                predictTextArea.setText("Word Entered Is Empty\n");
                return;
            }
    
            // Remove multiple spaces from the words in the phrase - FIX by BT 30/3/22
            // String[] sentence = textToComplete.split(" ");
            String[] sentence = textToComplete.split("\\s+");
            
            for(int i = 0 ; i < sentence.length; i++)
            {
                // Get the completion for the last word in the phrase
                if(sentence.length - 1 == i)
                {
                    WordNode foundTextNode = dictionary.findNode(sentence[i], dictionary.getRoot());
    
                    // Check the partial word was found - FIX by BT 30/3/22
                    if (foundTextNode == null || foundTextNode.getNextNodes().isEmpty() == true)
                    {
                        predictTextArea.setText("No Completions Were Found In The Dictionary \n");
                        if (prediction.getAddWord() == true)
                        {
                            boolean added = dictionary.addWord(sentence[i]);
                            if(added == true)
                            {
                                predictTextArea.append("*" + sentence[i] + " Is Not Recognised But Has Been Added Due To AddWord Setting Being On \n"); 
                            }
                        }
                        dictionary.updateFrequency(sentence[i], 1);
                        return;
                    }
                    
                    prediction.predictText(foundTextNode, textToComplete);
                    getCompletions();
                }
                else
                {
                    // Add new words to the dictionary if the setting is on - update by BT 29/03/22
                    if (prediction.getAddWord() == true)
                    {
                        boolean added = dictionary.addWord(sentence[i]);
                        if(added == true)
                        {
                            predictTextArea.append("*" + sentence[i] + " Is Not Recognised But Has Been Added Due To AddWord Setting Being On \n"); 
                        }
                    }
                    
                    // Increase the frequency of the times this word has been used
                    dictionary.updateFrequency(sentence[i], 1);
                }
            }
    }


    public void getCompletions()
	{
		ArrayList<Integer> frequency = new ArrayList<Integer>();

		for(WordNode node : prediction.getCompletions())
		{
			frequency.add(Integer.valueOf(node.getFrequency()));
		}

		int numCompletions = prediction.getCompletions().size();

		for(int i = 0; i < Math.min(prediction.getMaxCompletions(), numCompletions); i++)
		{
			int pos = 0;
			int currentMax = frequency.get(0);
			for(int v = 0 ; v <= frequency.size()-1 ; v++)
			{
				if(currentMax < frequency.get(v))
				{
					currentMax = frequency.get(v);
					pos = v;
				}
			}

            predictTextArea.append(prediction.getWords().get(pos) + "\n");
			//System.out.println(prediction.getWords().get(pos));
			prediction.getCompletions().remove(pos);
			prediction.getWords().remove(pos);
			frequency.remove(pos);

		}	
	}


    public void deleteUserEnteredWord()
    {
        String word = removeWordTextField.getText();

            // Make sure the word entered contains some letters
		    if(dictionary.wordEnteredIsNull(word))
		    {
			    removeWordLabel.setText("Word Entered Is Null");
		    }
		
		    // Try removing the node for the word
		    else
		    {
			    if (dictionary.deleteNode(word, dictionary.getRoot()))
                    removeWordLabel.setText(word + " has been removed from the dictionary.");
			    else
                    removeWordLabel.setText("Word '" + word + "' does not exist in the dictionary.");
			    // dictionary.findNode(word, dictionary.getRoot());
		    }
    }


    /**
	 * Method to display the full dictionary trie from the root node
	 * @author Becky Tyler (2461535)
	 */
	public void displayTest()
	{
        searchWordTextArea.setText("");
		//String startWord = this.getString("Enter letter(s) to display the dictionary from, or press ENTER for full dictionary: ").trim();
        String startWord = searchWordTextField.getText();

		if (startWord.equals(""))
		{
			dictionary.displayDictionary(dictionary.getRoot(), "");
		}
		else  // Display the part of the dictionary with words starting with startWord
		{
			// Find the starting node;
			WordNode startNode = dictionary.findNode(startWord, dictionary.getRoot());

			if (startNode != null)
			{
				searchWordTextArea.append("DISPLAYING THE DICTIONARY: Words beginning with " + startWord);
				//System.out.println("==========================");
				// Display the dictionary from the start word
				dictionary.displayDictionary(startNode, startWord);
				//System.out.println("==========================\n");
			}
			else
                searchWordTextArea.append("Word Does Not Exist In the Dictionary");
		}
	}


    public void displayDictionary(WordNode currentNode, String nodeName)
    {
        // Print the information stored in the current node;
		if (currentNode.getIsWord() == true)
		{
			int freq = currentNode.getFrequency();
			if (freq <= 0)
                searchWordTextArea.append(nodeName);
			else
                searchWordTextArea.append(nodeName + " (" + freq + ")");
		}
		
		// Use recursion to print the information stored in the next nodes
		if (!currentNode.getNextNodes().isEmpty())
		{
			for (String letter : currentNode.getNextNodes().keySet())
			{
				displayDictionary(currentNode.getNextNodes().get(letter), nodeName + letter);	
			}
		}
    }


    public void findWord()
	{
		String wordToFind = searchWordTextField.getText();

		System.out.println("SEARCHING FOR THE WORD: " + wordToFind);
		System.out.println("=======================");
		WordNode foundNode = dictionary.findNode(wordToFind,dictionary.getRoot());
		if (foundNode == null)
			searchWordTextArea.setText("Node '" + wordToFind + "' not found.");
		else
            searchWordTextArea.setText("Node '" + wordToFind + "' found! " + foundNode.printInfo());
		System.out.println();
	}


    public String printInfo(WordNode word)
	{
		String printInfo = "";
		printInfo = "IsWord: " + word.getIsWord() +
			    ", Frequency: " + word.getFrequency() +
			    ", Next Nodes:";
		if (!word.getNextNodes().isEmpty())
		{
			for (String letter : word.getNextNodes().keySet())
			{
				printInfo = printInfo + " " + letter;
			}
		}
		else
			printInfo = printInfo + " null";
		return printInfo;
	}


    public void changeAddWordSetting()
    {
        if(prediction.getAddWord() == false)
		{
			prediction.setAddWord(true);
			System.out.println("\nAdd Word Setting Has Been Turned ON");
		}
		else
		{
			prediction.setAddWord(false);
			System.out.println("\nAdd Word Setting Has Been Turned OFF");
		}
    }


    public int getInt(String userPrompt)
	{
		Scanner s = new Scanner(System.in);
		
		System.out.print("\n" + userPrompt);
		while (!s.hasNextInt())
		{
			s.next();
			System.out.print(userPrompt);
		}
		
		int num = s.nextInt();
		return num;
	}


    public String getString(String userPrompt)
	{
		Scanner s = new Scanner(System.in);
		System.out.print("\n" + userPrompt);
		String userInput = s.nextLine();
		
		// Check that the input doesn't contain special characters
		Pattern p = Pattern.compile("[^a-z ]", Pattern.CASE_INSENSITIVE);
		Matcher match = p.matcher(userInput);
		while (match.find())
		{
			System.out.println("Sorry, numbers and special characters are not allowed.");
			System.out.print("\n" + userPrompt);
			userInput = s.nextLine();
			match = p.matcher(userInput);
		}
		
		return userInput.trim();
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
