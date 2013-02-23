package projet;

import java.util.ArrayList;
import java.util.List;

public class CompagnieManager {
	
	private static List<Compagnie> compagnies = new ArrayList<Compagnie>();
	
	public static List<Compagnie> getCompagnies() {
		return compagnies;
	}

	public static void setCompagnies(List<Compagnie> compagnies) {
		CompagnieManager.compagnies = compagnies;
	}

	private static int getNumberOfCompagnies(){
		if(compagnies == null) return 0;
		return compagnies.size();
	}

}
