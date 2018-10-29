package com.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Ball {
	public double xspeed, yspeed;
	public double acceleration;

	public double x, y;
	public int width, height;

	public Ball(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;

		acceleration = 0.99;
	}

	public void tick() {
		if (x < 0 || x + width > Game.width) {
			xspeed *= -1;
		}
		if (y < 0 || y + height > Game.height) {
			yspeed *= -1;
		}

		xspeed *= acceleration;
		yspeed *= acceleration;

		x += xspeed;
		y += yspeed;

		if (y >= 130 && y <= 250) {
			if (x <= 0){
				Game.player2.score++;
				Game.restart();
			}
			if (x + width >= Game.width){
				Game.player.score++;
				Game.restart();
			}
		}

	}

	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLUE);
		g2.fill(new Rectangle2D.Double(x, y, width, height));
	}

}
