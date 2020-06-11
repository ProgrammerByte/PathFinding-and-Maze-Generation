package com.mygdx.game;

public class AStar extends Dijkstra {
	protected int[] distancesGoal; //distance to the goal, calculated for every tile before the algorithm cycle can begin
	protected int[] distancesStart; //For priority queue (same length as the visiting list) (from start)
	protected int[] goalPos;
	
	public AStar(Tile[][] tiles, int[] playerPos, int[] goalPos) {
		super(tiles, playerPos);
		this.distancesStart = new int[] {0}; //distance from start (this represents starting at the start tile)
		this.goalPos = goalPos.clone();
		
		this.distancesGoal = new int[] {calculateDistance(playerPos[0], playerPos[1])};
	}
	
	public boolean cycle(Tile[][] tiles, float[][] colours) {
		
		int[][] visiting = this.getVisiting();
		int[] distancesStart = this.getDistancesStart();
		int[] distancesGoal = this.getDistancesGoal();
		int length = visiting.length;
		
		if (length != 0) {
			boolean finished = false;
			
			int minimum = 100000000, currentVal; //Priority queue implementation
			int index = -1;
			for (int i = 0; i < length; i++) { //for loop selects minimum distance value
				currentVal = distancesStart[i] + distancesGoal[i];
				if (currentVal <= minimum) {
					minimum = currentVal;
					index = i;
				}
			}
			
			int xPos = visiting[index][0], yPos = visiting[index][1];
			tiles[xPos][yPos].changeSpace(4, colours); //change to has been visited
			int[][] combinations = new int[][] {{xPos + 1, yPos}, {xPos - 1, yPos}, {xPos, yPos + 1}, {xPos, yPos - 1}};
			
			
			int type, ind1, ind2, nextDistanceStart = distancesStart[index] + 1, xCount = this.getXCount(), yCount = this.getYCount();
			for (int n = 0; n < 4; n++) {
				ind1 = combinations[n][0];
				ind2 = combinations[n][1];
				if (ind1 >= 0 && ind1 < xCount && ind2 >= 0 && ind2 < yCount) {
					type = tiles[ind1][ind2].getType();
					if (type == 3) {
						//isRunning = false;
						this.setHasWon(true);
						//tiles[ind1][ind2].setShortestPath(addValue(tiles[x][y].getShortestPath(), visiting[i]));
						winningPath = new int[][] {{xPos, yPos}};
						
						int[] nextNode = tiles[xPos][yPos].getPreviousNode();
						while (nextNode.length != 0) {
							winningPath = Library.addValue(winningPath, nextNode);
							nextNode = tiles[nextNode[0]][nextNode[1]].getPreviousNode();
						}
						winningPath = Library.removeValue(winningPath, winningPath.length - 1);
						
						this.setWinningPath(winningPath);
						
						finished = true;
					}
					else if (type == 0) {
						tiles[ind1][ind2].changeSpace(5, colours);
						tiles[ind1][ind2].setPreviousNode(visiting[index]);
						visiting = Library.addValue(visiting, combinations[n]);
						distancesStart = Library.addInt(distancesStart, nextDistanceStart);
						distancesGoal = Library.addInt(distancesGoal, calculateDistance(ind1, ind2));
						//newVisits = Library.addValue(newVisits, combinations[n]);
					}
				}
			}
			visiting = Library.removeValue(visiting, index);
			distancesStart = Library.removeInt(distancesStart, index);
			distancesGoal = Library.removeInt(distancesGoal, index);
			this.setVisiting(visiting);
			this.setDistancesStart(distancesStart);
			this.setDistancesGoal(distancesGoal);
			
			return finished;
		}
		else {
			return true;
		}
	}
	
	
	
	
	
	
	
	
	
	
	public int calculateDistance(int x, int y) { //from goal
		int[] goalPos = this.getGoalPos();
		int xChange = goalPos[0] - x;
		int yChange = goalPos[1] - y;
		///return Math.sqrt((xChange * xChange) + (yChange * yChange));
		
		if (xChange < 0) {
			xChange = -xChange;
		}
		if (yChange < 0) {
			yChange = -yChange;
		}
		return xChange + yChange; //defined as the minimum number of arcs needed to reach the destination.
	}
	
	public int[] getGoalPos() {
		return goalPos;
	}
	
	public int[] getDistancesStart() {
		return distancesStart.clone();
	}
	
	public void setDistancesStart(int[] value) {
		distancesStart = value.clone();
	}
	
	public int[] getDistancesGoal() {
		return distancesGoal.clone();
	}
	
	public void setDistancesGoal(int[] value) {
		distancesGoal = value.clone();
	}
}
