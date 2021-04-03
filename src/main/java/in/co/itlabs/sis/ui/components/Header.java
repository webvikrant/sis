package in.co.itlabs.sis.ui.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Header extends FlexLayout {

	private Span appSpan = new Span();
	private Span dateSpan = new Span();

	public Header() {
		// TODO Auto-generated constructor stub
		setJustifyContentMode(JustifyContentMode.BETWEEN);

		Icon icon = VaadinIcon.DATABASE.create();
		icon.setSize("24px");

//		Span logoSpan = new Span("SIS - ");

		appSpan.setText("Student Information System");

		HorizontalLayout title = new HorizontalLayout();
		title.setPadding(false);
		title.setJustifyContentMode(JustifyContentMode.CENTER);
		title.setAlignItems(Alignment.CENTER);
		title.add(icon, appSpan);

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, YYYY");
		LocalDate today = LocalDate.now();
		dateSpan.setText(dateFormatter.format(today));

		add(title, dateSpan);
	}
}
