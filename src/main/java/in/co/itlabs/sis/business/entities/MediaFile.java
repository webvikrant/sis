package in.co.itlabs.sis.business.entities;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MediaFile {
	public enum Label {
		Photograph, Signature, HighSchool_MarksSheet, HighSchool_Certificate
	}

	private int id;
	private int studentId;

	private Label label;

	private String fileName;
	private String fileMime;
	private byte[] fileBytes;
	private LocalDateTime createdAt;

	public void clear() {
		id = 0;
		studentId = 0;
		label = null;
		fileName = null;
		fileMime = null;
		fileBytes = null;
		createdAt = null;
	}
}
