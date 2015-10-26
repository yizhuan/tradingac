package mobi.qubits.tradingapp.config;

import mobi.qubits.tradingapp.AxonConfiguration;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class AxonTestConfig extends AxonConfiguration {

	@Bean
	public EventBus eventBus() {
		return new SimpleEventBus();
	}
}
