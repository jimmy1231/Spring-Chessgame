package component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Box;
import model.Location;
import service.ChessboardService;
import utils.DrawUtil;

@Component
public class Canvas extends Base {
	
	private List<Location> userClickedList = new ArrayList<>();
	
	@Autowired
	private ChessboardService chessboardService; 
	
	@Autowired
	private Chessboard chess; 
	
	public void setChessboardService(ChessboardService chessboardService) {
		this.chessboardService = chessboardService;
	}
	public ChessboardService getChessboardService() {
		return this.chessboardService;
	}
	public void setChessboard(Chessboard chessboard) {
		this.chess = chessboard; 
	}
	public Chessboard getChessboard() {
		return this.chess;
	}
	public List<Location> getUserClickedList() {
		return userClickedList;
	}
	public void setUserClickedList(List<Location> userClickedList) {
		this.userClickedList = userClickedList;
	}

	/**
	 * main method for drawing write private method below, then insert method call
	 * here.
	 * 
	 * @param g
	 *       Graphics parameter for drawing
	 */
	private void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		this.cellWidth = length / numCols;
		this.cellHeight = width / numRows;

		if (chessboardService.getCheckmate()) {
			drawWinScreen(g2);
		} else {
			drawChessboard(g2);
		}
	}

	/**
	 * Draw Chessboard
	 * 
	 * @param g2
	 *      casted Graphics2D enable additional graphics API
	 */
	private void drawChessboard(Graphics2D g2) {
		int currXPos = 0;
		int currYPos = 0;

		int alternate_inner = 0;
		int alternate_outer = 0;

		for (int i = 0; i < numRows; i++) {
			currYPos = i * cellHeight;

			for (int j = 0; j < numCols; j++) {
				currXPos = j * cellWidth;
				
				Box box = this.chess.getChessboard().get(currYPos).get(currXPos);

				if (alternate_inner == 0)
					g2.setColor(new Color(224, 192, 104));
				else
					g2.setColor(new Color(184, 112, 48));
				
				g2.fillRect(currXPos, currYPos, cellWidth, cellHeight);
				
				if (box.isClicked()) {
					if (box.getPiece() != null) {
						g2.setColor(Color.lightGray);
						g2.fillRect(currXPos, currYPos, cellWidth, cellHeight);
					}
					box.setClicked(false);
				}
				
				if (box.getPiece() != null) {
					BufferedImage img = box.getPiece().getImage();
					Location loc = DrawUtil.getCenteredImageLoc(box.getLocation(), img, cellWidth);
					g2.drawImage(img, loc.getX(), loc.getY(), null); 
				}
				
				if (box.getPiece() != null) {
					Location loc = DrawUtil.getCircleCenter(currXPos, currYPos, cellWidth); 
					g2.setColor(Color.GREEN);
					g2.drawString(box.getPiece().getPieceType().toString(), loc.getX(), loc.getY());
				}
				
				alternate_inner = (alternate_inner == 0) ? 1 : 0;
			}

			alternate_outer = (alternate_outer == 0) ? 1 : 0;
			alternate_inner = (alternate_outer == 0) ? 0 : 1;
		}
		
		/*
		 * Drawing available moves so it does not get overlayed with box drawing.
		 */
		for (int i = 0; i < numRows; i++) {
			currYPos = i * cellHeight;

			for (int j = 0; j < numCols; j++) {
				currXPos = j * cellWidth;
				Box box = this.chess.getChessboard().get(currYPos).get(currXPos);
				
				if (box.isNextMove()) {
					if (box.getPiece() != null) {
						g2.setColor(Color.RED);
					} else {
						g2.setColor(Color.GREEN);
					}
					g2.setStroke(new BasicStroke(2));
					g2.drawRect(currXPos, currYPos, cellWidth, cellHeight);
					
					box.setNextMove(false);
				}
			}
		}
				
	}
	
	private void drawWinScreen(Graphics2D g2) {
		g2.setColor(Color.GREEN);
		g2.drawString("GAME OVER!", 0, 0);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
}
