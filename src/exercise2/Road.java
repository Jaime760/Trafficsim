package exercise2;

import java.awt.Color;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Road extends Actor {

	public static final int ROAD_WIDTH = 50;

	public Road() {
		GreenfootImage image = new GreenfootImage(TrafficWorld.WIDTH, ROAD_WIDTH);
		image.setColor(Color.gray);
		image.fill();
		this.setImage(image);

	}

}
