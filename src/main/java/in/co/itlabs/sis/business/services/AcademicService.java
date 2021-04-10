package in.co.itlabs.sis.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import in.co.itlabs.sis.business.entities.Qualification;
import in.co.itlabs.sis.business.entities.Board;
import in.co.itlabs.sis.business.entities.Exam;
import in.co.itlabs.sis.business.entities.Exam.Level;
import in.co.itlabs.sis.business.entities.Program;
import in.co.itlabs.sis.business.entities.School;
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
			con.close();
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
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return sessions;
	}

	// boards
	public List<Board> getAllBoards() {
		List<Board> boards = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from board order by name";

		try (Connection con = sql2o.open()) {
			boards = con.createQuery(sql).executeAndFetch(Board.class);
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return boards;
	}

	// exams
	public List<Exam> getAllExams() {
		List<Exam> exams = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from exam order by name";

		try (Connection con = sql2o.open()) {
			exams = con.createQuery(sql).executeAndFetch(Exam.class);
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return exams;
	}

	public List<Exam> getExams(Level level) {
		List<Exam> exams = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from exam where level = :level order by name";

		try (Connection con = sql2o.open()) {
			exams = con.createQuery(sql).addParameter("level", level).executeAndFetch(Exam.class);
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return exams;
	}

	// schools
	public List<School> getAllSchools() {
		List<School> schools = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from school order by name";

		try (Connection con = sql2o.open()) {
			schools = con.createQuery(sql).executeAndFetch(School.class);
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return schools;
	}

	// academic qualification
	// create
	public int createAcademicQualification(Qualification academicQualification) {
		int id = 0;
		Sql2o sql2o = databaseService.getSql2o();
		String sql = "insert into academic_qualification (studentId, level, examId, boardId, schoolId, year, rollNo, maximumMarks, obtainedMarks, percentMarks)"
				+ " values(:studentId, :level, :examId, :boardId, :schoolId, :year, :rollNo, :maximumMarks, :obtainedMarks, :percentMarks)";

		try (Connection con = sql2o.open()) {
			id = con.createQuery(sql).addParameter("studentId", academicQualification.getStudentId())
					.addParameter("level", academicQualification.getLevel())
					.addParameter("examId", academicQualification.getExamId())
					.addParameter("boardId", academicQualification.getBoardId())
					.addParameter("schoolId", academicQualification.getSchoolId())
					.addParameter("year", academicQualification.getYear())
					.addParameter("rollNo", academicQualification.getRollNo())
					.addParameter("maximumMarks", academicQualification.getMaximumMarks())
					.addParameter("obtainedMarks", academicQualification.getObtainedMarks())
					.addParameter("percentMarks", academicQualification.getPercentMarks()).executeUpdate()
					.getKey(Integer.class);

			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return id;
	}

	public List<Qualification> getAcademicQualifications(int studentId) {
		List<Qualification> qualifications = null;

		Sql2o sql2o = databaseService.getSql2o();
		String sql = "select * from academic_qualification where studentId = :studentId order by year";
		String examSql = "select * from exam where id = :id";
		String boardSql = "select * from board where id = :id";
		String schoolSql = "select * from school where id = :id";

		try (Connection con = sql2o.open()) {
			qualifications = con.createQuery(sql).addParameter("studentId", studentId)
					.executeAndFetch(Qualification.class);

			for (var qualification : qualifications) {
				Exam exam = con.createQuery(examSql).addParameter("id", qualification.getExamId())
						.executeAndFetchFirst(Exam.class);
				qualification.setExam(exam);

				Board board = con.createQuery(boardSql).addParameter("id", qualification.getBoardId())
						.executeAndFetchFirst(Board.class);
				qualification.setBoard(board);

				School school = con.createQuery(schoolSql).addParameter("id", qualification.getSchoolId())
						.executeAndFetchFirst(School.class);
				qualification.setSchool(school);

			}
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return qualifications;
	}

}