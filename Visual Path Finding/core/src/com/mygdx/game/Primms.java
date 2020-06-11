package com.mygdx.game;

import java.util.Random;

public class Primms extends Algorithm { //TODO - MOVE MAZE GENERATING ALGORITHM HERE AND ALLOW IT TO BECOME A VISUAL THING
	protected int[][] arcs;
	protected int xWidth, yWidth, total, count;
	protected Node[][] nodes;
	
	public Primms(Tile[][] tiles, float[][] colours) {
		super();
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
					tiles[i][x].changeSpace(1, colours);
				}
			}
		}
		
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
	
	
	
	
	public boolean cycle(Tile[][] tiles, float[][] colours) { //Has it finished?
		
		Random rand = new Random();
		
		Node[][] nodes = this.getNodes();
		
		int total = this.getTotal();
		int count = this.getCount();
		
		int[][] arcs = this.getArcs();
		
		int index, xPos, yPos, xDir, yDir, xVal, yVal, skipped, length;
		if (count < total) { //TODO MODULI IS PROBABLY UNNECESSARY
			length = arcs.length;
			index = (int)(length * rand.nextDouble()) % length;
			xPos = arcs[index][0];
			yPos = arcs[index][1];
			xDir = arcs[index][2];
			yDir = arcs[index][3];
			
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
			return false;
		}
		else {
			
			return true;
		}
		
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
