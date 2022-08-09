/**
*  YahtzeeScoreCard.java
* 
*  Determines the scoring and prints the scorecard for one player. Calculates scores for number categories, three and four of a kind,
*  full house, small and large straights, chance, and yahtzee. Uses an array to update the scorecard.
*
* @author Anishka Chauhan
*	@since 10/19/2020
*/
public class YahtzeeScoreCard {
	private int [] scores; // array of scores

	public YahtzeeScoreCard ()
	{
		scores = new int [14];
		// sets the scores to a default value of -1
		for (int i = 0; i < scores.length; i++)
		{
			scores[i] = -1;
		}

	}
	/**
	 *  Print the scorecard header
	 */
	public void printCardHeader() {
		System.out.println("\n");
		System.out.printf("\t\t\t\t\t       3of  4of  Fll Smll Lrg\n");
		System.out.printf("  NAME\t\t  1    2    3    4    5    6   Knd  Knd  Hse " +
						"Strt Strt Chnc Ytz!\n");
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}
	public void printCardFooter() {
		System.out.printf("      \t\t  1    2    3    4    5    6    7    8    9  " +
		" 10   11   12   13\n");

	}

	/**
	 *  Prints the player's score
	 *
	 * @param player        the player information for scorecard (name)
	 *
	 */
	public void printPlayerScore(YahtzeePlayer player) {
		System.out.printf("| %-12s |", player.getName());
		for (int i = 1; i < 14; i++) {
			if (getScore(i) > -1)
				System.out.printf(" %2d |", getScore(i));
			else System.out.printf("    |");
		}
		System.out.println();
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}


	/**
	 *  Change the scorecard based on the category choice 1-13.
	 *
	 *  @param choice The choice of the player 1 to 13
	 *  @param dg  The DiceGroup to score
	 *  @return  true if change succeeded. Returns false if choice already taken.
	 */
	public boolean changeScore(int choice, DiceGroup dg) {

		if (scores[choice] > -1) // checks if the choice is greater than -1 (not default)
		{
			return false;
		}
		else // if position is not filled, then based off of choice the socring method is called
		{
			if (choice >= 1 && choice <= 6)
				numberScore(choice, dg);
			if (choice == 7)
				threeOfAKind(dg);
			if (choice == 8)
				fourOfAKind(dg);
			if (choice == 9)
				fullHouse(dg);
			if (choice == 10)
				smallStraight(dg);
			if (choice == 11)
				largeStraight(dg);
			if (choice == 12)
				chance(dg);
			if (choice == 13)
				yahtzeeScore(dg);
			return true;
		}

	}

	/**
	 *  Change the scorecard for a number score 1 to 6
	 *
	 *  @param choice The choice of the player 1 to 6
	 *  @param dg  The DiceGroup to score
	 */
	public void numberScore(int choice, DiceGroup dg)
	{
		int counter = 0;
		Dice [] die = dg.dices();
		for (int i = 0; i < die.length; i++) // counts how many times the choice is in the hand
		{
			if (die[i].getValue() == choice)
			{
				counter++;
			}
		}
		scores[choice] = choice * counter; // multiplies the count by the counter in order to get the score
	}

	/**
	* 	Returns a value from the scores array at the given index
	*
	* 	@param index the desired index to get value from
	* 	@return the value at scores[index]
	*/
	public int getScore (int index)
	{

		return scores[index];

	}
	/**
	 *	Updates the scorecard for Three Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */
	public void threeOfAKind(DiceGroup dg)
	{
		int counter = 0;
		int value = 0;
		boolean isValid = false;
		Dice [] die = dg.dices();

		for (int i = 0; i < die.length; i++)
		{
			for (int j = i+1; j < die.length; j++)
			{
				if (die[i].getValue() == die[j].getValue()) // counts to see how many times the number shows up
				{
					counter++;
				}
			}
			if (counter >= 2) // if the counter is greater than 2, then there is a three of a kind
			{
				isValid = true;
			}
			counter = 0;
		}

		if (isValid) // calculates score for three of a kind
		{
			for (int i = 0; i < die.length; i++)
			{
				value += die[i].getValue();
			}
		}
		scores[7] = value; // sets this score equal to the position in the array
	}

	/**
	 *	Updates the scorecard for Four Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */
	public void fourOfAKind(DiceGroup dg)
	{
		int counter = 0;
		int value = 0;
		boolean isValid = false;
		Dice [] die = dg.dices();

		for (int i = 0; i < die.length; i++)
		{
			for (int j = i+1; j < die.length; j++)
			{
				if (die[i].getValue() == die[j].getValue()) // counts to see how many times the number shows up
				{
					counter++;
				}
			}
			if (counter >= 3) // if the counter is greater than 3, then there is a three of a kind
			{
				isValid = true;
			}
			counter = 0;
		}
		if (isValid) // calculates score for four of a kind
		{
			for (int i = 0; i < die.length; i++)
			{
				value += die[i].getValue();
			}
		}
		scores[8] = value;

	}

	/**
	 *	Updates the scorecard for Full House choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */
	public void fullHouse(DiceGroup dg)
	{
		Dice [] values = dg.dices();
		int counter = 0;
		int [] die = new int [values.length];
		boolean isStraight = false;
		boolean hasPair = false;

		for (int i = 0; i < die.length; i++) // puts the dice values into an array in order to sort it
		{
			die[i] = values[i].getValue();
		}

		for (int i = 0; i < die.length; i++) // sorts array from smallest to largest
		{
			for (int j = i + 1; j < die.length; j++)
			{
				if (die[i] > die[j])
				{
					int temp = die[i];
					die[i] = die[j];
					die[j] = temp;
				}
			}

		}
		for (int i = 0; i < die.length-1; i++) // checks if there are consecutive numbers
		{
			if (die[i + 1] == die[i] + 1)
			{
				counter++;
				if (counter == 2)
					isStraight = true;
			}
			else if (die[i + 1] == die[i])
			{
				if (counter == 2)
					isStraight = true;
			}
			else
			{
				counter = 0;
			}
		}

		for (int i = 0; i < die.length; i++) // checks if there are two numbers that are the same
		{
			for (int j = i+1; j < die.length-1; j++)
			{
				if (die[i] == die[j])
					hasPair = true;
			}
		}
		if (hasPair && isStraight) // if both conditions are true, then the score is given, otherwise it is zero
			scores[9] = 25;
		else
			scores[9] = 0;
	}

	/**
	 *	Updates the scorecard for Small Straight choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */
	public void smallStraight(DiceGroup dg)
	{
	  Dice [] values = dg.dices();
		int counter = 0;
		int [] die = new int [values.length];
		boolean isStraight = false;


		for (int i = 0; i < die.length; i++) // puts the dice values into an array in order to sort it
		{
			die[i] = values[i].getValue();
		}

		for (int i = 0; i < die.length; i++) // sorts array from smallest to largest
		{
			for (int j = i + 1; j < die.length; j++)
			{
				if (die[i] > die[j])
				{
					int temp = die[i];
					die[i] = die[j];
					die[j] = temp;
				}
			}

		}
		for (int i = 0; i < die.length-1; i++) // checks if the numbers are consecutive
		{
			if (die[i + 1] == die[i] + 1)
			{
				counter++;
				if (counter == 3)
					isStraight = true;
			}
			else if (die[i + 1] == die[i])
			{
				if (counter == 3)
					isStraight = true;
			}
			else
			{
				counter = 0;
			}

		}
		if (isStraight) // if the numbers are consecutive, then the score is given
		{
			scores[10] = 30;
		}
		else
		{
			scores[10] = 0;
		}

	}

	/**
	 *	Updates the scorecard for Large Straight choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */
	public void largeStraight(DiceGroup dg)
	{
		Dice [] values = dg.dices();
	 int counter = 0;
	 int [] die = new int [values.length];
	 boolean isStraight = false;


	 for (int i = 0; i < die.length; i++) // puts the dice values into an array in order to sort it
	 {
		 die[i] = values[i].getValue();
	 }

	 for (int i = 0; i < die.length; i++) // sorts array from smallest to largest
	 {
		 for (int j = i + 1; j < die.length; j++)
		 {
			 if (die[i] > die[j])
			 {
				 int temp = die[i];
				 die[i] = die[j];
				 die[j] = temp;
			 }
		 }

	 }
	 for (int i = 0; i < die.length-1; i++) // checks if the numbers are consecutive
	 {
		 if (die[i + 1] == die[i] + 1)
		 {
			 counter++;
			 if (counter == 4)
				 isStraight = true;
		 }
		 else if (die[i + 1] == die[i])
		 {
			 if (counter == 4)
				 isStraight = true;
		 }
		 else
		 {
			 counter = 0;
		 }

	 }
	 if (isStraight) // if the numbers are consecutive, then the score is given
	 {
		 scores[11] = 40;
	 }
	 if (!isStraight)
	 {
		 scores[11] = 0;
	 }

	}

	/**
	 *	Updates the scorecard for Chance choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */
	public void chance(DiceGroup dg)
	{
		int total = 0;
		Dice [] die = dg.dices();

		// uses an array to add all the scores of the dice
		for (int i = 0; i < die.length; i++)
		{
			total += die[i].getValue();
		}
		scores[12] = total;
	}

	/**
	 *	Updates the scorecard for Yahtzee choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */
	public void yahtzeeScore(DiceGroup dg)
	{
		int counter = 0;
		int value = 0;
		boolean isValid = false;
		Dice [] die = dg.dices();

		for (int i = 0; i < die.length; i++) // checks if all dice are equal to each other
		{
			for (int j = i+1; j < die.length; j++)
			{
				if (die[i].getValue() == die[j].getValue())
				{
					counter++;
				}
			}
			if (counter >= 4)
			{
				isValid = true;
			}
			counter = 0;
		}
		if (isValid) // if valid Yahtzee, then the points are given
		{
			scores[13] = 50;
		}
		else
		{
			scores[13] = 0;
		}
	}
	/**
	 *	Returns the total score of the player
	 *
	 *	@return the total score of the player
	 */
	public int getTotalScore ()
	{
		int total = 0;
		for (int i = 0; i < scores.length; i++)
		{
			total += scores[i];
		}
		return total;
	}
}
