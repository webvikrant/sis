package in.co.itlabs.sis.business.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Exam {
	
	public enum Level{
		High_School, Intermediate, Diploma, Degree
	}
	
	private int id;
	private Level level;
	private String name;
}
