package in.co.itlabs.sis.ui.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import in.co.itlabs.sis.business.entities.Session;
import in.co.itlabs.sis.business.services.AcademicService;
import in.co.itlabs.sis.ui.layouts.AppLayout;

@PageTitle(value = "Registration")
@Route(value = "registration", layout = AppLayout.class)
public class RegistrationView extends VerticalLayout {

	@Autowired
	public RegistrationView(AcademicService academicService) {

		setSizeFull();
		setAlignItems(Alignment.CENTER);

		// TODO Auto-generated constructor stub
		Icon icon = VaadinIcon.PLUS.create();
		icon.setSize("16px");

		Span titleSpan = new Span("Registration");

		HorizontalLayout title = new HorizontalLayout();
		title.setJustifyContentMode(JustifyContentMode.CENTER);
		title.setAlignItems(Alignment.CENTER);
		title.add(icon, titleSpan);

		add(title);
		setHorizontalComponentAlignment(Alignment.CENTER, titleSpan);

		for (Session session : academicService.getAllSessions()) {
			add(new Span(session.getName()));
		}
	}

}