package mobi.qubits.tradingac;

import mobi.qubits.tradingac.domain.quote.Stock;

import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockConfig extends AxonConfiguration{

	@Bean
	public EventSourcingRepository<Stock> StockRepository() {

		DefaultMongoTemplate template = new DefaultMongoTemplate(this.mongo);
		MongoEventStore eventStore = new MongoEventStore(template
				);
		EventSourcingRepository<Stock> repository = new EventSourcingRepository<Stock>(
				Stock.class, eventStore);
		repository.setEventBus(eventBus());
		return repository;
	}	

	@Bean
	public AggregateAnnotationCommandHandler<Stock> StockCommandHandler() {
		AggregateAnnotationCommandHandler<Stock> commandHandler = AggregateAnnotationCommandHandler
				.subscribe(Stock.class, StockRepository(), commandBus());
		return commandHandler;
	}
		
	
}
