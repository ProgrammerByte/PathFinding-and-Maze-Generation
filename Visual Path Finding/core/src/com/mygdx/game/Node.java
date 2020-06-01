package com.mygdx.game;

public class Node { //Used for generating a maze
	protected boolean connected;
	//protected boolean[] connections; //TODO Might be obsolete
	//protected boolean[] adjacent; //right, up, down, and left respectively (marks unconnected adjacent vertices) TODO - MIGHT BE OBSOLETE
	//protected int[] indexes; //Indexes of arcs stored in the arcs array
	protected int xPos, yPos;
	
	//protected int[][] tempStorage; //temporarily stores the arcs array as it cannot be returned
	
	//TODO - "indexes" correlates with right, up, down, and left respectively
	
	public Node(int xPos, int yPos) {
		//this.connections = new boolean[] {false, false}; //right, down - each will either be 0 or 1
		this.connected = false;
		
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	//TODO - CHANGE changeConnected method to return an array of all indexes of items to remove from arcs array
	
	//TODO - The following method alters the graph accordingly for when a new vertex is added to the tree,
	//this is accomplished by determining what adjacent unconnected vertices there are for the new vertex,
	//and by removing any other arcs connecting to the new vertex from other connected vertices.
	//TODO - STORE THE CHOSEN ARC FOR SAID VERTEX SOMEWHERE IN AN ARRAY!!!
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
	
	
	
	
	
	//public int[][] getTempStorage() {
	//	return tempStorage;
	//}
	
	//public void setTempStorage(int[][] value) {
	//	tempStorage = value;
	//}
	
	//public boolean[] getAdjacent() {
	//	return adjacent;
	//}
	
	//public void setAdjacent(boolean[] value) {
	//	adjacent = value;
	//}
	
	//public int[] getIndexes() {
	//	return indexes;
	//}
	
	//public void setIndexes(int[] value) {
	//	indexes = value.clone();
	//}
	
	//public boolean[] getConnections() {
	//	return connections;
	//}
	
	//public void setConnections(boolean[] value) {
	//	connections = value;
	//}
	
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
