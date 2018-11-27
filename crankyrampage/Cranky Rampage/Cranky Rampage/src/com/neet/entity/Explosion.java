package com.neet.entity;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.neet.managers.Content;
import com.neet.tilemap.TileMap;

public class Explosion extends MapObject {
	
	private boolean remove;
	
	private int type;
	public static final int GUN_FLASH = 0;
	public static final int GUN_HIT = 1;
	public static final int EXPLOSION = 2;
	
	private Point2D.Double[] points;
	private double speed;
	private double diagSpeed;
	
	public Explosion(TileMap tm, ArrayList<Platform> p, double x, double y, int type, int delay) {
		
		super(tm, p);
		
		this.x = x;
		this.y = y;
		this.type = type;
		
		if(type == GUN_FLASH) {
			animation.setFrames(Content.GUN_FLASH, delay);
			width = height = 7;
		}
		if(type == GUN_HIT) {
			animation.setFrames(Content.GUN_FLASH, 24, 1);
			width = height = 7;
			speed = 1.7;
			diagSpeed = speed / 1.41;
			points = new Point2D.Double[8];
			for(int i = 0; i < points.length; i++) {
				points[i] = new Point2D.Double((int) x, (int) y);
			}
		}
		if(type == EXPLOSION) {
			animation.setFrames(Content.EXPLOSION, delay);
			width = height = 32;
			speed = 2;
			diagSpeed = speed / 1.41;
			points = new Point2D.Double[8];
			for(int i = 0; i < points.length; i++) {
				points[i] = new Point2D.Double((int) x, (int) y);
			}
		}
		
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	public void update() {
		
		if(remove) return;
		
		animation.update();
		
		if(animation.hasPlayedOnce()) {
			remove = true;
		}
		
		if(type == GUN_HIT || type == EXPLOSION) {
			points[0].x += speed;
			points[1].x += diagSpeed;
			points[1].y += diagSpeed;
			points[2].y += speed;
			points[3].x -= diagSpeed;
			points[3].y += diagSpeed;
			points[4].x -= speed;
			points[5].x -= diagSpeed;
			points[5].y -= diagSpeed;
			points[6].y -= speed;
			points[7].x += diagSpeed;
			points[7].y -= diagSpeed;
		}
	}
	
	public void draw(Graphics2D g) {
		
		if(remove) return;
		
		if(type == GUN_HIT || type == EXPLOSION) {
			setMapPosition();
			for(int i = 0; i < points.length; i++) {
				g.drawImage(
					animation.getImage(),
					(int) (points[i].x + xmap - width / 2),
					(int) (points[i].y + ymap - height / 2),
					null
				);
			}
			return;
		}
		super.draw(g);
	}
	
}
