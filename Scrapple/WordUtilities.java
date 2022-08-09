import java.util.Scanner;

/**
 *  Finds all the words that can be formed with a list of letters,
 *  then finds the word with the highest score according to a table.
 *
 *	Requires FileUtils and Prompt classes and wordlist.txt fiile.
 *
 *	@author Anishka Chauhan
 *	@since October 1, 2020
 *
 */

public class WordUtilities
{
	private static final String WORDS_DB = "wordlist.txt";

	/**
	 *  Enter a list of 3 to 15 letters. It returns a string that is all
	 *  lowercase alphabetic characters.
	 *
	 *  @return		A string of the letters
	 */
	public static String getInput ( )
	{
    boolean done = false;
    String letters = "";
		do
		{
      done = false;
			letters = Prompt.getString("\nEnter 3-15 letters");
			letters = letters.toLowerCase();
			// less than 3 letters, quit
			if (letters.length() < 3 || letters.length() > 15)
				done = true;
      else if (!isWordInList(letters))
        done = true;
		}
		while (done);
		return letters;
	}

	/**
	 *  Find all words that can be formed by a list of letters from the WORD_DB.
	 *	Postcondition: The returned array of words found is exactly the number (length)
	 *					of the words found.
	 *  @param letters	String list of letters
	 *  @return			An array of strings with all words found.
	 */
	public static String [] findAllWords (String letters)
	{
    int counter = 0; // counter for the number of words
    // open the word database
    Scanner inFile = FileUtils.openToRead(WORDS_DB);
    // Check each word in the database against the letters
    while (inFile.hasNext())
    {
      String word = inFile.next();
      // if word matches letters, then add to counter
      if (matchLetters(word, letters))
      {
        counter++;
      //  System.out.println(word);
      }
    }
    inFile.close();
    String [] rightWords = new String [counter]; // creates array using counter variable to ensure proper size

    Scanner inFile2 = FileUtils.openToRead(WORDS_DB);
    // Check each word in the database against the letters
    int i = 0; // counter for moving forward in array
    while (inFile2.hasNext())
    {
      String word = inFile2.next();
      // if word matches letters, then store in rightwords
      if (matchLetters(word, letters))
      {
        rightWords[i] = word;
        i++;
      }
    }
    inFile2.close();

    return rightWords;

	}

	/**
	 *  Determines if a word can be formed by a list of letters.
	 *
	 *  @param str		The word to be tested.
	 *  @param letters	A string of the list of letters
	 *  @return   		true if word can be formed, false otherwise
	 */
	public static boolean matchLetters (String str, String letters)
	{
    for (int a = 0; a < str.length(); a++)
    {
      char c = str.charAt(a);
      // character does not appear in letters
      int index = letters.indexOf(c);
      if (index == -1) return false;
      // take character out of letters
      letters = letters.substring(0, index) + letters.substring(index + 1);

    }
    return true;
	}

	/**
	 *  Print the words found to the screen.
	 *
	 *  @param words	The string array containing the words.
	 */
	public static void printWords (String [] words)
	{
    System.out.println();
    for (int i = 0; i < words.length; i++)
    {
      System.out.printf("%2s ", words[i]);

    }
    System.out.println("\n");

	}

	/**
	 *  Finds the highest scoring word according to Scrabble rules.
	 *
	 *  @param word			An array of words to check.
	 *  @param scoreTable	An array of 26 integer scores in letter order.
	 *  @return				The word with the highest score.
	 */
	public static String bestWord (String [] word, int [] scoreTable)
	{
    String bestWord = word[0]; // sets the first word in array as largest in case first word is the best word
    int largest = getScore(word[0], scoreTable); // uses the first word's score as the largest and compares it in the loop
    int wordScore = 0; // score of word in array
    for (int i = 1; i < word.length; i++) // this for loop traverses through the words array starting at 1
    {
      String wrd = word[i]; // sets wrd to the string at the index of the array
      wordScore = getScore(wrd, scoreTable); // gets score of word at index
      if (largest < wordScore) // if greater than previous, sets bestWord to the word and largest to the score of word
      {
        largest = wordScore;
        bestWord = wrd;
      }
    }
		return bestWord;
	}

	/**
	 *  Calculates the score of a word according to Scrabble rules.
	 *
	 *  @param word			The word to score all in lowercase
	 *  @param scoreTable	The array of 26 scores for alphabet.
	 *  @return				The integer score of the word.
	 */
	public static int getScore (String word, int [] scoreTable)
	{
    int wordScore = 0;
    for (int x = 0; x < word.length(); x++) // traverses through each character in the word
    {
      word = word.toLowerCase();
      char c = word.charAt(x); // takes a character at the given index
      int position = c - 97; // calculates position in the
      wordScore += scoreTable[position]; // adds to the score until loop finishes
    }

    return wordScore;


	}

	/**
	 *  Determine if word is in wordlist.
	 *
	 *  @param wordToMatch	The word to search in the wordlist.
	 *  @return				True if the word is found, otherwise false
	 */
	public static boolean isWordInList(String wordToMatch)
	{

      Scanner inFile = FileUtils.openToRead(WORDS_DB);

		  while (inFile.hasNext())
      {
        String word = inFile.next(); // word in file
				String sequence = wordToMatch; // sequence of letters given (ediited, so stored in local variable)
				String temp = "";
        // if word matches letters, then return true
				if (word.equals(wordToMatch))
				{
					return true;
				}
				for (int i = 0; i < word.length(); i++) // builds a temporary word by checking if a character can be found in the letter sequence
				{
					char c = word.charAt(i);
					int index = sequence.indexOf(c);
					if (index != -1)
					{
						temp = temp + c + "";
						sequence = sequence.substring(0, index) + sequence.substring(index + 1); // removes character from sequence to avoid repetiton

					}
				}
			//	System.out.println(temp);
        if (temp.equals(word)) // if this temporary word is equal to the word from file, then true is returned
				{
					return true;
				}

      }
      inFile.close();

			return false;

	}

	/*********************************************************************/
	/*************************  For Testing  *****************************/
	/*********************************************************************/
	public static void main (String [] args)
	{
		String input = getInput();
	  String [] word = findAllWords(input);

		printWords(word);

		// Score table in alphabetic order according to Scrabble
		int [] scoreTable = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
		String best = bestWord(word,scoreTable);
		System.out.println("\nBest word: " + best);
		System.out.println("\nBest word score: " + getScore(best, scoreTable) + "\n");
	}

}
