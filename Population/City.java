
/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author Anishka Chauhan
 *	@since 1/27/2021
 */
public class City implements Comparable<City> {

	// fields
	private String state;
	private String name;
	private String designation;
	private int population;

	// constructor
	public City (String state, String name, String designation, int population)
	{
		this.state = state;
		this.name = name;
		this.designation = designation;
		this.population = population;

	}

	/**	Compare two cities populations
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 *		If populations are different, then returns (this.population - other.population)
	 *		else if states are different, then returns (this.state - other.state)
	 *		else returns (this.name - other.name)
	 */
	public int compareTo (City other)
	{
		if (this.population != other.population)
		{
			return this.population - other.population;
		}
		else if (this.state != other.state)
		{
			return this.state.compareTo(other.state);
		}
		return this.name.compareTo(other.name);
	}

	/**	Equal city name and state name
	 *	@param other		the other City to compare
	 *	@return				true if city name and state name equal; false otherwise
	 */
	public boolean equals (City other)
	{
		if (this.name == other.name && this.state == other.state)
			return true;
		return false;
	}

	/**	Accessor methods */
	public String getName ()
	{
		return name;
	}
	public String getState ()
	{
		return state;
	}

	/**	toString */
	@Override
	public String toString() {
		return String.format("%-22s %-22s %-12s %,12d", state, name, designation,
						population);
	}
}
