package mobi.qubits.tradingapp.domain.trader.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * 
 * @author yizhuan
 *
 */
public class ModifyTraderInfoCommand {

	@TargetAggregateIdentifier
	private String id;
	
	private String name;
	
	private Float investment;

	public ModifyTraderInfoCommand(String id, String name, Float investment) {
		super();
		this.id = id;
		this.name = name;
		this.investment = investment;
	}

	public String getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public Float getInvestment() {
		return investment;
	}

	
	
}
