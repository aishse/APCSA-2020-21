/**
 * 	DiceGroup.java
 *
 *  Rolls and prints the dice for Yahtzee.java
 *
 *	@author Anishka Chauhan
 *	@since 10/19/2020
 */
public class DiceGroup {

	private Dice [] die;	// the array of dice

	// Create the seven different line images of a die
	private String [] line = {	" _______ ",
								"|       |",
								"| O   O |",
								"|   O   |",
								"|     O |",
								"| O     |",
								"|_______|" };

	/**
	* Initializes die array to a new instance of the Dice class
	*/
	public DiceGroup ()
	{
		die = new Dice [5];
		for (int i = 0; i < die.length; i++)
		{
			die[i] = new Dice();
		}
	}

	/**
	* Rolls all the dice in the dice array
	*/
	public void rollAllDice ()
	{
		for (int i = 0; i < die.length; i++)
		{
			die[i].roll();
		}
	}

	/**
	* Rolls all dices except ones that are specified using parameter.
	*
	* @param index    String of numbers/indexes of dices to hold
	*/
	public void holdDice(String index)
	{
		int length = index.length();
		boolean hold = false;

		if (length > 5) // if the length/number of numbers is more than 5, the index is cut
		{
			index = index.substring(0, 5);
			length = index.length();
		}
		int [] indexes = new int [length]; // creates array of numbers that is the same length as index string

		for (int i = 0; i < indexes.length; i++) // splits the index string into indexes to be used for the dice array
		{
			String num = index.substring(i,i+1);
			indexes[i] = Integer.parseInt(num) - 1;
		}

		for (int i = 0; i < die.length; i++) // rolls all the dice except for the ones that match the indexes of the array
		{
			for (int x = 0; x < indexes.length; x++)
			{
				if (i == indexes[x])
				{
					hold = true;
				}
			}
			if (!hold)
			{
				die[i].roll();
			}
			hold = false;
		}


	}
	/**
	* Returns dice array
	* @return die array
	*/
	public Dice [] dices ()
	{
		return die;
	}

	/**
	* Returns score of dice at certain value
	* @return score of dice at the index 
	*/
	public int scoreOfDice (int index)
	{
		return die[index].getValue();
	}
	/**
	 *  Prints out the images of the dice
	 */
	public void printDice() {
		printDiceHeaders();
		for (int i = 0; i < 6; i++) {
			System.out.print(" ");
			for (int j = 0; j < die.length; j++) {
				printDiceLine(die[j].getValue() + 6 * i);
				System.out.print("     ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 *  Prints the first line of the dice.
	 */
	public void printDiceHeaders() {
		System.out.println();
		for (int i = 1; i < 6; i++) {
			System.out.printf("   # %d        ", i);
		}
		System.out.println();
	}

	/**
	 *  Prints one line of the ASCII image of the dice.
	 *
	 *  @param value The index into the ASCII image of the dice.
	 */
	public void printDiceLine(int value) {
		System.out.print(line[getDiceLine(value)]);
	}

	/**
	 *  Gets the index number into the ASCII image of the dice.
	 *
	 *  @param value The index into the ASCII image of the dice.
	 */
	public int getDiceLine(int value) {
		if (value < 7) return 0;
		if (value < 14) return 1;
		switch (value) {
			case 20: case 22: case 25:
				return 1;
			case 16: case 17: case 18: case 24: case 28: case 29: case 30:
				return 2;
			case 19: case 21: case 23:
				return 3;
			case 14: case 15:
				return 4;
			case 26: case 27:
				return 5;
			default:	// value > 30
				return 6;
		}
	}
}
