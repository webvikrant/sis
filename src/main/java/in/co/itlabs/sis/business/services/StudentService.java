package in.co.itlabs.sis.business.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import in.co.itlabs.sis.business.entities.Student;

@Service
public class StudentService {

	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<Student>();

		Student student = new Student();
		students.add(student);
		
		return students;
	}

}
