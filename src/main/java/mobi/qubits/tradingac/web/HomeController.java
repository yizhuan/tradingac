package mobi.qubits.tradingac.web;

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
	 public String home(@RequestParam(value="id", required=false, defaultValue="752fe7ef-b94a-45ab-880a-19097824a4a4") String id, Model model) {

		List<TradingBalance> bals = tradingBalanceRepository.findByTraderId(id);
		List<RealtimeBalance> balances = getRealtimeBalance(bals);
		
		model.addAttribute("balances", balances);
		 
		 
		List<TradeEntry> entries = tradeEntryRepository.findByTraderIdAndType(
				id, (short) 1);
		List<RealtimeBalance> trades = getRealtimeTradeBalance(entries);

		model.addAttribute("trades", trades);

		return "index";
	 }	
	

}
