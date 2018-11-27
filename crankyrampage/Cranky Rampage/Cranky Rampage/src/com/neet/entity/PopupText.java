package com.neet.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.neet.managers.Content;
import com.neet.managers.GameUtils;
import com.neet.tilemap.TileMap;

public class PopupText extends MapObject {
	
	private String text;
	private Color outer;
	private Color inner;
	
	private int timer;
	private int time;
	private float alpha;
	
	private boolean remove;
	
	public PopupText(TileMap tm, String s, double x, double y, Color c1, Color c2) {
		super(tm, null);
		text = s;
		outer = c1;
		inner = c2;
		this.x = x + tileMap.getx();
		this.y = y + tileMap.gety();
		timer = 0;
		time = 90;
		alpha = 1;
	}
	
	public boolean shouldRemove() { return remove; }
	
	public void update() {
		y -= 0.7;
		timer++;
		if(timer >= time) {
			remove = true;
		}
		if(timer > time / 2) {
			alpha = (float) (1 - 1.0 * (timer - (time / 2)) / (time / 2));
		}
	}
	
	public void draw(Graphics2D g) {
		Composite original = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		FontMetrics fm = g.getFontMetrics();
		int adv = fm.stringWidth(text);
		g.setFont(Content.PS_FONT);
		GameUtils.drawOutlinedString(g, text, (int) x - adv / 2, (int) y, outer, inner);
		g.setComposite(original);
	}
	
}
