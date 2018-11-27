package com.neet.ui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.neet.managers.Animation;
import com.neet.managers.Mouse;

public class Cursor {
	
	private Animation animation;
	private int width;
	private int height;
	
	private double angle;
	private AffineTransform tx;
	
	public Cursor(BufferedImage[] sprites, int frames) {
		animation = new Animation();
		animation.setFrames(sprites, frames);
		width = sprites[0].getWidth();
		height = sprites[0].getHeight();
		tx = new AffineTransform();
	}
	
	public void update() {
		angle += 0.1;
		if(angle > 3.14) angle = 0;
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		tx = new AffineTransform();
		tx.translate(Mouse.x, Mouse.y);
		tx.rotate(angle);
		tx.translate(-width / 2, -height / 2);
		g.drawImage(animation.getImage(), tx, null);
	}
	
}
