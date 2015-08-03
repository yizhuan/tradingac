package mobi.qubits.tradingac.domain.trade;

import mobi.qubits.tradingac.domain.commands.BuyCommand;
import mobi.qubits.tradingac.domain.commands.SellCommand;
import mobi.qubits.tradingac.domain.events.BuyEvent;
import mobi.qubits.tradingac.domain.events.SellEvent;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * 
 * @author yizhuan
 *
 */
public class Trade extends AbstractAnnotatedAggregateRoot<String>{

	private static final long serialVersionUID = -8636138534455558578L;

	@AggregateIdentifier
	private String id;	
	
	private Short type; //0: sell; 1: buy
		
	Trade(){
		
	}
	
	@CommandHandler
	public Trade(BuyCommand cmd) {
		//make domain changes here
		apply(new BuyEvent(cmd.getId(), cmd.getSymbol(), cmd.getShares(), cmd.getPrice()));
	}
	
	@CommandHandler
	void on(SellCommand cmd) {
		//make domain changes here
		apply(new SellEvent(cmd.getId(), cmd.getSymbol(), cmd.getShares(), cmd.getPrice()));
	}
		
	//called immediately when events are applied to the local aggregate
	@EventSourcingHandler
	void on(SellEvent event) {
		this.id = event.getId();	
		this.type = 0;
	}
	
	@EventSourcingHandler
	void on(BuyEvent event) {
		this.id = event.getId();
		this.type = 1;
	}

	
}
