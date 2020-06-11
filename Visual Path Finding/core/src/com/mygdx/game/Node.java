package com.mygdx.game;

public class Node { //Used for generating a maze
	protected boolean connected;
	protected int xPos, yPos;
	
	public Node(int xPos, int yPos) {
		this.connected = false;
		
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public int[][] changeConnected(Node[][] nodes, int[][] arcs) { //Adding node to tree
		this.setConnected(true);
		int[][] combinations = new int[][] {{1, 0}, {0, 1}, {0, -1}, {-1, 0}};
		int x = this.getXPos();
		int y = this.getYPos();
		
		//The following block of code determines adjacent unconnected nodes
		int newX, newY; //tempX, tempY;
		for (int i = 0; i < 4; i++) {
			newX = x + combinations[i][0];
			newY = y + combinations[i][1];
			if (newX >= 0 && newX < nodes.length && newY >= 0 && newY < nodes[0].length) {
				if (nodes[newX][newY].getConnected() == false) {
					arcs = Library.addValue(arcs, new int[] {x, y, combinations[i][0], combinations[i][1]}.clone());
				}
			}
		}
		
		return arcs;
	}
	
	public boolean getConnected() {
		return connected;
	}
	
	public void setConnected(boolean value) {
		connected = value;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
}
