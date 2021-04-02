package in.co.itlabs.sis.business.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import in.co.itlabs.sis.business.entities.Student;

@Service
public class StudentService {

	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<Student>();

		Student student = null;
		
		student = new Student();
		student.setId(1);
		student.setName("11111111111111111111111111");
		students.add(student);

		student = new Student();
		student.setId(2);
		student.setName("222222222222222222222222222");
		students.add(student);

		student = new Student();
		student.setId(3);
		student.setName("333333333333333333");
		students.add(student);

		student = new Student();
		student.setId(4);
		student.setName("4444444444444444444444");
		students.add(student);

		student = new Student();
		student.setId(5);
		student.setName("555555555555555555555555");
		students.add(student);

		return students;
	}

}
