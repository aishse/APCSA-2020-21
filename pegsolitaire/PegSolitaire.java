import java.util.ArrayList;
/**
 *	PegSolitaire game.
 *
 *	The game moves pegs either horizontally and vertically across a 7x7 grid with blank 2x2 corners cut out on each corner.
 * 	This class runs the basic functions of the game, such as user interaction, printing and moving pegs, and gauging whether the player
 *  has made all the possible moves they could have made.
 *
 *	@author Anishka Chauhan
 *	@since 1/8/2021
 *
 */
public class PegSolitaire {

	private String jumperLocation; // the user input as a string
	private String [] coordinates; // for storing the coordinates of the jumper peg
	private PegBoard board; // an instance of the PegBoard class in order to run the game
	private ArrayList<Location> posLocations; // the possible locations a peg can jump to
	private boolean endGame; // boolean for ending the game

	/** constructor */
	public PegSolitaire()
	{
		jumperLocation = "";
		board = new PegBoard();
		endGame = false;
		posLocations = new ArrayList<Location>();
		coordinates = new String [100]; // starting value is large

	}
	/** methods */
	// runs the start game method
	public static void main (String[] args)
	{
		PegSolitaire run = new PegSolitaire();
		run.startGame();
	}
	/**
 	 * Handles the structure of the game. Checks to see if the pieces on the board are valid using private helper
 	 * methods and a a do-while loop. Resets information from the last turn as well. Displays the total number of
 	 * pegs remaining after the game is finished.
	 */
	public void startGame ()
	{
		printIntroduction();
		do
		{
			posLocations = new ArrayList<Location>();
			if (!hasValidMoves() || board.pegCount() <= 1)
			{
					endGame = true;
			}
			else
			{
				posLocations = new ArrayList<Location>();
				board.printBoard();
				checkInput();
			}


		}
		while (endGame == false);
		board.printBoard();
		System.out.println("\nYour score: " + board.pegCount() + " pegs remaining.\n");
		System.out.println("Thanks for playing PegSolitaire!\n");
	}
/**
 * Traverses through the entire array of pegs from PegBoard class using the isPeg() helper method.
 * Checks to see if the peg has any valid moves by checking the size of the posLocations array. Resets the array after each peg.
 *
 * @return true, if posLocations.size() is greater than 0, false otherwise.
 */
	private boolean hasValidMoves ()
	{
		// uses board.getSize() for the constraints of the loop
		for (int r = 0; r < board.getBoardSize(); r++)
		{
			for (int c = 0; c < board.getBoardSize(); c++)
			{
				if (board.isPeg(r,c))
				{
					getValidMoves(r, c); // runs the valid peg coordinates through getValidMoves(), which manipulates the posLocations array
					if (posLocations.size() > 0) // if getValidMoves() finds valid moves, return true and exit out of the method.
					{
						return true;
					}
					else
					{
						posLocations = new ArrayList<Location>(); // otherwise reset the array back to 0 and continue
					}
				}
			}
		}
		return false; // if all pegs have no valid moves, return false
	}
	/**
	 * Handles the checking of input and the moving of the pegs based off of the size of posLocations array. The input is checked
	 * using a do-while loop, which screens for too long input, non-integers (uses try-catch block), and pegs that have no valid
	 * moves. It also checks if the user wishes to quit the game, which sets the endGame variable to false.
	 * If the pegs get through these checks, the method calls the movePeg() method to move the pegs. It asks the user to choose a
	 * location if there is more than one location to move, otherwise it moves automatically.
	 *
	 */
	private void checkInput ()
	{
		boolean badInput = false; // boolean for bad input
		int row = 0; // the row coordinate
		int col = 0; // the column coordinate
		do
		{
			badInput = false; // resets badInput after each input from user
			jumperLocation = Prompt.getString("Jumper peg - row col (ex. 3 5, q=quit)"); // prompts user to enter a set of coordinates (as string)
			if (jumperLocation.equals("q")) // if the user enters a "q," the rest of the method is not performed.
			{
				badInput = false;
				endGame = true;
			}
			else
			{
				if (jumperLocation.length() > 3) // if input is already too long, automatically asks for another input
				{
					badInput = true;
					System.out.println("Invalid jumper peg -> " + jumperLocation);
				}
				if (badInput == false)
				{
					coordinates = jumperLocation.split(" +"); // splits the string from the space
					try
					{
						row = Integer.parseInt(coordinates[0]); // tries to parse each coordinate as an integer
						col = Integer.parseInt(coordinates[1]);
					}
					catch (NumberFormatException e) // if they are not integers, throws an error message and the loop restarts
					{
						badInput = true;
						System.out.println("Invalid jumper peg -> " + jumperLocation);
					}
					catch (ArrayIndexOutOfBoundsException b) // if the array is too short, an error message is thrown and the loop restarts
					{
						badInput = true;
						System.out.println("Invalid jumper peg -> " + jumperLocation);
					}
				}
				if (badInput == false) // if the two inputs are integers, they are then checked for validity
				{
					if (!board.isValidLocation(row, col) || !board.isPeg(row, col))
					{
						badInput = true;
						System.out.println("Invalid jumper peg -> " + jumperLocation);
					}

				}
				if (badInput == false) // if the location is valid/on the board, it is checked for valid moves
				{                      // (also doubles for gathering valid moves if the input is valid)
					getValidMoves(row, col);
					if (posLocations.size() <= 0)
					{
						badInput = true;
						System.out.println("Invalid jumper peg -> " + jumperLocation);
					}
				}
			}
		}
		while (badInput == true);
		if (endGame == false)
		{
			if (posLocations.size() > 1) // moves the peg automatically or by the user's command, depending on the size of posLocations
			{
				int choice = 0;
				System.out.println("Possible peg jump locations:");
				for (int i = 0; i < posLocations.size(); i++) // prints out all possible locations for the peg if there is more than one
				{
					System.out.println(i + " " + posLocations.get(i));
				}
				do // asks user to select a location (also error checks)
				{
					choice = Prompt.getInt("Enter location (0 - " + (posLocations.size()-1) + ")");
				}
				while (choice < 0 || choice >= posLocations.size());
				movePeg(posLocations.get(choice), row, col);
			}
			else
			{
				movePeg(posLocations.get(0), row, col); // automatically moves otherwise
			}
		}
	}
	/**
	 * Finds the peg to "jump over" by checking if the two pegs are on the same x or y axis, and calculating the midpoint of the
	 * other coordinate. Places a peg at the given location (lc) and removes the jumper peg and the midpoint peg.
	 *
	 * @param  lc      a Location class instance that stores the location the peg intends to jump to.
	 * @param  pegRow  the jumper peg's row coordinate
	 * @param  pegCol  the jumper peg's column coordinate
	 */
	private void movePeg(Location lc, int pegRow, int pegCol)
	{
		int midRow = 0;
		int midCol = 0;
		if (pegRow - lc.getRow() == 0) // checks if the rows are the same
		{
			midRow = pegRow;
			midCol = (lc.getCol() + pegCol) / 2;
		//	System.out.println("Midrow: (" + midRow + ", " + midCol + ")");

		}
		else if (pegCol - lc.getCol() == 0) // checks if columns are the same
		{
			midCol = pegCol;
			midRow = (lc.getRow() + pegRow) / 2;
		//	System.out.println("Midrow: (" + midRow + ", " + midCol + ")");
		}
		// performs the move
		board.removePeg(pegRow, pegCol);
		board.putPeg(lc.getRow(), lc.getCol());
		board.removePeg(midRow, midCol);


	}
/**
 * Calculates the valid moves of a certain peg vertically and horizontally. Adds each valid location to the
 * posLocations array.
 *
 * @param  row           the row coordinate of the peg
 * @param  col           the column coordinate of the peg
 */
	private void getValidMoves (int row, int col)
	{
		// vertical moves
		if (row + 2 < board.getBoardSize()) // checks if the bottom has space to move
		{
			if (board.isPeg(row + 1, col) && board.isValidLocation(row + 2, col) && !board.isPeg(row + 2, col)) // checks to see if there is a empty space and a peg
			{
				posLocations.add(new Location(row + 2, col)); // adds location to array if true

			}
		}
		if (row - 2 >= 0) // looks to see if top side is on the board or not
		{
			if (board.isPeg(row - 1, col) && board.isValidLocation(row - 2, col) && !board.isPeg(row - 2, col))
			{
					posLocations.add(new Location(row - 2, col));
			}
		}

		if (col + 2 < board.getBoardSize()) // looks to see if it is possible to move right on the board
		{
			if (board.isPeg(row, col + 1) && !board.isPeg(row, col + 2) && board.isValidLocation(row, col + 2))
			{
				posLocations.add(new Location(row, col + 2));
			}
		}
		if (col - 2 >= 0) // looks to see if it is possible to move to the left on the board
		{
			if (board.isPeg(row, col - 1) && !board.isPeg(row, col - 2) && board.isValidLocation(row, col - 2))
			{
				posLocations.add(new Location(row, col-2));
			}
		}
	}

	/**
	 *	Print the introduction to the game
	 */
	public void printIntroduction() {
		System.out.println("  _____              _____       _ _ _        _ ");
		System.out.println(" |  __ \\            / ____|     | (_) |      (_)");
		System.out.println(" | |__) |__  __ _  | (___   ___ | |_| |_ __ _ _ _ __ ___ ");
		System.out.println(" |  ___/ _ \\/ _` |  \\___ \\ / _ \\| | | __/ _` | | '__/ _ \\");
		System.out.println(" | |  |  __/ (_| |  ____) | (_) | | | || (_| | | | |  __/");
		System.out.println(" |_|   \\___|\\__, | |_____/ \\___/|_|_|\\__\\__,_|_|_|  \\___|");
		System.out.println("             __/ |");
		System.out.println("            |___/");
		System.out.println("\nWelcome to Peg Solitaire!!!\n");
		System.out.println("Peg Solitaire is a game for one player. The " +
							"goal is to remove all\n" +
							"but one of the 32 pegs from a special board. " +
							"The board is a 7x7\n" +
							"grid with the corners cut out (shown below)." +
							" Pegs are placed in all");
		System.out.println("grid locations except the center which is " +
							"left empty. Pegs jump\n" +
							"over other pegs either horizontally or " +
							"vertically into empty\n" +
							"locations and the jumped peg is removed. Play " +
							"continues until\n" +
							"there are no more jumps possible, or there " +
							"is one peg remaining.");
		System.out.println("\nLet's play!!!\n");
	}

}
