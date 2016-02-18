package exercise3;

import java.awt.Color;
import java.util.Random;

import exercise3.TrafficWorld;
import greenfoot.GreenfootImage;
import greenfoot.World;

public class TrafficWorld extends World {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 750;
	public static final int CELL_SIZE = 1;
	public static final int CAR_OFFSET = 10;
	public static final Road[] roads = new Road[5];
	public static final Road[] road1 = new Road[7];
	public static int hGap_size = (TrafficWorld.HEIGHT - (roads.length * Road.ROAD_WIDTH)) / (roads.length - 1);
	public static int vGap_size = (TrafficWorld.WIDTH - (road1.length * Road.ROAD_WIDTH)) / (road1.length - 1) + 1;


	public static Random gen = new Random();

	public TrafficWorld() {
		super(WIDTH, HEIGHT, CELL_SIZE);
		GreenfootImage background = this.getBackground();
		background.setColor(Color.GREEN);
		background.fill();

		NorthSouthRoads();
		EastWestRoads();
		placeIntersection();
//		PlaceCars();
		placeSingleCar();
	}


	public void EastWestRoads() {
		for(int x = 0; x < roads.length; x++) {
			roads[x] = new Road();
			int y;
			y = ((hGap_size + Road.ROAD_WIDTH) * x) + Road.ROAD_WIDTH / 2;
			this.addObject(roads[x], TrafficWorld.WIDTH / 2, y);
		}
	}



	public void NorthSouthRoads() {
		for(int x = 0; x < road1.length; x++) {
			road1[x] = new Road();
			road1[x].setRotation(90);
			int y;
			y = ((vGap_size + Road.ROAD_WIDTH) * x) + Road.ROAD_WIDTH / 2;
			this.addObject(road1[x], y, TrafficWorld.HEIGHT / 2);
		}
	}


	public void PlaceCars() {
		int gap_size = (TrafficWorld.HEIGHT - (roads.length * Road.ROAD_WIDTH)) / (roads.length - 1);
		for(int x = 0; x < roads.length; x++) {
			Car car = new Car();
			this.addObject(car, gen.nextInt(TrafficWorld.WIDTH), ((gap_size + Road.ROAD_WIDTH) * x) + Road.ROAD_WIDTH / 2 + CAR_OFFSET);
		}

		for(int x = 0; x < roads.length; x++) {
			Car car = new Car();
			car.setRotation(180);
			this.addObject(car, gen.nextInt(TrafficWorld.WIDTH), ((gap_size + Road.ROAD_WIDTH) * x) + Road.ROAD_WIDTH / 2 - CAR_OFFSET);
		}

		gap_size = (TrafficWorld.WIDTH - (road1.length * Road.ROAD_WIDTH)) / (road1.length - 1);
		for(int x = 0; x < road1.length; x++) {
			Car car = new Car();
			car.setRotation(90);
			this.addObject(car, ((gap_size + Road.ROAD_WIDTH) * x) + Road.ROAD_WIDTH / 2 - CAR_OFFSET, gen.nextInt(TrafficWorld.HEIGHT));
		}

		for(int x = 0; x < road1.length; x++) {
			Car car = new Car();
			car.setRotation(270);
			this.addObject(car, ((gap_size + Road.ROAD_WIDTH) * x) + Road.ROAD_WIDTH / 2 + CAR_OFFSET, gen.nextInt(TrafficWorld.HEIGHT));
		}

	}


	private void placeIntersection() {
		for(int j = 0; j < roads.length; j++) {
			for(int l = 0; l < road1.length; l++) {
				Intersection i = new Intersection();
				this.addObject(i, road1[l].getX(), roads[j].getY());
				i.addLights();
			}
		}
	}
	
	public void placeSingleCar() {
		int carX = 0;
		int carY = 0;
		
		Car car = new Car();
		car.setRotation(car.randomDirection());
		
		if(car.getRotation() == 0) {
			carY = ((hGap_size + Road.ROAD_WIDTH) * TrafficWorld.gen.nextInt(roads.length)) + Road.ROAD_WIDTH / 2 + CAR_OFFSET;
		}
		
		if(car.getRotation() == 180) {
			carY = ((hGap_size + Road.ROAD_WIDTH) * TrafficWorld.gen.nextInt(roads.length)) + Road.ROAD_WIDTH / 2 - CAR_OFFSET;
			carX = TrafficWorld.WIDTH;
		}
		
		if(car.getRotation() == 90) {
			carX = ((vGap_size + Road.ROAD_WIDTH) * TrafficWorld.gen.nextInt(road1.length)) + Road.ROAD_WIDTH / 2 - CAR_OFFSET;
		}
		
		if(car.getRotation() == 270) {
			carX = ((vGap_size + Road.ROAD_WIDTH) * TrafficWorld.gen.nextInt(road1.length)) + Road.ROAD_WIDTH / 2 +  CAR_OFFSET;
			carY = TrafficWorld.HEIGHT;
		}
		
		this.addObject(car, carX, carY);
		
	}




}
