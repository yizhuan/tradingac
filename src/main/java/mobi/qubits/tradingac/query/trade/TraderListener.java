package mobi.qubits.tradingac.query.trade;

import mobi.qubits.tradingac.domain.events.BuyEvent;
import mobi.qubits.tradingac.domain.events.RegisterNewTraderEvent;
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
public class TraderListener {

	@Autowired
	private TraderEntryRepository traderEntryRepository;

	@Autowired
	private TradingBalanceRepository tradingAccountRepository;

	@EventHandler
	void on(RegisterNewTraderEvent event) {
		TraderEntry entry = new TraderEntry(event.getId(), event.getName());
		traderEntryRepository.save(entry);
	}

	@EventHandler
	void on(BuyEvent event) {

		TradingBalance acc = tradingAccountRepository.findByTraderIdAndSymbol(
				event.getId(), event.getSymbol());

		if (acc == null) {

			TradingBalance acc1 = new TradingBalance();

			acc1.setTraderId(event.getId());
			acc1.setSymbol(event.getSymbol());
			acc1.setShares(event.getShares());
			acc1.setCostPerShare(event.getPrice());

			tradingAccountRepository.save(acc1);
		} else {
			Long existingShares = acc.getShares();
			Long newShares = event.getShares();
			Long totalShares = existingShares + newShares;

			acc.setShares(totalShares);

			Float existingPrice = acc.getCostPerShare();
			Float newPrice = event.getPrice();
			Float newCostPerShare = (existingShares * existingPrice + newShares
					* newPrice)
					/ (existingShares + newShares);

			acc.setCostPerShare(newCostPerShare);

			tradingAccountRepository.save(acc);
		}

	}

	@EventHandler
	void on(SellEvent event) {

		TradingBalance acc = tradingAccountRepository.findByTraderIdAndSymbol(
				event.getId(), event.getSymbol());

		if (acc == null) {
			TradingBalance acc1 = new TradingBalance();
			
			acc1.setTraderId(event.getId());			
			acc1.setSymbol(event.getSymbol());
			acc1.setShares(0L - event.getShares());
			acc1.setCostPerShare(event.getPrice());
		} else {

			Long existingShares = acc.getShares();
			Long newShares = event.getShares();
			Long totalShares = existingShares - newShares;

			acc.setShares(totalShares);

			tradingAccountRepository.save(acc);
		}

	}

}
