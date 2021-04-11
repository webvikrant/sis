package in.co.itlabs.sis.ui.components;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;

import in.co.itlabs.sis.business.entities.MediaFile;
import in.co.itlabs.sis.business.entities.MediaFile.Label;
import in.co.itlabs.sis.business.entities.Program;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.helpers.Stage;
import in.co.itlabs.sis.business.services.MediaService;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentCard extends VerticalLayout {

	private Button pickPhotoMediaButton;

	private Image photo;
	private TextField nameField;
	private TextField admissionIdField;
	private TextField programField;
	private TextField statusField;
	private TextField stageField;

	private int studentId;
	private Student student;

	private StudentService studentService;
	private MediaService mediaService;

	private Dialog dialog;
	private MediaFilePicker photoPicker;
	private MediaFilePicker signPicker;

	private final List<String> messages = new ArrayList<>();

	public StudentCard(StudentService studentService, MediaService mediaService) {
		this.studentService = studentService;
		this.mediaService = mediaService;

		pickPhotoMediaButton = new Button("Set photo", VaadinIcon.PICTURE.create());
		configurePickPhotoMediaButton();

//		photo = new Image("https://picsum.photos/100/110", "photograph");
		photo = new Image();
		configurePhoto();

		nameField = new TextField("Name");
		configureNameField();

		admissionIdField = new TextField("Admission Id");
		configureAdmissionIdField();

		programField = new TextField("Program");
		configureProgramField();

		statusField = new TextField("Status");
		configureStatusField();

		stageField = new TextField("Stage");
		configureStageField();

		VerticalLayout card = new VerticalLayout();
		card.addClassName("card");
		card.setAlignItems(Alignment.CENTER);
		card.add(photo, nameField, admissionIdField, programField, statusField, stageField);

		add(pickPhotoMediaButton, card);

		// dialog related
		dialog = new Dialog();
		configureDialog();

		photoPicker = new MediaFilePicker(mediaService);
		photoPicker.addListener(MediaFilePicker.SaveEvent.class, this::handlePhotoSaveEvent);

	}

	private void configurePickPhotoMediaButton() {
		pickPhotoMediaButton.addClickListener(e -> {
			dialog.removeAll();
			dialog.add(photoPicker);
			dialog.open();

			photoPicker.setStudentId(studentId);
		});
	}

	private void configurePhoto() {
		photo.addClassName("photo");
		photo.getStyle().set("objectFit", "contain");
		photo.setHeight("130px");
	}

	private void configureNameField() {
		nameField.setWidthFull();
		nameField.setReadOnly(true);
	}

	private void configureAdmissionIdField() {
		admissionIdField.setWidthFull();
		admissionIdField.setReadOnly(true);
	}

	private void configureProgramField() {
		programField.setWidthFull();
		programField.setReadOnly(true);
	}

	private void configureStatusField() {
		// TODO Auto-generated method stub
		statusField.setWidthFull();
		statusField.setReadOnly(true);
	}

	private void configureStageField() {
		// TODO Auto-generated method stub
		stageField.setWidthFull();
		stageField.setReadOnly(true);
	}

	public void setStudentId(int id) {
		this.studentId = id;
		if (id == 0) {
			StudentCard.this.setEnabled(false);
		} else {
			StudentCard.this.setEnabled(true);
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
		student = studentService.getStudentById(studentId);

		MediaFile mediaFile = mediaService.getMediaFile(student.getPhotoMediaFileId());
		if (mediaFile != null) {
			byte[] imageBytes = mediaFile.getFileBytes();
			StreamResource resource = new StreamResource(mediaFile.getFileName(),
					() -> new ByteArrayInputStream(imageBytes));
			photo.setSrc(resource);
		} else {
			photo.setSrc("https://picsum.photos/100/110");
		}

		nameField.setValue(student.getName());
		admissionIdField.setValue(student.getAdmissionId());

		Program program = student.getProgram();
		if (program != null) {
			programField.setValue(program.getName());
		}

		Stage stage = student.getStage();
		if (stage != null) {
			stageField.setValue(stage.name());
		}
	}

	private void handlePhotoSaveEvent(MediaFilePicker.SaveEvent event) {
		MediaFile mediaFile = event.getMediaFile();
		boolean success = studentService.updateStudentPhotoMediaFileId(messages, studentId, mediaFile.getId());
		if (success) {
			Notification.show("Photograph set successfully", 3000, Position.TOP_CENTER);
			reload();
		} else {
			Notification.show(messages.toString(), 3000, Position.TOP_CENTER);
		}
	}

}
