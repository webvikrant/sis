package in.co.itlabs.sis.business.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Session {
	private int id;
	private int year;
	private String name;
}
