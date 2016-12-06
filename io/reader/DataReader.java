package io.reader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import config.GridConfig;
import dataObjects.CarParkData;
import dataObjects.DriverData;
import io.IOConfig;

public class DataReader {

	private DataReader(){
	}
	
	private static final int CAR_PARK_ARG = 10;
	private static final int DRIVER_ARG = 10;
	private static final int TWENTY_FOUR_HOURS = 1440;

	public static List<CarParkData> readCarParkData(String path) {
		if (path == null)
			throw new IllegalArgumentException("No path for data file given.");
		InputStream in = ClassLoader.getSystemResourceAsStream(path);
		Scanner sc = new Scanner(in);
		int i = 0;
		List<CarParkData> map = new ArrayList<CarParkData>();
		while (sc.hasNext()) {
			i++;
			String line = sc.nextLine();
			try {
				CarParkData data = parseLineCarPark(line);
				if (map.contains(data)) {
					System.out.println("Duplicated data in line " + i);
					continue;
				}
				map.add(data);
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage() + " in line " + i);
				continue;
			}
		}
		sc.close();
		return map;
	}
	
	public static List<DriverData> readDriverData(String path) {
		if (path == null)
			throw new IllegalArgumentException("No path for data file given.");
		InputStream in = ClassLoader.getSystemResourceAsStream(path);
		Scanner sc = new Scanner(in);
		int i = 0;
		List<DriverData> map = new ArrayList<DriverData>();
		while (sc.hasNext()) {
			i++;
			String line = sc.nextLine();
			try {
				DriverData data = parseLineDriver(line);
				if (map.contains(data)) {
					System.out.println("Duplicated data in line " + i);
					continue;
				}
				map.add(data);
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage() + " in line " + i);
				continue;
			}
		}
		sc.close();
		return map;
	}
	

	private static DriverData parseLineDriver(String line) throws IllegalArgumentException{
		String[] split = line.split(IOConfig.DELIMITER);
		if (split.length != DRIVER_ARG)
			throw new IllegalArgumentException("Wrong number of parameter");

		int id  =Integer.parseInt(split[0]);
		
		int startX =Integer.parseInt(split[1]);
		int startY =Integer.parseInt(split[2]);
		if (startX < 0 || startX > GridConfig.GRID_WIDTH || startY < 0
				|| startY > GridConfig.GRID_HEIGHT)
			throw new IllegalArgumentException("Illegal start point");
		
		int destX=Integer.parseInt(split[3]);
		int destY=Integer.parseInt(split[4]);
		if (startX < 0 || startX > GridConfig.GRID_WIDTH || startY < 0
				|| startY > GridConfig.GRID_HEIGHT)
			throw new IllegalArgumentException("Illegal destination point");
		
		int arrival=Integer.parseInt(split[5]);
		if(arrival<0 || arrival > TWENTY_FOUR_HOURS)
			throw new IllegalArgumentException("Illegal arrival time");
		
		int maxPrice=Integer.parseInt(split[6]);
		if(maxPrice<0)
			throw new IllegalArgumentException("Negative maximal price");
		
		int duration=Integer.parseInt(split[7]);
		if(duration<=0 || arrival+duration > TWENTY_FOUR_HOURS)
			throw new IllegalArgumentException("Illegal duration of stay");
		
		int walkingDistance=Integer.parseInt(split[8]);
		if(walkingDistance<0)
			throw new IllegalArgumentException("Illegal walking distance");
		int initialTime = Integer.parseInt(split[9]);
		if(initialTime>arrival || initialTime<0 || initialTime> TWENTY_FOUR_HOURS)
			throw new IllegalArgumentException("Illegal walking distance");
		
		return new DriverData(id, startX, startY, destX, destY, arrival, maxPrice, duration, walkingDistance, initialTime);
	}

	private static CarParkData parseLineCarPark(String line)
			throws IllegalArgumentException {
		String[] split = line.split(IOConfig.DELIMITER);
		if (split.length != CAR_PARK_ARG)
			throw new IllegalArgumentException("Wrong number of parameter");

		int id = Integer.parseInt(split[0]);
		String name = split[1];
		int locationX = Integer.parseInt(split[2]);
		int locationY = Integer.parseInt(split[3]);

		if (locationX < 0 || locationX > GridConfig.GRID_WIDTH || locationY < 0
				|| locationY > GridConfig.GRID_HEIGHT)
			throw new IllegalArgumentException("Illegal location");

		int maximalCapacity = Integer.parseInt(split[4]);
		if (maximalCapacity < 0)
			throw new IllegalArgumentException("Negativ capacity");
		int currentCapacity = Integer.parseInt(split[5]);
		if (currentCapacity < 0 || currentCapacity > maximalCapacity)
			throw new IllegalArgumentException("Illegal current capacity");

		int minimumPrice = Integer.parseInt(split[6]);
		if (minimumPrice < 0)
			throw new IllegalArgumentException("Negativ price");
		int maximalPrice = Integer.parseInt(split[7]);
		if (minimumPrice > maximalPrice)
			throw new IllegalArgumentException("Illegal price range");
		int currentPrice = Integer.parseInt(split[8]);
		if (minimumPrice > currentPrice || maximalPrice < currentPrice)
			throw new IllegalArgumentException("Illegal current price");
		int maximalDuration = Integer.parseInt(split[9]);
		if (maximalDuration > TWENTY_FOUR_HOURS)
			throw new IllegalArgumentException("Maximal duration over one day.");
		return new CarParkData(id, name, locationX, locationY, maximalCapacity,
				currentCapacity, minimumPrice, maximalPrice, currentPrice,
				maximalDuration);
	}

}
