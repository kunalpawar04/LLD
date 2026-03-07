package org.example;

import java.util.ArrayList;

/*
 Iterator Design Pattern:
 ---------------------------------------
 - Provides a way to traverse elements of a collection without exposing its internal structure.
 - Separates traversal logic from the collection itself.
 - Different collections can provide their own iterator implementations while the client uses the same interface.

 In this example:
 - Iterator defines traversal operations (hasNext, next)
 - IterableCollection defines a method to create an iterator
 - LinkedList and Vector are concrete collections
 - LinkedListIterator and VectorIterator implement traversal logic
 - Client code interacts only with Iterator interface
*/

/*
---------------------------------------
  Iterator Interface
---------------------------------------
*/

/*
 Defines methods required to iterate over a collection.
 Every concrete iterator must implement these methods.
*/
interface Iterator {
    boolean hasNext(); // Checks if more elements exist
    Object next();     // Returns the next element
}

/*
---------------------------------------
  IterableCollection Interface
---------------------------------------
*/

/*
 Any collection that wants to support iteration
 must implement this interface and provide its iterator.
*/
interface IterableCollection {
    Iterator getIterator();
}

/*
---------------------------------------
  Node class for LinkedList (Pointer based structure)
---------------------------------------
*/

/*
 Represents a single node in the LinkedList.
 Each node stores data and a pointer to the next node.
*/
class Node {
    String data;
    Node next;

    Node(String data) {
        this.data = data;
        this.next = null;
    }
}

/*
---------------------------------------
  LinkedList Collection
---------------------------------------
*/

/*
 Concrete collection implemented using pointers.

 Responsibilities:
 - Store elements using nodes
 - Provide an iterator for traversal
*/
class LinkedList implements IterableCollection {

    private Node head;

    // Adds element at the end of the linked list
    public void add(String value) {
        Node newNode = new Node(value);

        // If list is empty
        if (head == null) {
            head = newNode;
            return;
        }

        // Traverse to last node
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;
    }

    // Returns the head node so iterator can start traversal
    public Node getHead() {
        return head;
    }

    // Creates and returns LinkedList specific iterator
    public Iterator getIterator() {
        return new LinkedListIterator(head);
    }
}

/*
---------------------------------------
    LinkedList Iterator
---------------------------------------
*/

/*
 Concrete iterator for LinkedList.

 Uses pointer traversal:
 head -> node1 -> node2 -> node3 -> null

 The iterator moves the pointer forward
 every time next() is called.
*/
class LinkedListIterator implements Iterator {

    private Node current;

    // Iterator starts from the head of the list
    public LinkedListIterator(Node head) {
        this.current = head;
    }

    // Returns true if more nodes exist
    public boolean hasNext() {
        return current != null;
    }

    // Returns current node value and moves pointer forward
    public Object next() {
        String value = current.data;
        current = current.next;
        return value;
    }
}

/*
---------------------------------------
    Vector Iterator
---------------------------------------
*/

/*
 Concrete iterator for Vector.

 Uses index-based traversal since Vector
 internally stores elements in an array.
*/
class VectorIterator implements Iterator {

    private Vector vector;
    private int index = 0;

    public VectorIterator(Vector vector) {
        this.vector = vector;
    }

    // Checks if more elements exist
    public boolean hasNext() {
        return index < vector.size();
    }

    // Returns next element and increments index
    public Object next() {
        return vector.get(index++);
    }
}

/*
---------------------------------------
    Vector Collection
---------------------------------------
*/

/*
 Concrete collection implemented using ArrayList.

 Responsibilities:
 - Store elements
 - Provide an iterator for traversal
*/
class Vector implements IterableCollection {

    private ArrayList<String> data = new ArrayList<>();

    // Adds element to vector
    public void add(String value) {
        data.add(value);
    }

    // Returns element at given index
    public String get(int index) {
        return data.get(index);
    }

    // Returns number of elements
    public int size() {
        return data.size();
    }

    // Creates and returns Vector specific iterator
    public Iterator getIterator() {
        return new VectorIterator(this);
    }
}

/*
---------------------------------------
    Client Code
---------------------------------------
*/

public class Main {

    public static void main(String[] args) {

        /*
         Client works with Iterator interface
         and does not care about the internal
         structure of LinkedList or Vector.
        */

        LinkedList linkedList = new LinkedList();
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("C");

        Iterator listIterator = linkedList.getIterator();

        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }

        System.out.println();

        Vector vector = new Vector();
        vector.add("X");
        vector.add("Y");
        vector.add("Z");

        Iterator vectorIterator = vector.getIterator();

        while (vectorIterator.hasNext()) {
            System.out.println(vectorIterator.next());
        }
    }
}
