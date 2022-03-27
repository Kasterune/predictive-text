import java.util.Map;
import java.util.Scanner;

/**
 * This class runs tests on the Dictionary class
 * 
 * @author Becky Tyler (2461535), Oliver Shearer (2455913)
 * @version 1.0 (17 March 2022)
 */
public class Tester
{
	// Create object reference to the dictionary
	Dictionary dictionary;
	Dictionary dictionaryI;
	
	// Object reference to the prediction
	Prediction prediction;
		
	/**
	 * Default constructor
	 */
	public Tester()
	{
		// Create a new dictionary trie
		dictionary = new Dictionary("English");
		dictionaryI = new Dictionary("Italian");
		
		// Create a new prediction
		prediction = new Prediction();
	}
	
	/**
	 * Main method to run tests from
	 */
	public static void main(String[] args)
	{
		Tester tester = new Tester();
		tester.processUserInput();

		// Test adding words to the dictionary trie
		//tester.runAddTests();
				
		// Test displaying the dictionary
		//tester.displayTest();
		
		// Test finding a word
//		tester.findTest("ten");
		
		// Test deleting a word from the dictionary entered by the user
//		tester.deleteUserEnteredWord();
		
		// Test the prediction to get some completions
//		tester.predictTestv1("t");
//		tester.predictTestv1("i");
		
		//tester.enterSentence();

	}
	

	/**
	 * Displays the menu to the user and asks them what option they would like to do and validates it and sends it to be processed
	 * 
	 * @return userChoice which is an integer representing the option that the user chose
	 */
	private int displayMenu() {
		
		boolean validChoiceProvided = false;
        int userChoice = 0;

        while (!validChoiceProvided) {
        	
        	System.out.println("\n----------MENU----------");
    		System.out.println("\n1. Enter A Word Or Phrase");
    		System.out.println("\n2. Change Limit Of Word Suggestions");
			System.out.println("\n3. Add Unrecognised Words To The Dictionary Setting");
			System.out.println("\n4. Change Language");
			System.out.println("\n5. Remove Word From Dictionary");
			System.out.println("\n6. Display The Dictionary");
			System.out.println("\n7. Save Dictionary");
    		System.out.println("\n8. Exit");
    		userChoice = getInt("\nEnter The Number Of The Option You Would Like To Choose: ");
    		
    		if ((userChoice >= 1) && (userChoice <= 3))
            {
                validChoiceProvided = true;  
            }
            else
            {
                System.out.println("\nSorry, that is not a valid choice");
            }
        	
        }
        	
        return userChoice;
	
	}

	private void processUserInput()
	{

		boolean exitChoiceSelected = false;
			
		while (!exitChoiceSelected) {
				
			Scanner s = new Scanner(System.in);
		    int userChoice = 0;
			userChoice = displayMenu();
				
			switch(userChoice) {
				
			case 1:
				enterSentence();
				break;

			case 2:
				updateWordLimit();
				break;
					
			case 3:
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
				break;

			case 4:
				if(prediction.getLanguage().equals("English"))
				{
					prediction.setLanguage("Italian");
					System.out.println("\nLanguage Has Been Switched To Italian");
				}
				else
				{
					prediction.setLanguage("English");
					System.out.println("\nLanguage Has Been Switched To English");
				}
				break;
				
			case 5:
				deleteUserEnteredWord();
				break;

			case 6:
				displayTest();
				break;

			case 7:
				
				break;

			case 8:
				exitChoiceSelected = true;
				break;
				
			default:
				System.out.println("\nInvalid Input, Try Again");
				displayMenu();
				
			}
		}
			
	}


	/**
	 * Method to run tests to add words to the dictionary trie
	 */
	public void runAddTests()
	{
		System.out.println("TESTING: Adding words to the dictionary");
		System.out.println("=======================================");
		
		// Get the root of the dictionary trie
		WordNode root = dictionary.getRoot();
		
		// Try printing an empty dictionary
		System.out.println("\nPRINTING AN EMPTY DICTIONARY TRIE:");
		dictionary.printDictionary(root, "");
		
		// Add a word to the dictionary and print the trie
		System.out.println("\nADDING WORD 'to' TO THE DICTIONARY TRIE:");
		dictionary.addWord("to");
		dictionary.printDictionary(root, "");
		
		// Add some more words - these are the words from the Wikipedia trie example
		System.out.println("\nADDING MORE WORDS TO THE DICTIONARY TRIE:");
		dictionary.addWord("tea");
		dictionary.addWord("ted");
		dictionary.addWord("ten");
		dictionary.addWord("A");
		dictionary.addWord("I");
		dictionary.addWord("in");
		dictionary.addWord("inn");
		dictionary.printDictionary(root, "");
		
		// Test deleting a node and adding it back in again
		System.out.println("\nDELETING THE WORD 'in' FROM THE DICTIONARY TRIE:");
		WordNode inNode = dictionary.findNode("in", root);
		dictionary.deleteNode("in", root);
		dictionary.printDictionary(inNode, "in");
		System.out.println("\nADDING THE WORD 'in' BACK INTO THE DICTIONARY TRIE:");
		dictionary.addWord("in");
		dictionary.printDictionary(inNode, "in");
		
		// Test updating the frequency for a word
		System.out.println("\nINCREASING THE FREQUENCY OF 'in' BY 4:");
		dictionary.updateFrequency("in", 4);
		dictionary.printDictionary(inNode, "in");
		System.out.println("\nINCREASING THE FREQUENCY OF 'in' BY ANOTHER 3:");
		dictionary.updateFrequency("in", 3);
		dictionary.printDictionary(inNode, "in");		
	}
	
	/**
	 * Method to test deleting a word entered by the user
	 * @author Oliver Shearer (2455913)
	 */
	public void deleteUserEnteredWord()
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter A Word");
		String word = s.nextLine();
		if(dictionary.wordEnteredIsNull(word))
		{
			System.out.println("\n");
		}
		else
		{
			dictionary.deleteNode(word, dictionary.getRoot());
			dictionary.findNode(word, dictionary.getRoot());
		}
	}

	/**
	 * Method to display the full dictionary trie from the root node
	 */
	public void displayTest()
	{
		System.out.println("DISPLAYING THE FULL DICTIONARY:");
		System.out.println("===============================");
		dictionary.displayDictionary(dictionary.getRoot(), "");
		System.out.println("===============================");
		System.out.println();		
	}

	/**
	 * Method to test the findNode method to search for a word and its node
	 * @param wordToFind The word to find
	 */
	public void findTest(String wordToFind)
	{
		// Search for a word
		System.out.println("SEARCHING FOR THE WORD: " + wordToFind);
		System.out.println("=======================");
		WordNode foundNode = dictionary.findNode(wordToFind,dictionary.getRoot());
		if (foundNode == null)
			System.out.println("Node '" + wordToFind + "' not found.");
		else
			System.out.println("Node '" + wordToFind + "' found!\n" + foundNode.printInfo());
		System.out.println();
	}
	
	/**
	 * Method to test the prediction method
	 * @param textToComplete The text to predict/complete
	 */
	public void predictTestv1(String textToComplete)
	{
		System.out.println("\nPREDICTING THE TEXT: " + textToComplete);
		System.out.println("====================");
		Map<String, Integer> completions = 
				prediction.predictTextv1(dictionary, textToComplete);
		if (completions != null)
		{
			System.out.println("Possible completions for " + textToComplete + ":");
			for (String text : completions.keySet())
			{
				System.out.println(text + " (" + completions.get(text) + ")");
			}
			System.out.println();
		}
	}

	
	public void enterSentence()
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter A Word");
		String textToComplete = s.nextLine();

		boolean empty = dictionary.wordEnteredIsNull(textToComplete);

		if(empty == true)
		{
			return;
		}

		String[] sentence = textToComplete.split(" ");
		
		for(int i = 0 ; i < sentence.length; i++)
		{
			if(sentence.length - 1 == i)
			{
				WordNode foundTextNode = dictionary.findNode(sentence[i], dictionary.getRoot());

				prediction.predictText(foundTextNode, textToComplete);
				prediction.getCompletions();
			}
			else
			{
				dictionary.updateFrequency(sentence[i], 1);
			}
		}

	}
	

	public void updateWordLimit()
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter The New Limit Of Word Suggestions");
		int limit = s.nextInt();

		if(limit < 1 || limit > 100)
		{
			System.out.println("Number Entered Is Out Of Range");
		}
		else
		{
			prediction.setMaxCompletions(limit);
		}
	}


	public int getInt(String userPrompt)
	{
		Scanner s = new Scanner(System.in);
		
		System.out.print(userPrompt);
		while (!s.hasNextInt())
		{
			s.next();
			System.out.print(userPrompt);
		}
		
		int num = s.nextInt();
		return num;
	}


}
