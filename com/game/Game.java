package com.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final int width = 600;
	public static final int height = 382;
	public static final String title = "Soccer!";
	
	private boolean running;
	
	Thread thread;
	Image background;
	
	public static Ball ball = new Ball(width/2 - 5, height/2 - 5, 10, 10);
	public static Player player = new Player(width/2 - 200 - 20, height/2 - 10, 20, 20, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
	public static Player player2 = new Player(width/2 + 200, height/2 - 10, 20, 20);
	
	public Game(){
		Dimension d = new Dimension(width, height);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setBackground(new Color(0, 100, 0));
		this.addKeyListener(new Keyboard());
		
		try {
			background = ImageIO.read(new File("field.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void start(){
		if(running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick(){
		player.tick();
		player2.tick();
		ball.tick();
		
		if(Keyboard.keys[KeyEvent.VK_R]){
			player.score = player2.score = 0;
			restart();
		}
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		g.drawImage(background, 0, 0, null);
		
		player.render(g);
		player2.render(g);
		ball.render(g);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", 2, 40));
		g.drawString(player.score + "   " + player2.score, width/2-40, 50);
		
		g.dispose();
		bs.show();
	}
	
	public void run(){
		this.requestFocus();
		long time = 0;
		int framerate = 16;
		while(running){
			if(time + framerate > System.currentTimeMillis())
				continue;
			time = System.currentTimeMillis();
			
			tick();
			render();
		}
	}
	
	public static void restart(){
		ball.x = width/2 - 5;
		ball.y = height/2 - 5;
		ball.xspeed = 0;
		ball.yspeed = 0;
		
		player.x = width/2 - 100 - 20;
		player.y = height/2 - 10;
		
		player2.x = width/2 + 100;
		player2.y = height/2 - 10;
		
	}
	
	public static void main(String[] args){
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.start();
	}
	
}
