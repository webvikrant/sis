package in.co.itlabs.sis.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import in.co.itlabs.sis.business.entities.Program;
import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.components.StudentFilterForm;
import in.co.itlabs.sis.ui.components.editors.NewStudentEditor;
import in.co.itlabs.sis.ui.layouts.AppLayout;

@PageTitle(value = "Students")
@Route(value = "students", layout = AppLayout.class)
public class StudentsView extends VerticalLayout {

	private StudentService studentService;
	private AcademicService academicService;

	private StudentFilterForm filterForm;
	private Span recordCount;
	private Grid<Student> grid;

	private NewStudentEditor newStudentEditor;
	private Student student;
	private Dialog dialog;

	private final List<String> messages = new ArrayList<>();

	@Autowired
	public StudentsView(StudentService studentService, AcademicService academicService) {
		this.studentService = studentService;
		this.academicService = academicService;

		setMargin(false);
		setPadding(false);
		setSpacing(false);

		setAlignItems(Alignment.CENTER);

		student = new Student();

		newStudentEditor = new NewStudentEditor(academicService);
		newStudentEditor.addListener(NewStudentEditor.SaveEvent.class, this::handleSaveEvent);
		newStudentEditor.addListener(NewStudentEditor.CancelEvent.class, this::handleCancelEvent);

		filterForm = new StudentFilterForm();
		filterForm.setPadding(true);

		dialog = new Dialog();
		dialog.setWidth("300px");
		dialog.setModal(true);
		dialog.setDraggable(true);

		recordCount = new Span();
		grid = new Grid<Student>(Student.class);
		configureGrid();

		var titleBar = buildTitleBar();
		var menuBar = buildMenuBar();

		VerticalLayout content = new VerticalLayout();
		content.add(menuBar, grid);
		content.expand(grid);

//		split layout
		SplitLayout splitLayout = new SplitLayout();
		splitLayout.setSplitterPosition(25);
		splitLayout.setSizeFull();

		splitLayout.addToPrimary(filterForm);
		splitLayout.addToSecondary(content);

		add(titleBar, splitLayout);

		reload();
	}

	private Div buildTitleBar() {
		Div root = new Div();
		root.addClassName("view-title");
		root.add("Students");
		return root;
	}

	private HorizontalLayout buildMenuBar() {
		// TODO Auto-generated method stub

		Button exportButton = new Button("Download as MS Excel", VaadinIcon.DOWNLOAD.create());

		Button importButton = new Button("Upload from MS Excel", VaadinIcon.UPLOAD.create());
		importButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

		Button createButton = new Button("New", VaadinIcon.PLUS.create());
		createButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		createButton.addClickListener(event -> {
			dialog.removeAll();
			dialog.add(newStudentEditor);
			dialog.open();
			newStudentEditor.setStudent(student);
		});

		Span blank = new Span();

		HorizontalLayout root = new HorizontalLayout();
		root.setWidthFull();
		root.setAlignItems(Alignment.CENTER);
		root.add(recordCount, exportButton, blank, importButton, createButton);
		root.expand(blank);
		
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

		grid.addColumn(student -> {
			String value = "";
			Program program = student.getProgram();
			if (program != null) {
				value = program.getName();
			}
			return value;
		}).setHeader("Program");

		grid.addColumn("stage");

//		grid.addComponentColumn(item -> {
//			return new Anchor("student-details/" + item.getId(), "Details");
//		}).setHeader("Details");

		grid.addComponentColumn(student -> {
			Button button = new Button("More", VaadinIcon.ARROW_FORWARD.create());
			button.addThemeVariants(ButtonVariant.LUMO_SMALL);
			button.addClickListener(e -> {
				VaadinSession.getCurrent().setAttribute(Student.class, student);
				UI.getCurrent().navigate("student-details");
			});

			return button;
		}).setHeader("More");

		grid.getColumns().forEach(col -> col.setAutoWidth(true));
	}

	public void handleSaveEvent(NewStudentEditor.SaveEvent event) {
		Student student = event.getStudent();
		messages.clear();
		int id = studentService.createStudent(messages, student);
		if (id > 0) {
			Notification.show("Student created successfully", 3000, Position.TOP_CENTER);
			student.clear();
			newStudentEditor.setStudent(student);
			reload();
		} else {
			Notification.show(messages.toString(), 3000, Position.TOP_CENTER);
		}
	}

	public void handleCancelEvent(NewStudentEditor.CancelEvent event) {
		dialog.close();
	}

	private void reload() {
		grid.setItems(studentService.getAllStudents());
		recordCount.setText("100 record(s) found");
	}

//	public static abstract class StudentEvent extends ComponentEvent<StudentsView> {
//		private Student student;
//
//		protected StudentEvent(StudentsView source, Student student) {
//
//			super(source, false);
//			this.student = student;
//		}
//
//		public Student getStudent() {
//			return student;
//		}
//	}
//
//	public static class ShowDetailsEvent extends StudentEvent {
//		ShowDetailsEvent(StudentsView source, Student student) {
//			super(source, student);
//		}
//	}
//
//	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
//			ComponentEventListener<T> listener) {
//
//		return getEventBus().addListener(eventType, listener);
//	}
}
