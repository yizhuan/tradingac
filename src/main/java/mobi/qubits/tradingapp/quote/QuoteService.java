package mobi.qubits.tradingapp.quote;

/**
 * 
 * @author yizhuan
 *
 */
public interface QuoteService {
	
	public Ticker getQuote(String symbol);
}
