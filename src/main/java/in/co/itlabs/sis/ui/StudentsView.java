package in.co.itlabs.sis.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.components.NewStudentForm;
import in.co.itlabs.sis.ui.components.StudentFilterForm;
import in.co.itlabs.sis.ui.layouts.AppLayout;

@PageTitle(value = "Students")
@Route(value = "students", layout = AppLayout.class)
public class StudentsView extends VerticalLayout {

	private AcademicService academicService;
	private StudentService studentService;

	private final Grid<Student> grid = new Grid<>(Student.class);

	private NewStudentForm newStudentForm;
	private Dialog dialog;

	@Autowired
	public StudentsView(AcademicService academicService, StudentService studentService) {
		this.academicService = academicService;
		this.studentService = studentService;

		newStudentForm = new NewStudentForm(academicService, studentService);
		dialog = new Dialog();

		setSizeFull();
		setPadding(false);
		setAlignItems(Alignment.START);

//		title bar
		var titleBar = buildTitleBar();
		add(titleBar);

//		split layout
		SplitLayout splitLayout = new SplitLayout();
		splitLayout.setSplitterPosition(25);
		splitLayout.setSizeFull();
		add(splitLayout);

//		filter component on left
		var filterComponent = buildFilterComponent();
		splitLayout.addToPrimary(filterComponent);

//		grid etc on right
		var resultsComponent = buildResultsComponent();
		splitLayout.addToSecondary(resultsComponent);

		configureDialog();

		newStudentForm.addListener(NewStudentForm.StudentCreatedEvent.class, this::handleStudentCreatedEvent);

		reload();
	}

	private Div buildTitleBar() {
		Div root = new Div();
		root.addClassName("section-title");
		root.add("Students");
		return root;
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

	private VerticalLayout buildFilterComponent() {
		// TODO Auto-generated method stub
		VerticalLayout root = new VerticalLayout();
		StudentFilterForm filterForm = new StudentFilterForm();
		root.add(filterForm);
		return root;
	}

	private VerticalLayout buildResultsComponent() {
		VerticalLayout root = new VerticalLayout();

//		tool bar
		var toolBar = buildToolBar();
		toolBar.setWidthFull();

//		grid
		configureGrid();

		root.add(toolBar, grid);
		root.expand(grid);

		return root;
	}

	private HorizontalLayout buildToolBar() {
		// TODO Auto-generated method stub

		Button createButton = new Button("New", VaadinIcon.PLUS.create());
		createButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		createButton.addClickListener(event -> {
			dialog.open();
		});

		Span space = new Span();

		HorizontalLayout root = new HorizontalLayout();
		root.setJustifyContentMode(JustifyContentMode.END);
		root.add(space, createButton);
		root.expand(space);

		return root;
	}

	private void configureGrid() {
		// TODO Auto-generated method stub

		grid.removeAllColumns();

		grid.addColumn("admissionId");
		grid.addColumn("name");

		grid.addColumn(student -> {
			String value = "";
			if (student.getSession() != null) {
				value = student.getSession().getName();
			}
			return value;
		}).setHeader("Session");

		grid.addColumn("programId");
		grid.addColumn("stage");

		grid.getColumns().forEach(col -> col.setAutoWidth(true));

		grid.addComponentColumn(item -> {
			return new Anchor("student-details/" + item.getId(), "Details");
		}).setHeader("Details");
	}

	private void handleStudentCreatedEvent(NewStudentForm.StudentCreatedEvent event) {
		Notification.show("Student '" + event.getStudent().getName() + "' created.", 3000, Position.TOP_CENTER);
		reload();
	}

	private void reload() {
		grid.setItems(studentService.getAllStudents());
	}
}
