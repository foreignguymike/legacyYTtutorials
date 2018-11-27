package com.neet.main;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game {
	
	private static JFrame window;
	
	public static void main(String[] args) {
		window = new JFrame("Cranky Rampage");
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	public static void setCursorVisible(boolean b) {
		if(b) {
			window.setCursor(null);
			return;
		}
		BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(bi, new Point(0, 0), ".");
		window.setCursor(c);
	}
	
}
