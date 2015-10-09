package mobi.qubits.tradingapp.domain.trader.events;

import java.io.Serializable;

/**
 * 
 * @author yizhuan
 *
 */
public interface TradeEvent extends Serializable {
	public String getId();
	public String getSymbol();
	public Long getShares();	
	public Float getPrice();
}
