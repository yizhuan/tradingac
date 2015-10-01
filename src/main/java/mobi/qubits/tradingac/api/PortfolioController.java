package mobi.qubits.tradingac.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.qubits.tradingac.query.trade.AssetEntity;
import mobi.qubits.tradingac.query.trade.AssetEntityRepository;
import mobi.qubits.tradingac.query.trade.QuoteEntity;
import mobi.qubits.tradingac.query.trade.QuoteEntityRepository;
import mobi.qubits.tradingac.query.trade.TradeEntityRepository;
import mobi.qubits.tradingac.query.trade.TraderEntity;
import mobi.qubits.tradingac.query.trade.TraderEntityRepository;
import mobi.qubits.tradingac.quote.QuoteProvider;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.DefaultIdentifierFactory;
import org.axonframework.domain.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author yizhuan
 *
 */
@RestController
public class PortfolioController /*extends QuoteProvider*/{

	private final IdentifierFactory identifierFactory = new DefaultIdentifierFactory();

	@Autowired
	private TraderEntityRepository traderEntryRepository;
	
	@Autowired
	private TradeEntityRepository tradeEntryRepository;


	@Autowired
	private AssetEntityRepository tradingBalanceRepository;

	
	@Autowired
	private QuoteEntityRepository quoteRepo;
	
	
	@Autowired
	private AssetEntityRepository assetRepo;
	

	@Autowired
	private CommandGateway cmdGateway;
	

	@RequestMapping(value = "/api/traders/{id}/portfolios", method = RequestMethod.GET)	
	public @ResponseBody Portfolio portfolios(@PathVariable String id) {
		
		TraderEntity trader = traderEntryRepository.findOne(id);
		
		List<AssetEntity> assetEntities = assetRepo.findByTraderId(id);
		List<String> symbols = new ArrayList<String>();
		for (AssetEntity e : assetEntities){
			symbols.add(e.getSymbol());
		}
		
		Map<String, QuoteEntity> quotes = new HashMap<String, QuoteEntity>();
		for (String sym : symbols){
			QuoteEntity e = quoteRepo.findOne(sym);
			if (e!=null)
				quotes.put(sym, e);
		}
					
		
		Float assetCurrentValue = 0.0f;
		Float assetGain = 0.0f; 
		
		List<Asset> assets = new ArrayList<Asset>();
		
		for (AssetEntity e : assetEntities){
			
			String symbol = e.getSymbol();
			QuoteEntity quote = quotes.get(symbol);
			Float price = quote.getCurrentQuote();
			Float costPrice = e.getCostPerShare();
			Long shares = e.getShares();
			
			Float currentValue = shares * price;
			Float costValue = shares * costPrice;
			
			Float gain = currentValue - costValue;
			Float gainPct = 100.0f * (gain / costValue);
			
			assetCurrentValue += currentValue;
			assetGain += gain;
			
			Asset asset = new Asset();
			asset.setCost(costPrice);
			asset.setCurrentPrice(price);
			asset.setCurrentValue(currentValue);
			
			asset.setGain(gain);
			asset.setGainPct(gainPct);
			
			asset.setShares(shares);
			asset.setSymbol(symbol);
						
			assets.add(asset);			
		}
		
		PortfolioSummary summary = new PortfolioSummary();
		summary.setCurrentValue(trader.getInvestment()+assetGain);
		summary.setAssetValue(assetCurrentValue);
		summary.setInvestment(trader.getInvestment());
		
		Float sgain = assetGain;
		Float sgainPct = 100.0f * (sgain / trader.getInvestment());
		
		summary.setGain(sgain);
		summary.setGainPct(sgainPct);		
		
		Portfolio portfolio = new Portfolio();
		
		portfolio.setTraderId(id);
		
		portfolio.setAssets(assets);
		portfolio.setSummary(summary);
		
		return portfolio;		
	}
	
	
}
