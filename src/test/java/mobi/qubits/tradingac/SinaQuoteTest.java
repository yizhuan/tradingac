package mobi.qubits.tradingac;

import static org.junit.Assert.*;
import mobi.qubits.tradingac.quote.sina.SinaQuote;
import mobi.qubits.tradingac.quote.sina.SinaQuoteService;

import org.junit.Test;

public class SinaQuoteTest {

	
	@Test
	public void testQuote(){
		SinaQuoteService s = new SinaQuoteService();
		
		SinaQuote q = s.getQuote("600036");
		
		assertTrue(q.getSymbol().equals("600036"));
		assertTrue(q.getName().equals("招商银行"));
				
	}
	
}
