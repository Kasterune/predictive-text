import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles the text prediction part of the application
 *
 * @author Becky Tyler (2461535), Oliver Shearer (2455913)
 * @version 1.0 (24 March 2022)
 */
public class Prediction
{
	// Automatically add new words entered by the user into the dictionary?
	private boolean addWord;

	// The current language/dictionary being used
	Dictionary.Language language;

	// The maximum number of completions to be predicted
	private int maxCompletions;

	private ArrayList<WordNode> completions;

	private	ArrayList<String> words;

	/**
	 * Default constructor
	 */
	public Prediction()
	{
		addWord = false;
		language = Dictionary.Language.ENGLISH;
		maxCompletions = 7;
		completions = new ArrayList<WordNode>();
		words = new ArrayList<String>();
	}

	/**
	 * Method to get the 'addWord' setting to automatically add new words to the dictionary
	 * @return The value of the 'add word' setting (true or false)
	 */
	public boolean getAddWord()
	{
		return this.addWord;
	}

	/**
	 * Method to get the current language
	 * @return The current language
	 */
	public Dictionary.Language getLanguage()
	{
		return this.language;
	}

	/**
	 * Method to get the maximum number of completions for a prediction
	 * @return The maximum number of completions
	 */
	public int getMaxCompletions()
	{
		return this.maxCompletions;
	}


	public ArrayList<WordNode> getCompletions()
	{
		return this.completions;
	}


	public ArrayList<String> getWords()
	{
		return this.words;
	}


	/**
	 * Method to set the 'addWord' setting to automatically add new words to the dictionary
	 * @param addWord The 'addWord' setting (true/false) to automatically add new words to the dictionary
	 */
	public void setAddWord(boolean addWord)
	{
		this.addWord = addWord;
	}

	/**
	 * Method to set the current language
	 * @param language The current language of the dictionary
	 */
	public void setLanguage(Dictionary.Language language)
	{
		this.language = language;
	}

	/**
	 * Method to set the maximum number of completions for a prediction
	 * @param maxComp The new maximum number of completions
	 */
	public void setMaxCompletions(int maxComp)
	{
		this.maxCompletions = maxComp;
	}

	/**
	 * Method to reset the completions lists
	 * @author Becky Tyler (2461535)
	 */
	public void resetCompletions()
	{
		// Clear the list of completions ready for a new prediction
		this.completions.clear();
		this.words.clear();
	}

	public void predictText(WordNode foundTextNode, String textToComplete)
	{
		//WordNode foundTextNode = dictionary.findNode(textToComplete, node);

		if(foundTextNode.getNextNodes() != null)
		{
			Map<String, WordNode> nextNodeMap = foundTextNode.getNextNodes();

			for (String letter : nextNodeMap.keySet())
			{
				predictText(nextNodeMap.get(letter), textToComplete + letter);
			}
		}

		if (foundTextNode.getIsWord() == true)
		{
			completions.add(foundTextNode);
			words.add(textToComplete);
		}

	}


	/**
	 * Method to predict the possible word(s) the user has started to enter
	 * This is version 1 of this method which has since been deprecated
	 * Please see the new version of the predictText() method.
	 * @param dictionary The current dictionary object reference
	 * @param textToComplete The text to be completed/predicted
	 * @return A map of possible completions & their frequencies as a HashMap
	 * @author Becky Tyler (2461535)
	 */
	public Map<String, Integer> predictTextv1(Dictionary dictionary, String textToComplete)
	{
		// Find the node at the end of the word in the trie
		WordNode foundTextNode = dictionary.findNode(textToComplete);

		//If the text does not exist in the trie, there are no completions
		if(foundTextNode == null)
		{
		  return null;
		}

		// Create a hash map to store the list of possible completions
		Map<String, Integer> completions = new HashMap<String, Integer>();
		if (foundTextNode.getIsWord() == true)
			completions.put(textToComplete, foundTextNode.getFrequency());

		// Get the next nodes and add all the proper words from those nodes down the trie
		if (!foundTextNode.getNextNodes().isEmpty())
		{
			Map<String, Integer> tempMap = new HashMap<String, Integer>();

			// For each letter in the next nodes
			for (String letter : foundTextNode.getNextNodes().keySet())
			{
				// Get the completions for that node, storing it in a temporary hash map
				tempMap = this.predictTextv1(dictionary, textToComplete + letter);

				// Add the words from the temporary map into the final map of completions
				if (tempMap != null)
				{
					for (String tempWord : tempMap.keySet())
					{
						completions.put(tempWord, tempMap.get(tempWord));
					}
				}
			}

		}
		return completions;
	}
}
