package com.neet.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.neet.tilemap.TileMap;

public class Collectable extends MapObject {
	
	protected boolean active;
	protected int timer;
	
	protected boolean remove;
	
	public Collectable(TileMap tm, ArrayList<Platform> p) {
		super(tm, p);
		active = false;
	}
	
	public void setActive() {
		active = true;
		falling = true;
		timer = 0;
	}
	
	public void activate(Player p) {
		
	}
	
	protected void getNextPosition() {
		if(dx < 0) {
			dx += stopSpeed;
			if(dx > 0) dx = 0;
		}
		if(dx > 0) {
			dx -= stopSpeed;
			if(dx < 0) dx = 0;
		}
		if(!falling) {
			dx = 0;
		}
		if(ledgeFall || falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	public void update() {
		timer++;
		if(timer > 420) {
			remove = true;
		}
	}
	
	public void draw(Graphics2D g) {
		
	}
	
}
