package utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import enums.Position;
import model.Box;

public class ChessboardTest {

	
	/**
	 * this is a test function to print out the chessboard
	 */
	public static void printPieces(Map<Integer, Map<Integer, Box>> chessboard) {
		Iterator outer = chessboard.entrySet().iterator();
		while(outer.hasNext()) {
			Entry outer_pair = (Entry) outer.next(); 
			Iterator inner = ((Map<Integer, Box>) outer_pair.getValue()).entrySet().iterator(); 
			System.out.println();
			while (inner.hasNext()) {
				Entry inner_pair = (Entry) inner.next(); 
				Box box = (Box) inner_pair.getValue();
				
				if (box.getPiece() != null) {
					System.out.print(box.getNumbering() + ": " + box.getPiece().getPieceType() + " |  ");
				} else {
					System.out.print(box.getNumbering() + ": x |  ");
				}
			}
		}
	}
	
	/**
	 * Print out links for chessboard 
	 * 
	 * @param chessboard
	 */
	public static void printLinks(Map<Integer, Map<Integer, Box>> chessboard) {
		Iterator outer = chessboard.entrySet().iterator();
		while(outer.hasNext()) {
			Entry outer_pair = (Entry) outer.next(); 
			Iterator inner = ((Map<Integer, Box>) outer_pair.getValue()).entrySet().iterator(); 
			System.out.println();
			while (inner.hasNext()) {
				Entry inner_pair = (Entry) inner.next(); 
				Box box = (Box) inner_pair.getValue();
				System.out.println("------------------------");
				System.out.println(box.getNumbering() + ": ");
				
				if (box.getUp() != null) {
					System.out.println(Position.UP.toString() + ": " + box.getUp().getNumbering());
				}
				if (box.getDown() != null) {
					System.out.println(Position.DOWN.toString() + ": " + box.getDown().getNumbering());
				}
				if (box.getLeft() != null) {
					System.out.println(Position.LEFT.toString() + ": " + box.getLeft().getNumbering());
				}
				if (box.getRight() != null) {
					System.out.println(Position.RIGHT.toString() + ": " + box.getRight().getNumbering());
				}
				if (box.getUpRight() != null) {
					System.out.println(Position.UP_RIGHT.toString() + ": " + box.getUpRight().getNumbering());
				}
				if (box.getUpLeft() != null) {
					System.out.println(Position.UP_LEFT.toString() + ": " + box.getUpLeft().getNumbering());
				}
				if (box.getDownRight() != null) {
					System.out.println(Position.DOWN_RIGHT.toString() + ": " + box.getDownRight().getNumbering());
				}
				if (box.getDownLeft() != null) {
					System.out.println(Position.DOWN_LEFT.toString() + ": " + box.getDownLeft().getNumbering());
				}
			}
		}
	}
}
