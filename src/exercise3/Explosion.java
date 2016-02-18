package exercise3;

import greenfoot.Actor;

public class Explosion extends Actor {
	private String[] explosions = {
			"images//explosion1.png",
			"images//explosion2.png",
			"images//explosion3.png",
	};
	private final int ex1 = 10;
	private final int ex2 = 25;
	private final int ex3 = 35;
	private int count = 0;
	
	public Explosion() {
		this.setImage("images//explosion1.png");
	}
	public void act() {
		count++;
		if(count == ex1) {
			this.setImage("images//explosion2.png");
		}
		if(count == ex2) {
			this.setImage("images//explosion3.png");
		}
		if(count == ex3) {
			this.getWorld().removeObject(this);
		}
	}
}
