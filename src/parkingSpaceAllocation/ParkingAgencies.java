package parkingSpaceAllocation;

public interface ParkingAgencies  {

	public boolean checkin(int id,int checkin);
	public double checkout(int id,int checkout);
	public double getPrice(int day, int starttime, int staytime);
	
}
