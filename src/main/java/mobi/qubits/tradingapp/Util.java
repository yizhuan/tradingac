package mobi.qubits.tradingapp;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class Util {
	
	
	public static boolean isNumeric(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
	

}
