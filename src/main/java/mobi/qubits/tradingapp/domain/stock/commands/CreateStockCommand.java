package mobi.qubits.tradingapp.domain.stock.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreateStockCommand {

	@TargetAggregateIdentifier
	private String id;

	private String symbol;

	public CreateStockCommand(String id, String symbol) {
		super();

		this.id = id;
		this.symbol = symbol;
	}

	public String getId() {
		return id;
	}

	public String getSymbol() {
		return symbol;
	}

}
