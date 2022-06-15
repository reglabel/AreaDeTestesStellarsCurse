package com.rbcl.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.rbcl.entities.Enemy;
import com.rbcl.entities.Entity;
import com.rbcl.entities.Player;
import com.rbcl.entities.WeaponShoot;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private static JFrame frame;
	
	private BufferedImage image;
	
	public static int WIDTH = 240;
	public static int HEIGHT = 160;
	public static final int SCALE = 3;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<WeaponShoot> weaponShoots;
	
	
	public static Player player;
	
	private boolean isRunning;
	
	public int mx, my;
	
	public static Random rand;
	
	public static double tempoJogo;
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public Game() {
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		this.initFrame();
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		weaponShoots = new ArrayList<WeaponShoot>();
		
		player = new Player(120-16, Game.HEIGHT-30, 16, 16, 1);
		entities.add(player);
		
		tempoJogo = 0;
	}
	
	public void initFrame() {
		frame = new JFrame("will you play along?");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		//frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
			frame.setTitle("here we go!");
			
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			
			for(int i = 0; i < weaponShoots.size(); i++) {
				weaponShoots.get(i).tick();
			}
			
			for(int i = 0; i < enemies.size(); i++) {
				Entity e = enemies.get(i);
				e.tick();
			}
			
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.createGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		for(int i = 0; i < weaponShoots.size(); i++) {
			weaponShoots.get(i).render(g);
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(g);
		}
		
		
		g.dispose(); 
		g = bs.getDrawGraphics();
		

		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		bs.show();
	}

	@Override
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double timer = System.currentTimeMillis();
		int ondas = 0;
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				if (player.life == 0) {
					System.out.println("Você morreu!");
					System.exit(0);
				}
				
				if(player.ammo == 0) {
					player.ammo = 9999;
				}
				
				tick();
				render();
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				tempoJogo+=1;
				
				if(ondas == 0){
					if(tempoJogo==5) {
						enemies.add(new Enemy(-32-32, Game.HEIGHT-30, 16, 16, 0.3, 1, 3, 3));
						enemies.add(new Enemy(-64-16, Game.HEIGHT-30, 16, 16, 0.4, 2, 5, 5));
						enemies.add(new Enemy(-112-32, Game.HEIGHT-30, 16, 16, 0.5, 3, 7, 7));
						enemies.add(new Enemy(-80-16, Game.HEIGHT-30, 16, 16, 0.3, 1, 3, 3));
						enemies.add(new Enemy(-96-32, Game.HEIGHT-30, 16, 16, 0.3, 1, 3, 3));
						
						enemies.add(new Enemy(Game.WIDTH+80+16, Game.HEIGHT-30, 16, 16, 0.5, 1, 3, 3));
						enemies.add(new Enemy(Game.WIDTH+112+32, Game.HEIGHT-30, 16, 16, 0.4, 2, 5, 5));
						enemies.add(new Enemy(Game.WIDTH+64+16, Game.HEIGHT-30, 16, 16, 0.5, 1, 3, 3));
						enemies.add(new Enemy(Game.WIDTH+96+16, Game.HEIGHT-30, 16, 16, 0.4, 2, 5, 5));
						enemies.add(new Enemy(Game.WIDTH+32+32, Game.HEIGHT-30, 16, 16, 0.3, 3, 7, 7));
						
						ondas+=1;
					}
				}
			}
		}
		
		stop();
	}
	
	

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if((e.getKeyCode() == KeyEvent.VK_RIGHT) 
				|| (e.getKeyCode() == KeyEvent.VK_D)) {
			player.right = true;
		} else if((e.getKeyCode() == KeyEvent.VK_LEFT) 
				|| (e.getKeyCode() == KeyEvent.VK_A)) {
			player.left = true;
		}
		
		if((e.getKeyCode() == KeyEvent.VK_UP) 
				|| (e.getKeyCode() == KeyEvent.VK_W)) {
			player.up = true;
			
			
		} else if((e.getKeyCode() == KeyEvent.VK_DOWN) 
				|| (e.getKeyCode() == KeyEvent.VK_S)) {
			player.down = true;
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.shoot = true;
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if((e.getKeyCode() == KeyEvent.VK_RIGHT) 
				|| (e.getKeyCode() == KeyEvent.VK_D)) {
			player.right = false;
		} else if((e.getKeyCode() == KeyEvent.VK_LEFT) 
				|| (e.getKeyCode() == KeyEvent.VK_A)) {
			player.left = false;
		}
		
		if((e.getKeyCode() == KeyEvent.VK_UP) 
				|| (e.getKeyCode() == KeyEvent.VK_W)) {
			player.up = false;
		} else if((e.getKeyCode() == KeyEvent.VK_DOWN) 
				|| (e.getKeyCode() == KeyEvent.VK_S)) {
			player.down = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Game.player.mouseShoot = true;
		player.mx = (e.getX()/3);
		player.my = (e.getY()/3);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mx = e.getX();
		this.my= e.getY();
	}
}

