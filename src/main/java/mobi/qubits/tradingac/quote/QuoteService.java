package mobi.qubits.tradingac.quote;

/**
 * 
 * @author yizhuan
 *
 */
public interface QuoteService {
	
	public Ticker getQuote(String symbol);
}
