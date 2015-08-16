package mobi.qubits.tradingac.query.trade;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author yizhuan
 *
 */
public class TradeEntry {

	@Id
	private String id;
	
	private String traderId;

	private String symbol;
	private Long shares;
	private Float price;

	private Short type; // 0: sell; 1: buy

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
		
	public String getTraderId() {
		return traderId;
	}

	public void setTraderId(String traderId) {
		this.traderId = traderId;
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

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

}
