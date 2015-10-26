package mobi.qubits.tradingapp.query;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 
 * @author yizhuan
 *
 */
public interface TradeEntityRepository extends
		MongoRepository<TradeEntity, String> {
	
	public List<TradeEntity> findByTraderId(String traderId);	
	
	public List<TradeEntity> findByTraderIdOrderBySymbolAsc(String traderId);
	
	public List<TradeEntity> findByTraderIdAndSymbolAndType(String traderId, String symbol, Short type);
	public List<TradeEntity> findByTraderIdAndSymbol(String traderId, String symbol);
		
	public List<TradeEntity> findByTraderIdAndType(String traderId, Short type);

}
