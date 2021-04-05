package in.co.itlabs.sis.ui.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;

@Component
@UIScope
public class StudentCard extends VerticalLayout {

	private Image photo;
	private TextField sessionField;
	private TextField admissionIdField;
	private TextField nameField;
	
	private Button personalButton = new Button("Personal details");
	
	private int id; // student id

	private AcademicService academicService;
	private StudentService studentService;

	@Autowired
	public StudentCard(AcademicService academicService, StudentService studentService) {
		this.academicService = academicService;
		this.studentService = studentService;

		photo = new Image();

		sessionField = new TextField("Session");
		sessionField.setWidthFull();

		admissionIdField = new TextField("Admission Id");
		admissionIdField.setWidthFull();

		nameField = new TextField("Name");
		nameField.setWidthFull();

		VerticalLayout fieldsBar = new VerticalLayout();
		fieldsBar.addClassName("content");
		fieldsBar.add(photo, nameField, admissionIdField, sessionField);

		var navBar = buildNavBar();

		add(fieldsBar, navBar);
	}

	private VerticalLayout buildNavBar() {
		VerticalLayout root = new VerticalLayout();
		root.setPadding(false);

		
//		personalButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
		personalButton.setWidthFull();
		personalButton.addClickListener(event -> {
			Notification.show("Personal button clicked", 3000, Position.TOP_CENTER);
		});

		Button academicButton = new Button("Academic details");
//		academicButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
		academicButton.setWidthFull();

		Button scholarshipButton = new Button("Scholarship details");
//		scholarshipButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
		scholarshipButton.setWidthFull();

		root.add(personalButton, academicButton, scholarshipButton);

		return root;
	}

	public void setStudentId(int id) {
		this.id = id;
		Notification.show("Student selected", 3000, Position.TOP_CENTER);
		if (id == 0) {
			StudentCard.this.setEnabled(false);
		} else {
			StudentCard.this.setEnabled(true);
			reload();
		}
	}

	private void reload() {
		Student student = studentService.getStudentById(id);
//		sessionField.setValue(student.getSession().getName());
		admissionIdField.setValue(student.getAdmissionId());
		nameField.setValue(student.getName());
		
		personalButton.click();
	}

}
