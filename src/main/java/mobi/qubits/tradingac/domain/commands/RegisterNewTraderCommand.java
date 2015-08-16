package mobi.qubits.tradingac.domain.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class RegisterNewTraderCommand {

	@TargetAggregateIdentifier
	private String id;
	
	private String name;

	public RegisterNewTraderCommand(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
