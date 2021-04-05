package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;

public class Navigation extends FlexLayout implements AfterNavigationObserver {

	private final HorizontalLayout menuHLayout;
	private final HorizontalLayout userHLayout;

	private final Button dashboardButton;
	private final Button studentsButton;
	private final Button studentDetailsButton;

	private final Button userButton;
	private final Button logoutButton;

	public Navigation() {
		// TODO Auto-generated constructor stub
		setJustifyContentMode(JustifyContentMode.BETWEEN);

		menuHLayout = new HorizontalLayout();
		userHLayout = new HorizontalLayout();

		dashboardButton = new Button("Dashboard", VaadinIcon.DASHBOARD.create());
		studentsButton = new Button("Students", VaadinIcon.USERS.create());
		studentDetailsButton = new Button("Student details", VaadinIcon.USER_CARD.create());

		userButton = new Button("Vikrant Thakur", VaadinIcon.USER.create());
		logoutButton = new Button("Logout", VaadinIcon.SIGN_OUT.create());

		configureButtons();

		menuHLayout.add(dashboardButton, studentsButton, studentDetailsButton);

		userHLayout.setAlignItems(Alignment.CENTER);
		userHLayout.add(userButton, logoutButton);

		add(menuHLayout, userHLayout);

	}

	private void configureButtons() {
		// TODO Auto-generated method stub
		dashboardButton.addClickListener(evt -> {
			UI.getCurrent().navigate("dashboard");
		});

		studentsButton.addClickListener(evt -> {
			UI.getCurrent().navigate("students");
		});

		studentDetailsButton.addClickListener(evt -> {
			UI.getCurrent().navigate("student-details");
		});

		userButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		logoutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {

		dashboardButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
		studentsButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
		studentDetailsButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);

		String location = event.getLocation().getFirstSegment();

		switch (location) {
		case "dashboard":
			dashboardButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			break;

		case "students":
			studentsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			break;

		case "student-details":
			studentDetailsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			break;

		default:
			break;
		}
	}
}
