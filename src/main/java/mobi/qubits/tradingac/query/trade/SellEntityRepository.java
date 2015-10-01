package mobi.qubits.tradingac.query.trade;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 
 * @author yizhuan
 *
 */
public interface SellEntityRepository extends
		MongoRepository<SellEntity, String> {	

	public List<SellEntity> findByTraderIdAndSymbol(String traderId, String symbol);
	
}
