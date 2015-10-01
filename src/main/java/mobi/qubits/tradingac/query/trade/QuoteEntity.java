package mobi.qubits.tradingac.query.trade;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class QuoteEntity {

	@Id
	private String symbol;
	
	private String name;
	private float open;
	private float prevClose;
	private float currentQuote;
	private float high;
	private float low;
	
	private String date;
	private String quoteTime;	
	
	private Date timestamp;

	public QuoteEntity() {
		super();
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getOpen() {
		return open;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public float getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(float prevClose) {
		this.prevClose = prevClose;
	}

	public float getCurrentQuote() {
		return currentQuote;
	}

	public void setCurrentQuote(float currentQuote) {
		this.currentQuote = currentQuote;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getQuoteTime() {
		return quoteTime;
	}

	public void setQuoteTime(String quoteTime) {
		this.quoteTime = quoteTime;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
	
	
}
