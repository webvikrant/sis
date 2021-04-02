package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;

public class StudentFilterForm extends FormLayout {

	private TextField nameField;
	private ComboBox<String> programCombo;
	private ComboBox<String> branchCombo;
	private ComboBox<String> sessionCombo;
	private ComboBox<String> semesterCombo;
	private ComboBox<String> categoryCombo;
	private ComboBox<String> genderCombo;

	private Checkbox hostelCheck;

	private Button applyButton;
	private Button resetButton;

	public StudentFilterForm() {
		// TODO Auto-generated constructor stub
		nameField = new TextField("Name");
		programCombo = new ComboBox<String>("Program");
		branchCombo = new ComboBox<String>("Branch");
		sessionCombo = new ComboBox<String>("Session");
		semesterCombo = new ComboBox<String>("Semester");
		categoryCombo = new ComboBox<String>("Category");
		genderCombo = new ComboBox<String>("Gender");

		hostelCheck = new Checkbox("Hostel");

		applyButton = new Button("Apply", VaadinIcon.CHECK.create());
		applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		resetButton = new Button("Reset", VaadinIcon.CLOSE.create());
//		resetButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		FlexLayout buttons = new FlexLayout();
		buttons.setJustifyContentMode(JustifyContentMode.BETWEEN);
		buttons.add(applyButton, resetButton);

		add(nameField, programCombo, branchCombo, sessionCombo, semesterCombo, categoryCombo, genderCombo, hostelCheck, buttons);
	}
}
