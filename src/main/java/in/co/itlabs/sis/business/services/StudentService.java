package in.co.itlabs.sis.business.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import in.co.itlabs.sis.business.entities.Address;
import in.co.itlabs.sis.business.entities.Contact;
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
	public int createStudent(List<String> messages, Student student) {
		int id = 0;
		Sql2o sql2o = databaseService.getSql2o();
		String sql = "insert into student (admissionId, name, sessionId)" + " values(:admissionId, :name, :sessionId)";

		try (Connection con = sql2o.open()) {
			id = con.createQuery(sql).addParameter("admissionId", student.getAdmissionId())
					.addParameter("name", student.getName()).addParameter("sessionId", student.getSessionId())
					.executeUpdate().getKey(Integer.class);

			con.close();
		} catch (Exception e) {
			messages.add(e.getMessage());
		}
		return id;
	}

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

	// update student photo media file id
	public boolean updateStudentPhotohMediaFileId(List<String> messages, int studentId, int photoMediaFileId) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set photoMediaFileId = :photoMediaFileId where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", studentId).addParameter("photoMediaFileId", photoMediaFileId)
					.executeUpdate();
			success = true;

			con.close();
		}

		return success;
	}

	// update student signature media file id
	public boolean updateStudentSignMediaFileId(List<String> messages, int studentId, int signMediaFileId) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set signMediaFileId = :signMediaFileId where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", studentId).addParameter("signMediaFileId", signMediaFileId)
					.executeUpdate();
			success = true;

			con.close();
		}

		return success;
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

	public boolean updateStudentProgramId(List<String> messages, int studentId, int programId) {
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

	// update contact details
	public boolean updateStudentContactDetails(List<String> messages, Contact contact) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set" + " mobileNo = :mobileNo," + " whatsappNo = :whatsappNo,"
				+ " emailId = :emailId where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", contact.getStudentId())
					.addParameter("mobileNo", contact.getMobileNo()).addParameter("whatsappNo", contact.getWhatsappNo())
					.addParameter("emailId", contact.getEmailId()).executeUpdate();
			success = true;

			con.close();
		} catch (Exception e) {
			messages.add(e.getMessage());
		}

		return success;
	}

	// update address details
	public boolean updateStudentPermanentAddressDetails(List<String> messages, Address address) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set" + " permanentAddress = :address," + " permanentPincode = :pinCode,"
				+ " permanentDistrictId = :districtId where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", address.getStudentId())
					.addParameter("address", address.getAddress()).addParameter("pinCode", address.getPinCode())
					.addParameter("districtId", address.getDistrictId()).executeUpdate();
			success = true;

			con.close();
		} catch (Exception e) {
			messages.add(e.getMessage());
		}

		return success;
	}

	public boolean updateStudentCorrespondenceAddressDetails(List<String> messages, Address address) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set" + " correspondenceAddress = :address," + " correspondencePincode = :pinCode,"
				+ " correspondenceDistrictId = :districtId where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", address.getStudentId())
					.addParameter("address", address.getAddress()).addParameter("pinCode", address.getPinCode())
					.addParameter("districtId", address.getDistrictId()).executeUpdate();
			success = true;

			con.close();
		} catch (Exception e) {
			messages.add(e.getMessage());
		}

		return success;
	}

	public boolean updateStudentLocalGuardianAddressDetails(List<String> messages, Address address) {
		boolean success = false;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "update student set" + " localGuardianAddress = :address," + " localGuardianPincode = :pinCode,"
				+ " localGuardianDistrictId = :districtId where id = :id";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("id", address.getStudentId())
					.addParameter("address", address.getAddress()).addParameter("pinCode", address.getPinCode())
					.addParameter("districtId", address.getDistrictId()).executeUpdate();
			success = true;

			con.close();
		}

		return success;
	}

}
