package mobi.qubits.tradingac.domain.trader;

import mobi.qubits.tradingac.domain.quote.commands.CreateStockCommand;
import mobi.qubits.tradingac.domain.quote.commands.UpdateQuoteCommand;
import mobi.qubits.tradingac.domain.quote.events.QuoteUpdatedEvent;
import mobi.qubits.tradingac.domain.trader.commands.BuyCommand;
import mobi.qubits.tradingac.domain.trader.events.BuyShareEvent;
import mobi.qubits.tradingac.query.trade.QuoteEntity;
import mobi.qubits.tradingac.query.trade.QuoteEntityRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

public class TradingSaga extends AbstractAnnotatedSaga {

	private static final long serialVersionUID = -4307621759024578972L;

	@Autowired
	private transient QuoteEntityRepository quoteRepo;
	
	@Autowired
	private transient CommandGateway cmdGateway;
	
	private String symbol;
	private String eventId;
	private long shares;
	private float price;
	
	@StartSaga
	@SagaEventHandler(associationProperty="symbol")
	public void on(BuyShareEvent event) {

		symbol = event.getSymbol();
		eventId =  event.getId();
		shares = event.getShares();
		price = event.getPrice();		
		
		QuoteEntity q = quoteRepo.findOne(event.getSymbol());
		if (q==null){
			//associateWith("symbol", symbol);
			cmdGateway.send(new CreateStockCommand(symbol));
			cmdGateway.send(new UpdateQuoteCommand(symbol));
		}
		else{
			cmdGateway.send(new BuyCommand(event.getId(), event.getSymbol(), event
					.getShares(), event.getPrice()));
			end();
		}
	}
	
	@SagaEventHandler(associationProperty="symbol")
	public void on(QuoteUpdatedEvent event){
		cmdGateway.send(new BuyCommand(eventId, symbol, shares, price));
		end();
	}
	
	
}
