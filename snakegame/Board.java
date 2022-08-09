/**
 * Board.java
 *
 * Prints the board for the SnakeGame class, including the target and the snake
 *
 * @author Anishka Chauhan
 * @since 5/19/2021
 */

public class Board {
  private String [][] theBoard;
  private  final int WIDTH = 20;
  private  final int HEIGHT = 20;

  public Board () {
    theBoard = new String[WIDTH + 2][HEIGHT + 2];

    for (int r = 0; r < theBoard.length; r++) {
      for (int c = 0; c < theBoard[0].length; c++) {
        if (r == 0 || r == theBoard.length -1) {
          if ((c == 0 || c == theBoard[0].length -1)) {
            theBoard[r][c] = "+ ";
          }
          else
          {
            theBoard[r][c] = "- ";
          }
        }
        else if (c == 0 || c == theBoard[0].length -1) {
          theBoard[r][c] = "| ";
        }
        else {
          theBoard[r][c] = "  ";
        }

      }
    }


  }
  /**
   * checks to see if target location is valid
   * @method isValidTarget
   * @param  x              column index
   * @param  y              row index
   * @return                true if valid, false otherwise
   */
  public boolean isValidTarget(int x, int y) {

    if (theBoard[y][x] == " @" || theBoard[y][x] == " *" || y == 0 || y == WIDTH || x == 0 || x == HEIGHT)
      return false;
    return true;
  }
  /**
   * Places target in specified location
   * @method placeTarget
   * @param  c           Coordinate object with coordinates for target
   */
  public void placeTarget (Coordinate c) {
    int x = c.getX();
    int y = c.getY();
    theBoard[y][x] = " +";

  }
  /**
   * Places the snake in the specified location
   * @method placeSnake
   * @param  s          the Snake object to be placed on the board
   */
  public void placeSnake(Snake s) {

    for (int i = 0; i < s.size(); i++) {
      int x = s.get(i).getValue().getX();
      int y = s.get(i).getValue().getY();
      if (i == 0)
        theBoard[y][x] = " @";
      else
        theBoard[y][x] = " *";
    }
  }
  /**
   * Removes the snake from the board
   * @method clearSnake
   * @param  s          Snake object to be removed
   */
  public void clearSnake(Snake s) {
    for (int i = 0; i < s.size(); i++) {
      int x = s.get(i).getValue().getX();
      int y = s.get(i).getValue().getY();
      theBoard[y][x] = "  ";
    }
  }
  /**
   * Removes target from the board
   * @method clearTarget
   * @param  c           [description]
   */
  public void clearTarget(Coordinate c) {
    theBoard[c.getY()][c.getX()] = "  ";
  }
  /**
   * Checks if snake location is valid to move to
   * @method isValidLocation
   * @param  x               desired column index
   * @param  y               desired row index
   * @return                 true if valid, false otherwise
   */
  public boolean isValidLocation (int x, int y) {

    if (theBoard[y][x] == " @" || theBoard[y][x] == " *" || x >= HEIGHT || x <= 0 || y >= WIDTH + 1 || y <= 0) {
      return false;
    }
    return true;
  }
  /**
   * Prints the game board
   * @method printBoard
   */
  public void printBoard () {
    System.out.println();
    for (int r = 0; r < theBoard.length; r++) {
        for (int c = 0; c < theBoard[0].length; c++) {
          System.out.print(theBoard[r][c]);
        }
        System.out.println();
    }

  }

}
