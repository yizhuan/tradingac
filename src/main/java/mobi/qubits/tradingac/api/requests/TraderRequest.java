package mobi.qubits.tradingac.api.requests;

public class TraderRequest {

	private String name;

	public TraderRequest(){
		
	}
	
	
	
	public TraderRequest(String name) {
		super();
		this.name = name;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
