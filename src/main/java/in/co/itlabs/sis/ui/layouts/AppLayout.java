package in.co.itlabs.sis.ui.layouts;

import com.vaadin.flow.component.html.Div;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.RouterLayout;

public class AppLayout extends Div implements RouterLayout {

	private Div header;
	private Div navigation;
	
	public AppLayout() {
//		header
//		navigation
//		content
		
		header = new Div();
		
		navigation = new Div();
	
		header.add(new H1("Header"));
		navigation.add(new H2("Navigation"));
	
		add(header, navigation);
	}
}
