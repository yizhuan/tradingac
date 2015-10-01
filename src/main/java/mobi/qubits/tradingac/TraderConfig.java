package mobi.qubits.tradingac;

import mobi.qubits.tradingac.domain.trader.Trader;

import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TraderConfig extends AxonConfiguration{

	
	@Bean
	public EventSourcingRepository<Trader> traderRepository() {

		DefaultMongoTemplate template = new DefaultMongoTemplate(this.mongo);
		MongoEventStore eventStore = new MongoEventStore(template
				);
		EventSourcingRepository<Trader> repository = new EventSourcingRepository<Trader>(
				Trader.class, eventStore);
		repository.setEventBus(eventBus());
		return repository;
	}	

	@Bean
	public AggregateAnnotationCommandHandler<Trader> TraderCommandHandler() {
		AggregateAnnotationCommandHandler<Trader> commandHandler = AggregateAnnotationCommandHandler
				.subscribe(Trader.class, traderRepository(), commandBus());
		return commandHandler;
	}
		
	
}
