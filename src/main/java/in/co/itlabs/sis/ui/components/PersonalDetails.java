package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;

public class PersonalDetails extends VerticalLayout {

	private TextField fatherNameField;
	private TextField motherNameField;

	private int id;

	private AcademicService academicService;
	private StudentService studentService;

	public PersonalDetails(AcademicService academicService, StudentService studentService) {
		this.academicService = academicService;
		this.studentService = studentService;

		fatherNameField = new TextField("Father's name");
		motherNameField = new TextField("Mother's name");

		add(fatherNameField, motherNameField);
	}

	public void setStudentId(int id) {
		this.id = id;
//		Notification.show("Student selected", 3000, Position.TOP_CENTER);
		if (id == 0) {
			PersonalDetails.this.setVisible(false);
		} else {
			PersonalDetails.this.setVisible(true);
			reload();
		}
	}

	private void reload() {
		Student student = studentService.getStudentById(id);
		fatherNameField.setValue(student.getAdmissionId());
		motherNameField.setValue(student.getName());

	}

}
