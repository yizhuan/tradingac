package mobi.qubits.tradingac.domain.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * 
 * @author yizhuan
 *
 */
public class BuyCommand {

	@TargetAggregateIdentifier
	private String id;

	private String symbol;	
	private Long shares;
	private Float price;
	
	public BuyCommand(String id, String symbol, Long shares, Float price) {
		super();
		this.id = id;
		this.symbol = symbol;
		this.shares = shares;
		this.price = price;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Long getShares() {
		return shares;
	}
	public void setShares(Long shares) {
		this.shares = shares;
	}
	
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	

	
}
