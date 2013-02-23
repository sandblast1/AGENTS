package projet;

public class Airport {
	
	private int id;
	private String code; //le code de l'aéroport genre ('A','B','C');
	private String name;
	
	public Airport(){
		
	}
	public Airport(int i, String c, String n){
		this.id = i;
		this.code = c;
		this.name = n;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
