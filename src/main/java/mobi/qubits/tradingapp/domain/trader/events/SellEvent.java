package mobi.qubits.tradingapp.domain.trader.events;

/**
 * 
 * @author yizhuan
 *
 */
public class SellEvent implements TradeEvent{

	private final String id;
	
	private String symbol;	
	private Long shares;

	private Float price;
	
	private Float costPerShare;
	
	public SellEvent(String id, String symbol, Long shares, Float price, Float costPerShare) {
		super();
		this.id = id;
		this.symbol = symbol;
		this.shares = shares;
		this.price = price;
		this.costPerShare = costPerShare;
	}

	public String getId() {
		return id;
	}

	public String getSymbol() {
		return symbol;
	}


	public Long getShares() {
		return shares;
	}

	public Float getPrice() {
		return price;
	}

	public Float getCostPerShare() {
		return costPerShare;
	}
	
	
	
}
