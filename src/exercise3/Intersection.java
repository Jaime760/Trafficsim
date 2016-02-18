package exercise3;

import java.awt.Color;
import java.util.ArrayList;

import exercise3.Car.Direction;
import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Intersection extends Actor {
	private int lightCounter = 0;
	private static final int GREEN_COUNT = 100;
	private static final int YELLOW_COUNT = 50;
	private TrafficLights[] tf = new TrafficLights[4];

	ArrayList<IntersectionListener> Outer = new ArrayList<IntersectionListener>();
	ArrayList<IntersectionListener> prevOuter = new ArrayList<IntersectionListener>();
	ArrayList<IntersectionListener> Inner = new ArrayList<IntersectionListener>();
	ArrayList<IntersectionListener> prevInner = new ArrayList<IntersectionListener>();


	public TrafficLights.Color verticalColor;
	public TrafficLights.Color horizontalColor;

	public Intersection() {
		GreenfootImage intersection = new GreenfootImage(Road.ROAD_WIDTH, Road.ROAD_WIDTH);
		intersection.setColor(Color.BLACK);
		//intersection.fill();
		setImage(intersection);			
	}

	public void addLights() {
		verticalColor = TrafficLights.Color.GREEN;
		horizontalColor = TrafficLights.Color.RED;

		tf[0] = new TrafficLights(verticalColor, Direction.EAST);
		getWorld().addObject(tf[0], this.getX(), getY() + Road.ROAD_WIDTH / 2 + tf[0].getImage().getHeight() / 2);

		tf[1] = new TrafficLights(horizontalColor, Direction.SOUTH);
		tf[1].setRotation(90);		
		getWorld().addObject(tf[1], this.getX() - Road.ROAD_WIDTH / 2 - tf[1].getImage().getHeight() / 2, getY());

		tf[2] = new TrafficLights(verticalColor, Direction.WEST);
		tf[2].setRotation(180);
		getWorld().addObject(tf[2], this.getX(), getY() - Road.ROAD_WIDTH / 2 - tf[2].getImage().getHeight() / 2);

		tf[3] = new TrafficLights(horizontalColor, Direction.NORTH);
		tf[3].setRotation(270);
		getWorld().addObject(tf[3], this.getX() + Road.ROAD_WIDTH / 2 + tf[3].getImage().getHeight() / 2, getY());

	}

	public void act() {

		notifyApproachingCars();
		
		notifyEnteringCars();
		
		notifyExitingCars();

		lightCounter++;
		for(int x = 0; x < tf.length; x++) {
			if(tf[x].getRotation() == 0 || tf[x].getRotation() == 180) {
				switch(horizontalColor) {
				case GREEN:
					if(lightCounter == GREEN_COUNT) {
						horizontalColor = TrafficLights.Color.YELLOW;
						lightCounter = 0;

					}
					break;
				case YELLOW:
					if(lightCounter == YELLOW_COUNT) {
						horizontalColor = TrafficLights.Color.RED;
						lightCounter = 0;

					}
					break;
				case RED:
					if(verticalColor.equals(TrafficLights.Color.RED)) {
						horizontalColor = TrafficLights.Color.GREEN;
						lightCounter = 0;

					}
					break;
				}
				tf[x].setColor(horizontalColor);
			}

			if(tf[x].getRotation() == 90 || tf[x].getRotation() == 270) {
				switch(verticalColor) {
				case GREEN:
					if(lightCounter == GREEN_COUNT) {
						verticalColor = TrafficLights.Color.YELLOW;
						lightCounter = 0;
					}
					break;
				case YELLOW:
					if(lightCounter == YELLOW_COUNT) {
						verticalColor = TrafficLights.Color.RED;
						lightCounter = 0;

					}
					break;
				case RED:
					if(horizontalColor.equals(TrafficLights.Color.RED)) {
						verticalColor = TrafficLights.Color.GREEN;
						lightCounter = 0;

					}
					break;
				}
				tf[x].setColor(verticalColor);

			}

		}

	}

	private void notifyEnteringCars() {
		Inner = (ArrayList<IntersectionListener>) this.getIntersectingObjects(IntersectionListener.class);
		for(IntersectionListener Innerlist : Inner) {
			if(!prevInner.contains(Innerlist)) {
				Innerlist.enteringIntersection(this);
			}
		}
	}

	private void notifyExitingCars() {
		Inner = (ArrayList<IntersectionListener>) this.getIntersectingObjects(IntersectionListener.class);
		for(IntersectionListener Exitinglist : prevInner) {
			if(!Inner.contains(Exitinglist)) {
				Exitinglist.leavingIntersection(this);
			}
		}
		prevInner = Inner;
	}

	private void notifyApproachingCars() {
		Outer = (ArrayList<IntersectionListener>) this.getObjectsInRange(Road.ROAD_WIDTH, IntersectionListener.class);
		for(IntersectionListener Outerlist : Outer) {
			if(!prevOuter.contains(Outerlist)) {
				Outerlist.approachingIntersection(this);
			}
		}
		prevOuter = Outer;
	}

	public exercise3.TrafficLights.Color getLightState(Direction curDirection) {
		if(curDirection == Direction.NORTH || curDirection == Direction.SOUTH) {
			return horizontalColor;
		}
		else 
			return verticalColor;
	}
}
