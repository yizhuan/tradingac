package mobi.qubits.tradingac.web;

import java.util.ArrayList;
import java.util.List;

import mobi.qubits.tradingac.query.trade.RealtimeBalance;
import mobi.qubits.tradingac.query.trade.TradeEntry;
import mobi.qubits.tradingac.query.trade.TradeEntryRepository;
import mobi.qubits.tradingac.query.trade.TraderEntryRepository;
import mobi.qubits.tradingac.query.trade.TradingBalance;
import mobi.qubits.tradingac.query.trade.TradingBalanceRepository;
import mobi.qubits.tradingac.quote.QuoteProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author yizhuan
 *
 */
@Controller
public class HomeController extends QuoteProvider implements ErrorController{
	
	@Autowired
	private TraderEntryRepository traderEntryRepository;
	
	@Autowired
	private TradeEntryRepository tradeEntryRepository;


	@Autowired
	private TradingBalanceRepository tradingBalanceRepository;

	 //@RequestMapping("/index.html")
	@RequestMapping("/")
	 public String home(@RequestParam(value="id", required=false, defaultValue="752fe7ef-b94a-45ab-880a-19097824a4a4") String id, Model model) {
	 //public String home(@RequestParam(value="id", required=false, defaultValue="8bb53941-d9d3-40a6-9d42-cbf2755bf7db") String id, Model model) {

		List<TradingBalance> bals = tradingBalanceRepository.findByTraderId(id);
		List<TradeEntry> buys = tradeEntryRepository.findByTraderIdAndType(id, (short)1);
		List<TradeEntry> sells = tradeEntryRepository.findByTraderIdAndType(id, (short)0);
		
		List<String> symbols = findSymbols1(bals);
		List<String> symbols2 = findSymbols2(buys);
		List<String> symbols3 = findSymbols2(sells);
		symbols.addAll(symbols2);
		symbols.addAll(symbols3);
		
		initQuoteMap(symbols);		
		
		List<RealtimeBalance> balances = getRealtimeOverallBalance(bals);		
		model.addAttribute("balances", balances);
		 		 
								
		List<TradeEntry> results1 = new ArrayList<TradeEntry>();	

		for (TradingBalance b: bals){
			for (TradeEntry e: buys){			
				if (e.getSymbol().equals(b.getSymbol())){
					results1.add(e);
				}
			}
		}
				
		List<RealtimeBalance> tradesBuy = getRealtimeTradeBalance(results1);
		model.addAttribute("tradesBuy", tradesBuy);	
		
		model.addAttribute("tradesSell", sells);		
		
		return "index";
	 }	
	
	@RequestMapping("/error")
	public String error() {
		return "404";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
}


