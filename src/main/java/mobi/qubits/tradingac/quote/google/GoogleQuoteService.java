package mobi.qubits.tradingac.quote.google;

import java.io.IOException;
import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mobi.qubits.tradingac.quote.QuoteService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * http://finance.google.com/finance/info?client=ig&q=NASDAQ:GOOG
 * 
 * http://finance.google.com/finance/info?client=ig&q=SHA:600036     (SHANGHAI)
 * http://finance.google.com/finance/info?client=ig&q=SHE:000002     (SHENZHEN)
 * 
 * 
 * @author yizhuan
 *
 */
public class GoogleQuoteService implements QuoteService {

	
	public GoogleQuote getQuote(String symbol){
		
		Client client = ClientBuilder.newClient();

		String url = "http://finance.google.com/finance/info?client=ig&q="+ GoogleSymbolUtil.toGoogleSymbol(symbol);
		
		Response res = client.target(url).request(MediaType.TEXT_PLAIN).get();
		
		String response = res.readEntity(String.class);
		
		if (response.contains("FAILED")){
			return null;
		}
		else{
			try {
				return parse(symbol, response);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//TODO
			return null;
		}
	}
	
	private GoogleQuote parse(String symbol, String response) throws JsonParseException, JsonMappingException, IOException{
		
		/*  http://finance.google.com/finance/info?client=ig&q=SHE:000002
		 *  // [ { "id": "8085691" ,"t" : "000002" ,"e" : "SHE" ,"l" : "14.95" ,"l_fix" : "14.95" ,"l_cur" : "CN¥14.95" ,"s": "0" ,"ltt":"3:00PM GMT+8" ,"lt" : "Aug 14, 3:00PM GMT+8" ,"lt_dts" : "2015-08-14T15:00:25Z" ,"c" : "+0.07" ,"c_fix" : "0.07" ,"cp" : "0.47" ,"cp_fix" : "0.47" ,"ccol" : "chg" ,"pcls_fix" : "14.88" } ]
		{
		  "id": "8085691",
		  "t": "000002",
		  "e": "SHE",
		  "l": "14.95",
		  "l_fix": "14.95",
		  "l_cur": "CN¥14.95",
		  "s": "0",
		  "ltt": "3:00PM GMT+8",
		  "lt": "Aug 14, 3:00PM GMT+8",
		  "lt_dts": "2015-08-14T15:00:25Z",
		  "c": "+0.07",  //change
		  "c_fix": "0.07",
		  "cp": "0.47",
		  "cp_fix": "0.47",
		  "ccol": "chg",
		  "pcls_fix": "14.88"
		}	
	 */
		
		GoogleQuote q = new GoogleQuote();
		
		String src = response.substring(response.indexOf("[") + 1, response.indexOf("]"));
		
		ObjectMapper om = new ObjectMapper();

		HashMap<String,String> hm = (HashMap<String,String>) om.readValue(src, HashMap.class);
		q.setSymbol(symbol);
		float currentQuote = strToFloat(hm.get("l"));
		q.setCurrentQuote(currentQuote);
		q.setQuoteTime(hm.get("ltt"));
		q.setDate(hm.get("lt"));
		
		String change = hm.get("c");
		
		//TODO: this is not correct.
		Float prevClose = change==null||change.length()==0 ? currentQuote : currentQuote - strToFloat(change);
		
		q.setPrevClose(prevClose);

		return q;
	}
	
	private float strToFloat(String str){		
		return Float.valueOf(str);		
	}

	
}
