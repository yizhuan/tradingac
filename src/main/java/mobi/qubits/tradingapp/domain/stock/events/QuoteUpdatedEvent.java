package mobi.qubits.tradingapp.domain.stock.events;

import mobi.qubits.tradingapp.quote.Ticker;

public class QuoteUpdatedEvent {
	

	private String symbol;
	private Ticker ticker;
	
	public QuoteUpdatedEvent( String symbol, Ticker ticker) {
		super();
		
		this.symbol = symbol;
		this.ticker = ticker;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public Ticker getTicker() {
		return ticker;
	}

	
	

}
