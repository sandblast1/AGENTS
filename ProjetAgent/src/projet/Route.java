package projet;

import java.util.Date;

public class Route {
	
	private Airport departureAirport;
	private String departureAirportName;
	private Airport destinationAirport;
	private String destinationAirportName;
	
	private Date dateFrom;
	private Date dateTo;
	
	public Route(String start, String destination, Date dF, Date dT){
		
		this.departureAirportName = start;
		this.destinationAirportName = destination;
		this.dateFrom = dF;
		this.dateTo = dT;
		
		this.departureAirport = AirportManager.getAirportByName(start);
		this.destinationAirport = AirportManager.getAirportByName(destination);		
	}
	
	public Route(){
		this.departureAirport = null;
		this.departureAirportName = "";
		this.destinationAirport = null;
		this.destinationAirportName = "";
		this.dateFrom = new Date();
		this.dateTo = new Date();
	}
	
	public boolean includes(Route r){
		
		boolean b = false;
		if(this.departureAirportName == r.getDepartureAirportName() && this.destinationAirportName == r.getDepartureAirportName()){
			b = true;
		}
		return b;
	}
	
	public boolean equals(Route r){
		int a = this.departureAirportName.compareTo(r.getDepartureAirportName());
		int b = this.destinationAirportName.compareTo(r.getDestinationAirportName());
		
		if (a==0 && b==0){
			return true;
		}else{
			return false;
		}
	}

	public Airport getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(Airport departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getDepartureAirportName() {
		return departureAirportName;
	}

	public void setDepartureAirportName(String departureAirportName) {
		this.departureAirportName = departureAirportName;
	}

	public Airport getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(Airport destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	public String getDestinationAirportName() {
		return destinationAirportName;
	}

	public void setDestinationAirportName(String destinationAirportName) {
		this.destinationAirportName = destinationAirportName;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	

}
