package com.mygdx.game;

import java.util.Random;

public class RecursiveBacktracker extends Algorithm {
	//protected int xWidth, yWidth, total, count;
	protected int[][] stack;
	protected boolean[][] visited; //which nodes have already been visited?
	
	public RecursiveBacktracker(Tile[][] tiles, float[][] colours) {
		super();
		
		int xCount = tiles.length;
		int yCount = tiles[0].length;
		for (int i = 0; i < xCount; i++) { //same set up as Primm's algorithm
			for (int x = 0; x < yCount; x++) {
				if (i % 2 == 0 && x % 2 == 0) {
					tiles[i][x].changeSpace(0, colours);
				}
				else {
					tiles[i][x].changeSpace(1, colours);
				}
			}
		}
		
		int xWidth = (xCount + 1) / 2;
		int yWidth = (yCount + 1) / 2;
		boolean[][] visited = new boolean[xWidth][yWidth]; //the amount of nodes in the maze
		for (int i = 0; i < xWidth; i++) {
			for (int x = 0; x < yWidth; x++) {
				visited[i][x] = false;
			}
		}
		
		//this.stack = new int[][] {playerPos.clone()};
		Random rand = new Random(); //starts in a random place
		int xVal = rand.nextInt(xWidth) * 2;
		int yVal = rand.nextInt(yWidth) * 2;
		
		this.stack = new int[][] {{xVal, yVal}}; //temporary
		visited[xVal / 2][yVal / 2] = true;
		this.visited = visited;
	}
	
	public boolean cycle(Tile[][] tiles, float[][] colours) {
		int[][] directions = new int[][] {{2, 0}, {-2, 0}, {0, 2}, {0, -2}}; //2 instead of 1 due to the shape of the grid
		int[][] feasibleDirections = new int[0][2]; //which directions could be travelled in?
		int[][] stack = this.getStack();
		boolean[][] visited = this.getVisited();
		
		int xVal, yVal, index = stack.length - 1;
		if (index != -1) {
			tiles[stack[index][0]][stack[index][1]].changeSpace(0, colours);
			for (int i = 0; i < 4; i++) {
				xVal = stack[index][0] + directions[i][0];
				yVal = stack[index][1] + directions[i][1];
				if (xVal >= 0 && xVal < tiles.length && yVal >= 0 && yVal < tiles[0].length) {
					if (visited[xVal / 2][yVal / 2] == false) {
						feasibleDirections = Library.addValue(feasibleDirections, new int[] {directions[i][0], directions[i][1]});
					}
				}
			}
			
			int length = feasibleDirections.length;
			if (length != 0) {
				Random rand = new Random();
				int chosenIndex = rand.nextInt(length);
				xVal = stack[index][0] + feasibleDirections[chosenIndex][0];
				yVal = stack[index][1] + feasibleDirections[chosenIndex][1];
				stack = Library.addValue(stack, new int[] {xVal, yVal}); //where the algorithm currently is
				
				tiles[xVal - (feasibleDirections[chosenIndex][0] / 2)][yVal - (feasibleDirections[chosenIndex][1] / 2)].changeSpace(0, colours); //coordinates of wall to remove
				//visited[feasibleDirections[chosenIndex][0]][feasibleDirections[index][1]] = true;
				visited[xVal / 2][yVal / 2] = true;
				this.setVisited(visited);
				
			}
			else {
				stack = Library.removeValue(stack, index);
			}
			this.setStack(stack);
			
			index = stack.length - 1;
			if (index != -1) {
				tiles[stack[index][0]][stack[index][1]].changeSpace(5, colours); //shows where the algorithm currently is
			}
			return false;
		}
		else {
			return true;
		}
	}
	
	
	
	
	
	
	
	
	public int[][] getStack() {
		return stack.clone();
	}
	
	public void setStack(int[][] value) {
		stack = value.clone();
	}
	
	public boolean[][] getVisited() {
		return visited.clone();
	}
	
	public void setVisited(boolean[][] value) {
		visited = value.clone();
	}
}
