package mobi.qubits.tradingac.domain.quote.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdateQuoteCommand {

	@TargetAggregateIdentifier
	private String symbol;

	public UpdateQuoteCommand(String symbol) {
		super();

		this.symbol = symbol;
	}


	public String getSymbol() {
		return symbol;
	}
}
