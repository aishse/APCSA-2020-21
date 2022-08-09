import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *	Population - Sorts a text file of ~30,000 cities lexigraphically and by population using the helper class SortMethods.java.
 *	A user is presented a series of options through a menu, including finding the top 50 most populous cities, the top 50 least populous cities,
 *	cities sorted from A - Z, and cities sorted from Z - A. There is also two additional options in which the user can input a city name and find all
 *	cities that match, and another in which the user can find all cities in a chosen state. The user's input is error-checked to avoid any crashes.
 *
 *	Requires FileUtils and Prompt classes.
 *
 *	@author Anishka Chauhan
 *	@since 1/28/2021
 */
public class Population {

	// List of cities
	private List<City> cities;
	private int index; // sort selection (1-9)
	private boolean endSearch; // boolean for checking if the program should continue or end
	private SortMethods sorter; // an instance of the SortMethods class, for sorting

	// US data file
	private final String DATA_FILE = "usPopData2017.txt";

	public Population () // sets all fields to default values
	{
		index = -1;
		endSearch = false;
		cities = new ArrayList<City>();
		sorter = new SortMethods();
	}

	public static void main(String[] args)
	{
		Population run = new Population();
		run.start();
	}
	/**
	 * Runs the basic strcuture of the game. Resets cities ArrayList after every loop.
	 *
	 * @method start
	 */
	public void start ()
	{
		loadCities(); // loads information from the text file into cities arraylist
		printIntroduction(); // prints introduction
		System.out.println(cities.size() + " cities in database.\n");
		// loops the program again so the user can choose another sort
		do
		{
			cities = new ArrayList<City>(); // resets cities after each iteration
			loadCities();
			printMenu();
			checkInput();
		}
		while (endSearch == false); // ends when endSearch is true
		System.out.println("\nThanks for using Population!");

	}
	/**
	 * Calls the corresponding sort method to the integer choice
	 *
	 * Preecondition: query must be a an integer that corresponds to a sort
	 * @method executeSort
	 * @param  query       integer that designates the sort
	 */
	private void executeSort (int query)
	{
		switch (query)
		{
			case 1: ascendingPopulation();
			break;
			case 2: descendingPopulation();
			break;
			case 3: ascendingName();
			break;
			case 4: descendingName();
			break;
			case 5: mostPopulousCity();
			break;
			case 6: allCities();
			break;
		}
	}

	/**
	 * Uses helper method getAllCities() to find all instances of a city based off the user's query. Uses merge sort via
	 * sortMethods to sort them by highest population.
	 *
	 * @method allCities
	 */
	private void allCities ()
	{
		System.out.println();
		boolean badInput = false;
		String cityQuery = "";
		List<City> chosenCity = new ArrayList<City>();
		// error checks the user's chosen city to see if it is valid by checking the size of the chosen city array
		do
		{
			badInput = false;
			chosenCity = new ArrayList<City>();
			cityQuery = Prompt.getString("Enter city name");
			chosenCity = getAllCities(cityQuery); // gets all the cities that match the user's search
			if (chosenCity.size() <= 0) // if there are no cities that match, loops again
			{
				badInput = true;
			}
		}
		while (badInput);

		sorter.mergeSort(chosenCity, 0, chosenCity.size()-1); // sorts the chosenCity array by descending population

		System.out.println("\nCity " + cityQuery + " by population: ");
		System.out.printf("%11s %21s %22s %20s%n", "State", "City", "Type", "Population");

		for (int i = 0; i < chosenCity.size(); i++)
		{
				System.out.printf("  %2d: %-4s%n", i+1, chosenCity.get(i).toString());
		}
		System.out.println();

	}
	/**
	 * Iterates through cities array and adds all city objects corresponding with the query to chosenCity.
	 *
	 * @method getAllCities
	 * @param  query        The city name to compare
	 * @return              ArrayList of City objects that all share the same city name
	 */
	private List<City> getAllCities (String query)
	{
		List<City> chosenCity = new ArrayList<City>();
		for (int i = 0; i < cities.size(); i++)
		{
			if (cities.get(i).getName().equals(query))
			{
				chosenCity.add(cities.get(i));
			}
		}
		return chosenCity;
	}
	/**
	 * Finds the most populous cities in a chosen state and prints out the first 50. Each state is given a corressponding index, and the user
	 * selects one depending on their choice. A helper method is used to extract all possible states from the database, and is saved in an
	 * ArrayList. The user's input is error checked for validity. An additional helper method is used to find all city objects that have the
	 * same state name as the chosen query.
	 *
	 * @method mostPopulousCity
	 */
	private void mostPopulousCity ()
	{
		System.out.println();
		int stateIndex = 0;
		sorter.insertionSortString(cities);
		ArrayList<String> states = removeRepeats(cities); // gets all possible states from the ArrayList
		formatStates(states); // prints the selection of states nicely using printf
		do // error checks user input
		{
			stateIndex = Prompt.getInt("Select state (1 - 50)");
		}
		while (stateIndex < 1 || stateIndex > 50);

		System.out.println("\n Fifty most populous cities in " + states.get(stateIndex-1));
		System.out.printf("%11s %21s %22s %20s%n", "State", "City", "Type", "Population");

		cities = sortStates(states.get(stateIndex-1)); // uses sortStates to get all corresponding city objects with same name

		for (int i = 0; i + 1<= 50; i++)
		{
			if (i + 1 <= cities.size())
				System.out.printf("  %2d: %-4s%n", i+1, cities.get(i).toString());
		}
		System.out.println();

	}

	/**
	 * Helper method for finding and sorting all city objects with state name equal
	 * to parameter string. Uses merge sort to sort by descending population.
	 *
	 * Preecondition: name must be a valid state for the method to return an ArrayList
	 * with size greater than 0.
	 * @method sortStates
	 * @param  name       The state name to find
	 * @return            An ArrayList of city objects with the same state name as the parameter
	 */
	private List<City> sortStates (String name)
	{
		List<City> states = new ArrayList<City>();
		for (int i = 0; i < cities.size(); i++)
		{
			if (cities.get(i).getState().equals(name))
			{
				states.add(cities.get(i));
			}
		}
		sorter.mergeSort(states, 0, states.size()-1);
		return states;
	}
	/**
	 * Formats and prints the states menu nicely using printf.
	 *
	 * @method formatStates
	 * @param  states       ArrayList to be formatted and printed.
	 */
	private void formatStates (ArrayList<String> states)
	{
		for (int i = 1; i <= 10; i++)
		{
			for (int j = 1; j < states.size(); j++)
			{
				if (j % 10 == i)
				{
					System.out.printf(" %2d:%-15s", j, states.get(j-1));
				}
			}
			if (i < 10)
				System.out.println();
		}
		for (int j = 10; j <= states.size(); j+= 10)
		{
			System.out.printf(" %2d:%-15s", j, states.get(j-1));

		}

		System.out.println();
		System.out.println();
	}
	/**
	 * Creates an array of unique states (no repeats) from a List of City objects.
	 *
	 * @method removeRepeats
	 * @param  list          The array of City objects to be processed
	 * @return               An array of Strings with unique states
	 */
	private ArrayList<String> removeRepeats (List<City> list)
	{
		ArrayList<String> states = new ArrayList<String>();
		String str = "";
		for (int i = 0; i < list.size(); i++)
		{
				if (!str.equals(list.get(i).getState()))
				{
					states.add(list.get(i).getState());
				}
			 str = list.get(i).getState();

		}
		return states;
	}
	/**
	 * Sorts the array by city name in descending lexigraphic order (Z-A) using
	 * merge sort via the SortMethods class and prints the first 50 elements as well
	 * as the total amount of time taken for the sort (in milliseconds).
	 *
	 * @method descendingName
	 */
	private void descendingName ()
	{
		System.out.println();

		long startMillisec = System.currentTimeMillis();
		sorter.mergeSortString(cities, 0, cities.size()-1);
	  long endMillisec = System.currentTimeMillis();

		System.out.println("Fifty cities sorted by name descending");
		System.out.printf("%11s %21s %22s %20s%n", "State", "City", "Type", "Population");

		for (int i = 0; i + 1<= 50; i++)
				System.out.printf("  %2d: %-4s%n", i+1, cities.get(i).toString());

		System.out.println("\nElapsed Time " + (endMillisec-startMillisec) + " milliseconds\n");

	}
	/**
	 * Sorts the array by city name in ascending lexigraphic order (A-Z) and prints the
	 * first 50 elements using merge sort via the SortMethods class and the total amount of
	 * time taken for the sort (in milliseconds).
	 *
	 * @method ascendingName
	 */
	private void ascendingName ()
	{
		System.out.println();

		long startMillisec = System.currentTimeMillis();
		sorter.insertionSort(cities);
	  long endMillisec = System.currentTimeMillis();

		System.out.println("Fifty cities sorted by name");
		System.out.printf("%11s %21s %22s %20s%n", "State", "City", "Type", "Population");

		for (int i = 0; i + 1<= 50; i++)
				System.out.printf("  %2d: %-4s%n", i+1, cities.get(i).toString());

		System.out.println("\nElapsed Time " + (endMillisec-startMillisec) + " milliseconds\n");

	}
	/**
	 * Sorts the array by state population in ascending order (least to greatest) and prints the first 50 elements
	 * using selection sort (via the SortMethods class) and the total amount of time taken for the sort (in milliseconds).
	 *
	 * @method ascendingPopulation
	 */
	private void ascendingPopulation ()
	{
		System.out.println();

		long startMillisec = System.currentTimeMillis();
		sorter.selectionSort(cities);
		long endMillisec = System.currentTimeMillis();

		System.out.println("Fifty least populous cities");
		System.out.printf("%11s %21s %22s %20s%n", "State", "City", "Type", "Population");

		for (int i = 0; i + 1<= 50; i++)
			System.out.printf("  %2d: %-4s%n", i+1, cities.get(i).toString());

		System.out.println("\nElapsed Time " + (endMillisec-startMillisec) + " milliseconds\n");

	}
	/**
	 * Sorts the array by state population in descending order (greatest to least) and prints the first 50 elements using merge sort (via the
	 * SortMethods class) and the total amount of time taken for the sort (in milliseconds).
	 *
	 * @method descendingPopulation
	 */
	private void descendingPopulation()
	{
		System.out.println();

		long startMillisec = System.currentTimeMillis();
		sorter.mergeSort(cities, 0, cities.size()-1);
		long endMillisec = System.currentTimeMillis();

		System.out.println("Fifty most populous cities");
		System.out.printf("%11s %21s %22s %20s%n", "State", "City", "Type", "Population");

		for (int i = 0; i +1 <= 50; i++)
			System.out.printf("  %2d: %-4s%n", i+1, cities.get(i).toString());

		System.out.println("\nElapsed Time " + (endMillisec-startMillisec) + " milliseconds\n");

	}
	/**
	 * Uses the FileUtils class to open the text file containing all the state/city data. Processes the data
	 * using Scanner into the cities ArrayList.
	 *
	 * @method loadCities
	 */
	private void loadCities ()
	{
		Scanner scan = FileUtils.openToRead(DATA_FILE);
		int counter = 0;
		while (scan.hasNext())
		{
			cities.add(new City(scan.next(), scan.next(), scan.next(), scan.nextInt()));
		}
		scan.close();
	}
	/**
	 * Checks the user's input and calls the executeSort method if the user wishes to not terminate the program.
	 * Checks for whether it is an integer (via Prompt class) and if it is a valid index. If the user inputs the index to quit the
	 * program (9), then endSearch is set to true and the executeSort method is not called.
	 *
	 * @method checkInput
	 */
	private void checkInput()
	{
		boolean badInput = false;
		do {
			badInput = false;
			index = Prompt.getInt("Enter selection (1 - 9)");

			if (index > 9 || index <= 0)
					badInput = true;
			if (index == 9)
			{
				endSearch = true;
			}
		} while (badInput == true);
		if (!endSearch)
				executeSort(index);
	}

	/**	Prints the introduction to Population */
	public void printIntroduction() {
		System.out.println("   ___                  _       _   _");
		System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
		System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
		System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
		System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
		System.out.println("           |_|");
		System.out.println();
	}

	/**	Print out the choices for population sorting */
	public void printMenu() {
		System.out.println("1. Fifty least populous cities in USA (Selection Sort)");
		System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
		System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
		System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
		System.out.println("5. Fifty most populous cities in named state");
		System.out.println("6. All cities matching a name sorted by population");
		System.out.println("9. Quit");
	}

}
