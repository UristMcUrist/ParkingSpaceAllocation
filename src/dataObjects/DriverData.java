package dataObjects;

import io.IOConfig;

import java.awt.Point;

public class DriverData {

	private int ID;
	private Point POSITION;
	private Point DESTINATION;
	private int ARRIVAL;
	private int MAXIMUM_PRICE_PER_HOUR;
	private int DURATION_OF_STAY;
	private int MAXIMUM_WALKING_DISTANCE;
	private int INITIAL_TIME;

	public DriverData(int id, int startX, int startY, int destX, int destY,
			int arrival, int maxPrice, int duration, int walkingDistance, int initialTime) {
		ID = id;
		POSITION = new Point(startX, startY);
		DESTINATION = new Point(destX, destY);
		ARRIVAL = arrival;
		MAXIMUM_PRICE_PER_HOUR = maxPrice;
		DURATION_OF_STAY = duration;
		MAXIMUM_WALKING_DISTANCE = walkingDistance;
		INITIAL_TIME = initialTime;
	}

	public int getID() {
		return ID;
	}

	public Point getPOSITION() {
		return POSITION;
	}

	public Point getDESTINATION() {
		return DESTINATION;
	}

	public int getARRIVAL() {
		return ARRIVAL;
	}

	public int getMAXIMUM_PRICE_PER_HOUR() {
		return MAXIMUM_PRICE_PER_HOUR;
	}

	public int getDURATION_OF_STAY() {
		return DURATION_OF_STAY;
	}

	public int getMAXIMUM_WALKING_DISTANCE() {
		return MAXIMUM_WALKING_DISTANCE;
	}
	
	public int getINITIAL_TIME() {
		return INITIAL_TIME;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DriverData){
			DriverData data = (DriverData) obj;
			return data.ID == ID;
		}
		return super.equals(obj);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(ID);
		builder.append(IOConfig.DELIMITER);
		builder.append(POSITION.x);
		builder.append(IOConfig.DELIMITER);
		builder.append(POSITION.y);
		builder.append(IOConfig.DELIMITER);
		builder.append(DESTINATION.x);
		builder.append(IOConfig.DELIMITER);
		builder.append(DESTINATION.y);
		builder.append(IOConfig.DELIMITER);
		builder.append(ARRIVAL);
		builder.append(IOConfig.DELIMITER);
		builder.append(MAXIMUM_PRICE_PER_HOUR);
		builder.append(IOConfig.DELIMITER);
		builder.append(DURATION_OF_STAY);
		builder.append(IOConfig.DELIMITER);
		builder.append(MAXIMUM_WALKING_DISTANCE);
		builder.append(IOConfig.DELIMITER);
		builder.append(INITIAL_TIME);
		return builder.toString();
	}
}
