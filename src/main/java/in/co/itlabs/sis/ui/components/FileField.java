package in.co.itlabs.sis.ui.components;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.google.common.io.ByteStreams;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamResource;

public class FileField extends VerticalLayout {
	private final Image image;
	private final MemoryBuffer buffer;
	private final Upload upload;
	private byte[] fileBytes;
	private String fileMime;
	private String fileName;

	public FileField() {

		image = new Image();

		buffer = new MemoryBuffer();
		upload = new Upload(buffer);


		add(image, upload);
		configureImage();

		// listeners
		upload.addStartedListener(event -> {
			System.out.println("Started");
		});

		upload.addFinishedListener(event -> {
			System.out.println("Finished");
		});

		upload.addFailedListener(event -> {
			System.out.println("Failed - " + event.getReason().getMessage());
		});

		upload.addSucceededListener(event -> {
			System.out.println("Succeeded");
			try {
				fileBytes = ByteStreams.toByteArray(buffer.getInputStream());
				StreamResource resource = new StreamResource(buffer.getFileName(),
						() -> new ByteArrayInputStream(fileBytes));
				image.setSrc(resource);
				fileMime = event.getMIMEType();
				fileName = event.getFileName();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				Notification.show(e.getMessage(), 3000, Position.TOP_CENTER);
			}
		});

	}

	private void configureImage() {
//		image.addClassName("photo");
		image.setWidthFull();
		image.setHeight("100px");

		image.getStyle().set("objectFit", "contain");
	}

	public void setReadOnly(boolean readOnly) {
		upload.setVisible(!readOnly);
	}

	public void setResource(StreamResource resource, String mime, String name) {
		if (resource != null) {
			image.setSrc(resource);
			if (name != null) {
				image.setAlt(name);
			}
		}
	}

	public void setBase64(String base64, String mime, String name) {
		if (base64 != null && mime != null) {
			image.setSrc(String.format("data:" + mime + ";base64,%s", base64));
			if (name != null) {
				image.setAlt(name);
			}
		}
	}

	public void clear() {
	}

	public Upload getUpload() {
		return upload;
	}

	public byte[] getFileBytes() {
		return fileBytes;
	}

	public String getFileMime() {
		return fileMime;
	}

	public String getFileName() {
		return fileName;
	}
}
