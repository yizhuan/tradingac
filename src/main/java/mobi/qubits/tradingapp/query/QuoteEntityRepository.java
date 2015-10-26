package mobi.qubits.tradingapp.query;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuoteEntityRepository extends MongoRepository<QuoteEntity, String> {
	public QuoteEntity findBySymbol(String symbol);
}
