package mobi.qubits.tradingapp.query.trade;

import mobi.qubits.tradingapp.domain.stock.events.QuoteUpdatedEvent;
import mobi.qubits.tradingapp.domain.stock.events.StockCreatedEvent;
import mobi.qubits.tradingapp.quote.Ticker;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuoteListener {
	
	
	@Autowired
	private QuoteEntityRepository repo;

 
	@EventHandler
	void on(StockCreatedEvent event) {

		QuoteEntity entry = new QuoteEntity();
		entry.setSymbol(event.getSymbol());

		repo.save(entry);
	}	
	

	@EventHandler
	void on(QuoteUpdatedEvent event) {
		QuoteEntity entry = repo.findOne(event.getSymbol());
		
		Ticker q = event.getTicker();
		
		entry.setCurrentQuote(q.getCurrentQuote());
		entry.setDate(q.getDate());
		entry.setHigh(q.getHigh());
		entry.setLow(q.getLow());
		entry.setName(q.getName());
		entry.setOpen(q.getOpen());
		entry.setPrevClose(q.getPrevClose());
		entry.setQuoteTime(q.getQuoteTime());
		entry.setSymbol(q.getSymbol());
		entry.setTimestamp(q.getTimestamp());
				
		repo.save(entry);	
		
	}	

}
