package in.co.itlabs.sis.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import in.co.itlabs.sis.business.entities.Contact;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.components.editors.ContactEditor;

public class StudentContactDetails extends VerticalLayout {

	private ContactEditor studentContactEditor;
	private Checkbox editStudentContactCheck;

	private ContactEditor motherContactEditor;
	private Checkbox editMotherContactCheck;

	private ContactEditor fatherContactEditor;
	private Checkbox editFatherContactCheck;

	private int studentId;
	private Student student;

	private StudentService studentService;

	private final List<String> messages = new ArrayList<>();

	public StudentContactDetails(StudentService studentService) {

		this.studentService = studentService;

		setPadding(true);
		setSpacing(true);

		studentContactEditor = new ContactEditor();
		configureAddressEditor(studentContactEditor);

		editStudentContactCheck = new Checkbox("Edit");
		configurePermanentCheck();

		motherContactEditor = new ContactEditor();
		configureAddressEditor(motherContactEditor);

		editMotherContactCheck = new Checkbox("Edit");
		configureCorrespondenceCheck();

		fatherContactEditor = new ContactEditor();
		configureAddressEditor(fatherContactEditor);

		editFatherContactCheck = new Checkbox("Edit");
		configureLocalGuardianCheck();

		VerticalLayout studentContact = new VerticalLayout();
		studentContact.add(editStudentContactCheck, studentContactEditor);

		VerticalLayout motherContact = new VerticalLayout();
		motherContact.add(editMotherContactCheck, motherContactEditor);

		VerticalLayout fatherContact = new VerticalLayout();
		fatherContact.add(editFatherContactCheck, fatherContactEditor);

		add(studentContact, motherContact, fatherContact);

		studentContactEditor.addListener(ContactEditor.SaveEvent.class, this::handleSaveContactEvent);
		studentContactEditor.addListener(ContactEditor.CancelEvent.class, this::handleCancelContactEvent);

		motherContactEditor.addListener(ContactEditor.SaveEvent.class, this::handleSaveContactEvent);
		motherContactEditor.addListener(ContactEditor.CancelEvent.class, this::handleCancelContactEvent);

		fatherContactEditor.addListener(ContactEditor.SaveEvent.class, this::handleSaveContactEvent);
		fatherContactEditor.addListener(ContactEditor.CancelEvent.class, this::handleCancelContactEvent);

	}

	private void configureAddressEditor(ContactEditor contactEditor) {
		contactEditor.addClassName("card");
		contactEditor.setEditingEnabled(false);
	}

	public void setStudentId(int id) {
		this.studentId = id;
		if (id == 0) {
			StudentContactDetails.this.setVisible(false);
		} else {
			StudentContactDetails.this.setVisible(true);
			reload();
		}
	}

	private void configurePermanentCheck() {
		editStudentContactCheck.addValueChangeListener(e -> {
			studentContactEditor.setEditingEnabled(e.getValue());
		});
	}

	private void configureCorrespondenceCheck() {
		editMotherContactCheck.addValueChangeListener(e -> {
			motherContactEditor.setEditingEnabled(e.getValue());
		});
	}

	private void configureLocalGuardianCheck() {
		editFatherContactCheck.addValueChangeListener(e -> {
			fatherContactEditor.setEditingEnabled(e.getValue());
		});
	}

	private void reload() {
		student = studentService.getStudentById(studentId);

		Contact studentAddress = new Contact();
		Contact motherAddress = new Contact();
		Contact fatherAddress = new Contact();

		studentAddress.setMobileNo(student.getMobileNo());
		studentAddress.setWhatsappNo(student.getWhatsappNo());
		studentAddress.setEmailId(student.getEmailId());
		studentAddress.setStudentId(studentId);
		studentAddress.setType(Contact.Type.Student);
		if (student.getName() != null) {
			studentAddress.setName(student.getName());
		}

		motherAddress.setMobileNo(student.getMotherMobileNo());
		motherAddress.setWhatsappNo(student.getMotherWhatsappNo());
		motherAddress.setEmailId(student.getMotherEmailId());
		motherAddress.setStudentId(studentId);
		motherAddress.setType(Contact.Type.Mother);
		if (student.getMotherName() != null) {
			studentAddress.setName(student.getMotherName());
		}

		fatherAddress.setMobileNo(student.getFatherMobileNo());
		fatherAddress.setWhatsappNo(student.getFatherWhatsappNo());
		fatherAddress.setEmailId(student.getFatherEmailId());
		fatherAddress.setStudentId(studentId);
		fatherAddress.setType(Contact.Type.Father);
		if (student.getFatherName() != null) {
			studentAddress.setName(student.getFatherName());
		}

		studentContactEditor.setContact(studentAddress);
		motherContactEditor.setContact(motherAddress);
		fatherContactEditor.setContact(fatherAddress);
	}

	public void handleSaveContactEvent(ContactEditor.SaveEvent event) {
		Contact contact = event.getContact();

		messages.clear();
		boolean success = false;

		switch (contact.getType()) {
		case Student:
			success = studentService.updateStudentContactDetails(messages, contact);
			if (success) {
				Notification.show("Student contact updated successfully", 3000, Position.TOP_CENTER);
				editStudentContactCheck.setValue(false);
			}
			break;

		case Mother:
//			success = studentService.updateStudentCorrespondenceAddressDetails(messages, address);
//			if (success) {
//				Notification.show("Correspondence address updated successfully", 3000, Position.TOP_CENTER);
//				editMotherContactCheck.setValue(false);
//			}
			break;

		case Father:
//			success = studentService.updateStudentLocalGuardianAddressDetails(messages, address);
//			if (success) {
//				Notification.show("Local guardian address updated successfully", 3000, Position.TOP_CENTER);
//				editFatherContactCheck.setValue(false);
//			}
			break;

		default:
			break;
		}

	}

	public void handleCancelContactEvent(ContactEditor.CancelEvent event) {
		Contact contact = event.getContact();
		switch (contact.getType()) {
		case Student:
			editStudentContactCheck.setValue(false);
			break;

		case Mother:
			editMotherContactCheck.setValue(false);
			break;

		case Father:
			editFatherContactCheck.setValue(false);
			break;

		default:
			break;
		}
	}

}
