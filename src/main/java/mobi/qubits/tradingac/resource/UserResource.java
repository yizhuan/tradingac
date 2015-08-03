package mobi.qubits.tradingac.resource;

import java.util.List;

import mobi.qubits.tradingac.domain.user.User;
import mobi.qubits.tradingac.query.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author yizhuan
 *
 */
@RestController
public class UserResource {

	@Autowired
	private UserRepository repository;

	@RequestMapping(value = "/users")
	public List<User> findAllUsers() {
		return this.repository.findAll();
	}
	
	@RequestMapping(value = "/users/{id}")
	public User findUser(@PathVariable String id) {
		return this.repository.findOne(id);
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<User> save(@RequestBody User user) {
		User u1 = this.repository.insert(user);
		return new ResponseEntity<User>(u1, HttpStatus.OK);
	}

}
