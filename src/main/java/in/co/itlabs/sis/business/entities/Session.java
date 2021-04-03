package in.co.itlabs.sis.business.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="session")
public class Session {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="name")
	private String name;
}
