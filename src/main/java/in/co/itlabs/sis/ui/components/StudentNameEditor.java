package in.co.itlabs.sis.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentNameEditor extends VerticalLayout {

	private TextField nameField;

	private Binder<Student> binder;

	private StudentService studentService;
	private final List<String> messages = new ArrayList<>();

	public StudentNameEditor(StudentService studentService) {

		this.studentService = studentService;

		setPadding(false);

		nameField = new TextField("Name");
		nameField.setWidthFull();

		binder = new Binder<>(Student.class);

		binder.forField(nameField).asRequired("Name can not be blank").bind("name");

		var actionBar = buildActionBar();

		add(nameField, actionBar);

	}

	public void setStudent(Student student) {
//		this.student = student;
		binder.setBean(student);
	}

	private HorizontalLayout buildActionBar() {

		Button submitButton = new Button("OK", VaadinIcon.CHECK.create());
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		submitButton.addClickListener(event -> {

			if (binder.validate().isOk()) {
				messages.clear();
//				boolean success = studentService.updateStudent(messages, binder.getBean());
//
//				if (success) {
//					fireEvent(new NameUpdatedEvent(this, binder.getBean()));
//				} else {
//					Notification.show(messages.toString(), 3000, Position.TOP_CENTER);
//				}
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

	public static abstract class StudentEvent extends ComponentEvent<StudentNameEditor> {
		private Student student;

		protected StudentEvent(StudentNameEditor source, Student student) {

			super(source, false);
			this.student = student;
		}

		public Student getStudent() {
			return student;
		}
	}

	public static class NameUpdatedEvent extends StudentEvent {
		NameUpdatedEvent(StudentNameEditor source, Student student) {
			super(source, student);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {

		return getEventBus().addListener(eventType, listener);
	}
}
