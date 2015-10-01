package mobi.qubits.tradingac.api.requests;

/**
 * 
 * @author yizhuan
 *
 */
public class TraderRequest {

	private String name;
	
	private Float investment;

	public TraderRequest(){
		
	}
	
	
	
	public TraderRequest(String name, Float investment) {
		super();
		this.name = name;
		this.investment = investment;		
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Float getInvestment() {
		return investment;
	}



	public void setInvestment(Float investment) {
		this.investment = investment;
	}
	
	
}
