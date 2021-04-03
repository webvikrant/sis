package in.co.itlabs.sis.ui.views.students;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.components.StudentFilterForm;
import in.co.itlabs.sis.ui.layouts.AppLayout;

@PageTitle(value = "Students")
@Route(value = "", layout = AppLayout.class)
@CssImport("./views/students-view.css")
public class StudentsView extends VerticalLayout {

	private StudentService studentService;

//	toolbar components

	private final Grid<Student> grid = new Grid<>(Student.class);

	public StudentsView(StudentService studentService) {
		this.studentService = studentService;

		addClassName("students-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);

		configureGrid();

		// TODO Auto-generated constructor stub
		Icon icon = VaadinIcon.USERS.create();
		icon.setSize("14px");
		
		Span titleSpan = new Span("Students");
		
		HorizontalLayout title = new HorizontalLayout();
		title.setJustifyContentMode(JustifyContentMode.CENTER);
		title.setAlignItems(Alignment.CENTER);
		title.add(icon,titleSpan);

		FlexLayout toolbar = new FlexLayout();
		toolbar.setWidthFull();

		toolbar.setJustifyContentMode(JustifyContentMode.END);

		StudentFilterForm filterForm = new StudentFilterForm();
		VerticalLayout leftVLayout = new VerticalLayout();

		leftVLayout.add(filterForm);

		VerticalLayout rightVLayout = new VerticalLayout();
		rightVLayout.add(toolbar, grid);
		rightVLayout.expand(grid);

		SplitLayout splitLayout = new SplitLayout();
		splitLayout.setSizeFull();
		splitLayout.setSplitterPosition(20);

		splitLayout.addToPrimary(leftVLayout);
		splitLayout.addToSecondary(rightVLayout);

		add(title, splitLayout);
		setHorizontalComponentAlignment(Alignment.CENTER, titleSpan);
	}

	private void configureGrid() {
		// TODO Auto-generated method stub
		grid.setColumns("id", "name", "fatherName", "motherName", "dateOfBirth", "gender", "mobileNo", "whatsAppNo");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));
		grid.setItems(studentService.getAllStudents());
	}
}
