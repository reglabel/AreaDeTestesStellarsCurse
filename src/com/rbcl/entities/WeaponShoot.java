package com.rbcl.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.rbcl.main.Game;

public class WeaponShoot extends Entity {
	
	private double dx, dy;
	public int life, curLife, dano;
	private double spd;
	
	public WeaponShoot(int x, int y, int width, int height, double dx, double dy, int life, int dano) {
		super(x, y, width, height);
		this.dx = dx;
		this.dy = dy;
		this.spd = 2;
		this.life = life;
		this.dano = dano;
		this.curLife = 0;
	}

	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		
		if(curLife == life) {
			Game.weaponShoots.remove(this);
			return;
		}
		
		
	}

	
	
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(this.getX(), this.getY(), width, height);
	}
}
