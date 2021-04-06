package in.co.itlabs.sis.business.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@DatabaseTable(tableName = "state")
public class State {
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private String code;
	
	@DatabaseField(canBeNull = false)
	private String name;
}
