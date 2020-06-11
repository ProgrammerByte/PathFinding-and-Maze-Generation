package com.mygdx.game;

public class TreeTrimming extends Algorithm{
	protected int[][] vertexOrders; //Stores all vertex orders of the tree
	protected int[] currentNode; //Used for displaying solution
	protected int xCount, yCount; //Size of vertexOrders array
	
	public TreeTrimming(Tile[][] tiles) { //Sets everything up
		super();
		this.xCount = tiles.length;
		this.yCount = tiles[0].length;
		int adjacent, xVal, yVal;
		
		int[][] combinations;
		this.vertexOrders = new int[xCount][yCount];
		for (int i = 0; i < tiles.length; i++) {
			for (int x = 0; x < tiles[0].length; x++) {
				adjacent = -1;
				if (tiles[i][x].getType() == 0) {
					combinations = new int[][] {{i + 1, x}, {i - 1, x}, {i, x + 1}, {i, x - 1}};
					adjacent = 0;
					for (int a = 0; a < 4; a++) {
						xVal = combinations[a][0];
						yVal = combinations[a][1];
						
						if (xVal >= 0 && xVal < xCount && yVal >= 0 && yVal < yCount) {
							if (tiles[xVal][yVal].getType() != 1) {
								adjacent += 1;
							}
						}
					}
				}
				else if (tiles[i][x].getType() == 2) {
					this.setCurrentNode(new int[] {i, x});
				}
				this.vertexOrders[i][x] = adjacent; //-1 if wall
			}
		}
	}
	
	public boolean cycle(Tile[][] tiles, float[][] colours) { //Every frame
		int[][] vertexOrders = this.getVertexOrders();
		int xCount = this.getXCount();
		int yCount = this.getYCount();
		int xVal, yVal;
		boolean finished = true;
		
		int[][] tempVertexOrders = new int[xCount][yCount]; //Hard clone so that the algorithm works one step at a time
		for (int i = 0; i < xCount; i++) {
			tempVertexOrders[i] = vertexOrders[i].clone();
		}
		
		int[][] combinations;
		for (int i = 0; i < xCount; i++) {
			for (int x = 0; x < yCount; x++) {
				if (tempVertexOrders[i][x] == 1) {
					finished = false;
					combinations = new int[][] {{i + 1, x}, {i - 1, x}, {i, x + 1}, {i, x - 1}};
					vertexOrders[i][x] = -1;
					for (int a = 0; a < 4; a++) {
						xVal = combinations[a][0];
						yVal = combinations[a][1];
						
						if (xVal >= 0 && xVal < xCount && yVal >= 0 && yVal < yCount) {
							vertexOrders[combinations[a][0]][combinations[a][1]] -= 1;
						}
					}
					tiles[i][x].changeType(5, colours);
				}
				else if (tiles[i][x].getType() == 5) {
					tiles[i][x].changeType(4, colours);
				}
			}
		}
		this.setHasWon(finished);
		return finished;
	}
	
	public boolean displaySolution(Tile[][] tiles, float[][] colours) {
		int[] currentNode = this.getCurrentNode();
		int x = currentNode[0];
		int y = currentNode[1];
		int xTemp, yTemp;
		int xCount = this.getXCount();
		int yCount = this.getYCount();
		boolean finished = true;
		
		int[][] combinations = new int[][] {{x + 1, y}, {x - 1, y}, {x, y + 1}, {x, y - 1}};
		for (int a = 0; a < 4; a++) {
			xTemp = combinations[a][0];
			yTemp = combinations[a][1];
			
			if (xTemp >= 0 && xTemp < xCount && yTemp >= 0 && yTemp < yCount) {
				if (tiles[xTemp][yTemp].getType() == 0) {
					tiles[xTemp][yTemp].changeType(6, colours);
					currentNode[0] = xTemp;
					currentNode[1] = yTemp;
					this.setCurrentNode(currentNode);
					finished = false;
					break;
				}
			}
		}
		return finished;
	}
	
	
	
	
	public int[][] getVertexOrders() {
		return vertexOrders.clone();
	}
	
	public void setVertexOrders(int[][] value) {
		vertexOrders = value;
	}
	
	public int[] getCurrentNode() {
		return currentNode.clone();
	}
	
	public void setCurrentNode(int[] value) {
		currentNode = value;
	}
	
	public int getXCount() {
		return xCount;
	}
	
	public int getYCount() {
		return yCount;
	}
}
