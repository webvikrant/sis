package in.co.itlabs.sis.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dieselpoint.norm.Database;

import in.co.itlabs.sis.business.entities.Student;

@Service
public class StudentService {

	private DatabaseService databaseService;

	@Autowired
	public StudentService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public List<Student> getAllStudents() {
		Database db = databaseService.getDatabase();
		List<Student> students = db.results(Student.class);
		return students;
	}

}
