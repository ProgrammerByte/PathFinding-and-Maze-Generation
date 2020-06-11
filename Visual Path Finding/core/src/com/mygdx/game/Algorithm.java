package com.mygdx.game;

public class Algorithm {
	protected boolean hasWon;
	public Algorithm() {
		this.hasWon = false;
	}
	
	
	public boolean cycle(Tile[][] tiles, float[][] colours) { //true if finished, false if not finished
		return true;
	}
	
	public boolean displaySolution(Tile[][] tiles, float[][] colours) { //true if finished, false if not finished. Should only be executed if hasWon is equal to true
		return true;
	}
	
	
	
	public boolean getHasWon() {
		return hasWon;
	}
	
	public void setHasWon(boolean value) {
		hasWon = value;
	}
}
