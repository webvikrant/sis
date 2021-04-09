package in.co.itlabs.sis.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.entities.Contact;
import in.co.itlabs.sis.business.entities.Contact.Type;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.StudentService;

public class ContactComponent extends FlexLayout {

	private TextField typeField;
	private TextField mobileNoField;
	private TextField whatsappNoField;
	private TextField emailIdField;

	private int studentId;
	private Contact.Type contactType;
	private Contact contact;

	private StudentService studentService;

	private MobileNoEditor mobileNoEditor;

	private Dialog dialog;

	private final List<String> messages = new ArrayList<>();

	public ContactComponent(StudentService studentService, Contact.Type contactType) {

		getElement().getStyle().set("padding", "8px");
		getElement().getStyle().set("gap", "8px");
		setFlexWrap(FlexWrap.WRAP);

		this.studentService = studentService;
		this.contactType = contactType;

		dialog = new Dialog();
		configureDialog();

		typeField = new TextField();
		configureTypeField();

		mobileNoField = new TextField("Mobile No");
		configureMobileNoField();

		whatsappNoField = new TextField("Whatsapp No");
		configureWhatsappNoField();

		emailIdField = new TextField("Email Id");
		configureEmailIdField();

		add(typeField, mobileNoField, whatsappNoField, emailIdField);
	}

	private void configureTypeField() {
		typeField.setWidth("150px");
		typeField.setReadOnly(true);
	}

	private void configureMobileNoField() {
		mobileNoField.setWidth("110px");
		mobileNoField.setReadOnly(true);
		mobileNoField.getElement().addEventListener("dblclick", e -> {
			dialog.open();
			if (mobileNoEditor == null) {
				mobileNoEditor = new MobileNoEditor();
				mobileNoEditor.addListener(MobileNoEditor.SaveEvent.class, this::handleMobileNoSaveEvent);
			}
			dialog.removeAll();
			dialog.add(mobileNoEditor);
			mobileNoEditor.setContact(contact);
		});
	}

	private void configureWhatsappNoField() {
		whatsappNoField.setWidth("110px");
		whatsappNoField.setReadOnly(true);
		whatsappNoField.getElement().addEventListener("dblclick", e -> {
			dialog.open();
//			if (mobileNoEditor == null) {
//				mobileNoEditor = new WhatsappNoEditor(addressService);
//				mobileNoEditor.addListener(DistrictStateEditor.SaveEvent.class, this::handleDistrictSaveEvent);
//			}
//			dialog.removeAll();
//			dialog.add(mobileNoEditor);
//			mobileNoEditor.setAddress(contact);
		});
	}

	private void configureEmailIdField() {
		emailIdField.setWidth("250px");
		emailIdField.setReadOnly(true);
		emailIdField.getElement().addEventListener("dblclick", e -> {
//			dialog.open();
//			if (stateEditor == null) {
//				stateEditor = new StudentProgramEditor(studentService, academicService);
//				stateEditor.addListener(StudentProgramEditor.ProgramUpdatedEvent.class,
//						this::handleProgramUpdatedEvent);
//			}
//			dialog.removeAll();
//			dialog.add(stateEditor);
//			stateEditor.setStudent(student);
		});
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
		reload();
	}

	private void configureDialog() {
		// TODO Auto-generated method stub
		dialog.setWidth("300px");
		dialog.setModal(true);
		dialog.setDraggable(true);
	}

	private void handleMobileNoSaveEvent(MobileNoEditor.SaveEvent event) {
		Contact updatedContact = event.getContact();
		if (updatedContact.getType() == Type.Student) {
			studentService.updateStudentMobileNo(messages, updatedContact.getStudentId(), updatedContact.getMobileNo());
		} else if (updatedContact.getType() == Type.Mother) {

		} else if (updatedContact.getType() == Type.Father) {

		} else if (updatedContact.getType() == Type.Local_Guardian) {

		}
		reload();
	}

	private void reload() {

		Student student = studentService.getStudentById(studentId);

		contact = new Contact();

		contact.setStudentId(studentId);
		contact.setType(contactType);

		switch (contactType) {
		case Student:
			contact.setMobileNo(student.getMobileNo());
			contact.setWhatsappNo(student.getWhatsappNo());
			contact.setEmailId(student.getEmailId());

			break;

		case Mother:
			contact.setMobileNo(student.getMotherMobileNo());
			contact.setWhatsappNo(student.getMotherWhatsappNo());
			contact.setEmailId(student.getMotherEmailId());

			break;

		case Father:
			contact.setMobileNo(student.getFatherMobileNo());
			contact.setWhatsappNo(student.getFatherWhatsappNo());
			contact.setEmailId(student.getFatherEmailId());

			break;

		case Local_Guardian:
			contact.setMobileNo(student.getLocalGuardianMobileNo());
			contact.setWhatsappNo(student.getLocalGuardianWhatsappNo());
			contact.setEmailId(student.getLocalGuardianEmailId());

			break;

		default:
			break;
		}

		if (contactType == Contact.Type.Student) {
			typeField.setLabel("Student");
			if (student.getName() != null) {
				typeField.setValue(student.getName());
			}

		} else if (contactType == Contact.Type.Mother) {
			typeField.setLabel("Mother");
			if (student.getMotherName() != null) {
				typeField.setValue(student.getMotherName());
			}

		} else if (contactType == Contact.Type.Father) {
			typeField.setLabel("Father");
			if (student.getFatherName() != null) {
				typeField.setValue(student.getFatherName());
			}

		} else if (contactType == Contact.Type.Local_Guardian) {
			typeField.setLabel("Local guardian");
			if (student.getLocalGuardianName() != null) {
				typeField.setValue(student.getLocalGuardianName());
			}
		}

		if (contact.getMobileNo() != null) {
			mobileNoField.setValue(contact.getMobileNo());
		}
		if (contact.getWhatsappNo() != null) {
			whatsappNoField.setValue(contact.getWhatsappNo());
		}
		if (contact.getEmailId() != null) {
			emailIdField.setValue(contact.getEmailId());
		}
	}
}