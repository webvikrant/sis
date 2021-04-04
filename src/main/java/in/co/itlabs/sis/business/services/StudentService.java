package in.co.itlabs.sis.business.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import in.co.itlabs.sis.business.entities.Student;

@Service
public class StudentService {

	private DatabaseService databaseService;

	@Autowired
	public StudentService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public int createStudent(Student student) {
		int id = 0;
		try {
			ConnectionSource connectionSource = databaseService.getConnectioSource();
			Dao<Student, Integer> studentDao = DaoManager.createDao(connectionSource, Student.class);
			studentDao.create(student);
			id = student.getId();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public List<Student> getAllStudents(){
		List<Student> students = null;
		try {
			ConnectionSource connectionSource = databaseService.getConnectioSource();
			Dao<Student, Integer> studentDao = DaoManager.createDao(connectionSource, Student.class);
			students = studentDao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return students;
	}
}
