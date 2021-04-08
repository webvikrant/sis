package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentPersonalDetails extends VerticalLayout {

	private TextField birthDateField;
	private TextField genderField;
	private TextField motherField;
	private TextField fatherField;
	private TextField guardianField;
	private TextField religionField;
	private TextField casteField;
	private TextField categoryField;
	private TextField aadhaarNoField;

	private int id;
	private Student student;

	private AcademicService academicService;
	private StudentService studentService;

	private StudentGenderEditor genderEditor;
	private Dialog dialog;

//	private final List<String> messages = new ArrayList<>();

	public StudentPersonalDetails(StudentService studentService, AcademicService academicService) {
		this.academicService = academicService;
		this.studentService = studentService;

		dialog = new Dialog();
		configureDialog();

		birthDateField = new TextField("Date of Birth");
		configureBirthDateField();

		genderField = new TextField("Gender");
		configureGenderField();

		motherField = new TextField("Mother");
		configureMotherField();

		fatherField = new TextField("Father");
		configureFatherField();

		guardianField = new TextField("Local guardian");
		configureGuardianField();

		religionField = new TextField("Religion");
		configureReligionField();

		casteField = new TextField("Caste");
		configureCasteField();

		categoryField = new TextField("Category");
		configureCategoryField();

		aadhaarNoField = new TextField("Aadhaar No");
		configureAadhaarNoField();

		FlexLayout flex1 = new FlexLayout();
		configureFlex(flex1);

		FlexLayout flex2 = new FlexLayout();
		configureFlex(flex2);

		FlexLayout flex3 = new FlexLayout();
		configureFlex(flex3);

		FlexLayout flex4 = new FlexLayout();
		configureFlex(flex4);

		flex1.add(birthDateField, genderField);
		flex2.add(motherField, fatherField, guardianField);
		flex3.add(religionField, casteField, categoryField);
		flex4.add(aadhaarNoField);

		add(flex1, flex2, flex3, flex4);

	}

	private void configureFlex(FlexLayout flexLayout) {
		flexLayout.setFlexWrap(FlexWrap.WRAP);
		flexLayout.getElement().getStyle().set("padding", "8px");
		flexLayout.getElement().getStyle().set("gap", "8px");
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
				genderEditor.addListener(StudentGenderEditor.GenderUpdatedEvent.class, this::handleGenderUpdatedEvent);
			}
			dialog.removeAll();
			dialog.add(genderEditor);
			genderEditor.setStudent(student);
		});
	}

	private void configureMotherField() {
		motherField.setWidth("200px");
		motherField.setReadOnly(true);
	}

	private void configureFatherField() {
		fatherField.setWidth("200px");
		fatherField.setReadOnly(true);
	}

	private void configureGuardianField() {
		guardianField.setWidth("200px");
		guardianField.setReadOnly(true);
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
		aadhaarNoField.setWidth("250px");
		aadhaarNoField.setReadOnly(true);
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

	private void configureDialog() {
		// TODO Auto-generated method stub
		dialog.setWidth("300px");
		dialog.setModal(true);
		dialog.setDraggable(true);
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
