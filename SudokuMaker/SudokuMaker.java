/**
 *	SudokuMaker - Creates a Sudoku puzzle using recursion and backtracking
 *
 *	@author Anishka Chauhan
 *	@since 2/11/2021
 *
 */
public class SudokuMaker {

	private int[][] puzzle;
	private int [] nums; // an array of random integers

	public SudokuMaker ()
	{
		nums = new int[9];
		puzzle = new int [9][9];
		for (int row = 0; row < puzzle.length; row++) // initializes the array to default value of zero
		{
			for (int col = 0; col < puzzle[0].length; col++)
			{
				puzzle[row][col] = 0;
			}
		}
	}

	/**
	 *	printPuzzle - prints the Sudoku puzzle with borders
	 *	If the value is 0, then print an empty space; otherwise, print the number.
	 */
	public void printPuzzle() {
		System.out.print("  +-----------+-----------+-----------+\n");
		String value = "";
		for (int row = 0; row < puzzle.length; row++) {
			for (int col = 0; col < puzzle[0].length; col++) {
				// if number is 0, print a blank
				if (puzzle[row][col] == 0) value = " ";
				else value = "" + puzzle[row][col];
				if (col % 3 == 0)
					System.out.print("  |  " + value);
				else
					System.out.print("  " + value);
			}
			if ((row + 1) % 3 == 0)
				System.out.print("  |\n  +-----------+-----------+-----------+\n");
			else
				System.out.print("  |\n");
		}
	}

	public static void main (String[] args)
	{
		SudokuMaker run = new SudokuMaker();
		run.run();

	}
	/**
	 * Calls for the creation of the board and prints it to the screen.
	 * @method run
	 */
	private void run ()
	{
		recursiveBoardFiller();
		System.out.println("\nSudoku Puzzle\n");
		printPuzzle();
		System.out.println();
	}
/**
 * Create a solved sudoku board. Each number cannot appear in the same
 * row or column twice and cannot be in the same 3x3 grid location. Uses recursion
 * to fill the board and backtracks if all random ints cannot work, creating a new set and trying again.
 * Finishes when the entire board is filled.
 *
 * @method recursiveBoardFiller
 * @return true or false, depending on state.
 */
	private boolean recursiveBoardFiller ()
	{
		createRandomArray(); // initally creates random ints in an array called nums
		for (int row = 0; row < 9; row++) // iterates through each location on the board
		{
			for (int col = 0; col < 9; col++)
			{
				if (puzzle[row][col] == 0) // checks each empty cell (in this case, all)
				{
					for (int i = 0; i < 9; i++) // iterates through nums array
					{
						if (isSafe(row, col, i)) // checks if safe to place num in cell
						{
							puzzle[row][col] = nums[i];
							//printPuzzle();
							if (recursiveBoardFiller()) // keeps filling the board until either the whole board is filled (true)
							{                                 //
							//	System.out.println("recurse");
								return true; // if the call returns true then continue
							}
							else
							{
								puzzle[row][col] = 0; // if false, then clear the cell
							}

						}
					}
					createRandomArray(); // creates new random array if all random ints are not valid anymore
					return false;
				}

			}
		}
	return true; // returns true when the method exits out the loop  (finishes the board)

	}
/**
 * Checks to see if desired number is in the same row
 * @method isRow
 * @param  r     row index
 * @param  index index in the nums array
 * @return       true if in row, false otherwise
 */
private boolean isRow(int r, int index)
{
	for (int c = 0; c < 9; c++)
	{
		if (puzzle[r][c] == nums[index])
		{
			return true;
		}
	}
	return false;
}

/**
 * Checks if desired number is in the same column.
 * @method isCol
 * @param  c     column index
 * @param  index random number index
 * @return       true if in column, false otherwise.
 */
private boolean isCol(int c, int index)
{
	for (int r = 0; r < 9; r++)
	{
		if (puzzle[r][c] == nums[index])
			return true;
	}
	return false;
}
/**
 * Checks if random number is in same 3x3 grid.
 * @method isGrid
 * @param  r      row index of location
 * @param  c      column index of location
 * @param  index  index of random number
 * @return        true if in grid, false otherwise.
 */
private boolean isGrid(int r, int c, int index)
{
	int startbox = r-r%3;
	int endbox = c-c%3;
//	System.out.println("\n" + startbox + "" + endbox);
	for (int i = startbox; i < 3+startbox; i++)
	{
		for (int j = endbox; j < 3 + endbox; j++)
		{
			if (puzzle[i][j] == nums[index])
				return true;
		}
	}
	return false;
}
/**
 * Checks if location is a safe location to fill with specified number.
 * @method isSafe
 * @param  r      row index
 * @param  c      column index
 * @param  index  index of number from nums array
 * @return        true if all methods return false, false otherwise.
 */
private boolean isSafe(int r, int c, int index)
{
	return !(isRow(r, index) || isCol(c, index) || isGrid(r, c, index));

}

/**
 * Initializes the nums array to a set of 9 random integers from 1 - 9. 
 */
	public void createRandomArray()
	{
 		for (int i = 0; i < 9; i++)
		{
			nums[i] = (int)(Math.random()*9 + 1);
			//System.out.print(nums[i] + " \n");
		}
	}
}
