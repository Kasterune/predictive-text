import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * This is the dictionary class which is represented by a Trie of words
 * @author Becky Tyler (2461535), Joshua Price (2481545), Oliver Shearer (2455913)
 * @version 2.0 (25 March 2022)
 */
public class Dictionary implements Serializable
{
	// Define field to hold the reference to the root of the tree
	private WordNode root;

	// Define field to hold the two separate dictionaries
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


	/**
	 * Load words method that will take the data from the files in the same folder and will load that data into the program when started by the user.
	 * If the saved files dont exist then a new file will be created so that the dictionary data can be saved to it.
	 * @param language is the language currently being used in the program
	 */
	public void loadWords(Language language)
	{
		File tempFile;
		String fileName;
		FileReader fileReader = null;
	    BufferedReader bufferedReader = null;
	    String nextLine;

		if(language.equals(Language.ENGLISH))
		{
			tempFile = new File("DictionaryE.save");		// Checks to see if there are saved files in the same folder
			fileName = "EnglishUK.txt";
		}
		else
		{
			tempFile = new File("DictionaryI.save");
			fileName = "ItalianItaly.txt";
		}
		boolean exists = tempFile.exists();

		if(exists == false)									// If the saved files dont exist then the words are loaded in from the word file 
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
		} else {															// If the files do exist then the data from the file will be loaded in so that the user can use there saved dictionary
			ObjectInputStream in = null;
			String loadFile = language.equals(Language.ENGLISH) ? "DictionaryE.save" : "DictionaryI.save";

			try {
				FileInputStream fis = new FileInputStream(loadFile);
				in = new ObjectInputStream(fis);

				Dictionary dict = (Dictionary) in.readObject();
				root = dict.root;

			} catch (FileNotFoundException e) {
				System.out.println("Error opening file: " + e);
				System.exit(1);
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error reading from file: " + e);
				System.exit(1);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						System.out.println("Error closing file: " + e);
					}
				}
			}
		}
	}

	/**
	 * Saves current dictionary to file.
	 * @param language is the language currently being used in the program
	 * @return true or false depending on whether the save was successful
	 */
	public boolean saveToFile(Language language) {
		ObjectOutputStream out = null;
		String saveFile = language.equals(Language.ENGLISH) ? "DictionaryE.save" : "DictionaryI.save";

		try {
			FileOutputStream fos = new FileOutputStream(saveFile);
			out = new ObjectOutputStream(fos);

			out.writeObject(this);

		} catch (FileNotFoundException e) {
			System.out.println("Error opening file: " + e);
			return false;
		} catch (IOException e) {
			System.out.println("Error writing to file: " + e);
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					System.out.println("Error closing file: " + e);
					return false;
				}
			}
		}
		return true;
	}


	/**
	 * Checks the benchmark of our program
	 * @param lang is the current dictionary being used 
	 */
	public void benchmark(Language lang) {
		if (!lang.equals(Language.ENGLISH)) {
			throw new IllegalArgumentException("English is the only supported language for the benchmark.");
		}

		BufferedReader bufferedReader = null;
		long startTime = 0;

		try {
			FileReader fileReader = new FileReader("test_words.txt");
			bufferedReader = new BufferedReader(fileReader);
			Prediction prediction = new Prediction();

			String nextLine;
			startTime = System.nanoTime();
			while ((nextLine = bufferedReader.readLine()) != null) {
				prediction.predictText(findNode(nextLine), nextLine);


				// TODO: Dedupe this as it's mostly a duplicate of the two other getPredictions methods.
				ArrayList<Integer> frequency = new ArrayList<Integer>();

				for (WordNode node : prediction.getCompletions()) {
					frequency.add(Integer.valueOf(node.getFrequency()));
				}

				int numCompletions = prediction.getCompletions().size();

				for (int i = 0; i < Math.min(prediction.getMaxCompletions(), numCompletions); i++) {
					int pos = 0;
					int currentMax = frequency.get(0);
					for (int v = 0; v <= frequency.size() - 1; v++) {
						if (currentMax < frequency.get(v)) {
							currentMax = frequency.get(v);
							pos = v;
						}
					}

					// System.out.println(prediction.getWords().get(pos));
					prediction.getCompletions().remove(pos);
					prediction.getWords().remove(pos);
					frequency.remove(pos);

				}

				prediction.resetCompletions();

			}
		} catch (FileNotFoundException e) {
			System.out.println("Sorry, your file was not found.");
		} catch (IOException e) {
			System.out.println("Sorry, there has been a problem opening or reading from the file");
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					System.out.println("Sorry, there has been a problem closing the file");
				}
			}
		}
		long endTime = System.nanoTime();
		System.out.println("24215 completions took " + (endTime - startTime) / 1_000_000 + " milliseconds to complete");
	}

	/**
	 * Method to check if a word is in the dictionary
	 * @param word Word to search for in the dictionary
	 * @return True if the word is in the dictionary, else false
	 */
	public boolean contains(String word) {
		word = word.trim().toLowerCase();

		WordNode node = root;
		// Attempt to reach the node for word
		for (int i = 0; i < word.length(); i++) {
			if ((node = node.getNextNodes().get(word.substring(i, i + 1))) == null) {
				return false;
			}
		}

		return node.getIsWord();
	}

	/**
	 * Method to add a word to the dictionary
	 * This method adds any intermediate nodes required and then sets isWord to true
	 * for the end node.
	 * NOTE: This method does not check that the input word is a proper word
	 * @param word The word to add to the dictionary trie
	 * @author Original version by Becky Tyler (2461535), updated by Joshua Price (2481545)
	 * @return True if the word added is a new word, false if it was already in the dictionary.
	 */
	public boolean addWord(String word) {
		// Remove any leading or trailing spaces from the word and convert to lower case
		word = word.trim().toLowerCase();

		WordNode node = root;
		// Add a new node to the dictionary for each letter of word if it doesn't already exist
		for (int i = 0; i < word.length(); i++) {
			node.getNextNodes().putIfAbsent(word.substring(i, i + 1), new WordNode());
			node = node.getNextNodes().get(word.substring(i, i + 1));
		}

		boolean wasWord = node.getIsWord();
		// If word is empty then node will still be the root
		if (node != root) {
			node.setIsWord(true);
		}
		return !wasWord;
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
		WordNode foundNode = findNode(word);

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


	/**
	 * Will find the node of the word that was passed in
	 * @param word is the word that is going to be searched for in the tree
	 * @return the node of the word or null if the word does not exist in the dictionary.
	 */
	public WordNode findNode(String word) {
		word = word.trim().toLowerCase();

		WordNode node = root;
		// Attempt to reach the node for word
		for (int i = 0; i < word.length(); i++) {
			if ((node = node.getNextNodes().get(word.substring(i, i + 1))) == null) {
				return null;
			}
		}

		// Return the node even if it isn't a full word (needed for prediction)
		return node;
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


	/**
	 * This method will check to see if the word entered by a user is empty
	 * @param word is the word entered by the user that is to be checked
	 * @return false if it is empty and true if it is empty
	 */
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
