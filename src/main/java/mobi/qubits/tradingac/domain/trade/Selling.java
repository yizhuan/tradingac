package mobi.qubits.tradingac.domain.trade;

import mobi.qubits.tradingac.domain.commands.SellCommand;
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
public class Selling extends AbstractAnnotatedAggregateRoot<String>{

	private static final long serialVersionUID = -8636138534455558578L;

	@AggregateIdentifier
	private String id;
	
	Selling(){
		
	}
	
	@CommandHandler
	public Selling(SellCommand cmd) {	
		apply(new SellEvent(cmd.getId(), cmd.getSymbol(), cmd.getShares(), cmd.getPrice()));
	}
	
	@EventSourcingHandler
	void on(SellEvent event) {
		this.id = event.getId();
	}
	

	
}
