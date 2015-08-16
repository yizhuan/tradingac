package mobi.qubits.tradingac.sina;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Get a quote from Sina web service.
 * 
 * @author yizhuan
 *
 */
public class SinaQuoteRealtime {

	public SinaQuote getQuote(String symbol){
		Client client = ClientBuilder.newClient();

		String url = "http://hq.sinajs.cn/list="+ SinaSymbolUtil.toSinaSymbol(symbol);
		
		Response res = client.target(url).request(MediaType.TEXT_PLAIN).get();
		
		String response = res.readEntity(String.class);
		
		return parse(symbol, response);
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
		sq.setLastClose(strToFloat(values[2]));
		sq.setCurrentQuote(strToFloat(values[3]));
		sq.setHigh(strToFloat(values[4]));
		sq.setLow(strToFloat(values[5]));
		
		sq.setDate(values[30]);
		sq.setTimestamp(values[31]);
		
		return sq;
	}
	
	private float strToFloat(String str){		
		return Float.valueOf(str);		
	}

	
}
