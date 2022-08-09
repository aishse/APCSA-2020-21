/**
* Yahtzee,java
*
* Runs the functions of the game, calls the YahtzeePlayer, YahtzeeScoreCard,
* and DiceGroup for each player to determine scores and update the scorecard. Runs all 13 turns.
*
*
*
*	@author Anishka Chauhan
*	@since 10/19/2020
*/

public class Yahtzee {
	private YahtzeePlayer player1; // objects of YahtzeePlayer for both players
	private YahtzeePlayer player2;
	private DiceGroup playerOneDice; // objects of DiceGroup for both players
	private DiceGroup playerTwoDice;
	private boolean playerOneFirst, playerTwoFirst, endGame; // determines which player goes first and whether the game should end
	private YahtzeeScoreCard playerOneScores, playerTwoScores; // scorecards for each player

	/**
	* Creates instances and objects of the fields.
	*/
	public Yahtzee ()
	{
		player1 = new YahtzeePlayer();
		player2 = new YahtzeePlayer();
		playerOneDice = new DiceGroup();
		playerTwoDice = new DiceGroup();
		playerOneFirst = playerTwoFirst = endGame = false;
		playerOneScores = player1.getScoreCard();
		playerTwoScores = player2.getScoreCard();
	}

	/**
	* Prints the instructions for Yahtzee.
	*/
	public void printHeader() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!                                                    |");
		System.out.println("|                                                                                    |");
		System.out.println("| There are 13 rounds in a game of Yahtzee. In each turn, a player can roll his/her  |");
		System.out.println("| dice up to 3 times in order to get the desired combination. On the first roll, the |");
		System.out.println("| player rolls all five of the dice at once. On the second and third rolls, the      |");
		System.out.println("| player can roll any number of dice he/she wants to, including none or all of them, |");
		System.out.println("| trying to get a good combination.                                                  |");
		System.out.println("| The player can choose whether he/she wants to roll once, twice or three times in   |");
		System.out.println("| each turn. After the three rolls in a turn, the player must put his/her score down |");
		System.out.println("| on the scorecard, under any one of the thirteen categories. The score that the     |");
		System.out.println("| player finally gets for that turn depends on the category/box that he/she chooses  |");
		System.out.println("| and the combination that he/she got by rolling the dice. But once a box is chosen  |");
		System.out.println("| on the score card, it can't be chosen again.                                       |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME YAHTZEE!                                                           |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n\n");
	}

	/**
	* Creates new object of Yahtzee and runs the printDice method.
	*
	*/
	public static void main(String [] args)
	{
		Yahtzee run = new Yahtzee();
		run.printDice();
	}
	/**
	* Method for running the game. The order is determined for the game by having both
	* players roll a dice and determining which player goes first by who has the highest role.
	* After the order is determined, the startRounds() method is run to start the rounds.
	*
	*/
	public void printDice ()
	{
		// the two player scores
		int sum1 = 0;
		int sum2 = 0;
		printHeader();

		player1.setName(Prompt.getString("Player 1, please enter your first name"));
		System.out.println();
		player2.setName(Prompt.getString("Player 2, please enter your first name"));
		System.out.println();
		Prompt.getString("Let's see who will go first. " + player1.getName() + ", please hit enter to roll the dice");

		// rolls the dice using the player 1 instance of DiceGroup class
		playerOneDice.rollAllDice();
		playerOneDice.printDice();

		for (int i = 0; i < 5; i++)
		{
			sum1 += playerOneDice.scoreOfDice(i);
		}

	  Prompt.getString(player2.getName() + ", it's your turn. Please hit enter to roll the dice");

		// rolls the dice using the player 2 instance of DiceGroup class
		playerTwoDice.rollAllDice();
		playerTwoDice.printDice();

		for (int i = 0; i < 5; i++)
		{
			sum2 += playerTwoDice.scoreOfDice(i);
		}

		System.out.println(player1.getName() + ", you rolled a sum of " + sum1 + ", and " + player2.getName() + ", you rolled a sum of " + sum2 + ".");

		// the respective booleans are set to true or false if one player's score is greater than the other's
		if (sum1 >= sum2)
		{
			playerOneFirst = true;
			System.out.println(player1.getName() + ", since your sum was higher, you'll roll first");

		}
		if (sum1 < sum2)
		{
			System.out.println(player2.getName() + ", since your sum was higher, you'll roll first");
			playerOneFirst = false;
			playerTwoFirst = true;

		}

		// starts the players' turns
		startRounds();

	}
	/**
	* The rounds for the game begin. The player who was chosen to go first has their turn first, which is determined by a boolean.
	* The playerTurn() method is used to run each's player's turn using their respective instances of the game classes. A counter
	* variable is used to keep track of the rounds, and if the variable reaches 13 the game ends and the final scores and winner
	* are printed.
	*
	*/
	public void startRounds ()
	{
		int counter = 1;

		System.out.println();
		do
		{
			// what to do if player 1 is supposed to go first
			if (playerOneFirst == true)
			{
				printScoreCard();
				System.out.println("Round " + counter + " of 13 rounds.\n");
				playerTurn(player1, playerOneScores, playerOneDice);
				printScoreCard();
				playerTurn(player2, playerTwoScores, playerTwoDice);
				counter++;
			}
			else if (playerTwoFirst == true) // what to do if player 2 goes first
			{
				printScoreCard();
				System.out.println("Round " + counter + " of 13 rounds.\n");
				playerTurn(player2, playerTwoScores, playerTwoDice);
				printScoreCard();
				playerTurn(player1, playerOneScores, playerOneDice);
				counter++;
			}
			if (counter == 14) // the boolean for ending the game is set to true if the counter reaches 14
			{
				endGame = true;
			}
		}
		while (!endGame);
		printScoreCard(); // prints scorecard again and final results of game
		printPlayerScore();

	}
/**
* Runs a player's turn. Asks player to roll and holds whichever dice they want. Then, the player's scorecard is
* updated with the category they choose scored. This method can work for both players because it uses their instances
* of YahtzeePlayer, YahtzeeScoreCard, and DiceGroup as parameters.
*
* @param player          the YahtzeePlayer instance, to get player information
* @param playerScores    the scorecard of the player
* @param dg              the set of dice for each player
*/

	public void playerTurn (YahtzeePlayer player, YahtzeeScoreCard playerScores, DiceGroup dg)
	{
		boolean endTurn = false;
		boolean heldDice = false;
		int counter = 0;
		int scoreChoice = 0;
		String index = "";


		Prompt.getString(player.getName() + ", it's your turn to play. Please hit enter to roll the dice");
		dg.rollAllDice();
		do
		{
			dg.printDice();
			index = Prompt.getString("Which di(c)e would you like to keep? Enter the values you'd like to 'hold' without\n "
			+ "spaces. For example, if you would like to 'hold' die 1, 2, and 5, enter 125\n (enter -1 if you'd like to end the turn)");

			// checks to see if player wants to end their turn or hold dice
			if (index.length() > 0)
			{
				if (Integer.parseInt(index) < 0)
				{
					endTurn = true;
				}

				if (Integer.parseInt(index) > 0) // pareses index as an integer in order to see if it is not null
				{
					heldDice = true;
				}
			}
			if (heldDice)
			{
				dg.holdDice(index); // the method for holding the dice is called with the given index as a parameter
				counter++;
			}
			else if (!heldDice)
			{
				dg.rollAllDice(); // rolls dice if the user inputs a null string
				counter++;
			}
			heldDice = false; // resets heldDice
			if (counter == 2) // ends turn after two rolls
				endTurn = true;

		}
		while (!endTurn);
		dg.printDice(); // prints dice for last round
		printScoreCard();
		playerScores.printCardFooter();

		// asks player to make a choice for where to score
		do
		{
			scoreChoice = Prompt.getInt(player.getName() + ", now you need to make a choice."
			+ " Pick a valid integer from the list above");
		}
		while (playerScores.changeScore(scoreChoice, dg) == false); // if YahtzeeScoreCard changeScore() method is false, loop continues

	}
	/**
	* Prints the scorecard for both players using YahtzeeScoreCard class.
	*
	*/
	public void printScoreCard ()
	{
		playerOneScores.printCardHeader();
		playerOneScores.printPlayerScore(player1);
		playerTwoScores.printPlayerScore(player2);
	}
	/**
	* Prints the final scores for both players and determines a winner. This is called at the end of the game.
	*
	*/
	public void printPlayerScore ()
	{
		// the total score for both players
		int player1Score = playerOneScores.getTotalScore();
		int player2Score = playerTwoScores.getTotalScore();

		// prints out the scores of both players
		System.out.println();
		System.out.printf("%10s              score total = %d", player1.getName(), player1Score);
		System.out.println();
		System.out.printf("%10s              score total = %d", player2.getName(), player2Score);

		// checks to see which score is higher and prints appropriate message
		if (player1Score > player2Score)
		{
			System.out.println("\n\nCongratulations " + player1.getName() + ". YOU WON!!");
		}
		else if (player2Score > player1Score)
		{
			System.out.println("\n\nCongratulations " + player2.getName() + ". YOU WON!!");
		}
		else
		{
			System.out.println("\n\nTIE GAME!!!");
		}
		System.out.println("\n");
	}
}
