package mobi.qubits.tradingac.api.requests;

/**
 * 
 * @author yizhuan
 *
 */
public class TradingRequest {

	private String symbol;	
	private Long shares;
	
	private Float price;
	
	private Float costPerShare;
	
	private Short type;
	
	public TradingRequest(){
		
	}
	
	public TradingRequest(String symbol, Long shares, Float price, Short type) {
		this(symbol, shares, price,type, null);		
	}
	
	public TradingRequest(String symbol, Long shares, Float price, Short type, Float costPerShare) {
		super();
		this.symbol = symbol;
		this.shares = shares;
		
		this.price = price;
		
		this.costPerShare = costPerShare;
		
		this.type = type;
		
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

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Float getCostPerShare() {
		return costPerShare;
	}

	public void setCostPerShare(Float costPerShare) {
		this.costPerShare = costPerShare;
	}

	
}
