package pieces;

import java.awt.image.BufferedImage;

import enums.Colour;
import enums.Pieces;
import model.Box;

public class Piece {
	
	private int position;
	private Colour colour;
	private Pieces pieceType;
	private BufferedImage image;
	private boolean alreadyMoved;
	private Box box;
	
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}
	public boolean isAlreadyMoved() {
		return alreadyMoved;
	}
	public void setAlreadyMoved(boolean alreadyMoved) {
		this.alreadyMoved = alreadyMoved;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public Pieces getPieceType() {
		return pieceType;
	}
	public void setPieceType(Pieces pieceType) {
		this.pieceType = pieceType;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public Colour getColour() {
		return colour;
	}
	public void setColour(Colour colour) {
		this.colour = colour;
	} 
	
}
