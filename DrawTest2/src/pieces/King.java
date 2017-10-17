package pieces;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import enums.Colour;
import enums.Pieces;

public class King extends Piece {
	
	private final String uri_black= "DrawTest2/src/pictures/chess-pieces/ki.png"; 
	
	private final String uri_white = "DrawTest2/src/pictures/chess-pieces/ki_w.png";
	
	
	private void setKingImage() {
		try {
			if (super.getColour() == Colour.BLACK)
				super.setImage(ImageIO.read(new File(uri_black)));
			else 
				super.setImage(ImageIO.read(new File(uri_white)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public King(Colour colour) {
		super.setColour(colour);
		super.setPieceType(Pieces.KING);
		setKingImage();
	}

}
