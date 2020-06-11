package com.mygdx.game;

//TODO - ADD A SHOW SOLUTION METHOD!!! (AND GET THIS WORKING)
public class Dijkstra extends Algorithm {
	protected int[][] visiting, winningPath;
	protected int solutionIndex, xCount, yCount;
	
	public Dijkstra(Tile[][] tiles, int[] playerPos) {
		super();
		this.solutionIndex = 0;
		this.xCount = tiles.length;
		this.yCount = tiles[0].length;
		this.visiting = new int[][] {{playerPos[0], playerPos[1]}};
	}
	
	public boolean cycle(Tile[][] tiles, float[][] colours) {
		int[][] visiting = this.getVisiting();
		//boolean hasWon = false;
		if (visiting.length != 0) {
			boolean finished = false;
			int[][] winningPath;
			int xCount = this.getXCount();
			int yCount = this.getYCount();
			
			int type;
			int[][] newVisits = new int[0][2];
			int[][] combinations;
			//int[][] tempShortest;
			int x, y;
			int ind1, ind2;
			for (int i = 0; i < visiting.length; i++) { //combinations = x+1, x-1, y+1, y-1
				x = visiting[i][0];
				y = visiting[i][1];
				if (tiles[x][y].getType() != 2) {
					tiles[x][y].changeType(4, colours);
				}
				combinations = new int[][] {{x + 1, y}, {x - 1, y}, {x, y + 1}, {x, y - 1}}; //possible cells
				
				for (int n = 0; n < 4; n++) {
					ind1 = combinations[n][0];
					ind2 = combinations[n][1];
					if (ind1 >= 0 && ind1 < xCount && ind2 >= 0 && ind2 < yCount) {
						type = tiles[ind1][ind2].getType();
						if (type == 3) {
							//isRunning = false;
							this.setHasWon(true);
							//tiles[ind1][ind2].setShortestPath(addValue(tiles[x][y].getShortestPath(), visiting[i]));
							winningPath = new int[][] {{x, y}};
							
							int[] nextNode = tiles[x][y].getPreviousNode();
							while (nextNode.length != 0) {
								winningPath = Library.addValue(winningPath, nextNode);
								nextNode = tiles[nextNode[0]][nextNode[1]].getPreviousNode();
							}
							winningPath = Library.removeValue(winningPath, winningPath.length - 1);
							
							this.setWinningPath(winningPath);
							finished = true;
						}
						else if (type == 0) {
							tiles[ind1][ind2].changeType(5, colours);
							tiles[ind1][ind2].setPreviousNode(visiting[i]);
							newVisits = Library.addValue(newVisits, combinations[n]);
						}
					}
				}
			}
			this.setVisiting(newVisits);
			return finished;
		}
		else {
			return true;
		}
	}
	
	public boolean displaySolution(Tile[][] tiles, float[][] colours) {
		int solutionIndex = this.getSolutionIndex();
		int[][] winningPath = this.getWinningPath();
		if (solutionIndex < winningPath.length) {
			tiles[winningPath[(winningPath.length - 1) - solutionIndex][0]][winningPath[(winningPath.length - 1) - solutionIndex][1]].changeType(6, colours);
			this.setSolutionIndex(solutionIndex + 1);
			return false;
		}
		else {
			return true;
		}
	}
	
	
	public int getSolutionIndex() {
		return solutionIndex;
	}
	
	public void setSolutionIndex(int value) {
		solutionIndex = value;
	}
	
	public int[][] getVisiting() {
		return visiting.clone();
	}
	
	public void setVisiting(int[][] value) {
		visiting = value.clone();
	}
	
	public int[][] getWinningPath() {
		return winningPath;
	}
	
	public void setWinningPath(int[][] value) {
		winningPath = value;
	}
	
	public int getXCount() {
		return xCount;
	}
	
	public int getYCount() {
		return yCount;
	}
}
