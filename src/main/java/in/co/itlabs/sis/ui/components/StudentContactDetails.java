package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentContactDetails extends VerticalLayout {

	private TextField studentField;
	private TextField mobileNoField;
	private TextField whatsappNoField;
	private TextField emailIdField;

	private TextField motherField;
	private TextField motherMobileNoField;
	private TextField motherWhatsappNoField;
	private TextField motherEmailIdField;

	private TextField fatherField;
	private TextField fatherMobileNoField;
	private TextField fatherWhatsappNoField;
	private TextField fatherEmailIdField;

	private TextField guardianField;
	private TextField guardianMobileNoField;
	private TextField guardianWhatsappNoField;
	private TextField guardianEmailIdField;

	private int id;
	private Student student;

	private AcademicService academicService;
	private StudentService studentService;

//	private StudentGenderEditor genderEditor;
	private Dialog dialog;

//	private final List<String> messages = new ArrayList<>();

	public StudentContactDetails(StudentService studentService, AcademicService academicService) {
		this.academicService = academicService;
		this.studentService = studentService;

		setSpacing(false);

		dialog = new Dialog();
		configureDialog();

		studentField = new TextField("Student");
		configureStudentField();

		mobileNoField = new TextField("Mobile No");
		configureMobileNoField();

		whatsappNoField = new TextField("WhatsApp No");
		configureWhatsappNoField();

		emailIdField = new TextField("Email Id");
		configureEmailIdField();

		motherField = new TextField("Mother");
		configureMotherField();

		motherMobileNoField = new TextField("Mobile No");
		configureMotherMobileNoField();

		motherWhatsappNoField = new TextField("WhatsApp No");
		configureMotherWhatsappNoField();

		motherEmailIdField = new TextField("Email Id");
		configureMotherEmailIdField();

		fatherField = new TextField("Father");
		configureFatherField();

		fatherMobileNoField = new TextField("Mobile No");
		configureFatherMobileNoField();

		fatherWhatsappNoField = new TextField("WhatsApp No");
		configureFatherWhatsappNoField();

		fatherEmailIdField = new TextField("Email Id");
		configureFatherEmailIdField();

		guardianField = new TextField("Guardian");
		configureGuardianField();

		guardianMobileNoField = new TextField("Mobile No");
		configureGuardianMobileNoField();

		guardianWhatsappNoField = new TextField("WhatsApp No");
		configureGuardianWhatsappNoField();

		guardianEmailIdField = new TextField("Email Id");
		configureGuardianEmailIdField();

		FlexLayout flex1 = new FlexLayout();
		configureFlex(flex1);

		FlexLayout flex2 = new FlexLayout();
		configureFlex(flex2);

		FlexLayout flex3 = new FlexLayout();
		configureFlex(flex3);

		FlexLayout flex4 = new FlexLayout();
		configureFlex(flex4);

		FlexLayout flex5 = new FlexLayout();
		configureFlex(flex5);

		flex1.add(studentField, mobileNoField, whatsappNoField, emailIdField);
		flex2.add(motherField, motherMobileNoField, motherWhatsappNoField, motherEmailIdField);
		flex3.add(fatherField, fatherMobileNoField, fatherWhatsappNoField, fatherEmailIdField);
		flex4.add(guardianField, guardianMobileNoField, guardianWhatsappNoField, guardianEmailIdField);

		add(flex1, flex2, flex3, flex4);

	}

	private void configureFlex(FlexLayout flexLayout) {
		flexLayout.setFlexWrap(FlexWrap.WRAP);
//		flexLayout.setAlignItems(Alignment.END);
		flexLayout.getElement().getStyle().set("padding", "8px");
		flexLayout.getElement().getStyle().set("gap", "8px");
	}

	private void configureStudentField() {
		studentField.setWidth("150px");
		studentField.setReadOnly(true);
	}

	private void configureMobileNoField() {
		mobileNoField.setWidth("110px");
		mobileNoField.setReadOnly(true);
	}

	private void configureWhatsappNoField() {
		whatsappNoField.setWidth("110px");
		whatsappNoField.setReadOnly(true);
	}

	private void configureEmailIdField() {
		emailIdField.setWidth("250px");
		emailIdField.setReadOnly(true);
	}

	private void configureMotherField() {
		motherField.setWidth("150px");
		motherField.setReadOnly(true);
	}

	private void configureMotherMobileNoField() {
		motherMobileNoField.setWidth("110px");
		motherMobileNoField.setReadOnly(true);
	}

	private void configureMotherWhatsappNoField() {
		motherWhatsappNoField.setWidth("110px");
		motherWhatsappNoField.setReadOnly(true);
	}

	private void configureMotherEmailIdField() {
		motherEmailIdField.setWidth("250px");
		motherEmailIdField.setReadOnly(true);
	}

	private void configureFatherField() {
		fatherField.setWidth("150px");
		fatherField.setReadOnly(true);
	}

	private void configureFatherMobileNoField() {
		fatherMobileNoField.setWidth("110px");
		fatherMobileNoField.setReadOnly(true);
	}

	private void configureFatherWhatsappNoField() {
		fatherWhatsappNoField.setWidth("110px");
		fatherWhatsappNoField.setReadOnly(true);
	}

	private void configureFatherEmailIdField() {
		fatherEmailIdField.setWidth("250px");
		fatherEmailIdField.setReadOnly(true);
	}

	private void configureGuardianField() {
		guardianField.setWidth("150px");
		guardianField.setReadOnly(true);
	}

	private void configureGuardianMobileNoField() {
		guardianMobileNoField.setWidth("110px");
		guardianMobileNoField.setReadOnly(true);
	}

	private void configureGuardianWhatsappNoField() {
		guardianWhatsappNoField.setWidth("110px");
		guardianWhatsappNoField.setReadOnly(true);
	}

	private void configureGuardianEmailIdField() {
		guardianEmailIdField.setWidth("250px");
		guardianEmailIdField.setReadOnly(true);
	}

	public void setStudentId(int id) {
		this.id = id;
		if (id == 0) {
			StudentContactDetails.this.setVisible(false);
		} else {
			StudentContactDetails.this.setVisible(true);
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

		studentField.clear();
		if (student.getName() != null) {
			studentField.setValue(student.getName());
		}

		mobileNoField.clear();
		if (student.getMobileNo() != null) {
			mobileNoField.setValue(student.getMobileNo());
		}

		whatsappNoField.clear();
		if (student.getWhatsappNo() != null) {
			whatsappNoField.setValue(student.getWhatsappNo());
		}

		emailIdField.clear();
		if (student.getEmailId() != null) {
			emailIdField.setValue(student.getEmailId());
		}
	}

//	private void handleGenderUpdatedEvent(StudentGenderEditor.GenderUpdatedEvent event) {
//		Notification.show("Student '" + event.getStudent().getName() + "' updated.", 3000, Position.TOP_CENTER);
//		reload();
//	}
}
