package mobi.qubits.tradingac.api;

import java.util.List;

import javax.validation.Valid;

import mobi.qubits.tradingac.api.requests.TradingRequest;
import mobi.qubits.tradingac.domain.commands.BuyCommand;
import mobi.qubits.tradingac.domain.commands.SellCommand;
import mobi.qubits.tradingac.query.trade.TradeEntry;
import mobi.qubits.tradingac.query.trade.TradeEntryRepository;
import mobi.qubits.tradingac.query.trade.TradingAccount;
import mobi.qubits.tradingac.query.trade.TradingAccountRepository;

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
	private TradeEntryRepository tradeEntryRepository;

	@Autowired
	private TradingAccountRepository tradingAccountRepository;


	@Autowired
	private CommandGateway cmdGateway;
	
	@RequestMapping(value = "/api/trades/buy", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<?> buy(@RequestBody @Valid TradingRequest request, UriComponentsBuilder b) {
		String id = identifierFactory.generateIdentifier();
		cmdGateway.send(new BuyCommand(id, request.getSymbol(), request
				.getShares(), request.getPrice()));

		UriComponents uriComponents = b.path("/api/trades/{id}").buildAndExpand(
				id);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponents.toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/trades/{id}/sell", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void sell(@RequestBody @Valid TradingRequest request, @PathVariable String id) {
			cmdGateway.send(new SellCommand(id, request.getSymbol(), request
					.getShares(), request.getPrice()));
	}	
	
	
	@RequestMapping(value = "/api/trades/{id}", method = RequestMethod.GET)
	public TradeEntry findlTrade(@PathVariable String id) {
		return tradeEntryRepository.findOne(id);
	}
	
	@RequestMapping(value = "/api/trades/types/{type}", method = RequestMethod.GET)
	public @ResponseBody List<TradeEntry> findlAll(@PathVariable short type) {
		return tradeEntryRepository.findByType(type);
	}
	
	
	
	@RequestMapping(value = "/api/tradingaccount/{symbol}", method = RequestMethod.GET)
	public @ResponseBody TradingAccount find(@PathVariable String symbol) {
		return tradingAccountRepository.findBySymbol(symbol);
	}

	@RequestMapping(value = "/api/tradingaccount", method = RequestMethod.GET)
	public @ResponseBody List<TradingAccount> findlAll() {
		return tradingAccountRepository.findAll();
	}
	
}
