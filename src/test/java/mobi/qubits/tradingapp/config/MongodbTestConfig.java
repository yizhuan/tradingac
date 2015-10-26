package mobi.qubits.tradingapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;

@Profile("test")
@Configuration
public class MongodbTestConfig  {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Bean
	public MongoClient mongoClient() throws Exception {
		log.info("Use Fongo for test");
		Fongo fongo = new Fongo("fake-mongodb");
		return fongo.getMongo();
	}
}
