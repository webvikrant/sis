package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.components.editors.AddressEditor.CancelEvent;
import in.co.itlabs.sis.ui.components.editors.AddressEditor.SaveEvent;

public class StudentPersonalDetails extends VerticalLayout {

	private TextField birthDateField;
	private TextField genderField;
	private TextField motherField;
	private TextField fatherField;
	private TextField guardianField;
	private TextField religionField;
	private TextField casteField;
	private TextField categoryField;
	private TextField aadhaarNoField;

	private Checkbox editCheck;
	private Button saveButton;
	private Button cancelButton;
	
	private int id;
	private Student student;

	private StudentService studentService;

	private Dialog dialog;

//	private final List<String> messages = new ArrayList<>();

	public StudentPersonalDetails(StudentService studentService) {
		this.studentService = studentService;

		dialog = new Dialog();
		configureDialog();

		birthDateField = new TextField("Date of Birth");
		configureBirthDateField();

		genderField = new TextField("Gender");
		configureGenderField();

		motherField = new TextField("Mother");
		configureMotherField();

		fatherField = new TextField("Father");
		configureFatherField();

		guardianField = new TextField("Local guardian");
		configureGuardianField();

		religionField = new TextField("Religion");
		configureReligionField();

		casteField = new TextField("Caste");
		configureCasteField();

		categoryField = new TextField("Category");
		configureCategoryField();

		aadhaarNoField = new TextField("Aadhaar No");
		configureAadhaarNoField();

		editCheck = new Checkbox("Edit");
		saveButton = new Button("OK", VaadinIcon.CHECK.create());
		cancelButton = new Button("Cancel", VaadinIcon.CLOSE.create());
		
		HorizontalLayout actionBar = buildActionBar();
		
		FlexLayout flex1 = new FlexLayout();
		configureFlex(flex1);

		FlexLayout flex2 = new FlexLayout();
		configureFlex(flex2);

		FlexLayout flex3 = new FlexLayout();
		configureFlex(flex3);

		FlexLayout flex4 = new FlexLayout();
		configureFlex(flex4);

		FlexLayout flex5 = new FlexLayout();
		configureFlex(flex5);

		flex2.add(birthDateField, genderField);
		flex3.add(motherField, fatherField, guardianField);
		flex4.add(religionField, casteField, categoryField);
		flex5.add(aadhaarNoField);

		add(flex1, flex2, flex3, flex4, flex5);

	}

	private HorizontalLayout buildActionBar() {
		HorizontalLayout root = new HorizontalLayout();

		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton.addClickListener(e -> {
//			if (binder.validate().isOk()) {
//
//				binder.getBean().setDistrictId(binder.getBean().getDistrict().getId());
//
//				fireEvent(new SaveEvent(this, binder.getBean()));
//			}
		});

		cancelButton.addClickListener(e -> {
//			fireEvent(new CancelEvent(this, binder.getBean()));
		});

		Span blank = new Span();

		root.add(saveButton, blank, cancelButton);
		root.expand(blank);

		return root;
	}

	private void configureFlex(FlexLayout flexLayout) {
		flexLayout.setFlexWrap(FlexWrap.WRAP);
		flexLayout.getElement().getStyle().set("padding", "8px");
		flexLayout.getElement().getStyle().set("gap", "8px");
	}

	private void configureBirthDateField() {
		birthDateField.setWidth("150px");
		birthDateField.setReadOnly(true);
	}

	private void configureGenderField() {
		genderField.setWidth("150px");
		genderField.setReadOnly(true);
	}

	private void configureMotherField() {
		motherField.setWidth("200px");
		motherField.setReadOnly(true);
	}

	private void configureFatherField() {
		fatherField.setWidth("200px");
		fatherField.setReadOnly(true);
	}

	private void configureGuardianField() {
		guardianField.setWidth("200px");
		guardianField.setReadOnly(true);
	}

	private void configureReligionField() {
		religionField.setWidth("150px");
		religionField.setReadOnly(true);
	}

	private void configureCasteField() {
		casteField.setWidth("150px");
		casteField.setReadOnly(true);
	}

	private void configureCategoryField() {
		categoryField.setWidth("150px");
		categoryField.setReadOnly(true);
	}

	private void configureAadhaarNoField() {
		aadhaarNoField.setWidth("250px");
		aadhaarNoField.setReadOnly(true);
	}

	public void setStudentId(int id) {
		this.id = id;
		if (id == 0) {
			StudentPersonalDetails.this.setVisible(false);
		} else {
			StudentPersonalDetails.this.setVisible(true);
			reload();
		}
	}

	private void configureDialog() {
		// TODO Auto-generated method stub
		dialog.setWidth("300px");
		dialog.setModal(true);
		dialog.setDraggable(true);
	}

	private void reload() {
		student = studentService.getStudentById(id);

		birthDateField.clear();
		if (student.getBirthDate() != null) {
			birthDateField.setValue(student.getBirthDate().toString());
		}

		genderField.clear();
		if (student.getGender() != null) {
			genderField.setValue(student.getGender().toString());
		}

	}
}
