package in.co.itlabs.sis.business.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@DatabaseTable(tableName = "student")
public class Student {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField
	private String admissionId; // required, unique

	@DatabaseField(canBeNull = false)
	private String name; // required

	// foreign
	@DatabaseField(columnName = "sessionId", foreign = true)
	private Session session;

}
