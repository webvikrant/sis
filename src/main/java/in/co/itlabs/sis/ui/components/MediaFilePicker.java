package in.co.itlabs.sis.ui.components;

import java.io.ByteArrayInputStream;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.shared.Registration;

import in.co.itlabs.sis.business.entities.MediaFile;
import in.co.itlabs.sis.business.services.MediaService;

public class MediaFilePicker extends VerticalLayout {

	private ComboBox<MediaFile> mediaFileCombo;
	private Image image;
	private Button saveButton;
	private Button cancelButton;

	private MediaService mediaService;

	private int studentId;

	public MediaFilePicker(MediaService mediaService) {
		this.mediaService = mediaService;

		setPadding(false);

		mediaFileCombo = new ComboBox<MediaFile>();
		configureMediaFileCombo();

		image = new Image();
		configureImage();

		saveButton = new Button("OK", VaadinIcon.CHECK.create());
		cancelButton = new Button("Cancel", VaadinIcon.CLOSE.create());

		HorizontalLayout actionBar = buildActionBar();
		actionBar.setWidthFull();

		add(mediaFileCombo, image, actionBar);
	}

	private void configureImage() {
		image.addClassName("photo");
		image.getStyle().set("objectFit", "contain");
		image.setHeight("130px");

	}

	private void configureMediaFileCombo() {
		mediaFileCombo.setWidthFull();
		mediaFileCombo.setItemLabelGenerator(mediaFile -> {
			return mediaFile.getLabel() + " - " + mediaFile.getFileName();
		});

		mediaFileCombo.addValueChangeListener(e -> {
			MediaFile mediaFile = e.getValue();
			if (mediaFile != null) {
				byte[] imageBytes = mediaFile.getFileBytes();
				StreamResource resource = new StreamResource(mediaFile.getFileName(),
						() -> new ByteArrayInputStream(imageBytes));
				image.setSrc(resource);
			}

		});
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
		reload();
	}

	private void reload() {
		mediaFileCombo.setItems(mediaService.getMediaFiles(studentId));
	}

	private HorizontalLayout buildActionBar() {
		HorizontalLayout root = new HorizontalLayout();

		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton.addClickListener(e -> {
			if (mediaFileCombo.getValue() != null) {
				fireEvent(new SaveEvent(this, mediaFileCombo.getValue()));
			}
		});

		cancelButton.addClickListener(e -> {
			fireEvent(new CancelEvent(this, null));
		});

		Span blank = new Span();

		root.add(saveButton, blank, cancelButton);
		root.expand(blank);

		return root;
	}

	public static abstract class MediaEvent extends ComponentEvent<MediaFilePicker> {
		private MediaFile mediaFile;

		protected MediaEvent(MediaFilePicker source, MediaFile mediaFile) {

			super(source, false);
			this.mediaFile = mediaFile;
		}

		public MediaFile getMediaFile() {
			return mediaFile;
		}
	}

	public static class SaveEvent extends MediaEvent {
		SaveEvent(MediaFilePicker source, MediaFile mediaFile) {
			super(source, mediaFile);
		}
	}

	public static class CancelEvent extends MediaEvent {
		CancelEvent(MediaFilePicker source, MediaFile mediaFile) {
			super(source, mediaFile);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {

		return getEventBus().addListener(eventType, listener);
	}
}
