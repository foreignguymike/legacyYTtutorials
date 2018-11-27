/**
 * Helper methods to make life easier.
 */

package com.neet.managers;

import java.awt.Graphics2D;
import java.awt.Paint;

public class GameUtils {
	
	// draws a string with a 1px stroke
	public static void drawOutlinedString(Graphics2D g, String s, int x, int y, Paint outer, Paint inner) {
		g.setPaint(outer);
		g.drawString(s, x - 1, y - 1);
		g.drawString(s, x + 1, y - 1);
		g.drawString(s, x - 1, y + 1);
		g.drawString(s, x + 1, y + 1);
		g.drawString(s, x - 1, y);
		g.drawString(s, x + 1, y);
		g.drawString(s, x, y - 1);
		g.drawString(s, x, y + 1);
		g.setPaint(inner);
		g.drawString(s, x, y);
	}
	
}
