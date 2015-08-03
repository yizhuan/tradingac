package mobi.qubits.tradingac.query.trade;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 
 * @author yizhuan
 *
 */
public interface TradingAccountRepository extends
		MongoRepository<TradingAccount, String> {

	public TradingAccount findBySymbol(String symbol);

}
