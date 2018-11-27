package com.neet.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;

import com.neet.main.GamePanel;
import com.neet.managers.GameStateManager;
import com.neet.managers.Keys;

public class MenuState extends GameState {
	
	private int currentChoice = 0;
	private String[] options = {
		"Start",
		"Quit"
	};
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
	}
	
	public void update() {
		
		handleInput();
		
	}
	
	public void draw(Graphics2D g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) g.setColor(java.awt.Color.RED);
			else g.setColor(java.awt.Color.WHITE);
			g.drawString(options[i], 100, 100 + i * 15);
		}
		
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < options.length - 1) {
				currentChoice++;
			}
		}
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > 0) currentChoice--;
		}
		if(Keys.isPressed(Keys.ENTER)) {
			select();
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.DEMOACT1);
		}
		else if(currentChoice == 1) {
			System.exit(0);
		}
	}
	
}










