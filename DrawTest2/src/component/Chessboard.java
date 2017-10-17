package component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import enums.Colour;
import enums.Pieces;
import model.Box;
import model.Location;
import pieces.Piece;

@Component
public class Chessboard extends Base {
	
	private Map<Integer, Map<Integer, Box>> chessboard;
	private Map<Integer, Location> locationMap;
	private Set<Piece> closedSetBlack;
	private Set<Piece> closedSetWhite;
	private ListMultimap<Pieces, Piece> whitePieceMultimap;
	private ListMultimap<Pieces, Piece> blackPieceMultimap;
	
	public Chessboard() {
		chessboard = new HashMap<Integer, Map<Integer, Box>>();
		locationMap = new HashMap<Integer, Location>();
		closedSetBlack = new HashSet<Piece>();
		closedSetWhite = new HashSet<Piece>();
		whitePieceMultimap = ArrayListMultimap.create();
		blackPieceMultimap = ArrayListMultimap.create();
	}
	
	
	public ListMultimap<Pieces, Piece> getWhitePieceMultimap() {
		return whitePieceMultimap;
	}
	public void setWhitePieceMultimap(ListMultimap<Pieces, Piece> whitePieceMultimap) {
		this.whitePieceMultimap = whitePieceMultimap;
	}
	public ListMultimap<Pieces, Piece> getBlackPieceMultimap() {
		return blackPieceMultimap;
	}
	public void setBlackPieceMultimap(ListMultimap<Pieces, Piece> blackPieceMultimap) {
		this.blackPieceMultimap = blackPieceMultimap;
	}
	public Set<Piece> getClosedSetBlack() {
		return closedSetBlack;
	}
	protected void setClosedSetBlack(Set<Piece> closedSetBlack) {
		this.closedSetBlack = closedSetBlack;
	}
	public Set<Piece> getClosedSetWhite() {
		return closedSetWhite;
	}
	protected void setClosedSetWhite(Set<Piece> closedSetWhite) {
		this.closedSetWhite = closedSetWhite;
	}
	public Map<Integer, Map<Integer, Box>> getChessboard() {
		return chessboard;
	}
	protected void setChessboard(Map<Integer, Map<Integer, Box>> chessboard) {
		this.chessboard = chessboard;
	}
	public Map<Integer, Location> getLocationMap() {
		return locationMap;
	}
	protected void setLocationMap(Map<Integer, Location> locationMap) {
		this.locationMap = locationMap;
	}
	
	public List<Box> getChessboardAsList() {
		
		List<Box> list = new ArrayList<Box>(); 
		Iterator<?> outer = this.chessboard.entrySet().iterator();
		
		while (outer.hasNext()) {
			Entry<Integer, Map<Integer, Box>> outer_pair = (Entry<Integer, Map<Integer, Box>>) outer.next(); 	
			Iterator<?> inner = ((Map<Integer, Box>) outer_pair.getValue()).entrySet().iterator(); 
			
			while (inner.hasNext()) {
				Entry<Integer, Map<Integer, Box>> inner_pair = (Entry<Integer, Map<Integer, Box>>) inner.next(); 		
				list.add((Box) inner_pair.getValue()); 
			}
		}
		
		return list;
	}
	
	public List<Box> getAllBoxesByColour(Colour colour, ArrayList<Pieces> exceptions) {
		List<Box> boxList = new ArrayList<Box>();
		
		Iterator<?> outer = this.chessboard.entrySet().iterator();
		
		while (outer.hasNext()) {
			Entry<Integer, Map<Integer, Box>> outer_pair = (Entry<Integer, Map<Integer, Box>>) outer.next(); 	
			Iterator<?> inner = ((Map<Integer, Box>) outer_pair.getValue()).entrySet().iterator(); 
			
			while (inner.hasNext()) {
				Entry<Integer, Map<Integer, Box>> inner_pair = (Entry<Integer, Map<Integer, Box>>) inner.next(); 
				Box box = (Box) inner_pair.getValue(); 
				if (box.getPiece() != null) {
					if (box.getPiece().getColour().equals(colour)) {
						boolean isExcepted = false; 
						
						for (Pieces exception : exceptions ) {
							if (box.getPiece().getPieceType().equals(exception)) {
								isExcepted = true;
								break;
							}
						}
						if (!isExcepted) 
							boxList.add((Box) inner_pair.getValue()); 
					}
				}
			}
		}
		return boxList;
	}
	
	public void removeFromMultimap(Box box) {
		
		Piece piece = box.getPiece(); 
		List<Piece> pieceList = null; 
		if (piece.getColour().equals(Colour.WHITE)) {
			pieceList = this.whitePieceMultimap.get(piece.getPieceType());
			
		} else {
			pieceList = this.blackPieceMultimap.get(piece.getPieceType()); 
		}
		
		for (int i = 0; i < pieceList.size(); i ++ ) {
			Piece p = pieceList.get(i);
			if (p.getPosition() == piece.getPosition()) {
				pieceList.remove(i);
			}
		}
	}
	
	public Box getBoxByNumbering(Integer numbering) {
		Location loc = this.locationMap.get(numbering);
		return (this.chessboard.get(loc.getY()).get(loc.getX())); 
	}
	
	public Box getBoxByLocation(Location loc) {
		return (this.chessboard.get(loc.getY()).get(loc.getX()));
	}
	
	public void setClosedSet(Piece piece, Colour colour) {
		if (colour.equals(Colour.BLACK))
			closedSetBlack.add(piece);
		else
			closedSetWhite.add(piece);
	}
	
	public Box getKingBox(Colour colour) {
		Box box = null; 
		
		List<Box> allBoxes = this.getAllBoxesByColour(colour, new ArrayList<Pieces>());
		
		for (Box iterBox : allBoxes) {
			Pieces pieceType = iterBox.getPiece().getPieceType();
			if (pieceType.equals(Pieces.KING)) {
				box = iterBox;
				break;
			}
		}
		return box;
	}
	
}
