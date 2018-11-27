/**
 * The ui for the game.
 * Shows stuff like player health, player lives,
 * current weapon, etc.
 */

package com.neet.ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

import com.neet.entity.Player;
import com.neet.entity.Weapon;
import com.neet.managers.Content;
import com.neet.managers.GameUtils;

public class HUD {
	
	private Player player;
	
	private Color textBorderColor;
	private Color heartColor;
	private Color heartBorderColor;
	
	private GradientPaint textColor;
	private GradientPaint bottomTextColor;
	
	public HUD(Player player) {
		
		this.player = player;
		
		textBorderColor = new Color(32, 32, 32);
		heartColor = new Color(204, 44, 44);
		heartBorderColor = new Color(112, 32, 32);
		textColor = new GradientPaint(0, 11, Color.WHITE, 0, 12, Color.LIGHT_GRAY);
		bottomTextColor = new GradientPaint(0, 229, Color.WHITE, 0, 230, Color.LIGHT_GRAY);
		
	}
	
	public void draw(Graphics2D g) {
		
		g.setFont(Content.PS_FONT);
		
		// draw health
		g.drawImage(Content.HEART_HUD, 8, 8, null);
		GameUtils.drawOutlinedString(
			g,
			player.getHealth() + "/" + player.getMaxHealth() + "\u221e",
			27,
			16,
			textBorderColor,
			textColor
		);
		g.setColor(heartBorderColor);
		g.drawRect(24, 19, 46, 2);
		if(player.getHealth() > 0) {
			g.setColor(heartColor);
			g.drawLine(25, 20, (int) (25 + 44.0 * player.getHealth() / player.getMaxHealth()), 20);
		}
		
		// draw rampage meter
		g.drawImage(Content.RAMPAGE_HUD, 75, 8, null);
		GameUtils.drawOutlinedString(
			g,
			"RAMPAGE LV." + player.getRampageLevel(),
			93,
			16,
			textBorderColor,
			textColor
		);
		g.setColor(heartBorderColor);
		g.drawRect(90, 19, 100, 2);
		if(player.getRampage() > 0) {
			g.setColor(heartColor);
			g.drawLine(91, 20, (int) (91 + 98.0 * player.getRampage() / player.getMaxRampage()), 20);
		}
		
		// draw score
		GameUtils.drawOutlinedString(
			g,
			"SCORE: ",
			210,
			16,
			textBorderColor,
			textColor
		);
		String score = Integer.toString(player.getScore());
		int padding = 7 - score.length();
		for(int i = 0; i < padding; i++) {
			score = "0" + score;
		}
		GameUtils.drawOutlinedString(
			g,
			score,
			260,
			16,
			textBorderColor,
			textColor
		);
		
		// draw lives
		g.drawImage(Content.HEAD_HUD, 8, 220, null);
		GameUtils.drawOutlinedString(
			g,
			"x" + player.getLives(),
			31,
			234,
			textBorderColor,
			bottomTextColor
		);
		
		// draw weapon
		Weapon w = player.getWeapon();
		g.drawImage(Content.WEAPONS_HUD[w.getType()][0], 60, 223, null);
		int ammo = w.getAmmo();
		String sammo = "";
		if(ammo == -1) {
			sammo = "-";
		}
		else {
			sammo = "x" + ammo;
		}
		GameUtils.drawOutlinedString(
			g,
			sammo,
			78,
			234,
			textBorderColor,
			bottomTextColor
		);
		
	}
	
}
