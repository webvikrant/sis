package in.co.itlabs.sis.ui;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import in.co.itlabs.sis.business.services.StudentService;
import in.co.itlabs.sis.ui.components.Login;
import in.co.itlabs.sis.ui.layouts.GuestLayout;

@PageTitle(value = "Login")
@Route(value = "", layout = GuestLayout.class)
public class LoginView extends HorizontalLayout {

//	toolbar components

	public LoginView(StudentService studentService) {

		setWidthFull();
		
		setMargin(false);
		setPadding(false);
		setSpacing(false);
		
		// left is graphic
		Image image = new Image("https://picsum.photos/800/600", "SIS");
		image.getStyle().set("objectFit", "contain");
		image.addClassName("login-photo");
		image.setWidth("60%");

		// right is login component
		Login login = new Login();
		login.setWidth("40%");
		login.setMargin(true);

		add(image, login);
	}

}
