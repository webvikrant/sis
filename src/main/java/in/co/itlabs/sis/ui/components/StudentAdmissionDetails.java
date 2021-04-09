package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.entities.AcademicQualification;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;

public class StudentAdmissionDetails extends VerticalLayout {

	private TextField sessionField;

	private TextField admissionProgramField;
	private TextField admissionStageField;
	private TextField admissionIdField;
	private TextField admissionModeField;
	private TextField admissionDateField;

	private TextField upseeRollNoField;
	private TextField upseeRankField;

	private TextField domicileIdField;

	private TextField interPhysicsPercentField;
	private TextField interChemistryPercentField;
	private TextField interMathematicsPercentField;
	private TextField interBiologyPercentField;
	private TextField interEnglishPercentField;

	private TextField pcmPercentField;
	private TextField pcbPercentField;

	private Button createQualificationButton;
	private Grid<AcademicQualification> grid;

	private int studentId;
	private Student student;

	private StudentService studentService;
	private AcademicService academicService;

	private AcademicQualification academicQualification;
	private AcademicQualificationEditor academicQualificationEditor;
	private Dialog dialog;

	public StudentAdmissionDetails(StudentService studentService, AcademicService academicService) {
		this.studentService = studentService;
		this.academicService = academicService;

		sessionField = new TextField("Session");
		configureTextField(sessionField);

		admissionProgramField = new TextField("Program");
		configureTextField(admissionProgramField);

		admissionStageField = new TextField("Stage");
		admissionIdField = new TextField("Admission Id");

		admissionModeField = new TextField("Mode");
		configureTextField(admissionModeField);

		admissionDateField = new TextField("Admission Date");
		configureTextField(admissionDateField);

		upseeRollNoField = new TextField("UPSEE Roll No");
		upseeRankField = new TextField("UPSEE Rank");
		domicileIdField = new TextField("Domicile");

		interPhysicsPercentField = new TextField("Physics (%)");
		configureTextField(interPhysicsPercentField);

		interChemistryPercentField = new TextField("Chemistry (%)");
		configureTextField(interChemistryPercentField);

		interMathematicsPercentField = new TextField("Math (%)");
		configureTextField(interMathematicsPercentField);

		interBiologyPercentField = new TextField("Biology (%)");
		configureTextField(interBiologyPercentField);

		interEnglishPercentField = new TextField("English (%)");
		configureTextField(interEnglishPercentField);

		pcmPercentField = new TextField("PCM (%)");
		configureTextField(pcmPercentField);

		pcbPercentField = new TextField("PCB (%)");
		configureTextField(pcbPercentField);

		createQualificationButton = new Button("Add", VaadinIcon.PLUS.create());
		configureCreateQualificationButton();

		grid = new Grid<>(AcademicQualification.class);
		configureGrid();

		FlexLayout flex1 = new FlexLayout();
		configureFlex(flex1);

		FlexLayout flex2 = new FlexLayout();
		configureFlex(flex2);

		FlexLayout flex3 = new FlexLayout();
		flex3.setWidthFull();
		flex3.setJustifyContentMode(JustifyContentMode.END);

		flex1.add(sessionField, admissionProgramField, admissionStageField, admissionIdField, admissionModeField,
				admissionDateField, upseeRollNoField, upseeRankField, domicileIdField);
		flex2.add(interPhysicsPercentField, interChemistryPercentField, interMathematicsPercentField,
				interBiologyPercentField, interEnglishPercentField, pcmPercentField, pcbPercentField);

		flex3.add(createQualificationButton);

		add(flex1, flex2, flex3, grid);

		// dialog related
		academicQualificationEditor = new AcademicQualificationEditor(academicService);

		academicQualificationEditor.addListener(AcademicQualificationEditor.SaveEvent.class,
				this::handleSaveAcademicQualificationEvent);

		academicQualificationEditor.addListener(AcademicQualificationEditor.CloseEvent.class,
				this::handleCloseAcademicQualificationEvent);

		academicQualification = new AcademicQualification();
		dialog = new Dialog();
		configureDialog();
	}

	private void configureTextField(TextField textField) {
		textField.setWidth("100px");
		textField.setReadOnly(true);
	}

	private void configureFlex(FlexLayout flexLayout) {
		flexLayout.setFlexWrap(FlexWrap.WRAP);
		flexLayout.getElement().getStyle().set("padding", "8px");
		flexLayout.getElement().getStyle().set("gap", "8px");
	}

	public void setStudentId(int id) {
		this.studentId = id;
		if (id == 0) {
			StudentAdmissionDetails.this.setVisible(false);
		} else {
			StudentAdmissionDetails.this.setVisible(true);
			reload();
		}
	}

	private void configureGrid() {
		grid.setHeight("200px");
		grid.removeAllColumns();

		grid.addComponentColumn(item -> {
			Button button = new Button("Edit", VaadinIcon.PENCIL.create());
			button.addThemeVariants(ButtonVariant.LUMO_SMALL);
			return button;
		}).setHeader("Edit").setFrozen(true);
		
		grid.addColumn(aq -> {
			return aq.getLevel().toString();
		}).setHeader("Level").setFrozen(true);

		grid.addColumn(aq -> {
			return aq.getExam().getName();
		}).setHeader("Exam").setFrozen(true);

		grid.addColumn("year").setFrozen(true);

		grid.addColumn(aq -> {
			return aq.getBoard().getName();
		}).setHeader("Board/University");

		grid.addColumn("rollNo");

		grid.addColumn(aq -> {
			return aq.getSchool().getName();
		}).setHeader("School");

		grid.addColumn("obtainedMarks").setHeader("MO");
		grid.addColumn("maximumMarks").setHeader("MM");
		grid.addColumn("percentMarks").setHeader("%");

		grid.getColumns().forEach(col -> col.setAutoWidth(true));

		reloadGrid();
	}

	private void reloadGrid() {
		grid.setItems(academicService.getAcademicQualifications(studentId));
	}

	private void configureCreateQualificationButton() {
		createQualificationButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

		createQualificationButton.addClickListener(e -> {
			dialog.removeAll();
			dialog.add(academicQualificationEditor);
			dialog.open();
			academicQualification.setStudentId(studentId);
			academicQualificationEditor.setAcademicQualification(academicQualification);
		});
	}

	private void configureDialog() {
		// TODO Auto-generated method stub
		dialog.setWidth("400px");
		dialog.setModal(true);
		dialog.setDraggable(true);
	}

	private void reload() {
		student = studentService.getStudentById(studentId);
		reloadGrid();
	}

	public void handleSaveAcademicQualificationEvent(AcademicQualificationEditor.SaveEvent event) {
		AcademicQualification academicQualification = event.getAcademicQualification();

		if (academicQualification.getId() == 0) {
// 		create new
			int id = academicService.createAcademicQualification(event.getAcademicQualification());
			if (id > 0) {
				Notification.show("Qualification added successfully", 3000, Position.TOP_CENTER);
				academicQualification.clear();
				academicQualification.setStudentId(studentId);
				academicQualificationEditor.setAcademicQualification(academicQualification);
				reloadGrid();
			}
		} else {
// 		update existing
			boolean success = false;
//			academicService.updateAcademicQualification(event.getAcademicQualification());
		}
	}

	public void handleCloseAcademicQualificationEvent(AcademicQualificationEditor.CloseEvent event) {
		dialog.close();
	}

}
