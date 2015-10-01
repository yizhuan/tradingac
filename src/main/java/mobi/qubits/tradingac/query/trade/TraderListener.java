package mobi.qubits.tradingac.query.trade;

import mobi.qubits.tradingac.domain.trader.events.BuyEvent;
import mobi.qubits.tradingac.domain.trader.events.RegisterNewTraderEvent;
import mobi.qubits.tradingac.domain.trader.events.SellEvent;

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
	private TraderEntityRepository traderEntryRepository;

	@Autowired
	private AssetEntityRepository tradingAccountRepository;

	@EventHandler
	void on(RegisterNewTraderEvent event) {
		TraderEntity entry = new TraderEntity(event.getId(), event.getName(), event.getInvestment());
		traderEntryRepository.save(entry);
	}

	@EventHandler
	void on(BuyEvent event) {

		AssetEntity acc = tradingAccountRepository.findByTraderIdAndSymbol(
				event.getId(), event.getSymbol());

		if (acc == null) {

			AssetEntity acc1 = new AssetEntity();

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

		AssetEntity acc = tradingAccountRepository.findByTraderIdAndSymbol(
				event.getId(), event.getSymbol());

		if (acc == null) {
			AssetEntity acc1 = new AssetEntity();
			
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
