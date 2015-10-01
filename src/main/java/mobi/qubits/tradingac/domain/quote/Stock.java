package mobi.qubits.tradingac.domain.quote;

import java.util.Date;

import mobi.qubits.tradingac.Util;
import mobi.qubits.tradingac.domain.quote.commands.CreateStockCommand;
import mobi.qubits.tradingac.domain.quote.commands.UpdateQuoteCommand;
import mobi.qubits.tradingac.domain.quote.events.QuoteUpdatedEvent;
import mobi.qubits.tradingac.domain.quote.events.StockCreatedEvent;
import mobi.qubits.tradingac.quote.QuoteService;
import mobi.qubits.tradingac.quote.Ticker;
import mobi.qubits.tradingac.quote.google.GoogleQuoteService;
import mobi.qubits.tradingac.quote.sina.SinaQuoteService;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class Stock  extends AbstractAnnotatedAggregateRoot<String>{
	
	@AggregateIdentifier
	private String symbol;
	
	private Ticker quote;	
	
	Stock() {
		
	}
	
	@CommandHandler
	public Stock(CreateStockCommand cmd) {	
		apply(new StockCreatedEvent(cmd.getSymbol()));
	}	
		
	@CommandHandler
	public void on(UpdateQuoteCommand cmd) {
		
		QuoteService service = Util.isNumeric(this.symbol)? new SinaQuoteService() :  new GoogleQuoteService();
		Ticker q = service.getQuote(symbol);	
		
		q.setTimestamp(new Date());
		
		apply(new QuoteUpdatedEvent(cmd.getSymbol(), q));
	}	
		
	@EventSourcingHandler
	void on(StockCreatedEvent event) {
		this.symbol = event.getSymbol();
	}
		
	@EventSourcingHandler
	void on(QuoteUpdatedEvent event) {
		this.quote = event.getTicker();	
	}
			
	
}
