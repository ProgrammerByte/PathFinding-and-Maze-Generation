package com.mygdx.game;

import java.util.Random;

public class Primms { //TODO - MOVE MAZE GENERATING ALGORITHM HERE AND ALLOW IT TO BECOME A VISUAL THING
	protected int[][] arcs;
	protected int xWidth, yWidth, total, count;
	protected boolean isMaze, blocked;
	protected Node[][] nodes;
	
	public Primms(Tile[][] tiles, float[][] colours) {
		this.isMaze = false;
		boolean blocked = false;
		this.count = 1;
		
		int xCount = tiles.length;
		this.xWidth = (xCount + 1) / 2;
		
		int yCount = tiles[0].length;
		this.yWidth = (yCount + 1) / 2;
		
		this.total = xWidth * yWidth;
		
		for (int i = 0; i < xCount; i++) { //Fills in everything with walls - except from nodes
			for (int x = 0; x < yCount; x++) {
				if (i % 2 == 0 && x % 2 == 0) {
					tiles[i][x].changeSpace(0, colours);
				}
				else {
					if (tiles[i][x].changeSpace(1, colours) == false) {
						blocked = true;
					}
				}
			}
		}
		this.blocked = blocked;
		
		Node[][] nodes = new Node[xWidth][yWidth];
		for (int i = 0; i < xWidth; i++) {
			for (int x = 0; x < yWidth; x++) {
				nodes[i][x] = new Node(i, x);
			}
		}
		this.nodes = nodes;
		
		Random rand = new Random();
		int xVal = (int)(rand.nextDouble() * xWidth) % xWidth;
		int yVal = (int)(rand.nextDouble() * yWidth) % yWidth;
		this.arcs = nodes[xVal][yVal].changeConnected(nodes, new int[0][4]);
	}
	
	
	
	
	public boolean generateMazeNew(Tile[][] tiles, float[][] colours) { //Has it finished?
		//for (int i = 0; i < xCount; i++) { //Fills in everything with walls
		//	for (int x = 0; x < yCount; x++) {
		//		if (i % 2 == 0 && x % 2 == 0) {
		//			changeSpace(tiles, i, x, 0);
		//		}
		//		else {
		//			if (changeSpace(tiles, i, x, 1) == false) {
		//				isMaze = false;
		//			}
		//		}
		//	}
		//}
		
		Random rand = new Random();
		
		Node[][] nodes = this.getNodes();
		
		int total = this.getTotal();
		int count = this.getCount();
		
		//int[][] finalArcs = new int[0][4];
		int[][] arcs = this.getArcs();
		
		int index, xPos, yPos, xDir, yDir, xVal, yVal, skipped, length;
		if (count < total) { //TODO MODULI IS PROBABLY UNNECESSARY
			length = arcs.length;
			index = (int)(length * rand.nextDouble()) % length;
			xPos = arcs[index][0];
			yPos = arcs[index][1];
			xDir = arcs[index][2];
			yDir = arcs[index][3];
			
			//finalArcs = Library.addValue(finalArcs, arcs[index].clone());
			
			//xPos = finalArcs[i][0];
			//yPos = finalArcs[i][1];
			//xDir = finalArcs[i][2];
			//yDir = finalArcs[i][3];
			//changeSpace(tiles, (xPos * 2) + xDir, (yPos * 2) + yDir, 0, colours);
			tiles[(xPos * 2) + xDir][(yPos * 2) + yDir].changeSpace(0, colours);
			arcs = Library.removeValue(arcs, index);
			
			xVal = xPos + xDir;
			yVal = yPos + yDir;
			arcs = nodes[xVal][yVal].changeConnected(nodes, arcs);
			
			skipped = 0;
			for (int i = 0; i < length - 1; i++) {
				index = i - skipped;
				if (arcs[index][0] + arcs[index][2] == xVal && arcs[index][1] + arcs[index][3] == yVal) {
				//if (nodes[arcs[i][0] + arcs[i][2]][arcs[i][1] + arcs[i][3]].getConnected() == true) {
					arcs = Library.removeValue(arcs, index);
					skipped += 1;
				//}
				}
			}
			this.setCount(count + 1);
			this.setArcs(arcs);
			return true;
		}
		else {
			//this.setArcs(arcs);
			if (this.getBlocked() == false) {
				this.setIsMaze(true);
			}
			
			return false;
		}
		
		/*for (int i = 0; i < finalArcs.length; i++) {
			xPos = finalArcs[i][0];
			yPos = finalArcs[i][1];
			xDir = finalArcs[i][2];
			yDir = finalArcs[i][3];
			changeSpace(tiles, (xPos * 2) + xDir, (yPos * 2) + yDir, 0, colours);
		}*/
	}
	
	
	
	
	
	//TODO - MAYBE MOVE THE BELOW METHODS ELSEWHERE
	/*public int[][] Library.addValue(int[][] list, int[] values) { //adds a new node to the visiting list
		int[][] result = new int[list.length + 1][2];
		for (int i = 0; i < list.length; i++) {
			result[i] = list[i].clone();
		}
		result[list.length] = values.clone();
		return result;
	}
	
	public int[][] Library.removeValue(int[][] list, int index) { //Removes a value from a list given the list and index of item to remove
		//The following removes the desired value
		int[][] result = new int[list.length - 1][];
		for (int i = 0; i < list.length; i++) {
			if (i > index) {
				result[i - 1] = list[i].clone();
			}
			else if (i != index) {
				result[i] = list[i].clone();
			}
		}
		return result;
	}*/
	
	//public boolean changeSpace(Tile[][] tiles, int xPos, int yPos, int type, float[][] colours) { //whether successful
	//	if (tiles[xPos][yPos].getType() != 2 && tiles[xPos][yPos].getType() != 3) {
	//		tiles[xPos][yPos].changeType(type, colours);
	//		return true;
	//	}
	//	return false;
	//}
	
	
	
	
	
	public boolean getIsMaze() {
		return isMaze;
	}
	
	public void setIsMaze(boolean value) {
		isMaze = value;
	}
	
	public boolean getBlocked() {
		return blocked;
	}
	
	public int getTotal() {
		return total;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int value) {
		count = value;
	}
	
	public Node[][] getNodes() {
		return nodes;
	}
	
	public int[][] getArcs() {
		return arcs.clone();
	}
	
	public void setArcs(int[][] value) {
		arcs = value;
	}
}
