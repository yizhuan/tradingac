package mobi.qubits.tradingac.quote;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

import mobi.qubits.tradingac.query.trade.RealtimeBalance;
import mobi.qubits.tradingac.query.trade.TradeEntry;
import mobi.qubits.tradingac.query.trade.TradingBalance;
import mobi.qubits.tradingac.quote.google.GoogleQuoteService;
import mobi.qubits.tradingac.quote.sina.SinaQuoteService;

public class QuoteProvider {

	public Quote getQuote(String symbol){
		QuoteService service = isNumeric(symbol)? new SinaQuoteService() :  new GoogleQuoteService();
		Quote q = service.getQuote(symbol);	
		return q;
	}
	
	
	public List<RealtimeBalance> getRealtimeBalance(List<TradingBalance> tradingBalances){
		List<RealtimeBalance> bals = new ArrayList<RealtimeBalance>();
		for (TradingBalance bal : tradingBalances){
			RealtimeBalance rt = getRealtimeBalance(bal);
			if (rt!=null)
				bals.add(rt);
		}		
		return bals;
	}
	

	public RealtimeBalance getRealtimeBalance(TradingBalance tradingBalance){		
		Quote q = getQuote(tradingBalance.getSymbol());			
		return q==null?null:getRealtimeBalance(q, tradingBalance);
	}

	
	public RealtimeBalance getRealtimeBalance(Quote quote, TradingBalance tradingBalance){
		
		Long shares = tradingBalance.getShares();
		
		RealtimeBalance bal = new RealtimeBalance();
		bal.setId(tradingBalance.getId());
		bal.setTraderId(tradingBalance.getTraderId());
		bal.setSymbol(tradingBalance.getSymbol());
		bal.setShares(shares);
		bal.setCostPerShare(tradingBalance.getCostPerShare());
		
		bal.setPrevClose(quote.getPrevClose());
		bal.setCurrentQuote(quote.getCurrentQuote());
		
		
		Float gainToday = shares * (quote.getCurrentQuote() - quote.getPrevClose());
		Float gainTodayPct = 100.0f*(quote.getCurrentQuote()- quote.getPrevClose())/quote.getPrevClose();
		
		Float gain = shares * (quote.getCurrentQuote() - tradingBalance.getCostPerShare());
		Float gainPct = 100.0f*(quote.getCurrentQuote() - tradingBalance.getCostPerShare())/quote.getPrevClose();
		
		bal.setGainToday(gainToday);
		bal.setGainTodayPct(gainTodayPct);
		bal.setGain(gain);
		bal.setGainPct(gainPct);		
		
		return bal;
	}
	
		
	
	
	//balances on trade history (buying)	
	
	public List<RealtimeBalance> getRealtimeTradeBalance(List<TradeEntry> entries){
		List<RealtimeBalance> bals = new ArrayList<RealtimeBalance>();
		for (TradeEntry bal : entries){
			RealtimeBalance rt = getRealtimeTradeBalance(bal);
			if (rt!=null)
				bals.add(rt);
		}		
		return bals;
	}
	

	public RealtimeBalance getRealtimeTradeBalance(TradeEntry entry){
		String symbol = entry.getSymbol();
		Quote q = getQuote(symbol);
		return q==null?null:getRealtimeTradeBalance(q, entry);
	}
	
	public RealtimeBalance getRealtimeTradeBalance(Quote quote, TradeEntry entry){
		
		Long shares = entry.getShares();
		
		RealtimeBalance bal = new RealtimeBalance();
		bal.setId(entry.getId());
		bal.setTraderId(entry.getTraderId());
		bal.setSymbol(entry.getSymbol());
		bal.setShares(shares);
		bal.setCostPerShare(entry.getPrice());
		
		bal.setPrevClose(quote.getPrevClose());
		bal.setCurrentQuote(quote.getCurrentQuote());
		
		
		Float gainToday = shares * (quote.getCurrentQuote() - quote.getPrevClose());
		Float gainTodayPct = 100.0f*(quote.getCurrentQuote()- quote.getPrevClose())/quote.getPrevClose();
		
		Float gain = shares * (quote.getCurrentQuote() - entry.getPrice());
		Float gainPct = 100.0f*(quote.getCurrentQuote() - entry.getPrice())/quote.getPrevClose();
		
		bal.setGainToday(gainToday);
		bal.setGainTodayPct(gainTodayPct);
		bal.setGain(gain);
		bal.setGainPct(gainPct);		
		
		return bal;
	}	
	
	public boolean isNumeric(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
	

	
}
