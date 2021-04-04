package in.co.itlabs.sis.ui.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.components.NewStudentForm;
import in.co.itlabs.sis.ui.layouts.AppLayout;

@PageTitle(value = "Registration")
@Route(value = "registration", layout = AppLayout.class)
public class RegistrationView extends VerticalLayout {

	private StudentService studentService;

	private NewStudentForm newStudentForm;
	
	private Dialog dialog;

	@Autowired
	public RegistrationView(StudentService studentService, NewStudentForm newStudentForm) {
		this.studentService = studentService;
		this.newStudentForm = newStudentForm;

		setSizeFull();
		setAlignItems(Alignment.CENTER);

//		title bar
		var titleBar = buildTitleBar();
		add(titleBar);
		setHorizontalComponentAlignment(Alignment.CENTER, titleBar);

//		search bar
		var searchBar = buildSearchBar();
		add(searchBar);
		searchBar.setWidthFull();

		dialog = new Dialog();
		configureDialog();

	}

	private HorizontalLayout buildTitleBar() {
		// TODO Auto-generated method stub
		Icon icon = VaadinIcon.RECORDS.create();
		icon.setSize("16px");

		Span titleSpan = new Span("Registration");

		HorizontalLayout root = new HorizontalLayout();
		root.add(icon, titleSpan);
		root.setAlignItems(Alignment.CENTER);

		return root;
	}

	private HorizontalLayout buildSearchBar() {
		// TODO Auto-generated method stub
		ComboBox<Student> studentCombo = new ComboBox<Student>();
		configureCombo(studentCombo);

		Button createButton = new Button("New", VaadinIcon.PLUS.create());
		createButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		createButton.addClickListener(event -> {
			dialog.open();
		});

		Span space = new Span();

		HorizontalLayout root = new HorizontalLayout();
		root.add(studentCombo, space, createButton);
		root.expand(space);

		return root;
	}

	private void configureCombo(ComboBox<Student> studentCombo) {
		studentCombo.setWidth("400px");
		studentCombo.setPlaceholder("Select a student...");
//		studentCombo.setItems(studentService.getAllStudents());
		studentCombo.setItemLabelGenerator(student -> {
			return student.getName();
		});
	}

	private void configureDialog() {
		// TODO Auto-generated method stub
		Span title = new Span("New Student");
		Button closeButton = new Button(VaadinIcon.CLOSE.create());
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		closeButton.addClickListener(event -> {
			dialog.close();
		});

		FlexLayout titleBar = new FlexLayout();
		titleBar.setJustifyContentMode(JustifyContentMode.BETWEEN);
		titleBar.setWidthFull();
		titleBar.setAlignItems(Alignment.CENTER);
		titleBar.add(title, closeButton);

		dialog.add(titleBar, newStudentForm);

		dialog.setWidth("300px");
		dialog.setModal(true);
		dialog.setDraggable(true);
		dialog.setCloseOnOutsideClick(false);
	}
}
