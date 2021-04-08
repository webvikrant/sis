package in.co.itlabs.sis.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import in.co.itlabs.sis.business.entities.State;

@Service
public class AddressService {

	private DatabaseService databaseService;

	@Autowired
	public AddressService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	// programs
	public List<State> getAllStates() {
		List<State> states = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from state order by name";

		try (Connection con = sql2o.open()) {
			states = con.createQuery(sql).executeAndFetch(State.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return states;
	}

}
