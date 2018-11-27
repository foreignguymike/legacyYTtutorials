package com.neet.entity.enemies;

import java.awt.Color;
import java.awt.Graphics2D;

import com.neet.entity.Enemy;
import com.neet.managers.Animation;
import com.neet.managers.Content;
import com.neet.tilemap.TileMap;

public class EnergyGate extends Enemy {
	
	private int gateHeight;
	
	private Animation topGate;
	private Animation energy;
	private Animation bottomGate;
	
	public EnergyGate(TileMap tm) {
		
		super(tm, null, null);
		
		health = maxHealth = Integer.MAX_VALUE;
		width = height = 32;
		flinchDelay = 5;
		
		topGate = new Animation();
		topGate.setFrames(Content.ENERGY_GATE[0], 2);
		energy = new Animation();
		energy.setFrames(Content.ENERGY_GATE[1], 2);
		bottomGate = new Animation();
		bottomGate.setFrames(Content.ENERGY_GATE[2], 2);
		
	}
	
	public EnergyGate(TileMap tm, double x, double y, int gateHeight) {
		this(tm);
		this.x = x;
		this.y = y;
		this.gateHeight = gateHeight;
		cwidth = width;
		cheight = gateHeight;
	}
	
	public void hit() {
		if(dead || flinching) return;
		flinching = true;
		flinchCount = 0;
	}
	
	public void hit(int damage) {
		return;
	}
	
	public void update() {
		super.update();
		topGate.update();
		energy.update();
		bottomGate.update();
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		if(flinching) {
			g.setXORMode(Color.RED);
		}
		g.drawImage(topGate.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - gateHeight / 2 - 1), null);
		g.drawImage(bottomGate.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap + gateHeight / 2 - height + 1), null);
		g.setPaintMode();
	}
	
}
