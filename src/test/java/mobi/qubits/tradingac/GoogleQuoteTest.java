package mobi.qubits.tradingac;

import static org.junit.Assert.assertTrue;
import mobi.qubits.tradingac.quote.google.GoogleQuote;
import mobi.qubits.tradingac.quote.google.GoogleQuoteService;

import org.junit.Test;

/**
 * 
 * @author yizhuan
 *
 */
public class GoogleQuoteTest {

	
	@Test
	public void testQuoteCmb(){
		GoogleQuoteService s = new GoogleQuoteService();
		
		GoogleQuote q = s.getQuote("600036");
		
		assertTrue(q.getSymbol().equals("600036"));
				
	}
	
	@Test
	public void testQuoteGoog(){
		GoogleQuoteService s = new GoogleQuoteService();
		
		GoogleQuote q = s.getQuote("GOOG");
		
		assertTrue(q.getSymbol().equals("GOOG"));
				
	}

}
