import java.util.Scanner;

/**
 *  This program prompts for a string of 3 to 10 letters, then searches the
 *  wordlist.txt database to see what words it matches.
 *
 *  Requires classes FileUtils and Prompt.
 *
 *  @author Anishka Chauhan
 *  @since September 28, 2020
 *
 *	Algorithm:
 *	- Input string of letters
 *	- Check if string is only letters
 *	- Open wordlist.txt and read each word
 *		o test word with letters
 *		o if they match, add word to list
 */

public class EveryWord {
	private String [][] words;		// words found from letters, each row number
									// is the number of characters in the words
	private int [] numWords;		// the number of words in each row

	private final String WORD_DB = "wordlist.txt";	// word database

	public EveryWord() {
		words = new String[15][1000];
		numWords = new int[15];
	}

	public static void main(String[] args) {
		EveryWord ew = new EveryWord();
		ew.run();
	}

	/**	Prompts for a series of letters and finds all words contained within. */
	public void run() {
		boolean done = false;
		do
		{
			String letters = Prompt.getString("\nEnter 3-15 letters");
			letters = letters.toLowerCase();
			// less than 3 letters, quit
			if (letters.length() < 3)
				done = true;
			// if not all letters, then print message
			else if (!isAllLetters(letters))
				System.out.println("ERROR: Must be all letters.");
			// otherwise find all words
			else
			{
				findWords(letters);
				printWords();
			}
		}
		while (!done);
	}

	/**	Find all the words that match a string of letters. */
	public void findWords(String letters) {
		// clear the word array
		clearArrays();
		// open the word database
		Scanner inFile = FileUtils.openToRead(WORD_DB);
		// Check each word in the database against the letters
		while (inFile.hasNext())
		{
			String word = inFile.next();
			// if word matches letters, then store in words
			if (wordMatch(word, letters))
			{
				//System.out.println(wordMatch(word, letters));
				words[word.length()][numWords[word.length()]] = word;
				numWords[word.length()]++;
			}
		}
		inFile.close();
	}

	/**	Decides if a word matches a group of letters.
	 *  @param word  The word to test.
	 *  @param letters  A string of letters to compare
	 *  @return  true if the word matches the letters, false otherwise
	 */
	private boolean wordMatch (String word, String letters) {
		for (int a = 0; a < word.length(); a++)
		{
			char c = word.charAt(a);
			// character does not appear in letters
			int index = letters.indexOf(c);
			if (index == -1) return false;
			// take character out of letters
			letters = letters.substring(0, index) + letters.substring(index + 1);

		}

		return true;
	}

	/**	Print the list of words that match to the screen. */
	private void printWords() {
		System.out.println();
		for (int row = 3; row < words.length; row++)
		{
			for (int col = 0; col < numWords[row]; col++)
			{
				System.out.printf("%-10s ", words[row][col]);
				if ((col + 1) % 8 == 0) System.out.println();
			}
			System.out.println();
		}
		System.out.println("\n");
	}

	/**	Set the numWords array to zeros */
	private void clearArrays() {
		for (int i = 0; i < 15; i++) numWords[i] = 0;
	}

	/**	Test if string is all letters.
	 *	@param str		the string of characters to test
	 *	@return			true if all letters; false otherwise
	 */
	private boolean isAllLetters(String str) {
		for (int a = 0; a < str.length(); a++)
			if (!Character.isLetter(str.charAt(a)))
				return false;
		return true;
	}
}
