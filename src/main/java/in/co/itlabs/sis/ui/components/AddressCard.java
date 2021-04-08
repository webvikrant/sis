package in.co.itlabs.sis.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.entities.Address;
import in.co.itlabs.sis.business.entities.Address.Type;
import in.co.itlabs.sis.business.entities.District;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AddressService;
import in.co.itlabs.sis.business.services.StudentService;

public class AddressCard extends VerticalLayout {

	private TextField typeField;
	private TextField stateField;
	private TextField districtField;
	private TextArea addressField;
	private TextField pincodeField;

	private int studentId;
	private Address.Type addressType;
	private Address address;

	private AddressService addressService;
	private StudentService studentService;

	private DistrictStateEditor districtStateEditor;

	private Dialog dialog;

	private final List<String> messages = new ArrayList<>();

	public AddressCard(AddressService addressService, StudentService studentService, Address.Type addressType) {
		this.addressService = addressService;
		this.studentService = studentService;
		this.addressType = addressType;

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
			if (districtStateEditor == null) {
				districtStateEditor = new DistrictStateEditor(addressService);
				districtStateEditor.addListener(DistrictStateEditor.SaveEvent.class, this::handleDistrictSaveEvent);
			}
			dialog.removeAll();
			dialog.add(districtStateEditor);
			districtStateEditor.setAddress(address);
		});
	}

	private void configureDistrictField() {
		districtField.setWidthFull();
		districtField.setReadOnly(true);
		districtField.getElement().addEventListener("dblclick", e -> {
			dialog.open();
			if (districtStateEditor == null) {
				districtStateEditor = new DistrictStateEditor(addressService);
				districtStateEditor.addListener(DistrictStateEditor.SaveEvent.class, this::handleDistrictSaveEvent);
			}
			dialog.removeAll();
			dialog.add(districtStateEditor);
			districtStateEditor.setAddress(address);
		});
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

	private void handleDistrictSaveEvent(DistrictStateEditor.SaveEvent event) {
		Address updatedAddress = event.getAddress();
		int districtId = 0;
		if (event.getAddress().getDistrict() != null) {
			districtId = event.getAddress().getDistrict().getId();
		}
		if (updatedAddress.getType() == Type.Permanent) {
			studentService.updateStudentPermanentDistrict(messages, updatedAddress.getStudentId(), districtId);
		} else if (updatedAddress.getType() == Type.Correspondence) {
			studentService.updateStudentCorrespondenceDistrict(messages, updatedAddress.getStudentId(), districtId);
		} else if (updatedAddress.getType() == Type.Local_Guardian) {
			studentService.updateStudentLocalGuardianDistrict(messages, updatedAddress.getStudentId(), districtId);
		}
		reload();
	}

	private void reload() {

		Student student = studentService.getStudentById(studentId);

		address = new Address();

		address.setStudentId(studentId);
		address.setType(addressType);

		District district = null;

		switch (addressType) {
		case Permanent:
			district = addressService.getDistrict(student.getPermanentDistrictId());

			address.setAddress(student.getPermanentAddress());
			address.setPinCode(student.getPermanentPinCode());
			address.setDistrict(district);
			address.setState(district.getState());

			break;

		case Correspondence:
			district = addressService.getDistrict(student.getCorrespondenceDistrictId());

			address.setAddress(student.getCorrespondenceAddress());
			address.setPinCode(student.getCorrespondencePinCode());
			address.setDistrict(district);
			address.setState(district.getState());

			break;

		case Local_Guardian:
			district = addressService.getDistrict(student.getLocalGuardianDistrictId());

			address.setAddress(student.getLocalGuardianAddress());
			address.setPinCode(student.getLocalGuardianPinCode());
			address.setDistrict(district);
			address.setState(district.getState());

			break;

		default:
			break;
		}

		if (addressType == Address.Type.Permanent) {
			typeField.setValue("Permanent");

		} else if (addressType == Address.Type.Correspondence) {
			typeField.setValue("Correspondence");

		} else if (addressType == Address.Type.Local_Guardian) {
			typeField.setValue("Local guardian");
		}

		if (address.getDistrict() != null) {
			districtField.setValue(address.getDistrict().getName());
		}

		if (address.getState() != null) {
			stateField.setValue(address.getState().getName());
		}

		if (address.getAddress() != null) {
			addressField.setValue(address.getAddress());
		}

		if (address.getPinCode() != null) {
			pincodeField.setValue(address.getPinCode());
		}
	}
}