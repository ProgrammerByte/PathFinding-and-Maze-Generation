package com.mygdx.game;

import java.util.Random;

//ruleString = B4|S1234
public class Mazectric extends Algorithm { //Cellular automaton
	public Mazectric (Tile[][] tiles, float[][] colours) {
		super();
		Random rand = new Random();
		for (int i = 0; i < tiles.length; i++) { //starts in a random state
			for (int x = 0; x < tiles[0].length; x++) {
				if (rand.nextDouble() <= 0.5) {
					tiles[i][x].changeSpace(1, colours);
				}
				else {
					tiles[i][x].changeSpace(0, colours);
				}
			}
		}
	}
	
	
	public boolean cycle(Tile[][] tiles, float[][] colours) {
		int[][] toBirth = new int[0][2]; //list of coordinates
		int[][] toKill = new int[0][2];
		
		
		int count, xMax = tiles.length, yMax = tiles[0].length, xVal, yVal;
		for (int i = 0; i < xMax; i++) {
			for (int x = 0; x < yMax; x++) { //for every tile
				
				count = 0;
				for (int a = -1; a <= 1; a++) {
					for (int b = -1; b <= 1; b++) { //for every neighbour of the current tile
						xVal = i + a;
						yVal = x + b;
						if (xVal >= 0 && xVal < xMax && yVal >= 0 && yVal < yMax) {
							if (tiles[xVal][yVal].getType() == 1) {
								count += 1;
							}
						}
					}
				}
				
				if (tiles[i][x].getType() == 0) {
					if (count == 4) {
						toBirth = Library.addValue(toBirth, new int[] {i, x}.clone());
					}
				}
				else {
					if (count < 2 || count > 5) {
						toKill = Library.addValue(toKill, new int[] {i, x}.clone());
					}
				}
				
				
				
			}
		}
		
		
		
		
		
		for (int i = 0; i < toBirth.length; i++) {
			tiles[toBirth[i][0]][toBirth[i][1]].changeSpace(1, colours);
		}
		for (int i = 0; i < toKill.length; i++) {
			tiles[toKill[i][0]][toKill[i][1]].changeSpace(0, colours);
		}
		
		if (toBirth.length != 0 || toKill.length != 0) {
			return false;
		}
		else {
			return true;
		}
	}
}
