package in.co.itlabs.sis.business.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import in.co.itlabs.sis.business.entities.Session;
import in.co.itlabs.sis.business.entities.Student;

@Service
public class StudentService {

	private DatabaseService databaseService;

	@Autowired
	public StudentService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	// create
//	public int createStudent(Student student) {
//		int id = 0;
//		try {
//			ConnectionSource connectionSource = databaseService.getConnectioSource();
//			Dao<Student, Integer> studentDao = DaoManager.createDao(connectionSource, Student.class);
//			studentDao.create(student);
//			id = student.getId();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return id;
//	}

	// read one
	public Student getStudentById(int id) {
		Student student = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from student where id = :id";

		try (Connection con = sql2o.open()) {
			student = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Student.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return student;
	}

	// read many
	public List<Student> getAllStudents() {
		List<Student> students = null;

		Sql2o sql2o = databaseService.getSql2o();
		String studentSql = "select * from student";
		String sessionSql = "select * from session where id = :id";

		try (Connection con = sql2o.open()) {
			students = con.createQuery(studentSql).executeAndFetch(Student.class);

			for (Student student : students) {
				Session session = con.createQuery(sessionSql).addParameter("id", student.getSessionId())
						.executeAndFetchFirst(Session.class);
				student.setSession(session);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return students;
	}

	// update
	public boolean updateStudentBirthDate(List<String> messages, int studentId, LocalDate birthDate) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set birthDate = :birthDate where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", studentId).addParameter("birthDate", birthDate).executeUpdate();
			success = true;
		}

		return success;
	}
}
