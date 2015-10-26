package mobi.qubits.tradingapp.api;

public class Asset {

	private String symbol;
	private String name;

	private Long shares;

	private Float cost;//the price when the asset was bought
	private Float currentPrice;

	private Float currentValue;
	private Float gainPct;
	private Float gain;


	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getShares() {
		return shares;
	}
	public void setShares(Long shares) {
		this.shares = shares;
	}
	public Float getCost() {
		return cost;
	}
	public void setCost(Float cost) {
		this.cost = cost;
	}
	public Float getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Float currentPrice) {
		this.currentPrice = currentPrice;
	}
	public Float getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(Float currentValue) {
		this.currentValue = currentValue;
	}
	public Float getGainPct() {
		return gainPct;
	}
	public void setGainPct(Float gainPct) {
		this.gainPct = gainPct;
	}
	public Float getGain() {
		return gain;
	}
	public void setGain(Float gain) {
		this.gain = gain;
	}



}
