package org.example;

import java.util.ArrayList;
import java.util.List;

/*
 Virtual Proxy Pattern:
 - Provides a placeholder for a real object
 - Delays creation of the real (expensive) object until it is actually needed
 - Improves performance by avoiding unnecessary object creation
 - Client remains unaware whether it is using proxy or real object
*/

/*
 Subject Interface
 Both Real Object and Proxy implement this.
 Client talks only to this interface.
 */
interface SortIntegers {
    void sort(List<Integer> numbers);
}

/*
 Real Object (Heavy Object)

 Imagine this class:
 - Takes time to initialize
 - Uses memory
 - Has expensive setup (DB connection, file loading, etc.)

 We don't want to create it unless absolutely necessary.
 */
class InsertionSort implements SortIntegers {

    public InsertionSort() {
        // Simulate expensive object creation
        System.out.println("InsertionSort object created (Expensive initialization)");
    }

    /*
     Heavy sorting logic
     Time Complexity: O(N^2)
     */
    @Override
    public void sort(List<Integer> numbers) {

        System.out.println("Performing insertion sort...");

        int n = numbers.size();

        for (int i = 1; i < n; i++) {

            int key = numbers.get(i);
            int j = i - 1;

            // Shift elements greater than key one position ahead
            while (j >= 0 && numbers.get(j) > key) {
                numbers.set(j + 1, numbers.get(j));
                j--;
            }

            // Insert key at correct position
            numbers.set(j + 1, key);
        }
    }
}

/*
 Proxy Class (Virtual Proxy)

 Responsibilities:
 1. Controls access to real object
 2. Creates real object only when needed (lazy initialization)

 This saves:
 - Memory
 - Startup time
 - CPU resources
 */
class InsertionSortProxy implements SortIntegers {

    // Reference to real object
    private InsertionSort insertionSort;

    @Override
    public void sort(List<Integer> numbers) {

        /*
         Lazy Initialization happens here

         Real object is created ONLY when sort() is called.

         If sort() is never called, real object is never created.
         */
        if (insertionSort == null) {
            System.out.println("Proxy creating real object now...");
            insertionSort = new InsertionSort();
        }

        // Delegate work to real object
        insertionSort.sort(numbers);
    }
}

/*
 Client

 Client DOES NOT know:
 - Whether it's using proxy or real object
 - How object creation is managed

 Client only knows SortIntegers interface
 */
public class Main {

    public static void main(String[] args) {

        // Example data (imagine millions of numbers)
        List<Integer> numbers = new ArrayList<>();

        numbers.add(10);
        numbers.add(3);
        numbers.add(7);
        numbers.add(1);
        numbers.add(9);

        /*
         Client uses proxy instead of real object

         Important:
         Real object NOT created here
         */
        SortIntegers sorter = new InsertionSortProxy();

        System.out.println("Proxy created. Real object not created yet.\n");

        /*
         Real object will be created ONLY NOW
         */
        sorter.sort(numbers);

        System.out.println("\nSorted numbers: " + numbers);
    }
}