package com.neet.entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.neet.main.GamePanel;
import com.neet.managers.Animation;
import com.neet.tilemap.TileMap;


public abstract class MapObject {
	
	// tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	// platforms
	protected ArrayList<Platform> platforms;
	
	// position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	// dimensions
	protected int width;
	protected int height;
	
	// collision box
	protected int cwidth;
	protected int cheight;
	
	// collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected int leftTile;
	protected int rightTile;
	protected int topTile;
	protected int bottomTile;
	protected boolean topCollision;
	protected boolean leftCollision;
	protected boolean rightCollision;
	protected boolean bottomCollision;
	protected boolean bottomLedge;
	protected Platform topLeftBlock;
	protected Platform topRightBlock;
	protected Platform bottomLeftBlock;
	protected Platform bottomRightBlock;
	protected Platform onPlatform;
	
	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	protected boolean ledgeFall;
	
	// movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	// constructor
	public MapObject(TileMap tm, ArrayList<Platform> p) {
		tileMap = tm;
		tileSize = tm.getTileSize();
		animation = new Animation();
		facingRight = true;
		platforms = p;
	}
	
	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	
	public boolean intersects(Rectangle r) {
		return getRectangle().intersects(r);
	}
	
	public boolean contains(int x, int y) {
		return getRectangle().contains(x, y);
	}
	
	public boolean contains(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.contains(r2);
	}
	
	public boolean contains(Rectangle r) {
		return getRectangle().contains(r);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(
				(int)(x - cwidth / 2),
				(int)(y - cheight / 2),
				cwidth,
				cheight
		);
	}
	
	public void calculateCollision(double x, double y) {
		topCollision = leftCollision = rightCollision = bottomCollision = bottomLedge = false;
		int xl = (int)(x - cwidth / 2);
		int xr = (int)(x + cwidth / 2 - 1);
		int yt = (int)(y - cheight / 2);
		int yb = (int)(y + cheight / 2 - 1);
		leftTile = xl / tileSize;
		rightTile = xr / tileSize;
		topTile = yt / tileSize;
		bottomTile = yb / tileSize;
		if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
			leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			return;
		}
		for(int i = 0; i < rightTile - leftTile + 1; i++) {
			topCollision |= tileMap.getType(topTile, leftTile + i) == TileMap.BLOCKED;
			bottomCollision |= tileMap.getType(bottomTile, leftTile + i) == TileMap.BLOCKED;
			bottomLedge |= tileMap.getType(bottomTile, leftTile + i) == TileMap.LEDGE;
		}
		for(int i = 0; i < bottomTile - topTile + 1; i++) {
			leftCollision |= tileMap.getType(topTile + i, leftTile) == TileMap.BLOCKED;
			rightCollision |= tileMap.getType(topTile + i, rightTile) == TileMap.BLOCKED;
		}
		for(int i = 0; i < platforms.size(); i++) {
			Platform p = platforms.get(i);
			if(p.contains(xl, yt)) {
				topLeftBlock = p;
			}
			if(p.contains(xr, yt)) {
				topRightBlock = p;
			}
			if(p.contains(xl, yb)) {
				bottomLeftBlock = p;
				onPlatform = p;
			}
			if(p.contains(xr, yb)) {
				bottomRightBlock = p;
				onPlatform = p;
			}
		}
	}
	
	public boolean checkTileMapCollision() {
		
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		boolean collision = false;
		
		calculateCollision(x, ydest);
		if(dy < 0) {
			if(topCollision) {
				dy = 0;
				ytemp = (topTile + 1) * tileSize + cheight / 2;
				collision = true;
			}
			else {
				ytemp += dy;
			}
			if(topLeftBlock != null) {
				dy = 0;
				ytemp = topLeftBlock.gety() + topLeftBlock.getCHeight() / 2 + cheight / 2;
				topLeftBlock = null;
			}
			if(topRightBlock != null) {
				dy = 0;
				ytemp = topRightBlock.gety() + topRightBlock.getCHeight() / 2 + cheight / 2;
				topRightBlock = null;
			}
		}
		if(dy > 0) {
			if(bottomCollision) {
				dy = 0;
				falling = false;
				ytemp = bottomTile * tileSize - cheight / 2;
				collision = true;
				ledgeFall = false;
			}
			else if(!ledgeFall && bottomLedge && (y + cheight / 2 - 1) - bottomTile * tileSize < dy) {
				dy = 0;
				falling = false;
				ytemp = bottomTile * tileSize - cheight / 2;
				collision = true;
			}
			else {
				ytemp += dy;
			}
			if(ledgeFall) {
				ledgeFall = tileMap.getType(
						(int)(y - cheight / 2 + 1)/ tileMap.getTileSize(),
						(int)x / tileSize) != TileMap.LEDGE;
			}
			if(bottomLeftBlock != null) {
				dy = 0;
				falling = false;
				ytemp = bottomLeftBlock.gety() - bottomLeftBlock.getCHeight() / 2 - cheight / 2;
				bottomLeftBlock = null;
			}
			if(bottomRightBlock != null) {
				dy = 0;
				falling = false;
				ytemp = bottomRightBlock.gety() - bottomRightBlock.getCHeight() / 2 - cheight / 2;
				bottomRightBlock = null;
			}
		}
		
		calculateCollision(xdest, y);
		if(dx < 0) {
			if(leftCollision) {
				dx = 0;
				xtemp = (leftTile + 1) * tileSize + cwidth / 2;
				collision = true;
			}
			else {
				xtemp += dx;
			}
			if(topLeftBlock != null) {
				dx = 0;
				xtemp = topLeftBlock.getx() + topLeftBlock.getCWidth() / 2 + cwidth / 2;
				topLeftBlock = null;
			}
			if(bottomLeftBlock != null) {
				dx = 0;
				xtemp = bottomLeftBlock.getx() + bottomLeftBlock.getCWidth() / 2 + cwidth / 2;
				bottomLeftBlock = null;
			}
		}
		if(dx > 0) {
			if(rightCollision) {
				dx = 0;
				xtemp = rightTile * tileSize - cwidth / 2;
				collision = true;
			}
			else {
				xtemp += dx;
			}
			if(topRightBlock != null) {
				dx = 0;
				xtemp = topRightBlock.getx() - topRightBlock.getCWidth() / 2 - cwidth / 2;
				topRightBlock = null;
			}
			if(bottomRightBlock != null) {
				dx = 0;
				xtemp = bottomRightBlock.getx() - bottomRightBlock.getCWidth() / 2 - cwidth / 2;
				bottomRightBlock = null;
			}
		}
		
		if(!falling) {
			calculateCollision(x, ydest + 1);
			if(!bottomCollision && !bottomLedge && bottomLeftBlock == null && bottomRightBlock == null) {
				falling = true;
			}
			if(onPlatform != null) {
				if(onPlatform.getdy() < 0) {
					ytemp = onPlatform.gety() - onPlatform.getCHeight() / 2 - cheight / 2;
				}
				xtemp += onPlatform.getdx();
				ytemp += onPlatform.getdy();
			}
		}
		
		topLeftBlock = topRightBlock = bottomLeftBlock = bottomRightBlock = onPlatform = null;
		return collision;
		
	}
	
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public double getdx() { return dx; }
	public double getdy() { return dy; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return cwidth; }
	public int getCHeight() { return cheight; }
	public boolean isFacingRight() { return facingRight; }
	public int getRow() { return (int) (y / tileMap.getTileSize()); }
	public int getCol() { return (int) (x / tileMap.getTileSize()); }
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	public void setCollision(int w, int h) {
		cwidth = w;
		cheight = h;
	}
	
	public void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}
	
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	public void setJumping(boolean b) { jumping = b; }
	
	public boolean notOnScreen() {
		return x + xmap + width < 0 ||
			x + xmap - width > GamePanel.WIDTH ||
			y + ymap + height < 0 ||
			y + ymap - height > GamePanel.HEIGHT;
	}
	
	public void draw(java.awt.Graphics2D g) {
		setMapPosition();
		if(facingRight) {
			g.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				null
			);
		}
		else {
			g.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2 + width),
				(int)(y + ymap - height / 2),
				-width,
				height,
				null
			);
		}
		
		// debug
		//g.setColor(java.awt.Color.BLUE);
		//Rectangle r = getRectangle();
		//r.x += xmap;
		//r.y += ymap;
		//g.draw(r);
	}
	
}