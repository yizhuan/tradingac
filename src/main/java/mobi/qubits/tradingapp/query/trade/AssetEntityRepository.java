package mobi.qubits.tradingapp.query.trade;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 
 * @author yizhuan
 *
 */
public interface AssetEntityRepository extends
		MongoRepository<AssetEntity, String> {

	public List<AssetEntity> findByTraderId(String traderId);
	
	public AssetEntity findByTraderIdAndSymbol(String traderId, String symbol);

}
