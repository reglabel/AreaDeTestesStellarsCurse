package com.rbcl.entities;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.rbcl.main.Game;


public class Entity {
	
	//protected int speed = 1; -> p/ AStar
	protected double speed = 0.3;
	protected double x, y;
	protected int z;
	protected int width, height;
	protected int maskx, masky, mwidth, mheight;
	protected int frames, maxFrames, index, maxIndex;
	
	private BufferedImage sprite;

	public Entity(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX(), this.getY(), null);
	}

	public void tick() {
		
	}
	
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
	}
	
	public boolean isColliding(int xNext, int yNext) {
		Rectangle currentEnemy = new Rectangle(xNext + maskx, yNext + masky, mwidth, mheight);
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy en = Game.enemies.get(i);
			if(en == this) {
				continue;
			}
			
			Rectangle targetEnemy = new Rectangle(en.getX() + maskx, en.getY() + masky, mwidth, mheight);
			if(currentEnemy.intersects(targetEnemy)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX()+e1.maskx, e1.getY()+e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX()+e2.maskx, e2.getY()+e2.masky, e2.mwidth, e2.mheight);

		if(e1Mask.intersects(e2Mask) && e1.z == e2.z) {
			return true;
		}
		return false;
	}
	
	public int getX() {
		return (int)this.x;
	}

	public int getY() {
		return (int)this.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
