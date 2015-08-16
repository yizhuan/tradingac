package mobi.qubits.tradingac.api;

import java.util.List;

import javax.validation.Valid;

import mobi.qubits.tradingac.api.requests.TraderRequest;
import mobi.qubits.tradingac.api.requests.TradingRequest;
import mobi.qubits.tradingac.domain.commands.BuyCommand;
import mobi.qubits.tradingac.domain.commands.RegisterNewTraderCommand;
import mobi.qubits.tradingac.domain.commands.SellCommand;
import mobi.qubits.tradingac.query.trade.TradeEntry;
import mobi.qubits.tradingac.query.trade.TradeEntryRepository;
import mobi.qubits.tradingac.query.trade.TraderEntry;
import mobi.qubits.tradingac.query.trade.TraderEntryRepository;
import mobi.qubits.tradingac.query.trade.TradingBalance;
import mobi.qubits.tradingac.query.trade.TradingBalanceRepository;

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
public class TradeController {

	private final IdentifierFactory identifierFactory = new DefaultIdentifierFactory();

	@Autowired
	private TraderEntryRepository traderEntryRepository;
	
	@Autowired
	private TradeEntryRepository tradeEntryRepository;


	@Autowired
	private TradingBalanceRepository tradingBalanceRepository;


	@Autowired
	private CommandGateway cmdGateway;
	
	@RequestMapping(value = "/api/traders/", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<?> createTrader(@RequestBody @Valid TraderRequest request, UriComponentsBuilder b) {
		String id = identifierFactory.generateIdentifier();
		cmdGateway.send(new RegisterNewTraderCommand(id, request.getName()));

		UriComponents uriComponents = b.path("/api/traders/{id}").buildAndExpand(
				id);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponents.toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}	
	
	@RequestMapping(value = "/api/traders/{id}/buy", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void buy(@RequestBody @Valid TradingRequest request, @PathVariable String id) {
		cmdGateway.send(new BuyCommand(id, request.getSymbol(), request
				.getShares(), request.getPrice()));
	}
	
	@RequestMapping(value = "/api/traders/{id}/sell", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void sell(@RequestBody @Valid TradingRequest request, @PathVariable String id) {
			cmdGateway.send(new SellCommand(id, request.getSymbol(), request
					.getShares(), request.getPrice()));
	}		
	
	@RequestMapping(value = "/api/traders/{id}", method = RequestMethod.GET)
	public TraderEntry findlTrade(@PathVariable String id) {
		return traderEntryRepository.findOne(id);
	}
	
	@RequestMapping(value = "/api/traders", method = RequestMethod.GET)
	public @ResponseBody List<TraderEntry> findlAllTraders() {
		return traderEntryRepository.findAll();
	}
	
	@RequestMapping(value = "/api/traders/{id}/trades", method = RequestMethod.GET)
	public @ResponseBody List<TradeEntry> find( @PathVariable String id) {
		return tradeEntryRepository.findByTraderId(id);
	}
	
			
	@RequestMapping(value = "/api/traders/{id}/trades/{symbol}", method = RequestMethod.GET)
	public @ResponseBody List<TradeEntry> find( @PathVariable String id, @PathVariable String symbol) {
		return tradeEntryRepository.findByTraderIdAndSymbol(id, symbol);
	}

	@RequestMapping(value = "/api/traders/{id}/trades/{symbol}/{type}", method = RequestMethod.GET)
	public @ResponseBody List<TradeEntry> findlAll(@PathVariable String id, @PathVariable String symbol,  @PathVariable Short type) {
		return tradeEntryRepository.findByTraderIdAndSymbolAndType(id, symbol, type);
	}

	
	@RequestMapping(value = "/api/traders/{id}/balance", method = RequestMethod.GET)
	public @ResponseBody List<TradingBalance> findBalance( @PathVariable String id) {
		return tradingBalanceRepository.findByTraderId(id);
	}	
	
	
	@RequestMapping(value = "/api/traders/{id}/balance/{symbol}", method = RequestMethod.GET)
	public @ResponseBody TradingBalance findBalance( @PathVariable String id, @PathVariable String symbol) {
		return tradingBalanceRepository.findByTraderIdAndSymbol(id, symbol);
	}	
	
}
