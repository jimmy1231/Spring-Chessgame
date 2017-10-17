package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import component.Chessboard;
import enums.Colour;
import enums.Pieces;
import enums.Position;
import model.Box;
import model.Location;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import utils.ChessboardTest;
import utils.DrawUtil;

@Component
public class ChessboardService {
	
	@Autowired
	private Chessboard chessboard; 
	
	@Autowired
	private ChessboardHelperService chessboardHelperService;
	
	private boolean checkmate;
	
	public Chessboard getChessboard() {
		return chessboard;
	}
	public void setChessboard(Chessboard chessboard) {
		this.chessboard = chessboard;
	}
	public void setCheckmate(boolean checkmate) {
		this.checkmate = checkmate;
	}
	public boolean getCheckmate() {
		return this.checkmate;
	}
	
	public void initialize() {
		this.loadChessboard();
		this.initializePieces();
		this.linkBoxes(); 
		this.checkmate = false;
		ChessboardTest.printLinks(this.chessboard.getChessboard());
		ChessboardTest.printPieces(this.chessboard.getChessboard());
	}
	
	public Location determineClickedBox(Integer x, Integer y) {
		Location loc = new Location();
		
		Integer xLoc = (x / this.chessboard.getCellWidth()) * this.chessboard.getCellWidth();
		Integer yLoc = (y / this.chessboard.getCellHeight()) * this.chessboard.getCellHeight();

		System.out.println(xLoc + " " + yLoc);
		loc.setX(xLoc);
		loc.setY(yLoc);
		
		Box box = this.chessboard.getChessboard().get(yLoc).get(xLoc);
		box.setClicked(true);
		System.out.println("DETERMINED CLICK " + box.getLocation().getX() + " " + box.getLocation().getY());
		
		return loc;
	}
	
	/**
	 * Returns a list of valid locations for that piece to move to.
	 * 
	 * @param pieceType
	 * @param loc
	 * @return
	 */
	public List<Location> getAvailableMoves(Box box, Boolean killMove) {
		
		List<Location> nextMoveList = new ArrayList<>(); 
		
		if (box.getPiece().getPieceType() == Pieces.PAWN) {
			chessboardHelperService.pawnAvailableMoves(nextMoveList, box, killMove);
			
		} else if (box.getPiece().getPieceType() == Pieces.BISHOP) {
			chessboardHelperService.bishopAvailableMoves(nextMoveList, box, killMove); 
			
		} else if (box.getPiece().getPieceType() == Pieces.ROOK) {
			chessboardHelperService.rookAvailableMoves(nextMoveList, box, killMove); 
			
		} else if (box.getPiece().getPieceType() == Pieces.KING) {
			chessboardHelperService.kingAvailableMoves(nextMoveList, box, killMove); 
			
		} else if (box.getPiece().getPieceType() == Pieces.QUEEN) {
			chessboardHelperService.queenAvailableMoves(nextMoveList, box, killMove); 
			
		} else if (box.getPiece().getPieceType() == Pieces.KNIGHT) {
			chessboardHelperService.knightAvailableMoves(nextMoveList, box, killMove);
			
		}
		
		return nextMoveList;
	}
	
	public boolean isCheckmate(Colour colour) {
		boolean checkmate = false; 
		List<Location> kingMoves = new ArrayList<>();
		
		Box kingBox = chessboard.getKingBox(colour);
//		System.out.println(colour.toString() + " KING LOCATION: " + kingBox.getNumbering());
		kingMoves = chessboardHelperService.kingAvailableMoves(kingMoves, kingBox, true);
//		System.out.println("KING MOVES SIZE: " + kingMoves.size());
		if (kingMoves.size() == 0) {
			checkmate = true;
		}
		
		return checkmate;
	}
	
	/**
	 * Load All Chess Boards onto Map (Board)
	 * 
	 * @return Map<Integer, Map<Integer, Box>>
	 */
	private void loadChessboard() {
		int currXPos = 0;
		int currYPos = 0;
		
		int numbering = 1;
		for (int i = 0; i < this.chessboard.getNumRows(); i++) {
			currYPos = i * this.chessboard.getCellHeight();
			Map<Integer, Box> innerMap = new HashMap<>();
			this.chessboard.getChessboard().put(currYPos, innerMap);
			
			for (int j = 0; j < this.chessboard.getNumCols(); j++) {
				currXPos = j * this.chessboard.getCellWidth();
				innerMap.put(currXPos, createBox(currXPos, currYPos, this.chessboard.getCellWidth(), this.chessboard.getCellHeight(), numbering));
				this.chessboard.getLocationMap().put(numbering, new Location(currXPos, currYPos));
				numbering++;
			}
		}
	}
	
	/**
	 * Puts down all pieces in their starting positions
	 */
	private void initializePieces() {
		createPieces(Colour.BLACK);
		createPieces(Colour.WHITE);
	}
	
	/**
	 * Link all boxes together for traversal and path
	 * determination algorithms
	 */
	private void linkBoxes() {
		List<Box> boxes = this.chessboard.getChessboardAsList(); 
		
		for (Box box : boxes) {
			
			for (Integer i = 0; i < 8; i ++ ) {
				
				Integer number = neighbourBoxNum(box, box.getNumbering(), DrawUtil.getPos(i));
				
				if (number > 0) {
					box.setLink(this.chessboard.getBoxByNumbering(number), DrawUtil.getPos(i));
				}
			}
		}
	}
	
	/*
	 * Piece Alignment for 8 x 8 Chess board:
	 * 
	 * WHITE ------------------- 
	 * PAWN: 		49 - 56 
	 * ROOK: 		57, 64 
	 * KNIGHT: 		58, 63 
	 * BISHOP: 		59, 62 
	 * QUEEN: 		61 
	 * KING: 		60
	 * 
	 * BLACK ------------------- 
	 * PAWN: 		9 - 16 
	 * ROOK: 		1, 8 
	 * KNIGHT: 		2, 7 
	 * BISHOP: 		3, 6
	 * QUEEN: 		4 
	 * KING: 		5
	 * 
	 */
	private void createPieces(Colour colour) {
		if (colour == Colour.WHITE) {
			for (int i = 49; i <= 56; i++) {
				Piece pawn = new Pawn(Colour.WHITE);
				setPieceAndBox(i, pawn);
			}
			Piece rook1 = new Rook(Colour.WHITE);
			Piece rook2 = new Rook(Colour.WHITE);
			setPieceAndBox(57, rook1);
			setPieceAndBox(64, rook2);

			Piece knight1 = new Knight(Colour.WHITE);
			Piece knight2 = new Knight(Colour.WHITE);
			setPieceAndBox(58, knight1);
			setPieceAndBox(63, knight2);

			Piece bishop1 = new Bishop(Colour.WHITE);
			Piece bishop2 = new Bishop(Colour.WHITE);
			setPieceAndBox(59, bishop1);
			setPieceAndBox(62, bishop2);

			Piece queen = new Queen(Colour.WHITE);
			setPieceAndBox(61, queen);

			Piece king = new King(Colour.WHITE);
			setPieceAndBox(60, king);
			
		} else if (colour == Colour.BLACK) {
			for (int i = 9; i <= 16; i++) {
				Piece pawn = new Pawn(Colour.BLACK);
				setPieceAndBox(i, pawn); 
			}
			
			Piece rook1 = new Rook(Colour.BLACK);
			Piece rook2 = new Rook(Colour.BLACK);
			setPieceAndBox(1, rook1);
			setPieceAndBox(8, rook2); 

			Piece knight1 = new Knight(Colour.BLACK);
			Piece knight2 = new Knight(Colour.BLACK);
			setPieceAndBox(2, knight1); 
			setPieceAndBox(7, knight2); 

			Piece bishop1 = new Bishop(Colour.BLACK);
			Piece bishop2 = new Bishop(Colour.BLACK);
			setPieceAndBox(3, bishop1);
			setPieceAndBox(6, bishop2);

			Piece queen = new Queen(Colour.BLACK);
			setPieceAndBox(4, queen); 

			Piece king = new King(Colour.BLACK);
			setPieceAndBox(5, king);
		}
	}
	
	/**
	 * 
	 * @param box
	 * @param numbering
	 * @param pos
	 * @return 
	 * 		> 0 -> returns valid Box id
	 * 		< 0 -> returns invalid Box id (means we are out of bounds for that position)
	 */
	private Integer neighbourBoxNum(Box box, Integer numbering, Position pos) {
		Integer numCols = this.chessboard.getNumCols();
		Integer numRows = this.chessboard.getNumRows();

		Integer boxNum = -1; 
		
		if (pos.equals(Position.UP)) {
			boxNum = numbering - (numCols); 
		} else if (pos.equals(Position.DOWN)) {
			boxNum = numbering + (numCols);
			if (boxNum > (numCols * numRows))
				boxNum = -1;
		} else if (pos.equals(Position.LEFT)) {
			if (numbering % numCols != 1) {
				boxNum = numbering -1;
			} else {
				boxNum = -1;
			}
		} else if (pos.equals(Position.RIGHT)) {
			if (numbering % numCols != 0) {
				boxNum = numbering + 1;
			} else {
				boxNum = -1;
			}
		} else if (pos.equals(Position.UP_RIGHT)) {
			boxNum = numbering - (numCols - 1); 
			
			if (boxNum > ((int) ( (int) (numbering / numRows) * numRows)) || (numbering % numRows == 0 ) || (numbering / numRows == 0)) {
				boxNum = -1;
			}
		} else if (pos.equals(Position.UP_LEFT)) {
			boxNum = numbering - (numCols + 1);
			
			// prevent when number at the rightmost side, division becomes whole, so will take the next row as reference
			if (numbering % numCols == 0) {
				numbering -= 1; 
			}
			if (boxNum <= ((int) (((int) (numbering - numCols) / numRows) * numRows)) || (numbering / numRows == 0)) {
				boxNum = -1;
			}
		} else if (pos.equals(Position.DOWN_RIGHT)) {
			boxNum = numbering + (numCols + 1); 
			
			// prevent when number at the rightmost side, division becomes whole, so will take the next row as reference
			if (numbering % numCols == 0) {
				numbering -= 1; 
			}
			if (boxNum > (numCols * numRows) || boxNum > (((int) ((numbering + 2 * numCols) / numRows)) * numRows)) {
				boxNum = -1;
			}
		} else if (pos.equals(Position.DOWN_LEFT)) {
			boxNum = numbering + (numCols - 1); 
			
			// prevent when number at the rightmost side, division becomes whole, so will take the next row as reference
			if (numbering % numCols == 0) {
				numbering -= 1; 
			}
			if (boxNum > (numCols * numRows) || boxNum <= ( ( (int) ((numbering + numCols) / numRows ) ) * numRows) ) {
				boxNum = -1;
			}
		}
		
		return boxNum; 
	}
	
	private Box getBox(Integer numbering) {
		Location loc = this.chessboard.getLocationMap().get(numbering);
		return (this.chessboard.getChessboard().get(loc.getY()).get(loc.getX()));
	}
	
	private Box createBox(int x, int y, int width, int height, int numbering) {
		Box box = new Box();
		box.setHeight(height);
		box.setWidth(width);
		box.setLocation(new Location(x, y));
		box.setNumbering(numbering);
		return box;
	}
	
	private void setPieceAndBox(Integer numbering, Piece piece) {
		Box box = getBox(numbering);
		box.setPiece(piece);
		piece.setBox(box);
		piece.setPosition(box.getNumbering());
		if (piece.getColour().equals(Colour.WHITE)) {
			chessboard.getWhitePieceMultimap().put(piece.getPieceType(), piece); 
		} else {
			chessboard.getBlackPieceMultimap().put(piece.getPieceType(), piece); 
		}
	}
	
	
}
