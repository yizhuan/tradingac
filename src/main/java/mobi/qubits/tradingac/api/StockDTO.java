package mobi.qubits.tradingac.api;

public class StockDTO {
	private String symbol;

	
	public StockDTO() {
		super();		
	}

	
	public StockDTO(String symbol) {
		super();
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	
}
