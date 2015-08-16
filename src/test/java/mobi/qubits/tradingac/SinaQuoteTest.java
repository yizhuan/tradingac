package mobi.qubits.tradingac;

import static org.junit.Assert.*;
import mobi.qubits.tradingac.sina.SinaQuote;
import mobi.qubits.tradingac.sina.SinaQuoteRealtime;

import org.junit.Test;

public class SinaQuoteTest {

	
	@Test
	public void testQuote(){
		SinaQuoteRealtime s = new SinaQuoteRealtime();
		
		SinaQuote q = s.getQuote("600036");
		
		assertTrue(q.getSymbol().equals("600036"));
		assertTrue(q.getName().equals("招商银行"));
				
	}
	
}
