package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import controller.ChessController;
import spring.Config;

public class Main {
	
	public static void main(String[] args) {	
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		context.getBean(ChessController.class).initialize();
	}
}
