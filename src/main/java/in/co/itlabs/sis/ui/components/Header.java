package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

public class Header extends FlexLayout {

	private Span appSpan = new Span();
	private Span dateSpan = new Span();

	public Header() {
		// TODO Auto-generated constructor stub
		setJustifyContentMode(JustifyContentMode.BETWEEN);
		
				
		appSpan.setText("Student Information System");
		
		dateSpan.setText("Today's date");

		add(appSpan, dateSpan);
	}
}
