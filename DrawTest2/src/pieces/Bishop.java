package pieces;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import enums.Colour;
import enums.Pieces;

public class Bishop extends Piece {
	
	private final String uri_black = "DrawTest2/src/pictures/chess-pieces/b.png"; 
	
	private final String uri_white = "DrawTest2/src/pictures/chess-pieces/b_w.png";
	
	
	public Bishop(Colour colour) {
		super.setColour(colour);
		super.setPieceType(Pieces.BISHOP);
		setBishopImage(); 
	}
	
	private void setBishopImage() {
		try {
			if (super.getColour() == Colour.BLACK)
				super.setImage(ImageIO.read(new File(uri_black)));
			else
				super.setImage(ImageIO.read(new File(uri_white)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
