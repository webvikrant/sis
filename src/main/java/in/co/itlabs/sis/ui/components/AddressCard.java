package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.entities.District;
import in.co.itlabs.sis.business.entities.State;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.helpers.AddressType;
import in.co.itlabs.sis.business.services.AddressService;
import in.co.itlabs.sis.business.services.StudentService;

public class AddressCard extends VerticalLayout {

	private AddressType addressType;

	private TextField typeField;
	private TextField stateField;
	private TextField districtField;
	private TextArea addressField;
	private TextField pincodeField;

	private int studentId;
	private Student student;

	private StudentService studentService;
	private AddressService addressService;

	private StudentStateEditor stateEditor;

	private Dialog dialog;

	public AddressCard(StudentService studentService, AddressService addressService, AddressType addressType) {
		this.addressType = addressType;
		this.studentService = studentService;
		this.addressService = addressService;

		dialog = new Dialog();
		configureDialog();

		typeField = new TextField();
		configureTypeField();

		stateField = new TextField("State");
		configureStateField();

		districtField = new TextField("District");
		configureDistrictField();

		addressField = new TextArea("Address");
		configureAddressField();

		pincodeField = new TextField("Pincode");
		configurePincodeField();

		VerticalLayout card = new VerticalLayout();
		card.addClassName("card");
		card.setAlignItems(Alignment.CENTER);
		card.add(typeField, addressField, districtField, stateField, pincodeField);

		add(card);
	}

	private void configureTypeField() {
		typeField.setWidthFull();
		typeField.setReadOnly(true);
	}

	private void configureStateField() {
		stateField.setWidthFull();
		stateField.setReadOnly(true);
		stateField.getElement().addEventListener("dblclick", e -> {
			dialog.open();
//			if (stateEditor == null) {
//				stateEditor = new StudentStateEditor(studentService, addressService);
//				stateEditor.addListener(StudentNameEditor.NameUpdatedEvent.class, this::handleNameUpdatedEvent);
//			}
//			dialog.removeAll();
//			dialog.add(stateEditor);
//			stateEditor.setStudent(student);
		});
	}

	private void configureDistrictField() {
		districtField.setWidthFull();
		districtField.setReadOnly(true);
	}

	private void configureAddressField() {
		addressField.setWidthFull();
		addressField.setReadOnly(true);
		addressField.getElement().getStyle().set("minHeight", "120px");
		addressField.getElement().addEventListener("dblclick", e -> {
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

	private void configurePincodeField() {
		// TODO Auto-generated method stub
		pincodeField.setWidthFull();
		pincodeField.setReadOnly(true);
	}

	public void setStudentId(int id) {
		this.studentId = id;
		if (id == 0) {
			AddressCard.this.setEnabled(false);
		} else {
			AddressCard.this.setEnabled(true);
			reload();
		}
	}

	private void configureDialog() {
		// TODO Auto-generated method stub
		dialog.setWidth("300px");
		dialog.setModal(true);
		dialog.setDraggable(true);
	}

	private void handleStateUpdatedEvent(StudentStateEditor.StateUpdatedEvent event) {
		Notification.show("Student '" + event.getStudent().getName() + "' updated.", 3000, Position.TOP_CENTER);
		reload();
	}

	private void reload() {
		student = studentService.getStudentById(studentId);

		District district = null;
		State state = null;

		if (addressType == AddressType.Permanent) {
			typeField.setValue("Permanent");

			district = student.getPermanentDistrict();
			if (district != null) {
				districtField.setValue(district.getName());
				state = district.getState();
			}

			if (state != null) {
				stateField.setValue(state.getName());
			}

			if (student.getPermanentAddress() != null) {
				addressField.setValue(student.getPermanentAddress());
			}

			if (student.getPermanentPinCode() != null) {
				pincodeField.setValue(student.getPermanentPinCode());
			}

		} else if (addressType == AddressType.Correspondence) {
			typeField.setValue("Correspondence");
		} else if (addressType == AddressType.Local_Guardian) {
			typeField.setValue("Local guardian");
		}

	}

}
