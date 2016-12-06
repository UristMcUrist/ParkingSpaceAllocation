package parkingSpaceAllocation;

public interface Driver {
	public void move(int direction);
	public GuidedDriver(Context<Object> context, Grid<Object> grid, int ID, int desX, int desY, int arrivalT, int maxPrice, int duration, int maxWalking, int DAY, boolean cheapOrNot, boolean lazyOrNot);
	public void step();
	public double findOptimalParking();
	public int getDirectionToParking(ParkingAgent par);
}


