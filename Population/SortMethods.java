import java.util.List;
import java.util.ArrayList;
/**
 *	SortMethods - Sorts an array of Integers in ascending order.
 *
 *	@author Anishka Chauhan
 *	@since	1/21/21
 */
public class SortMethods {
	private List<City> temp;
	/**
	 *	Bubble Sort algorithm - in ascending order
	 *	@param arr		array of Integer objects to sort
	 */
	public void bubbleSort(List<City> arr)
	{
		for (int outer = arr.size() - 1; outer > 0; outer--)
			for (int inner = 0; inner < outer; inner++)
				if (arr.get(inner).compareTo(arr.get(inner + 1)) > 0)
					swap(arr, inner, inner + 1);
	}

	/**
	 *	Swaps two Integer objects in array arr
	 *	@param arr		array of Integer objects
	 *	@param x		index of first object to swap
	 *	@param y		index of second object to swap
	 */
	public void swap(List<City> arr, int x, int y) {
		City temp = arr.get(x);
		arr.set(x, arr.get(y));
		arr.set(y, temp);
	}

	/**
	 *	Selection Sort algorithm - sorts populations in ascending order
	 *	@param arr		array of City objects to sort
	 */
	public void selectionSort(List<City> arr) {
		for (int n = arr.size(); n > 1; n--)
		{
			int iMax = 0;
			for (int i = 0; i < n; i++)
			{
				if (arr.get(i).compareTo(arr.get(iMax)) > 0)
				{
					iMax = i;
				}
			}
			swap(arr, iMax, n-1);

		}
	}

	/**
	 *	Insertion Sort algorithm - in city name in ascending order lexigraphically
	 *	@param arr		array of City objects to sort
	 */
	public void insertionSort(List<City> arr) {
		for (int n = 1; n < arr.size(); n++)
		{
			City aTemp = arr.get(n);

			int i = n;
			while (i > 0 && aTemp.getName().compareTo(arr.get(i -1).getName()) < 0)
			{
				arr.set(i, arr.get(i-1));
				i--;
			}
			arr.set(i, aTemp);

		}
	}
	/**
	 *	Insertion Sort algorithm for organizing states - in ascending order
	 *	@param arr		array of Integer objects to sort
	 */
	public void insertionSortString(List<City> arr) {
		for (int n = 1; n < arr.size(); n++)
		{
			City aTemp = arr.get(n);

			int i = n;
			while (i > 0 && aTemp.getState().compareTo(arr.get(i -1).getState()) < 0)
			{
				arr.set(i, arr.get(i-1));
				i--;
			}
			arr.set(i, aTemp);

		}
	}

	/**
	 *	Merge Sort algorithm for Strings
	 *	@param arr		array of City objects to sort
	 */
	public void mergeSortString (List<City> arr, int start, int end)
	{
		temp = new ArrayList<City>();
		for (int i = 0; i < arr.size(); i++)
		{
			temp.add(i, new City("", "", "", 0));
		}
		recursiveHelperString(arr, start, end);
	}
	/**
	 * Recursively swaps and sorts the array by name lexigraphically.
	 * @method recursiveHelperString
	 * @param  arr                   the array of City objects to sort
	 * @param  from                  starting point on array
	 * @param  to                    ending point on array
	 */
	public void recursiveHelperString(List<City> arr, int from, int to)
	{
		if (to - from < 2)
		{
			if (to > from && arr.get(to).getName().compareTo(arr.get(from).getName()) > 0)
			{
				swap(arr, to, from);
			}
		}
		else
		{
			int middle = (from + to)/2;
			recursiveHelperString(arr, from, middle);
			recursiveHelperString(arr, middle + 1, to);
			mergeString(arr, from, middle, to);

		}
	}
	/**
	 * Merges the two sorted halves of the arrays and sorts them (city name specific)
	 * @param  arr    array
	 * @param  from   the start index
	 * @param  middle midpoint of array
	 * @param  to     the end index of array
	 */
	private void mergeString(List<City> arr, int from, int middle, int to)
	{
		int i = from;
		int j = middle + 1;
		int k = from;

		while (i <= middle && j <= to)
		{
			if (arr.get(i).getName().compareTo(arr.get(j).getName()) > 0)
			{
				temp.set(k, arr.get(i));
				i++;
			}
			else
			{
				temp.set(k, arr.get(j));
				j++;
			}
			k++;
		}

		while (i <= middle)
		{
			temp.set(k, arr.get(i));
			i++;
			k++;
		}
		while (j <= to)
		{
			temp.set(k, arr.get(j));
			j++;
			k++;
		}

		for (k = from; k <= to; k++)
		{
			arr.set(k, temp.get(k));
		}
	}
	/**
	 *	Merge Sort algorithm - in ascending order for population
	 *	@param arr		array of Integer objects to sort
	 *	@param start	start index
	 *	@param end		end index
	 */
	public void mergeSort (List<City> arr, int start, int end)
	{
		temp = new ArrayList<City>();
		for (int i = 0; i < arr.size(); i++)
		{
			temp.add(i, new City("", "", "", 0));
		}
		recursiveHelper(arr, start, end);
	}
	/**
	*	Recursively swaps and sorts the array by population ascending order.
	*
	* @method recursiveHelper
	* @param  arr                   the array of City objects to sort
	* @param  from                  starting point on array
	* @param  to                    ending point on array
	* */
	public void recursiveHelper(List<City> arr, int from, int to)
	{
		if (to - from < 2)
		{
			if (to > from && arr.get(to).compareTo(arr.get(from)) > 0)
			{
				swap(arr, to, from);
			}
		}
		else
		{
			int middle = (from + to)/2;
			recursiveHelper(arr, from, middle);
			recursiveHelper(arr, middle + 1, to);
			merge(arr, from, middle, to);
		}
	}

	/**
	 * Merges the two sorted halves of the arrays and sorts them (populations)
	 * @param  arr    array
	 * @param  start  [description]
	 * @param  middle [description]
	 * @param  end    [description]
	 */
	private void merge(List<City> arr, int from, int middle, int to)
	{
		int i = from;
		int j = middle + 1;
		int k = from;

		while (i <= middle && j <= to)
		{
			if (arr.get(i).compareTo(arr.get(j)) > 0)
			{
				temp.set(k, arr.get(i));
				i++;
			}
			else
			{
				temp.set(k, arr.get(j));
				j++;
			}
			k++;
		}

		while (i <= middle)
		{
			temp.set(k, arr.get(i));
			i++;
			k++;
		}
		while (j <= to)
		{
			temp.set(k, arr.get(j));
			j++;
			k++;
		}

		for (k = from; k <= to; k++)
		{
			arr.set(k, temp.get(k));
		}
	}

}
