package com.neet.gamestates;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.neet.entity.Collectable;
import com.neet.entity.Enemy;
import com.neet.entity.Explosion;
import com.neet.entity.Platform;
import com.neet.entity.Player;
import com.neet.entity.PopupText;
import com.neet.entity.Projectile;
import com.neet.entity.enemies.Blocker;
import com.neet.entity.enemies.EnergyGate;
import com.neet.entity.enemies.EnergyGateSource;
import com.neet.entity.enemies.Sensor;
import com.neet.entity.enemies.Sentry;
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

public class DemoAct1 extends GameState {
	
	private Player player;
	private TileMap tileMap;
	
	private ArrayList<Platform> platforms;
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Collectable> collectables;
	private ArrayList<Explosion> explosions;
	private ArrayList<PopupText> popupTexts;
	
	private HUD hud;
	private Cursor cursor;
	
	private Background skyBG;
	private Background cloudBG;
	private Background mountainBG;
	
	private boolean set1;
	private boolean set2;
	private boolean set3;
	private boolean set4;
	private boolean set5;
	private boolean set6;
	private boolean set7;
	private boolean set8;
	private boolean set9;
	private boolean set10;
	
	public DemoAct1(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		// init lists
		platforms = new ArrayList<Platform>();
		enemies = new ArrayList<Enemy>();
		projectiles = new ArrayList<Projectile>();
		collectables = new ArrayList<Collectable>();
		explosions = new ArrayList<Explosion>();
		popupTexts = new ArrayList<PopupText>();
		
		// setup tilemap
		tileMap = new TileMap(16);
		tileMap.loadTileset(Content.ELECTRIC_TS);
		tileMap.loadMap("/maps/demoact1.tme");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);
		
		// setup player
		player = new Player(
				tileMap,
				platforms,
				enemies,
				projectiles,
				collectables);
		player.setPosition(300, 531);
		//player.setPosition(1100, 347);
		tileMap.setPositionImmediately(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		tileMap.setBounds(tileMap.getWidth(), 639, -256, 0);
		Mouse.setPosition(317, 547);
		
		// set up factory
		GameObjectFactory.player = player;
		GameObjectFactory.tileMap = tileMap;
		GameObjectFactory.platforms = platforms;
		GameObjectFactory.projectiles = projectiles;
		GameObjectFactory.explosions = explosions;
		GameObjectFactory.enemies = enemies;
		GameObjectFactory.popupTexts = popupTexts;
		
		// set HUD
		hud = new HUD(player);
		
		// set cursor
		Game.setCursorVisible(false);
		cursor = new Cursor(Content.CURSOR, 1);
		
		skyBG = new Background(Content.SKY_BG, 0);
		cloudBG = new Background(Content.CLOUD_BG, 0.05);
		mountainBG = new Background(Content.MOUNTAIN_BG, 0.1);
		
		// test enemies
		enemies.add(new Sensor(tileMap, platforms, player, 1000, 345));
		enemies.add(new Sensor(tileMap, platforms, player, 1100, 345));
		enemies.add(new Sensor(tileMap, platforms, player, 1200, 345));
		
		EnergyGate eg = new EnergyGate(tileMap, 1380, 425, 48);
		enemies.add(new EnergyGateSource(tileMap, platforms, player, eg, 1345, 360));
		enemies.add(eg);
		
	}
	
	private void spawnEnemies() {
		
		if(!set1 && player.getx() > 450) {
			set1 = true;
			for(int i = 0; i < 3; i++) {
				Sentry s = new Sentry(tileMap, platforms, player);
				s.setPosition(650 + 40 * i, 546);
				enemies.add(s);
			}
		}
		
		if(!set2 && player.getx() > 1000) {
			set2 = true;
			Sentry s = new Sentry(tileMap, platforms, player);
			s.setPosition(1200, 466);
			enemies.add(s);
		}
		
		if(!set3 && player.getx() > 1600) {
			set3 = true;
			for(int i = 0; i < 3; i++) {
				Sentry s = new Sentry(tileMap, platforms, player);
				s.setPosition(1850 + 32 * i, 306);
				enemies.add(s);
			}
		}
		
		if(!set4 && player.getx() > 1968) {
			set4 = true;
			enemies.clear();
			enemies.add(new Sentry(tileMap, platforms, player, 2000, -300));
			enemies.add(new Sentry(tileMap, platforms, player, 2230, -300));
		}
		
		if(!set5 && set4 && enemies.size() == 0) {
			set5 = true;
			enemies.add(new Sentry(tileMap, platforms, player, 2050, -32));
			enemies.add(new Sentry(tileMap, platforms, player, 2180, -32));
		}
		
		if(!set6 && set5 && enemies.size() == 0) {
			set6 = true;
			enemies.add(new Sentry(tileMap, platforms, player, 2100, -32));
			enemies.add(new Sentry(tileMap, platforms, player, 2130, -32));
		}
		
		if(!set7 && set6 && enemies.size() == 0) {
			set7 = true;
			enemies.add(new Blocker(tileMap, platforms, player, 2000, -32));
			enemies.add(new Blocker(tileMap, platforms, player, 2230, -32));
		}
		
		if(!set8 && set7 && enemies.size() == 0) {
			set8 = true;
			enemies.add(new Blocker(tileMap, platforms, player, 2050, -32));
			enemies.add(new Blocker(tileMap, platforms, player, 2180, -32));
		}
		
		if(!set9 && set8 && enemies.size() == 0) {
			set9 = true;
			enemies.add(new Blocker(tileMap, platforms, player, 2100, -32));
			enemies.add(new Blocker(tileMap, platforms, player, 2130, -32));
		}
		
		if(!set10 && set9 && enemies.size() == 0) {
			set10 = true;
			enemies.add(new Blocker(tileMap, platforms, player, 2000, -32));
			enemies.add(new Blocker(tileMap, platforms, player, 2230, -32));
			enemies.add(new Sentry(tileMap, platforms, player, 2050, -32));
			enemies.add(new Sentry(tileMap, platforms, player, 2180, -32));
		}
		
	}
	
	public void update() {
		
		// check user input
		handleInput();
		
		// check enemy spawns
		spawnEnemies();
		
		// update player/enemies/projectiles/collectables
		player.update();
		
		// update tilemap
		if(player.getx() > 1968) {
			tileMap.setPosition(-1952, -96);
		}
		else {
			double dx = Mouse.x - player.getx() - tileMap.getx();
			double dy = Mouse.y - player.gety() - tileMap.gety();
			double dist = Math.sqrt(dx * dx + dy * dy);
			dx /= dist / 48;
			dy /= dist / 48;
			int xoff = (int) -dx;
			int yoff = (int) -dy;
			tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx() + xoff,
				GamePanel.HEIGHT / 2 - player.gety() + yoff
			);
		}
		tileMap.fixBounds();
		tileMap.update();
		
		if(tileMap.getDepth(player.getRow(), player.getCol()) == TileMap.FOREGROUND) {
			tileMap.setVisible(false);
		}
		else {
			tileMap.setVisible(true);
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
		// update popup texts
		for(int i = 0; i < popupTexts.size(); i++) {
			popupTexts.get(i).update();
			if(popupTexts.get(i).shouldRemove()) {
				popupTexts.remove(i);
				i--;
			}
		}
		
		// update bg
		skyBG.setPosition(tileMap.getx(), tileMap.gety());
		cloudBG.setPosition(tileMap.getx(), tileMap.gety());
		mountainBG.setPosition(tileMap.getx(), tileMap.gety());
		
		// update cursor
		cursor.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		g.setColor(java.awt.Color.BLACK);
		g.fillRect(0, 0, 320, 240);
		
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
		
		// draw popup texts
		for(int i = 0; i < popupTexts.size(); i++) {
			popupTexts.get(i).draw(g);
		}
		
		// draw hud
		hud.draw(g);
		
		// draw cursor
		cursor.draw(g);
		
		g.setColor(java.awt.Color.WHITE);
		g.drawString((int)player.getx() + ", " + (int)player.gety(), 150, 234);
		
	}
	
	public void handleInput() {
		player.setLeft(Keys.isDown(Keys.LEFT));
		player.setRight(Keys.isDown(Keys.RIGHT));
		player.setDown(Keys.isDown(Keys.DOWN));
		player.setJumping(Keys.isDown(Keys.SPACE) || Keys.isDown(Keys.UP));
		player.setFiring(Mouse.isDown());
	}

}