package mobi.qubits.tradingapp.api.requests;

/**
 * 
 * @author yizhuan
 *
 */
public class BuyRequest {

	private String symbol;
	private Long shares;

	private Float price;

	public BuyRequest() {

	}

	public BuyRequest(String symbol, Long shares, Float price) {
		super();
		this.symbol = symbol;
		this.shares = shares;
		this.price = price;
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
}
