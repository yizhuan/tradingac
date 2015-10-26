package mobi.qubits.tradingapp.query;

import mobi.qubits.tradingapp.domain.trader.events.BuyEvent;
import mobi.qubits.tradingapp.domain.trader.events.SellEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yizhuan
 *
 */
@Component
public class TradeListener {

	@Autowired
	private TradeEntityRepository tradeEntryRepository;



	@EventHandler
	void on(BuyEvent event) {

		TradeEntity entry = new TradeEntity();
		entry.setTraderId(event.getId());
		entry.setSymbol(event.getSymbol());
		entry.setShares(event.getShares());
		entry.setPrice(event.getPrice());
		entry.setType((short) 1);

		tradeEntryRepository.save(entry);
	}
	
	@EventHandler
	void on(SellEvent event) {
		TradeEntity entry = new TradeEntity();
		entry.setTraderId(event.getId());
		entry.setSymbol(event.getSymbol());
		entry.setShares(0L-event.getShares());
		entry.setPrice(event.getPrice());
		entry.setType((short) 0);

		tradeEntryRepository.save(entry);
	}	

}
