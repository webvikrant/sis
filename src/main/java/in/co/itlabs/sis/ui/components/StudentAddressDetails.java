package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.helpers.AddressType;
import in.co.itlabs.sis.business.services.AddressService;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentAddressDetails extends VerticalLayout {

	private AddressCard permanentAddressCard;
	private AddressCard correspondenceAddressCard;
	private AddressCard localGuardianAddressCard;

	private int id;
	private Student student;

	private AddressService addressService;
	private StudentService studentService;

//	private StudentGenderEditor genderEditor;
	private Dialog dialog;

//	private final List<String> messages = new ArrayList<>();

	public StudentAddressDetails(StudentService studentService, AddressService addressService) {

		this.studentService = studentService;
		this.addressService = addressService;

		setPadding(false);
		setSpacing(false);

		dialog = new Dialog();
		configureDialog();

		permanentAddressCard = new AddressCard(studentService, addressService, AddressType.Permanent);
		configureAddressCard(permanentAddressCard);

		correspondenceAddressCard = new AddressCard(studentService, addressService, AddressType.Correspondence);
		configureAddressCard(correspondenceAddressCard);

		localGuardianAddressCard = new AddressCard(studentService, addressService, AddressType.Local_Guardian);
		configureAddressCard(localGuardianAddressCard);

		FlexLayout flex = new FlexLayout();
		configureFlex(flex);

		flex.add(permanentAddressCard, correspondenceAddressCard, localGuardianAddressCard);

		add(flex);

	}

	private void configureAddressCard(AddressCard addressCard) {
		addressCard.setWidth("230px");
	}

	private void configureFlex(FlexLayout flexLayout) {
		flexLayout.setFlexWrap(FlexWrap.WRAP);
//		flexLayout.setAlignItems(Alignment.END);
		flexLayout.getElement().getStyle().set("padding", "2px");
		flexLayout.getElement().getStyle().set("gap", "2px");

	}

	public void setStudentId(int id) {
		this.id = id;
		if (id == 0) {
			StudentAddressDetails.this.setVisible(false);
		} else {
			StudentAddressDetails.this.setVisible(true);
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
		permanentAddressCard.setStudentId(id);
		correspondenceAddressCard.setStudentId(id);
		localGuardianAddressCard.setStudentId(id);
	}

//	private void handleGenderUpdatedEvent(StudentGenderEditor.GenderUpdatedEvent event) {
//		Notification.show("Student '" + event.getStudent().getName() + "' updated.", 3000, Position.TOP_CENTER);
//		reload();
//	}
}