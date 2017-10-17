package pieces;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import enums.Colour;
import enums.Pieces;

public class Pawn extends Piece {
	
	private final String uri_black = "DrawTest2/src/main/resouces/picture/chess-pieces/p.png"; 
	
	private final String uri_white = "DrawTest2/src/main/resouces/picture/chess-pieces/p_w.png"; 
	
	
	private void setPawnImage() {
		try {
			if (super.getColour() == Colour.BLACK)
				super.setImage(ImageIO.read(new File(uri_black)));
			else 
				super.setImage(ImageIO.read(new File(uri_white)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Pawn(Colour colour) {
		super.setColour(colour);
		super.setPieceType(Pieces.PAWN);
		setPawnImage();
	}

}
