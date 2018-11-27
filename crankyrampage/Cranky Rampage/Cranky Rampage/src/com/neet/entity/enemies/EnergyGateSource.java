package com.neet.entity.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.neet.entity.Enemy;
import com.neet.entity.Platform;
import com.neet.entity.Player;
import com.neet.managers.Content;
import com.neet.tilemap.TileMap;

public class EnergyGateSource extends Enemy {
	
	private EnergyGate energyGate;
	
	public EnergyGateSource(TileMap tm, ArrayList<Platform> p, Player pl, EnergyGate eg) {
		
		super(tm, p, pl);
		
		energyGate = eg;
		
		width = height = cwidth = cheight = 32;
		flinchDelay = 5;
		
		health = maxHealth = 15;
		damage = 1;
		score = 50;
		rampage = 5;
		
		animation.setFrames(Content.ENERGY_GATE_SOURCE[0], 2);
		
	}
	
	public EnergyGateSource(TileMap tm, ArrayList<Platform> p, Player pl, EnergyGate eg, double x, double y) {
		this(tm, p, pl, eg);
		this.x = x;
		this.y = y;
	}
	
	public void hit(int damage) {
		super.hit(damage);
		energyGate.hit();
		if(dead) energyGate.setDead();
	}
	
	public void update() {
		super.update();
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		if(flinching) {
			g.setXORMode(Color.RED);
		}
		super.draw(g);
		g.setPaintMode();
	}
	
}
