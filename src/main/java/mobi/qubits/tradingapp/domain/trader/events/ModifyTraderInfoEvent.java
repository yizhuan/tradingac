package mobi.qubits.tradingapp.domain.trader.events;

/**
 * 
 * @author yizhuan
 *
 */
public class ModifyTraderInfoEvent {

	private String id;
	
	private String name;
	
	private Float investment;

	public ModifyTraderInfoEvent(String id, String name,  Float investment) {
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
