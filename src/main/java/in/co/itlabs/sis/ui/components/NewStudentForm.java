package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import in.co.itlabs.sis.business.entities.Session;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;

public class NewStudentForm extends VerticalLayout {

	private ComboBox<Session> sessionCombo;
	private TextField admissionIdField;
	private TextField nameField;

	private Binder<Student> binder;

	private AcademicService academicService;
	private StudentService studentService;

	public NewStudentForm(AcademicService academicService, StudentService studentService) {

		this.academicService = academicService;
		this.studentService = studentService;

		setPadding(false);

		sessionCombo = new ComboBox<Session>("Session");
		configureSessionCombo();

		admissionIdField = new TextField("Admission Id");
		admissionIdField.setWidthFull();

		nameField = new TextField("Name");
		nameField.setWidthFull();

		binder = new Binder<>(Student.class);

//		binder.forField(sessionCombo).asRequired("Session can not be blank").bind("session");
		binder.forField(admissionIdField).asRequired("Admission Id can not be blank").bind("admissionId");
		binder.forField(nameField).asRequired("Name can not be blank").bind("name");

		var actionBar = buildActionBar();

		add(sessionCombo, admissionIdField, nameField, actionBar);

	}

	private void configureSessionCombo() {
		// TODO Auto-generated method stub
		sessionCombo.setWidthFull();
		sessionCombo.setPlaceholder("Select a session...");

		sessionCombo.setItemLabelGenerator(session -> {
			return session.getName();
		});

		sessionCombo.setItems(academicService.getAllSessions());
	}

	private HorizontalLayout buildActionBar() {
		Button submitButton = new Button("OK", VaadinIcon.CHECK.create());
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		submitButton.addClickListener(event -> {
			try {
				Student student = new Student();
				binder.writeBean(student);
//				studentService.createStudent(student);
				fireEvent(new StudentCreatedEvent(this, student));

			} catch (ValidationException e) {
				Notification.show(e.getMessage(), 3000, Position.TOP_CENTER);
			}
		});

		Button resetButton = new Button("Reset", VaadinIcon.CLOSE.create());

		Span blank = new Span();

		HorizontalLayout root = new HorizontalLayout();
		root.setWidthFull();
		root.add(submitButton, blank, resetButton);
		root.expand(blank);

		return root;
	}

	public static abstract class StudentEvent extends ComponentEvent<NewStudentForm> {
		private Student student;

		protected StudentEvent(NewStudentForm source, Student student) {

			super(source, false);
			this.student = student;
		}

		public Student getStudent() {
			return student;
		}
	}

	public static class StudentCreatedEvent extends StudentEvent {
		StudentCreatedEvent(NewStudentForm source, Student student) {
			super(source, student);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {

		return getEventBus().addListener(eventType, listener);
	}
}
