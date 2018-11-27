package com.neet.managers;

import java.awt.Color;
import java.util.ArrayList;

import com.neet.entity.Enemy;
import com.neet.entity.Explosion;
import com.neet.entity.Platform;
import com.neet.entity.Player;
import com.neet.entity.PopupText;
import com.neet.entity.Projectile;
import com.neet.entity.Weapon;
import com.neet.entity.enemies.Bullet;
import com.neet.entity.enemies.Rocket;
import com.neet.tilemap.TileMap;

public class GameObjectFactory {
	
	public static Player player;
	public static TileMap tileMap;
	public static ArrayList<Platform> platforms;
	public static ArrayList<Projectile> projectiles;
	public static ArrayList<Explosion> explosions;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<PopupText> popupTexts;
	
	public static void createProjectile(double x, double y, int weapon,	int dir) {
		
		Projectile p = null;
		
		if(weapon == Weapon.MACHINE_GUN) {
			p = new Projectile(tileMap, platforms, x, y, dir, weapon);
			projectiles.add(p);
		}
		else if(weapon == Weapon.SPREADER) {
			p = new Projectile(tileMap, platforms, x, y, dir, weapon);
			int angle = -90 + dir * 45;
			p.setAngle(angle);
			projectiles.add(p);
			p = new Projectile(tileMap, platforms, x, y, dir, weapon);
			angle = -90 + dir * 45;
			p.setAngle(angle - 20);
			projectiles.add(p);
			p = new Projectile(tileMap, platforms, x, y, dir, weapon);
			angle = -90 + dir * 45;
			p.setAngle(angle + 20);
			projectiles.add(p);
		}
		else if(weapon == Weapon.HIGH_CALIBER) {
			p = new Projectile(tileMap, platforms, x, y, dir, weapon);
			projectiles.add(p);
		}
		else if(weapon == Weapon.FLAMING_MACHINE_GUN) {
			p = new Projectile(tileMap, platforms, x, y, dir, weapon);
			projectiles.add(p);
		}
		
	}
	
	public static void createExplosion(double x, double y, int type, int delay) {
		explosions.add(new Explosion(tileMap, platforms, x, y, type, delay));
	}
	
	public static void createEnemy(Enemy e) {
		enemies.add(e);
	}
	
	public static void createEnemyBullet(double x, double y, double dx, double dy) {
		Bullet b = new Bullet(tileMap, platforms);
		b.setPosition(x, y);
		b.setVector(dx, dy);
		enemies.add(b);
	}
	
	public static void createEnemyRocket(double x, double y) {
		Rocket r = new Rocket(tileMap, platforms, player, x, y);
		enemies.add(r);
	}
	
	public static void createPopupText(String s, double x, double y, Color c1, Color c2) {
		popupTexts.add(new PopupText(tileMap, s, x, y, c1, c2));
	}
	
}
