/**
 *	PegBoard for the Peg Solitaire game.
 *
 *	@author Anishka Chauhan
 *	@since 1/8/2020
 *
 *	This is the English version of the board.
 *	It is a 7x7 board without the corners. The game starts with pegs in
 *	all the locations except the center, as shown below.
 *
 *  col 0   1   2   3   4   5   6
 * row        -------------
 *  0         | P | P | P |
 *            -------------
 *  1         | P | P | P |
 *    -----------------------------
 *  2 | P | P | P | P | P | P | P |
 *    -----------------------------
 *  3 | P | P | P |   | P | P | P |
 *    -----------------------------
 *  4 | P | P | P | P | P | P | P |
 *    -----------------------------
 *  5         | P | P | P |
 *            -------------
 *  6         | P | P | P |
 *            -------------
 *
 */

public class PegBoard {

	private char[][] board;				// the peg board of characters

	private final int BOARD_SIZE = 7;	// the side length of the square board

	/* constructor */
	public PegBoard() {
		// initialize board
		board = new char[BOARD_SIZE][BOARD_SIZE];
		for (int r = 0; r < BOARD_SIZE; r++)
		{
			for (int c = 0; c < BOARD_SIZE; c++)
			{
				if (r == 0 || r == 1 || r == 5 || r == 6)
				{
					if (c >= 2 && c <= 4)
					{
						board[r][c] = 'P';
					}
				}
				else if (r == 3 && c == 3)
				{
					board[r][c] = ' ';
				}
				else
				{
					board[r][c] = 'P';
				}
			}
		}
	}

	/**
	 *	Print the peg board to the screen.
	 */
	public void printBoard() {
		System.out.println();
		System.out.println(" col 0   1   2   3   4   5   6");
		System.out.println("row        -------------");
		System.out.print(" 0         |");
		for (int a = 2; a < 5; a++) System.out.printf(" %c |", board[0][a]);
		System.out.println("\n           -------------");
		System.out.print(" 1         |");
		for (int a = 2; a < 5; a++) System.out.printf(" %c |", board[1][a]);
		System.out.println("\n   -----------------------------");
		System.out.print(" 2 |");
		for (int a = 0; a < 7; a++) System.out.printf(" %c |", board[2][a]);
		System.out.println("\n   -----------------------------");
		System.out.print(" 3 |");
		for (int a = 0; a < 7; a++) System.out.printf(" %c |", board[3][a]);
		System.out.println("\n   -----------------------------");
		System.out.print(" 4 |");
		for (int a = 0; a < 7; a++) System.out.printf(" %c |", board[4][a]);
		System.out.println("\n   -----------------------------");
		System.out.print(" 5         |");
		for (int a = 2; a < 5; a++) System.out.printf(" %c |", board[5][a]);
		System.out.println("\n           -------------");
		System.out.print(" 6         |");
		for (int a = 2; a < 5; a++) System.out.printf(" %c |", board[6][a]);
		System.out.println("\n           -------------");
		System.out.println();
	}

	/**
	 * Counts the total number of pegs by checking if
	 * each spot on the array is equal to 'P'.
	 *
	 * @return the total number of pegs on the board.
	 */
	public int pegCount() {
		int counter = 0;
		for (int r = 0; r < BOARD_SIZE; r++)
		{
			for (int c = 0; c < BOARD_SIZE; c++)
			{
				if (board[r][c] == 'P')
				{
					counter++;
				}
			}
		}
		return counter;
	}

	/**
	 * Checks to see if the peg is on the board by checking if the row and column
	 * coordinates are not part of the empty spaces on the board.
	 *
	 * @param  row             the row coordinate of the peg
	 * @param  col             the column coordinate of the peg
	 * @return                 true or false, depending on if the peg is valid or not.
	 */
	public boolean isValidLocation(int row, int col) {

		if (row >= BOARD_SIZE || col >= BOARD_SIZE) return false;

		if (row == 0 || row == 1 || row == 5 || row == 6)
		{
			if (col == 0 || col == 1 || col == 5 || col == 6)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Replaces the location of the peg on the array with a 'P'. Will put a peg regardless
	 * of whether it is already empty or not.
	 *
	 * @param  row    row coordinate of space
	 * @param  col    column coordinate of space
	 */
	public void putPeg(int row, int col)
	{
		board[row][col] = 'P';
	}

	/**
	 * Sets the element at the given row and column equal to a space, making it "removed" on the game board. Removes a peg even if
	 * the location is already empty.
	 *
	 * @param  row       the row coordinate
	 * @param  col       the column coordinate
	 */
	public void removePeg(int row, int col)
	{
		board[row][col] = ' ';

	}

	/**
	 * Checks to see if the peg at the given location is equal to 'P' or not.
	 * Preecondition: must be a valid location.
	 *
	 * @method isPeg
	 * @param  row   row coordinate of location to be tested
	 * @param  col   column coordinate of location to be tested
	 * @return       true or false, depending on if the location on the board is equal to 'P' or not. 
	 */
	public boolean isPeg(int row, int col) {
		if (board[row][col] == 'P')
			return true;
		return false;
	}

	/** @return		size of the board */
	public int getBoardSize() { return BOARD_SIZE; }
}
