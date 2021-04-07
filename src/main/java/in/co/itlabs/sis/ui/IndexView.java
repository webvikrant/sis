package in.co.itlabs.sis.ui;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.layouts.GuestLayout;

@PageTitle(value = "Index")
@Route(value = "", layout = GuestLayout.class)
public class IndexView extends VerticalLayout {

//	toolbar components

	public IndexView(StudentService studentService) {

		setSizeFull();
		setAlignItems(Alignment.CENTER);

		

		// TODO Auto-generated constructor stub
		Icon icon = VaadinIcon.CHECK.create();
		icon.setSize("14px");

		Span titleSpan = new Span("Index");

		HorizontalLayout title = new HorizontalLayout();
		title.setJustifyContentMode(JustifyContentMode.CENTER);
		title.setAlignItems(Alignment.CENTER);
		title.add(icon, titleSpan);

		add(title);
		setHorizontalComponentAlignment(Alignment.CENTER, titleSpan);
	}

}
