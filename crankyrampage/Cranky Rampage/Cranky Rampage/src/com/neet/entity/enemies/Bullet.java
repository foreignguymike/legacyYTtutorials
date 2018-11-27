package com.neet.entity.enemies;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.neet.entity.Enemy;
import com.neet.entity.Platform;
import com.neet.main.GamePanel;
import com.neet.managers.Content;
import com.neet.tilemap.TileMap;

public class Bullet extends Enemy {
	
	public Bullet(TileMap tm, ArrayList<Platform> p) {
		super(tm, p, null);
		width = height = 5;
		cwidth = cheight = 5;
		damage = 1;
		animation.setFrames(Content.ENEMY_BULLET, 2);
		flinching = true;
	}
	
	public void update() {
		x += dx;
		y += dy;
		animation.update();
		if(x < -tileMap.getx() - GamePanel.WIDTH / 2 ||
			x > -tileMap.getx() + GamePanel.WIDTH + GamePanel.WIDTH / 2) {
			remove = true;
		}
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}
