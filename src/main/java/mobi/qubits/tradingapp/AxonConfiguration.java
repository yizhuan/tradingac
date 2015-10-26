package mobi.qubits.tradingapp;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executor;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.commandhandling.interceptors.BeanValidationInterceptor;
import org.axonframework.eventhandling.ClassNamePrefixClusterSelector;
import org.axonframework.eventhandling.Cluster;
import org.axonframework.eventhandling.ClusterSelector;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventhandling.async.AsynchronousCluster;
import org.axonframework.eventhandling.async.SequentialPerAggregatePolicy;
import org.axonframework.eventhandling.replay.BackloggingIncomingMessageHandler;
import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.SpringAggregateSnapshotter;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.SnapshotEventStore;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.axonframework.serializer.bson.DBObjectXStreamSerializer;
import org.axonframework.unitofwork.NoTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.mongodb.Mongo;

/**
 *
 *
 * @author yizhuan
 */
@Profile("!test")
@Configuration
public class AxonConfiguration {

	protected final static Logger log = LoggerFactory.getLogger(AxonConfiguration.class);

	@Autowired private ApplicationContext applicationContext;
	@Autowired Mongo mongo;

	@Value("${spring.data.mongodb.eventstore:${app.name}-eventstore}")
	protected String eventStoreDbName;

	@Value("${eventbus.amqp.exchangeName:tradingapp.eventbus}")
	String exchangeName;

	@Value("${eventbus.amqp.queueName:${app.name}-${app.index:${server.port}}}")
	String queueName;

	@Value("${eventbus.amqp.bindingKeys:${app.domainPackage:*}.#}")
	String[] bindingKeys;

	@Value("${app.queryPackage}")
	String queryPackage;


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
	public SnapshotEventStore snapshotEventStore() {
		return mongoEventStore();
	}

	@Bean
	public EventStore eventStore() {
		return mongoEventStore();
	}

	@Bean
	public MongoEventStore mongoEventStore() {
		DefaultMongoTemplate template = new DefaultMongoTemplate(mongo, eventStoreDbName, "domainevents", "snapshotevents", null,
				null);
		MongoEventStore store = new MongoEventStore(new DBObjectXStreamSerializer(), template);
		return store;
	}


	@Bean
	ClusterSelector clusterSelector() {
		// use replaying cluster for query side
		// otherwise use asynchronous cluster
		log.info("use ReplayingCluster for package '{}'", queryPackage);
		return new ClassNamePrefixClusterSelector(//
				Collections.singletonMap(queryPackage, replayingCluster()),//
				asynchronousCluster());
	}

	private Cluster createAsynchronousCluster() {
		return new AsynchronousCluster(queueName,//
				taskExecutor(),//
				new SequentialPerAggregatePolicy());
	}

	@Bean
	Cluster asynchronousCluster() {
		return createAsynchronousCluster();
	}

	@Bean
	ReplayingCluster replayingCluster() {
		int commitThreshold = 100;
		return new ReplayingCluster(createAsynchronousCluster(),//
				mongoEventStore(),//
				new NoTransactionManager(),// no transaction in mongodb
				commitThreshold,//
				new BackloggingIncomingMessageHandler());
	}



	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(5);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}

	@Bean
	public Snapshotter snapshotter() {
		SpringAggregateSnapshotter snapshotter = new SpringAggregateSnapshotter();
		snapshotter.setExecutor(taskExecutor());
		snapshotter.setEventStore(snapshotEventStore());
		return snapshotter;
	}

}
