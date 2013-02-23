package projet;

import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

public class Compagnie extends Agent {
	
	private final static int VOLCHARTER=1;
	private final static int VOLREGULIER=2;
	private int tauxSatisfationClients;
	private int tauxReductionDerniereMinute;
	private String name;
	
	public String compagnieName;
	private ArrayList<Vol> listeVols;
	
	public String getCompagnieName() {
		return compagnieName;
	}
	public void setCompagnieName(String compagnieName) {
		this.compagnieName = compagnieName;
	}

	public ArrayList<Vol> getListeVols() {
		return listeVols;
	}

	public void setListeVols(ArrayList<Vol> listeVols) {
		this.listeVols = listeVols;
	}
	
	public List<Vol> getCorrespondingVols(Route route){
		
		List<Vol> corrVols = new ArrayList<Vol>();
		
		for(Vol v : listeVols){
			if(v.getRoute().includes(route)){
				corrVols.add(v);
			}
		}
		return corrVols;
		
	}

	
}
