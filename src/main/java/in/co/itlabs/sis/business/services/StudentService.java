package in.co.itlabs.sis.business.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import in.co.itlabs.sis.business.entities.Program;
import in.co.itlabs.sis.business.entities.Session;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.helpers.Gender;

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
		String sessionSql = "select * from session where id = :id";
		String programSql = "select * from program where id = :id";

		try (Connection con = sql2o.open()) {
			student = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Student.class);

			// fetch session
			Session session = con.createQuery(sessionSql).addParameter("id", student.getSessionId())
					.executeAndFetchFirst(Session.class);
			student.setSession(session);

			// fetch program
			Program program = con.createQuery(programSql).addParameter("id", student.getProgramId())
					.executeAndFetchFirst(Program.class);
			student.setProgram(program);

			con.close();
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
		String programSql = "select * from program where id = :id";

		try (Connection con = sql2o.open()) {
			students = con.createQuery(studentSql).executeAndFetch(Student.class);

			for (Student student : students) {
				// fetch session
				Session session = con.createQuery(sessionSql).addParameter("id", student.getSessionId())
						.executeAndFetchFirst(Session.class);
				student.setSession(session);

				// fetch program
				Program program = con.createQuery(programSql).addParameter("id", student.getProgramId())
						.executeAndFetchFirst(Program.class);
				student.setProgram(program);
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return students;
	}

	// update birthDate
	public boolean updateStudentBirthDate(List<String> messages, int studentId, LocalDate birthDate) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set birthDate = :birthDate where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", studentId).addParameter("birthDate", birthDate).executeUpdate();
			success = true;

			con.close();
		}

		return success;
	}

	public boolean updateStudentName(List<String> messages, int studentId, String name) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set name = :name where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", studentId).addParameter("name", name).executeUpdate();
			success = true;

			con.close();
		}

		return success;
	}

	public boolean updateStudentProgram(List<String> messages, int studentId, int programId) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set programId = :programId where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", studentId).addParameter("programId", programId).executeUpdate();
			success = true;

			con.close();
		}

		return success;
	}

	public boolean updateStudentGender(List<String> messages, int studentId, Gender gender) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set gender = :gender where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", studentId).addParameter("gender", gender).executeUpdate();
			success = true;

			con.close();
		}

		return success;
	}

	public boolean updateStudentPermanentDistrict(List<String> messages, int studentId, int districtId) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set  permanentDistrictId = :districtId where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", studentId).addParameter("districtId", districtId).executeUpdate();
			success = true;

			con.close();
		}

		return success;
	}

	public boolean updateStudentCorrespondenceDistrict(List<String> messages, int studentId, int districtId) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set  correspondenceDistrictId = :districtId where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", studentId).addParameter("districtId", districtId).executeUpdate();
			success = true;

			con.close();
		}

		return success;
	}

	public boolean updateStudentLocalGuardianDistrict(List<String> messages, int studentId, int districtId) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set  localGuardianDistrictId = :districtId where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", studentId).addParameter("districtId", districtId).executeUpdate();
			success = true;

			con.close();
		}

		return success;
	}

}
