package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentCard extends VerticalLayout {

	private Image photo;
	private TextField sessionField;
	private TextField admissionIdField;
	private TextField nameField;

	private int studentId; // student id

	private AcademicService academicService;
	private StudentService studentService;

	public StudentCard(AcademicService academicService, StudentService studentService) {
		this.academicService = academicService;
		this.studentService = studentService;

		photo = new Image("https://picsum.photos/100/110", "");
		photo.setWidth("100px");
		photo.setHeight("110px");

		admissionIdField = new TextField("Admission Id");
		admissionIdField.setWidthFull();

		nameField = new TextField("Name");
		nameField.setWidthFull();

		VerticalLayout card = new VerticalLayout();
		card.addClassName("card");
		card.setAlignItems(Alignment.CENTER);
		card.add(photo, nameField, admissionIdField);

		add(card);
	}

	public void setStudentId(int id) {
		this.studentId = id;
//		Notification.show("Student selected", 3000, Position.TOP_CENTER);
		if (id == 0) {
			StudentCard.this.setEnabled(false);
		} else {
			StudentCard.this.setEnabled(true);
			reload();
		}
	}

	private void reload() {
		Student student = studentService.getStudentById(studentId);
//		sessionField.setValue(student.getSession().getName());
		admissionIdField.setValue(student.getAdmissionId());
		nameField.setValue(student.getName());
	}

}
