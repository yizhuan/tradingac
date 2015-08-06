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
public class TradingAccountListener {
	@Autowired
	private TradingAccountRepository tradingAccountRepository;

	@EventHandler
	void on(SellEvent event) {

		TradingAccount acc = tradingAccountRepository.findBySymbol(event
				.getSymbol());

		Long existingShares = acc.getShares();
		Long newShares = event.getShares();
		Long totalShares = existingShares - newShares;

		acc.setShares(totalShares);

		tradingAccountRepository.save(acc);

	}

	@EventHandler
	void on(BuyEvent event) {

		TradingAccount acc = tradingAccountRepository.findBySymbol(event
				.getSymbol());

		if (acc == null) {

			TradingAccount acc1 = new TradingAccount();

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
}
