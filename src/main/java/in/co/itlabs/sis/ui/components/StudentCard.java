package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.entities.Program;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.helpers.Stage;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentCard extends VerticalLayout {

	private Image photo;
	private TextField nameField;
	private TextField admissionIdField;
	private TextField programField;
	private TextField statusField;
	private TextField stageField;

	private int studentId;
	private Student student;

	private StudentService studentService;

	private Dialog dialog;

	public StudentCard(StudentService studentService) {
		this.studentService = studentService;

		dialog = new Dialog();
		configureDialog();
		
		photo = new Image("https://picsum.photos/100/110", "");
		photo.setWidth("100px");
		photo.setHeight("110px");
		photo.addClassName("photo");

		nameField = new TextField("Name");
		configureNameField();

		admissionIdField = new TextField("Admission Id");
		configureAdmissionIdField();

		programField = new TextField("Program");
		configureProgramField();

		statusField = new TextField("Status");
		configureStatusField();

		stageField = new TextField("Stage");
		configureStageField();

		VerticalLayout card = new VerticalLayout();
		card.addClassName("card");
		card.setAlignItems(Alignment.CENTER);
		card.add(photo, nameField, admissionIdField, programField, statusField, stageField);

		add(card);
	}

	private void configureNameField() {
		nameField.setWidthFull();
		nameField.setReadOnly(true);
	}

	private void configureAdmissionIdField() {
		admissionIdField.setWidthFull();
		admissionIdField.setReadOnly(true);
	}

	private void configureProgramField() {
		programField.setWidthFull();
		programField.setReadOnly(true);
	}

	private void configureStatusField() {
		// TODO Auto-generated method stub
		statusField.setWidthFull();
		statusField.setReadOnly(true);
	}

	private void configureStageField() {
		// TODO Auto-generated method stub
		stageField.setWidthFull();
		stageField.setReadOnly(true);
	}

	public void setStudentId(int id) {
		this.studentId = id;
		if (id == 0) {
			StudentCard.this.setEnabled(false);
		} else {
			StudentCard.this.setEnabled(true);
			reload();
		}
	}

	private void configureDialog() {
		// TODO Auto-generated method stub
		dialog.setWidth("300px");
		dialog.setModal(true);
		dialog.setDraggable(true);
	}

	private void reload() {
		student = studentService.getStudentById(studentId);

		nameField.setValue(student.getName());
		admissionIdField.setValue(student.getAdmissionId());

		Program program = student.getProgram();
		if (program != null) {
			programField.setValue(program.getName());
		}

		Stage stage = student.getStage();
		if (stage != null) {
			stageField.setValue(stage.name());
		}

	}

}
