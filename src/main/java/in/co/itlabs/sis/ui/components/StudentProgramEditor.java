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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import in.co.itlabs.sis.business.entities.Program;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentProgramEditor extends VerticalLayout {

	private ComboBox<Program> programCombo;

	private Binder<Student> binder;

	private StudentService studentService;
	private AcademicService academicService;

	private final List<String> messages = new ArrayList<>();

	public StudentProgramEditor(StudentService studentService, AcademicService academicService) {

		this.studentService = studentService;
		this.academicService = academicService;

		setPadding(false);

		programCombo = new ComboBox<Program>("Program");
		configureProgramCombo();

		binder = new Binder<>(Student.class);

		binder.forField(programCombo).asRequired("Program can not be blank").bind("program");

		var actionBar = buildActionBar();

		add(programCombo, actionBar);

	}

	private void configureProgramCombo() {
		programCombo.setWidthFull();
		programCombo.setItemLabelGenerator(program -> {
			return program.getName();
		});
		programCombo.setItems(academicService.getAllPrograms());
	}

	public void setStudent(Student student) {
		binder.setBean(student);
		programCombo.focus();
	}

	private HorizontalLayout buildActionBar() {

		Button submitButton = new Button("OK", VaadinIcon.CHECK.create());
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		submitButton.addClickListener(event -> {

			if (binder.validate().isOk()) {
				messages.clear();
				boolean success = studentService.updateStudentProgramId(messages, binder.getBean().getId(),
						binder.getBean().getProgram().getId());

				if (success) {
					fireEvent(new ProgramUpdatedEvent(this, binder.getBean()));
				} else {
					Notification.show(messages.toString(), 3000, Position.TOP_CENTER);
				}
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

	public static abstract class StudentEvent extends ComponentEvent<StudentProgramEditor> {
		private Student student;

		protected StudentEvent(StudentProgramEditor source, Student student) {

			super(source, false);
			this.student = student;
		}

		public Student getStudent() {
			return student;
		}
	}

	public static class ProgramUpdatedEvent extends StudentEvent {
		ProgramUpdatedEvent(StudentProgramEditor source, Student student) {
			super(source, student);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {

		return getEventBus().addListener(eventType, listener);
	}
}
