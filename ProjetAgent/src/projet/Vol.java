package projet;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Vol {
	
	private String DepartureAirport;
	private String DestinationAirport;
	private String dateDepart;
	private String dateArrivee;
	private String CompagnieName;
	private double unitCost;
	private String TRAVEL_CLASSES;
	private Compagnie compagnie;
	private String UID;
	private List<VolClass> travelClasses;
	private Route route;
	
	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Vol(){
		travelClasses = new ArrayList<VolClass>();
		
	}	
	
	public void setTravelClasses(List<VolClass> val){
		this.travelClasses = val;
		updateTravelClassCosts();
	}
	
	
	public List<VolClass> travelClasses(){
		
		List<VolClass> vc = new ArrayList<VolClass>();
		
		for(String s : TRAVEL_CLASSES){
			
			TravelClassEnum en = TravelClassEnum.ECONOMY_CLASS;
			switch(s){
			case "FIRST_CLASS":
				en = TravelClassEnum.FIRST_CLASS;
				break;
			case "BUSINESS_CLASS":
				en = TravelClassEnum.BUSINESS_CLASS;
				break;
			case "ECONOMY_CLASS":
				en = TravelClassEnum.ECONOMY_CLASS;
				break;
			default:
				break;			
			}
			
			VolClass newVol; // construire avec le nombre de siège dispo (dans s ?) et travelclass = en
			vc.add(newVol);
			
		}
		return vc;
		
	}
	
	
	//obtenir le prix du vol par siege dans une classe spécifique	
	public double getTravelClassCost(TravelClassEnum t){
		
		double cost = 0;
		for(VolClass v : travelClasses){
			if(v.getTravelClass().compareTo(t) == 0){
				v.getCost();
			}
		}
		
		return cost;
		
	}
	
	//update du prix par classe en fonction du prix unitaire du vol
	public void updateTravelClassCosts(){
		for(VolClass v : travelClasses){
			
			switch(v.getTravelClass()){
			case BUSINESS_CLASS: 
				v.setCost(10 * unitCost);
				break;
			case FIRST_CLASS:
				v.setCost(7 * unitCost);
				break;
			case ECONOMY_CLASS:
				v.setCost(5 * unitCost);
				break;
			default :
				break;
			}
			
		}
	}

	public String getDepartureAirport() {
		return DepartureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		DepartureAirport = departureAirport;
	}

	public String getDestinationAirport() {
		return DestinationAirport;
	}

	public void setDestinationAirport(String destinationAirport) {
		DestinationAirport = destinationAirport;
	}

	public String getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(String dateDepart) {
		this.dateDepart = dateDepart;
	}

	public String getDateArrivee() {
		return dateArrivee;
	}

	public void setDateArrivee(String dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	public String getCompagnieName() {
		return CompagnieName;
	}

	public void setCompagnieName(String compagnieName) {
		CompagnieName = compagnieName;
	}

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	public String getTRAVEL_CLASSES() {
		return TRAVEL_CLASSES;
	}

	public void setTRAVEL_CLASSES(String tRAVEL_CLASSES) {
		TRAVEL_CLASSES = tRAVEL_CLASSES;
	}

	public Compagnie getCompagnie() {
		return compagnie;
	}

	public void setCompagnie(Compagnie compagnie) {
		this.compagnie = compagnie;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public List<VolClass> getTravelClasses() {
		return travelClasses;
	}	
	
}

