package in.co.itlabs.sis.business.entities;

import java.time.LocalDate;
import java.util.List;

import in.co.itlabs.sis.business.helpers.AdmissionMode;
import in.co.itlabs.sis.business.helpers.Gender;
import in.co.itlabs.sis.business.helpers.Stage;
import in.co.itlabs.sis.business.helpers.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Student {

	private int id;

	// personal details
	private String name; // required
	private LocalDate birthDate; // required
	private int photoMediaFileId;
	private int signMediaFileId;
	private Gender gender;
	private int religionId;
	private int casteId;
	private int categoryId;
	private String fatherName;
	private String motherName;
	private String localGuardianName;
	private int relationId;
	private int fatherOccupationId;
	private int motherOccupationId;
	private String aadhaarNo;
	private String mobileNo;
	private String whatsappNo;
	private String emailId;
	private String fatherMobileNo;
	private String fatherWhatsappNo;
	private String fatherEmailId;
	private String motherMobileNo;
	private String motherWhatsappNo;
	private String motherEmailId;
	private String localGuardianMobileNo;
	private String localGuardianWhatsappNo;
	private String localGuardianEmailId;
	private String permanentAddress;
	private String permanentPinCode;
	private int permanentDistrictId;
	private String correspondenceAddress;
	private String correspondencePinCode;
	private int correspondenceDistrictId;
	private String localGuardianAddress;
	private String localGuardianPinCode;
	private int localGuardianDistrictId;

	// admission details

	private String admissionId; // required, unique
	private LocalDate admissionDate;
	private AdmissionMode admissionMode;
	private String upseeRollNo;
	private int upseeRank;

	// academic details

	private String rollNo;
	private boolean hostel;
	private int sessionId;
	private int programId;
	private Stage stage;
	private Status status;

	// transient fields
	private Session session;
	private Program program;
	private State state;
	private District district;
	private District permanentDistrict;
	private District correspondenceDistrict;
	private District localGuardianDistrict;

	public void clear() {
		name = null;
		admissionId = null;
	}

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String ADMISSION_ID = "admissionId";

	public static List<String> columns() {
		List<String> columns = List.of(ID, NAME, ADMISSION_ID);
		return columns;
	}
}
