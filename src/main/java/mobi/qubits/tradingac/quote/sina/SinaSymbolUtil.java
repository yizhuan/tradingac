package mobi.qubits.tradingac.quote.sina;

/**
 * 
 * @author yizhuan
 *
 */
public class SinaSymbolUtil {

	/**
	 * Add a prefix to symbol by Sina symbol convention, e.g. 600036 to sh600036.
	 * 
	 * @param symbol
	 * @return
	 */
	 public static String toSinaSymbol(String symbol){
		 if ( symbol.startsWith("600")
				 ||symbol.startsWith("601")
				 ||symbol.startsWith("603")
				 ){
			 return "sh"+symbol;
		 }
		 else if ( symbol.startsWith("000")
				 ||symbol.startsWith("002")
				 ||symbol.startsWith("300")				 
				 ){
			 return "sz"+symbol;
		 }			 
		 else 
			 return symbol;
	 }
	
}
