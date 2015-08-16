package mobi.qubits.tradingac.quote.google;

/**
 * 
 * @author yizhuan
 *
 */
public class GoogleSymbolUtil {

	/**
	 * Add a prefix to symbol by Google symbol convention, e.g. 600036 to SHA:600036.
	 * 
	 * @param symbol
	 * @return
	 */
	 public static String toGoogleSymbol(String symbol){
		 if ( symbol.startsWith("600")
				 ||symbol.startsWith("601")
				 ||symbol.startsWith("603")
				 ){
			 return "SHA:"+symbol;
		 }
		 else if ( symbol.startsWith("000")
				 ||symbol.startsWith("002")
				 ||symbol.startsWith("300")				 
				 ){
			 return "SHE:"+symbol;
		 }			 
		 else{ 
			 //TODO: ignored other markets, this is wrong.   
			 return "NASDAQ:"+symbol;
		 }
	 }
	
}
