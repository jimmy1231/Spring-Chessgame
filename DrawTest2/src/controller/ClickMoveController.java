package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import component.Chessboard;
import enums.Colour;
import enums.Pieces;
import model.Box;
import model.Location;
import service.ChessboardService;
import service.ClickMoveService;

@Controller
public class ClickMoveController {
	
	@Autowired
	ClickMoveService clickMoveService; 
	
	@Autowired 
	ChessboardService chessboardService; 
	
	@Autowired 
	Chessboard chessboard;
	
	private Colour turn;
	
	public ClickMoveController() {
		this.turn = Colour.WHITE;
	}
	
	/* 
	 * when user clicked for the 2nd time
	 * 1. determine if they clicked a piece or a box
	 * 			if same coloured piece, do nothing
	 * 			if different coloured piece, determine if that move is valid for killing that piece from previous calculations
	 * 			if box, determine if that move is valid or not from the previous calculations
	 * 
	 * 2. if move is valid, then move the piece over there
	 * 3. if invalid, then do nothing
	 * 
	 * when user clicked for the 1st time
	 * 1. determine if it's a piece or a box
	 * 
	 */
	public void determineAction(Integer x, Integer y) {
		
		Location loc = chessboardService.determineClickedBox(x, y);
		Box box = chessboard.getChessboard().get(loc.getY()).get(loc.getX()); 
		
//		System.out.println("TURN IS: " + this.turn);
		
		// Fix this later
		boolean checkmate = false;
		
		if (!checkmate) {
			// Determine if the box has a piece, if not, do nothing
			if (box.getPiece() != null) {
				if (clickMoveService.isClicked()) {
					// If there was a click before
					
					if (box.getPiece().getColour().equals(clickMoveService.getClickedBox().getPiece().getColour())) {
						// if the clicked box is of the same colour
						
						List<Location> nextMoveList = null;
								
						if (box.getPiece().getColour().equals(turn))
								nextMoveList = clickMoveService.availableMoves(box);
						
						clickMoveService.setClicked(true);
						clickMoveService.setClickedBox(box);
						clickMoveService.setNextMoveList(nextMoveList);
					} else {
						/* 
						 * If the clicked box is opposite colour, determine if overtaking 
						 * is part of the available list of moves
						 */
						boolean overtake = canMove(clickMoveService.getNextMoveList(), box);
						
						if (overtake) {
							if (isPromotion(box)) {
								clickMoveService.promotion(clickMoveService.getClickedBox(), box, true);
							} else {
								clickMoveService.overtakePiece(clickMoveService.getClickedBox(), box);
							}
							
							// NEW TURN
							this.turn = this.turn.equals(Colour.WHITE) ? Colour.BLACK : Colour.WHITE;
							clickMoveService.setClicked(false);
							clickMoveService.setClickedBox(null);
						} else {
							if (box.getPiece().getColour().equals(turn)) {
								List<Location> nextMoveList = null;
	
								nextMoveList = clickMoveService.availableMoves(box);
								clickMoveService.setClicked(true);
								clickMoveService.setClickedBox(box);
								clickMoveService.setNextMoveList(nextMoveList);
							}
							System.out.println("Invalid overtake: " + box.getPiece().getColour() + " " + box.getPiece().getPieceType());
						}
						
					}
					
				} else {
					// show available moves
					
					List<Location> nextMoveList = null; 
					
					if (box.getPiece().getColour().equals(turn)) {
						nextMoveList = clickMoveService.availableMoves(box);
					}
					
					clickMoveService.setClicked(true);
					clickMoveService.setNextMoveList(nextMoveList);
					clickMoveService.setClickedBox(box);
				}
				
			} else { // if user clicked on a box without pieces
				if (clickMoveService.isClicked()) {
					// Determine if the clicked piece can be moved to this empty box.
					
					boolean validMove = canMove(clickMoveService.getNextMoveList(), box); 
					
					if (validMove) {
						if (isPromotion(box)) {
							clickMoveService.promotion(clickMoveService.getClickedBox(), box, false);
						} else {
							clickMoveService.movePiece(clickMoveService.getClickedBox(), box);
						}
						this.turn = this.turn.equals(Colour.WHITE) ? Colour.BLACK : Colour.WHITE;
					}
				}
				
				clickMoveService.setClicked(false);
				clickMoveService.setClickedBox(null);
			}
		} else {
			// Insert what happens if checkmated
			
		}
		
	}
	
	private boolean canMove(List<Location> nextMoveList, Box box) {
		boolean validMove = false; 
		Location clickedBoxLocation = box.getLocation(); 
		if (nextMoveList != null) {
			for (Location nextMove : nextMoveList) {
				if (clickedBoxLocation.getX() == nextMove.getX() && clickedBoxLocation.getY() == nextMove.getY()) {
					 validMove = true;
				}
			}
		}
		
		return validMove;
	}
	
	private boolean isPromotion(Box box) {
		boolean isPromotion = false;
		Box clickedBox = clickMoveService.getClickedBox(); 
		
		if (clickedBox.getPiece().getPieceType().equals(Pieces.PAWN)) {
			Integer numbering = box.getNumbering(); 
			
			if (clickedBox.getPiece().getColour().equals(Colour.BLACK)) {
				if (numbering > (chessboard.getNumCols() * (chessboard.getNumRows() - 1 ) ) ) {
					isPromotion = true;
				}
			} else {
				if (numbering <= chessboard.getNumCols() ) {
					isPromotion = true;
				}
			}
			
		}
		
		return isPromotion;
	}
}
