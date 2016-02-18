package exercise2;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Car extends Actor implements IntersectionListener{

	public static enum CarState {
		OUTSIDE,
		APPROACHING,
		INSIDE
	};

	public static enum Direction {
		NORTH,
		SOUTH,
		EAST,
		WEST
	};

	private String[] carColors = {
			"images\\topCarRed.png",
			"images\\topCarBlue.png",
			"images\\topCarPurple.png",
			"images\\topCarYellow.png"
	};

	CarState state;
	Intersection CurIntersection;
	Direction curDirection;
	private static final int NormSpeed = 2;
	private static final int SlowSpeed = 1;
	private static final int Stopped = 0;
	private int MoveSpeed = NormSpeed;


	public Car() {
		GreenfootImage image = new GreenfootImage(carColors[TrafficWorld.gen.nextInt(4)]);
		this.setImage(image);
		state = CarState.OUTSIDE;
	}

	public void act() {
		move(MoveSpeed);
		if(isAtEdge()) {
			if(getRotation() == 0) {
				setLocation(0, getY());
				curDirection = Direction.EAST;
			}

			if(getRotation() == 180) {
				setLocation(TrafficWorld.WIDTH, getY());
				curDirection = Direction.WEST;
			}

			if(getRotation() == 90) {
				setLocation(getX(), 0);
				curDirection = Direction.SOUTH;
			}

			if(getRotation() == 270) {
				setLocation(getX(), TrafficWorld.HEIGHT);
				curDirection = Direction.NORTH;
			}
		}

		switch(state) {
		case OUTSIDE:
			speedingUp();
			break;

		case APPROACHING:
			switch(CurIntersection.getLightState(curDirection)) {
			
			case GREEN:
				speedingUp();
				break;

			case YELLOW:
				slowingDown();
				break;

			case RED:
				slowingDownFast();
				break;
			}
			break;
			
		case INSIDE:
			speedingUp();
			break;
		}

	}
	public void speedingUp() {
		MoveSpeed++;
		if(MoveSpeed > NormSpeed) {
			MoveSpeed = NormSpeed;
		}
	}
	
	public void slowingDown() {
		MoveSpeed--;
		if(MoveSpeed < 2) {
			MoveSpeed = SlowSpeed;
		}
	}
	
	public void slowingDownFast() {
		MoveSpeed -= 1;
		if(MoveSpeed < 0) {
			MoveSpeed = Stopped;
		}
	}

	@Override
	public void enteringIntersection(Intersection intersection) {
		state = CarState.INSIDE;
		CurIntersection = intersection;
	}

	@Override
	public void approachingIntersection(Intersection intersection) {
		state = CarState.APPROACHING;
		CurIntersection = intersection;
	}

	@Override
	public void leavingIntersection(Intersection intersection) {
		state = CarState.OUTSIDE;
		CurIntersection = intersection;
	}
}
