package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.entities.Program;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.helpers.Stage;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentCard extends VerticalLayout {

	private Image photo;
	private TextField nameField;
	private TextField admissionIdField;
	private TextField programField;
	private TextField stageField;

	private int studentId;
	private Student student;

	private AcademicService academicService;
	private StudentService studentService;

	private StudentNameEditor nameEditor;
	private StudentProgramEditor programEditor;

	private Dialog dialog;

	public StudentCard(AcademicService academicService, StudentService studentService) {
		this.academicService = academicService;
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

		stageField = new TextField("Stage");
		configureStageField();

		VerticalLayout card = new VerticalLayout();
		card.addClassName("card");
		card.setAlignItems(Alignment.CENTER);
		card.add(photo, nameField, admissionIdField, programField, stageField);

		add(card);
	}

	private void configureNameField() {
		nameField.setWidthFull();
		nameField.setReadOnly(true);
		nameField.getElement().addEventListener("dblclick", e -> {
			dialog.open();
			if (nameEditor == null) {
				nameEditor = new StudentNameEditor(studentService);
				nameEditor.addListener(StudentNameEditor.NameUpdatedEvent.class, this::handleNameUpdatedEvent);
			}
			dialog.removeAll();
			dialog.add(nameEditor);
			nameEditor.setStudent(student);
		});
	}

	private void configureAdmissionIdField() {
		admissionIdField.setWidthFull();
		admissionIdField.setReadOnly(true);
	}

	private void configureProgramField() {
		programField.setWidthFull();
		programField.setReadOnly(true);
		programField.getElement().addEventListener("dblclick", e -> {
			dialog.open();
			if (programEditor == null) {
				programEditor = new StudentProgramEditor(studentService, academicService);
				programEditor.addListener(StudentProgramEditor.ProgramUpdatedEvent.class,
						this::handleProgramUpdatedEvent);
			}
			dialog.removeAll();
			dialog.add(programEditor);
			programEditor.setStudent(student);
		});
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

	private void handleNameUpdatedEvent(StudentNameEditor.NameUpdatedEvent event) {
		Notification.show("Student '" + event.getStudent().getName() + "' updated.", 3000, Position.TOP_CENTER);
		reload();
	}

	private void handleProgramUpdatedEvent(StudentProgramEditor.ProgramUpdatedEvent event) {
		Notification.show("Student '" + event.getStudent().getName() + "' updated.", 3000, Position.TOP_CENTER);
		reload();
	}

	private void reload() {
		student = studentService.getStudentById(studentId);

		nameField.setValue(student.getName());
		admissionIdField.setValue(student.getAdmissionId());

		Program program = student.getProgram();
		if (program != null) {
			programField.setValue(student.getProgram().getName());
		}

		Stage stage = student.getStage();
		if (stage != null) {
			stageField.setValue(student.getStage().name());
		}

	}

}
