package com.neet.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import com.neet.main.GamePanel;
import com.neet.managers.Content;
import com.neet.tilemap.TileMap;

public class Projectile extends MapObject {
	
	private double speed;
	private double diagSpeed;
	private int damage;
	private int recoil;
	
	private double angle;
	private int direction;
	private AffineTransform tx;
	private int offset;
	
	private boolean remove;
	
	public Projectile(
			TileMap tm,
			ArrayList<Platform> platforms,
			double x,
			double y,
			int direction,
			int type) {
		
		super(tm, platforms);
		
		this.x = x;
		this.y = y;
		this.direction = direction;
		angle = Math.toRadians(-90 + direction * 45);
		
		cwidth = cheight = 15;
		
		if(type == Weapon.MACHINE_GUN) {
			speed = 12;
			damage = 1 * Player.damageMultiplier;
			recoil = 10;
			animation.setFrames(Content.MG_BULLETS, 1);
			width = Content.MG_BULLETS[0].getWidth();
			height = Content.MG_BULLETS[0].getHeight();
			offset = 20;
		}
		else if(type == Weapon.SPREADER) {
			speed = 8;
			damage = 1 * Player.damageMultiplier;
			recoil = 0;
			animation.setFrames(Content.MG_BULLETS, 1);
			width = Content.MG_BULLETS[0].getWidth();
			height = Content.MG_BULLETS[0].getHeight();
			offset = 20;
		}
		else if(type == Weapon.HIGH_CALIBER) {
			speed = 12;
			damage = 5 * Player.damageMultiplier;
			recoil = 0;
			animation.setFrames(Content.MG_BULLETS, 1);
			width = Content.MG_BULLETS[0].getWidth();
			height = Content.MG_BULLETS[0].getHeight();
			offset = 10;
		}
		else if(type == Weapon.FLAMING_MACHINE_GUN) {
			speed = 14;
			damage = 2 * Player.damageMultiplier;
			recoil = 10;
			animation.setFrames(Content.FMG_BULLETS, 1);
			width = Content.FMG_BULLETS[0].getWidth();
			height = Content.FMG_BULLETS[0].getHeight();
			offset = 20;
		}
		diagSpeed = speed / 1.41;
		
		if(recoil == 0) {
			setDirection();
		}
		else {
			setRecoil();
		}
		
		setOffset(direction);
		tx = new AffineTransform();
		
	}
	
	public double getSpeed() { return speed; }
	public int getDamage() { return damage; }
	public double getRecoil() { return recoil; }
	
	private void setDirection() {
		if(direction == Player.LEFT) {
			dx = -speed;
		}
		else if(direction == Player.RIGHT) {
			dx = speed;
		}
		else if(direction == Player.UP) {
			dy = -speed;
		}
		else if(direction == Player.DOWN) {
			dy = speed;
		}
		else if(direction == Player.UPLEFT) {
			dx = -diagSpeed;
			dy = -diagSpeed;
		}
		else if(direction == Player.UPRIGHT) {
			dx = diagSpeed;
			dy = -diagSpeed;
		}
		else if(direction == Player.DOWNRIGHT) {
			dx = diagSpeed;
			dy = diagSpeed;
		}
		else if(direction == Player.DOWNLEFT) {
			dx = -diagSpeed;
			dy = diagSpeed;
		}
	}
	
	private void setRecoil() {
		int rand = (int) (Math.random() * recoil) - recoil / 2;
		angle += Math.toRadians(rand);
		dx = Math.cos(angle);
		dy = Math.sin(angle);
		dx *= speed;
		dy *= speed;
	}
	
	private void setOffset(int i) {
		int doffset = (int) (1.0 * offset / 1.41);
		if(i == Player.LEFT) {
			x -= offset;
		}
		else if(i == Player. RIGHT) {
			x += offset;
		}
		else if(i == Player. UP) {
			y -= offset;
		}
		else if(i == Player. DOWN) {
			y += offset;
		}
		else if(i == Player. UPLEFT) {
			x -= doffset;
			y -= doffset;
		}
		else if(i == Player. UPRIGHT) {
			x += doffset;
			y -= doffset;
		}
		else if(i == Player. DOWNLEFT) {
			x -= doffset;
			y += doffset;
		}
		else if(i == Player. DOWNRIGHT) {
			x += doffset;
			y += doffset;
		}
	}
	
	public void setAngle(int angle) {
		this.angle = Math.toRadians(angle);
		dx = Math.cos(this.angle);
		dy = Math.sin(this.angle);
		dx *= speed;
		dy *= speed;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		x += dx;
		y += dy;
		//if(x < 0 || x > tileMap.getWidth() || y < 0 || y > tileMap.getHeight()) {
		if(x < -tileMap.getx() || x > -tileMap.getx() + GamePanel.WIDTH ||
			y < -tileMap.gety() || y > -tileMap.gety() + GamePanel.HEIGHT) {
			hit();
		}
		if(tileMap.getType(
				(int)y / tileMap.getTileSize(),
				(int)x / tileMap.getTileSize()) == TileMap.BLOCKED) {
			hit();
		}
		if(tileMap.getType(
				(int)y / tileMap.getTileSize(),
				(int)x / tileMap.getTileSize()) == TileMap.LEDGE && dy > 0) {
			hit();
		}
	}
	
	public void hit() {
		remove = true;
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	public void draw(Graphics2D g) {
		
		tx = new AffineTransform();
		tx.translate(tileMap.getx() + x, tileMap.gety() + y);
		tx.rotate(1.57 + angle);
		tx.translate(-width / 2, -height / 2);
		
		g.drawImage(animation.getImage(), tx, null);
		
	}
	
}
