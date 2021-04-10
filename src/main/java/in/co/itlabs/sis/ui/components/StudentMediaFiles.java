package in.co.itlabs.sis.ui.components;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;

import in.co.itlabs.sis.business.entities.MediaFile;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.MediaService;
import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.components.editors.MediaFileEditor;

public class StudentMediaFiles extends VerticalLayout {

	private Button createMediaFileButton;
	private VerticalLayout content;

	private int studentId;
	private Student student;

	private StudentService studentService;
	private MediaService mediaService;

	private MediaFile mediaFile;
	private MediaFileEditor mediaFileEditor;
	private Dialog dialog;

	public StudentMediaFiles(StudentService studentService, MediaService mediaService) {
		this.studentService = studentService;
		this.mediaService = mediaService;

		createMediaFileButton = new Button("Add", VaadinIcon.PLUS.create());
		configureCreateMediaFileButton();

		content = new VerticalLayout();
		configureContent();

		FlexLayout actionBar = new FlexLayout();
		actionBar.setWidthFull();
		actionBar.setJustifyContentMode(JustifyContentMode.END);

		actionBar.add(createMediaFileButton);

		add(actionBar, content);

		// dialog related
		mediaFileEditor = new MediaFileEditor();

		mediaFileEditor.addListener(MediaFileEditor.SaveEvent.class, this::handleSaveEvent);
		mediaFileEditor.addListener(MediaFileEditor.CancelEvent.class, this::handleCancelEvent);

		mediaFile = new MediaFile();
		dialog = new Dialog();
		configureDialog();
	}

	private void configureContent() {
		content.setPadding(false);
	}

	private void configureTextField(TextField textField) {
		textField.setWidth("100px");
		textField.setReadOnly(true);
	}

	private void configureFlex(FlexLayout flexLayout) {
		flexLayout.setFlexWrap(FlexWrap.WRAP);
		flexLayout.getElement().getStyle().set("padding", "8px");
		flexLayout.getElement().getStyle().set("gap", "8px");
	}

	public void setStudentId(int id) {
		this.studentId = id;
		if (id == 0) {
			StudentMediaFiles.this.setVisible(false);
		} else {
			StudentMediaFiles.this.setVisible(true);
			reload();
		}
	}

	private void configureCreateMediaFileButton() {
		createMediaFileButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

		createMediaFileButton.addClickListener(e -> {
			dialog.removeAll();
			dialog.add(mediaFileEditor);
			dialog.open();
			mediaFile.setStudentId(studentId);
			mediaFileEditor.setMediaFile(mediaFile);
		});
	}

	private void configureDialog() {
		// TODO Auto-generated method stub
		dialog.setWidth("400px");
		dialog.setModal(true);
		dialog.setDraggable(true);
	}

	private void reload() {
		student = studentService.getStudentById(studentId);

		content.removeAll();
		
		List<MediaFile> mediaFiles = mediaService.getMediaFiles(studentId);
		for (MediaFile mediaFile : mediaFiles) {
			FileField fileField = new FileField();

			byte[] fileBytes = mediaFile.getFileBytes();
			StreamResource resource = new StreamResource(mediaFile.getFileName(),
					() -> new ByteArrayInputStream(fileBytes));
			fileField.setResource(resource);
			content.add(fileField);
		}
	}

	public void handleSaveEvent(MediaFileEditor.SaveEvent event) {
		MediaFile mediaFile = event.getMediaFile();

		if (mediaFile.getId() == 0) {
// 		create new
			int id = mediaService.createMediaFile(event.getMediaFile());
			if (id > 0) {
				Notification.show("Media file created successfully", 3000, Position.TOP_CENTER);
				mediaFile.clear();
				mediaFile.setStudentId(studentId);
				mediaFileEditor.setMediaFile(mediaFile);
				reload();
			}
		} else {
// 		update existing
			boolean success = false;
//			academicService.updateAcademicQualification(event.getAcademicQualification());
		}
	}

	public void handleCancelEvent(MediaFileEditor.CancelEvent event) {
		dialog.close();
	}

}
