package mobi.qubits.tradingac;

import static org.junit.Assert.assertTrue;
import mobi.qubits.tradingac.api.requests.TraderRequest;
import mobi.qubits.tradingac.api.requests.TradingRequest;
import mobi.qubits.tradingac.domain.commands.BuyCommand;
import mobi.qubits.tradingac.domain.commands.RegisterNewTraderCommand;
import mobi.qubits.tradingac.domain.commands.SellCommand;
import mobi.qubits.tradingac.query.trade.TradeEntry;
import mobi.qubits.tradingac.query.trade.TradeEntryRepository;
import mobi.qubits.tradingac.query.trade.TradingBalance;
import mobi.qubits.tradingac.query.trade.TradingBalanceRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.DefaultIdentifierFactory;
import org.axonframework.domain.IdentifierFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TradesTest {

	private final IdentifierFactory identifierFactory = new DefaultIdentifierFactory();
	
	@Autowired
	private TradeEntryRepository tradeEntryRepository;

	@Autowired
	private TradingBalanceRepository tradingAccountEntryRepository;

	@Autowired
	private CommandGateway cmdGateway;
	
	private String id;// trader ID;
	
	@Before
	public void setUp(){
		
		this.id = identifierFactory.generateIdentifier();	
		System.out.println("======"+id);
		TraderRequest traderReq = new TraderRequest("John");
		cmdGateway.send(new RegisterNewTraderCommand(id, traderReq.getName()));
	}
	
	@Test
	public void testBuying() throws InterruptedException{
		
		TradingRequest req = new TradingRequest("FB", 100L, 92.5F, (short)1);
		
		cmdGateway.send(new BuyCommand(id, req.getSymbol(), req
				.getShares(), req.getPrice()));
		
		/*
		Thread.sleep(1000);
		
		TradeEntry buying = tradeEntryRepository.findOne(id);
		assertTrue(buying.getSymbol().equals(req.getSymbol()));
		assertTrue(buying.getShares().equals(req.getShares()));
		assertTrue(buying.getPrice().equals(req.getPrice()));
		assertTrue(buying.getType().equals(req.getType()));
		*/
	}
	
	@Test
	public void testSelling() throws InterruptedException{
		
		TradingRequest req = new TradingRequest("FB", 100L, 92.5F, (short)0);
		
		cmdGateway.send(new SellCommand(id, req.getSymbol(), req
				.getShares(), req.getPrice()));
		
		/*
		Thread.sleep(1000);
		
		TradeEntry selling = tradeEntryRepository.findOne(id);
		assertTrue(selling.getSymbol().equals(req.getSymbol()));
		assertTrue(selling.getShares().equals(req.getShares()));
		assertTrue(selling.getPrice().equals(req.getPrice()));
		assertTrue(selling.getType().equals(req.getType()));
		*/
	}	
	
	
	@Test
	public void testBuyingAndSelling() throws InterruptedException{
		
		
		TradingBalance ac = tradingAccountEntryRepository.findByTraderIdAndSymbol(id, "FB");
		Long shares = ac==null? 0L: ac.getShares();
		
		
		TradingRequest breq = new TradingRequest("FB", 100L, 92.5F, (short)1);
		
		cmdGateway.send(new BuyCommand(id, breq.getSymbol(), breq
				.getShares(), breq.getPrice()));		
				
		TradingRequest sreq = new TradingRequest("FB", 100L, 92.5F, (short)0);
		
		cmdGateway.send(new SellCommand(id, sreq.getSymbol(), sreq
				.getShares(), sreq.getPrice()));
		
		Thread.sleep(1000);
					
		TradingBalance ac1 = tradingAccountEntryRepository.findByTraderIdAndSymbol(id, "FB");
		assertTrue(ac1.getShares().equals(shares) );
		
	}		
	
}
