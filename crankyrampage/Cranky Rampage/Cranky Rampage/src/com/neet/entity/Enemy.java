package com.neet.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.neet.tilemap.TileMap;

public class Enemy extends MapObject {
	
	protected Player player;
	protected int rampage;
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	protected boolean remove;
	
	protected boolean flinching;
	protected int flinchCount;
	protected int flinchDelay;
	
	protected int score;
	
	public Enemy(TileMap tm, ArrayList<Platform> p, Player pl) {
		super(tm, p);
		player = pl;
		remove = false;
	}
	
	public int getRampage() { return rampage; }
	public int getDamage() { return damage; }
	public int getScore() { return score; }
	public boolean isDead() { return dead; }
	public boolean isFlinching() { return flinching; }
	public boolean shouldRemove() { return remove; }
	
	public void setDead() {
		health = 0;
		dead = true;
		if(dead) remove = true;
	}
	
	public void hit(int damage) {
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		if(dead) remove = true;
		flinching = true;
		flinchCount = 0;
	}
	
	public void update() {
		if(flinching) {
			flinchCount++;
			if(flinchCount > flinchDelay) {
				flinching = false;
			}
		}
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}