package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
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
	private Dialog dialog;

	public StudentCard(AcademicService academicService, StudentService studentService) {
		this.academicService = academicService;
		this.studentService = studentService;

		photo = new Image("https://picsum.photos/100/110", "");
		photo.setWidth("100px");
		photo.setHeight("110px");
		photo.addClassName("photo");

		nameField = new TextField("Name");
		configureNamefield();

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

		nameEditor = new StudentNameEditor(studentService);
		dialog = new Dialog();
		configureDialog();

		nameEditor.addListener(StudentNameEditor.NameUpdatedEvent.class, this::handleNameUpdatedEvent);

	}

	private void configureNamefield() {
		nameField.setWidthFull();
		nameField.setReadOnly(true);
		nameField.getElement().addEventListener("dblclick", e -> {
//			Notification.show("Student Id: " + studentId, 3000, Position.TOP_CENTER);
			dialog.open();
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
		Span title = new Span("Edit Student Name");
		Button closeButton = new Button(VaadinIcon.CLOSE.create());
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		closeButton.addClickListener(event -> {
			dialog.close();
		});

		FlexLayout titleBar = new FlexLayout();
		titleBar.setJustifyContentMode(JustifyContentMode.BETWEEN);
		titleBar.setWidthFull();
		titleBar.setAlignItems(Alignment.CENTER);
		titleBar.add(title, closeButton);

		dialog.add(titleBar, nameEditor);

		dialog.setWidth("300px");
		dialog.setModal(true);
		dialog.setDraggable(true);
		dialog.setCloseOnOutsideClick(false);
	}

	private void handleNameUpdatedEvent(StudentNameEditor.NameUpdatedEvent event) {
		Notification.show("Student '" + event.getStudent().getName() + "' updated.", 3000, Position.TOP_CENTER);
		reload();
	}

	private void reload() {
		student = studentService.getStudentById(studentId);
		nameField.setValue(student.getName());
		admissionIdField.setValue(student.getAdmissionId());

//		Program program = student.getProgram();
//		if (program != null) {
//			programField.setValue(student.getProgram().getName());
//		}
//
//		Stage stage = student.getStage();
//		if (stage != null) {
//			stageField.setValue(student.getStage().name());
//		}

	}

}
