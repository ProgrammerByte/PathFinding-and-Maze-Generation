package com.mygdx.game;

public class Depth extends Algorithm { //Depth-first graph traversal algorithm
	protected int[][] stack; //Stores all previously visited tiles
	protected int[] lastNode;
	protected int currentVal;
	
	public Depth(int[] playerPos) {
		super();
		this.stack = new int[][] {{playerPos[0], playerPos[1]}};
		this.lastNode = new int[0];
		this.hasWon = false;
		this.currentVal = 1;
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
			int[] pos;
			int xVal, yVal;
			boolean available = false;
			pos = stack[length - 1].clone();
			//tiles[pos[0]][pos[1]].changeSpace(4, colours);
			
			for (int i = 0; i < 4; i++) {
				xVal = pos[0] + combinations[i][0];
				yVal = pos[1] + combinations[i][1];
				
				if (xVal >= 0 && xVal < tiles.length && yVal >= 0 && yVal < tiles[0].length) {
					if (tiles[xVal][yVal].getType() == 3) {
						this.setHasWon(true);
						return true;
					}
					else if (tiles[xVal][yVal].getType() == 0) {
						available = true;
						tiles[xVal][yVal].changeSpace(5, colours);
						this.setLastNode(new int[] {xVal, yVal});
						stack = Library.addValue(stack, new int[] {xVal, yVal});
						break;
					}
				}
			}
			if (available == false) {
				this.setLastNode(stack[stack.length - 1]);
				tiles[stack[length - 1][0]][stack[length - 1][1]].changeSpace(5, colours);
				stack = Library.removeValue(stack, stack.length - 1);
			}
			
			this.setStack(stack);
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean displaySolution(Tile[][] tiles, float[][] colours) {
		int[][] stack = this.getStack();
		int index = this.getCurrentVal();
		if (index < stack.length) {
			tiles[stack[index][0]][stack[index][1]].changeType(6, colours);
			this.setCurrentVal(index + 1);
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
	
	public int[] getLastNode() {
		return lastNode.clone();
	}
	
	public void setLastNode(int[] value) {
		lastNode = value.clone();
	}
	
	public int getCurrentVal() {
		return currentVal;
	}
	
	public void setCurrentVal(int value) {
		currentVal = value;
	}
}
