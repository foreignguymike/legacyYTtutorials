package com.neet.entity.collectables;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.neet.entity.Collectable;
import com.neet.entity.Platform;
import com.neet.entity.Player;
import com.neet.entity.Weapon;
import com.neet.tilemap.TileMap;

public class WeaponC extends Collectable {
	
	private int type;
	
	public WeaponC(TileMap tm, ArrayList<Platform> p, int type) {
		super(tm, p);
		this.type = type;
		cwidth = cheight = width = height = 10;
	}
	
	public void activate(Player player) {
		player.setWeapon(new Weapon(type));
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		g.setColor(java.awt.Color.WHITE);
		g.fillOval(
			(int)(x + xmap - width / 2),
			(int)(y + ymap - height / 2),
			width,
			height
		);
		
	}
	
}
