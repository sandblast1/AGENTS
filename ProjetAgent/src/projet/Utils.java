package projet;

public class Utils {
	
	private static String getAirportDescription(int ch){
		switch(ch){
		case 1 : return "Berlin";
		case 2 : return "Londres";
		case 3 : return "Madrid";
		case 4 : return "Paris";
		case 5 : return "Rome";
		default: return ch  +" Airport";
		}
	}
	
}
