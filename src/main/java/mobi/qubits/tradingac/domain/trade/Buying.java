package mobi.qubits.tradingac.domain.trade;

import mobi.qubits.tradingac.domain.commands.BuyCommand;
import mobi.qubits.tradingac.domain.events.BuyEvent;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * 
 * @author yizhuan
 *
 */
public class Buying extends AbstractAnnotatedAggregateRoot<String>{

	private static final long serialVersionUID = -8636138534455558578L;

	@AggregateIdentifier
	private String id;
	
	Buying(){
		
	}
	
	@CommandHandler
	public Buying(BuyCommand cmd) {	
		apply(new BuyEvent(cmd.getId(), cmd.getSymbol(), cmd.getShares(), cmd.getPrice()));
	}
	
	@EventSourcingHandler
	void on(BuyEvent event) {
		this.id = event.getId();		
	}
		
}
