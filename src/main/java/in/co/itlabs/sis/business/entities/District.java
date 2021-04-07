package in.co.itlabs.sis.business.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class District {
	private int id;
	private String name;
	private State state;
}