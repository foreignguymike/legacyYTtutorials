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

public class Sentry extends Enemy {
	
	private int state;
	private final int MOVING_TO_PLAYER = 0;
	private final int SHOOTING = 1;
	private final int COOLDOWN = 2;
	
	private int shootingRange;
	
	private int coolDownTimer;
	private int coolDownTime;
	
	private final int IDLE = 0;
	private final int MOVING = 1;
	
	public Sentry(TileMap tm, ArrayList<Platform> p, Player pl) {
		
		super(tm, p, pl);
		
		width = height = 32;
		cwidth = 20;
		cheight = 28;
		right = true;
		
		maxSpeed = 0.8;
		fallSpeed = 0.8;
		maxFallSpeed = 4;
		flinchDelay = 5;
		
		health = maxHealth = 5;
		damage = 1;
		score = 5;
		rampage = 1;
		
		state = MOVING_TO_PLAYER;
		shootingRange = 150;
		
		coolDownTime = 150;
		coolDownTimer = 0;
		
		currentAction = MOVING;
		animation.setFrames(Content.SENTRY[MOVING], 2);
	
	}
	
	public Sentry(TileMap tm, ArrayList<Platform> p, Player pl, double x, double y) {
		this(tm, p, pl);
		this.x = x;
		this.y = y;
	}
	
	private void getNextPosition() {
		if(left) {
			dx = -maxSpeed;
		}
		else if(right) {
			dx = maxSpeed;
		}
		else {
			dx = 0;
		}
		if(falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
			dx = 0;
		}
	}
	
	public void update() {
		
		super.update();
		
		if(state == MOVING_TO_PLAYER) {
			if(player.getx() < x) {
				left = true;
				right = facingRight = false;
			}
			if(player.getx() > x) {
				left = false;
				right = facingRight = true;
			}
			if(Math.abs(player.getx() - x) < shootingRange &&
				Math.abs(player.gety() - y) < 10) {
				state = SHOOTING;
			}
		}
		else if(state == SHOOTING) {
			left = right = false;
			if(player.getx() < x) {
				GameObjectFactory.createEnemyBullet(x - 13, y - 6, -2, 0);
				facingRight = false;
			}
			else {
				GameObjectFactory.createEnemyBullet(x, y - 6, 2, 0);
				facingRight = true;
			}
			state = COOLDOWN;
		}
		else if(state == COOLDOWN) {
			coolDownTimer++;
			if(coolDownTimer > coolDownTime) {
				coolDownTimer = 0;
				if(Math.abs(player.getx() - x) < shootingRange) {
					state = SHOOTING;
				}
				else {
					state = MOVING_TO_PLAYER;
				}
			}
		}
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(state == MOVING_TO_PLAYER) {
			if(currentAction != MOVING) {
				currentAction = MOVING;
				animation.setFrames(Content.SENTRY[MOVING], 2);
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(Content.SENTRY[IDLE], 2);
			}
		}
		animation.update();
		
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