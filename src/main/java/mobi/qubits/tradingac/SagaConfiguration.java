package mobi.qubits.tradingac;

import mobi.qubits.tradingac.domain.trader.TradingSaga;

import org.axonframework.saga.GenericSagaFactory;
import org.axonframework.saga.ResourceInjector;
import org.axonframework.saga.SagaFactory;
import org.axonframework.saga.SagaManager;
import org.axonframework.saga.SagaRepository;
import org.axonframework.saga.annotation.AsyncAnnotatedSagaManager;
import org.axonframework.saga.repository.mongo.DefaultMongoTemplate;
import org.axonframework.saga.repository.mongo.MongoSagaRepository;
import org.axonframework.saga.repository.mongo.MongoTemplate;
import org.axonframework.saga.spring.SpringResourceInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 
 * @author yizhuan
 *
 */
@Configuration
@Import(AxonConfiguration.class)
public class SagaConfiguration extends AxonConfiguration{	
	
	@Bean 
	public ResourceInjector resourceInjector() {
		return new SpringResourceInjector();
	}
	
	@Bean
	public SagaFactory sagaFactory() {
		GenericSagaFactory factory = new GenericSagaFactory();
		factory.setResourceInjector(resourceInjector());
		return factory;
	}

	@Bean
	public SagaRepository sagaRepository() {
		MongoTemplate template = new DefaultMongoTemplate(mongo, "axonframework", "sagas", null, null);
		MongoSagaRepository rep = new MongoSagaRepository(template);
		rep.setResourceInjector(resourceInjector());
		return rep;
	}
	
	@Bean
	@SuppressWarnings({ "deprecation", "unchecked" })
	public SagaManager sagaManager() {
		AsyncAnnotatedSagaManager mgr =	new AsyncAnnotatedSagaManager(eventBus(), TradingSaga.class);
		/*
		 	AsyncAnnotatedSagaManager mgr =	new AsyncAnnotatedSagaManager(BookAdminSaga.class);
			axonConfig.eventBus().subscribe(mgr);
		 */
		mgr.setSagaFactory(sagaFactory());
		mgr.setSagaRepository(sagaRepository());
		mgr.setExecutor(taskExecutor());
		mgr.start();
		return mgr;
	}
}
