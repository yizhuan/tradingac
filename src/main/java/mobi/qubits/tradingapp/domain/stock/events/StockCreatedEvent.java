package mobi.qubits.tradingapp.domain.stock.events;

public class StockCreatedEvent {


	private String id;
	private String symbol;
	public StockCreatedEvent(String id, String symbol) {
		super();
		this.id = id;
		this.symbol = symbol;
	}
	public String getId() {
		return id;
	}
	public String getSymbol() {
		return symbol;
	}



}
