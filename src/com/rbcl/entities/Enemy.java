package com.rbcl.entities;

import java.awt.Color;
//import java.awt.Color;
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
//import java.util.Random;

import com.rbcl.main.Game;

public class Enemy extends Entity{

	private double life, atck;
	private int color;
	
	public Enemy(int x, int y, int width, int height, double spd, int color, int life, int atck) {
		super(x, y, width, height);
		
		setMask(3,3,10,10);

		this.speed = spd;
		this.color = color;
		this.life = life;
		this.atck = atck;
		
	}
	
	public void tick() {
		
		int xDoPlayer = Game.player.getX();
		int yDoPlayer = Game.player.getY();
		int xDoEnemy = this.getX();
		int yDoEnemy = this.getY();
		int subir = (int) (y - speed);
		int descer = (int) (y + speed);
		int direita = (int) (x + speed);
		int esquerda = (int) (x - speed);
		
		if(this.calculateDistance(xDoEnemy, yDoEnemy, xDoPlayer, yDoPlayer) > 16) {
			
				if(xDoEnemy < xDoPlayer) {
					x += speed;
				} else if (xDoEnemy > xDoPlayer){
					x -= speed;
				}
				
				else if(yDoEnemy < yDoPlayer) {
					y += speed;
				} else if (yDoEnemy > yDoPlayer){
					y -= speed;
				}
			} else {
				Game.player.life -= atck;
				Game.player.isDamaged = true;
				
			}
		
		collidingShoot();
		
		if(life <= 0) {
			destroySelf();
			return;
		}
		
		
	}
	
	public void destroySelf() {
		Game.entities.remove(this);
		Game.enemies.remove(this);
	}
	
	public void collidingShoot() {
		for(int i = 0; i < Game.weaponShoots.size(); i++) {
			Entity e =Game.weaponShoots.get(i);
			if(Entity.isColliding(this, e)) {
				life = life - Game.weaponShoots.get(i).dano;
				Game.weaponShoots.remove(e);
				return;
			}
		}
	}
	
	public boolean isCollindingWithPlayer() {
		Rectangle currentEnemy = new Rectangle(this.getX() + maskx, this.getY() + masky, mwidth, mheight);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		
		return currentEnemy.intersects(player);
	}
	
	
	public void render(Graphics g) {
		if(color == 1) {
			g.setColor(Color.green);
		} else if (color == 2) {
			g.setColor(Color.yellow);
		}else if (color == 3) {
			g.setColor(Color.red);
		}
		
		g.fillRect((int)x, (int)y, 16, 16);
	}
	
}
