package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import component.Chessboard;
import model.Box;
import model.Location;
import pieces.Piece;
import pieces.Queen;

@Component
public class ClickMoveService {

	private boolean clicked;
	
	private List<Location> nextMoveList; 
	
	private Box clickedBox; 
	
	@Autowired
	private Chessboard chessboard; 
	
	@Autowired
	private ChessboardService chessboardService; 

	
	public Box getClickedBox() {
		return clickedBox;
	}
	public void setClickedBox(Box clickedBox) {
		this.clickedBox = clickedBox;
	}
	public List<Location> getNextMoveList() {
		return nextMoveList;
	}
	public void setNextMoveList(List<Location> nextMoveList) {
		this.nextMoveList = nextMoveList;
	}
	public boolean isClicked() {
		return clicked;
	}
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	/**
	 * Determines available moves and sets a flag so they can be drawn
	 * out in repaint()
	 * 
	 * @param box
	 */
	public List<Location> availableMoves(Box box) {
		List<Location> nextMoveList = chessboardService.getAvailableMoves(box, false);
		
		for (Location nextMove : nextMoveList ) {
			setNextMove(nextMove);
		}
		
		return nextMoveList;
	}
	
	/**
	 * Move this piece from one box to another 
	 * 
	 * @param source
	 * @param dest
	 */
	public void movePiece(Box source, Box dest) {
		dest.setPiece(source.getPiece());
		dest.getPiece().setBox(dest);
		source.setPiece(null);
		dest.getPiece().setAlreadyMoved(true);
		dest.getPiece().setPosition(dest.getNumbering());
	}
	
	/**
	 * Overtake the destination piece with the source piece. 
	 * 
	 * Step 1: Put overtaken piece into closed set (based on respective colour) 
	 * 
	 * Step 2: Transfer pieces
	 * 
	 * @param source
	 * @param dest
	 */
	public void overtakePiece(Box source, Box dest) {
//		printPiece(dest.getPiece());

		// Step 1
		chessboard.setClosedSet(dest.getPiece(), dest.getPiece().getColour());
		chessboard.removeFromMultimap(dest);
		
//		printPiece(dest.getPiece());

		// Step 2
		dest.setPiece(source.getPiece());
		dest.getPiece().setBox(dest);
		source.setPiece(null);
		dest.getPiece().setAlreadyMoved(true);
		dest.getPiece().setPosition(dest.getNumbering());
	}
	
	/**
	 * Promote the piece to a Queen
	 * 
	 * @param source
	 * @param dest
	 */
	public void promotion(Box source, Box dest, boolean overtake) {
//		printPiece(dest.getPiece()); 
		if (overtake) {
			chessboard.setClosedSet(dest.getPiece(), dest.getPiece().getColour());
			chessboard.removeFromMultimap(dest);
		}	
//		printPiece(dest.getPiece()); 

		dest.setPiece(new Queen(source.getPiece().getColour()));
		dest.getPiece().setBox(dest);
		dest.getPiece().setPosition(dest.getNumbering());
		source.setPiece(null);
	}
	
	private void setNextMove(Location loc) {
		this.chessboard.getChessboard().get(loc.getY()).get(loc.getX()).setNextMove(true);
	}
	
	private void printPiece(Piece piece) {
		if (piece != null) {
			List<Piece> list = this.chessboard.getWhitePieceMultimap().get(piece.getPieceType()); 
			System.out.println("Size of WHITE"  + " " + piece.getPieceType() + ": " + list.size());
			list = this.chessboard.getBlackPieceMultimap().get(piece.getPieceType()); 
			System.out.println("Size of BLACK"  + " " + piece.getPieceType() + ": " + list.size());
		}
	}
}
