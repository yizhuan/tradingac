package mobi.qubits.tradingac.quote;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.qubits.tradingac.query.trade.RealtimeBalance;
import mobi.qubits.tradingac.query.trade.TradeEntry;
import mobi.qubits.tradingac.query.trade.TradingBalance;
import mobi.qubits.tradingac.quote.google.GoogleQuoteService;
import mobi.qubits.tradingac.quote.sina.SinaQuoteService;

public class QuoteProvider {

	protected Map<String, Quote> quoteMap;
	
	protected void initQuoteMap(List<String> symbols){
		this.quoteMap = createQuoteMap(symbols);
	}
	
	protected void initQuoteMap(String symbol){
		Quote q = getQuoteFromService(symbol);
		this.quoteMap = new HashMap<String, Quote>();
		this.quoteMap.put(symbol, q);
	}
	
	protected Quote getQuote(String symbol){
		return quoteMap.get(symbol);
	}	
	
	protected List<String> findSymbols(List<TradingBalance> bals){
		List<String> symbols = new ArrayList<String>();
		for (TradingBalance b: bals){
			String s = b.getSymbol();
			if (!symbols.contains(s))
				symbols.add(s);
		}
		return symbols;
	}
	
	protected List<String> findSymbols2(List<TradeEntry> entries){
		List<String> symbols = new ArrayList<String>();
		for (TradeEntry b: entries){
			String s = b.getSymbol();
			if (!symbols.contains(s))
				symbols.add(s);
		}
		return symbols;
	}
	
	private Quote getQuoteFromService(String symbol){
		QuoteService service = isNumeric(symbol)? new SinaQuoteService() :  new GoogleQuoteService();
		Quote q = service.getQuote(symbol);	
		return q;
	}
	
	private Map<String, Quote> createQuoteMap(List<String> symbols){		
		Map<String, Quote> map = new HashMap<String, Quote>();
		for (String symbol: symbols){
			Quote q = getQuoteFromService(symbol);
			map.put(symbol,  q);			
		}
		return map;
	}	
	
	protected List<RealtimeBalance> getRealtimeBalance(List<TradingBalance> tradingBalances){
		List<RealtimeBalance> bals = new ArrayList<RealtimeBalance>();
		for (TradingBalance bal : tradingBalances){
			RealtimeBalance rt = getRealtimeBalance(bal);
			if (rt!=null)
				bals.add(rt);
		}		
		return bals;
	}
	

	protected RealtimeBalance getRealtimeBalance(TradingBalance tradingBalance){		
		Quote q = getQuote(tradingBalance.getSymbol());			
		return q==null?null:getRealtimeBalance(q, tradingBalance);
	}

	
	protected RealtimeBalance getRealtimeBalance(Quote quote, TradingBalance tradingBalance){
		
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
		Float gainPct = 100.0f*(quote.getCurrentQuote() - tradingBalance.getCostPerShare())/tradingBalance.getCostPerShare();
		
		bal.setGainToday(gainToday);
		bal.setGainTodayPct(gainTodayPct);
		bal.setGain(gain);
		bal.setGainPct(gainPct);		
		
		return bal;
	}
	
		
	
	
	//balances on trade history (buying)	
	
	protected List<RealtimeBalance> getRealtimeTradeBalance(List<TradeEntry> entries){
		List<RealtimeBalance> bals = new ArrayList<RealtimeBalance>();
		for (TradeEntry bal : entries){
			RealtimeBalance rt = getRealtimeTradeBalance(bal);
			if (rt!=null)
				bals.add(rt);
		}		
		return bals;
	}
	

	protected RealtimeBalance getRealtimeTradeBalance(TradeEntry entry){
		String symbol = entry.getSymbol();
		Quote q = getQuote(symbol);
		return q==null?null:getRealtimeTradeBalance(q, entry);
	}
	
	protected RealtimeBalance getRealtimeTradeBalance(Quote quote, TradeEntry entry){
		
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
		Float gainPct = 100.0f*(quote.getCurrentQuote() - entry.getPrice())/entry.getPrice();
		
		bal.setGainToday(gainToday);
		bal.setGainTodayPct(gainTodayPct);
		bal.setGain(gain);
		bal.setGainPct(gainPct);		
		
		return bal;
	}	
	
	protected boolean isNumeric(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
	

	
}
