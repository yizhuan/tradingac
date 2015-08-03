package mobi.qubits.tradingac;

import java.util.List;

import mobi.qubits.tradingac.domain.user.User;
import mobi.qubits.tradingac.query.user.UserDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * 
 * @author yizhuan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserDaoTest {

	@Autowired
	ApplicationContext context;

	@Test
	public void testFindAll() throws Exception {
		Mongo mongo = context.getBean(Mongo.class);
		//List<String> dbs = mongo.getDatabaseNames();
		UserDao dao = new UserDao(mongo.getDB("test"));
		List<User> users = dao.findAll();

		for (User user : users) {
			System.out.println("-----");
			System.out.println(user.getFirstName());
			System.out.println(user.getLastName());
		}
		
		//TODO
		//verify		

	}

	@Configuration
	public static class SpringMongoConfig extends AbstractMongoConfiguration {

		@Override
		public String getDatabaseName() {
			return "test";
		}

		@Override
		@Bean
		public Mongo mongo() throws Exception {
			Mongo mongo = new MongoClient("localhost:27017");
			return mongo;
		}

		@Bean
		public MongoTemplate mongoTemplate() throws Exception {
			return new MongoTemplate(mongo(),  getDatabaseName());
		}
	}

}
