package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import component.Canvas;
import component.ChessboardFrame;
import enums.Colour;
import service.ChessboardService;

@Controller
public class ChessController {
	
	@Autowired 
	private ChessboardFrame gui; 
	
	@Autowired
	private ClickMoveController clickMoveController;
	
	@Autowired
	private ChessboardService chessboardService; 
	
	@Autowired 
	private Canvas canvas; 
		
	public void initialize() {
		chessboardService.initialize();
		gui.setSize(800, 800);
		canvas.setSize(800, 800);
		gui.setTitle("Chess Game");
		gui.add(canvas);
		
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				clickMoveController.determineAction(e.getX(), e.getY());
				
				canvas.repaint();
			}
		});
		
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
	}
}
