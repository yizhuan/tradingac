package mobi.qubits.tradingac;

import static org.junit.Assert.assertTrue;
import mobi.qubits.tradingac.api.requests.TradingRequest;
import mobi.qubits.tradingac.domain.commands.BuyCommand;
import mobi.qubits.tradingac.domain.commands.SellCommand;
import mobi.qubits.tradingac.query.trade.TradeEntry;
import mobi.qubits.tradingac.query.trade.TradeEntryRepository;
import mobi.qubits.tradingac.query.trade.TradingAccount;
import mobi.qubits.tradingac.query.trade.TradingAccountRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.DefaultIdentifierFactory;
import org.axonframework.domain.IdentifierFactory;
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
	private TradingAccountRepository tradingAccountEntryRepository;

	@Autowired
	private CommandGateway cmdGateway;
	
	@Test
	public void testBuying() throws InterruptedException{
		
		
		String id = identifierFactory.generateIdentifier();
		
		TradingRequest req = new TradingRequest("FB", 100L, 92.5F, (short)1);
		
		cmdGateway.send(new BuyCommand(id, req.getSymbol(), req
				.getShares(), req.getPrice()));
		
		Thread.sleep(1000);
		
		TradeEntry buying = tradeEntryRepository.findOne(id);
		assertTrue(buying.getSymbol().equals(req.getSymbol()));
		assertTrue(buying.getShares().equals(req.getShares()));
		assertTrue(buying.getPrice().equals(req.getPrice()));
		assertTrue(buying.getType().equals(req.getType()));
	}
	
	@Test
	public void testSelling() throws InterruptedException{
		
		
		String id = identifierFactory.generateIdentifier();
		
		TradingRequest req = new TradingRequest("FB", 100L, 92.5F, (short)0);
		
		cmdGateway.send(new SellCommand(id, req.getSymbol(), req
				.getShares(), req.getPrice()));
		
		Thread.sleep(1000);
		
		TradeEntry selling = tradeEntryRepository.findOne(id);
		assertTrue(selling.getSymbol().equals(req.getSymbol()));
		assertTrue(selling.getShares().equals(req.getShares()));
		assertTrue(selling.getPrice().equals(req.getPrice()));
		assertTrue(selling.getType().equals(req.getType()));
	}	
	
	
	@Test
	public void testBuyingAndSelling() throws InterruptedException{
		
		
		TradingAccount ac = tradingAccountEntryRepository.findBySymbol("FB");
		Long shares = ac==null? 0L: ac.getShares();
		
		
		String buyingid = identifierFactory.generateIdentifier();
		
		TradingRequest breq = new TradingRequest("FB", 100L, 92.5F, (short)1);
		
		cmdGateway.send(new BuyCommand(buyingid, breq.getSymbol(), breq
				.getShares(), breq.getPrice()));		
		
		String sellingid = identifierFactory.generateIdentifier();
		
		TradingRequest sreq = new TradingRequest("FB", 100L, 92.5F, (short)0);
		
		cmdGateway.send(new SellCommand(sellingid, sreq.getSymbol(), sreq
				.getShares(), sreq.getPrice()));
		
		Thread.sleep(1000);
		
		TradeEntry buying = tradeEntryRepository.findOne(buyingid);
		assertTrue(buying.getSymbol().equals(breq.getSymbol()));
		assertTrue(buying.getShares().equals(breq.getShares()));
		assertTrue(buying.getPrice().equals(breq.getPrice()));
		assertTrue(buying.getType().equals(breq.getType()));
		
		
		TradeEntry selling = tradeEntryRepository.findOne(sellingid);
		assertTrue(selling.getSymbol().equals(sreq.getSymbol()));
		assertTrue(selling.getShares().equals(sreq.getShares()));
		assertTrue(selling.getPrice().equals(sreq.getPrice()));
		assertTrue(selling.getType().equals(sreq.getType()));
		
		
		TradingAccount ac1 = tradingAccountEntryRepository.findBySymbol("FB");
		assertTrue(ac1.getShares().equals(shares) );
		
	}		
	
}
