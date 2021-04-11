package in.co.itlabs.sis.ui.components;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import in.co.itlabs.sis.business.entities.MediaFile;
import in.co.itlabs.sis.business.services.MediaService;
import in.co.itlabs.sis.ui.components.editors.MediaFileEditor;

public class StudentMediaFiles extends VerticalLayout {

	private Button createMediaFileButton;
	private Grid<MediaFile> grid;

	private int studentId;
//	private Student student;

	private MediaService mediaService;

	private MediaFile mediaFile;
	private MediaFileEditor mediaFileEditor;
	private Dialog dialog;

	public StudentMediaFiles(MediaService mediaService) {
		this.mediaService = mediaService;

		createMediaFileButton = new Button("Add", VaadinIcon.PLUS.create());
		configureCreateMediaFileButton();

		FlexLayout actionBar = new FlexLayout();
		actionBar.setWidthFull();
		actionBar.setJustifyContentMode(JustifyContentMode.END);

		actionBar.add(createMediaFileButton);

		grid = new Grid<>(MediaFile.class);

		add(actionBar, grid);
		configureGrid();

		// dialog related
		mediaFileEditor = new MediaFileEditor();

		mediaFileEditor.addListener(MediaFileEditor.SaveEvent.class, this::handleSaveEvent);
		mediaFileEditor.addListener(MediaFileEditor.CancelEvent.class, this::handleCancelEvent);

		mediaFile = new MediaFile();
		dialog = new Dialog();
		configureDialog();
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

	private void configureGrid() {
		grid.removeAllColumns();

		grid.addComponentColumn(mediaFile -> {
			Image photo = new Image();
			photo.addClassName("photo");
			photo.getStyle().set("objectFit", "contain");
			photo.setHeight("50px");

			if (mediaFile != null) {
				byte[] imageBytes = mediaFile.getFileBytes();
				StreamResource resource = new StreamResource(mediaFile.getFileName(),
						() -> new ByteArrayInputStream(imageBytes));
				photo.setSrc(resource);
			}

			return photo;

		}).setHeader("Media");

		grid.addColumn("label").setHeader("Label");
		grid.addColumn("fileName").setHeader("File name");

		grid.getColumns().forEach(column -> {
			column.setAutoWidth(true);
		});
	}

	private void configureDialog() {
		// TODO Auto-generated method stub
		dialog.setWidth("400px");
		dialog.setModal(true);
		dialog.setDraggable(true);
	}

	private void reload() {
		grid.setItems(mediaService.getMediaFiles(studentId));
	}

	public void handleSaveEvent(MediaFileEditor.SaveEvent event) {
		MediaFile mediaFile = event.getMediaFile();

		if (mediaFile.getId() == 0) {
// 		create new
			mediaFile.setCreatedAt(LocalDateTime.now());
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
//			boolean success = false;
//			academicService.updateAcademicQualification(event.getAcademicQualification());
		}
	}

	public void handleCancelEvent(MediaFileEditor.CancelEvent event) {
		dialog.close();
	}

}
