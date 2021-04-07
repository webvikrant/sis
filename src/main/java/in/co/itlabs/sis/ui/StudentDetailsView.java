package in.co.itlabs.sis.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import in.co.itlabs.sis.business.entities.Student;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.components.StudentPersonalDetails;
import in.co.itlabs.sis.ui.components.StudentCard;
import in.co.itlabs.sis.ui.layouts.AppLayout;

@PageTitle(value = "Student details")
@Route(value = "student-details", layout = AppLayout.class)
public class StudentDetailsView extends VerticalLayout implements HasUrlParameter<Integer> {

	private AcademicService academicService;
	private StudentService studentService;

//	private NewStudentForm newStudentForm;
	private StudentCard studentCard;
	private Tabs tabs;
	private Tab personalTab;
	private Tab parentsTab;
	private Tab academicTab;
	private Tab scholarshipTab;
	private Tab documentsTab;

	private VerticalLayout content;
	private StudentPersonalDetails personalDetails;

//	private Dialog dialog;

	private int studentId = 0;

	@Autowired
	public StudentDetailsView(AcademicService academicService, StudentService studentService) {

		this.academicService = academicService;
		this.studentService = studentService;

		setSizeFull();
		setPadding(false);
		setAlignItems(Alignment.START);

//		newStudentForm = new NewStudentForm(academicService,studentService);
		studentCard = new StudentCard(academicService, studentService);

		tabs = new Tabs();

		personalTab = new Tab("Personal");
		parentsTab = new Tab("Parents");
		academicTab = new Tab("Academic");
		scholarshipTab = new Tab("Scholarship");
		documentsTab = new Tab("Documents");

		content = new VerticalLayout();
		personalDetails = new StudentPersonalDetails(academicService, studentService);

//		title bar
		var titleBar = buildTitleBar();
		add(titleBar);

//		search bar
		var searchBar = buildSearchBar();
		setHorizontalComponentAlignment(Alignment.CENTER, searchBar);
//		searchBar.setWidthFull();
		add(searchBar);

//		split layout
		var splitLayout = buildSplitLayout();
		splitLayout.setSizeFull();
		add(splitLayout);

//		dialog = new Dialog();
//		configureDialog();

	}

	private SplitLayout buildSplitLayout() {
		// TODO Auto-generated method stub
		SplitLayout root = new SplitLayout();
		root.setSplitterPosition(25);
		root.setSizeFull();

		root.addToPrimary(studentCard);

		configureTabs();

		VerticalLayout tabsLayout = new VerticalLayout();
		tabsLayout.setPadding(false);
		tabsLayout.setSpacing(false);
		tabsLayout.add(tabs, content);

		root.addToSecondary(tabsLayout);

		return root;
	}

	private void configureTabs() {
		content.setPadding(false);
		content.setSpacing(false);

		tabs.add(personalTab);
		tabs.add(parentsTab);
		tabs.add(academicTab);
		tabs.add(scholarshipTab);
		tabs.add(documentsTab);

		tabs.addSelectedChangeListener(event -> {
			content.removeAll();

			Tab tab = event.getSelectedTab();
			if (tab == personalTab) {
				content.add(personalDetails);
				personalDetails.setStudentId(studentId);
			} else if (tab == academicTab) {

			} else if (tab == scholarshipTab) {

			}
		});
	}

	private Div buildTitleBar() {
		Div root = new Div();
		root.addClassName("section-title");
		root.add("Student details");
		return root;
	}

	private HorizontalLayout buildSearchBar() {

		ComboBox<Student> studentCombo = new ComboBox<Student>();
		configureCombo(studentCombo);

		HorizontalLayout root = new HorizontalLayout();
		root.add(studentCombo);

		return root;
	}

	private void configureCombo(ComboBox<Student> studentCombo) {
		studentCombo.setWidth("300px");
		studentCombo.setPlaceholder("Select a student...");
		studentCombo.setItems(studentService.getAllStudents());
		studentCombo.setItemLabelGenerator(student -> {
			return student.getAdmissionId() + " " + student.getName();
		});

		studentCombo.addValueChangeListener(event -> {
			studentId = 0;
			if (event.getValue() != null) {
				studentId = event.getValue().getId();
			}

			loadStudent();
		});
	}

	private void loadStudent() {
		studentCard.setStudentId(studentId);
		tabs.setSelectedTab(null);
		tabs.setSelectedTab(personalTab);
	}

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter Integer parameter) {
		if (parameter == null || parameter == 0) {
			return;
		}
		studentId = parameter;
		loadStudent();
	}
}
