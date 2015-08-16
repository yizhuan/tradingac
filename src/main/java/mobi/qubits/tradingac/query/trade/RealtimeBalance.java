package mobi.qubits.tradingac.query.trade;

public class RealtimeBalance extends TradingBalance{
		
	private Float lastClose;
	private Float currentQuote;
	
	private Float gainToday;
	private Float gainTodayPct;
	
	private Float gain;
	private Float gainPct;
	
	
	public RealtimeBalance() {
		super();
	}


	public Float getLastClose() {
		return lastClose;
	}


	public void setLastClose(Float lastClose) {
		this.lastClose = lastClose;
	}


	public Float getCurrentQuote() {
		return currentQuote;
	}


	public void setCurrentQuote(Float currentQuote) {
		this.currentQuote = currentQuote;
	}


	public Float getGainToday() {
		return gainToday;
	}


	public void setGainToday(Float gainToday) {
		this.gainToday = gainToday;
	}


	public Float getGainTodayPct() {
		return gainTodayPct;
	}


	public void setGainTodayPct(Float gainTodayPct) {
		this.gainTodayPct = gainTodayPct;
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
	
	
}