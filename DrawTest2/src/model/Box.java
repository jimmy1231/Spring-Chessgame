package model;

import enums.Position;
import pieces.Piece;

public class Box {
	
	
	private int width;
	
	
	private int height;
	
	
	private int numbering;
	
	
	private Piece piece; 
	
	
	private Location location;
	
	
	private boolean clicked;
	
	
	private boolean nextMove; 
	
	
	private Box up;
	
	
	private Box down;
	
	
	private Box left; 
	
	
	private Box right; 
	
	
	private Box upRight; 
	
	
	private Box upLeft; 
	
	
	private Box downRight; 
	
	
	private Box downLeft; 
	
	
	
	public boolean isNextMove() {
		return nextMove;
	}
	public void setNextMove(boolean nextMove) {
		this.nextMove = nextMove;
	}
	public Box getUpRight() {
		return upRight;
	}
	public void setUpRight(Box upRight) {
		this.upRight = upRight;
	}
	public Box getUpLeft() {
		return upLeft;
	}
	public void setUpLeft(Box upLeft) {
		this.upLeft = upLeft;
	}
	public Box getDownRight() {
		return downRight;
	}
	public void setDownRight(Box downRight) {
		this.downRight = downRight;
	}
	public Box getDownLeft() {
		return downLeft;
	}
	public void setDownLeft(Box downLeft) {
		this.downLeft = downLeft;
	}
	public Box getUp() {
		return up;
	}
	public void setUp(Box up) {
		this.up = up;
	}
	public Box getDown() {
		return down;
	}
	public void setDown(Box down) {
		this.down = down;
	}
	public Box getLeft() {
		return left;
	}
	public void setLeft(Box left) {
		this.left = left;
	}
	public Box getRight() {
		return right;
	}
	public void setRight(Box right) {
		this.right = right;
	}
	public boolean isClicked() {
		return clicked;
	}
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	public Piece getPiece() {
		return piece;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	public int getNumbering() {
		return this.numbering;
	}
	public void setNumbering(int numbering) {
		this.numbering = numbering;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setLink(Box box, Position pos) {
		if (pos.equals(Position.UP)) {
			this.setUp(box);
		} else if (pos.equals(Position.DOWN)) {
			this.setDown(box);
		} else if (pos.equals(Position.LEFT)) {
			this.setLeft(box);
		} else if (pos.equals(Position.RIGHT)) {
			this.setRight(box);
		} else if (pos.equals(Position.UP_RIGHT)) {
			this.setUpRight(box);
		} else if (pos.equals(Position.UP_LEFT)) {
			this.setUpLeft(box);
		} else if (pos.equals(Position.DOWN_RIGHT)) {
			this.setDownRight(box);
		} else if (pos.equals(Position.DOWN_LEFT)) {
			this.setDownLeft(box);
		}
	}
}
