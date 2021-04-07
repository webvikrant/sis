package in.co.itlabs.sis.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;

public class PersonalDetailsEditor extends FlexLayout {

//	private TextField birthDateField;
	private DatePicker birthDateField;
	private TextField genderField;
	private TextField religionField;
	private TextField casteField;
	private TextField categoryField;
	private TextField aadhaarNoField;
	private TextField mobileNoField;
	private TextField whatsappNoField;
	private TextField emailIdField;

	private Binder<Student> binder;
	
	private int id;

	private AcademicService academicService;
	private StudentService studentService;

	private final List<String> messages = new ArrayList<>();
	
	public PersonalDetailsEditor(AcademicService academicService, StudentService studentService) {
		this.academicService = academicService;
		this.studentService = studentService;

		getElement().getStyle().set("padding", "8px");
		getElement().getStyle().set("gap", "8px");
		
		setFlexWrap(FlexWrap.WRAP);
		
//		birthDateField = new TextField("Birth date");
		
		birthDateField = new DatePicker("Date of Birth");
		
		genderField = new TextField("Gender");
		religionField = new TextField("Religion");
		casteField = new TextField("Caste");
		categoryField = new TextField("Category");
		aadhaarNoField = new TextField("Aadhaar No");
		mobileNoField = new TextField("Mobile No");
		whatsappNoField = new TextField("WhatsApp No");
		emailIdField = new TextField("Email Id");

		binder = new Binder<>(Student.class);

		binder.forField(birthDateField).bind("birthDate");

		var actionBar = buildActionBar();

		add(birthDateField, genderField, religionField, casteField, categoryField, aadhaarNoField, mobileNoField,
				whatsappNoField, emailIdField);
		add(actionBar);
	}

	public void setStudentId(int id) {
		this.id = id;
//		Notification.show("Student selected", 3000, Position.TOP_CENTER);
		if (id == 0) {
			PersonalDetailsEditor.this.setVisible(false);
		} else {
			PersonalDetailsEditor.this.setVisible(true);
			reload();
		}
	}

	private HorizontalLayout buildActionBar() {

		Button submitButton = new Button("OK", VaadinIcon.CHECK.create());
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		submitButton.addClickListener(event -> {

			if (binder.validate().isOk()) {
				messages.clear();
				boolean success = studentService.updateStudentBirthDate(messages, id, binder.getBean().getBirthDate());

				if (success) {
					fireEvent(new PersonalDetailsUpdatedEvent(this, binder.getBean()));
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

	private void reload() {
		Student student = studentService.getStudentById(id);
		binder.setBean(student);
		
//		birthDateField.setValue(student.getAdmissionId());
//
//		Gender gender = student.getGender();
//		if (gender != null) {
//			genderField.setValue(gender.name());
//		}
//
//		Religion religion = student.getReligion();
//		if (religion != null) {
//			religionField.setValue(religion.getName());
//		}
//
//		Caste caste = student.getCaste();
//		if (caste != null) {
//			casteField.setValue(caste.getName());
//		}
//
//		Category category = student.getCategory();
//		if (category != null) {
//			categoryField.setValue(category.getName());
//		}
//
//		String aadhaarNo = student.getAadhaarNo();
//		if (aadhaarNo != null) {
//			aadhaarNoField.setValue(aadhaarNo);
//		}
//
//		String mobileNo = student.getMobileNo();
//		if (mobileNo != null) {
//			mobileNoField.setValue(student.getMobileNo());
//		}
//
//		String whatsappNo = student.getWhatsappNo();
//		if (whatsappNo != null) {
//			whatsappNoField.setValue(student.getWhatsappNo());
//		}
//
//		String emailId = student.getEmailId();
//		if (emailId != null) {
//			emailIdField.setValue(student.getEmailId());
//		}
	}
	public static abstract class StudentEvent extends ComponentEvent<PersonalDetailsEditor> {
		private Student student;

		protected StudentEvent(PersonalDetailsEditor source, Student student) {

			super(source, false);
			this.student = student;
		}

		public Student getStudent() {
			return student;
		}
	}

	public static class PersonalDetailsUpdatedEvent extends StudentEvent {
		PersonalDetailsUpdatedEvent(PersonalDetailsEditor source, Student student) {
			super(source, student);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {

		return getEventBus().addListener(eventType, listener);
	}
}
