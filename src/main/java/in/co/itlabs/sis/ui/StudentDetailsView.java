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
import in.co.itlabs.sis.business.services.AddressService;
import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.components.StudentPersonalDetails;
import in.co.itlabs.sis.ui.components.StudentAddressDetails;
import in.co.itlabs.sis.ui.components.StudentAdmissionDetails;
import in.co.itlabs.sis.ui.components.StudentCard;
import in.co.itlabs.sis.ui.components.StudentContactDetails;
import in.co.itlabs.sis.ui.layouts.AppLayout;

@PageTitle(value = "Student details")
@Route(value = "student-details", layout = AppLayout.class)
public class StudentDetailsView extends VerticalLayout implements HasUrlParameter<Integer> {

	private StudentService studentService;
	private AcademicService academicService;
	private AddressService addressService;

//	private NewStudentForm newStudentForm;
	private StudentCard studentCard;
	private Tabs tabs;
	private Tab personalTab;
	private Tab contactTab;
	private Tab addressTab;
	private Tab admissionTab;
	private Tab progressTab;
	private Tab scholarshipTab;
	private Tab documentsTab;

	private VerticalLayout content;
	private StudentPersonalDetails personalDetails;
	private StudentContactDetails contactDetails;
	private StudentAddressDetails addressDetails;
	private StudentAdmissionDetails admissionDetails;

//	private Dialog dialog;

	private int studentId = 0;

	@Autowired
	public StudentDetailsView(StudentService studentService, AcademicService academicService,
			AddressService addressService) {

		this.studentService = studentService;
		this.academicService = academicService;
		this.addressService = addressService;

		setAlignItems(Alignment.CENTER);

//		newStudentForm = new NewStudentForm(academicService,studentService);
		studentCard = new StudentCard(academicService, studentService);

		tabs = new Tabs();

		personalTab = new Tab("Personal");
		contactTab = new Tab("Contact");
		addressTab = new Tab("Address");
		admissionTab = new Tab("Admission");
		progressTab = new Tab("Progress");
		scholarshipTab = new Tab("Scholarship");
		documentsTab = new Tab("Documents");

		content = new VerticalLayout();

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
		tabs.add(contactTab);
		tabs.add(addressTab);
		tabs.add(admissionTab);
		tabs.add(progressTab);
		tabs.add(scholarshipTab);
		tabs.add(documentsTab);

		tabs.addSelectedChangeListener(event -> {
			content.removeAll();

			Tab tab = event.getSelectedTab();
			if (tab == personalTab) {
				if (personalDetails == null) {
					personalDetails = new StudentPersonalDetails(studentService, academicService);
				}
				content.add(personalDetails);
				personalDetails.setStudentId(studentId);
			} else if (tab == contactTab) {
				if (contactDetails == null) {
					contactDetails = new StudentContactDetails(studentService);
				}
				content.add(contactDetails);
				contactDetails.setStudentId(studentId);

			} else if (tab == addressTab) {
				if (addressDetails == null) {
					addressDetails = new StudentAddressDetails(studentService, addressService);
				}
				content.add(addressDetails);
				addressDetails.setStudentId(studentId);

			} else if (tab == admissionTab) {
				if (admissionDetails == null) {
					admissionDetails = new StudentAdmissionDetails(studentService, academicService);
				}
				content.add(admissionDetails);
				admissionDetails.setStudentId(studentId);

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
