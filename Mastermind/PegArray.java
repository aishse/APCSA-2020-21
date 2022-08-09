/**
 *  This class creates and manages one array of pegs from the game MasterMind.
 *
 *  @author Anishka Chauhan
 *  @since 11/1/2020
*/

public class PegArray {

	// array of pegs
	private Peg [] pegs;

	// the number of exact and partial matches for this array
	// as matched against the master.
	// Precondition: these values are valid after getExactMatches() and getPartialMatches()
	//				are called
	private int exactMatches, partialMatches;

	/**
	 *	Constructor
	 *	@param numPegs	number of pegs in the array
	 */
	public PegArray(int numPegs)
	{
		pegs = new Peg[numPegs];
		for (int i = 0; i < pegs.length; i++)
		{
			pegs[i] = new Peg();
		}
	}

	/**
	 *	Return the peg object
	 *	@param n	The peg index into the array
	 *	@return		the peg object
	 */
	public Peg getPeg(int n)
	{
		return pegs[n];
	}

	/**
	 *  Finds exact matches between master (key) peg array and this peg array
	 *	Postcondition: field exactMatches contains the matches with the master
	 *  @param master	The master (code) peg array
	 *	@return			The number of exact matches
	 */
	public int getExactMatches(PegArray master)
	{

		for (int i = 0; i < pegs.length; i++)
		{
			if (pegs[i].getLetter() == master.getPeg(i).getLetter())
			{
				exactMatches++;
			}
		}
		return exactMatches;
	}

	/**
	 *  Find partial matches between master (key) peg array and this peg array
	 *	Postcondition: field partialMatches contains the matches with the master
	 *  @param master	The master (code) peg array
	 *	@return			The number of partial matches
	 */
	public int getPartialMatches(PegArray master)
	{
		int numMatches = 0;
		for (int i = 0; i < pegs.length; i++)
		{
			for (int j = i; j < pegs.length; j++)
			{
				if (pegs[i].getLetter() == master.getPeg(j).getLetter())
				{
					numMatches++;
				}
			}
			if (numMatches >= 1)
			{
				partialMatches++;
			}
		}
		partialMatches = partialMatches - exactMatches;
		return partialMatches;
	}

	// Accessor methods
	// Precondition: getExactMatches() and getPartialMatches() must be called first
	public int getExact() { return exactMatches; }
	public int getPartial() { return partialMatches; }

	/**
	* Creates master array
	*/
	public void createMasterArray ()
	{
		for (int i = 0; i < pegs.length; i++)
		{
			int temp = (int)(Math.random()*6 + 65);

			char character = (char) temp;
			pegs[i].setLetter(character);
			System.out.println(pegs[i].getLetter() + " ");
		}

	}
	/**
	*  Sets the character for the peg array
	*
	*  @param c          the character that will replace the current one in the array
	*  @param index      index of array
	*/
	public void setPeg (char c, int index)
	{
		 pegs[index].setLetter(c);
	}


}
