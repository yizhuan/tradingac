package mobi.qubits.tradingapp.domain.stock;

import java.util.Date;

import mobi.qubits.tradingapp.Util;
import mobi.qubits.tradingapp.domain.stock.commands.CreateStockCommand;
import mobi.qubits.tradingapp.domain.stock.commands.UpdateQuoteCommand;
import mobi.qubits.tradingapp.domain.stock.events.QuoteUpdatedEvent;
import mobi.qubits.tradingapp.domain.stock.events.StockCreatedEvent;
import mobi.qubits.tradingapp.quote.QuoteService;
import mobi.qubits.tradingapp.quote.Ticker;
import mobi.qubits.tradingapp.quote.google.GoogleQuoteService;
import mobi.qubits.tradingapp.quote.sina.SinaQuoteService;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class Stock  extends AbstractAnnotatedAggregateRoot<String>{

	@AggregateIdentifier
	private String id;

	private String symbol;

	private Ticker quote;

	Stock() {

	}

	@CommandHandler
	public Stock(CreateStockCommand cmd) {
		apply(new StockCreatedEvent(cmd.getId(), cmd.getSymbol()));
	}

	@CommandHandler
	public void on(UpdateQuoteCommand cmd) {

		QuoteService service = Util.isNumeric(this.symbol)? new SinaQuoteService() :  new GoogleQuoteService();
		Ticker q = service.getQuote(symbol);

		if (q!=null){
			q.setTimestamp(new Date());
			apply(new QuoteUpdatedEvent(cmd.getSymbol(), q));
		}
	}

	@EventSourcingHandler
	void on(StockCreatedEvent event) {
		this.id= event.getId();
		this.symbol = event.getSymbol();
	}

	@EventSourcingHandler
	void on(QuoteUpdatedEvent event) {
		this.quote = event.getTicker();
	}


}
