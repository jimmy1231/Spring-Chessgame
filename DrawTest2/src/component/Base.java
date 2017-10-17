package component;

import javax.swing.JPanel;

public class Base extends JPanel {

	protected int length;
	
	
	protected int width;
	
	
	protected int numRows;
	
	
	protected int numCols;
	

	protected int cellWidth;
	
	
	protected int cellHeight;
	
	
	/**
	 * Change chessboard display properties here:
	 * 
	 * @author Jimmy Li
	 */
	public Base() {
		this.length = 700;
		this.width = 700;
		this.numRows = 8;
		this.numCols = 8;
		this.cellWidth = this.length / this.numCols;
		this.cellHeight = this.width / this.numRows;
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getNumRows() {
		return numRows;
	}
	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}
	public int getNumCols() {
		return numCols;
	}
	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}
	public int getCellWidth() {
		return cellWidth;
	}
	public void setCellWidth(int cellWidth) {
		this.cellWidth = cellWidth;
	}
	public int getCellHeight() {
		return cellHeight;
	}
	public void setCellHeight(int cellHeight) {
		this.cellHeight = cellHeight;
	}
}
