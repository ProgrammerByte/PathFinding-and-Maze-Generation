package com.mygdx.game;

public class Button { //for use in the menu
	protected int xPos, yPos, width, height;
	protected String contents;
	
	public Button(int xPos, int yPos, int width, int height, String contents) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.contents = contents;
	}
	
	public boolean detectClick(float mouseX, float mouseY) { //has the button been clicked?
		int xPos = this.getXPos();
		int yPos = this.getYPos();
		if (mouseX >= xPos && mouseX <= xPos + this.getWidth() && mouseY >= yPos && mouseY <= yPos + this.getHeight()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public int getXPos() {
		return xPos;
	}
	public int getYPos() {
		return yPos;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public String getContents() {
		return contents;
	}
}
