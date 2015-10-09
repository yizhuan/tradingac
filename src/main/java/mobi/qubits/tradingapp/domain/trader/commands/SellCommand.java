package mobi.qubits.tradingapp.domain.trader.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * 
 * @author yizhuan
 *
 */
public class SellCommand {

	@TargetAggregateIdentifier
	private String id; //traderId
	
	private String symbol;	
	private Long shares;
	private Float price;
	
	private Float costPerShare;
	
	public SellCommand(String id, String symbol, Long shares, Float price, Float costPerShare) {
		super();
		this.id = id;
		this.symbol = symbol;
		this.shares = shares;
		this.price = price;
		
		this.costPerShare = costPerShare;
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

	public Float getCostPerShare() {
		return costPerShare;
	}

	public void setCostPerShare(Float costPerShare) {
		this.costPerShare = costPerShare;
	}	
	
}
