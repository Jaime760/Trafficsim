package exercise1;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Car extends Actor {

	private String[] carColors = {
			"images\\topCarRed.png",
			"images\\topCarBlue.png",
			"images\\topCarPurple.png",
			"images\\topCarYellow.png"
	};

	public Car() {
		GreenfootImage image = new GreenfootImage(carColors[TrafficWorld.gen.nextInt(4)]);
		this.setImage(image);
	}

	public void act() {
		move(1);
		if(isAtEdge()) {
			if(getRotation() == 0) {
				setLocation(0, getY());
			}

			if(getRotation() == 180) {
				setLocation(TrafficWorld.WIDTH, getY());

			}

			if(getRotation() == 90) {
				setLocation(getX(), 0);
			}

			if(getRotation() == 270) {
				setLocation(getX(), TrafficWorld.HEIGHT);
			}
		}

	}
}
