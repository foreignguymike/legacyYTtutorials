// GameState that shows logo.

package com.neet.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.main.GamePanel;
import com.neet.managers.Content;
import com.neet.managers.GameStateManager;
import com.neet.managers.Keys;

public class IntroState extends GameState {
	
	private BufferedImage logo;
	private int logow;
	private int logoh;
	private int logox;
	private int logoy;
	
	private int alpha;
	private int ticks;
	
	private final int FADE_IN = 90;
	private final int LENGTH = 90;
	private final int FADE_OUT = 90;
	
	public IntroState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		ticks = 0;
		
		logo = Content.LOGO[0][0];
		logow = logo.getWidth() * 2;
		logoh = logo.getHeight() * 2;
		logox = (GamePanel.WIDTH - logow) / 2;
		logoy = (GamePanel.HEIGHT - logoh) / 2;
		
	}
	
	public void update() {
		handleInput();
		ticks++;
		if(ticks < FADE_IN) {
			alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
			if(alpha < 0) alpha = 0;
		}
		if(ticks > FADE_IN + LENGTH) {
			alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
			if(alpha > 255) alpha = 255;
		}
		if(ticks > FADE_IN + LENGTH + FADE_OUT) {
			gsm.setState(GameStateManager.MENU);
		}
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.drawImage(logo, logox, logoy, logow, logoh, null);
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) {
			gsm.setState(GameStateManager.MENU);
		}
	}
	
}