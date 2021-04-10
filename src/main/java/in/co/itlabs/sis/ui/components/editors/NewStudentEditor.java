package in.co.itlabs.sis.ui.components.editors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import in.co.itlabs.sis.business.entities.Session;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;

public class NewStudentEditor extends VerticalLayout implements Editor {

	private ComboBox<Session> sessionCombo;
	private TextField admissionIdField;
	private TextField nameField;

	private Button saveButton;
	private Button cancelButton;

	private Binder<Student> binder;

	private AcademicService academicService;

	public NewStudentEditor(AcademicService academicService) {

		this.academicService = academicService;
		
		setPadding(false);
		
		sessionCombo = new ComboBox<Session>("Session");
		configureSessionCombo();

		admissionIdField = new TextField("Admission Id");
		admissionIdField.setWidthFull();

		nameField = new TextField("Name");
		nameField.setWidthFull();

		binder = new Binder<>(Student.class);

		binder.forField(sessionCombo).asRequired("Session can not be blank").bind("session");
		binder.forField(admissionIdField).asRequired("Admission Id can not be blank").bind("admissionId");
		binder.forField(nameField).asRequired("Name can not be blank").bind("name");

		saveButton = new Button("OK", VaadinIcon.CHECK.create());
		cancelButton = new Button("Cancel", VaadinIcon.CLOSE.create());

		HorizontalLayout actionBar = buildActionBar();
		actionBar.setWidthFull();

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
	
	public void setStudent(Student student) {
		binder.setBean(student);
	}

	private HorizontalLayout buildActionBar() {
		HorizontalLayout root = new HorizontalLayout();

		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton.addClickListener(e -> {
			if (binder.validate().isOk()) {

				binder.getBean().setSessionId(binder.getBean().getSession().getId());

				fireEvent(new SaveEvent(this, binder.getBean()));
			}
		});

		cancelButton.addClickListener(e -> {
			fireEvent(new CancelEvent(this, binder.getBean()));
		});

		Span blank = new Span();

		root.add(saveButton, blank, cancelButton);
		root.expand(blank);

		return root;
	}


	public static abstract class StudentEvent extends ComponentEvent<NewStudentEditor> {
		private Student student;

		protected StudentEvent(NewStudentEditor source, Student student) {

			super(source, false);
			this.student = student;
		}

		public Student getStudent() {
			return student;
		}
	}

	public static class SaveEvent extends StudentEvent {
		SaveEvent(NewStudentEditor source, Student student) {
			super(source, student);
		}
	}

	public static class CancelEvent extends StudentEvent {
		CancelEvent(NewStudentEditor source, Student student) {
			super(source, student);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {

		return getEventBus().addListener(eventType, listener);
	}

	@Override
	public void setEditingEnabled(boolean enabled) {
//		typeCombo.setReadOnly(!enabled);
		sessionCombo.setReadOnly(!enabled);
		admissionIdField.setReadOnly(!enabled);
		nameField.setReadOnly(!enabled);

		saveButton.setVisible(enabled);
		cancelButton.setVisible(enabled);
	}
}
