package mobi.qubits.tradingac;

import java.util.Arrays;

import mobi.qubits.tradingac.domain.trade.Trade;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.commandhandling.interceptors.BeanValidationInterceptor;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.Mongo;

/**
 * 
 * 
 * @author yizhuan
 */
@Configuration
public class AxonConfiguration {
	
	
	@Autowired private ApplicationContext applicationContext;
	@Autowired Mongo mongo;

	@Bean
	public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
		AnnotationEventListenerBeanPostProcessor processor = new AnnotationEventListenerBeanPostProcessor();
		processor.setEventBus(eventBus());
		return processor;
	}

	@Bean
	public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
		AnnotationCommandHandlerBeanPostProcessor processor = new AnnotationCommandHandlerBeanPostProcessor();
		processor.setCommandBus(commandBus());
		return processor;
	}

	@Bean
	public CommandBus commandBus() {
		SimpleCommandBus commandBus = new SimpleCommandBus();
		commandBus.setHandlerInterceptors(Arrays
				.asList(new BeanValidationInterceptor()));
		return commandBus;
	}

	@Bean
	public EventBus eventBus() {
		return new SimpleEventBus();
	}

	@Bean
	public CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean() {
		CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<CommandGateway>();
		factory.setCommandBus(commandBus());
		return factory;
	}
	
	@Bean
	public EventSourcingRepository<Trade> tradeRepository() {

		DefaultMongoTemplate template = new DefaultMongoTemplate(this.mongo);
		MongoEventStore eventStore = new MongoEventStore(template
				);
		EventSourcingRepository<Trade> repository = new EventSourcingRepository<Trade>(
				Trade.class, eventStore);
		repository.setEventBus(eventBus());
		return repository;
	}	

	@Bean
	public AggregateAnnotationCommandHandler<Trade> tradeCommandHandler() {
		AggregateAnnotationCommandHandler<Trade> commandHandler = AggregateAnnotationCommandHandler
				.subscribe(Trade.class, tradeRepository(), commandBus());
		return commandHandler;
	}
}
