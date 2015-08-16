package mobi.qubits.tradingac.query.trade;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 
 * @author yizhuan
 *
 */
public interface TraderEntryRepository extends
		MongoRepository<TraderEntry, String> {

	public List<TraderEntry> findByName(String name);

}
