package mobi.qubits.tradingapp.api;

import java.util.List;

import javax.validation.Valid;

import mobi.qubits.tradingapp.api.requests.TraderRequest;
import mobi.qubits.tradingapp.domain.trader.commands.ModifyTraderInfoCommand;
import mobi.qubits.tradingapp.domain.trader.commands.RegisterNewTraderCommand;
import mobi.qubits.tradingapp.query.trade.AssetEntityRepository;
import mobi.qubits.tradingapp.query.trade.TradeEntity;
import mobi.qubits.tradingapp.query.trade.TradeEntityRepository;
import mobi.qubits.tradingapp.query.trade.TraderEntity;
import mobi.qubits.tradingapp.query.trade.TraderEntityRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.DefaultIdentifierFactory;
import org.axonframework.domain.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 
 * @author yizhuan
 *
 */
@RestController
public class TraderController{

	private final IdentifierFactory identifierFactory = new DefaultIdentifierFactory();

	@Autowired
	private TraderEntityRepository traderEntryRepository;
	
	@Autowired
	private TradeEntityRepository tradeEntryRepository;


	@Autowired
	private AssetEntityRepository tradingBalanceRepository;


	@Autowired
	private CommandGateway cmdGateway;
	
	@RequestMapping(value = "/api/traders", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<?> createTrader(@RequestBody @Valid TraderRequest request, UriComponentsBuilder b) {
		String id = identifierFactory.generateIdentifier();
		cmdGateway.send(new RegisterNewTraderCommand(id, request.getName(), request.getInvestment()));

		UriComponents uriComponents = b.path("/api/traders/{id}").buildAndExpand(
				id);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponents.toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}		

	@RequestMapping(value = "/api/traders", method = RequestMethod.GET)
	public @ResponseBody List<TraderEntity> findlAllTraders() {
		return traderEntryRepository.findAll();
	}
	
	@RequestMapping(value = "/api/traders/{id}", method = RequestMethod.GET)
	public TraderEntity findTrade(@PathVariable String id) {
		return traderEntryRepository.findOne(id);
	}
	
	@RequestMapping(value = "/api/traders/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateTraderInfo(@PathVariable String id, @RequestBody @Valid TraderRequest req) {
		cmdGateway.send(new ModifyTraderInfoCommand(id, req.getName(), req.getInvestment()));
		return new ResponseEntity<Void>(HttpStatus.OK);
	}	
	
	
	@RequestMapping(value = "/api/traders/{id}/trades", method = RequestMethod.GET)
	public @ResponseBody List<TradeEntity> find( @PathVariable String id) {
		return tradeEntryRepository.findByTraderId(id);
	}
	
			
	@RequestMapping(value = "/api/traders/{id}/trades/{symbol}", method = RequestMethod.GET)
	public @ResponseBody List<TradeEntity> find( @PathVariable String id, @PathVariable String symbol) {
		return tradeEntryRepository.findByTraderIdAndSymbol(id, symbol);
	}

	/*
	@RequestMapping(value = "/api/traders/{id}/trades/{symbol}/{type}", method = RequestMethod.GET)
	public @ResponseBody List<TradeEntity> findlAll(@PathVariable String id, @PathVariable String symbol,  @PathVariable Short type) {
		return tradeEntryRepository.findByTraderIdAndSymbolAndType(id, symbol, type);
	}
	

	
	@RequestMapping(value = "/api/traders/{id}/balance", method = RequestMethod.GET)
	public @ResponseBody List<AssetEntity> findBalance( @PathVariable String id) {
		return tradingBalanceRepository.findByTraderId(id);
	}	
	
	
	@RequestMapping(value = "/api/traders/{id}/balance/{symbol}", method = RequestMethod.GET)
	public @ResponseBody AssetEntity findBalance( @PathVariable String id, @PathVariable String symbol) {
		return tradingBalanceRepository.findByTraderIdAndSymbol(id, symbol);
	}
	
	
	//realtime balance on trading account
	@RequestMapping(value = "/api/traders/{id}/realtime-balance", method = RequestMethod.GET)
	public @ResponseBody List<RealtimeBalance> findRealtimeBalance( @PathVariable String id) {
		List<AssetEntity> bals = tradingBalanceRepository.findByTraderId(id);
		initQuoteMap(findSymbols1(bals));
		return getRealtimeOverallBalance(bals);
	}	
	
	//realtime balance on trading account
	@RequestMapping(value = "/api/traders/{id}/realtime-balance/{symbol}", method = RequestMethod.GET)
	public @ResponseBody RealtimeBalance findRealtimeBalance( @PathVariable String id, @PathVariable String symbol) {
		AssetEntity bal = tradingBalanceRepository.findByTraderIdAndSymbol(id, symbol);
		initQuoteMap(bal.getSymbol());
		return getRealtimeBalance(bal);
	}
	
	
	//realtime balance on historical trades (buying) showing historical gain/loss against current quote
	@RequestMapping(value = "/api/traders/{id}/trades/realtime-balance", method = RequestMethod.GET)
	public @ResponseBody List<RealtimeBalance> findRealtimeTradeBalance( @PathVariable String id) {
		
		List<AssetEntity> bals = tradingBalanceRepository.findByTraderId(id);
		
		initQuoteMap(findSymbols1(bals));
		
		List<TradeEntity> entries = tradeEntryRepository.findByTraderIdOrderBySymbolAsc(id);
		
		List<TradeEntity> results = new ArrayList<TradeEntity>();	

		//we will only list those with share balances.
		
		for (AssetEntity b: bals){
			if(b.getShares()<1L){
				continue;
			}
			for (TradeEntity e: entries){			
				if (e.getSymbol().equals(b.getSymbol())){
					results.add(e);
				}
			}
		}
		
		return getRealtimeTradeBalance(results);
	}
	
	//realtime balance on historical trades (buying) showing historical gain/loss against current quote
	@RequestMapping(value = "/api/traders/{id}/trades/{symbol}/realtime-balance", method = RequestMethod.GET)
	public @ResponseBody List<RealtimeBalance> findRealtimeTradeBalance( @PathVariable String id, @PathVariable String symbol) {
		List<TradeEntity> entries = tradeEntryRepository.findByTraderIdAndSymbolAndType(id, symbol, (short)1);
		initQuoteMap(findSymbols2(entries));
		return getRealtimeTradeBalance(entries);
	}	
	
	*/
}
