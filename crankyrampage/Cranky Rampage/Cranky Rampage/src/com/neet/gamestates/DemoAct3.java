package com.neet.gamestates;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.neet.entity.Collectable;
import com.neet.entity.Enemy;
import com.neet.entity.Explosion;
import com.neet.entity.Laser;
import com.neet.entity.Platform;
import com.neet.entity.Player;
import com.neet.entity.Projectile;
import com.neet.main.Game;
import com.neet.main.GamePanel;
import com.neet.managers.Content;
import com.neet.managers.GameObjectFactory;
import com.neet.managers.GameStateManager;
import com.neet.managers.Keys;
import com.neet.managers.Mouse;
import com.neet.tilemap.Background;
import com.neet.tilemap.TileMap;
import com.neet.ui.Cursor;
import com.neet.ui.HUD;


public class DemoAct3 extends GameState {
	
	private Player player;
	private TileMap tileMap;
	
	private ArrayList<Projectile> projectiles;
	private ArrayList<Collectable> collectables;
	private ArrayList<Explosion> explosions;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Platform> platforms;
	
	private HUD hud;
	private Cursor cursor;
	
	private Background skyBG;
	private Background cloudBG;
	private Background mountainBG;
	
	public DemoAct3(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		// init lists
		platforms = new ArrayList<Platform>();
		enemies = new ArrayList<Enemy>();
		projectiles = new ArrayList<Projectile>();
		collectables = new ArrayList<Collectable>();
		explosions = new ArrayList<Explosion>();
		
		// setup tilemap
		tileMap = new TileMap(16);
		tileMap.loadTileset("/tilesets/electrictileset.gif");
		tileMap.loadMap("/maps/demoact3.tme");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);
		
		// set up factory
		GameObjectFactory.tileMap = tileMap;
		GameObjectFactory.platforms = platforms;
		GameObjectFactory.projectiles = projectiles;
		GameObjectFactory.explosions = explosions;
		
		// setup player
		player = new Player(
				tileMap,
				platforms,
				enemies,
				projectiles,
				collectables);
		player.setPosition(100, 100);
		
		Laser l = new Laser(tileMap, platforms, player);
		l.setPosition(300, 20);
		enemies.add(l);
		
		// set HUD
		hud = new HUD(player);
		
		// set cursor
		Game.setCursorVisible(false);
		cursor = new Cursor(Content.CURSOR, 1);
		
		skyBG = new Background(Content.SKY_BG, 0);
		cloudBG = new Background(Content.CLOUD_BG, 0.05);
		mountainBG = new Background(Content.MOUNTAIN_BG, 0.1);
		
	}
	
	public void update() {
		
		handleInput();
		
		// update player/enemies/projectiles/collectables
		player.update();
		
		// update tilemap
		tileMap.setPosition(tileMap.getx() - 10, tileMap.gety());
		if(tileMap.getDepth(player.getRow(), player.getCol()) == TileMap.FOREGROUND) {
			tileMap.setVisible(false);
		}
		else {
			tileMap.setVisible(true);
		}
		tileMap.update();
		if(player.getx() - player.getCWidth() / 2 < -tileMap.getx()) {
			player.setPosition(
				-tileMap.getx() + player.getCWidth() / 2,
				player.gety()
			);
		}
		if(player.getx() + player.getCWidth() / 2 > -tileMap.getx() + GamePanel.WIDTH) {
			player.setPosition(
				-tileMap.getx() + GamePanel.WIDTH - player.getCWidth() / 2,
				player.gety()
			);
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
		skyBG.setPosition(tileMap.getx(), tileMap.gety());
		cloudBG.setPosition(tileMap.getx(), tileMap.gety());
		mountainBG.setPosition(tileMap.getx(), tileMap.gety());
		
		cursor.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		//g.setColor(new java.awt.Color(180, 215, 238));
		//g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		skyBG.draw(g);
		cloudBG.draw(g);
		mountainBG.draw(g);
		
		// draw tilemap
		tileMap.draw(g, 0);
		
		// draw collectables
		for(int i = 0; i < collectables.size(); i++) {
			collectables.get(i).draw(g);
		}
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.draw(g);
		}
		
		// draw player
		player.draw(g);
		
		// draw projectiles
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).draw(g);
		}
		
		// draw tilemap foreground
		tileMap.draw(g, 1);
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}
		
		// draw hud
		hud.draw(g);
		
		// draw cursor
		cursor.draw(g);
		
		g.setColor(java.awt.Color.WHITE);
		g.drawString((int)player.getx() + ", " + (int)player.gety(), 400, 20);
		
	}
	
	public void handleInput() {
		player.setLeft(Keys.isDown(Keys.LEFT));
		player.setRight(Keys.isDown(Keys.RIGHT));
		player.setDown(Keys.isDown(Keys.DOWN));
		player.setJumping(Keys.isDown(Keys.SPACE) || Keys.isDown(Keys.UP));
		player.setFiring(Mouse.isDown());
	}

}