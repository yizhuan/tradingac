package mobi.qubits.tradingapp.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import javax.validation.Valid;

import mobi.qubits.tradingapp.domain.stock.commands.CreateStockCommand;
import mobi.qubits.tradingapp.domain.stock.commands.UpdateQuoteCommand;
import mobi.qubits.tradingapp.query.QuoteEntity;
import mobi.qubits.tradingapp.query.QuoteEntityRepository;

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

/**
 *
 * @author yizhuan
 *
 */
@RestController
@RequestMapping(value = "/api/stocks")
public class StockController{

	private final IdentifierFactory identifierFactory = new DefaultIdentifierFactory();

	@Autowired
	private QuoteEntityRepository quoteRepo;


	@Autowired
	private CommandGateway cmdGateway;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Void> createStock(@RequestBody @Valid StockDTO req) {

		QuoteEntity stock = quoteRepo.findBySymbol(req.getSymbol());

		if (stock==null){
			String id = identifierFactory.generateIdentifier();
			cmdGateway.send(new CreateStockCommand(id, req.getSymbol()));
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(StockController.class).slash(req.getSymbol()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}


	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<QuoteEntity> list() {
		List<QuoteEntity> stocks = quoteRepo.findAll();
		return stocks;
	}

	@RequestMapping(value = "/{symbol}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody QuoteEntity getStock(@PathVariable String symbol ) {
		QuoteEntity stock = quoteRepo.findBySymbol(symbol);
		return stock;
	}

	@RequestMapping(value = "/{symbol}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void update(@PathVariable String symbol) {
		QuoteEntity stock = quoteRepo.findBySymbol(symbol);
		cmdGateway.send(new UpdateQuoteCommand(stock.getId(), symbol));
	}
}
