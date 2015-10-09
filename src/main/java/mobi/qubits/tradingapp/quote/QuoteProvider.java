package mobi.qubits.tradingapp.quote;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.qubits.tradingapp.query.trade.AssetEntity;
import mobi.qubits.tradingapp.query.trade.RealtimeBalance;
import mobi.qubits.tradingapp.query.trade.TradeEntity;
import mobi.qubits.tradingapp.quote.google.GoogleQuoteService;
import mobi.qubits.tradingapp.quote.sina.SinaQuoteService;

public class QuoteProvider {

	protected Map<String, Ticker> quoteMap;
	
	protected void initQuoteMap(List<String> symbols){
		this.quoteMap = createQuoteMap(symbols);
	}
	
	protected void initQuoteMap(String symbol){
		Ticker q = getQuoteFromService(symbol);
		this.quoteMap = new HashMap<String, Ticker>();
		this.quoteMap.put(symbol, q);
	}
	
	protected Ticker getQuote(String symbol){
		return quoteMap.get(symbol);
	}	
	
	protected List<String> findSymbols1(List<AssetEntity> bals){
		List<String> symbols = new ArrayList<String>();
		for (AssetEntity b: bals){
			String s = b.getSymbol();
			if (!symbols.contains(s))
				symbols.add(s);
		}
		return symbols;
	}
	
	protected List<String> findSymbols2(List<TradeEntity> entries){
		List<String> symbols = new ArrayList<String>();
		for (TradeEntity b: entries){
			String s = b.getSymbol();
			if (!symbols.contains(s))
				symbols.add(s);
		}
		return symbols;
	}
	
	private Ticker getQuoteFromService(String symbol){
		QuoteService service = isNumeric(symbol)? new SinaQuoteService() :  new GoogleQuoteService();
		Ticker q = service.getQuote(symbol);	
		return q;
	}
	
	private Map<String, Ticker> createQuoteMap(List<String> symbols){		
		Map<String, Ticker> map = new HashMap<String, Ticker>();
		for (String symbol: symbols){
			Ticker q0 = map.get(symbol);
			if (q0!=null)
				continue;
			Ticker q = getQuoteFromService(symbol);
			map.put(symbol,  q);			
		}
		return map;
	}	
	
	protected List<RealtimeBalance> getRealtimeOverallBalance(List<AssetEntity> tradingBalances){
		List<RealtimeBalance> bals = new ArrayList<RealtimeBalance>();
		for (AssetEntity bal : tradingBalances){
			RealtimeBalance rt = getRealtimeBalance(bal);
			if (rt!=null && rt.getShares()>1L)
				bals.add(rt);
		}		
		return bals;
	}
	

	protected RealtimeBalance getRealtimeBalance(AssetEntity tradingBalance){		
		Ticker q = getQuote(tradingBalance.getSymbol());			
		return q==null?null:getRealtimeBalance(q, tradingBalance);
	}

	
	protected RealtimeBalance getRealtimeBalance(Ticker quote, AssetEntity tradingBalance){
		
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
	
	protected List<RealtimeBalance> getRealtimeTradeBalance(List<TradeEntity> entries){
		List<RealtimeBalance> bals = new ArrayList<RealtimeBalance>();
		for (TradeEntity bal : entries){
			RealtimeBalance rt = getRealtimeTradeBalance(bal);
			if (rt!=null)
				bals.add(rt);
		}		
		return bals;
	}
	

	protected RealtimeBalance getRealtimeTradeBalance(TradeEntity entry){
		String symbol = entry.getSymbol();
		Ticker q = getQuote(symbol);
		return q==null?null:getRealtimeTradeBalance(q, entry);
	}
	
	protected RealtimeBalance getRealtimeTradeBalance(Ticker quote, TradeEntity entry){
		
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
