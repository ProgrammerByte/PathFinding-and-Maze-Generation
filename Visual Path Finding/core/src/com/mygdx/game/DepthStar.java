package com.mygdx.game;

public class DepthStar extends Depth { //Incorporates the same theory behind the A star algorithm however applied to depth first traversal
	int[] goalPos;

	public DepthStar(int[] playerPos, int[] goalPos) {
		super(playerPos);
		this.goalPos = goalPos.clone();
	}
	
	public boolean cycle(Tile[][] tiles, float[][] colours) {
		int[][] stack = this.getStack();
		int[] lastNode = this.getLastNode();
		int length = stack.length;
		if (lastNode.length != 0) {
			tiles[lastNode[0]][lastNode[1]].changeSpace(4, colours);
		}
		
		if (length != 0) {
			int[][] combinations = new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
			//int[][] possibleSpaces = new int[0][2]; //currentPossible next spaces for the algorithm
			int[] currentPos, nextPos = new int[0]; //-1 indicates null
			int xVal, yVal, minVal = 1000000, currentVal;
			currentPos = stack[length - 1].clone();
			
			for (int i = 0; i < 4; i++) {
				xVal = currentPos[0] + combinations[i][0];
				yVal = currentPos[1] + combinations[i][1];
				
				if (xVal >= 0 && xVal < tiles.length && yVal >= 0 && yVal < tiles[0].length) {
					if (tiles[xVal][yVal].getType() == 3) {
						this.setHasWon(true);
						return true;
					}
					else if (tiles[xVal][yVal].getType() == 0) {
						currentVal = calculateDistance(xVal, yVal);
						if (currentVal <= minVal) {
							minVal = currentVal;
							nextPos = new int[] {xVal, yVal};
						}
					}
				}
			}
			
			if (nextPos.length == 0) { //moving backwards
				this.setLastNode(stack[stack.length - 1]);
				tiles[stack[length - 1][0]][stack[length - 1][1]].changeSpace(5, colours);
				stack = Library.removeValue(stack, stack.length - 1);
			}
			else {
				tiles[nextPos[0]][nextPos[1]].changeSpace(5, colours);
				this.setLastNode(nextPos);
				stack = Library.addValue(stack, nextPos);
			}
			
			this.setStack(stack);
			return false;
		}
		else {
			return true;
		}
	}
	
	public int calculateDistance(int x, int y) { //from goal
		int[] goalPos = this.getGoalPos();
		int xChange = goalPos[0] - x;
		int yChange = goalPos[1] - y;
		
		if (xChange < 0) {
			xChange = -xChange;
		}
		if (yChange < 0) {
			yChange = -yChange;
		}
		return xChange + yChange; //defined as the minimum number of arcs needed to reach the destination.
	}
	
	
	public int[] getGoalPos() {
		return goalPos.clone();
	}
}
