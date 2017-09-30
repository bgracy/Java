
public class myLinkedList <AnyType> implements Iterable <AnyType>
{
	// Defining Node Class
	public	class	Node <AnyType>
	{
		public	Node (AnyType data, Node<AnyType> head, Node<AnyType> tail)
		{
			myData = data;
			myHead = head;
			myTail = tail;
		}
		public AnyType myData;
		public Node<AnyType> myHead;		// next
		public Node<AnyType> myTail;		// previous
	}
	
	
	// myLinkedList Constructor
	public myLinkedList ()
	{
		beginMarker = 	null;
		endMarker =	null;
		theSize = 	0;		
	}
	
	
	// myLinkedList Functions/Methods
	public void clear ()
	{
		beginMarker = new Node<AnyType> (null, null, null);
		endMarker = beginMarker;
		theSize	= 0;
		//modCount++;
	}
	
	
	public int size () { return theSize; }
	public	boolean	exist( AnyType newVal)			
	{
		if (theSize == 0)
			return false;
		else
		{
			Node<AnyType>	CurrentNode	= beginMarker;

			for (int i = 0; i < theSize; i++)
			{
				if (CurrentNode.myData.equals(newVal))
					return true;
				else
					CurrentNode	= CurrentNode.myHead;
			}
			return false;	
		}
	}
	
	
	public	boolean	add	(AnyType newVal)
	{
		if (theSize == 0)
		{
			Node<AnyType> head = new Node<AnyType> (newVal, null, null);
			beginMarker = head;
			endMarker = head;
			theSize++;
			modCount++;
			return true;
		}
		else
		{
			Node<AnyType> newNode = new Node<AnyType> (newVal, null, endMarker);
			endMarker.myHead = newNode;
			endMarker = newNode;
			theSize++;
			modCount++;
			return	true;
		}
	}
	
	
	public	boolean	add	(int index, AnyType newVal)		// index starts at 1, cannot enter 0
	{
		if (index == 0)
		{
			System.out.println ("Index values start at 1 and go higher.");
			return false;
		}
		else
		{
			if (index > (theSize + 1))
			{
				System.out.println ("The requested index is too big. There are currently " + theSize + " elements in the list.");
				return false;
			}
			else
			{
				if (index == 1)
				{
					Node<AnyType> newNode = new Node<AnyType> (newVal, beginMarker, null);
					beginMarker.myTail = newNode;
					beginMarker	= newNode;
					theSize++;
					modCount++;
					return true;
				}
				else
				{
					if (index == (theSize + 1))
					{
						Node<AnyType>	newNode	= new Node<AnyType> (newVal, null, endMarker);
						endMarker.myHead = newNode;
						endMarker = newNode;
						theSize++;
						modCount++;
						return true;
					}
					else
					{
						if (index <= theSize)
						{
							Node<AnyType> newNode = new Node<AnyType> (newVal, null, null);
							Node<AnyType> CurrentNode = beginMarker;
							int	Counter	= 1;
							boolean	done = false;
							while (done == false)
							{ 
								if (Counter == index)
								{
									newNode.myHead = CurrentNode;
									newNode.myTail = CurrentNode.myTail;
									CurrentNode.myTail.myHead = newNode;
									CurrentNode.myTail = newNode;
									theSize++;
									modCount++;
									done = true;
									return true;
								}
								else
								{
									Counter++;
									CurrentNode	= CurrentNode.myHead;
								}
							}
						}
						else;
					}
					return false;
				}
			}
		}
		 
	}
	
	
	public	Node<AnyType> get (AnyType nodeData)		// this is finding and getting a value within the list
	{
		Node<AnyType> CurrentNode = beginMarker;
		while (!(CurrentNode.equals (null)))
		{
			if (nodeData.equals(CurrentNode.myData))
			{
				return CurrentNode;
			}
			else
			{
				CurrentNode = CurrentNode.myHead;
				if (CurrentNode.equals(null))
					System.out.println ("The value you requested is not in the list.");
				else;
			}
		}
		return null;
	}

	
	public void	printList ()
	{
		if (theSize > 0)
		{
			Node<AnyType> CurrentNode = beginMarker;
			do {
				System.out.println (CurrentNode.myData);
				CurrentNode = CurrentNode.myHead;
			} while (CurrentNode != null);
		}
		else
			System.out.println ("There is nothing in the list currently.");
	}
	
	
	public	void remove	(AnyType removeVal)			
	{
		if (theSize == 1)
		{
			if (removeVal.equals(beginMarker.myData))
			{
				Node<AnyType> head	= new Node<AnyType> (null, null, null);
				beginMarker = head;
				endMarker = head;
				theSize = 0;
				modCount++;
			}
			else
				System.out.println ("The value does not exist in the list.");
		}
		else
		{
			if (exist (removeVal))
			{
				if (removeVal.equals(beginMarker.myData))		// if it's the head node
				{
					beginMarker	= beginMarker.myHead;
					beginMarker.myTail	= null;	
					theSize--;
					modCount++;
				}
				else
				{
					if (removeVal.equals(endMarker.myData))		// if it's the tail node
					{
						endMarker = endMarker.myTail;
						endMarker.myHead = null;
						theSize--;
						modCount++;
					}
					else
					{										// it's somewhere in the middle of the list
						Node<AnyType> CurrentNode = beginMarker;
						boolean	done = false;
						while (done == false)
						{
							if (CurrentNode.myData.equals(removeVal))
							{
								CurrentNode.myTail.myHead	= CurrentNode.myHead;
								CurrentNode.myHead.myTail	= CurrentNode.myTail;
								CurrentNode.myTail 			= null;
								CurrentNode.myHead			= null;
								theSize--;
								modCount++;
								done = true;
							}
							else
								CurrentNode	= CurrentNode.myHead;
						}
					}
				}
			}
			else
				System.out.println ("The value does not exist in the list.");
		}
	}
	
	
	public java.util.Iterator <AnyType> iterator ()	{ return new LinkedListIterator (); }
	
	
	// myLinkedList data values
	private	int				theSize;
	private	int				modCount	= 0;
	private	Node<AnyType>	beginMarker;	// head of the linked list
	private	Node<AnyType>	endMarker;		// tail of the linked list
	
	
	private class LinkedListIterator implements java.util.Iterator<AnyType>
	{
		private Node<AnyType> current = beginMarker.myHead;
		private int	expectedModCount = modCount;
		private boolean	oktoRemove = false;
		
		public void remove ()
		{
			if (modCount != expectedModCount)
				throw new java.util.ConcurrentModificationException ();
			if (!oktoRemove)
				throw new IllegalStateException ();
			
			//myLinkedList.remove (current.myTail);
			expectedModCount++;
			oktoRemove = false;
		}
		
		
		public	boolean	hasNext	() { return current != endMarker; }
		public	AnyType	next ()
		{
			if (modCount != expectedModCount)
				throw new java.util.ConcurrentModificationException ();
			if (!hasNext ())
				throw new java.util.NoSuchElementException ();
			
			AnyType nextItem	= current.myData;
			current				= current.myHead;
			oktoRemove			= true;
			return nextItem;
		}
	}
	
	
	private	void	addBefore	(Node<AnyType> previousNode, AnyType newNode)
	{
		Node<AnyType>	addedNode	= new Node<AnyType> (newNode, previousNode, previousNode.myTail);
		previousNode.myTail.myHead	= addedNode;
		previousNode.myTail			= addedNode;
		theSize++;
		modCount++;
	}
	
	
	// myLinkedlist Testing Functions/Methods below
	private	void	testListIntegers	()
	{
		myLinkedList <Integer> testList = new myLinkedList <Integer> ();
		testList.add (new Integer (5));
		testList.add (new Integer (4));
		testList.add (new Integer (3));
		System.out.println ("We have so far inserted " + testList.size () + " elements in the list");
		testList.remove (4);
		System.out.println ("Now, there are only " + testList.size () + " elements left in the list");
		testList.add (1, new Integer (7));
		testList.add (2, new Integer (8));
		System.out.println ("About to print the contents of the list:");
		testList.printList ();
	}
		
	
	private	void	testListStrings ()
	{
		myLinkedList <String> testList = new myLinkedList <String> ();
		testList.add (new String ("hello"));
		testList.add (new String ("this is"));
		testList.add (new String ("Brad"));
		System.out.println (" We have so far inserted " + testList.size () + " elements in the list");
		testList.remove ("this is");
		System.out.println (" Now, there are only " + testList.size () + " elements left in the list");
		System.out.println ("About to print the contents of the list:");
		testList.printList ();
	}			
	
	
	public static void main(String[] args)
	{
		myLinkedList Object	= new myLinkedList ();
		Object.testListIntegers();
		Object.testListStrings();
	}

}
