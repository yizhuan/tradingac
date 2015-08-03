package mobi.qubits.tradingac.query.user;

import java.util.List;

import mobi.qubits.tradingac.domain.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yizhuan
 *
 */
@Component
public class UserTemplateDao {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public UserTemplateDao(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<User> findAll() {
		return mongoTemplate.findAll(User.class);
	}
	
	public User findByID(String id) {
		return mongoTemplate.findById(id, User.class);
	}
	
	public void insert(User user){
		mongoTemplate.insert(user);		
	}
}
