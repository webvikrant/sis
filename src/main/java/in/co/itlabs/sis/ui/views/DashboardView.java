package in.co.itlabs.sis.ui.views;

import com.vaadin.flow.component.html.Div;
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
		setPadding(false);
		setAlignItems(Alignment.START);
		
//		title bar
		var titleBar = buildTitleBar();
		add(titleBar);
	}

	private Div buildTitleBar() {
		Div root = new Div();
		root.addClassName("section-title");
		root.add("Dashboard");
		return root;
	}
}
