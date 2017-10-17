package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ListMultimap;

import component.Chessboard;
import enums.Colour;
import enums.Pieces;
import enums.Position;
import model.Box;
import model.Location;
import pieces.Bishop;
import pieces.King;
import pieces.Piece;
import pieces.Rook;

@Component
public class ChessboardHelperService {

	@Autowired
	ChessboardService chessboardService; 
	
	@Autowired
	Chessboard chessboard; 
	
	
	/**
	 * Returns a list of moves (as reference) that a Pawn at its current position
	 * can perform
	 * 
	 * @param nextMoveList
	 * @param box
	 * @param killMove 
	 * 		 	since all friendly pieces have the potential to be taken out by an enemy piece, 
	 * 		 	for King move, we must think of available moves as one after the enemy has taken our piece
	 *		 	and we can counter strike
	 *
	 * @return nextMoveList
	 */
	public List<Location> pawnAvailableMoves(List<Location> nextMoveList, Box box, Boolean killMove) {
		Piece pawn = box.getPiece();
		
		if (pawn.getColour().equals(Colour.BLACK)) {
			if (!killMove) {
				if (pawn.isAlreadyMoved()) {
					// move only 1 square down
					if (box.getDown() != null) {
						if (box.getDown().getPiece() == null)
							nextMoveList.add(box.getDown().getLocation());
					}
					
				} else {
					// move 2 squares down
					Box boxPointer = box;
					for (int i = 0; i < 2; i ++ ) {
						boxPointer = boxPointer.getDown();
						if (boxPointer != null) {
							if (boxPointer.getPiece() == null) {
								nextMoveList.add(boxPointer.getLocation());
							} else {
								break;
							}
						}
					}
				} 
				if (box.getDownLeft() != null && box.getDownLeft().getPiece() != null 
						&& !box.getDownLeft().getPiece().getColour().equals(box.getPiece().getColour())) {
					nextMoveList.add(box.getDownLeft().getLocation());
				}
				if (box.getDownRight() != null && box.getDownRight().getPiece() != null
						&& !box.getDownRight().getPiece().getColour().equals(box.getPiece().getColour())) {
					nextMoveList.add(box.getDownRight().getLocation()); 
				}
			} else {
				if (box.getDownLeft() != null 
						&& (box.getDownLeft().getPiece() == null || box.getDownLeft().getPiece().getColour().equals(box.getPiece().getColour()))) {
					nextMoveList.add(box.getDownLeft().getLocation());
				}
				if (box.getDownRight() != null 
						&& (box.getDownRight().getPiece() == null || box.getDownRight().getPiece().getColour().equals(box.getPiece().getColour()))) {
					nextMoveList.add(box.getDownRight().getLocation());
				}
			}
			
		} else {
			if (!killMove) {
				if (pawn.isAlreadyMoved()) {
					// move only 1 square up
					if (box.getUp() != null) {
						if (box.getUp().getPiece() == null) 
							nextMoveList.add(box.getUp().getLocation());
					}
				} else {
					// move 2 squares up
					Box boxPointer = box;
					for (int i = 0; i < 2; i ++ ) {
						boxPointer = boxPointer.getUp();
						if (boxPointer != null) {
							if (boxPointer.getPiece() == null) {
								nextMoveList.add(boxPointer.getLocation());
							} else {
								break;
							}
						}
					}
				}
				if (box.getUpLeft() != null && box.getUpLeft().getPiece() != null
						&& !box.getUpLeft().getPiece().getColour().equals(box.getPiece().getColour())) {
					nextMoveList.add(box.getUpLeft().getLocation()); 
				}
				if (box.getUpRight() != null && box.getUpRight().getPiece() != null
						&& !box.getUpRight().getPiece().getColour().equals(box.getPiece().getColour())) {
					nextMoveList.add(box.getUpRight().getLocation()); 
				}
			} else {
				if (box.getUpLeft() != null && box.getUpLeft().getPiece() == null) {
					nextMoveList.add(box.getUpLeft().getLocation()); 
				}
				if (box.getUpRight() != null && box.getUpRight().getPiece() == null) {
					nextMoveList.add(box.getUpRight().getLocation()); 
				}
			}
		}
		
		return nextMoveList;
	}
	
	/**
	 * Returns a list of moves (as reference) that a Bishop at its current position
	 * can perform
	 * 
	 * @param nextMoveList
	 * @param box
	 * @return nextMoveList
	 */
	public List<Location> bishopAvailableMoves(List<Location> nextMoveList, Box box, Boolean killMove) {
		
		Box upRightCopy = box;
		Box upLeftCopy = box;
		Box downRightCopy = box;
		Box downLeftCopy = box;

		while (upRightCopy.getUpRight() != null ) {
			upRightCopy = upRightCopy.getUpRight(); 
			if (addNewMoveToList(nextMoveList, box, upRightCopy, killMove))
				break;
		}
		
		while (upLeftCopy.getUpLeft() != null ) {
			upLeftCopy = upLeftCopy.getUpLeft(); 
			if (addNewMoveToList(nextMoveList, box, upLeftCopy, killMove))
				break;
		}
		
		while (downRightCopy.getDownRight() != null ) {
			downRightCopy = downRightCopy.getDownRight();
			if (addNewMoveToList(nextMoveList, box, downRightCopy, killMove))
				break;
		}
		
		while (downLeftCopy.getDownLeft() != null ) {
			downLeftCopy = downLeftCopy.getDownLeft(); 
			if (addNewMoveToList(nextMoveList, box, downLeftCopy, killMove))
				break;
		}
		
		return nextMoveList;
	}
	
	/**
	 * Returns a list of moves (as reference) that a Rook at its current position
	 * can perform
	 * 
	 * @param nextMoveList
	 * @param box
	 * @param killMove: moves intended to kill the opponent piece
	 * @return nextMoveList
	 */
	public List<Location> rookAvailableMoves(List<Location> nextMoveList, Box box, Boolean killMove) {
		
		Box leftCopy = box; 
		Box rightCopy = box; 
		Box upCopy = box; 
		Box downCopy = box; 
		
		while (leftCopy.getLeft() != null ) {
			leftCopy = leftCopy.getLeft();
			if (addNewMoveToList(nextMoveList, box, leftCopy, killMove))
				break;
		}
		
		while (rightCopy.getRight() != null ) {
			rightCopy = rightCopy.getRight();
			if (addNewMoveToList(nextMoveList, box, rightCopy, killMove)) 
				break; 
		}

		while (upCopy.getUp() != null ) {
			upCopy = upCopy.getUp();
			if (addNewMoveToList(nextMoveList, box, upCopy, killMove))
				break;
		}

		while (downCopy.getDown() != null ) {
			downCopy = downCopy.getDown();
			if (addNewMoveToList(nextMoveList, box, downCopy, killMove ))
				break;
		}
		
		// Insert castling logic here
	
		
		return nextMoveList; 
	}
	
	/**
	 * Returns a list of moves (as reference) that a King at its current position
	 * can perform
	 * 
	 * @param nextMoveList
	 * @param box
	 * @return nextMoveList
	 */
	public List<Location> kingAvailableMoves(List<Location> nextMoveList, Box box, Boolean killMove) {
		
		List<Box> allAvailableMoves = null; 
		
		if (!killMove)
			allAvailableMoves = allOpponentMoves(box); 
		
		if (box.getUp() != null ) {
			if (!killMove) {
				if (!canBeTaken(box.getUp(), allAvailableMoves))
					addNewMoveToList(nextMoveList, box, box.getUp(), killMove); 
			} else {
				addNewMoveToList(nextMoveList, box, box.getUp(), killMove);
			}
		}
		if (box.getDown() != null ) {
			if (!killMove) {
				if (!canBeTaken(box.getDown(), allAvailableMoves))
					addNewMoveToList(nextMoveList, box, box.getDown(), killMove); 
			} else {
				addNewMoveToList(nextMoveList, box, box.getDown(), killMove);
			}
		}
		if (box.getLeft() != null ) {
			if (!killMove) {
				if (!canBeTaken(box.getLeft(), allAvailableMoves))
					addNewMoveToList(nextMoveList, box, box.getLeft(), killMove); 
			} else {
				addNewMoveToList(nextMoveList, box, box.getLeft(), killMove);
			}
		}
		if (box.getRight() != null ) {
			if (!killMove) {
				if (!canBeTaken(box.getRight(), allAvailableMoves))
					addNewMoveToList(nextMoveList, box, box.getRight(), killMove); 
			} else {
				addNewMoveToList(nextMoveList, box, box.getRight(), killMove);
			}
		}
		if (box.getUpRight() != null ) {
			if (!killMove) {
				if (!canBeTaken(box.getUpRight(), allAvailableMoves))
					addNewMoveToList(nextMoveList, box, box.getUpRight(), killMove);
			} else {
				addNewMoveToList(nextMoveList, box, box.getUpRight(), killMove);
			}
		}
		if (box.getUpLeft() != null ) {
			if (!killMove) {
				if (!canBeTaken(box.getUpLeft(), allAvailableMoves))
					addNewMoveToList(nextMoveList, box, box.getUpLeft(), killMove); 
			} else {
				addNewMoveToList(nextMoveList, box, box.getUpLeft(), killMove);
			}
		}
		if (box.getDownRight() != null ) {
			if (!killMove) {
				if (!canBeTaken(box.getDownRight(), allAvailableMoves))
					addNewMoveToList(nextMoveList, box, box.getDownRight(), killMove);
			} else {
				addNewMoveToList(nextMoveList, box, box.getDownRight(), killMove);
			}
		}
		if (box.getDownLeft() != null ) {
			if (!killMove) {
				if (!canBeTaken(box.getDownLeft(), allAvailableMoves))
					addNewMoveToList(nextMoveList, box, box.getDownLeft(), killMove); 
			} else {
				addNewMoveToList(nextMoveList, box, box.getDownLeft(), killMove); 
			}
		}
		
		// Insert castling logic
		castle(box, nextMoveList);
		
		return nextMoveList; 
	}
	
	/**
	 * Returns a list of moves (as reference) that a Queen at its current position
	 * can perform
	 * 
	 * @param nextMoveList
	 * @param box
	 * @return nextMoveList
	 */
	public List<Location> queenAvailableMoves(List<Location> nextMoveList, Box box, Boolean killMove) {
		this.bishopAvailableMoves(nextMoveList, box, killMove);
		this.rookAvailableMoves(nextMoveList, box, killMove);
		return nextMoveList;
	}
	
	/**
	 * Returns a list of moves (as reference) that a Knight at its current position
	 * can perform
	 * 
	 * @param nextMoveList
	 * @param box
	 * @return nextMoveList
	 */
	public List<Location> knightAvailableMoves(List<Location> nextMoveList, Box box, Boolean killMove) {
		
		/* 
		 * Split up into 2 up, 2 down, 2 left, 2 right (check if any of those are unavailable)
		 * 
		 * then 
		 * 
		 * Split up into up, down, left, right
		 * 
		 * 2 up: right, left
		 * 2 down: right, left
		 * 2 left: up, down
		 * 2 right: up, down
		 * 
		 */
		
		Box upUp = this.checkKnightUpDownLeftRight(box, Position.UP);
		Box downDown = this.checkKnightUpDownLeftRight(box, Position.DOWN);
		Box rightRight = this.checkKnightUpDownLeftRight(box, Position.RIGHT);
		Box leftLeft = this.checkKnightUpDownLeftRight(box, Position.LEFT);
		
		Colour colour = box.getPiece().getColour();
		
		if (upUp != null) {
			this.checkKnightLeftRight(nextMoveList, upUp, colour);
		}
		if (downDown != null) {
			this.checkKnightLeftRight(nextMoveList, downDown, colour);
		}
		if (rightRight != null) {
			this.checkKnightUpDown(nextMoveList, rightRight, colour);
		}
		if (leftLeft != null) {
			this.checkKnightUpDown(nextMoveList, leftLeft, colour);
		}
		
		return nextMoveList;
	}
	
	private Box checkKnightUpDownLeftRight(Box box, Position pos) {
		
		Box newBox = box;
		
		if (pos.equals(Position.UP)) {
			if (newBox.getUp() != null) {
				newBox = newBox.getUp();
				if (newBox.getUp() != null) {
					newBox = newBox.getUp();
				} else {
					newBox = null;
				}
			} else {
				newBox = null;
			}
		} else if (pos.equals(Position.DOWN)) {
			if (newBox.getDown() != null) {
				newBox = newBox.getDown();
				if (newBox.getDown() != null) {
					newBox = newBox.getDown();
				} else {
					newBox = null;
				}
			} else {
				newBox = null;
			}
		} else if (pos.equals(Position.RIGHT)) {
			if (newBox.getRight() != null) {
				newBox = newBox.getRight();
				if (newBox.getRight() != null) {
					newBox = newBox.getRight();
				} else {
					newBox = null;
				}
			} else {
				newBox = null;
			}
		} else if (pos.equals(Position.LEFT)) {
			if (newBox.getLeft() != null) {
				newBox = newBox.getLeft();
				if (newBox.getLeft() != null) {
					newBox = newBox.getLeft();
				} else {
					newBox = null;
				}
			} else {
				newBox = null;
			}
		}
		
		return newBox;
	}
	
	private void checkKnightLeftRight(List<Location> nextMoveList, Box box, Colour colour) {
		if (box.getLeft() != null) {
			Box left = box.getLeft();
			Piece piece = left.getPiece();
			
			if (piece != null) {
				if (!piece.getColour().equals(colour)) 
					nextMoveList.add(left.getLocation());
			} else
				nextMoveList.add(left.getLocation());
		}
		if (box.getRight() != null) {
			Box right = box.getRight();
			Piece piece = right.getPiece();
			
			if (piece != null) {
				if (!piece.getColour().equals(colour)) 
					nextMoveList.add(right.getLocation());
			} else
				nextMoveList.add(right.getLocation());
		}
	}
	
	private void checkKnightUpDown(List<Location> nextMoveList, Box box, Colour colour) {
		if (box.getUp() != null) {
			Box up = box.getUp();
			Piece piece = up.getPiece();
			
			if (piece != null) {
				if (!piece.getColour().equals(colour)) 
					nextMoveList.add(up.getLocation());
			} else
				nextMoveList.add(up.getLocation());
		}
		if (box.getDown() != null) {
			Box down = box.getDown();
			Piece piece = down.getPiece();
			
			if (piece != null) {
				if (!piece.getColour().equals(colour)) 
					nextMoveList.add(down.getLocation());
			} else
				nextMoveList.add(down.getLocation());
		}
	}
	
	/**
	 * Using geographical distance to find closest piece from source
	 * 
	 * @param source
	 * @param pieceType
	 * @param colour
	 * @return
	 */
	public Piece getClosestPieceByPieceType(Piece source, Pieces pieceType, Colour colour) {
		
		List<Piece> pieceList = null;
		
		if (source.getColour().equals(Colour.WHITE))
			pieceList = chessboard.getWhitePieceMultimap().get(pieceType); 
		else 
			pieceList = chessboard.getBlackPieceMultimap().get(pieceType);
		
		Piece minDistPiece = null;
		Double x_dist = 0D; 
		Double y_dist = 0D;
		Double dist = 0D;
		Double minDist = 0D; 
		
		for (int i = 0; i < pieceList.size(); i ++ ) {
			Piece piece = pieceList.get(i); 
			x_dist = Math.abs(source.getBox().getLocation().getX().doubleValue() - piece.getBox().getLocation().getX().doubleValue());
			y_dist = Math.abs(source.getBox().getLocation().getY().doubleValue() - piece.getBox().getLocation().getY().doubleValue()); 
			
			dist = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));
			
			if (i == 0) {
				minDist = dist;
				minDistPiece = piece;
			} else {
				if (dist < minDist ) {
					minDist = dist;
					minDistPiece = piece;
				}
			}
		}
		
		return minDistPiece;
	}
	
	/**
	 * Checks if the move can be made in 'move' square
	 * 
	 * Factors considered: 
	 * 	- if same coloured piece 
	 * 	- if out of bounds
	 * 	- if move is killMove (see description)
	 *
	 * @param nextMoveList	list of next moves
	 * @param source		the box which the move comes from 
	 * @param piece			the box we're considering to add to nextMoveList
	 * @param killMove		determining if it's a killMove (if it is, we include the enemy square as an available move, if 
	 * 						not, we don't)
	 * @return
	 */
	private boolean addNewMoveToList(List<Location> nextMoveList, Box source, Box move, Boolean killMove) {
		boolean isBreak = false;
		
		if (move.getPiece() == null ) {
			nextMoveList.add(move.getLocation());
		} else {
			if (!killMove) {
				if (!move.getPiece().getColour().equals(source.getPiece().getColour())) {
					nextMoveList.add(move.getLocation());
					isBreak = true;
				} else {
					isBreak = true;
				}
			} else {
				nextMoveList.add(move.getLocation());
				isBreak = true;
			}
		}
		return isBreak;
	}
	
	private boolean canBeTaken(Box box, List<Box> allAvailableMoves) {
		boolean canBeTaken = false;

		for (Box move : allAvailableMoves ) {
			if (move.getNumbering() == box.getNumbering()) {
				canBeTaken = true;
				break;
			}
		}
		return canBeTaken;
	}
	
	private List<Box> allOpponentMoves(Box source) {				
		Colour colour = source.getPiece().getColour().equals(Colour.BLACK) ? Colour.WHITE : Colour.BLACK; 
		
		List<Box> boxList = chessboard.getAllBoxesByColour(colour, new ArrayList<Pieces>());
		List<Box> allAvailableMoves = new ArrayList<Box>();
		
		for (Box boxlet : boxList ) {
//			System.out.println("Available Moves for: " + boxlet.getPiece().getPieceType() + " " + boxlet.getPiece().getColour() + " " + boxlet.getNumbering());
			List<Location> availableMoves = chessboardService.getAvailableMoves(boxlet, true);
			for (Location availableMove : availableMoves) {
				Box box = chessboard.getChessboard().get(availableMove.getY()).get(availableMove.getX());
				allAvailableMoves.add(box);
//				System.out.println("     " + box.getNumbering());
			}
			availableMoves.clear();
		}
		return allAvailableMoves;
	}
	
	/**
	 * Returns the theoretical locations which the King/Rook can castle to.
	 * IMPORTANT:
	 * --------------------
	 * Does NOT take into account whether king is being checked
	 * 
	 * Validation steps
	 * Step 1: There exists a Rook AND a King on the chessboard
	 * 
	 * Step 2: Both pieces have not been moved
	 * 
	 * Step 3: There are no pieces between them 
	 * 
	 * @param box
	 * @param nextMoveList
	 * @return List of possible moves
	 */
	private List<Location> castle(Box box, List<Location> nextMoveList) {
		List<Location> list = new ArrayList<Location>(); 
		
		King king = null;
		Rook rook = null;
		
		if (box.getPiece() instanceof King) {
			rook = (Rook) getClosestPieceByPieceType(box.getPiece(), Pieces.ROOK, box.getPiece().getColour());
			king = (King) box.getPiece();
		}
		else {
			king = (King) getClosestPieceByPieceType(box.getPiece(), Pieces.KING, box.getPiece().getColour());
			rook = (Rook) box.getPiece();
		}
		
		// Step 1
		if (king != null && rook != null) {

			// Step 2
			if (!king.isAlreadyMoved() && !rook.isAlreadyMoved()) {
				
				Box left = null; 
				Box right = null; 
				
				boolean isPiecesInBetween; 
				if (king.getPosition() < rook.getPosition()) {
					left = king.getBox(); 
					right = rook.getBox(); 
				} else {
					left = rook.getBox(); 
					right = king.getBox();
				}
				isPiecesInBetween = isPiecesInBetween(left, right); 
				
				// Step 3
				if (!isPiecesInBetween) {
					list.addAll(piecesInBetween(left, right)); 
				}	
			}
		}
		return list;
	}
	
	private boolean isPiecesInBetween(Box left, Box right) {
		boolean piecesInBetween = false;
		Box pointer = left;
		
		while (pointer != null) {
			if (!(pointer.getNumbering() == right.getNumbering())) {
				if ((pointer.getPiece() != null) && (pointer.getNumbering() != left.getNumbering())) {
					piecesInBetween = true;
					break;
				}
				pointer = pointer.getRight();
			} else break;
		}
		
		return piecesInBetween;
	}
	
	private Set<Location> piecesInBetween(Box left, Box right) {
		Set<Location> set = new HashSet<Location>();
		
		Box pointer = left;
		while (pointer != null) {
			if (!(pointer.getNumbering() == right.getNumbering())) {
				if ((pointer.getPiece() != null) && (pointer.getNumbering() != left.getNumbering())) {
					break;
				}
				if (pointer.getNumbering() != left.getNumbering()) {
					set.add(pointer.getLocation()); 
				}
				pointer = pointer.getRight();
			} else break;
		}
		
		return set;
	}
}
