package mobi.qubits.tradingac.query.trade;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author yizhuan
 *
 */
public class TradingAccount {

	@Id
	private String id;

	private String symbol;
	private Long shares;
	private Float costPerShare;

	public TradingAccount() {

	}

	public TradingAccount(String id, String symbol, Long shares,
			Float buyingPrice) {
		super();
		this.id = id;
		this.symbol = symbol;
		this.shares = shares;
		this.costPerShare = buyingPrice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Float getCostPerShare() {
		return costPerShare;
	}

	public void setCostPerShare(Float buyingPrice) {
		this.costPerShare = buyingPrice;
	}

}
