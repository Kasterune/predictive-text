import java.util.Scanner;

/**
 * This class runs tests on the Dictionary class
 * 
 * @author Becky Tyler (2461535)
 * @version 1.0 (17 March 2022)
 */
public class Tester
{
	// Create a dictionary field
	Dictionary dictionary;
	Prediction prediction;
	
	
	/**
	 * Default constructor
	 */
	public Tester()
	{
		// Create a new dictionary trie
		dictionary = new Dictionary();
		prediction = new Prediction();
	}
	
	/**
	 * Main method to run tests from
	 */
	public static void main(String[] args)
	{

		Tester tester = new Tester();
		tester.runAddTests();
	}
	
	/**
	 * Method to run tests to add words to the dictionary trie
	 */
	public void runAddTests()
	{
		// Get the root of the trie
		WordNode root = dictionary.getRoot();
		
		// Print an empty dictionary
//		dictionary.printDictionary(root);
		
		// Add a word to the dictionary and print the trie
		//dictionary.addWord("to");
//		dictionary.printDictionary(root);
		
		// Add some more words - these are the words from the Wikipedia trie example
		//dictionary.addWord("tea");
		//dictionary.addWord("ted");
		//dictionary.addWord("ten");
		//dictionary.addWord("A");
		//dictionary.addWord("I");
		//dictionary.addWord("in");
		//dictionary.addWord("inn");
		// dictionary.printDictionary(root);
		enterSentence();
		Scanner s = new Scanner(System.in);
		System.out.println("Enter A Word");
		String word = s.nextLine();
		if(dictionary.wordEnteredIsNull(word))
		{
			System.out.println("\n");
		}
		else
		{
			dictionary.deleteNode(word, root);
			dictionary.findNode(word, root);
		}


	}

	public void enterSentence()
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter A Word");
		String textToComplete = s.nextLine();

		WordNode foundTextNode = dictionary.findNode(textToComplete, dictionary.getRoot());

		prediction.predictText(foundTextNode, textToComplete);
		prediction.getCompletions();

	}
	

}
