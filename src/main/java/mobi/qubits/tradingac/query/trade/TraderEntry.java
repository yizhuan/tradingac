package mobi.qubits.tradingac.query.trade;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author yizhuan
 *
 */
public class TraderEntry {

	@Id
	private String id;
	
	private String name;
	
	public TraderEntry(String id, String name) {
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
