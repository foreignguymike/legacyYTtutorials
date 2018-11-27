package com.neet.entity.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.neet.entity.Enemy;
import com.neet.entity.Platform;
import com.neet.entity.Player;
import com.neet.managers.Content;
import com.neet.managers.GameObjectFactory;
import com.neet.tilemap.TileMap;

public class Sensor extends Enemy {
	
	private int range;
	private int timer;
	private int delay;
	
	public Sensor(TileMap tm, ArrayList<Platform> p, Player pl) {
		
		super(tm, p, pl);
		
		cwidth = cheight = width = height = 25;
		animation.setFrames(Content.SENSOR[0], 2);
		
		flinchDelay = 5;
		
		health = maxHealth = 8;
		damage = 1;
		score = 5;
		rampage = 2;
		
		range = 200;
		delay = 150;
		timer = delay;
		
	}
	
	public Sensor(TileMap tm, ArrayList<Platform> p, Player pl, double x, double y) {
		this(tm, p, pl);
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		
		super.update();
		animation.update();
		
		if(player.getx() > x - range && player.getx() < x + range && player.gety() > y) {
			timer++;
			if(timer >= delay) {
				timer = 0;
				GameObjectFactory.createEnemyBullet(x, y, -1.41, 1.41);
				GameObjectFactory.createEnemyBullet(x, y, 0, 2);
				GameObjectFactory.createEnemyBullet(x, y, 1.41, 1.41);
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		if(flinching) {
			g.setXORMode(Color.RED);
		}
		super.draw(g);
		g.setPaintMode();
		
		/*if(health < maxHealth) {
			g.setColor(java.awt.Color.RED);
			g.fillRect((int)(x + xmap - 10), (int)(y + ymap - height), 20, 2);
			g.setColor(java.awt.Color.GREEN);
			g.fillRect((int)(x + xmap - 10), (int)(y + ymap - height), (int) (20.0 * health / maxHealth), 2);
		}*/
		
	}
	
}