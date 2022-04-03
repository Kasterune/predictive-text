import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is a spell checker for words using the current dictionary
 * @author Becky Tyler (2461535)
 * @version 1.0 (31 March 2022)
 */
public class SpellChecker
{
	// Reference to the current dictionary
	Dictionary dictionary;  

	// Reference to the current prediction object
	Prediction prediction;  

	/**
	 * Default constructor
	 * @param dictionary  The dictionary object to check words against
	 */
    public SpellChecker(Dictionary dictionary, Prediction prediction)
    {
        this.dictionary = dictionary;
        this.prediction = prediction;
    }


	/**
	 * Method to check the spelling of a word
	 * @param phraseToCheck The phrase to check the spelling of
	 * @return output ArrayList of Strings to be output wherever wanted
	 * @author Becky Tyler (2461535)
 	 */
	public ArrayList<String> checkSpelling(String phraseToCheck)
	{
		ArrayList<String> output = new ArrayList<String>();
		
		// Check there are actually letters in the phrase
		phraseToCheck = phraseToCheck.trim();
		if (phraseToCheck.length() == 0)
		{
			output.add("Phrase is empty, there is nothing to check.\n");
			return output;
		}
		
		// Split the phrase up into its individual words
		String[] words = phraseToCheck.split("\\s+");
		
		// ArrayList to store the possible new spellings for each word
		ArrayList<String> newSpellings = new ArrayList<String>();
		
		for (int i = 0; i < words.length; i++)
		{
			if(!isSpecial(words[i]))
			{
				if(!checkWord(words[i]))
				{
					output.add("\n'" + words[i] + "' may be spelled incorrectly. Possible correct spellings:");
					newSpellings = findNewSpelling(words[i]);
					for (String spelling : newSpellings)
					{
						output.add("\t- " + spelling);
					}
				}
				else
				{
					output.add("\n'" + words[i] + "' is spelled correctly.");					
				}
			}
		}
		return output;		
	}

	/**
	 * Method to check whether a word contains numbers or special characters
	 * @param word  The word to check
	 * @return True if there are numbers or special characters in the word
	 * @author Becky Tyler (2461535)
 	 */
	public boolean isSpecial(String word) 
	{
		Pattern pattern;
		pattern = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
		Matcher matcher;
		matcher = pattern.matcher(word);
		return matcher.find();
	}

	/**
	 * Method to check whether a word is in the dictionary
	 * @param word The word to check
	 * @return True if the word is in the dictionary, otherwise false
	 * @author Becky Tyler (2461535)
 	 */
	public boolean checkWord(String word)
	{
		word = word.trim().toLowerCase();
		return dictionary.isInDictionary(word);
	}

	/**
	 * Method to find the correct spelling for a misspelled word
	 * @param word The word to find the correct spelling for
	 * @return ArrayList of possible spellings
	 * @author Becky Tyler (2461535)
 	 */
	public ArrayList<String> findNewSpelling(String word)
	{
		ArrayList<String> spellings = new ArrayList<String>();

		// Assume the first letter of the word is correct
		// Find the node in the dictionary for the first letter
		word = word.trim().toLowerCase();
		String firstLetter = word.substring(0, 1);
		WordNode firstLetterNode = dictionary.findNode(firstLetter, dictionary.getRoot());

		// If there are no nodes starting with the first letter then
		// no spelling suggestions can be made		
		if (firstLetterNode == null)
			return spellings;

		// Find words in the dictionary starting with the same letter and with the same length
		prediction.predictText(firstLetterNode, firstLetter);

		// Assume the word being checked has the correct number of letters
		int wordLength = word.length();
		
		ArrayList<String> predictions = prediction.getWords();
		
		// Only keep words in the list with the same number of letters
		for (String predictedWord : predictions)
		{
			if (predictedWord.length() == wordLength)
			{
				// Count the number of unmatched letters in the word
				int unmatched = 0;
				for (int i = 0; i < wordLength; i++)
				{
					if (!predictedWord.substring(i, i+1).equals(word.substring(i, i+1)))
					{
						unmatched = unmatched + 1;
					}
				}
				
				// Only keep those predictions with a small number of unmatched letters
				if (((wordLength < 6) && (unmatched < 2)) ||
					((wordLength >=6) && (unmatched < 3)))
					spellings.add(predictedWord);
			}
		}	
		return spellings;
	}
}