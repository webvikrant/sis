package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class Login extends VerticalLayout {

	private H2 customerName ;
	private TextField userNameField;
	private PasswordField passwordField;
	private Button submitButton;
	private Button resetPasswordButton;

	public Login() {
		addClassName("login-form");
		
		customerName = new H2("IEC");
		
		userNameField = new TextField("Username");
		userNameField.setWidthFull();

		passwordField = new PasswordField("Password");
		passwordField.setWidthFull();

		submitButton = new Button("Login", VaadinIcon.CHECK.create());
		resetPasswordButton = new Button("Reset password", VaadinIcon.REFRESH.create());

		add(customerName, userNameField, passwordField, submitButton, resetPasswordButton);
		setAlignSelf(Alignment.CENTER, customerName);
	}
}
