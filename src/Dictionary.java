import java.util.Map;

/**
 * This is the dictionary class which is represented by a Trie of words
 * @author Becky Tyler (2461535)
 * @version 1.0 (16 March 2022)
 */
public class Dictionary
{
	// Define field to hold the reference to the root of the tree
	private WordNode root;

	/**
	 * Default constructor
	 */
	public Dictionary()
	{
		// Set the root to be a blank node not referring to any other word nodes yet
		root = new WordNode();
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
	 * 
	 * @param newRoot WordNode reference to the node which will be the root of the trie. 
	 */
	public void setRoot(WordNode newRoot)
	{
		this.root = newRoot;
	}

	/**
	 * Method to add a word to the dictionary
	 * 
	 * This method splits the word up into all of its prefixes and 
	 * adds each prefix to the trie if it doesn't already exist in the trie
	 * If the word already exists, the frequency count is incremented by 1
	 * 
	 * NOTE: This method does not check that the input word is a proper word
	 * 
	 * @param word The word to add to the dictionary trie
	 */
	public void addWord(String word)
	{
		// Remove any leading or trailing spaces from the word
		word = word.trim();
		
		// Make sure the word contains some letters
		if (word.length() == 0)
		{
			System.out.println("NOTE: Word is empty, so cannot add it to the dictionary.");
			return;
		}
		
		// Check each prefix of the word, and add it to the dictionary if it doesn't already exist
		WordNode currentNode = root;
		Map<String, WordNode> nextNodeMap = root.getNextNodes();
		int wordPosition = 1;
		String prefix = word.substring(0, wordPosition);
			
		// Skip through the nodes where the prefixes already exist in the trie
		while (nextNodeMap.containsKey(prefix))
		{
			currentNode = nextNodeMap.get(prefix);
			
			// The word already exists in the trie so increment its frequency by 1
			if (wordPosition == word.length()) 
			{
				if (currentNode.getIsWord())
					currentNode.setFrequency(currentNode.getFrequency() + 1);
				System.out.println("NOTE: Frequency of word '" + prefix + "' increased.\n");
				return;
			}
			
			wordPosition++;
			prefix = word.substring(0, wordPosition);			
			nextNodeMap = currentNode.getNextNodes();
		}
		
		// Add nodes for the remaining prefixes and the word itself to the dictionary trie
		WordNode newNode;
		while (wordPosition <= word.length())
		{
			// Create a new word node and add the prefix
			newNode = new WordNode(false, 0);
			nextNodeMap.put(prefix, newNode);
			System.out.println("NOTE: Added: " + prefix);
			
			// Set the isWord and frequency for the full word
			if (wordPosition == word.length())
			{
				newNode.setIsWord(true);
				newNode.setFrequency(1);
				System.out.println("NOTE: Finished adding word '" + prefix + "'.\n");
				return;
			}
			
			// Get the next prefix to add
			wordPosition++;
			prefix = word.substring(0, wordPosition);			
			nextNodeMap = newNode.getNextNodes();
		}
	}
	
	/**
	 * Method to print out one of the word nodes in the trie
	 * @param node  The node to be printed out
	 */
	// public void printWordNode(WordNode node)
	// {
	// 	// Print the information stored in the node;
	// 	System.out.println(node.printInfo());
		
	// 	// Print the child nodes
	// 	if (node.getNextNodes().isEmpty())
	// 	{
	// 		System.out.println("Node '" + node.getLetters() + "' has no child nodes.\n");
	// 	}
	// 	else
	// 	{
	// 		System.out.println("Child nodes of " + node.getLetters() + ": ");
	// 		for (String prefix : node.getNextNodes().keySet())
	// 		{
	// 			System.out.print(prefix + " ");
	// 		}
	// 		System.out.println("\n");
	// 	}
	// }
	
	/**
	 * Method to print the dictionary trie from the current node down
	 * @param currentNode  The top node to start the printout from
	 */
	// public void printDictionary(WordNode currentNode)
	// {
	// 	this.printWordNode(currentNode);
		
	// 	if (!currentNode.getNextNodes().isEmpty())
	// 	{
	// 		for (String prefix : currentNode.getNextNodes().keySet())
	// 		{
	// 			this.printDictionary(currentNode.getNextNodes().get(prefix));
	// 		}
	// 	}
	// }
	
}
