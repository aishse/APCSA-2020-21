import java.util.Scanner;
import java.io.PrintWriter;
/**
 * SnakeGame.java
 *
 *  Runs the main functions of the SnakeGame, including user input, randomizing the target location, saving the file and
 *  restarting the game from a new save fil eand ending the game either when the user choses to or when the user loses the game.
 *  Uses prompt.java for user input and FileUtils to open savedGame.txt and load the game.
 * @author Anishka Chauhan
 * @since 5/19/2021
 */

public class SnakeGame {
  private boolean endGame; // boolean for ending the game
  private Board theBoard; // the game board
  private Direction direction; // an enum for the chosen direction
  private Coordinate targetLocation; // location of target
  private int score; // score
  private Snake theSnake; // the snake


  public SnakeGame () {

    endGame = false;
    score = 0;
    theBoard = new Board();
    targetLocation = setTarget();

  }

  public static void main(String[] args) {
    SnakeGame st = new SnakeGame();
    st.run(args);
  }
  /**
   * Loops the game over and over until the user terminates the game or loses.
   * @method run
   * @param  args   the filename if the user chooses to load a file
   */
  private void run (String [] args) {
    System.out.println();

    if (args.length > 0) {
      openSavedFile(args[0]);
      System.out.println("\nRestoring from file savedGame.txt");
    }
    else {
      theSnake = new Snake(getSnakeLocation());
    }
    do {
      theBoard.placeSnake(theSnake);
      theBoard.placeTarget(targetLocation);
      getInput();
      if (!endGame) {
        move();
      }

    }
    while (!endGame);

  }
  /**
   * Moves the snake north, south, east or west depending on the enum
   * @method move
   */
  private void move () {

    int xCord = theSnake.get(0).getValue().getX();
    int yCord = theSnake.get(0).getValue().getY();

    if (direction == Direction.NORTH) {
      yCord = yCord - 1;

    }
    else if (direction == Direction.SOUTH) {
      yCord = yCord + 1;
    }
    else if (direction == Direction.EAST) {
      xCord = xCord + 1;
    }
    else if (direction == Direction.WEST)
    {
      xCord = xCord - 1;

    }
    if (theBoard.isValidLocation(xCord, yCord)) {

      Coordinate previous = theSnake.get(0).getValue();
      Coordinate coord = new Coordinate(xCord, yCord);
      theSnake.set(0, coord);

        theBoard.clearSnake(theSnake);
      for (int i = 1; i < theSnake.size(); i++) {
        Coordinate val = new Coordinate(theSnake.get(i).getValue().getX(), theSnake.get(i).getValue().getY());
        Coordinate temp = new Coordinate(val.getX(), val.getY());
        theSnake.set(i, previous);
        previous = temp;
      }
      if (coord.getX() == targetLocation.getX() && coord.getY() == targetLocation.getY()) {

        theBoard.clearTarget(targetLocation);
        theBoard.clearSnake(theSnake);
        theSnake.add(theSnake.get(theSnake.size()-1).getValue());
        score++;
        targetLocation = setTarget();
        theBoard.placeTarget(targetLocation);
        theBoard.placeSnake(theSnake);

      }
    }
    else
      endGame = true;
  }
  /**
   * Sets the target to a random location within the constraints of the board and not intersecting the snake
   * @method setTarget
   * @return  an object of the Coordinate class that stores the location of the target point
   */
  private Coordinate setTarget () {
    int xCord = (int)(Math.random() * 20 + 1);
    int yCord = (int)(Math.random() * 20 + 1);

    while (!theBoard.isValidTarget(xCord, yCord)) {
      xCord = (int)(Math.random() * 20 + 1);
      yCord = (int)(Math.random() * 20 + 1);
    }
    Coordinate coord = new Coordinate (xCord, yCord);

    return coord;
  }
  /**
   * Sets the snake to a random location within the constraints of the board
   * @method getSnakeLocation
   * @return an object of the Coordinate class that stores the location of the head node of the snake
   */
  private Coordinate getSnakeLocation() {
    int xCord = (int)(Math.random() * 20 + 1);
    int yCord = (int)(Math.random() * 16 + 1);
    Coordinate coord = new Coordinate(xCord, yCord);
    return coord;
  }
  /**
   * Opens a save file to read using the FileUtils class and updates the game data
   * @method openSavedFile
   * @param  fileName      the name of the save file to be opened
   */
  private void openSavedFile (String fileName) {
    Snake newSnake = new Snake();
    while (!newSnake.isEmpty())
      newSnake.remove(0);
    Scanner infile = FileUtils.openToRead(fileName);
    infile.next();
    score = Integer.parseInt(infile.next());
    infile.next();
    int targetX = Integer.parseInt(infile.next());
    infile.next();
    int targetY = Integer.parseInt(infile.next());
    targetLocation = new Coordinate(targetX, targetY);
    infile.next();
    int xCord = Integer.parseInt(infile.next());
    infile.next();
    int yCord = Integer.parseInt(infile.next());
    newSnake.add(0, new Coordinate(xCord, yCord));

    while (infile.hasNext()) {
       xCord = Integer.parseInt(infile.next());
       infile.next();
       yCord = Integer.parseInt(infile.next());
       newSnake.add(new Coordinate(xCord, yCord));
    }
    infile.close();
    theSnake = newSnake;
  }
  /**
   * gets input from the user and calls the related methods
   * @method getInput
   */
  private void getInput () {
    boolean badInput = false;
    String str = "";
    do {
      theBoard.printBoard();
      badInput = false;
      str = Prompt.getString("Score: " + score + " (w - North, s - South, d - East, a - West, h - Help)");
      if (str.equals("q")) {
        str = Prompt.getString("Do you really want to quit (y or n)");
        if (str.equals("y"))
          endGame = true;
      }
      else if (str.equals("w") || str.equals("s") || str.equals("d") || str.equals("a")) {
        pickDirection(str);

      }
      else if (str.equals("h")) {
        printHelpMenu();
        badInput = true;

      }
      else if (str.equals("f")) {
        writeToFile();
        Prompt.getString("\nSaved game to file savedGame.txt to save game (Enter to continue)");
        badInput = true;
      }
      else {
        badInput = true;
        str = "";
      }
    }
    while (badInput);

  }
  /**
   * Writes gamedata to a new file called saveGame.txt
   * @method writeToFile
   */
  private void writeToFile() {
    PrintWriter outfile = FileUtils.openToWrite("savedGame.txt");
    outfile.println("Score");
    outfile.println(score);
    outfile.println("Target");
    outfile.println(targetLocation.getX() + " , " + targetLocation.getY());
    outfile.println("Snake");
    for (int i = 0; i < theSnake.size(); i++) {
      outfile.println(theSnake.get(i).getValue().getX() + " , " + theSnake.get(i).getValue().getY());
    }

    outfile.close();
  }
  /**
   * Sets the direction enum to the direction based off of the string
   * @method pickDirection
   * @param  str           the chosen direction
   */
  private void pickDirection (String str) {
    switch (str) {
      case "w": direction = Direction.NORTH;
      break;
      case "s": direction = Direction.SOUTH;
      break;
      case "d": direction = Direction.EAST;
      break;
      case "a": direction = Direction.WEST;
      break;
    }
  }
  /**
   * Prints the help menu
   * @method printHelpMenu
   */
  private void printHelpMenu () {
    System.out.println("\nSnake Game Help:");
    System.out.println("  w - moves snake North");
    System.out.println("  s - moves snake South");
    System.out.println("  d - moves snake East");
    System.out.println("  a - moves snake West");
    System.out.println("  f - saves game to file");
    System.out.println("  h - prints this message");
    System.out.println("  q - quit game without saving\n");
    Prompt.getString("Press Enter to continue");
  }

}
