package projet;

import java.util.ArrayList;
import java.util.List;

public class AirportManager {
	
	private static List<Airport> airports = new ArrayList<Airport>();
	
	public static Airport getAirportByName(String name){
		if(airports == null) return null;
		Airport ap = null;
		for(Airport a : airports){
			if(a.getName().equals(name)){
				ap = a;
			}
		}
		return ap;		
	}
	
	public static Airport getAirportByCode(String code){
		if(airports == null) return null;
		Airport ap = null;
		for(Airport a : airports){
			if(a.getCode().equals(code)){
				ap = a;
			}
		}
		return ap;		
	}
	
	public static Airport getAirportById(int id){
		if(airports == null) return null;
		Airport ap = null;
		for(Airport a : airports){
			if(a.getId()== id){
				ap = a;
			}
		}
		return ap;		
	}
	
	public static List<String> getAirportNameList(){
		
		List<String> ap = new ArrayList<String>();
		
		if(airports == null){
			return ap;
		}
		for(Airport a : airports){
			ap.add(a.getName());
		}
		return ap;
	}
	
	public static int getNumberofAirports(){
		if(airports == null){
			return 0;
		}
		return airports.size();
	}

}
