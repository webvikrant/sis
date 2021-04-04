package in.co.itlabs.sis.business.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import in.co.itlabs.sis.business.entities.Session;

@Service
public class AcademicService {

	private DatabaseService databaseService;

	@Autowired
	public AcademicService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public List<Session> getAllSessions() {
		List<Session> sessions = null;

		try {
			ConnectionSource connectionSource = databaseService.getConnectioSource();
			Dao<Session, Integer> sessionDao = DaoManager.createDao(connectionSource, Session.class);
			sessions = sessionDao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sessions;
	}
}
