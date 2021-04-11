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
import in.co.itlabs.sis.business.services.MediaService;
import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.components.StudentPersonalDetails;
import in.co.itlabs.sis.ui.components.StudentProgressDetails;
import in.co.itlabs.sis.ui.components.StudentScholarshipDetails;
import in.co.itlabs.sis.ui.components.StudentAddressDetails;
import in.co.itlabs.sis.ui.components.StudentAdmissionDetails;
import in.co.itlabs.sis.ui.components.StudentCard;
import in.co.itlabs.sis.ui.components.StudentContactDetails;
import in.co.itlabs.sis.ui.components.StudentMediaFiles;
import in.co.itlabs.sis.ui.layouts.AppLayout;

@PageTitle(value = "Student details")
@Route(value = "student-details", layout = AppLayout.class)
public class StudentDetailsView extends VerticalLayout implements HasUrlParameter<Integer> {

	private StudentService studentService;
	private AcademicService academicService;
	private AddressService addressService;
	private MediaService mediaService;

	private ComboBox<Student> studentCombo;

//	private NewStudentForm newStudentForm;
	private StudentCard studentCard;
	private Tabs tabs;
	private Tab personalTab;
	private Tab contactTab;
	private Tab addressTab;
	private Tab admissionTab;
	private Tab progressTab;
	private Tab scholarshipTab;
	private Tab mediaTab;

	private VerticalLayout content;
	private StudentPersonalDetails personalDetails;
	private StudentContactDetails contactDetails;
	private StudentAddressDetails addressDetails;
	private StudentAdmissionDetails admissionDetails;
	private StudentProgressDetails progressDetails;
	private StudentScholarshipDetails scholarshipDetails;
	private StudentMediaFiles mediaFiles;

//	private Dialog dialog;

	private int studentId = 0;
	private Tab currentTab = null;

	@Autowired
	public StudentDetailsView(StudentService studentService, AcademicService academicService,
			AddressService addressService, MediaService mediaService) {

		this.studentService = studentService;
		this.academicService = academicService;
		this.addressService = addressService;
		this.mediaService = mediaService;

		setSizeFull();
		setPadding(false);
		setAlignItems(Alignment.CENTER);

		studentCombo = new ComboBox<Student>();
		configureCombo(studentCombo);

//		newStudentForm = new NewStudentForm(academicService,studentService);
		studentCard = new StudentCard(studentService);

		tabs = new Tabs();

		personalTab = new Tab("Personal");
		contactTab = new Tab("Contact");
		addressTab = new Tab("Address");
		admissionTab = new Tab("Admission");
		progressTab = new Tab("Progress");
		scholarshipTab = new Tab("Scholarship");
		mediaTab = new Tab("Media");

		content = new VerticalLayout();

//		title bar
		var titleBar = buildTitleBar();
		add(titleBar);

//		split layout
		var splitLayout = buildSplitLayout();
		splitLayout.setSizeFull();

		add(studentCombo, splitLayout);

//		dialog = new Dialog();
//		configureDialog();
		Student student = new Student();
		student.setId(6);
		student.setName("XXX");
		studentCombo.setValue(student);
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
		tabs.add(mediaTab);

		tabs.addSelectedChangeListener(event -> {
			content.removeAll();

			Tab tab = event.getSelectedTab();
			if (tab == personalTab) {
				if (personalDetails == null) {
					personalDetails = new StudentPersonalDetails(studentService);
				}
				content.add(personalDetails);
				personalDetails.setStudentId(studentId);
				currentTab = personalTab;

			} else if (tab == contactTab) {
				if (contactDetails == null) {
					contactDetails = new StudentContactDetails(studentService);
				}
				content.add(contactDetails);
				contactDetails.setStudentId(studentId);
				currentTab = contactTab;

			} else if (tab == addressTab) {
				if (addressDetails == null) {
					addressDetails = new StudentAddressDetails(studentService, addressService);
				}
				content.add(addressDetails);
				addressDetails.setStudentId(studentId);
				currentTab = addressTab;

			} else if (tab == admissionTab) {
				if (admissionDetails == null) {
					admissionDetails = new StudentAdmissionDetails(studentService, academicService);
				}
				content.add(admissionDetails);
				admissionDetails.setStudentId(studentId);
				currentTab = admissionTab;

			} else if (tab == progressTab) {
				if (progressDetails == null) {
					progressDetails = new StudentProgressDetails(mediaService);
				}
				content.add(progressDetails);
				progressDetails.setStudentId(studentId);
				currentTab = progressTab;

			} else if (tab == scholarshipTab) {
				if (scholarshipDetails == null) {
					scholarshipDetails = new StudentScholarshipDetails(mediaService);
				}
				content.add(scholarshipDetails);
				scholarshipDetails.setStudentId(studentId);
				currentTab = scholarshipTab;

			} else if (tab == mediaTab) {
				if (mediaFiles == null) {
					mediaFiles = new StudentMediaFiles(mediaService);
				}
				content.add(mediaFiles);
				mediaFiles.setStudentId(studentId);
				currentTab = mediaTab;

			}
		});
	}

	private Div buildTitleBar() {
		Div root = new Div();
		root.addClassName("section-title");
		root.add("Student details");
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
		if (currentTab == null) {
			currentTab = personalTab;
		}
		tabs.setSelectedTab(currentTab);
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
