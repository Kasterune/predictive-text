import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * This is the dictionary class which is represented by a Trie of words
 * @author Becky Tyler (2461535), Joshua Price (2481545), Oliver Shearer (2455913)
 * @version 2.0 (25 March 2022)
 */
public class Dictionary
{
	// Define field to hold the reference to the root of the tree
	private WordNode root;

	enum Language {
		ENGLISH,
		ITALIAN
	}

	/**
	 * Default constructor
	 */
	public Dictionary(Language language)
	{
		// Set the root to be a blank node not referring to any other word nodes yet
		root = new WordNode();

		if (language.equals(Language.ENGLISH)) {
			loadWords(Language.ENGLISH);
		} else {
			loadWords(Language.ITALIAN);
		}
	}
	
	/**
	 * Get the root node which is at the top of the trie
	 * @return root The reference to the WordNode object at the top of the trie
	 *         (or null if the trie is empty).    
	 */
	public WordNode getRoot()
	{
		return root;
	}

	/**
	 * Set the root of the tree to the given node
	 * @param newRoot WordNode reference to the node which will be the root of the trie. 
	 */
	public void setRoot(WordNode newRoot)
	{
		this.root = newRoot;
	}


	public void loadWords(Language language)
	{
		File tempFile;
		String fileName;
		FileReader fileReader = null;
	    BufferedReader bufferedReader = null;
	    String nextLine;

		if(language.equals(Language.ENGLISH))
		{
			tempFile = new File("Saved_DictionaryE.txt");
			fileName = "EnglishUK.txt";
		}
		else
		{
			tempFile = new File("Saved_DictionaryI.txt");
			fileName = "ItalianItaly.txt";
		}
		boolean exists = tempFile.exists();
		
		if(exists == false)
		{
			try {
				fileReader = new FileReader(fileName);
			
				bufferedReader = new BufferedReader(fileReader);
	        
				nextLine = bufferedReader.readLine();
				while (nextLine != null) {
					addWord(nextLine);
					nextLine = bufferedReader.readLine();
				}
	        }
	        
	        catch (FileNotFoundException e)
	        {
	            System.out.println("Sorry, your file was not found.");
	        }
	        
	        catch (IOException e)
	        {
	            System.out.println("Sorry, there has been a problem opening or reading from the file");
	        }
	        
	        finally
	        {
	            // if the file was opened
	            if (bufferedReader != null)
	            {
	                try 
	                {
	                    // try to close it
	                    bufferedReader.close();
	                }
	                catch (IOException e)
	                {
	                    // warn user file wasn't properly closed
	                    System.out.println("Sorry, there has been a problem closing the file");
	                }
	            }
	        }
		}
	}


	/**
	 * Method to check if a word is in the dictionary
	 * @param word String to search for in the dictionary
	 * @return True if the word is in the dictionary, else false
	 */
	public boolean isInDictionary(String word)
	{
		return isInDictionary(word, root);
	}

	private boolean isInDictionary(String word, WordNode node)
	{
		if (word.length() == 0)
		{
			if (node.getIsWord())
			{
				return true;
			}
			return false;
		}

		if (node.getNextNodes().containsKey(word.substring(0, 1)))
		{
			return isInDictionary(word.substring(1), node.getNextNodes().get(word.substring(0, 1)));
		}
		return false;
	}

	/**
	 * Method to add a word to the dictionary
	 * This method adds any intermediate nodes required and then sets isWord to true
	 * for the end node.
	 * NOTE: This method does not check that the input word is a proper word
	 * @param word The word to add to the dictionary trie
	 * @author Original version by Becky Tyler (2461535), updated by Joshua Price (2481545)
	 */
	public void addWord(String word) {
		// Remove any leading or trailing spaces from the word and convert to lower case
		word = word.trim().toLowerCase();

		WordNode node = root;
		// Add a new node to the dictionary for each letter of word if it doesn't already exist
		for (int i = 0; i < word.length(); i++) {
			node.getNextNodes().putIfAbsent(word.substring(i, i + 1), new WordNode());
			node = node.getNextNodes().get(word.substring(i, i + 1));
		}

		// If word is empty then node will still be the root
		if (node != root) {
			node.setIsWord(true);
		}
	}

	/**
	 * Method to update the frequency for a word in the trie
	 * @param word The word in the dictionary to update the frequency for
	 * @param upNo The amount to update the frequency by
	 * @return true if frequency was updated, otherwise false
	 * @author Becky Tyler (2461535)
	 */
	public boolean updateFrequency(String word, int upNo)
	{
		// Remove any leading or trailing spaces from the word and convert to lower case
		word = word.trim().toLowerCase();
		
		// Find the node for the end of the word
		WordNode foundNode = this.findNode(word, root);

		// If the node exists, update the frequency
		if (foundNode != null)
		{
			foundNode.setFrequency(foundNode.getFrequency() + upNo);
			return true;
		}
		
		// Node not found so frequency not updated
		else
			return false;
	}
	
	/**
	 * Method to print out one of the word nodes in the trie
	 * This is just for testing and debugging purposes
	 * @param node  The node to be printed out
	 * @param prefix The name or word prefix for the current node
	 * @author Becky Tyler (2461535)
	 */
	public void printWordNode(WordNode node, String prefix)
	{		 
		// Print out the prefix for the node
		if ((node == this.root) && (prefix.equals("")))
			System.out.println("Node: *ROOT*");
		else
			System.out.println("Node: " + prefix);

		// Print the information stored in the node;
		System.out.println(node.printInfo() + "\n");
	}
	
	/**
	 * Method to print the dictionary trie from the current node down
	 * This is just for testing and debugging purposes
	 * @param currentNode  The top node to start the printout from
	 * @param nodeName The name or word prefix for the current node
	 * @author Becky Tyler (2461535)
	 */
	public void printDictionary(WordNode currentNode, String nodeName)
	{
		if (currentNode == this.root)
			nodeName = "";

		// Print the current node
		this.printWordNode(currentNode, nodeName);

		// Get the child nodes and print them if not null
		if (!currentNode.getNextNodes().isEmpty())
		{
			for (String prefix : currentNode.getNextNodes().keySet())
			{
				this.printDictionary(currentNode.getNextNodes().get(prefix), nodeName + prefix);
			}
		}
	}
	
	/**
	 * Method to display the words in the dictionary from the current node down
	 * @param currentNode  The node to start the display from
	 * @param nodeName The word prefix represented by the current node
	 * @author Becky Tyler (2461535)
	 */
	public void displayDictionary(WordNode currentNode, String nodeName)
	{
		// Print the information stored in the current node;
		if (currentNode.getIsWord() == true)
		{
			int freq = currentNode.getFrequency();
			if (freq <= 0)
				System.out.println(nodeName);
			else
				System.out.println(nodeName + " (" + freq + ")");
		}
		
		// Use recursion to print the information stored in the next nodes
		if (!currentNode.getNextNodes().isEmpty())
		{
			for (String letter : currentNode.getNextNodes().keySet())
			{
				this.displayDictionary(currentNode.getNextNodes().get(letter), nodeName + letter);		
			}
		}
	}
	
	public WordNode findNode(String word, WordNode node)
	{
		//String tempWord = word;
		Map<String, WordNode> nextNodeMap = node.getNextNodes();

		// Convert word to lower case - FIX by BT 30/3/22
		word = word.toLowerCase();
		
		while(nextNodeMap.containsKey(word.substring(0, 1)))
		{
			node = nextNodeMap.get(word.substring(0, 1));
			word = word.substring(1);

			if (word.length() == 0)
			{
				if (node.getIsWord())
				{
					//System.out.println("Word Exists In the Dictionary");
					//System.out.println("Word - " + tempWord);
					//System.out.println("Is It A Real Word - " + node.getIsWord());
					//System.out.println("Times Word Has Been Used - " + node.getFrequency() + " Times");
					return node;
				}
				//break;
				return node;  // FIX by BT: Return the node even if it isn't a full word (needed for prediction)
			}
			nextNodeMap = node.getNextNodes();
		}
		// System.out.println("Word Does Not Exist In the Dictionary");
		return null;
	}

	/**
	 * Method to delete a word from the dictionary
	 * @param word The word to be deleted
	 * @param node The starting node for the deletion
	 * @return true if word was deleted, otherwise false
	 * @author Oliver Shearer (2455913), updated by Becky Tyler (2461535)
	 */
	public boolean deleteNode(String word, WordNode node)
	{
		// Get the map of next nodes
		// String tempWord = word;
		Map<String, WordNode> nextNodeMap = node.getNextNodes();
		
		// Convert word to lower case - FIX by BT 30/3/22
		word = word.toLowerCase();
		
		// While there is a node for the next letter
		while(nextNodeMap.containsKey(word.substring(0, 1)))
		{
			// Get the node for the next letter in the word
			node = nextNodeMap.get(word.substring(0, 1));
			word = word.substring(1);

			// If this is the last letter in the word
			if (word.length() == 0)
			{
				// If the word is a proper word, 'remove' it by setting isWord to false
				if (node.getIsWord())
				{
					node.setIsWord(false);
					node.setFrequency(0);
					// System.out.println(tempWord + " Has Been Removed From The Dictionary");
					return true;
				}
				// Otherwise the word does not exist in the dictionary
				// System.out.println("Word Does Not Exist In the Dictionary");
				return false;
			}
			nextNodeMap = node.getNextNodes();
		}
		
		// If there are not enough nodes for all the letters the word does not exist
		// System.out.println("Word Does Not Exist In the Dictionary");
		return false;
	}

	public boolean wordEnteredIsNull(String word)
	{
		// Remove any leading or trailing spaces from the word
		word = word.trim();
		
		// Make sure the word contains some letters
		if (word.length() == 0)
		{
			System.out.println("Word Entered Is Null");
			return true;
		}
		return false;
	}

}
