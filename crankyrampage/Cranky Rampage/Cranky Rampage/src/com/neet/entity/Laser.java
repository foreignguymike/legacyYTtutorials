package com.neet.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.neet.tilemap.TileMap;

public class Laser extends Enemy {
	
	private int timer;
	private Color color;
	
	private final int LOCK_ON = 300;
	private final int CHARGING = LOCK_ON + 60;
	private final int FIRING = CHARGING + 180;
	private final int COOLDOWN = FIRING + 60;
	
	public Laser(TileMap tm, ArrayList<Platform> p, Player pl) {
		super(tm, p, pl);
		width = 60;
		height = 16;
		timer = 0;
	}
	
	public void update() {
		timer++;
		if(timer < LOCK_ON) {
			color = Color.GREEN;
			x += (player.getx() - x) * 0.07;
		}
		else if(timer < CHARGING) {
			color = Color.ORANGE;
		}
		else if(timer < FIRING) {
			color = Color.RED;
		}
		else if(timer < COOLDOWN) {
			color = Color.CYAN;
		}
		else {
			timer = 0;
		}
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		g.setColor(color);
		g.drawRect((int) (x + xmap - width / 2), (int) (y + ymap - height / 2), width, height);
	}
	
}
