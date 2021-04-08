package in.co.itlabs.sis.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import in.co.itlabs.sis.business.entities.State;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AddressService;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentStateEditor extends VerticalLayout {

	private ComboBox<State> stateCombo;

	private Binder<Student> binder;

	private StudentService studentService;
	private AddressService addressService;

	private final List<String> messages = new ArrayList<>();

	public StudentStateEditor(StudentService studentService, AddressService addressService) {

		this.studentService = studentService;
		this.addressService = addressService;

		setPadding(false);

		stateCombo = new ComboBox<State>("State");
		configureStateCombo();

		binder = new Binder<>(Student.class);

		binder.forField(stateCombo).asRequired("State can not be blank").bind("state");

		var actionBar = buildActionBar();

		add(stateCombo, actionBar);

	}

	private void configureStateCombo() {
		stateCombo.setWidthFull();
		stateCombo.setItemLabelGenerator(program -> {
			return program.getName();
		});
		stateCombo.setItems(addressService.getAllStates());
	}

	public void setStudent(Student student) {
		binder.setBean(student);
		stateCombo.focus();
	}

	private HorizontalLayout buildActionBar() {

		Button submitButton = new Button("OK", VaadinIcon.CHECK.create());
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		submitButton.addClickListener(event -> {

			if (binder.validate().isOk()) {
				messages.clear();
//				boolean success = studentService.updateStudentProgram(messages, binder.getBean().getId(),
//						binder.getBean().getProgram().getId());
//
//				if (success) {
//					fireEvent(new ProgramUpdatedEvent(this, binder.getBean()));
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

	public static abstract class StudentEvent extends ComponentEvent<StudentStateEditor> {
		private Student student;

		protected StudentEvent(StudentStateEditor source, Student student) {

			super(source, false);
			this.student = student;
		}

		public Student getStudent() {
			return student;
		}
	}

	public static class StateUpdatedEvent extends StudentEvent {
		StateUpdatedEvent(StudentStateEditor source, Student student) {
			super(source, student);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {

		return getEventBus().addListener(eventType, listener);
	}
}
