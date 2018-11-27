package com.neet.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.neet.managers.JukeBox;
import com.neet.tilemap.TileMap;

public class Platform extends MapObject {
	
	private int health;
	private boolean invulnerable;
	
	private boolean remove;
	
	private int type;
	public static final int STATIC = 0;
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;
	
	private int xmin;
	private int xmax;
	private int ymin;
	private int ymax;
	
	public Platform(TileMap tm, ArrayList<Platform> p) {
		
		super(tm, p);
		
		health = 5;
		invulnerable = false;
		
		width = 40;
		height = 20;
		cwidth = 40;
		cheight = 20;
		
		moveSpeed = 1;
		
	}
	
	public void setType(int i) { type = i; }
	public void setType(int i, int i1, int i2) {
		type = i;
		if(i == HORIZONTAL) {
			xmin = i1;
			xmax = i2;
			right = true;
		}
		if(i == VERTICAL) {
			ymin = i1;
			ymax = i2;
			up = true;
		}
	}
	public void setDimensions(int i1, int i2) { width = i1; height = i2; }
	public void setCDimensions(int i1, int i2) { cwidth = i1; cheight = i2; }
	public void setSpeed(double d) { moveSpeed = d; }
	
	public void update() {
		
		if(type == HORIZONTAL) {
			if(x > xmax) {
				right = false;
				left = true;
			}
			if(x < xmin) {
				right = true;
				left = false;
			}
		}
		if(type == VERTICAL) {
			if(y > ymax) {
				up = true;
				down = false;
			}
			if(y < ymin) {
				up = false;
				down = true;
			}
		}
		
		if(right) dx = moveSpeed;
		if(left) dx = -moveSpeed;
		if(up) dy = -moveSpeed;
		if(down) dy = moveSpeed;
		
		x += dx;
		y += dy;
		
		// update animation
		animation.update();
		
	}
	
	public void hit(int i) {
		if(invulnerable) return;
		JukeBox.play("enemyhit");
		health -= i;
		if(health <= 0) {
			health = 0;
			remove = true;
		}
	}
	
	public boolean shouldRemove() { return remove; }
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		g.setColor(java.awt.Color.BLACK);
		g.fillRect((int)(x + xmap - cwidth / 2), (int)(y + ymap - cheight / 2), cwidth, cheight);
		
	}
	
}
