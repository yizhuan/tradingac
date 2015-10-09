package mobi.qubits.tradingapp.query.trade;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuoteEntityRepository extends MongoRepository<QuoteEntity, String> {
	
}
