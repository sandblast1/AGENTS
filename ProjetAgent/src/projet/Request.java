package projet;

public class Request {
	
	private int numberOfSeat;
	private TravelClassEnum travelClass;
	private Route userRoute;
	
	public Request(){
		
		userRoute = new Route();
		
	}

	public int getNumberOfSeat() {
		return numberOfSeat;
	}

	public void setNumberOfSeat(int numberOfSeat) {
		this.numberOfSeat = numberOfSeat;
	}

	public TravelClassEnum getTravelClass() {
		return travelClass;
	}

	public void setTravelClass(TravelClassEnum travelClass) {
		this.travelClass = travelClass;
	}

	public Route getUserRoute() {
		return userRoute;
	}

	public void setUserRoute(Route userRoute) {
		this.userRoute = userRoute;
	}
	
	
	
	
}
