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
	
	private String symbol;
	private Long shares = 0L;
		
	Trade(){
		
	}
	
	@CommandHandler
	public Trade(BuyCommand cmd) {	
		apply(new BuyEvent(cmd.getId(), cmd.getSymbol(), cmd.getShares(), cmd.getPrice()));
	}
	
	@CommandHandler
	void on(SellCommand cmd) {
		//if (this.shares >= cmd.getShares()){
			apply(new SellEvent(id, cmd.getSymbol(), cmd.getShares(), cmd.getPrice()));
		//}
	}

	//called immediately when events are applied to the local aggregate
	@EventSourcingHandler
	void on(BuyEvent event) {
		this.id = event.getId();		
		this.symbol = event.getSymbol();
		this.shares = this.shares + event.getShares();
	}
	
	
	//called immediately when events are applied to the local aggregate
	@EventSourcingHandler
	void on(SellEvent event) {
		this.shares = this.shares - event.getShares();
	}
	

	
}
