package in.co.itlabs.sis.ui.students;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.layouts.AppLayout;

@PageTitle(value = "Students")
@Route(value = "", layout = AppLayout.class)
@CssImport("./views/students-view.css")
public class StudentsView extends VerticalLayout {

	private StudentService studentService;

//	toolbar components
	private final TextField filterField = new TextField();
	private final Button filterButton = new Button("Filter");
	private final Button createButton = new Button("New");

	private final Grid<Student> grid = new Grid<>(Student.class);

	public StudentsView(StudentService studentService) {
		this.studentService = studentService;

		addClassName("students-view");
		
		configureGrid();
		configureFilter();

		// TODO Auto-generated constructor stub
		add(new Label("I am Students View"));

		HorizontalLayout filterDiv = new HorizontalLayout();
		
		filterButton.setIcon(VaadinIcon.FILTER.create());
		filterDiv.add(filterField, filterButton);

		Icon icon = VaadinIcon.PLUS.create();
		createButton.setIcon(icon);
		
		createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		FlexLayout toolbar = new FlexLayout();
		toolbar.setWidthFull();
		
		toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);
		toolbar.add(filterDiv, createButton);

		add(toolbar, grid);
	}

	private void buildToolbar() {
		// TODO Auto-generated method stub

	}

	private void configureFilter() {
		// TODO Auto-generated method stub
		filterField.setPlaceholder("Filter by name...");

	}

	private void configureGrid() {
		// TODO Auto-generated method stub
		grid.setColumns("id", "name");
		grid.setItems(studentService.getAllStudents());
	}
}
