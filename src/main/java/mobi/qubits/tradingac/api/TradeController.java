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
public class TradeController {

	private final IdentifierFactory identifierFactory = new DefaultIdentifierFactory();

	@Autowired
	private TradeEntryRepository tradeEntryRepository;

	@Autowired
	private TradingAccountRepository tradingAccountRepository;


	@Autowired
	private CommandGateway cmdGateway;

	@RequestMapping(value = "/api/trades/{type}", method = RequestMethod.GET)
	public @ResponseBody List<TradeEntry> findlAll(@PathVariable short type) {
		return tradeEntryRepository.findByType(type);
	}

	@RequestMapping(value = "/api/trades", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void createTrade(@RequestBody @Valid TradingRequest request) {
		switch (request.getType()) {
		case 0:// sell
			cmdGateway.send(new SellCommand(identifierFactory
					.generateIdentifier(), request.getSymbol(), request
					.getShares(), request.getPrice()));
			break;
		case 1:// buy
			cmdGateway.send(new BuyCommand(identifierFactory
					.generateIdentifier(), request.getSymbol(), request
					.getShares(), request.getPrice()));
			break;
		}

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
