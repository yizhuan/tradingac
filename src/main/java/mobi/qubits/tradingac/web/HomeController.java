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
public class HomeController extends QuoteProvider{
	
	@Autowired
	private TraderEntryRepository traderEntryRepository;
	
	@Autowired
	private TradeEntryRepository tradeEntryRepository;


	@Autowired
	private TradingBalanceRepository tradingBalanceRepository;

	 @RequestMapping("/index.html")
	 //public String home(@RequestParam(value="id", required=false, defaultValue="752fe7ef-b94a-45ab-880a-19097824a4a4") String id, Model model) {
	 public String home(@RequestParam(value="id", required=false, defaultValue="8bb53941-d9d3-40a6-9d42-cbf2755bf7db") String id, Model model) {

		List<TradingBalance> bals = tradingBalanceRepository.findByTraderId(id);
		
		initQuoteMap(findSymbols(bals));
		
		List<RealtimeBalance> balances = getRealtimeBalance(bals);
		
		model.addAttribute("balances", balances);
		 		 
		List<TradeEntry> entries1 = tradeEntryRepository.findByTraderIdAndType(id, (short)1);
		
		List<TradeEntry> results1 = new ArrayList<TradeEntry>();	

		//we will only list those with share balances.
		
		for (TradingBalance b: bals){
			if(b.getShares()<1L){
				continue;
			}
			for (TradeEntry e: entries1){			
				if (e.getSymbol().equals(b.getSymbol())){
					results1.add(e);
				}
			}
		}
		
		List<RealtimeBalance> tradesBuy = getRealtimeTradeBalance(results1);

		model.addAttribute("tradesBuy", tradesBuy);
				
		
		List<TradeEntry> entries2 = tradeEntryRepository.findByTraderIdAndType(id, (short)0);
		
		List<TradeEntry> results2 = new ArrayList<TradeEntry>();	

		//we will only list those with share balances.
		
		for (TradingBalance b: bals){
			if(b.getShares()<1L){
				continue;
			}
			for (TradeEntry e: entries2){			
				if (e.getSymbol().equals(b.getSymbol())){
					results2.add(e);
				}
			}
		}
		
		List<RealtimeBalance> trades = getRealtimeTradeBalance(results2);

		model.addAttribute("tradesSell", trades);
		
		
		return "index";
	 }	
	

}
