package mobi.qubits.tradingac.api;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import mobi.qubits.tradingac.api.requests.TraderRequest;
import mobi.qubits.tradingac.api.requests.TradingRequest;
import mobi.qubits.tradingac.domain.commands.BuyCommand;
import mobi.qubits.tradingac.domain.commands.RegisterNewTraderCommand;
import mobi.qubits.tradingac.domain.commands.SellCommand;
import mobi.qubits.tradingac.query.trade.RealtimeBalance;
import mobi.qubits.tradingac.query.trade.TradeEntry;
import mobi.qubits.tradingac.query.trade.TradeEntryRepository;
import mobi.qubits.tradingac.query.trade.TraderEntry;
import mobi.qubits.tradingac.query.trade.TraderEntryRepository;
import mobi.qubits.tradingac.query.trade.TradingBalance;
import mobi.qubits.tradingac.query.trade.TradingBalanceRepository;
import mobi.qubits.tradingac.quote.Quote;
import mobi.qubits.tradingac.quote.QuoteService;
import mobi.qubits.tradingac.quote.google.GoogleQuoteService;
import mobi.qubits.tradingac.quote.sina.SinaQuoteService;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.DefaultIdentifierFactory;
import org.axonframework.domain.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 
 * @author yizhuan
 *
 */
@RestController
public class TradeController {

	private final IdentifierFactory identifierFactory = new DefaultIdentifierFactory();

	@Autowired
	private TraderEntryRepository traderEntryRepository;
	
	@Autowired
	private TradeEntryRepository tradeEntryRepository;


	@Autowired
	private TradingBalanceRepository tradingBalanceRepository;


	@Autowired
	private CommandGateway cmdGateway;
	
	@RequestMapping(value = "/api/traders/", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<?> createTrader(@RequestBody @Valid TraderRequest request, UriComponentsBuilder b) {
		String id = identifierFactory.generateIdentifier();
		cmdGateway.send(new RegisterNewTraderCommand(id, request.getName()));

		UriComponents uriComponents = b.path("/api/traders/{id}").buildAndExpand(
				id);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponents.toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}	
	
	@RequestMapping(value = "/api/traders/{id}/buy", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void buy(@RequestBody @Valid TradingRequest request, @PathVariable String id) {
		cmdGateway.send(new BuyCommand(id, request.getSymbol(), request
				.getShares(), request.getPrice()));
	}
	
	@RequestMapping(value = "/api/traders/{id}/sell", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void sell(@RequestBody @Valid TradingRequest request, @PathVariable String id) {
			cmdGateway.send(new SellCommand(id, request.getSymbol(), request
					.getShares(), request.getPrice()));
	}		
	
	@RequestMapping(value = "/api/traders/{id}", method = RequestMethod.GET)
	public TraderEntry findlTrade(@PathVariable String id) {
		return traderEntryRepository.findOne(id);
	}
	
	@RequestMapping(value = "/api/traders", method = RequestMethod.GET)
	public @ResponseBody List<TraderEntry> findlAllTraders() {
		return traderEntryRepository.findAll();
	}
	
	@RequestMapping(value = "/api/traders/{id}/trades", method = RequestMethod.GET)
	public @ResponseBody List<TradeEntry> find( @PathVariable String id) {
		return tradeEntryRepository.findByTraderId(id);
	}
	
			
	@RequestMapping(value = "/api/traders/{id}/trades/{symbol}", method = RequestMethod.GET)
	public @ResponseBody List<TradeEntry> find( @PathVariable String id, @PathVariable String symbol) {
		return tradeEntryRepository.findByTraderIdAndSymbol(id, symbol);
	}

	@RequestMapping(value = "/api/traders/{id}/trades/{symbol}/{type}", method = RequestMethod.GET)
	public @ResponseBody List<TradeEntry> findlAll(@PathVariable String id, @PathVariable String symbol,  @PathVariable Short type) {
		return tradeEntryRepository.findByTraderIdAndSymbolAndType(id, symbol, type);
	}

	
	@RequestMapping(value = "/api/traders/{id}/balance", method = RequestMethod.GET)
	public @ResponseBody List<TradingBalance> findBalance( @PathVariable String id) {
		return tradingBalanceRepository.findByTraderId(id);
	}	
	
	
	@RequestMapping(value = "/api/traders/{id}/balance/{symbol}", method = RequestMethod.GET)
	public @ResponseBody TradingBalance findBalance( @PathVariable String id, @PathVariable String symbol) {
		return tradingBalanceRepository.findByTraderIdAndSymbol(id, symbol);
	}
	
	
	//realtime balance on trading account
	@RequestMapping(value = "/api/traders/{id}/realtime-balance", method = RequestMethod.GET)
	public @ResponseBody List<RealtimeBalance> findRealtimeBalance( @PathVariable String id) {
		List<TradingBalance> bals = tradingBalanceRepository.findByTraderId(id);
		return getRealtimeBalance(bals);
	}	
	
	//realtime balance on trading account
	@RequestMapping(value = "/api/traders/{id}/realtime-balance/{symbol}", method = RequestMethod.GET)
	public @ResponseBody RealtimeBalance findRealtimeBalance( @PathVariable String id, @PathVariable String symbol) {
		TradingBalance bal = tradingBalanceRepository.findByTraderIdAndSymbol(id, symbol);
		return getRealtimeBalance(bal);
	}
	
	
	//realtime balance on historical trades (buying) showing historical gain/loss against current quote
	@RequestMapping(value = "/api/traders/{id}/trades/realtime-balance", method = RequestMethod.GET)
	public @ResponseBody List<RealtimeBalance> findRealtimeTradeBalance( @PathVariable String id) {
		List<TradeEntry> entries = tradeEntryRepository.findByTraderIdAndType(id, (short)1);
		return getRealtimeTradeBalance(entries);
	}
	
	//realtime balance on historical trades (buying) showing historical gain/loss against current quote
	@RequestMapping(value = "/api/traders/{id}/trades/realtime-balance/{symbol}", method = RequestMethod.GET)
	public @ResponseBody List<RealtimeBalance> findRealtimeTradeBalance( @PathVariable String id, @PathVariable String symbol) {
		List<TradeEntry> entries = tradeEntryRepository.findByTraderIdAndSymbolAndType(id, symbol, (short)1);
		return getRealtimeTradeBalance(entries);
	}	
	
	
	private Quote getQuote(String symbol){
		QuoteService service = isNumeric(symbol)? new SinaQuoteService() :  new GoogleQuoteService();
		Quote q = service.getQuote(symbol);	
		return q;
	}
	
	
	private List<RealtimeBalance> getRealtimeBalance(List<TradingBalance> tradingBalances){
		List<RealtimeBalance> bals = new ArrayList<RealtimeBalance>();
		for (TradingBalance bal : tradingBalances){
			RealtimeBalance rt = getRealtimeBalance(bal);
			if (rt!=null)
				bals.add(rt);
		}		
		return bals;
	}
	

	private RealtimeBalance getRealtimeBalance(TradingBalance tradingBalance){		
		Quote q = getQuote(tradingBalance.getSymbol());			
		return q==null?null:getRealtimeBalance(q, tradingBalance);
	}

	
	private RealtimeBalance getRealtimeBalance(Quote quote, TradingBalance tradingBalance){
		
		Long shares = tradingBalance.getShares();
		
		RealtimeBalance bal = new RealtimeBalance();
		bal.setId(tradingBalance.getId());
		bal.setTraderId(tradingBalance.getTraderId());
		bal.setSymbol(tradingBalance.getSymbol());
		bal.setShares(shares);
		bal.setCostPerShare(tradingBalance.getCostPerShare());
		
		bal.setLastClose(quote.getPrevClose());
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
	
	private List<RealtimeBalance> getRealtimeTradeBalance(List<TradeEntry> entries){
		List<RealtimeBalance> bals = new ArrayList<RealtimeBalance>();
		for (TradeEntry bal : entries){
			RealtimeBalance rt = getRealtimeTradeBalance(bal);
			if (rt!=null)
				bals.add(rt);
		}		
		return bals;
	}
	

	private RealtimeBalance getRealtimeTradeBalance(TradeEntry entry){
		String symbol = entry.getSymbol();
		Quote q = getQuote(symbol);
		return q==null?null:getRealtimeTradeBalance(q, entry);
	}
	
	private RealtimeBalance getRealtimeTradeBalance(Quote quote, TradeEntry entry){
		
		Long shares = entry.getShares();
		
		RealtimeBalance bal = new RealtimeBalance();
		bal.setId(entry.getId());
		bal.setTraderId(entry.getTraderId());
		bal.setSymbol(entry.getSymbol());
		bal.setShares(shares);
		bal.setCostPerShare(entry.getPrice());
		
		bal.setLastClose(quote.getPrevClose());
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
	
	private boolean isNumeric(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
	
}
