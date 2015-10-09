package mobi.qubits.tradingapp;

import static org.junit.Assert.assertTrue;
import mobi.qubits.tradingapp.Application;
import mobi.qubits.tradingapp.api.requests.TraderRequest;
import mobi.qubits.tradingapp.api.requests.SellRequest;
import mobi.qubits.tradingapp.domain.stock.commands.CreateStockCommand;
import mobi.qubits.tradingapp.domain.stock.commands.UpdateQuoteCommand;
import mobi.qubits.tradingapp.domain.trader.commands.BuyCommand;
import mobi.qubits.tradingapp.domain.trader.commands.RegisterNewTraderCommand;
import mobi.qubits.tradingapp.domain.trader.commands.SellCommand;
import mobi.qubits.tradingapp.query.trade.AssetEntity;
import mobi.qubits.tradingapp.query.trade.AssetEntityRepository;
import mobi.qubits.tradingapp.query.trade.QuoteEntity;
import mobi.qubits.tradingapp.query.trade.QuoteEntityRepository;
import mobi.qubits.tradingapp.query.trade.TradeEntityRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.DefaultIdentifierFactory;
import org.axonframework.domain.IdentifierFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author yizhuan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TradesTest {

	private final IdentifierFactory identifierFactory = new DefaultIdentifierFactory();
	
	@Autowired
	private TradeEntityRepository tradeEntryRepository;

	@Autowired
	private AssetEntityRepository assetEntityRepository;
	
	@Autowired
	private QuoteEntityRepository quoteEntityRepository;

	@Autowired
	private CommandGateway cmdGateway;
	
	private String symbol1 = "600036";
	private String symbol2 = "600406";
		
	private String id;// trader ID;
	
	
	
	
	@Before
	public void setUp(){
		
		
		this.id = identifierFactory.generateIdentifier();	
		System.out.println("======"+id);
		TraderRequest traderReq = new TraderRequest("John", 200000.0f);
		cmdGateway.send(new RegisterNewTraderCommand(id, traderReq.getName(), traderReq.getInvestment()));
				
		
		QuoteEntity quoteEntity = quoteEntityRepository.findOne(symbol1);
		if (quoteEntity==null){
			cmdGateway.send(new CreateStockCommand(symbol1));
		}
		
		cmdGateway.send(new UpdateQuoteCommand(symbol1));
		
		
	}
	
	@Test
	public void testBuying() throws InterruptedException{
		
		SellRequest req = new SellRequest(symbol1, 100L,17.0F, 18.9f);
		
		cmdGateway.send(new BuyCommand(id, req.getSymbol(), req
				.getShares(), req.getPrice()));
	}	
	
	@Test
	public void testBuyAndSell() throws InterruptedException{
	
		Float price = 17.8f;
		
	
		AssetEntity ac = assetEntityRepository.findByTraderIdAndSymbol(id, symbol1);
		Long shares = ac==null? 0L: ac.getShares();
		
		
		SellRequest breq = new SellRequest(symbol1, 100L, price, 12.09f);
		
		cmdGateway.send(new BuyCommand(id, breq.getSymbol(), breq
				.getShares(), breq.getPrice()));		
				
		SellRequest sreq = new SellRequest(symbol1, 100L, price, 15.9f);
		
		cmdGateway.send(new SellCommand(id, sreq.getSymbol(), sreq
				.getShares(), sreq.getPrice(), 17.0f));
		
		Thread.sleep(1000);
					
		AssetEntity ac1 = assetEntityRepository.findByTraderIdAndSymbol(id, symbol1);
		assertTrue(ac1.getShares().equals(shares) );
		
	}		
	
}
