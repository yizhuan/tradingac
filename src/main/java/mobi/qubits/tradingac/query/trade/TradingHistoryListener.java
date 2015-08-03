package mobi.qubits.tradingac.query.trade;

import mobi.qubits.tradingac.domain.events.BuyEvent;
import mobi.qubits.tradingac.domain.events.SellEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yizhuan
 *
 */
@Component
public class TradingHistoryListener {

	@Autowired
	private TradeEntryRepository tradeEntryRepository;

	@EventHandler
	void on(SellEvent event) {
		TradeEntry entry = new TradeEntry();
		entry.setEventId(event.getId());
		entry.setSymbol(event.getSymbol());
		entry.setShares(event.getShares());
		entry.setPrice(event.getPrice());
		entry.setType((short) 0);

		tradeEntryRepository.save(entry);
	}

	@EventHandler
	void on(BuyEvent event) {

		TradeEntry entry = new TradeEntry();
		entry.setEventId(event.getId());
		entry.setSymbol(event.getSymbol());
		entry.setShares(event.getShares());
		entry.setPrice(event.getPrice());
		entry.setType((short) 1);

		tradeEntryRepository.save(entry);
	}

}
