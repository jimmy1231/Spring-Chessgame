package pieces;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import enums.Colour;
import enums.Pieces;

public class Queen extends Piece {

	private final String uri_black = "picture/chess-pieces/q.png";

	private final String uri_white = "picture/chess-pieces/q_w.png";


	private void setQueenImage() {
		try {
			System.out.println(uri_black);

			if (super.getColour() == Colour.BLACK)
				super.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream(uri_black)));
			else
				super.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream(uri_white)));
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
