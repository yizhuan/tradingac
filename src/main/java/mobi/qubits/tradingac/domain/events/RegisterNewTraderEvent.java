package mobi.qubits.tradingac.domain.events;

public class RegisterNewTraderEvent {

private String id;
	
	private String name;

	public RegisterNewTraderEvent(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}


}
