/**
*  A simple version of the Scrabble game where the user plays against the computer.
*  The user is first given the game rules. A set of letter tiles are given and a hand of 8 tiles is dealt to the
*  computer and to the player. The player has to create a valid word that is between four and 8 tiles long and in the dictionary.
*	 The player gets a bonus if the word they choose has double letters in it. The computer always chooses the highest scoring word.
*	 The game ends if the player enters a word that is too small, too big, or not in the words list or their hand. The game also
*  ends if the letters run out or cannot form any words.
*
*	This requires the Prompt, FileUtils, and WordUtilities classes, and wordlist.txt.
*
*  @author	Anishka Chauhan
*  @since	October 9th, 2020
*/
import java.util.Scanner;

public class Scrapple {
	// array of scores for each letter
	public int [] scores = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10,
		1, 1, 1, 1, 4, 4, 8, 4, 10};
		// The word tiles
		private String tilesRemaining = "AAAAAAAAAABBCCDDDDEEEEEEEEEEEEEFFGGGHHIIIIIIIII" +
		"JKLLLLMMNNNNNNOOOOOOOOPPQRRRRRRSSSSTTTTTTUUUUVVWWXYYZ";
		private final int NUM_TILES = 8;		// the number of tiles
		private final int MIN_WORD_LENGTH = 4;	// minimum of 4 characters
		private int playerScore, compScore; // the scores of player and computer
		private boolean endGame; // determinds if the game has to end or not
		private String playerHand, compHand; // the hands of the computer and player

		// sets fields to default values
		public Scrapple()
		{
			endGame = false;
			playerScore = compScore = 0;
			playerHand = compHand = "";
		}

		public static void main(String [] args)
		{
			Scrapple run = new Scrapple();
			run.printIntroduction(); // prints introduction
			run.startGame(); // runs game
		}

		/**
		*  Print the introduction screen for Scrapple.
		*/
		public void printIntroduction() {
			System.out.print(" _______     _______     ______     ______    ");
			System.out.println(" ______    ______   __          _______");
			System.out.print("/\\   ___\\   /\\  ____\\   /\\  == \\   /\\  __ \\   ");
			System.out.println("/\\  == \\  /\\  == \\ /\\ \\        /\\  ____\\");
			System.out.print("\\ \\___   \\  \\ \\ \\____   \\ \\  __<   \\ \\  __ \\  ");
			System.out.println("\\ \\  _-/  \\ \\  _-/ \\ \\ \\_____  \\ \\  __\\");
			System.out.print(" \\/\\______\\  \\ \\______\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\ ");
			System.out.println(" \\ \\_\\     \\ \\_\\    \\ \\______\\  \\ \\______\\");
			System.out.print("  \\/______/   \\/______/   \\/_/ /_/   \\/_/\\/_/ ");
			System.out.println("  \\/_/      \\/_/     \\/______/   \\/______/ TM");
			System.out.println();
			System.out.print("This game is a modified version of Scrabble. ");
			System.out.println("The game starts with a pool of letter tiles, with");
			System.out.println("the following group of 100 tiles:\n");

			for (int i = 0; i < tilesRemaining.length(); i ++) {
				System.out.printf("%c ", tilesRemaining.charAt(i));
				if (i == 49) System.out.println();
			}
			System.out.println("\n");
			System.out.printf("The game starts with %d tiles being chosen at ran", NUM_TILES);
			System.out.println("dom to fill the player's hand. The player must");
			System.out.printf("then create a valid word, with a length from 4 to %d ", NUM_TILES);
			System.out.println("letters, from the tiles in his/her hand. The");
			System.out.print("\"word\" entered by the player is then checked. It is first ");
			System.out.println("checked for length, then checked to make ");
			System.out.print("sure it is made up of letters from the letters in the ");
			System.out.println("current hand, and then it is checked against");
			System.out.print("the word text file. If any of these tests fail, the game");
			System.out.println(" terminates. If the word is valid, points");
			System.out.print("are added to the player's score according to the following table ");
			System.out.println("(These scores are taken from the");
			System.out.println("game of Scrabble):");

			// Print line of letter scores
			char c = 'A';
			for (int i = 0; i < 26; i++) {
				System.out.printf("%3c", c);
				c = (char)(c + 1);
			}
			System.out.println();
			for (int i = 0; i < scores.length; i++) System.out.printf("%3d", scores[i]);
			System.out.println("\n");

			System.out.print("The score is doubled if the word has consecutive double ");
			System.out.println("letters (e.g. ball).\n");

			System.out.print("Once the player's score has been updated, more tiles are ");
			System.out.println("chosen at random from the remaining pool");
			System.out.printf("of letters, to fill the player's hand to %d letters. ", NUM_TILES);
			System.out.println("The player again creates a word, and the");
			System.out.print("process continues. The game ends when the player enters an ");
			System.out.println("invalid word, or the letters in the");
			System.out.println("pool and player's hand run out. Ready? Let's play!\n");

			Prompt.getString("HIT ENTER on the keyboard to continue:");

		}
		/**
		 *  Uses a do-while loop to generate the player hands and run the turns for the game.
		 *	If the endGame boolean is ever true, the loop ends and the game subsequently ends.
		 */
		public void startGame ()
		{
			playerHand = generateHand();
			do {
				if (!endGame)
				compHand = generateHand();
				playerTurn();
			} while (!endGame);
			System.out.println("No more words can be created.");
			System.out.println("\nPlayer score: " + playerScore);
			System.out.println("Computer score: " + compScore + "\n");
		}
		/**
		 *  Generates a hand of tiles that is a certain length from a preset set of tiles. Generates a random index
		 *	that is in the tile list and adds it to a string. The tiles are taken out of the full set of tiles.
		 *	If the tiles remaining are less than or equal to the number of tiles
		 *	in a hand, the game ends.
		 *
		 *	@return a hand consisting of 8 tiles from the tilesRemaining string
		 */
		public String generateHand()
		{
			String hand = "";
			int previous = -1;
			int num = -1;
			for (int i = 0; i < NUM_TILES; i++)
			{
				int length = tilesRemaining.length();
				num = (int)(Math.random()*length); // random index that is within the bounds of the tile string
				//	System.out.println(num);
				hand = hand + tilesRemaining.charAt(num) + " "; // adds character to the string
				tilesRemaining = tilesRemaining.substring(0, num) + "" + tilesRemaining.substring(num + 1); // removes the tile from tile set
				//System.out.println(tilesRemaining);
			}
			if (tilesRemaining.length() <= NUM_TILES) // if the remaining tiles are less than the amount needed for a hand, game ends.
			{
				endGame = true;

			}
			return hand; // returns the full hand
		}
	/**
	*	The player's turn. First the tiles are printed out and formatted and the scores and hands are displayed. The player is prompted to enter a word
	*	which is checked for validity. The word has to be between 4 and 8 characters and made up of letters that are in the player hand and the wordlist.
	*	If one of these conditions is false, the game ends. If all are true, the game continues. If the word consists of having double letters ("aa" or "bb")
	*	the player's score for that word is doubled, otherwise the player's score is added to their current score using the getScore() method from the WordUtils
	*	class. If the game still hasn't ended after the player's hand is generated, the computer's turn begins.
	*/
		public void playerTurn ()
		{

			int counter = 0; // counter for formatting the tiles
			String word = "";
			boolean isBonus = false; // if the word is bonusgplea
			System.out.println("Here are the tiles remaining in the pool of letters:");
			System.out.println();
			for (int i = 0; i < tilesRemaining.length(); i ++) {

				System.out.printf("%c ", tilesRemaining.charAt(i));
				counter++;
				if (counter == 20)
				{
					System.out.println();
					counter = 0;
				}

			}
			System.out.println();
			System.out.println("\nPlayer score: " + playerScore);
			System.out.println("Computer score: " + compScore + "\n");

			System.out.println("THE TILES IN YOUR HAND ARE: " + playerHand);
			System.out.println("THE TILES IN THE COMPUTER HAND ARE: " + compHand);
			System.out.println();

			if (!checkHand(playerHand.trim().toLowerCase())) // checks hand to see if any valid words can be generated. If not possible then end game.
			{
				endGame = true;

			}
			if (!endGame)
			{
				word = Prompt.getString("Please enter a word created from your current set of tiles"); // prompts for a word from player

				if (word.length() < 4 || word.length() > 8) // if length is too small or too short, game ends
				{
					System.out.println("GAME OVER");
					endGame = true;
				}
				if (!WordUtilities.matchLetters(word, playerHand.trim().toLowerCase())) // if letters don't match, game ends.
				{
					System.out.println("GAME OVER");
					endGame = true;
				}
				if (!isInList(word)) // if word is not in list, the game ends.
				{
					System.out.println("GAME OVER");
					endGame = true;
				}
			}

			if (!endGame)
			{
				/*
					Traverses through word and checks if there are two letters that appear twice consecutively. If true, then the double characters
					bonus is activated.
				*/
				for (int i = 0; i < word.length(); i++)
				{
					char c = word.charAt(i);
					String doubleChar = "" + c + c;
					if (word.indexOf(doubleChar) > -1)
					{
						isBonus = true;
					}
				}
			}

			if (!endGame)
			{
				playerHand = generateHand(); // generates player hand for later turn
				if (isBonus) // calculates score if bonus is active, otherwise calculates score without bonus
				{
					playerScore += 2*WordUtilities.getScore(word, scores);
					System.out.println("BONUS WORD SCORE!!\n");
				}
				else
				{
					playerScore += WordUtilities.getScore(word, scores);
				}
				if (!endGame) // if there are tiles left are player hand is generated, the computer's turn begins.
				compTurn();
			}
		}

		/**
		*	This is the computer's turn. The computer uses the bestWord() method from the WordUtilities class and gets a score from it.
		*	If their word has two consecutive characters, the bonus is activated for the computer and their score for that turn is doubled.
		*/
		public void compTurn ()
		{
			/*
			  the computer's choice using methods from Wordutilities. The findAllWords() method creates an array of all possible words
				using the computer's hand, and the bestWord() method finds the highest scoring word from that hand.
			*/
			if (!checkHand(compHand.trim().toLowerCase())) // checks hand to see if any valid words can be generated. If not possible then end game.
			{
				endGame = true;

			}
			String compChoice = WordUtilities.bestWord(WordUtilities.findAllWords(compHand.trim().toLowerCase()), scores);
			boolean isBonus = false; // for bonus
			int counter = 0;
			if (compChoice.length() > 8 || compChoice.length() < 4) // if the computer's choice is invalid, the game ends for them.
			{
				endGame = true;
			}

			if (!endGame)
			{

				System.out.println("Here are the tiles remaining in the pool of letters:"); // prints tiles remaining
				System.out.println();
				for (int i = 0; i < tilesRemaining.length(); i ++) {
					System.out.printf("%c ", tilesRemaining.charAt(i));
					counter++;
					if (counter == 20)
					{
						System.out.println();
						counter = 0;
					}

				}
				System.out.println();
				System.out.println("\nPlayer score: " + playerScore);
				System.out.println("Computer score: " + compScore + "\n");

				System.out.println("THE TILES IN YOUR HAND ARE: " + playerHand);
				System.out.println("THE TILES IN THE COMPUTER HAND ARE: " + compHand);
				System.out.println();

				Prompt.getString("It's the computer's turn. Hit ENTER on the keyboard to continue"); // prints computer's choice
				System.out.println("\nThe computer chose: " + compChoice.toUpperCase() + "\n");

				for (int i = 0; i < compChoice.length(); i++) // checks if computer choice has two consecutive characters that are the same
				{
					char c = compChoice.charAt(i);
					String doubleChar = "" + c + c;
					if (compChoice.indexOf(doubleChar) > -1)
					{
						isBonus = true;
					}
				}
				if (isBonus)
				{
					System.out.println("BONUS WORD SCORE!!\n");
					compScore += 2 * WordUtilities.getScore(compChoice, scores); // doubles score if the bonus is activated, otherwise calculates score without bonus
				}
				else
				{
					compScore += WordUtilities.getScore(compChoice, scores);
				}
			}
		}
		/**
		*	Checks if the word given is in the words list by opening the file and reading each word and comparing it. If it is,
		*	then the method returns true, otherwise it returns false.
		*
		*	@return                       boolean that is either true or false for if the word is in the list or not
		* @param String str             word to be checked
		*/
		public boolean isInList (String str)
		{
			// opens file to reading
			Scanner inFile = FileUtils.openToRead("wordlist.txt");

			while (inFile.hasNext())
			{
				// checks if each word in file is equal to the word given
				String word = inFile.next();
				if (word.equals(str))
				{
					return true;
				}
			}
			return false;
		}
		/**
		*	checks to see if the hand can form valid words that are between 4 and 8 characters long. If any words are between those
		*	parameters, then true is returned. If all words are more than 8 characters or less than 4, false is returned.
		*
		* @return boolean 								true or false, whether or not any words are between 4 and 8 characters long
		* @param String hand							the hand of the player
		*/
		public boolean checkHand (String hand)
		{
			String [] possibleWords = WordUtilities.findAllWords(hand.toLowerCase()); // finds all possible words using the hand
			boolean valid = false;
			for (int i = 0; i < possibleWords.length; i++) // checks if any of the words found are valid words
			{
				//		System.out.println(possibleWords[i]);
				if (possibleWords[i].length() >= 4 || possibleWords[i].length() <= 8)
				valid = true;

			}
			if (valid) // if any words are valid, true is returned, otherwise false
			return true;
			else
			return false;
		}


	}
