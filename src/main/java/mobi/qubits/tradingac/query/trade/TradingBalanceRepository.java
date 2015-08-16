package mobi.qubits.tradingac.query.trade;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 
 * @author yizhuan
 *
 */
public interface TradingBalanceRepository extends
		MongoRepository<TradingBalance, String> {

	public List<TradingBalance> findByTraderId(String traderId);
	
	public TradingBalance findByTraderIdAndSymbol(String traderId, String symbol);

}
