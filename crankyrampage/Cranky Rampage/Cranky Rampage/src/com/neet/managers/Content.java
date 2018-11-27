package com.neet.managers;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Content {
	
	public static BufferedImage[][] LOGO = load("/logo.gif", 128, 144);
	public static BufferedImage[] CURSOR = load("/ui/cursor.png", 27, 27)[0];
	
	public static BufferedImage SKY_BG = load("/backgrounds/sky.gif", 320, 240)[0][0];
	public static BufferedImage CLOUD_BG = load("/backgrounds/clouds.gif", 320, 240)[0][0];
	public static BufferedImage MOUNTAIN_BG = load("/backgrounds/mountains.png", 320, 240)[0][0];
	
	public static BufferedImage[][] ELECTRIC_TS = load("/tilesets/electrictileset.gif", 16, 16);
	public static BufferedImage[][] NEIGHBORHOOD_TS = load("/tilesets/neighborhoodtileset.png", 16, 16);
	
	public static BufferedImage[][] PLAYER_SPRITES = load("/sprites/playersprites.png", 32, 32);
	public static BufferedImage[][] PLAYER_LEG_SPRITES = load("/sprites/playerlegsprites.png", 32, 32);
	public static BufferedImage[] GUN_FLASH = load("/sprites/gunflash.png", 7, 7)[0];
	public static BufferedImage[] EXPLOSION = load("/sprites/explosion.png", 32, 32)[0];
	
	public static BufferedImage[] MG_BULLETS = load("/bullets/machinegun.png", 6, 20)[0];
	public static BufferedImage[] FMG_BULLETS = load("/bullets/flamingmachinegun.png", 10, 30)[0];
	
	public static BufferedImage HEART_HUD = loadImage("/ui/hearthud.png");
	public static BufferedImage RAMPAGE_HUD = loadImage("/ui/rampagehud.png");
	public static BufferedImage HEAD_HUD = loadImage("/ui/headhud.png");
	public static BufferedImage[][] WEAPONS_HUD = load("/ui/weaponshud.png", 13, 13);
	
	public static BufferedImage[] ENEMY_BULLET = load("/enemies/bulletsprites.png", 5, 5)[0];
	public static BufferedImage[] ENEMY_ROCKET = load("/enemies/rocket.png", 7, 15)[0];
	public static BufferedImage[][] SENTRY = load("/enemies/sentry.png", 32, 32);
	public static BufferedImage[][] BLOCKER = load("/enemies/blocker.png", 40, 40);
	public static BufferedImage[][] SENSOR = load("/enemies/sensor.png", 25, 25);
	public static BufferedImage[][] ENERGY_GATE_SOURCE = load("/enemies/energygatesource.png", 32, 32);
	public static BufferedImage[][] ENERGY_GATE = load("/enemies/energygate.png", 32, 32);
	
	public static Font PS_FONT = loadFont("/fonts/prstart.ttf", Font.TRUETYPE_FONT);
	
	public static BufferedImage loadImage(String s) {
		BufferedImage ret;
		try {
			ret = ImageIO.read(Content.class.getResourceAsStream(s));
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics: " + s);
			System.exit(0);
		}
		return null;
	}
	
	public static BufferedImage[][] load(String s, int w, int h) {
		BufferedImage[][] ret;
		try {
			BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(s));
			int width = spritesheet.getWidth() / w;
			int height = spritesheet.getHeight() / h;
			ret = new BufferedImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics: " + s);
			System.exit(0);
		}
		return null;
	}
	
	public static Font loadFont(String s, int format) {
		Font f = null;
		try {
			f = Font.createFont(format, Content.class.getResourceAsStream(s)).deriveFont(8f);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading font: " + s);
			System.exit(0);
		}
		return f;
	}
	
}
