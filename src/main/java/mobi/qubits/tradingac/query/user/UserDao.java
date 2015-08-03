package mobi.qubits.tradingac.query.user;

import java.util.ArrayList;
import java.util.List;

import mobi.qubits.tradingac.domain.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 
 * @author yizhuan
 *
 */
@Component
public class UserDao {

	private final DB db;

	private MongoDbFactory mongoFactory;
	
	@Autowired
	public UserDao(MongoDbFactory mongoFactory) {
		this.mongoFactory = mongoFactory;
		this.db = this.mongoFactory.getDb();
	}
	
	public UserDao(DB db) {
		super();
		this.db = db;
	}


	/**
	 * Create a collection.
	 */
	public void create() {
		DBCollection coll = db.createCollection("user", null);
	}

	public void insert() {		
		//TODO
	}

	public void update() {
		// TODO
	}

	public List<User> findAll() {
		DBCollection col = db.getCollection("user");
		DBCursor c = col.find();

		List<User> users = new ArrayList<User>();

		while (c.hasNext()) {
			DBObject obj = c.next();
			User user = new User((String) obj.get("firstName"),
					(String) obj.get("lastName"));

			user.setId(obj.get("_id").toString());
			users.add(user);
		}

		return users;

	}

}
