package mobi.qubits.tradingapp.query;

import mobi.qubits.tradingapp.domain.trader.events.BuyEvent;
import mobi.qubits.tradingapp.domain.trader.events.ModifyTraderInfoEvent;
import mobi.qubits.tradingapp.domain.trader.events.RegisterNewTraderEvent;
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
public class TraderListener {

	@Autowired
	private TraderEntityRepository traderEntryRepository;

	@Autowired
	private AssetEntityRepository assetRepo;

	@EventHandler
	void on(RegisterNewTraderEvent event) {
		TraderEntity entry = new TraderEntity(event.getId(), event.getName(), event.getInvestment());
		traderEntryRepository.save(entry);
	}

	@EventHandler
	void on(ModifyTraderInfoEvent event) {
		TraderEntity entry = traderEntryRepository.findOne(event.getId());
		entry.setName(event.getName());
		entry.setInvestment(event.getInvestment());
		traderEntryRepository.save(entry);
	}

	@EventHandler
	void on(BuyEvent event) {

		AssetEntity acc = assetRepo.findByTraderIdAndSymbol(
				event.getId(), event.getSymbol());

		if (acc == null) {

			AssetEntity acc1 = new AssetEntity();

			acc1.setTraderId(event.getId());
			acc1.setSymbol(event.getSymbol());
			acc1.setShares(event.getShares());
			acc1.setCostPerShare(event.getPrice());

			assetRepo.save(acc1);
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

			assetRepo.save(acc);
		}

	}

	@EventHandler
	void on(SellEvent event) {

		AssetEntity acc = assetRepo.findByTraderIdAndSymbol(
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

			assetRepo.save(acc);
		}

	}

}
