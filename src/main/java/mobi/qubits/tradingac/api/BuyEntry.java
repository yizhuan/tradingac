package mobi.qubits.tradingac.api;

import mobi.qubits.tradingac.query.trade.QuoteEntity;
import mobi.qubits.tradingac.query.trade.TradeEntity;

/**
 * 
 * @author yizhuan
 *
 */
public class BuyEntry {
	
	private String tradeId;
	
	private String symbol;
	private Long shares;
	private Float price;

	private Float gain;
	private Float gainPct;
	
	private Float currentQuote;
	private Float prevClose;
		
	public BuyEntry(TradeEntity entry, QuoteEntity quote){
		this.tradeId = entry.getId();
		this.symbol = entry.getSymbol();
		this.shares = entry.getShares();
		this.price = entry.getPrice();
		this.currentQuote = quote.getCurrentQuote();
		this.prevClose = quote.getPrevClose();
		
		this.gain = shares * (price - currentQuote);
		this.gainPct =  100.0f * (gain / (shares * price) );	
	}

	
	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
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

	public Float getGain() {
		return gain;
	}

	public void setGain(Float gain) {
		this.gain = gain;
	}

	public Float getGainPct() {
		return gainPct;
	}

	public void setGainPct(Float gainPct) {
		this.gainPct = gainPct;
	}

	public Float getCurrentQuote() {
		return currentQuote;
	}

	public void setCurrentQuote(Float currentQuote) {
		this.currentQuote = currentQuote;
	}

	public Float getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(Float prevClose) {
		this.prevClose = prevClose;
	}
	
}
