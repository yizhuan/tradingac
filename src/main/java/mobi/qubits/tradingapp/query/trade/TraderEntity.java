package mobi.qubits.tradingapp.query.trade;

import org.springframework.data.annotation.Id;

/**
 * 
 * @author yizhuan
 *
 */
public class TraderEntity {

	@Id
	private String id;
	
	private String name;
	
	private Float investment = 0.0f;
	
	public TraderEntity() {
		super();
	}

	public TraderEntity(String id, String name) {
		this(id,name, 0.0f);		
	}
	
	public TraderEntity(String id, String name, Float investment) {
		super();
		this.id = id;
		this.name = name;
		this.investment = investment;
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

	public Float getInvestment() {
		return investment;
	}

	public void setInvestment(Float investment) {
		this.investment = investment;
	}
	
	
	
}
