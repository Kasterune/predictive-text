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
		// Set the root to be empty, i.e. not referring to any word nodes yet
		root = null;
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

	
	
	
}
