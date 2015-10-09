package mobi.qubits.tradingapp.api.requests;

/**
 * 
 * @author yizhuan
 *
 */
public class SellRequest {

	private String symbol;	
	private Long shares;
	
	private Float price;
	
	private Float costPerShare;

	public SellRequest(){
		
	}
	
	
	public SellRequest(String symbol, Long shares, Float price, Float costPerShare) {
		super();
		this.symbol = symbol;
		this.shares = shares;		
		this.price = price;		
		this.costPerShare = costPerShare;		
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Long getShares() {
		return shares;
	}
	public void setShares(Long shares) {
		this.shares = shares;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getCostPerShare() {
		return costPerShare;
	}

	public void setCostPerShare(Float costPerShare) {
		this.costPerShare = costPerShare;
	}

	
}
