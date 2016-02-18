package exercise3;

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

	public int randomDirection() {
		int num = TrafficWorld.gen.nextInt(4);
		int rotation = 0;

		switch(num) {
		case 0:
			rotation = 0;
			break;
		case 1:
			rotation = 180;
			break;
		case 2:
			rotation = 90;
			break;
		case 3:
			rotation = 270;
			break;
		}
		return rotation;
	}

	private String[] carColors = {
			"images\\topCarRed.png",
			"images\\topCarBlue.png",
			"images\\topCarPurple.png",
			"images\\topCarYellow.png"
	};

	CarState state = CarState.OUTSIDE;
	Intersection CurIntersection = null;
	Direction curDirection;
	private static final int NormSpeed = 2;
	private static final int SlowSpeed = 1;
	private static final int Stopped = 0;
	private static int tcounter = 50;
	private static boolean turn = true;
	private static boolean tdirection = TrafficWorld.gen.nextBoolean();
	private int MoveSpeed = NormSpeed;
	private int num = TrafficWorld.gen.nextInt(100);
	private Car bugatti;


	public Car() {
		GreenfootImage image = new GreenfootImage(carColors[TrafficWorld.gen.nextInt(4)]);
		this.setImage(image);
		state = CarState.OUTSIDE;
	}

	public void act() {
		move(MoveSpeed);
		num = TrafficWorld.gen.nextInt(100);
		if(this.getWorld().getObjects(Car.class).toArray().length < 8) {
			if(num == 10) {
				this.addCar();
			}
		}
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

		setCurDirection();

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

		if(!turn) {
			turn = TrafficWorld.gen.nextBoolean();
		}
		else {
			if(tcounter < 0) {
				tcounter = 5;
				if(tdirection) {
					turnLeft();
				}
				else {
					turnRight();
				}
			}

		}

		tcounter--;
		try {
			bugatti = (Car) this.getOneIntersectingObject(Car.class);
			if(bugatti != null) {
				throw new Exception();
			}
		}
		catch(Exception e) {
			Explosion explosion = new Explosion();
			this.getWorld().addObject(explosion, bugatti.getX(), bugatti.getY());
			this.getWorld().removeObject(bugatti);
			this.getWorld().removeObject(this);
		}
	}
	
	

	public void turnLeft() {
		if(curDirection == Direction.EAST) {
			if(this.getX() == this.CurIntersection.getX() - (Road.ROAD_WIDTH / 4)) {
				this.turn(90);
			}
		}

		if(curDirection == Direction.WEST) {
			if(this.getX() == this.CurIntersection.getX() + (Road.ROAD_WIDTH / 4)) {
				this.turn(90);
			}
		}

		if(curDirection == Direction.NORTH) {
			if(this.getY() == this.CurIntersection.getY() + (Road.ROAD_WIDTH / 4)) {
				this.turn(90);
			}
		}

		if(curDirection == Direction.SOUTH) {
			if(this.getY() == this.CurIntersection.getY() - (Road.ROAD_WIDTH / 4)) {
				this.turn(90);
			}
		}
		turn = false;		
		tdirection = TrafficWorld.gen.nextBoolean();
	}

	public void turnRight() {
		if(curDirection == Direction.EAST) {
			if(this.getX() == this.CurIntersection.getX() + (Road.ROAD_WIDTH / 4)) {
				this.turn(-90);
			}
		}

		if(curDirection == Direction.WEST) {
			if(this.getX() == this.CurIntersection.getX() - (Road.ROAD_WIDTH / 4)) {
				this.turn(-90);
			}
		}

		if(curDirection == Direction.NORTH) {
			if(this.getY() == this.CurIntersection.getY() - (Road.ROAD_WIDTH / 4)) {
				this.turn(-90);
			}
		}

		if(curDirection == Direction.SOUTH) {
			if(this.getY() == this.CurIntersection.getY() + (Road.ROAD_WIDTH / 4)) {
				this.turn(-90);
			}
		}
		turn = false;
		tdirection = TrafficWorld.gen.nextBoolean();
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

	private void setCurDirection() {
		if(getRotation() == 0) {
			curDirection = Direction.EAST;
		}

		if(getRotation() == 180) {
			curDirection = Direction.WEST;
		}

		if(getRotation() == 90) {
			curDirection = Direction.SOUTH;
		}

		if(getRotation() == 270) {
			curDirection = Direction.NORTH;
		}
	}

	public void addCar() {
		int carX = 0;
		int carY = 0;

		Car car = new Car();
		car.setRotation(car.randomDirection());

		if(car.getRotation() == 0) {
			carY = ((TrafficWorld.hGap_size + Road.ROAD_WIDTH) * TrafficWorld.gen.nextInt(TrafficWorld.roads.length)) + Road.ROAD_WIDTH / 2 + TrafficWorld.CAR_OFFSET;
		}

		if(car.getRotation() == 180) {
			carY = ((TrafficWorld.hGap_size + Road.ROAD_WIDTH) * TrafficWorld.gen.nextInt(TrafficWorld.roads.length)) + Road.ROAD_WIDTH / 2 - TrafficWorld.CAR_OFFSET;
			carX = TrafficWorld.WIDTH;
		}

		if(car.getRotation() == 90) {
			carX = ((TrafficWorld.vGap_size + Road.ROAD_WIDTH) * TrafficWorld.gen.nextInt(TrafficWorld.road1.length)) + Road.ROAD_WIDTH / 2 - TrafficWorld.CAR_OFFSET;
		}

		if(car.getRotation() == 270) {
			carX = ((TrafficWorld.vGap_size + Road.ROAD_WIDTH) * TrafficWorld.gen.nextInt(TrafficWorld.road1.length)) + Road.ROAD_WIDTH / 2 +  TrafficWorld.CAR_OFFSET;
			carY = TrafficWorld.HEIGHT;
		}

		this.getWorld().addObject(car, carX, carY);

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
