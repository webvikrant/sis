package in.co.itlabs.sis.business.entities;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import in.co.itlabs.sis.business.helpers.AdmissionMode;
import in.co.itlabs.sis.business.helpers.Gender;
import in.co.itlabs.sis.business.helpers.Stage;
import in.co.itlabs.sis.business.helpers.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@DatabaseTable(tableName = "student")
public class Student {

	@DatabaseField(generatedId = true)
	private int id;

	// personal details

	@DatabaseField(canBeNull = false)
	private String name; // required

	@DatabaseField
	private Date birthDate; // required

	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] photo;
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] signature;

	@DatabaseField
	private Gender gender;
	
	@DatabaseField(columnName = "religionId", foreign = true)
	private Religion religion;
	
	@DatabaseField(columnName = "casteId", foreign = true)
	private Caste caste;
	
	@DatabaseField(columnName = "categoryId", foreign = true)
	private Category category;

	@DatabaseField
	private String fatherName;
	
	@DatabaseField
	private String motherName;
	
	@DatabaseField
	private String localGuardianName;
	
	@DatabaseField(columnName = "relationId", foreign = true)
	private Relation localGuardianRelation;
	
	@DatabaseField(columnName = "fatherOccupationId", foreign = true)
	private Occupation fatherOccupation;
	
	@DatabaseField(columnName = "motherOccupationId", foreign = true)
	private Occupation motherOccupation;

	@DatabaseField
	private String aadhaarNo;

	@DatabaseField
	private String mobileNo;
	
	@DatabaseField
	private String whatsappNo;
	
	@DatabaseField
	private String emailId;

	@DatabaseField
	private String fatherMobileNo;
	
	@DatabaseField
	private String fatherWhatsappNo;
	
	@DatabaseField
	private String fatherEmailId;

	@DatabaseField
	private String motherMobileNo;
	
	@DatabaseField
	private String motherWhatsappNo;
	
	@DatabaseField
	private String motherEmailId;

	@DatabaseField
	private String localGuardianMobileNo;
	
	@DatabaseField
	private String localGuardianWhatsappNo;
	
	@DatabaseField
	private String localGuardianEmailId;

	@DatabaseField
	private String permanentAddress;
	
	@DatabaseField
	private String permanentPinCode;
	
	@DatabaseField(columnName = "permanentDistrictId", foreign = true)
	private District permanentDistrict;

	@DatabaseField
	private String correspondenceAddress;
	
	@DatabaseField
	private String correspondencePinCode;
	
	@DatabaseField(columnName = "correspondenceDistrictId", foreign = true)
	private District correspondenceDistrict;

	@DatabaseField
	private String localGuardianAddress;
	
	@DatabaseField
	private String localGuardianPinCode;
	
	@DatabaseField(columnName = "localGuardianDistrictId", foreign = true)
	private District localGuardianDistrict;

	// admission details
	
	@DatabaseField
	private String admissionId; // required, unique
	
	@DatabaseField
	private Date admissionDate;
	
	@DatabaseField
	private AdmissionMode admissionMode;
	
	@DatabaseField
	private String upseeRollNo;
	
	@DatabaseField
	private int upseeRank;

	// academic details
	
	@DatabaseField
	private String rollNo;
	
	@DatabaseField
	private boolean hostel;

	@DatabaseField(columnName = "sessionId", foreign = true, foreignAutoRefresh = true)
	private Session session;
	
	@DatabaseField(columnName = "programId", foreign = true)
	private Program program;
	
	@DatabaseField
	private Stage stage;

	private Status status;
}
