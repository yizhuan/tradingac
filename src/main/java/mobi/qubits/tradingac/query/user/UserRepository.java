package mobi.qubits.tradingac.query.user;

import java.util.List;


import mobi.qubits.tradingac.domain.user.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
/**
 * 
 * @author yizhuan
 *
 */
public interface UserRepository extends MongoRepository<User, String>{

	public User findByFirstName(String firstName);

	public List<User> findByLastName(String lastName);
	
    //TODO
	@Query("{ 'firstname' : ?0 }")
	public List<User> findByName();

}
