package com.cooksys.ftd.assignments.control;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * The Fibonacci sequence is simply and recursively defined: the first two elements are `1`, and
 * every other element is equal to the sum of its two preceding elements. For example:
 * <p>
 * [1, 1] =>
 * [1, 1, 1 + 1]  => [1, 1, 2] =>
 * [1, 1, 2, 1 + 2] => [1, 1, 2, 3] =>
 * [1, 1, 2, 3, 2 + 3] => [1, 1, 2, 3, 5] =>
 * ...etc
 */
public class Fibonacci {

    /**
     * Calculates the value in the Fibonacci sequence at a given index. For example,
     * `atIndex(0)` and `atIndex(1)` should return `1`, because the first two elements of the
     * sequence are both `1`.
     *
     * @param i the index of the element to calculate
     * @return the calculated element
     * @throws IllegalArgumentException if the given index is less than zero
     */
    public static int atIndex(int i) throws IllegalArgumentException {
        //Check if index is less than 0
    	if (i < 0) {
			throw new IllegalArgumentException();
		}
        
    	//Check if index is 0 or 1
        if (i == 0 || i == 1) {
			return 1;
		}
        
        //Calculate fibonacci
        int previous = 0;
        int next = 1;
        int result = 0;
        
        for (int j = 0; j < i; j++) {
            result = previous + next;
            previous = next;
            next = result;
        }
        
        return result;
        
    }

    /**
     * Calculates a slice of the fibonacci sequence, starting from a given start index (inclusive) and
     * ending at a given end index (exclusive).
     *
     * @param start the starting index of the slice (inclusive)
     * @param end   the ending index of the slice(exclusive)
     * @return the calculated slice as an array of int elements
     * @throws IllegalArgumentException if either the given start or end is negative, or if the
     *                                  given end is less than the given start
     */
    public static int[] slice(int start, int end) throws IllegalArgumentException {
        if (start < 0 || end < 0 || end < start) {
			throw new IllegalArgumentException();
		}
        //Gets size and initializes a array to work with
        int sizeOfArray = end - start;
        int[] results = null;
        
        //Double checks the array size
        if (sizeOfArray <= 0) {
			results = new int[0];
		} else {
			results = new int[sizeOfArray];
		}

        //As long as the array isn't length of zero then calculate 
        if (results.length != 0) {
        	for (int i = 0; i < results.length; i++) {
    			results[i] = atIndex(start);
    			start++;
    		}
        }
        
        
        return results;
    }

    /**
     * Calculates the beginning of the fibonacci sequence, up to a given count.
     *
     * @param count the number of elements to calculate
     * @return the beginning of the fibonacci sequence, up to the given count, as an array of int elements
     * @throws IllegalArgumentException if the given count is negative
     */
    public static int[] fibonacci(int count) throws IllegalArgumentException {
        if (count < 0) {
			throw new IllegalArgumentException();
		}
        
        int[] countsResults = null;
        //Create the array based on the count size
        if(count != 0) {
        	countsResults = new int[count];
        } else {
        	countsResults = new int[count];
        }
        
        
        //Calculate the fibonnacci based the numbers leading to the count
        
        for (int i = count; i > 0; i--) {
     		countsResults[i] = atIndex(i);
     	}
        
       
        
        return countsResults;
        
        
    }
}
