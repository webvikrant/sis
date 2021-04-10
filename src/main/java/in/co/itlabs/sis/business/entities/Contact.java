package in.co.itlabs.sis.business.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Contact {

	public enum Type {
		Student, Mother, Father, Local_Guardian
	}

	private int studentId;
	private Type type;
	private String name;
	private String mobileNo;
	private String whatsappNo;
	private String emailId;
}
