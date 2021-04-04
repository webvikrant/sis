package in.co.itlabs.sis.business.services;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

@Service
public class DatabaseService {

	private ConnectionSource connectionSource;

	public DatabaseService(@Value("${mysql.url}") String url, @Value("${mysql.user}") String user,
			@Value("${mysql.password}") String password) throws SQLException {

		connectionSource = new JdbcPooledConnectionSource(url, user, password);
	}

	public ConnectionSource getConnectioSource() {
		return connectionSource;
	}

	@PreDestroy
	public void cleanUp() throws IOException {
		System.out.println("Application shutting down...closing database connection pool...");
		connectionSource.close();
		System.out.println("Database connection pool closed...application shut down complete.");
	}
}
