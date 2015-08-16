package mobi.qubits.tradingac.quote;

/**
 * A real-time quote value object.
 * 
 * @author yizhuan
 *
 */
public class Quote {

	private String symbol;
	
	private String name;
	private float open;
	private float prevClose;
	private float currentQuote;
	private float high;
	private float low;
	
	private String date;
	private String timestamp;	
	
	public Quote() {
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
	public void setPrevClose(float lastClose) {
		this.prevClose = lastClose;
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
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timstamp) {
		this.timestamp = timstamp;
	}
	
	
	
}
