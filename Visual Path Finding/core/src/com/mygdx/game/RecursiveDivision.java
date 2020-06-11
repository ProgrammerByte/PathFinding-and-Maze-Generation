package com.mygdx.game;

import java.util.Random;

//TODO - WORKS FOR BOTH HORIZONTAL AND VERTICAL BUT NOT BOTH!!!


public class RecursiveDivision extends Algorithm { //TODO - THIS!!!
	protected int[][] grids; //in the form {{xCoord, yCoord, width, height}} where coord represents bottom left corner and width and height represent number of wall spaces to the right and above (but including) the bottom left corner respectively
	protected int type; // 0 = normal, 1 = vertical only, -1 = horizontal only
	
	public RecursiveDivision(Tile[][] tiles, float[][] colours, int type) {
		super();
		int xCount = tiles.length;
		int yCount = tiles[0].length;
		for (int i = 0; i < xCount; i++) { //clears grid
			for (int x = 0; x < yCount; x++) {
				tiles[i][x].changeSpace(0, colours);
			}
		}
		//this.grids = new int[][] {{0, 0, this.xWidth - 1, this.yWidth - 1}};
		//COORDINATE SYSTEM IS IN TERMS OF WALLS, THEREFORE IN ORDER TO CONVERT IT INTO GRID COORDINATES MULTIPLY BY 2 AND ADD 1!!!
		
		this.grids = new int[][] {{0, 0, xCount, yCount}}; //of the form bottom left corner, width, height (inclusive)
		
		this.type = type;
	}
	
	public boolean cycle(Tile[][] tiles, float[][] colours) {
		int[][] grids = this.getGrids();
		int length = grids.length;
		
		if (length != 0) {
			
			Random rand = new Random();
			double choice;
			int wallPos, holePos, type = this.getType();
			for (int i = 0; i < length; i++) {
				
				if (grids[0][2] > 0 && grids[0][3] > 0) { //TODO - THIS IF STATEMENT SHOULD NOT BE NECESSARY!!!
					if (type == 0) {
						choice = rand.nextDouble();
					}
					else {
						choice = type;
					}
					//choice = 0.6; //for testing
					
					if (choice < 0.5 && grids[0][3] > 1) {
						//horizontal line
						wallPos = (2 * rand.nextInt(grids[0][3] / 2)) + 1 + grids[0][1]; //TODO - IF CODE DOESN'T WORK THEN THINK ABOUT THIS A BIT MORE CAREFULLY
						holePos = (2 * rand.nextInt((grids[0][2] + 1) / 2)) + grids[0][0];
						
						for (int x = grids[0][0]; x < grids[0][0] + grids[0][2]; x++) {
							if (x != holePos) {
								tiles[x][wallPos].changeSpace(1, colours); //TODO - ADD MAZE CHECKING
							}
						}
						//TODO - ABOVE WIDTH AND RIGHT HEIGHT IS PROBABLY INCORRECT (idk)
						grids = Library.addValue(grids, new int[] {grids[0][0], grids[0][1], grids[0][2], wallPos - grids[0][1]}.clone()).clone(); //underneath grid
						grids = Library.addValue(grids, new int[] {grids[0][0], wallPos + 1, grids[0][2], (grids[0][3] - (wallPos - grids[0][1])) - 1}.clone()).clone(); //above grid
					}
					else if (grids[0][2] > 1) {
						//vertical line
						wallPos = (2 * rand.nextInt(grids[0][2] / 2)) + 1 + grids[0][0]; //TODO - IF CODE DOESN'T WORK THEN THINK ABOUT THIS A BIT MORE CAREFULLY
						holePos = (2 * rand.nextInt((grids[0][3] + 1) / 2)) + grids[0][1];
						
						for (int x = grids[0][1]; x < grids[0][1] + grids[0][3]; x++) {
							if (x != holePos) {
								tiles[wallPos][x].changeSpace(1, colours); //TODO - ADD MAZE CHECKING
							}
						}
						
						grids = Library.addValue(grids, new int[] {grids[0][0], grids[0][1], wallPos - grids[0][0], grids[0][3]}.clone()).clone(); //left grid
						grids = Library.addValue(grids, new int[] {wallPos + 1, grids[0][1], (grids[0][2] - (wallPos - grids[0][0])) - 1, grids[0][3]}.clone()).clone(); //right grid
					}
					
					
				}
				grids = Library.removeValue(grids, 0);
			}
			
			
			this.setGrids(grids);
			return false;
		}
		else {
			return true;
		}
	}
	
	
	
	
	
	public int[][] getGrids() {
		return grids.clone();
	}
	
	public void setGrids(int[][] value) {
		grids = value.clone();
	}
	
	public int getType() {
		return type;
	}
}
