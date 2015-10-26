package mobi.qubits.tradingapp;

import static org.junit.Assert.assertTrue;

import java.util.List;

import mobi.qubits.tradingapp.api.requests.BuyRequest;
import mobi.qubits.tradingapp.api.requests.SellRequest;
import mobi.qubits.tradingapp.api.requests.TraderRequest;
import mobi.qubits.tradingapp.domain.stock.commands.CreateStockCommand;
import mobi.qubits.tradingapp.domain.stock.commands.UpdateQuoteCommand;
import mobi.qubits.tradingapp.domain.trader.commands.BuyCommand;
import mobi.qubits.tradingapp.domain.trader.commands.RegisterNewTraderCommand;
import mobi.qubits.tradingapp.domain.trader.commands.SellCommand;
import mobi.qubits.tradingapp.query.AssetEntity;
import mobi.qubits.tradingapp.query.AssetEntityRepository;
import mobi.qubits.tradingapp.query.QuoteEntity;
import mobi.qubits.tradingapp.query.QuoteEntityRepository;
import mobi.qubits.tradingapp.query.TradeEntityRepository;
import mobi.qubits.tradingapp.query.TraderEntity;
import mobi.qubits.tradingapp.query.TraderEntityRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.DefaultIdentifierFactory;
import org.axonframework.domain.IdentifierFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author yizhuan
 *
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TradesTest {

	protected final static Logger log = LoggerFactory.getLogger(AxonConfiguration.class);

	private final IdentifierFactory identifierFactory = new DefaultIdentifierFactory();

	@Autowired
	private TradeEntityRepository tradeRepo;

	@Autowired
	private TraderEntityRepository traderRepo;


	@Autowired
	private AssetEntityRepository assetRepo;

	@Autowired
	private QuoteEntityRepository quoteRepo;

	@Autowired
	private CommandGateway cmdGateway;

	private String symbol1 = "600036";

	private String id;// trader ID;

	@Before
	public void setUp(){


		this.id = identifierFactory.generateIdentifier();
		System.out.println("======"+id);
		TraderRequest traderReq = new TraderRequest("John", 260000.0f);
		cmdGateway.send(new RegisterNewTraderCommand(id, traderReq.getName(), traderReq.getInvestment()));


		QuoteEntity quoteEntity = quoteRepo.findBySymbol(symbol1);
		if (quoteEntity==null){
			cmdGateway.send(new CreateStockCommand(this.id, symbol1));
		}

		cmdGateway.send(new UpdateQuoteCommand(this.id, symbol1));


	}

	@Test
	public void testBuying() throws InterruptedException{

		BuyRequest req = new BuyRequest(symbol1, 10000L,18.9f);

		cmdGateway.send(new BuyCommand(id, req.getSymbol(), req
				.getShares(), req.getPrice()));
	}

	@Test
	public void testBuyAndSell() throws InterruptedException{

		Float price = 17.8f;

		TraderEntity trader = traderRepo.findOne(id);
		assertTrue(trader!=null);

		QuoteEntity quoteEntity = quoteRepo.findBySymbol(symbol1);
		assertTrue(quoteEntity!=null);

		BuyRequest breq = new BuyRequest(symbol1, 10000L, price);

		cmdGateway.send(new BuyCommand(id, breq.getSymbol(), breq
				.getShares(), breq.getPrice()));

		SellRequest sreq = new SellRequest(symbol1, 100L, price, 18.9f);

		cmdGateway.send(new SellCommand(id, sreq.getSymbol(), sreq
				.getShares(), sreq.getPrice(), price));

		List<AssetEntity> assets = assetRepo.findAll();
		for (AssetEntity a : assets){

			System.out.println("==============================================");
			System.out.println("id: "+a.getId());
			System.out.println("traderId: "+a.getTraderId());
			System.out.println("symbol: "+a.getSymbol());
			System.out.println("shares: "+a.getCostPerShare());
			System.out.println("shares: "+a.getShares());

		}

		System.out.println("==============================================");

		AssetEntity a0 = assets.get(0);
		assertTrue(a0.getSymbol().equals(symbol1));
		assertTrue(a0.getShares().compareTo(10000L)==0);

		AssetEntity a1 = assets.get(1);
		assertTrue(a1.getSymbol().equals(symbol1));
		assertTrue(a1.getShares().compareTo(10000L-100L)==0);

	}

}
