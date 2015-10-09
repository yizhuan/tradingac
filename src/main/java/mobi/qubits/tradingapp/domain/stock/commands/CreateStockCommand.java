package mobi.qubits.tradingapp.domain.stock.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreateStockCommand {

	@TargetAggregateIdentifier
	
	private String symbol;

	public CreateStockCommand(String symbol) {
		super();
		
		this.symbol = symbol;
	}


	public String getSymbol() {
		return symbol;
	}
	
	
	
}
