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

	public List<TradeEntry> findByType(Short type);

	public List<TradeEntry> findBySymbol(String symbol);

}
