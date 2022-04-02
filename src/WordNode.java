import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class stores the data for a node in the dictionary Trie
 * 
 * @author Becky Tyler (2461535), Joshua Price (2481545)
 * @version 2.0 (16 March 2022)
 */

public class WordNode implements Serializable
{
	// Define class variables/fields
	private boolean isWord;		// Is the current node the end of a proper word? 
	private int     frequency;	// Number of times word has been used (if isWord is true)
	
	// Define a map to hold the references to the child nodes, indexed on the letters
	private Map<String, WordNode> nextNodes;
	
	/**
	 * Default constructor
	 */  
	public WordNode()
	{
		// Initialise field values
		isWord     = false;
		frequency  = 0;
		
		// Initialise the hash map for the next nodes
		nextNodes  = new HashMap<String, WordNode>();
	}
	
	/**
	 * Alternative constructor to set fields to given values.
	 * @param isWord  Do the letters make a proper word
	 * @param frequency  The number of times this word has been used (if isWord is true)
	 */
	public WordNode(boolean isWord, int frequency)
	{
		// Set data value to value provided       
		this.frequency = frequency;
		this.isWord = isWord;
		
		// Initialise the hash map for the next nodes
		nextNodes  = new HashMap<String, WordNode>();
	}

	/**
	 * Method to get the value of the 'isWord' field 
	 * @return isWord Whether the letters are a valid word or not
	 */
	public boolean getIsWord()
	{
		return this.isWord;
	}
	
	/**
	 * Method to get the frequency of the word (if isWord is true)
	 * @return frequency The number of times the user has used this word
	 */
	public int getFrequency()
	{
		return this.frequency;
	}
	
	/**
	 * Method to get the map of child nodes
	 * @return nextNodes The map containing the references to the child nodes
	 */
	public Map<String, WordNode> getNextNodes()
	{
		return this.nextNodes;
	}

	/**
	 * Method to set the 'isWord' field (whether or not the letters make a proper word)
	 * @param isWord The new value for isWord
	 */
	public void setIsWord(boolean isWord)
	{
		this.isWord = isWord;
	}
	
	/**
	 * Method to set the frequency of the word (if isWord is true)
	 * @param frequency  The new frequency of the word
	 */
	public void setFrequency(int frequency)
	{
		this.frequency = frequency;
	}
	
	/**
	 * Method to set the map of references to the child nodes
	 * @param nextNodes The map of references to the child nodes
	 */
	public void setNextNodes(Map<String, WordNode> nextNodes)
	{
		this.nextNodes = nextNodes;
	}
	
	/**
	 * Method to get the information about the node ready for printing
	 * @return String containing the letters, if these are a word, and the frequency
	 * @author Becky Tyler (2461535)
	 */
	public String printInfo()
	{
		String printInfo = "";
		printInfo = "IsWord: " + this.isWord +
			    ", Frequency: " + this.frequency +
			    ", Next Nodes:";
		if (!this.nextNodes.isEmpty())
		{
			for (String letter : this.nextNodes.keySet())
			{
				printInfo = printInfo + " " + letter;
			}
		}
		else
			printInfo = printInfo + " null";
		return printInfo;
	}
}


