package com.neet.managers;

import java.awt.image.BufferedImage;

public class Animation {
	
	private BufferedImage[] frames;
	private int currentFrame;
	private int numFrames;
	
	private int count;
	private int delay;
	
	private int timesPlayed;
	private boolean reverse;
	
	public Animation() {
		timesPlayed = 0;
	}
	
	public void setFrames(BufferedImage[] frames) {
		setFrames(frames, 6);
	}
	
	public void setFrames(BufferedImage[] frames, int d) {
		setFrames(frames, d, frames.length);
	}
	
	public void setFrames(BufferedImage[] frames, int d, int n) {
		this.frames = frames;
		delay = d;
		numFrames = n;
		currentFrame = 0;
		count = 0;
		timesPlayed = 0;
		reverse = false;
	}
	
	public void setDelay(int i) { delay = i; }
	public void setFrame(int i) { currentFrame = i; }
	public void setNumFrames(int i) { numFrames = i; }
	
	public void setReverse(boolean b) {
		if(reverse == b) return;
		reverse = b;
		currentFrame = numFrames - 1;
	}
	
	public void update() {
		
		if(delay == -1) return;
		
		count++;
		
		if(count == delay) {
			if(reverse) {
				currentFrame--;
			}
			else {
				currentFrame++;
			}
			count = 0;
		}
		if(!reverse && currentFrame == numFrames) {
			currentFrame = 0;
			timesPlayed++;
		}
		else if(reverse && currentFrame == 0) {
			currentFrame = numFrames - 1;
			timesPlayed++;
		}
		
	}
	
	public int getFrame() { return currentFrame; }
	public int getCount() { return count; }
	public BufferedImage getImage() { return frames[currentFrame]; }
	public boolean hasPlayedOnce() { return timesPlayed > 0; }
	public boolean hasPlayed(int i) { return timesPlayed == i; }
	
}