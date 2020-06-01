package com.mygdx.game;

public class Library { //Where commonly used functions across the entire program are held
	public static int[][] addValue(int[][] list, int[] values) { //adds a new node to the visiting list
		int[][] result = new int[list.length + 1][2];
		for (int i = 0; i < list.length; i++) {
			result[i] = list[i].clone();
		}
		result[list.length] = values.clone();
		return result;
	}
	
	public static int[][] removeValue(int[][] list, int index) { //Removes a value from a list given the list and index of item to remove
		//The following removes the desired value
		int[][] result = new int[list.length - 1][];
		for (int i = 0; i < list.length; i++) {
			if (i > index) {
				result[i - 1] = list[i].clone();
			}
			else if (i != index) {
				result[i] = list[i].clone();
			}
		}
		return result;
	}
	
	//TODO - THE FOLLOWING MAY NOT BE USED FOR ANYTHING!!!
	public static int[][] combineArrays(int[][] list1, int[][] list2) { //Combines 2 arrays and returns a single array
		int length1 = list1.length, length2 = list2.length;
		int[][] result = new int[length1 + length2][];
		for (int i = 0; i < length1; i++) {
			result[i] = list1[i].clone();
		}
		for (int i = 0; i < length2; i++) {
			result[i + length1] = list2[i].clone();
		}
		return result;
	}
}
