package com.neet.entity;


public class Weapon {
	
	public static final int MACHINE_GUN = 0;
	public static final int SPREADER = 1;
	public static final int HIGH_CALIBER = 2;
	public static final int FLAMING_MACHINE_GUN = 3;
	
	private int type;
	private boolean auto;
	private int delay;
	private long timer;
	private boolean release;
	private boolean ready;
	private int ammo;
	
	public Weapon(int type) {
		
		this.type = type;
		
		initStats();
		
		timer = delay;
		ready = true;
		release = true;
		
	}
	
	private void initStats() {
		if(type == MACHINE_GUN) {
			delay = 8;
			auto = true;
			ammo = -1;
		}
		else if(type == SPREADER) {
			delay = 10;
			auto = true;
			ammo = 75;
		}
		else if(type == HIGH_CALIBER) {
			delay = 20;
			auto = true;
			ammo = 50;
		}
		else if(type == FLAMING_MACHINE_GUN) {
			delay = 5;
			auto = true;
			ammo = 100;
		}
	}
	
	public int getType() { return type; }
	public boolean isAuto() { return auto; }
	public int getDelay() { return delay; }
	public int getAmmo() { return ammo; }
	public boolean isEmpty() { return ammo == 0; }
	
	public boolean canFire() {
		if(ammo == 0) return false;
		ready = timer >= delay;
		if(auto) return ready;
		return ready && release;
	}
	
	public void setRelease() {
		release = true;
	}
	
	public void fire() {
		timer = 0;
		ready = false;
		release = false;
		if(ammo > 0) {
			ammo--;
		}
	}
	
	public void update() {
		timer++;
	}
	
}
