package mobi.qubits.tradingac.api;

import java.util.List;

import javax.validation.Valid;

import mobi.qubits.tradingac.domain.quote.commands.CreateStockCommand;
import mobi.qubits.tradingac.domain.quote.commands.UpdateQuoteCommand;
import mobi.qubits.tradingac.query.trade.QuoteEntity;
import mobi.qubits.tradingac.query.trade.QuoteEntityRepository;

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
public class StockController{
	

	@Autowired
	private QuoteEntityRepository quoteRepo;
	
	
	@Autowired
	private CommandGateway cmdGateway;
	
	@RequestMapping(value = "/api/stocks", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void createStock(@RequestBody @Valid StockDTO req) {
		cmdGateway.send(new CreateStockCommand(req.getSymbol()));
	}	
	
	
	@RequestMapping(value = "/api/stocks", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public @ResponseBody List<QuoteEntity> list() {
		List<QuoteEntity> stocks = quoteRepo.findAll();
		return stocks;
	}
	
	
	@RequestMapping(value = "/api/stocks/{symbol}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void update(@PathVariable String symbol) {
		cmdGateway.send(new UpdateQuoteCommand(symbol));
	}		
}
