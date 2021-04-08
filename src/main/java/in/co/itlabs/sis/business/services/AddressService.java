package in.co.itlabs.sis.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import in.co.itlabs.sis.business.entities.District;
import in.co.itlabs.sis.business.entities.State;

@Service
public class AddressService {

	private DatabaseService databaseService;

	@Autowired
	public AddressService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	// states
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
	
	public List<District> getDistricts(int stateId) {
		List<District> districts = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from district where stateId = :stateId order by name";

		try (Connection con = sql2o.open()) {
			districts = con.createQuery(sql).addParameter("stateId", stateId).executeAndFetch(District.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return districts;
	}

	public District getDistrict(int id) {
		District district = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from district where id = :id";
		String stateSql = "select * from state where id = :id";
		
		try (Connection con = sql2o.open()) {
			district = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(District.class);
			State state = con.createQuery(stateSql).addParameter("id", district.getStateId()).executeAndFetchFirst(State.class);
			district.setState(state);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return district;
	}
}
