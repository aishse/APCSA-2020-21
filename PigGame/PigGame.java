/**
 *	The game of Pig.
 *	- The player and the computer go against each other until one reaches 100 points.
 *  - The player has the option of rolling or holding.
 *	- If they roll a 1 they will lose all their points and their turn.
 *  - If they hold the points accumilated from the rolls will be added to their total score.
 *  - The computer always holds at 20 points.
 *
 *	@author	Anishka Chauhan
 *	@since	9/24/2020
 */
public class PigGame {

	private final int MAX_GAME_PTS = 100;	// game ends at this number of points
	private int playerPoints, computerPoints;
	private Dice dice;
	private boolean playerWin, compWin;

	public PigGame ()
	{
		playerPoints = computerPoints = 0;
		dice = new Dice();
		playerWin = compWin = false;
	}

	/**	Print the introduction to the game */
	public void printIntroduction() {
		System.out.println("\n");
		System.out.println("______ _         _____");
		System.out.println("| ___ (_)       |  __ \\");
		System.out.println("| |_/ /_  __ _  | |  \\/ __ _ _ __ ___   ___");
		System.out.println("|  __/| |/ _` | | | __ / _` | '_ ` _ \\ / _ \\");
		System.out.println("| |   | | (_| | | |_\\ \\ (_| | | | | | |  __/");
		System.out.println("\\_|   |_|\\__, |  \\____/\\__,_|_| |_| |_|\\___|");
		System.out.println("          __/ |");
		System.out.println("         |___/");
		System.out.println("\nThe Pig Game is human vs computer. Each takes a"
							+ " turn rolling a die and");
		System.out.println("the first to score to score " + MAX_GAME_PTS
							+ " points wins. A player can either ROLL or");
		System.out.println("HOLD. A turn works this way:");
		System.out.println("\nROLL:\t2 through 6: add points to turn total, "
							+ "player's turn continues");
		System.out.println("\t1: player loses turn");
		System.out.println("HOLD:\tturn total is added to player's score, "
							+ "turn goes to other player");
		System.out.println("\n");
	}

	public static void main(String [] args)
	{
		PigGame game = new PigGame(); // new instance of PigGame object
		game.printIntroduction(); // print starting introduction
		game.startGame(); // starts the game

	}
	public void startGame ()
	{
		while (!playerWin && !compWin)
		{
			playGame(); // until the player or the computer wins the game continues
		}
		// goodbye messages if the computer wins or if the player wins
		if (playerWin)
		{
			System.out.println("\nCongratulations! You Won!!\n");
			System.out.println("Thanks for playing Pig Game!\n");
		}
		if (compWin)
		{
			System.out.println("\nSorry. The COMPUTER WON.\n");
			System.out.println("Thanks for playing Pig Game!\n");
		}

	}

	public void playGame () // calls the playerTurn and computerTurn methods
	{
		playerTurn();
		if (!playerWin)
		{
			computerTurn();
		}
	}
	// the player's turn
	public void playerTurn ()
	{
		int turnScore = 0; // Player's score each turn
		String ask = "(r)oll or (h)old"; // question string for Prompt class
		char choice = 0; // the choice the player picks
		boolean holdTurn = false; // whether the player wants to holdturn or not
		boolean goodInput = true; // if the player's input is valid or not
		boolean losePoints = false; // if the player rolls a 1
		System.out.println("\n**************** PLAYER'S TURN ****************\n");
		do
		{

			System.out.println("Your turn score " + turnScore);
			System.out.println("Your total score " + playerPoints + "\n");
			do
			{
				choice = Prompt.getChar(ask);
				if (choice == 'r' || choice == 'h') // checks if the player's input is valid or not
				{
					goodInput = true;
				}
				else
				{
					goodInput = false;
				}
			}
			while (!goodInput);
			if (choice == 'h') // holdturn is true if the player inputs an h
			{
				holdTurn = true;
			}

			if (!holdTurn)
			{
				int a = dice.roll(); // takes value of dice and checks if it's greater than 1, then adds it to turn score
				if (a > 1)
				{
					turnScore += a;
					dice.printDice();
				}
				else
				{
					holdTurn = true; // if value of dice is 1 then the turn ends.
					losePoints = true;
					dice.printDice();

				}


			}
		}
		while (!holdTurn);


		if (losePoints)
		{
			System.out.println("\nYOU LOSE YOUR TURN");

			System.out.println("Your total score: " + playerPoints);
		}
		if (holdTurn && !losePoints)
		{
			System.out.println("\nYou HOLD.");
			playerPoints += turnScore;
			System.out.println("Your total score: " + playerPoints);
		}

		if (playerPoints >= MAX_GAME_PTS) // playerWin is true if player's points are greater than the max game points
		{
			playerWin = true;
		}




}

// the computer's turn
	public void computerTurn ()
	{
		int turnScore = 0; // computer's turn score
		boolean holdTurn = false; // if the computer wants to hold or not
		boolean lostTurn = false; // if the boolean rolls a 1
		System.out.println("\n**************** COMPUTER'S TURN ****************");
		System.out.println("Computer will try to get at least 21 points.\n");
		do
		{
			System.out.println("Computer turn score: " + turnScore);
			System.out.println("Computer total score: " + computerPoints);

			Prompt.getString("Press Enter for computer turn");

			System.out.println("\nComputer will ROLL");

			int a = dice.roll(); // value on dice

			if (a > 1)
			{
				turnScore += a;
			 	if (turnScore > 20) // if the computer has a turn score greater than 20 it holds
				{
					holdTurn = true;
				}
				dice.printDice();
			}

			else // if the dice value is 1 or less then the turn ends
			{
				holdTurn = true;
				lostTurn = true;
				dice.printDice();
			}

		}
		while (!holdTurn);

		if (lostTurn)
		{
			System.out.println("\nCOMPUTER LOSES TURN");

			System.out.println("Computer total score: " + computerPoints + "\n");

		}
		if (holdTurn && !lostTurn)
		{
			System.out.println("\nComputer HOLDS.");
			computerPoints += turnScore;
			System.out.println("Computer total score: " + computerPoints);
		}

		if (computerPoints >= MAX_GAME_PTS) // if computer's total points are greater than max game points then the computer wins
		{
			compWin = true;
		}



	}



}
