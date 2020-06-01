package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

//TODO - CHANGE NAME OF Library.addValue TO ADD VALUE
//TODO - ADD BACK LOOPING MAYBE
public class PathFinding extends ApplicationAdapter {
	ShapeRenderer sr;
	
	Primms primms;
	TreeTrimming treeTrimming;
	Dijkstra dijkstra;
	Depth depth;
	
	float[][] colours = new float[][] {{0, 0, 0}, {0, 0, 1}, {1, 0, 0}, {1, 1, 0}, {0.1f, 0.2f, 0.3f}, {0.3f, 0.5f, 1}, {0.5f, 0.2f, 1}}; 
	//Above corresponds with types: none, wall, player, objective, visited, currently visiting, winning path
	
	float[] tempColour;
	
	int screenWidth = 1920;
	int screenHeight = 1080;
	
	int xCount = 80;
	int yCount = 45;
	
	int selectedType = 1;
	
	int[] playerPos = new int[] {0, (int)(yCount / 2)};
	int[] goalPos = new int[] {xCount - 2, (int)(yCount / 2)};
	
	float cellWidth = (float)screenWidth / xCount;
	float cellHeight = (float)screenHeight / yCount;
	
	String solveType = "Dijkstra";
	
	Tile[][] tiles = new Tile[xCount][yCount];
	
	boolean grid = false, isRunning = false, hasWon = false, clearTime = false, isEditing = true, isMaze = false;
	
	//int[][] visiting = new int[0][2];
	
	int frameCount = 0, timeDelay = 1;
	
	@Override
	public void create() {
		sr = new ShapeRenderer();
		
		for (int i = 0; i < xCount; i++) {
			for (int j = 0; j < yCount; j++) {
				tiles[i][j] = new Tile(colours[0], i * cellWidth, j * cellHeight);
			}
		}
		
		tiles[playerPos[0]][playerPos[1]].changeType(2, colours);
		tiles[goalPos[0]][playerPos[1]].changeType(3, colours);
	}
	
	public void editGrid() {
		int type = -1;
		if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
			//System.out.println("AAAAAA");
			type = 0;
		}
		else if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			//System.out.println("AAAAAA");
			type = selectedType;
		}
		
		if (type != -1) {
			isMaze = false;
			float xMin, xMax, yMin, yMax;
			float mouseX = Gdx.input.getX();
			float mouseY = screenHeight - Gdx.input.getY();
			//System.out.println(mouseX + "   " + mouseY);
			for (int i = 0; i < xCount; i++) {
				for (int j = 0; j < yCount; j++) {
					xMin = tiles[i][j].getX();
					xMax = xMin + cellWidth;
					yMin = tiles[i][j].getY();
					yMax = yMin + cellHeight;
					
					if (xMin <= mouseX && xMax >= mouseX && yMin <= mouseY && yMax >= mouseY) {
						if (tiles[i][j].getType() != 2 && tiles[i][j].getType() != 3) {
							if (type == 2) {
								tiles[playerPos[0]][playerPos[1]].changeType(0, colours);
								playerPos = new int[] {i, j};
							}
							else if (type == 3) {
								tiles[goalPos[0]][goalPos[1]].changeType(0, colours);
								goalPos = new int[] {i, j};
							}
							
							tiles[i][j].changeType(type, colours);
						}
					}
				}
			}
		}
	}
	
	/*public boolean changeSpace(int xPos, int yPos, int type) { //whether successful
		if (tiles[xPos][yPos].getType() != 2 && tiles[xPos][yPos].getType() != 3) {
			tiles[xPos][yPos].changeType(type, colours);
			return true;
		}
		return false;
	}*/
	
	public void input() {
		if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			selectedType = 1;
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
			selectedType = 2;
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
			selectedType = 3;
		}
		if (Gdx.input.isKeyJustPressed(Keys.Q)) {
			grid = !grid;
		}
		//if (Gdx.input.isKeyJustPressed(Keys.L)) {
		//	isLooping = !isLooping;
		//}
		if (Gdx.input.isKeyPressed(Keys.T)) {
			if (isEditing == true) {
				solveType = "TreeTrimming";
			}
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			if (isEditing == true) {
				solveType = "Dijkstra";
			}
		}
		if (Gdx.input.isKeyPressed(Keys.F)) {
			if (isEditing == true) {
				solveType = "Depth";
			}
		}
			
			
		if (Gdx.input.isKeyPressed(Keys.M)) {
			if (isEditing == true) {
				//isMaze = true; //TODO - USE THIS VARIABLE FOR TREE TRIMMING ALGORITHM
				//generateMazeNew();
				solveType = "Primms";
			}
		}
		
		
		if (Gdx.input.isKeyJustPressed(Keys.C)) {
			if (isEditing == true) {
				for (int i = 0; i < xCount; i++) {
					for (int x = 0; x < yCount; x++) {
						tiles[i][x].changeSpace(0, colours);
					}
				}
				isMaze = false;
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			if (isEditing == true) {
			//	tiles[playerPos[0]][playerPos[1]].setType(5); //changing type but not colour to indicate visiting node
				//visiting = new int[][] {{playerPos[0], playerPos[1]}};
				isRunning = true;
				
				if (solveType.equals("Dijkstra")) {
					dijkstra = new Dijkstra(tiles, playerPos);
				}
				
				else if (solveType.equals("TreeTrimming") && isMaze == true) {
					treeTrimming = new TreeTrimming(tiles);
				}
				
				else if (solveType.equals("Depth")) {
					depth = new Depth(playerPos);
				}
				
				else if (solveType.equals("Primms")) {
					//isMaze = true;
					primms = new Primms(tiles, colours);
				}
			}
			else {
				clearTime = true;
				hasWon = false;
			}
			isEditing = !isEditing;
		}
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}
	
	/*public int[][] Library.addValue(int[][] list, int[] values) { //adds a new node to the visiting list
		int[][] result = new int[list.length + 1][2];
		for (int i = 0; i < list.length; i++) {
			result[i] = list[i].clone();
		}
		result[list.length] = values.clone();
		return result;
	}*/
	
	public void displaySolution() {
		boolean hasFinished = false;
		if (solveType.equals("Dijkstra")) {
			if (dijkstra.displaySolution(tiles, colours) == true) {
				hasFinished = true;
			}
		}
		else if (solveType.equals("TreeTrimming")) {
			if (isMaze == true) {
				if (treeTrimming.displaySolution(tiles, colours) == true) {
					hasFinished = true;
				}
			}
			else {
				hasFinished = true;
			}
		}
		else if (solveType.equals("Depth")) {
			if (depth.displaySolution(tiles, colours) == true) {
				hasFinished = true;
			}
		}
		
		if (hasFinished == true) {
			hasWon = false;
			//if (isLooping == false) {
			isRunning = false;
			//}
			
			/*else {
				clearTime = true;
				//solutionIndex = 0;
				isRunning = true;
				//visiting = new int[][] {{playerPos[0], playerPos[1]}};
				//generateMazeNew();
				primms = new Primms(tiles, colours);
				
				if (solveType.equals("TreeTrimming")) {
					treeTrimming = new TreeTrimming(tiles);
					//isEditing = false;
				}
				
				else if (solveType.equals("Dijkstra")) {
					dijkstra = new Dijkstra(tiles, playerPos);
				}
			}*/
		}
	}
	
	public void clearGrid() {
		for (int i = 0; i < xCount; i++) {
			for (int x = 0; x < yCount; x++) {
				tiles[i][x].setPreviousNode(new int[0]);
				if (tiles[i][x].getType() >= 4) {
					tiles[i][x].changeType(0, colours);
				}
			}
		}
		clearTime = false;
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		input();
		if (clearTime == true) {
			clearGrid();
		}
		
		else if (isEditing == true) {
			editGrid();
		}
		else if (isRunning == true && frameCount % timeDelay == 0) {
			if (hasWon == false) {
				if (solveType.equals("Dijkstra")) {
					if (dijkstra.cycle(tiles, colours) == true) {
						hasWon = true;
					}
					if (dijkstra.getRunning() == false) {
						isRunning = false;
					}
				}
				else if (solveType.equals("TreeTrimming") && isMaze == true) {
					if (treeTrimming.cycle(tiles, colours) == true) {
						hasWon = true;
					}
				}
				else if (solveType.equals("Depth")) {
					if (depth.cycle(tiles, colours) == false) {
						if (depth.getHasWon() == true) {
							hasWon = true;
							isRunning = true;
						}
						else {
							hasWon = false;
							isRunning = false;
						}
					}
				}
				else if (solveType.equals("Primms")) {
					if (primms.generateMazeNew(tiles, colours) == false) {
						isRunning = false;
						isMaze = primms.getIsMaze();
					}
				}
			}
			else {
				displaySolution();
			}
		}
		
		sr.begin(ShapeType.Filled);
		for (int i = 0; i < xCount; i++) {
			for (int j = 0; j < yCount; j++) {
				tempColour = tiles[i][j].getColour();
				sr.setColor(tempColour[0], tempColour[1], tempColour[2], 1);
				sr.rect(tiles[i][j].getX(), tiles[i][j].getY(), cellWidth, cellHeight);
			}
		}
		sr.end();
		
		
		//Follow render block creates a grid for creating a maze
		if (grid == true) {
			sr.begin(ShapeType.Line);
			sr.setColor(0.9f, 0.9f, 0.9f, 1);
			for (int i = 0; i < xCount; i++) {
				for (int j = 0; j < yCount; j++) {
					sr.rect(tiles[i][j].getX(), tiles[i][j].getY(), cellWidth, cellHeight);
				}
			}
			sr.end();
		}
		
		frameCount += 1;
	}
	
	@Override
	public void dispose() {
		sr.dispose();
	}
	
	
	
	
	
	
	
	
	
	/*public int[][] Library.removeValue(int[][] list, int index) { //Removes a value from a list given the list and index of item to remove
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
	
	
	//TODO - THE OLD MAZE GENERATION ALGORITHM THAT CREATES A MAZE "INSTANTLY"
	/*(public void generateMazeNew() {
		for (int i = 0; i < xCount; i++) { //Fills in everything with walls
			for (int x = 0; x < yCount; x++) {
				if (i % 2 == 0 && x % 2 == 0) {
					changeSpace(i, x, 0);
				}
				else {
					if (changeSpace(i, x, 1) == false) {
						isMaze = false;
					}
				}
			}
		}
		
		Random rand = new Random();
		
		int xWidth = (xCount + 1) / 2;
		int yWidth = (yCount + 1) / 2;
		int total = xWidth * yWidth;
		
		Node[][] nodes = new Node[xWidth][yWidth];
		for (int i = 0; i < xWidth; i++) {
			for (int x = 0; x < yWidth; x++) {
				nodes[i][x] = new Node(i, x);
			}
		}
		
		int[][] finalArcs = new int[0][4];
		int[][] arcs = new int[0][4];
		
		int xVal = (int)(rand.nextDouble() * xWidth) % xWidth;
		int yVal = (int)(rand.nextDouble() * yWidth) % yWidth;
		arcs = nodes[xVal][yVal].changeConnected(nodes, arcs);
		
		int index, xPos, yPos, xDir, yDir, skipped, length;
		for (int a = 1; a < total; a++) { //TODO MODULI IS PROBABLY UNNECESSARY
			length = arcs.length;
			index = (int)(length * rand.nextDouble()) % length;
			xPos = arcs[index][0];
			yPos = arcs[index][1];
			xDir = arcs[index][2];
			yDir = arcs[index][3];
			
			finalArcs = Library.addValue(finalArcs, arcs[index].clone());
			//arcs = Library.removeValue(arcs, index);
			
			xVal = xPos + xDir;
			yVal = yPos + yDir;
			arcs = nodes[xVal][yVal].changeConnected(nodes, arcs);
			
			skipped = 0;
			for (int i = 0; i < length; i++) {
				index = i - skipped;
				if (arcs[index][0] + arcs[index][2] == xVal && arcs[index][1] + arcs[index][3] == yVal) {
				//if (nodes[arcs[i][0] + arcs[i][2]][arcs[i][1] + arcs[i][3]].getConnected() == true) {
					arcs = Library.removeValue(arcs, index);
					skipped += 1;
				//}
				}
			}
		}
		
		for (int i = 0; i < finalArcs.length; i++) {
			xPos = finalArcs[i][0];
			yPos = finalArcs[i][1];
			xDir = finalArcs[i][2];
			yDir = finalArcs[i][3];
			changeSpace((xPos * 2) + xDir, (yPos * 2) + yDir, 0);
		}
	}*/
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
