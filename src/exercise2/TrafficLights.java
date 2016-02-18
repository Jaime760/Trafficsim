package exercise2;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class TrafficLights extends Actor {
	private Car.Direction lightDirection;
	
	public static enum Color {
		RED,
		YELLOW,
		GREEN
	}
	private String[] colorImages = {"images/trafficLightRed.png", "images/trafficLightYellow.png", "images/trafficLightGreen.png"};

	public TrafficLights(Color initialColor, Car.Direction lightDirection) {
		GreenfootImage image = new GreenfootImage(colorImages[initialColor.ordinal()]);
		this.setImage(image);
		this.lightDirection = lightDirection;
	}

	public void setColor(Color newcolor) {
		setImage(colorImages[newcolor.ordinal()]);
	}
	
	public Car.Direction getDirection() {
		return lightDirection;
	}
	
}
