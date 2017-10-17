package pieces;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import enums.Colour;
import enums.Pieces;

public class Rook extends Piece {

	private final String uri_black  = "picture/chess-pieces/r.png";

	private final String uri_white = "picture/chess-pieces/r_w.png";


	private void setRookImage() {
		try {
			System.out.println(System.getProperty("user.dir"));

			if (super.getColour() == Colour.BLACK)

				super.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream(uri_black)));
			else
				super.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream(uri_white)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Rook(Colour colour) {
		super.setColour(colour);
		super.setPieceType(Pieces.ROOK);
		setRookImage();
	}

}
