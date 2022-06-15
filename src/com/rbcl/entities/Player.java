package com.rbcl.entities;

import java.awt.Color;
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.rbcl.main.Game;

public class Player extends Entity {
		
	public boolean right, left, up, down, moved;
	public boolean isDamaged, shoot, mouseShoot;
	//private double speed;
	
	public double life, maxLife;
	public int ammo, damageFrames;
	public int mx, my;
	private int ultimaOcorrencia = 0;
	public int nivelArma;
	
	public Player(int x, int y, int width, int height, int nivelArma) {
		super(x, y, width, height);
		
		this.right = false;
		this.left = false;
		this.up = false;
		this.down = false;
		this.moved = false;
		this.isDamaged = false;
		this.shoot = false;
		this.mouseShoot = false;
		this.ammo=9999;
		
		this.speed = 0.9;
		
		life = 522;
		maxLife = 522;
		
	}
	
	public void render(Graphics g) {
		
			if(right) {
				ultimaOcorrencia = 1;
			} else if(left) {
				ultimaOcorrencia = 2;
			} else if(up) {
				ultimaOcorrencia = 3;
			} else if(down) {
				ultimaOcorrencia = 4;
			}
			
			
			g.setColor(Color.blue);
			g.fillRect((int)x, (int)y, 16, 16);
		
	}
	
	public void tick() {
		moved = false;
		
		
		
		if(right) {
			if((x+speed) >= (Game.WIDTH-16)) {
				moved = true;
				x-=speed;
			} else {
				moved = true;
				x += speed;
			}
		} else if (left) {
			if((x-speed) <= 0) {
				moved = true;
				x += speed;
			} else {
				moved = true;
				x -= speed;
			}
		}/* else if (up) {
			moved = true;
			y -= speed;
		} else if (down) {
			moved = true;
			y += speed;
		}*/
		
		if(mouseShoot) {
			mouseShoot = false;

			if(ammo > 0) {
				ammo--;
				int posX = 0, posY = 0;
				double angle = Math.atan2(my - (this.getY() + 8), mx - (this.getX() + 8));

				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				
				if(ultimaOcorrencia == 1) {
					posY = 7;
					posX = 15;
					
					if(dx < 0) {
						dx *= -1;
					}
				} else if (ultimaOcorrencia == 2) {
					posY = 7;
					posX = -3;
					
					if(dx >= 0) {
						dx *= -1;
					}
				} else if(ultimaOcorrencia == 3) {
					posX = 11;
					posY = -2;
					
					if(dy >= 0) {
						dy *= -1;
					}
				} else if (ultimaOcorrencia == 4 || ultimaOcorrencia == 0){
					posX = 9;
					posY = 15;
					
					if(dy < 0) {
						dy *= -1;
					}
				}
				
				WeaponShoot shoot = new WeaponShoot(this.getX() + posX, this.getY() + posY, 4, 4, dx, dy, 25, 4);
				Game.weaponShoots.add(shoot);
			}
			
		}
		
		if(this.life <= 0) {
			this.life = 0;
		}
		
	}

}
