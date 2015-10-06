package mobi.qubits.tradingac.api;

public class PortfolioSummary {
	private Float investment;
	private Float currentValue;
	private Float assetValue;
	private Float assetCost; 
	private Float gainPct;
	private Float gain;
	
	public Float getInvestment() {
		return investment;
	}
	public void setInvestment(Float investment) {
		this.investment = investment;
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
	public Float getAssetValue() {
		return assetValue;
	}
	public void setAssetValue(Float assetValue) {
		this.assetValue = assetValue;
	}
	public Float getAssetCost() {
		return assetCost;
	}
	public void setAssetCost(Float assetCost) {
		this.assetCost = assetCost;
	}
	
	
	
	
	
}
