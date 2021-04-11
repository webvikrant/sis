package in.co.itlabs.sis.ui.components.editors;

import java.io.ByteArrayInputStream;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.shared.Registration;

import in.co.itlabs.sis.business.entities.MediaFile;
import in.co.itlabs.sis.business.entities.MediaFile.Type;
import in.co.itlabs.sis.ui.components.FileField;

public class MediaFileEditor extends VerticalLayout implements Editor {

	private ComboBox<Type> typeCombo;
	private FileField fileField;

	private Button saveButton;
	private Button cancelButton;

	private Binder<MediaFile> binder;

	public MediaFileEditor() {

		typeCombo = new ComboBox<Type>("Type");
		configureTypeCombo();

		fileField = new FileField();
		configureFileField();

		binder = new Binder<>(MediaFile.class);

		binder.forField(typeCombo).asRequired("Type can not be blank").bind("type");
//		binder.forField(fileField).asRequired("File can not be blank").bind("fileMime");

		saveButton = new Button("OK", VaadinIcon.CHECK.create());
		cancelButton = new Button("Cancel", VaadinIcon.CLOSE.create());

		HorizontalLayout actionBar = buildActionBar();
		actionBar.setWidthFull();

		add(typeCombo, fileField, actionBar);

	}

	private void configureTypeCombo() {
		typeCombo.setWidthFull();
		typeCombo.setItems(Type.values());
	}

	private void configureFileField() {
		fileField.setWidthFull();
		fileField.setPadding(false);
		
		Upload upload = fileField.getUpload();
		upload.setAutoUpload(true);
		upload.setMaxFiles(1);
		upload.setDropLabel(new Label("Upload a 512 KB file (JPEG or PNG)"));
		upload.setAcceptedFileTypes("image/jpeg", "image/png");
		upload.setMaxFileSize(1024 * 512);
	}

	public void setMediaFile(MediaFile mediaFile) {
		binder.setBean(mediaFile);

		// are we creating a new mdeia file or editing an existing one
		if (mediaFile.getId() == 0) {
			// new media file

		} else {
			// existing media file
			byte[] fileBytes = mediaFile.getFileBytes();
			StreamResource resource = new StreamResource(mediaFile.getFileName(),
					() -> new ByteArrayInputStream(fileBytes));
			fileField.setResource(resource, mediaFile.getFileMime(), mediaFile.getFileName());
		}
	}

	private HorizontalLayout buildActionBar() {
		HorizontalLayout root = new HorizontalLayout();

		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton.addClickListener(e -> {
			if (binder.validate().isOk()) {

				String fileName = fileField.getFileName();
				String fileMime = fileField.getFileMime();
				byte[] fileBytes = fileField.getFileBytes();

				if (fileName != null && fileMime != null && fileBytes != null) {
					binder.getBean().setFileName(fileName);
					binder.getBean().setFileMime(fileMime);
					binder.getBean().setFileBytes(fileBytes);

					fireEvent(new SaveEvent(this, binder.getBean()));
				}
			}
		});

		cancelButton.addClickListener(e -> {
			fireEvent(new CancelEvent(this, binder.getBean()));
		});

		Span blank = new Span();

		root.add(saveButton, blank, cancelButton);
		root.expand(blank);

		return root;
	}

	@Override
	public void setEditingEnabled(boolean enabled) {
		typeCombo.setReadOnly(!enabled);
		fileField.setReadOnly(!enabled);

		saveButton.setVisible(enabled);
		cancelButton.setVisible(enabled);

	}

	public static abstract class MediaFileEvent extends ComponentEvent<MediaFileEditor> {
		private MediaFile mediaFile;

		protected MediaFileEvent(MediaFileEditor source, MediaFile mediaFile) {

			super(source, false);
			this.mediaFile = mediaFile;
		}

		public MediaFile getMediaFile() {
			return mediaFile;
		}
	}

	public static class SaveEvent extends MediaFileEvent {
		SaveEvent(MediaFileEditor source, MediaFile mediaFile) {
			super(source, mediaFile);
		}
	}

	public static class CancelEvent extends MediaFileEvent {
		CancelEvent(MediaFileEditor source, MediaFile mediaFile) {
			super(source, mediaFile);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {

		return getEventBus().addListener(eventType, listener);
	}
}