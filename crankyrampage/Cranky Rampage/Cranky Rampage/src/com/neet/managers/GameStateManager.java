package com.neet.managers;

import com.neet.gamestates.DemoAct1;
import com.neet.gamestates.DemoAct3;
import com.neet.gamestates.GameState;
import com.neet.gamestates.IntroState;
import com.neet.gamestates.MenuState;
import com.neet.gamestates.PauseState;
import com.neet.main.GamePanel;


public class GameStateManager {
	
	private GameState gameState;
	
	private PauseState pauseState;
	private boolean paused;
	
	public static final int LOGO = 0;
	public static final int MENU = 1;
	public static final int INTRO = 2;
	public static final int DEMOACT1 = 3;
	public static final int DEMOACT3 = 5;
	
	public GameStateManager() {
		
		JukeBox.init();
		
		pauseState = new PauseState(this);
		paused = false;
		
		setState(LOGO);
		
	}
	
	public void setState(int state) {
		if(state == LOGO) {
			gameState = new IntroState(this);
		}
		else if(state == MENU) {
			gameState = new MenuState(this);
		}
		else if(state == DEMOACT1) {
			gameState = new DemoAct1(this);
		}
		else if(state == DEMOACT3) {
			gameState = new DemoAct3(this);
		}
		gameState.init();
	}
	
	public void setPaused(boolean b) { paused = b; }
	
	public void update() {
		if(paused) {
			pauseState.update();
			return;
		}
		if(gameState != null) gameState.update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
			return;
		}
		if(gameState != null) gameState.draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
	
}