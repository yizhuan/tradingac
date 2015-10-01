package mobi.qubits.tradingac.api;

import java.util.List;

public class Portfolio {

	private String traderId;
	private PortfolioSummary summary;
	private List<Asset> assets;	
	
	public String getTraderId() {
		return traderId;
	}
	public void setTraderId(String traderId) {
		this.traderId = traderId;
	}
	public PortfolioSummary getSummary() {
		return summary;
	}
	public void setSummary(PortfolioSummary summary) {
		this.summary = summary;
	}
	public List<Asset> getAssets() {
		return assets;
	}
	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}
	
}
