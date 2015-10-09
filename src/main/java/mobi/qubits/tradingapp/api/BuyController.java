package mobi.qubits.tradingapp.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import mobi.qubits.tradingapp.api.requests.BuyRequest;
import mobi.qubits.tradingapp.domain.trader.commands.BuyShareCommand;
import mobi.qubits.tradingapp.query.trade.AssetEntityRepository;
import mobi.qubits.tradingapp.query.trade.QuoteEntity;
import mobi.qubits.tradingapp.query.trade.QuoteEntityRepository;
import mobi.qubits.tradingapp.query.trade.TradeEntity;
import mobi.qubits.tradingapp.query.trade.TradeEntityRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author yizhuan
 *
 */
@RestController
public class BuyController{
	
	@Autowired
	private TradeEntityRepository tradeEntryRepository;


	@Autowired
	private QuoteEntityRepository quoteRepo;
	
	
	@Autowired
	private AssetEntityRepository assetRepo;
	

	@Autowired
	private CommandGateway cmdGateway;
	
	@RequestMapping(value = "/api/traders/{id}/buy", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void buy(@RequestBody @Valid BuyRequest request, @PathVariable String id) {			
		cmdGateway.send(new BuyShareCommand(id, request.getSymbol(), request
				.getShares(), request.getPrice()));
		
	}	
	

	@RequestMapping(value = "/api/traders/{id}/trades/{symbol}/buys", method = RequestMethod.GET)
	public @ResponseBody List<BuyEntry> findBuys(@PathVariable String id, @PathVariable String symbol) {
		
		QuoteEntity q = quoteRepo.findOne(symbol);
		
		List<TradeEntity> buys = tradeEntryRepository.findByTraderIdAndSymbolAndType(id, symbol, (short)1);
		
		List<BuyEntry> buyEntries = new ArrayList<BuyEntry>();
		
		for (TradeEntity e : buys){
			BuyEntry buy = new BuyEntry(e, q);
			buyEntries.add(buy);
		}
		
		return buyEntries;
	}

	
}
