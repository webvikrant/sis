package in.co.itlabs.sis.business.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {

	public enum Type {
		Permanent, Correspondence, Local_Guardian
	}

	private int studentId;
	private Type type;
	private String address;
	private District district;
	private State state;
	private String pinCode;
}
