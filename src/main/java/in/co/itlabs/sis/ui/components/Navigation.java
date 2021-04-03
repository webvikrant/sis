package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Navigation extends FlexLayout {

	private HorizontalLayout menuHLayout = new HorizontalLayout();
	private HorizontalLayout userHLayout = new HorizontalLayout();

	public Navigation() {
		// TODO Auto-generated constructor stub
		setJustifyContentMode(JustifyContentMode.BETWEEN);

		userHLayout.setAlignItems(Alignment.CENTER);

		add(menuHLayout, userHLayout);

		Button registrationButton = new Button("Registration", VaadinIcon.PLUS.create());
		Button studentsButton = new Button("Students", VaadinIcon.USERS.create());
		Button studentDetailsButton = new Button("Student details", VaadinIcon.USER_CARD.create());

		menuHLayout.add(registrationButton, studentsButton, studentDetailsButton);

//		Span userSapn = new Span("Vikrant Thakur");
		Button userButton = new Button("Vikrant Thakur", VaadinIcon.USER.create());
		userButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		
		Button logoutButton = new Button("Logout", VaadinIcon.SIGN_OUT.create());
		logoutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

		userHLayout.add(userButton, logoutButton);
	}
}
