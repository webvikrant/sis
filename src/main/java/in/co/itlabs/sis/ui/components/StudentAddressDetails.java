package in.co.itlabs.sis.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;

import in.co.itlabs.sis.business.entities.Address;
import in.co.itlabs.sis.business.entities.District;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AddressService;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentAddressDetails extends VerticalLayout {

	private AddressEditor permanentAddressEditor;
	private Checkbox editPermanentCheck;

	private AddressEditor correspondenceAddressEditor;
	private Checkbox editCorrespondenceCheck;

	private AddressEditor localGuardianAddressEditor;
	private Checkbox editLocalGuardianCheck;

	private int studentId;
	private Student student;

	private AddressService addressService;
	private StudentService studentService;

	private final List<String> messages = new ArrayList<>();

	public StudentAddressDetails(StudentService studentService, AddressService addressService) {

		this.studentService = studentService;
		this.addressService = addressService;

		setPadding(true);
		setSpacing(true);

		permanentAddressEditor = new AddressEditor(addressService);
		configureAddressEditor(permanentAddressEditor);

		editPermanentCheck = new Checkbox("Edit");
		configurePermanentCheck();

		correspondenceAddressEditor = new AddressEditor(addressService);
		configureAddressEditor(correspondenceAddressEditor);

		editCorrespondenceCheck = new Checkbox("Edit");
		configureCorrespondenceCheck();

		localGuardianAddressEditor = new AddressEditor(addressService);
		configureAddressEditor(localGuardianAddressEditor);

		editLocalGuardianCheck = new Checkbox("Edit");
		configureLocalGuardianCheck();

		FlexLayout permanent = new FlexLayout();
		permanent.setFlexDirection(FlexDirection.COLUMN);
		permanent.add(editPermanentCheck, permanentAddressEditor);

		FlexLayout correspondence = new FlexLayout();
		correspondence.setFlexDirection(FlexDirection.COLUMN);
		correspondence.add(editCorrespondenceCheck, correspondenceAddressEditor);

		FlexLayout localGuardian = new FlexLayout();
		localGuardian.setFlexDirection(FlexDirection.COLUMN);
		localGuardian.add(editLocalGuardianCheck, localGuardianAddressEditor);

		FlexLayout flex = new FlexLayout();
		configureFlex(flex);

		flex.add(permanent, correspondence, localGuardian);

		add(flex);

		permanentAddressEditor.addListener(AddressEditor.SaveEvent.class, this::handleSaveAddressEvent);
		permanentAddressEditor.addListener(AddressEditor.CancelEvent.class, this::handleCancelAddressEvent);

		correspondenceAddressEditor.addListener(AddressEditor.SaveEvent.class, this::handleSaveAddressEvent);
		correspondenceAddressEditor.addListener(AddressEditor.CancelEvent.class, this::handleCancelAddressEvent);

		localGuardianAddressEditor.addListener(AddressEditor.SaveEvent.class, this::handleSaveAddressEvent);
		localGuardianAddressEditor.addListener(AddressEditor.CancelEvent.class, this::handleCancelAddressEvent);

	}

	private void configureAddressEditor(AddressEditor addressEditor) {
		addressEditor.setWidth("210px");
		addressEditor.addClassName("card");
		addressEditor.setEditingEnabled(false);
	}

	private void configureFlex(FlexLayout flexLayout) {
		flexLayout.setFlexWrap(FlexWrap.WRAP);
		flexLayout.getElement().getStyle().set("padding", "16px");
		flexLayout.getElement().getStyle().set("gap", "16px");
	}

	public void setStudentId(int id) {
		this.studentId = id;
		if (id == 0) {
			StudentAddressDetails.this.setVisible(false);
		} else {
			StudentAddressDetails.this.setVisible(true);
			reload();
		}
	}

	private void configurePermanentCheck() {
		editPermanentCheck.addValueChangeListener(e -> {
			permanentAddressEditor.setEditingEnabled(e.getValue());
		});
	}

	private void configureCorrespondenceCheck() {
		editCorrespondenceCheck.addValueChangeListener(e -> {
			correspondenceAddressEditor.setEditingEnabled(e.getValue());
		});
	}

	private void configureLocalGuardianCheck() {
		editLocalGuardianCheck.addValueChangeListener(e -> {
			localGuardianAddressEditor.setEditingEnabled(e.getValue());
		});
	}

	private void reload() {
		student = studentService.getStudentById(studentId);

		Address permanentAddress = new Address();
		Address correspondenceAddress = new Address();
		Address localGuardianAddress = new Address();

		District permanentDistrict = addressService.getDistrict(student.getPermanentDistrictId());
		permanentAddress.setDistrict(permanentDistrict);
		if (permanentDistrict != null) {
			permanentAddress.setState(permanentDistrict.getState());
		}
		permanentAddress.setDistrictId(student.getPermanentDistrictId());
		permanentAddress.setAddress(student.getPermanentAddress());
		permanentAddress.setPinCode(student.getPermanentPinCode());
		permanentAddress.setStudentId(studentId);
		permanentAddress.setType(Address.Type.Permanent);

		District correspondenceDistrict = addressService.getDistrict(student.getCorrespondenceDistrictId());
		correspondenceAddress.setDistrict(correspondenceDistrict);
		if (correspondenceDistrict != null) {
			correspondenceAddress.setState(correspondenceDistrict.getState());
		}
		correspondenceAddress.setDistrictId(student.getCorrespondenceDistrictId());
		correspondenceAddress.setAddress(student.getCorrespondenceAddress());
		correspondenceAddress.setPinCode(student.getCorrespondencePinCode());
		correspondenceAddress.setStudentId(studentId);
		correspondenceAddress.setType(Address.Type.Correspondence);

		District localGuardianDistrict = addressService.getDistrict(student.getPermanentDistrictId());
		localGuardianAddress.setDistrict(localGuardianDistrict);
		if (localGuardianDistrict != null) {
			localGuardianAddress.setState(localGuardianDistrict.getState());
		}
		localGuardianAddress.setDistrictId(student.getLocalGuardianDistrictId());
		localGuardianAddress.setAddress(student.getLocalGuardianAddress());
		localGuardianAddress.setPinCode(student.getLocalGuardianPinCode());
		localGuardianAddress.setStudentId(studentId);
		localGuardianAddress.setType(Address.Type.Local_Guardian);

		permanentAddressEditor.setAddress(permanentAddress);
		correspondenceAddressEditor.setAddress(correspondenceAddress);
		localGuardianAddressEditor.setAddress(localGuardianAddress);
	}

	public void handleSaveAddressEvent(AddressEditor.SaveEvent event) {
		Address address = event.getAddress();

		messages.clear();
		boolean success = false;

		switch (address.getType()) {
		case Permanent:
			success = studentService.updateStudentPermanentAddressDetails(messages, address);
			if (success) {
				Notification.show("Permanent address updated successfully", 3000, Position.TOP_CENTER);
				editPermanentCheck.setValue(false);
			}
			break;

		case Correspondence:
			success = studentService.updateStudentCorrespondenceAddressDetails(messages, address);
			if (success) {
				Notification.show("Correspondence address updated successfully", 3000, Position.TOP_CENTER);
				editCorrespondenceCheck.setValue(false);
			}
			break;

		case Local_Guardian:
			success = studentService.updateStudentLocalGuardianAddressDetails(messages, address);
			if (success) {
				Notification.show("Local guardian address updated successfully", 3000, Position.TOP_CENTER);
				editLocalGuardianCheck.setValue(false);
			}
			break;

		default:
			break;
		}

	}

	public void handleCancelAddressEvent(AddressEditor.CancelEvent event) {
		Address address = event.getAddress();
		switch (address.getType()) {
		case Permanent:
			editPermanentCheck.setValue(false);
			break;

		case Correspondence:
			editCorrespondenceCheck.setValue(false);
			break;

		case Local_Guardian:
			editLocalGuardianCheck.setValue(false);
			break;

		default:
			break;
		}
	}

}
