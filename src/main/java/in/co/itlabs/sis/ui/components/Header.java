package in.co.itlabs.sis.ui.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

public class Header extends FlexLayout {

	private Span appSpan = new Span();
	private Span dateSpan = new Span();

	public Header() {
		// TODO Auto-generated constructor stub
		setJustifyContentMode(JustifyContentMode.BETWEEN);
		
				
		appSpan.setText("Student Information System");

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, YYYY");
		
		LocalDate today = LocalDate.now();
		
		dateSpan.setText(dateFormatter.format(today));

		add(appSpan, dateSpan);
	}
}
