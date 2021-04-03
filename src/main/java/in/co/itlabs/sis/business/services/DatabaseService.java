package in.co.itlabs.sis.business.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dieselpoint.norm.Database;

@Service
public class DatabaseService {

	private Database db;

	public DatabaseService(@Value("${mysql.url}") String url, @Value("${mysql.user}") String user,
			@Value("${mysql.password}") String password) {

		db = new Database();
		db.setJdbcUrl(url);
		db.setUser(user);
		db.setPassword(password);
	}

	public Database getDatabase() {
		return db;
	}
}
