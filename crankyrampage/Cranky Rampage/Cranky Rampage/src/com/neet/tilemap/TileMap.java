package com.neet.tilemap;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.neet.main.GamePanel;
import com.neet.managers.Content;

public class TileMap {
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double tween;
	
	// map
	private int[][] collision;
	private int[][] map;
	private int[][] depth;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private BufferedImage[][] tileset;
	private int numTilesAcross;
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	public static final int LEDGE = 2;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	private float alpha;
	private boolean visible;
	private double visibleSpeed;
	
	public static final int BACKGROUND = 0;
	public static final int FOREGROUND = 1;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 1;
		visible = true;
		alpha = 1;
		visibleSpeed = 0.05;
	}
	
	public void setVisible(boolean b) {
		visible = b;
	}
	
	public void loadTileset(String s) {
		tileset = Content.load(s, tileSize, tileSize);
		numTilesAcross = tileset[0].length;
	}
	
	public void loadTileset(BufferedImage[][] bi) {
		tileset = bi;
		numTilesAcross = tileset[0].length;
	}
	
	public void loadMap(String s) {
		
		try {
			
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(
				new InputStreamReader(in)
			);
			
			// save file structure:
			// <tileset path>
			// <tile size>
			// <map width>
			// <map height>
			// <map>
			// <collision map>
			// <depth map>
			
			br.readLine();
			br.readLine();
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			collision = new int[numRows][numCols];
			depth = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
					collision[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					collision[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					depth[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getTileSize() { return tileSize; }
	public double getx() { return x; }
	public double gety() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getNumRows() { return numRows; }
	public int getNumCols() { return numCols; }
	
	public int getType(int row, int col) {
		if(row < 0 || row >= numRows || col < 0 || col >= numCols) return 0;
		return collision[row][col];
	}
	public int getDepth(int row, int col) {
		if(row < 0 || row >= numRows || col < 0 || col >= numCols) return 0;
		return depth[row][col];
	}
	
	public void setTween(double d) { tween = d; }
	public void setBounds(int i1, int i2, int i3, int i4) {
		xmin = GamePanel.WIDTH - i1;
		ymin = GamePanel.WIDTH - i2;
		xmax = i3;
		ymax = i4;
	}
	
	public void setPosition(double x, double y) {
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		fixBounds();
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
	}
	
	public void setPositionImmediately(double x, double y) {
		this.x = x;
		this.y = y;
		fixBounds();
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
	}
	
	public void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	public void update() {
		
		if(visible && alpha < 1) {
			alpha += visibleSpeed;
		}
		else if(!visible && alpha > 0.5) {
			alpha -= visibleSpeed;
		}
		if(alpha > 1) alpha = 1;
		if(alpha < 0.5) alpha = 0.5f;
		
	}
	
	public void draw(Graphics2D g, int d) {
		
		g.setColor(java.awt.Color.BLACK);
		
		Composite original = g.getComposite();
		if(d == 1) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		}
		
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
		
			if(row >= numRows) break;
			
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if(col >= numCols) break;
				if(map[row][col] == 0) continue;
				if(depth[row][col] != d) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(
					tileset[r][c],
					(int)x + col * tileSize,
					(int)y + row * tileSize,
					null
				);
				
			}
			
		}
		
		if(d == 1) {
			g.setComposite(original);
		}
		
	}
	
}



















