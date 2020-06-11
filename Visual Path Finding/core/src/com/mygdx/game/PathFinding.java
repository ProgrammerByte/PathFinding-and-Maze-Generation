package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

//TODO - CHANGE NAME OF Library.addValue TO ADD VALUE
//TODO - ADD BACK LOOPING MAYBE
public class PathFinding extends ApplicationAdapter {
	ShapeRenderer sr;
	SpriteBatch batch;
	BitmapFont font;
	
	Algorithm algorithm;
	
	//Primms primms;
	//TreeTrimming treeTrimming;
	//Dijkstra dijkstra;
	//Depth depth;
	
	float[][] colours = new float[][] {{0, 0, 0}, {0, 0, 1}, {1, 0, 0}, {1, 1, 0}, {0.1f, 0.2f, 0.3f}, {0.3f, 0.5f, 1}, {0.5f, 0.2f, 1}}; 
	//Above corresponds with types: none, wall, player, objective, visited, currently visiting, winning path
	
	float[] tempColour;
	
	int screenWidth = 1920;
	int screenHeight = 1080;
	
	int xCount = 160;
	int yCount = 90;
	
	int selectedType = 1;
	
	int[] playerPos = new int[] {0, (int)((yCount - 1) / 2)};
	int[] goalPos = new int[] {xCount - 2, (int)((yCount - 1) / 2)};
	
	float cellWidth = (float)screenWidth / xCount;
	float cellHeight = (float)screenHeight / yCount;
	
	String solveType = "Dijkstra", state = "menu";
	
	Button[] buttons;
	Tile[][] tiles = new Tile[xCount][yCount];
	
	boolean grid = false, isRunning = false, hasWon = false, clearTime = false, isEditing = true, isMaze = false, isHeld = false;
	
	//int[][] visiting = new int[0][2];
	
	int frameCount = 0, timeDelay = 1;
	int offset, xPos1, xPos2, yPos, increment;
	
	@Override
	public void create() {
		sr = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(screenHeight / 192);
		
		for (int i = 0; i < xCount; i++) { //initialises grid
			for (int j = 0; j < yCount; j++) {
				tiles[i][j] = new Tile(colours[0], i * cellWidth, j * cellHeight);
			}
		}
		
		tiles[playerPos[0]][playerPos[1]].changeType(2, colours);
		tiles[goalPos[0]][playerPos[1]].changeType(3, colours);
		
		
		String[] pathFinders = new String[] {"Tree-Trimming", "Dijkstra", "A-Star", "Depth-First", "Depth-Star"}; //initialises menu
		String[] mazeGenerators = new String[] {"Primms", "R-Backtracker", "R-Division", "R-Division-H", "R-Division-V", "Mazectric"};
		int length = pathFinders.length;
		buttons = new Button[length + mazeGenerators.length];
		xPos1 = screenWidth / 8;
		xPos2 = (screenWidth * 5) / 8;
		increment = screenHeight / 8;
		yPos = ((2 * screenHeight ) / 3) + increment;
		int width = screenWidth / 4;
		int height = screenHeight / 10;
		offset = (int) (height * 0.9);
		for (int i = 0; i < buttons.length; i++) {
			if (i < length) { 
				buttons[i] = new Button(xPos1, yPos - (int)((i + 1.3) * increment), width, height, pathFinders[i]);
			}
			else {
				buttons[i] = new Button(xPos2, yPos - (int)(((i + 1.3) - length) * increment), width, height, mazeGenerators[i - length]);
			}
		}
	}
	
	public void editGrid() {
		int type = -1;
		if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
			type = 0;
		}
		else if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			if (isHeld == false) {
				type = selectedType;
			}
		}
		else {
			isHeld = false; //isheld prevents the user from altering the grid by left clicking immediately after closing the menu
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
	
	public void setupMaze() { //moves players to positions where the tree trimming algorithm will work with 100% certainty
		tiles[playerPos[0]][playerPos[1]].changeType(0, colours);
		tiles[goalPos[0]][goalPos[1]].changeType(0, colours);
		
		playerPos[0] -= playerPos[0] % 2;
		playerPos[1] -= playerPos[1] % 2;
		goalPos[0] -= goalPos[0] % 2;
		goalPos[1] -= goalPos[1] % 2;
		
		tiles[playerPos[0]][playerPos[1]].changeType(2, colours);
		tiles[goalPos[0]][goalPos[1]].changeType(3, colours);
		
		isMaze = true;
	}
	
	public void input() {
		if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			selectedType = 1;
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
			selectedType = 2;
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
			selectedType = 3;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.Q)) {
			grid = !grid;
		}
		//if (Gdx.input.isKeyJustPressed(Keys.L)) {
		//	isLooping = !isLooping;
		//}
		if (isEditing == true) {
			
			if (Gdx.input.isKeyPressed(Keys.M)) {
				state = "menu";
			}
			
			else if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT)) {
				state = "controls";
			}
			
			
			else if (Gdx.input.isKeyJustPressed(Keys.C)) {
				for (int i = 0; i < xCount; i++) {
					for (int x = 0; x < yCount; x++) {
						tiles[i][x].changeSpace(0, colours);
					}
				}
				isMaze = false;
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) { //run / stop TODO - MOVE TO SEPARATE METHOD
			if (isEditing == true) {
			//	tiles[playerPos[0]][playerPos[1]].setType(5); //changing type but not colour to indicate visiting node
				//visiting = new int[][] {{playerPos[0], playerPos[1]}};
				isRunning = true;
				
				if (solveType.equals("Dijkstra")) {
					algorithm = new Dijkstra(tiles, playerPos);
				}
				
				else if (solveType.equals("A-Star")) {
					algorithm = new AStar(tiles, playerPos, goalPos);
				}
				
				else if (solveType.equals("Tree-Trimming")) {
					if (isMaze == true) {
						algorithm = new TreeTrimming(tiles);
					}
					else {
						algorithm = new Dijkstra(tiles, playerPos); //instead of returning an error if the maze is imperfect, the selected algorithm will change to dijkstra's
					}
				}
				
				else if (solveType.equals("Depth-First")) {
					algorithm = new Depth(playerPos);
				}
				
				else if (solveType.equals("Depth-Star")) {
					algorithm = new DepthStar(playerPos, goalPos);
				}
				
				else if (solveType.equals("Primms")) {
					setupMaze();
					algorithm = new Primms(tiles, colours);
				}
				
				else if (solveType.equals("R-Division")) {
					setupMaze();
					algorithm = new RecursiveDivision(tiles, colours, 0);
				}
				else if (solveType.equals("R-Division-V")) {
					algorithm = new RecursiveDivision(tiles, colours, 1);
					setupMaze();
				}
				else if (solveType.equals("R-Division-H")) {
					setupMaze();
					algorithm = new RecursiveDivision(tiles, colours, -1);
				}
				
				else if (solveType.equals("Mazectric")) {
					algorithm = new Mazectric(tiles, colours);
				}
				else if (solveType.equals("R-Backtracker")) {
					setupMaze();
					algorithm = new RecursiveBacktracker(tiles, colours);
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
	
	public void displaySolution() {
		if (algorithm.displaySolution(tiles, colours) == true) {
			hasWon = false;
			isRunning = false;
			isEditing = true;
			clearTime = true;
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
		
		if (state.equals("grid")) { //the main state of the program
			if (clearTime == true) {
				clearGrid();
			}
			
			else if (isEditing == true) {
				editGrid();
			}
			else if (isRunning == true && frameCount % timeDelay == 0) {
				if (hasWon == false) {
					if (algorithm.cycle(tiles, colours) == true) {
						//isRunning = false;
						hasWon = algorithm.getHasWon();
						if (hasWon == false) {
							isRunning = false;
							isEditing = true;
							clearTime = true;
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
		
		
		else if (state.equals("menu")) { //TODO - perhaps move this to a different method
			sr.begin(ShapeType.Line); //renders menu
			sr.setColor(1, 1, 1, 0);
			for (int i = 0; i < buttons.length; i++) {
				sr.rect(buttons[i].getXPos(), buttons[i].getYPos(), buttons[i].getWidth(), buttons[i].getHeight());
			}
			sr.end();
			
			batch.begin();
			font.draw(batch, "Solve a Maze:", xPos1, yPos);
			font.draw(batch, "Create a Maze:", xPos2, yPos);
			for (int i = 0; i < buttons.length; i++) {
				font.draw(batch, buttons[i].getContents(), buttons[i].getXPos(), buttons[i].getYPos() + offset);
			}
			font.draw(batch, "Press CTRL for Controls", 0, screenHeight);
			font.draw(batch, "Press M to return here", 0, (int)(increment * 0.5));
			batch.end();
			
			
			//following checks for input
			if (Gdx.input.isButtonPressed(Buttons.LEFT) == true) {
				float mouseX = Gdx.input.getX();
				float mouseY = screenHeight - Gdx.input.getY();
				for (int i = 0; i < buttons.length; i++) {
					if (buttons[i].detectClick(mouseX, mouseY) == true) {
						isHeld = true;
						state = "grid";
						solveType = buttons[i].getContents();
						break;
					}
				}
			}
		}
		
		else if (state.equals("controls")) {
			batch.begin();
			font.draw(batch, "CTRL - View controls \n" +
							 "M - View menu \n" +
							 "ESC - Exit \n" +
							 "Enter - Start / Stop currently selected algorithm \n" +
							 "Left Click - Place currently selected tile / press menu button \n" +
							 "Right Click - Remove current tile \n" +
							 "1 - Select wall tile \n" + 
							 "2 - Select player tile \n" + 
							 "3 - Select goal tile \n" + 
							 "Q - Toggle grid \n \n" +
							 "Press M to return to the menu", 0, screenHeight);
			batch.end();
		}
	}
	
	@Override
	public void dispose() {
		sr.dispose();
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
