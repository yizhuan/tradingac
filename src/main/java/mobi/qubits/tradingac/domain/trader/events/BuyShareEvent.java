package mobi.qubits.tradingac.domain.trader.events;

/**
 * 
 * @author yizhuan
 *
 */
public class BuyShareEvent implements TradeEvent{

	private final String id;
	
	private String symbol;	
	private Long shares;
	
	private Float price;

	
	public BuyShareEvent(String id, String symbol, Long shares, Float price) {
		super();
		this.id = id;
		this.symbol = symbol;
		this.shares = shares;
		this.price = price;
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

	
}
