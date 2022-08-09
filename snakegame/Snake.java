/**
 *	Snake for the SnakeGame
 *
 *	@author	Mr Greenstein
 */
public class Snake extends SinglyLinkedList<Coordinate> {
	
	/**	Create Snake that is 5 long vertical starting at coord */
	public Snake(Coordinate coord) {
		clear();
		int xStart = coord.getX();
		int yStart = coord.getY();
		for (int a = 0; a < 5; a++)
			add(new Coordinate(xStart, yStart + a));
	}
	
	/**	Default Snake starting at (3,3) */
	public Snake() {
		this(new Coordinate(3,3));
	}
	
}