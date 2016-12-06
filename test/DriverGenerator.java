package test;

import io.writer.DataPrinter;

import java.io.IOException;
import java.util.function.Function;

import config.GridConfig;
import dataObjects.DriverData;

public class DriverGenerator {

	public static void generateData(Function<Integer, Integer> dayFunction, String file) throws IOException{
		DataPrinter writer = new DataPrinter(file);
		int id = 0;
		for(int i=0;i<1440;i+=30){
			int numberOfDrivers = dayFunction.apply(i);
			
			for(int j=0;j<numberOfDrivers;j++){
				id++;
				int startX = (int)(Math.random()*GridConfig.GRID_WIDTH);
				int startY = (int)(Math.random()*GridConfig.GRID_HEIGHT);
				int destX = (int)(Math.random()*GridConfig.GRID_WIDTH);
				int destY = (int)(Math.random()*GridConfig.GRID_HEIGHT);
				int arrival = -15+(int) (Math.random()*31);
				int maxPrice = 70 + (int)(Math.random()*61);
				int duration = 0;
				if(i<360){
					// 1/2 to 2 hours
					duration = 30+(int) (Math.random()*91);
				}else if (i<540) {
					// 7 1/2 to 8 1/2 hours
					duration = 450+(int) (Math.random()*61);
				}else if(i<960){
					// 2 hours to 3 hours
					duration = 120+(int) (Math.random()*61);
				}else{
					// 1/2 to 2 hours
					duration = 30+(int) (Math.random()*91);
				}
				int walkingDistance = 800 * (int) (Math.random()*401);
				DriverData d = new DriverData(id, startX, startY, destX, destY, arrival, maxPrice, duration, walkingDistance);
				writer.printDriverData(i, d);
			}
		}
	}
}
