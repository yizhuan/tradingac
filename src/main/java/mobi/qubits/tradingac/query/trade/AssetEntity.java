package mobi.qubits.tradingac.query.trade;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author yizhuan
 *
 */
public class AssetEntity {

	@Id
	private String id;

	private String traderId;
	
	private String symbol;
	private Long shares;
	private Float costPerShare;

	public AssetEntity() {

	}

	public AssetEntity(String id, String traderId, String symbol, Long shares,
			Float buyingPrice) {
		super();
		this.id = id;
		this.traderId = traderId;
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

	public Float getCostPerShare() {
		return costPerShare;
	}

	public void setCostPerShare(Float buyingPrice) {
		this.costPerShare = buyingPrice;
	}

}
