package com.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Player {
	public double x, y, radius_x, radius_y;
	public int width, height, radius_width, radius_height;
	public double xspeed, yspeed, delta_x, delta_y;

	public int score = 0;
	public String type;

	private boolean shot = false;

	private int up, down, left, right, fire;

	public Player(int x, int y, int h, int w, int up, int down, int left, int right, int fire) {
		this.x = (double) x;
		this.y = (double) y;
		this.width = w;
		this.height = h;
		this.yspeed = 2;
		this.xspeed = 2;
		this.radius_width = 3 * width;
		this.radius_height = 3 * height;
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.fire = fire;
		type = "HUMAN";
	}

	public Player(int x, int y, int h, int w) {
		this.x = (double) x;
		this.y = (double) y;
		this.width = w;
		this.height = h;
		this.yspeed = 3;
		this.xspeed = 3;
		this.radius_width = 3 * width;
		this.radius_height = 3 * height;
		type = "CPU";
	}

	public void tick() {
		delta_y = 0;
		delta_x = 0;

		if (type == "HUMAN") {

			if (Keyboard.keys[up]) {
				delta_y = -yspeed;
			}
			if (Keyboard.keys[down]) {
				delta_y = yspeed;
			}
			if (Keyboard.keys[right]) {
				delta_x = xspeed;
			}
			if (Keyboard.keys[left]) {
				delta_x = -xspeed;
			}

			if (Keyboard.keys[fire]) {
				if (Game.ball.y + Game.ball.height / 2 > radius_y
						&& Game.ball.y + Game.ball.height / 2 < radius_y + radius_height
						&& Game.ball.x + Game.ball.width / 2 > radius_x
						&& Game.ball.x + Game.ball.width / 2 < radius_x + radius_width) {
					Game.ball.xspeed = 0.2 * ((Game.ball.x + (Game.ball.width / 2)) - (x + width / 2));
					Game.ball.yspeed = 0.2 * ((Game.ball.y + (Game.ball.height / 2)) - (y + height / 2));
				}
			}
		}

		if (type == "CPU") {
			shot = false;
			CPUtick();

			if (shot) {
				if (Game.ball.y + Game.ball.height / 2 > radius_y
						&& Game.ball.y + Game.ball.height / 2 < radius_y + radius_height
						&& Game.ball.x + Game.ball.width / 2 > radius_x
						&& Game.ball.x + Game.ball.width / 2 < radius_x + radius_width) {
					Game.ball.xspeed = 0.2 * ((Game.ball.x + (Game.ball.width / 2)) - (x + width / 2));
					Game.ball.yspeed = 0.2 * ((Game.ball.y + (Game.ball.height / 2)) - (y + height / 2));
				}
			}
		}
		x += delta_x;
		y += delta_y;

		/*
		 * if (x < 0) x = 0; if (x + width > Game.width) x = Game.width - width;
		 * if (y < 0) y = 0; if (y + height > Game.height) y = Game.height -
		 * height;
		 */

		radius_x = x - (width);
		radius_y = y - (height);
	}

	public void CPUtick() {
		int distance = 20;
		int radius = 20;

		if (y + height / 2 < f(Game.ball.x + distance))
			delta_y = yspeed;
		if (y + height / 2 > f(Game.ball.x + distance))
			delta_y = -yspeed;
		if (x + width / 2 > Game.ball.x + distance)
			delta_x = -xspeed;
		if (x + width / 2 < Game.ball.x + distance)
			delta_x = xspeed;

		if (x + width / 2 < Game.ball.x + distance + radius 
				&& x + width / 2 > Game.ball.x + distance - radius 
				&& y + height / 2 < f(Game.ball.x + distance) + radius
				&& y + height / 2 > f(Game.ball.x + distance) - radius) {
			shot = true;
			System.out.println("teste");
		}

	}

	double f(double p) {
		double ball_y = (Game.ball.y + Game.ball.height / 2);
		double ball_x = (Game.ball.x + Game.ball.width / 2);

		double m = (ball_y - Game.height / 2) / (ball_x - 0);
		double y = Game.height / 2 + m * p;

		return y;
	}

	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		if (Keyboard.keys[fire] || shot)
			g2.setColor(Color.RED);
		g2.fill(new Rectangle2D.Double(x, y, width, height));
		g2.draw(new Rectangle2D.Double(radius_x, radius_y, radius_width, radius_height));

	}
}
