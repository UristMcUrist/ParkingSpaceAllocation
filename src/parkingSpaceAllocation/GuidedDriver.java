package parkingSpaceAllocation;


import java.util.ArrayList;

import fireFighters_MAS.Fire;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;

public class GuidedDriver implements  Driver{
	private Grid<Object> grid;
	private int id;
	private int[] destination;
	//private int arrival;   //ATTENTION, the arrival should be set in the "grid" variable, I'm not sure how to do that for now, should be similar to parking agent
	private  int arrivalTime;
	private int maxPrice;
	private int durationOfStay; //Should be in minutes
	private int time;
	private int timeArrivedInParking;
	private int maxWalkingDistance;
	private int day;
	private boolean cheap=false;  //if the driver is cheap, the proportion of utility lost about the price he has to pay is 1.5 higher
	private boolean lazy=false;   //if the driver is lazy, the proportion of utility lost about the walking distance is 1.5 higher
	private boolean parked;
	private ParkingAgent optimalParking;
	private Context<Object> context;
	
	public GuidedDriver(Context<Object> context, Grid<Object> grid, int ID, int desX, int desY, int initT, int maxPrice, int duration, int maxWalking, int DAY, int timeinSys){
		Parameters params = RunEnvironment.getInstance().getParameters();
		
		this.context = context;
		this.grid = grid;
		this.id = ID;
		this.destination = new int[2];
		this.destination[0] = desX;
		this.destination[1] = desY;
		this.time = initT;
		this.maxPrice = maxPrice;
		this.durationOfStay = duration;
		this.maxWalkingDistance = maxWalking;
		this.day = DAY;
		double rand1 = Math.random();
		double rand2 = Math.random();
		if (rand1 < 0.5)
			this.cheap = true;
		if (rand2 < 0.5)
			this.lazy = true;
		this.parked = false;
	}
	
	/**
	 * A step method of the driver
	 */
	@ScheduledMethod(start = 1, interval = 1)  //Should be one minute
	public void step()
	{
		this.time++;
		if (this.parked) {
			if(this.time-this.timeArrivedInParking >= this.durationOfStay) {
				optimalParking.checkout(this.id, this.time);
				context.remove(this);
			}
		}
		else {
			//ATTENTION, I use this to find the optimal parking, but this is really not the way it should be done!
			if(optimalParking == null)
				this.findOptimalParking();
			
			if(!parked){
				for(int count=0; count<3; count++) //We assume for now that the agent can move at three squares per minutes (tic)
					this.move(this.getDirectionToParking(optimalParking));
			}
			
			Position p = optimalParking.getKnowledge().getPosition(optimalParking);
			if(grid.getLocation(this).getX() == p.getX() && grid.getLocation(this).getY() == p.getY()){ //If the driver just arrived to his parking
				if(optimalParking.usedcapacity >= optimalParking.maximumcapacity) {
					this.optimalParking = null;
					break;
					//check that everything is in here
				}
				else{
					optimalParking.checkin(this.id, this.time);
					this.timeArrivedInParking = this.time;
					this.parked = true;
					//Check that everything is in here
				}
			}
		}
	}
	
	public void move(int direction) {
		if(direction != -1){
			int x = this.grid.getLocation(this).getX();
			int y = this.grid.getLocation(this).getY();
			int[] result = Tools.dirToCoord(direction,x,y); //ATTENTION, can be usefull to chack if the dirToCoord is working correctly
			int otherX = result[0], otherY = result[1];
			//I assume that there can be several drivers on the same grid
			if (Tools.isWithinBorders(otherX, otherY, grid)) //I am not sure if that line is necessary though
				grid.moveTo(this, otherX, otherY);
		}
	}
	
	//Returns the utility of that "optimal parking", so that if the parking is not good enough, we can set a condition so that the drivers decides to take his chance and find a city park
	public double findOptimalParking(){  //ATTENTION, I assume that we will set a "basic knowledge" at the beginning with the data for all parkings
		int utility = 0;
		int distance, pricePerMinute, timeAtPark;
		double maxUtility = Double.NEGATIVE_INFINITY, 
				tempUtility, walkingUtility, priceUtility, price;
		
		double cheapEmphasis = 1.0, lazyEmphasis = 1.0; 
		if (this.cheap)
			cheapEmphasis = 1.5;
		if (this.lazy)
			lazyEmphasis = 1.5;
		
		for (int i=0; i < grid.getDimensions().getHeight(); i++)  //loops over all grid
			for (int j=0; j < grid.getDimensions().getWidth(); j++) {
				Iterable<Object> objects = grid.getObjectsAt(i, j);

				for (Object obj : objects)
				{
					if(obj.getClass() == ParkingAgent.class)
					{
						ParkingAgent par = (ParkingAgent) obj;
						tempUtility=0;
						Position p = par.getKnowledge().getPosition(par);
						distance = Tools.getDistanceTo(p.getX(), p.getY(), this.destination[0], this.destination[1]); //Note that you need the getGrid() function in ParkingAgent (I put it there)
						walkingUtility = (Math.pow(distance, 2)) / 10;  //we substract the distance he will have to walk to the square divided by 10
						timeAtPark = this.time + distance/3;
						price = (double) par.getPrice(day, timeAtPark, (timeAtPark + this.durationOfStay)); //Can be useful to check that I didn't make any mistakes here
						priceUtility = price / 3;
						tempUtility -= (cheapEmphasis*priceUtility) + (lazyEmphasis*walkingUtility); //Gives the lazy and cheap emphasis on the utility
						if(tempUtility > maxUtility && par.maximumcapacity > par.usedcapacity){
							this.optimalParking = par;
							maxUtility = tempUtility;
						}
					}
				}
		}
		return maxUtility;
	}
	
	public int getDirectionToParking(ParkingAgent par){
		Position p = par.getKnowledge().getPosition(par);
		
		int x = this.grid.getLocation(this).getX();
		int y = this.grid.getLocation(this).getY();
		int xF = p.getX();  //Parking's x coordinate
		int yF = p.getY();  //Parking's y coordinate
		int direction = -1;
		if (x - xF < 0) // It's somewhere to the East
		{
			if (y - yF == 0) { direction = 2; } // East
			else if (y - yF < 0) { direction = 1; } // North-East
			else { direction = 3; } // South-East
		}
		else if (x - xF == 0)  // It's either to the North or to the South
		{
			if (y - yF < 0)	{ direction = 0; } // North
			else if (y - yF == 0) { direction = -1; } // Fire is here!!!
			else { direction = 4; } // South
		}
		else // It's somewhere to the West
		{
			if (y - yF == 0) { direction = 6; } // West
			else if (y - yF < 0) { direction = 7; } // North-West
			else { direction = 5; } // South-West
		}
		return direction;
	}
}
