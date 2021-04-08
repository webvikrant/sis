package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import in.co.itlabs.sis.business.entities.Contact;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentContactDetails extends VerticalLayout {

	private ContactCard studentContactCard;
	private ContactCard motherContactCard;
	private ContactCard fatherContactCard;
	private ContactCard localGuardianContactCard;

	private int id;
//	private Student student;

//	private StudentService studentService;

	private Dialog dialog;

	public StudentContactDetails(StudentService studentService) {

		dialog = new Dialog();
		configureDialog();

		studentContactCard = new ContactCard(studentService, Contact.Type.Student);
		configureContactCard(studentContactCard);

		motherContactCard = new ContactCard(studentService, Contact.Type.Mother);
		configureContactCard(motherContactCard);

		fatherContactCard = new ContactCard(studentService, Contact.Type.Father);
		configureContactCard(fatherContactCard);

		localGuardianContactCard = new ContactCard(studentService, Contact.Type.Local_Guardian);
		configureContactCard(localGuardianContactCard);

		add(studentContactCard, new Hr(), motherContactCard, new Hr(), fatherContactCard, new Hr(),
				localGuardianContactCard);

	}

	private void configureContactCard(ContactCard contactCard) {
//		contactCard.setWidth("230px");
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
//		student = studentService.getStudentById(id);
		studentContactCard.setStudentId(id);
		motherContactCard.setStudentId(id);
		fatherContactCard.setStudentId(id);
		localGuardianContactCard.setStudentId(id);
	}

}
