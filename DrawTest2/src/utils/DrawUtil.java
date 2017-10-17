package utils;

import java.awt.image.BufferedImage;

import enums.Position;
import model.Location;

public class DrawUtil {

	public static Location getCircleCenter(int x, int y, int cellWidth) {
		Location loc = new Location(); 
		loc.setX((x + x + cellWidth) / 2);
		loc.setY((y + y + cellWidth) / 2); 
		return loc;
	}
	
	public static Location getCenteredImageLoc(Location boxLoc, BufferedImage img, int cellWidth) {
		boxLoc = getCircleCenter(boxLoc.getX(), boxLoc.getY(), cellWidth); 	
		
		int x = boxLoc.getX() - (img.getWidth() / 2); 
		int y = boxLoc.getY() - (img.getHeight() / 2); 
		
		Location loc = new Location(); 
		loc.setX(x);
		loc.setY(y);
		
		return loc;
	}
	
	public static Position getPos(Integer i) {
		if (i == 0) {
			return Position.UP; 
		} else if (i == 1) {
			return Position.DOWN; 
		} else if (i == 2) {
			return Position.LEFT; 
		} else if (i == 3) {
			return Position.RIGHT; 
		} else if (i == 4) {
			return Position.UP_RIGHT; 
		} else if (i == 5) {
			return Position.UP_LEFT; 
		} else if (i == 6) {
			return Position.DOWN_RIGHT; 
		} else {
			return Position.DOWN_LEFT; 
		}
	}
}
