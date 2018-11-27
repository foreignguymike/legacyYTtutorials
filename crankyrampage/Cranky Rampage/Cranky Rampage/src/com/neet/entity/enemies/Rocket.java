package com.neet.entity.enemies;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import com.neet.entity.Enemy;
import com.neet.entity.Platform;
import com.neet.entity.Player;
import com.neet.managers.Content;
import com.neet.tilemap.TileMap;

public class Rocket extends Enemy {
	
	private double radians;
	private double destRadians;
	private int rotateFactor;
	
	private AffineTransform tx;
	
	private int timer;
	private int time;
	
	public Rocket(TileMap tm, ArrayList<Platform> p, Player pl, double x, double y) {
		
		super(tm, p, pl);
		
		this.x = x;
		this.y = y;
		
		tx = new AffineTransform();
		
		maxSpeed = 1.8;
		score = 5;
		
		width = 7;
		height = 15;
		cwidth = cheight = 15;
		
		animation.setFrames(Content.ENEMY_ROCKET, 2);
		
		radians = -3.1415 / 2;
		rotateFactor = 60;
		
		health = maxHealth = 1;
		
		timer = 0;
		time = 600;
		
		damage = 1;
		
	}
	
	public void update() {
		
		super.update();
		
		timer++;
		if(timer > time) {
			dead = remove = true;
		}
		
		destRadians = Math.atan2(player.gety() - y, player.getx() - x);
		double deltaRadians = destRadians - radians;
		if(deltaRadians > 3.1415) {
			deltaRadians -= 3.1415 * 2;
		}
		else if(deltaRadians < -3.1415) {
			deltaRadians += 3.1415 * 2;
		}
		radians += deltaRadians / rotateFactor;
		if(radians > 3.1415 * 2) radians -= 3.1415 * 2;
		if(radians < -3.1415 * 2) radians += 3.1415 * 2;
		
		dx = Math.cos(radians) * maxSpeed;
		dy = Math.sin(radians) * maxSpeed;
		
		x += dx;
		y += dy;
		
		if(checkTileMapCollision()) {
			dead = remove = true;
		}
		
		if(intersects(player)) {
			dead = remove = true;
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		tx.setToIdentity();
		tx.translate(x + xmap, y + ymap);
		tx.rotate(3.14/2 + radians);
		tx.translate(-width / 2, -height / 2);
		
		g.drawImage(animation.getImage(), tx, null);
		
	}
	
}
