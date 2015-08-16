package mobi.qubits.tradingac.domain.trade;

import mobi.qubits.tradingac.domain.commands.BuyCommand;
import mobi.qubits.tradingac.domain.commands.RegisterNewTraderCommand;
import mobi.qubits.tradingac.domain.commands.SellCommand;
import mobi.qubits.tradingac.domain.events.BuyEvent;
import mobi.qubits.tradingac.domain.events.RegisterNewTraderEvent;
import mobi.qubits.tradingac.domain.events.SellEvent;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class Trader extends AbstractAnnotatedAggregateRoot<String>{

	@AggregateIdentifier
	private String id;
	
	Trader() {
		
	}
	
	@CommandHandler
	public Trader(RegisterNewTraderCommand cmd) {	
		apply(new RegisterNewTraderEvent(cmd.getId(), cmd.getName()));
	}	
	
	
	@CommandHandler
	public void on(BuyCommand cmd) {	
		apply(new BuyEvent(cmd.getId(), cmd.getSymbol(), cmd.getShares(), cmd.getPrice()));
	}

	@CommandHandler
	public void on(SellCommand cmd) {	
		apply(new SellEvent(cmd.getId(), cmd.getSymbol(), cmd.getShares(), cmd.getPrice()));
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
