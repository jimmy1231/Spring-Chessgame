package pieces;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import enums.Colour;
import enums.Pieces;

public class Queen extends Piece {
	
	private final String uri_black = "DrawTest2/src/main/resouces/picture/chess-pieces/q.png"; 
	
	private final String uri_white = "DrawTest2/src/main/resouces/picture/chess-pieces/q_w.png";
	
	
	private void setQueenImage() {
		try {
			if (super.getColour() == Colour.BLACK)
				super.setImage(ImageIO.read(new File(uri_black)));
			else
				super.setImage(ImageIO.read(new File(uri_white)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Queen(Colour colour) {
		super.setColour(colour);
		super.setPieceType(Pieces.QUEEN);
		setQueenImage();
	}

}
