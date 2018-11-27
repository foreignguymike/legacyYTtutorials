package com.neet.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.neet.managers.Animation;
import com.neet.managers.Content;
import com.neet.managers.GameObjectFactory;
import com.neet.managers.Mouse;
import com.neet.tilemap.TileMap;

public class Player extends MapObject {
	
	private int health;
	private int maxHealth;
	private boolean flinching;
	private int flinchCount;
	private int flinchTime;
	private boolean knockback;
	private boolean dead;
	
	private int lives;
	private int score;
	private int rampage;
	private int maxRampage;
	private int rampageLevel;
	
	private boolean doubleJump;
	private boolean alreadyDoubleJump;
	
	private Weapon defaultWeapon;
	private Weapon currentWeapon;
	private boolean firing;
	public static int damageMultiplier;
	
	private int direction;
	public static final int UP = 0;
	public static final int UPRIGHT = 1;
	public static final int RIGHT = 2;
	public static final int DOWNRIGHT = 3;
	public static final int DOWN = 4;
	public static final int DOWNLEFT = 5;
	public static final int LEFT = 6;
	public static final int UPLEFT = 7;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Collectable> collectables;
	
	private int currentAnimation;
	private Animation legAnimation;
	private int currentLegAnimation;
	private int numFramesLegs[] = {1, 8, 1};
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	
	public Player(
			TileMap tm,
			ArrayList<Platform> ap,
			ArrayList<Enemy> e,
			ArrayList<Projectile> p,
			ArrayList<Collectable> c) {
		
		super(tm, ap);
		enemies = e;
		projectiles = p;
		collectables = c;
		
		moveSpeed = maxSpeed = stopSpeed = 1.4;
		fallSpeed = 0.12;
		maxFallSpeed = 4.0;
		jumpStart = -3.8;
		stopJumpSpeed = 0.15;
		
		health = maxHealth = 10;
		
		flinchTime = 120;
		
		facingRight = true;
		
		defaultWeapon = new Weapon(Weapon.MACHINE_GUN);
		currentWeapon = defaultWeapon;
		damageMultiplier = 1;
		
		animation.setFrames(Content.PLAYER_SPRITES[RIGHT], -1);
		currentAnimation = RIGHT;
		legAnimation = new Animation();
		legAnimation.setFrames(Content.PLAYER_LEG_SPRITES[0], -1);
		currentLegAnimation = IDLE;
		
		width = height = 32;
		cwidth = 10;
		cheight = 26;
		
		rampage = 0;
		maxRampage = 10;
		rampageLevel = 1;
		
		lives = 3;
		
	}
	
	public void setHealth(int i) { health = i; }
	public void setMaxHealth(int i) { maxHealth = i; }
	public void setLives(int i) { lives = i; }
	public void loseLife() { lives--; }
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public int getScore() { return score; }
	public int getLives() { return lives; }
	public int getDirection() { return direction; }
	public boolean isDead() { return dead; }
	public int getRampage() { return rampage; }
	public int getMaxRampage() { return maxRampage; }
	public int getRampageLevel() { return rampageLevel; }
	public Weapon getWeapon() { return currentWeapon; }
	
	public void setJumping(boolean b) {
		if(knockback) return;
		if(!jumping && !falling && !alreadyDoubleJump && b && down && tileMap.getType(
				(int)(y + cheight / 2 + 1)/ tileMap.getTileSize(),
				(int)x / tileSize) == TileMap.LEDGE) {
			ledgeFall = true;
			return;
		}
		if(!ledgeFall) {
			if(b && !jumping && falling && !alreadyDoubleJump) {
				doubleJump = true;
			}
			jumping = b;
		}
	}
	
	public void setWeapon(Weapon w) {
		currentWeapon = w;
	}
	
	public void setFiring(boolean b) {
		if(!b && !currentWeapon.isAuto()) {
			currentWeapon.setRelease();
		}
		if(knockback) return;
		firing = b;
	}
	
	private void fire() {
		GameObjectFactory.createProjectile(x, y, currentWeapon.getType(), direction);
		double x = this.x;
		double y = this.y;
		if(direction == UP) {
			if(facingRight) {
				x -= 5;
			}
			else {
				x -= 3;
			}
			y -= 15;
		}
		else if(direction == UPRIGHT) {
			x += 5;
			y -= 12;
		}
		else if(direction == RIGHT) {
			x += 12;
			y -= 1;
		}
		else if(direction == DOWNRIGHT) {
			x += 5;
			y += 8;
		}
		else if(direction == DOWN) {
			if(facingRight) {
				x -= 3;
			}
			else {
				x -= 5;
			}
			y += 11;
		}
		else if(direction == DOWNLEFT) {
			x -= 12;
			y += 8;
		}
		else if(direction == LEFT) {
			x -= 18;
			y -= 1;
		}
		else if(direction == UPLEFT) {
			x -= 12;
			y -= 12;
		}
		GameObjectFactory.createExplosion(x, y, Explosion.GUN_FLASH, 4);
	}
	
	public void hit(int damage) {
		health -= damage;
		flinching = true;
		flinchCount = 0;
		dy = -3;
		knockback = true;
		falling = true;
		jumping = false;
		firing = false;
		if(rampageLevel > 1) {
			rampageLevel--;
			rampage = 0;
			setStats();
		}
	}
	
	public void restore(int amount) {
		health += amount;
		if(health > maxHealth) {
			health = maxHealth;
		}
	}
	
	private void setStats() {
		damageMultiplier = rampageLevel;
	}
	
	private void setAnimation(int i) {
		if(currentAnimation != i) {
			currentAnimation = i;
			animation.setFrames(Content.PLAYER_SPRITES[i], -1);
		}
	}
	
	private void setLegAnimation(int i) {
		if(currentLegAnimation != i) {
			currentLegAnimation = i;
			int d = numFramesLegs[i] == 1 ? -1 : 4;
			legAnimation.setFrames(Content.PLAYER_LEG_SPRITES[i], d, numFramesLegs[i]);
		}
	}
	
	private void getNextPosition() {
		
		if(knockback) {
			dy += fallSpeed * 2;
			if(facingRight) {
				dx = -1;
			}
			else {
				dx = 1;
			}
			if(dy > maxFallSpeed * 2) dy = maxFallSpeed * 2;
			if(!falling) knockback = false;
			return;
		}
		
		if(left) {
			if(dx > 0) {
				dx -= stopSpeed;
			}
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			if(dx < 0) {
				dx += stopSpeed;
			}
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}
		
		if(doubleJump) {
			dy = jumpStart;
			alreadyDoubleJump = true;
			doubleJump = false;
		}
		
		if(!falling) {
			alreadyDoubleJump = false;
		}
		
		if(ledgeFall || falling) {
			dy += fallSpeed;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		
	}
	
	public void update() {
		
		// set position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0) x = (int)x;
		if(dy == 0) y = (int)y;
		
		// update/check projectile collision
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			p.update();
			if(p.shouldRemove()) {
				projectiles.remove(i);
				i--;
				GameObjectFactory.createExplosion(p.getx(), p.gety(), Explosion.GUN_HIT, 10);
			}
		}
		
		// update/check enemy collision
		for(int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			enemy.update();
			if(!flinching && intersects(enemy)) {
				hit(enemy.getDamage());
			}
			for(int j = 0; j < projectiles.size(); j++) {
				Projectile p = projectiles.get(j);
				if(!enemy.isFlinching() && p.intersects(enemy)) {
					enemy.hit(p.getDamage());
					projectiles.remove(j);
					j--;
					GameObjectFactory.createExplosion(p.getx(), p.gety(), Explosion.GUN_HIT, 10);
				}
			}
			if(enemy.shouldRemove()) {
				enemies.remove(i);
				i--;
				if(enemy.isDead()) {
					GameObjectFactory.createExplosion(enemy.getx(), enemy.gety(), Explosion.EXPLOSION, 3);
				}
				score += enemy.getScore();
				rampage += enemy.getRampage();
				if(rampage >= maxRampage && rampageLevel < 3) {
					rampage -= 10;
					rampageLevel++;
					setStats();
					GameObjectFactory.createPopupText(rampageLevel + "x DAMAGE", x, y - tileSize, Color.BLACK, Color.YELLOW);
				}
				if(rampageLevel == 3) {
					rampage = 10;
				}
			}
		}
		
		// update/check collectables
		for(int i = 0; i < collectables.size(); i++) {
			Collectable c = collectables.get(i);
			c.update();
			if(intersects(c)) {
				c.activate(this);
				collectables.remove(i);
				i--;
				continue;
			}
			if(c.shouldRemove()) {
				collectables.remove(i);
				i--;
			}
		}
		
		// check firing
		double angle = Math.atan2(Mouse.y - y - ymap, Mouse.x - x - xmap);
		if(angle > -0.39 && angle < 0.39) {
			direction = RIGHT;
		}
		else if(angle > 0.39 && angle < 1.18) {
			direction = DOWNRIGHT;
		}
		else if(angle > 1.18 && angle < 1.96) {
			direction = DOWN;
		}
		else if(angle > 1.96 && angle < 2.75) {
			direction = DOWNLEFT;
		}
		else if(angle > 2.75 || angle < -2.75) {
			direction = LEFT;
		}
		else if(angle > -2.75 && angle < -1.96) {
			direction = UPLEFT;
		}
		else if(angle > -1.96 && angle < -1.18) {
			direction = UP;
		}
		else if(angle > -1.18 && angle < -0.39) {
			direction = UPRIGHT;
		}
		currentWeapon.update();
		if(firing) {
			if(currentWeapon.canFire()) {
				currentWeapon.fire();
				fire();
			}
		}
		if(currentWeapon.isEmpty()) {
			currentWeapon = defaultWeapon;
		}
		
		// check flinch
		if(flinching) {
			flinchCount++;
			if(flinchCount > flinchTime) {
				flinching = false;
			}
		}
		
		// update animation
		if(falling) {
			setLegAnimation(JUMPING);
		}
		else if(left || right) {
			setLegAnimation(WALKING);
		}
		else {
			setLegAnimation(IDLE);
		}
		legAnimation.update();
		if(direction == UP) {
			setAnimation(UP);
		}
		else if(direction == UPRIGHT ||
				direction == UPLEFT) {
			setAnimation(UPRIGHT);
		}
		else if(direction == RIGHT ||
				direction == LEFT) {
			setAnimation(RIGHT);
		}
		else if(direction == DOWNRIGHT ||
				direction == DOWNLEFT) {
			setAnimation(DOWNRIGHT);
		}
		else if(direction == DOWN) {
			setAnimation(DOWN);
		}
		animation.update();
		
		// set direction
		if(!knockback) {
			if(direction == UPRIGHT ||
				direction == RIGHT ||
				direction == DOWNRIGHT) {
				facingRight = true;
				legAnimation.setReverse(left);
			}
			else if(direction == UPLEFT ||
				direction == LEFT ||
				direction == DOWNLEFT) {
				facingRight = false;
				legAnimation.setReverse(right);
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		if(flinching) {
			if(flinchCount % 8 < 4) {
				g.setXORMode(java.awt.Color.RED);
			}
		}
		
		// draw player
		super.draw(g);
		if(facingRight) {
			g.drawImage(
				legAnimation.getImage(),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				null
			);
		}
		else {
			g.drawImage(
				legAnimation.getImage(),
				(int)(x + xmap - width / 2 + width),
				(int)(y + ymap - height / 2),
				-width,
				height,
				null
			);
		}
		
		g.setPaintMode();
		
	}
	
}