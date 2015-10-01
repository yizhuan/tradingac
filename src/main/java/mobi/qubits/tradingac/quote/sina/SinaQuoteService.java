package mobi.qubits.tradingac.quote.sina;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mobi.qubits.tradingac.quote.QuoteService;

/**
 * Get a quote from Sina web service.
 * 
 * @author yizhuan
 *
 */
public class SinaQuoteService implements QuoteService{

	public SinaQuote getQuote(String symbol){
		
		Client client = ClientBuilder.newClient();

		String url = "http://hq.sinajs.cn/list="+ SinaSymbolUtil.toSinaSymbol(symbol);
		
		Response res = client.target(url).request(MediaType.TEXT_PLAIN).get();
		
		String response = res.readEntity(String.class);
		
		if (response.contains("FAILED")){
			return null;
		}
		else{
			return parse(symbol, response);
		}
	}

	private SinaQuote parse(String symbol, String q) {
		//Specification:   http://blog.sina.com.cn/s/blog_58fc3aad01015nu7.html	
		
		String[] p = q.split("\"");
		String quote = p[1];
		
		String[] values = quote.split(",");
		
		SinaQuote sq = new SinaQuote();
		
		sq.setSymbol(symbol);		
		sq.setName(values[0]);
		sq.setOpen(strToFloat(values[1]));
		sq.setPrevClose(strToFloat(values[2]));
		sq.setCurrentQuote(strToFloat(values[3]));
		sq.setHigh(strToFloat(values[4]));
		sq.setLow(strToFloat(values[5]));
		
		sq.setDate(values[30]);
		sq.setQuoteTime(values[31]);
		
		return sq;
	}
	
	private float strToFloat(String str){		
		return Float.valueOf(str);		
	}

	
}
