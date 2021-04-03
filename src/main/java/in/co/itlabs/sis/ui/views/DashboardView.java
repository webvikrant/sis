package in.co.itlabs.sis.ui.views;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.layouts.AppLayout;

@PageTitle(value = "Dashboard")
@Route(value = "dashboard", layout = AppLayout.class)
public class DashboardView extends VerticalLayout {

//	toolbar components

	public DashboardView(StudentService studentService) {

		setSizeFull();
		setAlignItems(Alignment.CENTER);

		

		// TODO Auto-generated constructor stub
		Icon icon = VaadinIcon.DASHBOARD.create();
		icon.setSize("16px");

		Span titleSpan = new Span("Dashboard");

		HorizontalLayout title = new HorizontalLayout();
		title.setJustifyContentMode(JustifyContentMode.CENTER);
		title.setAlignItems(Alignment.CENTER);
		title.add(icon, titleSpan);

		add(title);
		setHorizontalComponentAlignment(Alignment.CENTER, titleSpan);
	}

}
