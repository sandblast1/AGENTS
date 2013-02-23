package projet;

public enum TravelClassEnum {
	
	FIRST_CLASS(1), BUSINESS_CLASS(2), ECONOMY_CLASS(3);
	private int value;
	
	private TravelClassEnum(int v){
		this.value = v;
	}

};

