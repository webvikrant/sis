package in.co.itlabs.sis.business.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {

	public enum Type {
		Permanent, Correspondence, Local_Guardian
	}

	private int id;
	private int studentId;

	private Type type;
	private String address;
	private int districtId;
	private String pinCode;

	// transient
	private District district;
	private State state;
}
