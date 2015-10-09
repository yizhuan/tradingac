package mobi.qubits.tradingapp;

import static org.junit.Assert.*;
import mobi.qubits.tradingapp.quote.sina.SinaQuote;
import mobi.qubits.tradingapp.quote.sina.SinaQuoteService;

import org.junit.Test;

/**
 * 
 * @author yizhuan
 *
 */
public class SinaQuoteTest {

	
	@Test
	public void testQuote(){
		SinaQuoteService s = new SinaQuoteService();
		
		SinaQuote q = s.getQuote("600036");
		
		assertTrue(q.getSymbol().equals("600036"));
		assertTrue(q.getName().equals("招商银行"));
				
	}
	
}
