package mobi.qubits.tradingac.domain.trader;

import java.util.List;

import mobi.qubits.tradingac.domain.trader.commands.BuyCommand;
import mobi.qubits.tradingac.domain.trader.commands.RegisterNewTraderCommand;
import mobi.qubits.tradingac.domain.trader.commands.SellCommand;
import mobi.qubits.tradingac.domain.trader.events.BuyEvent;
import mobi.qubits.tradingac.domain.trader.events.RegisterNewTraderEvent;
import mobi.qubits.tradingac.domain.trader.events.SellEvent;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * 
 * @author yizhuan
 *
 */
public class Trader extends AbstractAnnotatedAggregateRoot<String>{

	@AggregateIdentifier
	private String id;	
	
	private List<Share> shares;
	
	Trader() {
		
	}
	
	@CommandHandler
	public Trader(RegisterNewTraderCommand cmd) {	
		apply(new RegisterNewTraderEvent(cmd.getId(), cmd.getName(), cmd.getInvestment()));
	}	
	
	
	@CommandHandler
	public void on(BuyCommand cmd) {	
		apply(new BuyEvent(cmd.getId(), cmd.getSymbol(), cmd.getShares(), cmd.getPrice()));
	}

	@CommandHandler
	public void on(SellCommand cmd) {	
		apply(new SellEvent(cmd.getId(), cmd.getSymbol(), cmd.getShares(), cmd.getPrice(), cmd.getCostPerShare()));
	}
	

	@EventSourcingHandler
	void on(RegisterNewTraderEvent event) {
		this.id = event.getId();
	}
	
	
	@EventSourcingHandler
	void on(BuyEvent event) {
				
	}
			
	
	
	@EventSourcingHandler
	void on(SellEvent event) {
		
	}		
	
	
}
