package dataObjects;

import io.IOConfig;

import java.awt.Point;

public class CarParkData {

	private int ID;
	
	private String NAME;
	
	private Point LOCATION;
	
	private int MAXIMAL_CAPACITY;
	
	private int CURRENT_CAPACITY;
	
	private int MINIMUM_PRICE_PER_HOUR;
	
	private int MAXIMUM_PRICE_PER_DAY;
	
	private int CURRENT_PRICE;
	
	private int MAXIMUM_DURATION_OF_STAY;
	
	public CarParkData(int id, String name, int locationX, int locationY, int maximalCapacity, int currentCapacity, int minimumPrice, int maximalPrice, int currentPrice, int maximalDuration){
		ID = id;
		NAME = name;
		LOCATION = new Point(locationX, locationY);
		MAXIMAL_CAPACITY = maximalCapacity;
		CURRENT_CAPACITY = currentCapacity;
		MINIMUM_PRICE_PER_HOUR = minimumPrice;
		MAXIMUM_PRICE_PER_DAY = maximalPrice;
		CURRENT_PRICE = currentPrice;
		MAXIMUM_DURATION_OF_STAY = maximalDuration;
	}

	public int getID() {
		return ID;
	}

	public String getNAME() {
		return NAME;
	}

	public Point getLOCATION() {
		return LOCATION;
	}

	public int getMAXIMAL_CAPACITY() {
		return MAXIMAL_CAPACITY;
	}

	public int getCURRENT_CAPACITY() {
		return CURRENT_CAPACITY;
	}

	public int getMINIMUM_PRICE_PER_HOUR() {
		return MINIMUM_PRICE_PER_HOUR;
	}

	public int getMAXIMUM_PRICE_PER_DAY() {
		return MAXIMUM_PRICE_PER_DAY;
	}

	public int getCURRENT_PRICE() {
		return CURRENT_PRICE;
	}

	public int getMAXIMUM_DURATION_OF_STAY() {
		return MAXIMUM_DURATION_OF_STAY;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CarParkData){
			CarParkData data = (CarParkData) obj;
			return data.ID == ID;
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(ID);
		builder.append(IOConfig.DELIMITER);
		builder.append(NAME);
		builder.append(IOConfig.DELIMITER);
		builder.append(LOCATION.x);
		builder.append(IOConfig.DELIMITER);
		builder.append(LOCATION.y);
		builder.append(IOConfig.DELIMITER);
		builder.append(MAXIMAL_CAPACITY);
		builder.append(IOConfig.DELIMITER);
		builder.append(CURRENT_CAPACITY);
		builder.append(IOConfig.DELIMITER);
		builder.append(MINIMUM_PRICE_PER_HOUR);
		builder.append(IOConfig.DELIMITER);
		builder.append(MAXIMUM_PRICE_PER_DAY);
		builder.append(IOConfig.DELIMITER);
		builder.append(CURRENT_PRICE);
		builder.append(IOConfig.DELIMITER);
		builder.append(MAXIMUM_DURATION_OF_STAY);
		return builder.toString();
	}
}

