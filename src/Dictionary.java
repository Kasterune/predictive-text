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
	 * 
	 * This method adds any intermediate nodes required and then sets isWord to true
	 * for the end node.
	 * 
	 * NOTE: This method does not check that the input word is a proper word
	 * 
	 * @param word The word to add to the dictionary trie
	 */
	public void addWord(String word)
	{
		// Remove any leading or trailing spaces from the word and convert to lowercase
		word = word.trim().toLowerCase();

		addWord(word, root);
	}

	private void addWord(String word, WordNode node)
	{
		// Make sure the word contains some letters, or if fully recursed set word to true, and exit
		if (word.length() == 0)
		{
			if (node != root)
			{
				node.setIsWord(true);
			}
			return;
		}

		// Add prefix node to the dictionary if it doesn't already exist
		if (!node.getNextNodes().containsKey(word.substring(0, 1)))
		{
			node.getNextNodes().put(word.substring(0, 1), new WordNode());
		}

		addWord(word.substring(1), node.getNextNodes().get(word.substring(0, 1)));
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
