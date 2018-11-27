package com.neet.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.neet.managers.GameStateManager;
import com.neet.managers.Keys;
import com.neet.managers.Mouse;

@SuppressWarnings("serial")
public class GamePanel
extends JPanel
implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static int SCALE = 2;
	
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	private int averageFPS;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private GameStateManager gsm;
	
	private boolean screenshot;
	
	public GamePanel() {
		setPreferredSize(new Dimension((int)(WIDTH * SCALE), (int)(HEIGHT * SCALE)));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	private void init() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON
		);
		g.setRenderingHint(
			RenderingHints.KEY_TEXT_ANTIALIASING, 
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON
		);
		
		running = true;
		
		gsm = new GameStateManager();
		
	}
	
	public void run() {
		
		init();
		
		// don't know why, but sometimes setResizable gets called after pack
		// have to recheck that the panel is correct size
		if(WIDTH * SCALE != getWidth() || HEIGHT * SCALE != getHeight()) {
			System.out.println("WAT");
			JFrame frame = (JFrame) getTopLevelAncestor();
			frame.pack();
			frame.setLocationRelativeTo(null);
		}
		
		long start = System.nanoTime();
		long elapsed;
		long wait;
		
		long total = 0;
		int frames = 0;
		
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			frames++;
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			total += System.nanoTime() - start;
			if(total > 1000000000) {
				averageFPS = frames;
				total -= 1000000000;
				frames = 0;
			}
			
		}
		
	}
	
	private void update() {
		gsm.update();
		handleInput();
		Keys.update();
		Mouse.update();
	}
	
	private void draw() {
		gsm.draw(g);
		g.setColor(java.awt.Color.RED);
		g.drawString(Integer.toString(averageFPS), 300, 230);
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, (int)(WIDTH * SCALE), (int)(HEIGHT * SCALE), null);
		g2.dispose();
		if(screenshot) {
			screenshot = false;
			try {
				java.io.File out = new java.io.File("screenshot " + System.nanoTime() + ".gif");
				javax.imageio.ImageIO.write(image, "gif", out);
			}
			catch(Exception e) {}
		}
	}
	
	private void handleInput() {
		if(Keys.isPressed(Keys.F4)) {
			if(SCALE == 1) SCALE = 2;
			else SCALE = 1;
			setPreferredSize(new Dimension((int)(WIDTH * SCALE), (int)(HEIGHT * SCALE)));
			image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			g = (Graphics2D) image.getGraphics();
			javax.swing.JFrame frame = (javax.swing.JFrame) getTopLevelAncestor();
			frame.pack();
			frame.setLocationRelativeTo(null);
		}
	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		if(key.isControlDown()) {
			if(key.getKeyCode() == KeyEvent.VK_S) {
				screenshot = true;
				return;
			}
		}
		Keys.keySet(key.getKeyCode(), true);
	}
	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);
	}
	
	public void mousePressed(MouseEvent me) {
		Mouse.setAction(Mouse.PRESSED);
	}
	
	public void mouseReleased(MouseEvent me) {
		Mouse.setAction(Mouse.RELEASED);
	}
	
	public void mouseMoved(MouseEvent me) {
		me.translatePoint(-me.getX() + me.getX() / SCALE, -me.getY() + me.getY() / SCALE);
		Mouse.setPosition(me.getX(), me.getY());
	}
	
	public void mouseDragged(MouseEvent me) {
		me.translatePoint(-me.getX() + me.getX() / SCALE, -me.getY() + me.getY() / SCALE);
		Mouse.setAction(Mouse.PRESSED);
		Mouse.setPosition(me.getX(), me.getY());
	}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	public void mouseClicked(MouseEvent me) {}

}