package in.co.itlabs.sis.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dieselpoint.norm.Database;

import in.co.itlabs.sis.business.entities.Session;

@Service
public class AcademicService {
	private DatabaseService databaseService;

	@Autowired
	public AcademicService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public List<Session> getAllSessions() {
		Database db = databaseService.getDatabase();
		List<Session> sessions = db.results(Session.class);
		return sessions;
	}
}
