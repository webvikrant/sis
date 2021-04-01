package in.co.itlabs.sis.ui.students;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;

import in.co.itlabs.sis.ui.layouts.AppLayout;

@Route(value = "", layout = AppLayout.class)
public class StudentsView extends Div{

	public StudentsView() {
		// TODO Auto-generated constructor stub
		add(new Label("I am Students View"));
	}
}
