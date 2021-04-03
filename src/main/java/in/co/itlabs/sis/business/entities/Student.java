package in.co.itlabs.sis.business.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue
	private long id;

	@Column
	private String admissionId; // required, unique

	@Column
	private String name; // required

	// foreign
	@Column
	private long sessionId;
}
