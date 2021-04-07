package in.co.itlabs.sis.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentPersonalDetails extends FlexLayout {

	private TextField birthDateField;
	private TextField genderField;
	private TextField religionField;
	private TextField casteField;
	private TextField categoryField;
	private TextField aadhaarNoField;
	private TextField mobileNoField;
	private TextField whatsappNoField;
	private TextField emailIdField;

	private int id;
	private Student student;

	private AcademicService academicService;
	private StudentService studentService;

	private StudentGenderEditor genderEditor;
	private Dialog dialog;
	
	private final List<String> messages = new ArrayList<>();
	
	public StudentPersonalDetails(AcademicService academicService, StudentService studentService) {
		this.academicService = academicService;
		this.studentService = studentService;

		getElement().getStyle().set("padding", "8px");
		getElement().getStyle().set("gap", "8px");

		setFlexWrap(FlexWrap.WRAP);

		dialog = new Dialog();
		configureDialog();
		
		birthDateField = new TextField("Date of Birth");
		configureBirthDateField();

		genderField = new TextField("Gender");
		configureGenderField();

		religionField = new TextField("Religion");
		configureReligionField();

		casteField = new TextField("Caste");
		configureCasteField();

		categoryField = new TextField("Category");
		configureCategoryField();

		aadhaarNoField = new TextField("Aadhaar No");
		configureAadhaarNoField();

		mobileNoField = new TextField("Mobile No");
		configureMobileNoField();

		whatsappNoField = new TextField("WhatsApp No");
		configureWhatsappNoField();

		emailIdField = new TextField("Email Id");
		configureEmailIdField();

		add(birthDateField, genderField, religionField, casteField, categoryField, aadhaarNoField, mobileNoField,
				whatsappNoField, emailIdField);
		
		
	}

	private void configureBirthDateField() {
		birthDateField.setWidth("150px");
		birthDateField.setReadOnly(true);
	}

	private void configureGenderField() {
		genderField.setWidth("150px");
		genderField.setReadOnly(true);
		genderField.getElement().addEventListener("dblclick", e -> {
			dialog.open();
			if (genderEditor == null) {
				genderEditor = new StudentGenderEditor(studentService, academicService);
				genderEditor.addListener(StudentGenderEditor.GenderUpdatedEvent.class,
						this::handleGenderUpdatedEvent);
			}
			dialog.removeAll();
			dialog.add(genderEditor);
			genderEditor.setStudent(student);
		});
	}

	private void configureDialog() {
		// TODO Auto-generated method stub
		dialog.setWidth("300px");
		dialog.setModal(true);
		dialog.setDraggable(true);
	}
	
	private void configureReligionField() {
		religionField.setWidth("150px");
		religionField.setReadOnly(true);
	}

	private void configureCasteField() {
		casteField.setWidth("150px");
		casteField.setReadOnly(true);
	}

	private void configureCategoryField() {
		categoryField.setWidth("150px");
		categoryField.setReadOnly(true);
	}

	private void configureAadhaarNoField() {
		aadhaarNoField.setWidth("150px");
		aadhaarNoField.setReadOnly(true);
	}

	private void configureMobileNoField() {
		mobileNoField.setWidth("150px");
		mobileNoField.setReadOnly(true);
	}

	private void configureWhatsappNoField() {
		whatsappNoField.setWidth("150px");
		whatsappNoField.setReadOnly(true);
	}

	private void configureEmailIdField() {
		emailIdField.setWidth("150px");
		emailIdField.setReadOnly(true);
	}

	public void setStudentId(int id) {
		this.id = id;
		if (id == 0) {
			StudentPersonalDetails.this.setVisible(false);
		} else {
			StudentPersonalDetails.this.setVisible(true);
			reload();
		}
	}

	private void reload() {
		student = studentService.getStudentById(id);

		birthDateField.clear();
		if (student.getBirthDate() != null) {
			birthDateField.setValue(student.getBirthDate().toString());
		}

		genderField.clear();
		if (student.getGender() != null) {
			genderField.setValue(student.getGender().toString());
		}
	}
	
	private void handleGenderUpdatedEvent(StudentGenderEditor.GenderUpdatedEvent event) {
		Notification.show("Student '" + event.getStudent().getName() + "' updated.", 3000, Position.TOP_CENTER);
		reload();
	}
}
