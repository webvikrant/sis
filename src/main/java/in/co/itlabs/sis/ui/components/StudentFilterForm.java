package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import in.co.itlabs.sis.business.helpers.InfoType;

public class StudentFilterForm extends VerticalLayout {

	private ComboBox<InfoType> infoTypeCombo;

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

	private VerticalLayout filters;
	private FlexLayout actionBar;

	public StudentFilterForm() {
		
		setPadding(false);

		// TODO Auto-generated constructor stub
		infoTypeCombo = new ComboBox<InfoType>("Filter");

		nameField = new TextField("Name");
		programCombo = new ComboBox<String>("Program");
		branchCombo = new ComboBox<String>("Branch");
		sessionCombo = new ComboBox<String>("Session");
		semesterCombo = new ComboBox<String>("Semester");
		categoryCombo = new ComboBox<String>("Category");
		genderCombo = new ComboBox<String>("Gender");
		hostelCheck = new Checkbox("Hostel");

		applyButton = new Button("Apply", VaadinIcon.CHECK.create());
		resetButton = new Button("Reset", VaadinIcon.CLOSE.create());

		filters = new VerticalLayout();
		filters.addClassName("content");
		filters.setPadding(false);

		actionBar = new FlexLayout();

		configureInfoTypeCombo();
		configureFilters();
		configureActionBar();

		add(infoTypeCombo, filters, actionBar);

		// trigger

		infoTypeCombo.setValue(InfoType.Personal);
	}

	private void configureActionBar() {
		applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		actionBar.setWidthFull();
		actionBar.setJustifyContentMode(JustifyContentMode.BETWEEN);
		actionBar.add(applyButton, resetButton);
	}

	private void configureFilters() {
		nameField.setWidthFull();
		categoryCombo.setWidthFull();
		genderCombo.setWidthFull();
		programCombo.setWidthFull();
		branchCombo.setWidthFull();
		sessionCombo.setWidthFull();
		semesterCombo.setWidthFull();
		
		VerticalLayout personFilters = new VerticalLayout();
//		personFilters.setPadding(false);
		personFilters.add(nameField, categoryCombo, genderCombo);

		VerticalLayout academicFilters = new VerticalLayout();
//		academicFilters.setPadding(false);
		academicFilters.add(programCombo, branchCombo, sessionCombo, semesterCombo, hostelCheck);

		infoTypeCombo.addValueChangeListener(evt -> {
			filters.removeAll();

			InfoType infoType = evt.getValue();

			if (infoType == null) {
				return;
			}

			switch (infoType) {
			case Personal:
				filters.add(personFilters);
				break;

			case Academic:
				filters.add(academicFilters);
				break;
			case Scholarship:

				break;

			default:
				break;
			}
		});
	}

	private void configureInfoTypeCombo() {
		infoTypeCombo.setWidthFull();
		infoTypeCombo.setItems(InfoType.values());

	}
}
