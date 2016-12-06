package initialize;

import java.util.List;

import parkingSpaceAllocation.*;
import dataObjects.DriverData;
import io.reader.DataReader;
import parkingSpaceAllocation.GuidedDriver;
import repast.simphony.context.Context;
import repast.simphony.space.grid.Grid;

public class Example {

	public static List<Driver> driverList(Context context, Grid grid) {
		List<DriverData> list = DataReader.readDriverData("PATH OF FILE");
		for(DriverData data: list){
			
			int ID = data.getID();
			int desX = data.getDESTINATION().x;
			int desY = data.getDESTINATION().y;
			int arrivalT = data.getARRIVAL();
			int maxPrice = data.getMAXIMUM_PRICE_PER_HOUR();
			int duration = data.getDURATION_OF_STAY();
			int maxWalking = data.getMAXIMUM_WALKING_DISTANCE();
			int initialTime = data.getINITIAL_TIME();
			
			GuidedDriver gd = new GuidedDriver(context, grid, ID, desX, desY, arrivalT, maxPrice, duration, maxWalking);
			list.add(gd);
		}
	}
}
