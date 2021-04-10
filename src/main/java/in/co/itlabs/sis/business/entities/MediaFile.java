package in.co.itlabs.sis.business.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MediaFile {
	public enum Type {
		Photograph, Signature, HighSchool_MarksSheet, HighSchool_Certificate
	}

	private int id;
	private int studentId;

	private Type type;

	private String fileName;
	private String fileMime;
	private byte[] fileBytes;

	public void clear() {
		id = 0;
		studentId = 0;
		type = null;
		fileName = null;
		fileMime = null;
		fileBytes = null;
	}
}
