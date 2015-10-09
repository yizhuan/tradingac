package mobi.qubits.tradingapp.query.trade;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author yizhuan
 *
 */
public class SellEntity {

	@Id
	private String id;
	
	private String traderId;

	private String symbol;
	private Long shares;
	private Float price;
	
	private Float costPerShare;

	private Float gain;
	private Float gainPct;
	
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
	public Float getGain() {
		return gain;
	}
	public void setGain(Float gain) {
		this.gain = gain;
	}
	public Float getGainPct() {
		return gainPct;
	}
	public void setGainPct(Float gainPct) {
		this.gainPct = gainPct;
	}


}
