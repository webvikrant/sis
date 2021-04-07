package in.co.itlabs.sis.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import in.co.itlabs.sis.business.entities.Program;
import in.co.itlabs.sis.business.entities.Session;

@Service
public class AcademicService {

	private DatabaseService databaseService;

	@Autowired
	public AcademicService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	// programs
	public List<Program> getAllPrograms() {
		List<Program> programs = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from program order by name";

		try (Connection con = sql2o.open()) {
			programs = con.createQuery(sql).executeAndFetch(Program.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return programs;
	}

	public List<Session> getAllSessions() {
		List<Session> sessions = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from session order by year desc";

		try (Connection con = sql2o.open()) {
			sessions = con.createQuery(sql).executeAndFetch(Session.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return sessions;
	}
	
	
}
