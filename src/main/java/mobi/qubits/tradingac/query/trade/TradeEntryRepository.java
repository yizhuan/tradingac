package mobi.qubits.tradingac.query.trade;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 
 * @author yizhuan
 *
 */
public interface TradeEntryRepository extends
		MongoRepository<TradeEntry, String> {
	
	public List<TradeEntry> findByTraderId(String traderId);	
	
	public List<TradeEntry> findByTraderIdOrderBySymbolAsc(String traderId);
	
	public List<TradeEntry> findByTraderIdAndSymbolAndType(String traderId, String symbol, Short type);
	public List<TradeEntry> findByTraderIdAndSymbol(String traderId, String symbol);
		
	public List<TradeEntry> findByTraderIdAndType(String traderId, Short type);

}
