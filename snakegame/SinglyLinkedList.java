import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
/**
 *	SinglyLinkedList - creates a singly linked list using an ArrayList comprised of ListNodes. Has
 *	several accessor and modifier methods that retreive information about the ListNodes and the objects they hold. 
 *
 *	@author Anishka Chauhan
 *	@since 4/26/2021
 */
public class SinglyLinkedList<E extends Comparable<E>>
{
	/* Fields */
	private ListNode<E> head, tail;		// head and tail pointers to list
	private List<ListNode<E>> theList; // the list of nodes

	/* No-args Constructors */
	public SinglyLinkedList() {
		theList = new ArrayList<ListNode<E>>(); //creates new list

	}

	/** Copy constructor */
	public SinglyLinkedList(SinglyLinkedList<E> oldList) {
	  theList = new ArrayList<ListNode<E>>();
		for (int i = 0; i < oldList.size(); i++) {
			ListNode<E> ptr = new ListNode<E>(oldList.get(i).getValue()); // copys all of the oldList's listNodes' values into new listNodes
			theList.add(ptr);
		}
		for (int j = 0; j < oldList.size()-1; j++) { // sets each ListNode's next pointers
			ListNode<E> ptr = theList.get(j);
			ListNode<E> next = theList.get(j + 1);
			ptr.setNext(next);

		}
		head = this.theList.get(0); // sets head
		tail = this.theList.get(size()-1); // sets tail
	}

	/**	Clears the list of elements */
	public void clear() {
		head = null;
		tail = null;
		while (!theList.isEmpty()) {
			theList.remove(0);
		}

	}

	/**	Add the object to the end of the list
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public boolean add(E obj) {
		int size = theList.size();
		if (theList.size() < 1) { // no elements
			ListNode<E> node = new ListNode<E>(obj, tail);
			theList.add(node); // add new node
			node.setNext(null);
			head = node;
			tail = node; // set node to head
		}
		else { // has elements
			ListNode<E> node = new ListNode<E>(obj, tail);
			tail.setNext(node); // create new node
			theList.add(node); // add new node
			node.setNext(null);
			tail = node;
		}

		if (size < theList.size())
			return true;
		return false;
	}

	/**	Add the object at the specified index
	 *	@param index		the index to add the object
	 *	@param obj			the object to add
	 *	@return				true if successful; false otherwise
	 *	@throws NoSuchElementException if index does not exist
	 */
	public boolean add(int index, E obj) {
		int size = theList.size();
		ListNode<E> node = new ListNode<E>(obj);

			try {
				theList.add(index, node);
			}
			catch (IndexOutOfBoundsException e)
			{
				if (index == size()) {
					theList.add(node);
				}
				else
					throw new NoSuchElementException();
			}
			if (theList.size() >= 2) { // at least 2 elements
				if (index == 0) {
					head = node;
					node.setNext(theList.get(index + 1));
				}
				else if (index >= theList.size()-1){
					if (index == theList.size()) {
						tail = node;
						node.setNext(null);
					}
					else
					{
						node.setNext(theList.get(index));
					}
					theList.get(index - 1).setNext(node);
				}
				else {
					node.setNext(theList.get(index + 1));
					theList.get(index - 1).setNext(node);
				}
			}
			else { // has only one element
				head = node;
				node.setNext(null);
			}

		if (size < theList.size())
			return true;
		return false;
	}

	/**	@return the number of elements in this list */
	public int size() {

		return theList.size();
	}

	/**	Return the ListNode at the specified index
	 *	@param index		the index of the ListNode
	 *	@return				the ListNode at the specified index
	 *	@throws NoSuchElementException if index does not exist
	 */
	public ListNode<E> get(int index) {
		try {
			return theList.get(index);
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new NoSuchElementException();
		}

	}

	/**	Replace the object at the specified index
	 *	@param index		the index of the object
	 *	@param obj			the object that will replace the original
	 *	@return				the object that was replaced
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E set(int index, E obj) {
		try {
			theList.get(index).setValue(obj);

		}
		catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
		return obj;
	}

	/**	Remove the node at the specified index
	 *	@param index		the index of the element
	 *	@return				the object in the element that was removed
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E remove(int index) {

		try {
			E obj = theList.get(index).getValue();
			if (index == 0) {
				theList.remove(index);
				if (theList.size() > 0)
					head = theList.get(0);
				else
					head = null;
			}
			else if (index == theList.size()-1) {
				theList.remove(index);
				theList.get(theList.size()-1).setNext(null);
				if (theList.size() > 1)
					tail = theList.get(theList.size()-1);
				else {
					head = theList.get(0);
				}
			}
			else {
				theList.remove(index);
				theList.get(index-1).setNext(theList.get(index));
			}
			return obj;
		}
		catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}


	}

	/**	@return	true if list is empty; false otherwise */
	public boolean isEmpty() {
		if (head == null)
			return true;
		return false;
	}

	/**	Tests whether the list contains the given object
	 *	@param object		the object to test
	 *	@return				true if the object is in the list; false otherwise
	 */
	public boolean contains(E object) {

		ListNode<E> ptr = head;
		while (ptr != null)
		{
			if (object.equals(ptr.getValue())) {
				return true;
			}
			ptr = ptr.getNext();
		}
		return false;
	}

	/**	Return the first index matching the element
	 *	@param element		the element to match
	 *	@return				if found, the index of the element; otherwise returns -1
	 */
	public int indexOf(E element) {
		int i = 0;
		ListNode<E> ptr = theList.get(i);
		while (i < size())
		{
			ptr = theList.get(i);
			if (element.equals(ptr.getValue())) {
				return i;
			}
			i++;

		}
		return -1;
	}

	/**	Prints the list of elements */
	public void printList()
	{
		ListNode<E> ptr = head;

		while (ptr != null)
		{
			System.out.print(ptr.getValue() + "; ");

			ptr = ptr.getNext();

		}
	}


}
