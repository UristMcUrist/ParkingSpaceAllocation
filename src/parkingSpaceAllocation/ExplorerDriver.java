package parkingSpaceAllocation;


import java.util.ArrayList;

import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;

public class ExplorerDriver implements  Driver{
	private Grid<Object> grid;
	private int id;
	private int[] destination;
	//private int arrival;   //ATTENTION, the arrival should be set in the "grid" variable, I'm not sure how to do that for now, should be similar to parking agent
	private int arrivalTime; //Should be which minute the driver arrives
	private int maxPrice;
	private int durationOfStay; //Should be in minutes
	private int time;
	private int timeArrivedInParking;
	private int maxWalkingDistance;
	private int day;
	private int genDirection = -1;
	private boolean cheap;  //if the driver is cheap, the proportion of utility lost about the price he has to pay is 1.5 higher
	private boolean lazy;   //if the driver is lazy, the proportion of utility lost about the walking distance is 1.5 higher
	private boolean parked;
	private boolean foundPlace = false;
	private ParkingAgent optimalParking;
	private Context<Object> context;
	
	public GuidedDriver(Context<Object> context, Grid<Object> grid, int ID, int desX, int desY, int arrivalT, int maxPrice, int duration, int maxWalking, int DAY, boolean cheapOrNot, boolean lazyOrNot){
		Parameters params = RunEnvironment.getInstance().getParameters();
		
		this.context = context;
		this.grid = grid;
		this.id = ID;
		this.destination = new int[2];
		this.destination[0] = desX;
		this.destination[1] = desY;
		this.arrivalTime = arrivalT;
		this.time = arrivalT;
		this.maxPrice = maxPrice;
		this.durationOfStay = duration;
		this.maxWalkingDistance = maxWalking;
		this.day = DAY;
		this.cheap = cheapOrNot;
		this.lazy = lazyOrNot;
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
			if(!parked){
				if (!this.foundPlace) {
					int direction = this.genDirection(destination[0], destination[1]);
					this.move(direction);
				}
			else{
					for(int count=0; count<3; count++) { //We assume for now that the agent can move at three squares per minutes (tic)
						genDirection = (genDirection + RandomHelper.nextIntFromTo(-1, 1) + 8) % 8;
						move(genDirection);
					}
				}
			
			//ATTENTION, once again this structure should be changed
			Knowledge k;
			ArrayList<ParkingAgent> parkings = new ArrayList<>();
			parkings = k.getAllParkings();
			for (ParkingAgent par : parkings) //loops over all parkings
				if(grid.getLocation(this).getX() == par.getPosX() && grid.getLocation(this).getY() == par.getPosY()){ //If the driver just arrived to his parking
					if(par.usedcapacity < par.maximumcapacity) {
						optimalParking.checkin(this.id, this.time);
						this.timeArrivedInParking = this.time;
						this.parked = true;
				}
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
	
	public int getDirectionToParking(int x, int y){
		int xF = par.getGrid().getLocation().getX();  //Parking's x coordinate
		int yF = par.getGrid().getLocation().getY();  //Parking's y coordinate
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
