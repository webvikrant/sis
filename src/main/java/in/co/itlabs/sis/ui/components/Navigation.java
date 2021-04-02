package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
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

		MenuBar menuBar = new MenuBar();
		MenuItem menu1 = menuBar.addItem("Menu 1");
		MenuItem menu2 = menuBar.addItem("Menu 2");
		MenuItem menu3 = menuBar.addItem("Menu 3");

		SubMenu subMenu1 = menu1.getSubMenu();
		subMenu1.addItem("Item 1-1");
		subMenu1.addItem("Item 1-2");
		subMenu1.addItem("Item 1-3");

		SubMenu subMenu2 = menu2.getSubMenu();
		subMenu2.addItem("Item 2-1");
		subMenu2.addItem("Item 2-2");
		subMenu2.addItem("Item 2-3");

		SubMenu subMenu3 = menu3.getSubMenu();
		subMenu3.addItem("Item 3-1");
		subMenu3.addItem("Item 3-2");
		subMenu3.addItem("Item 3-3");

		menuHLayout.add(menuBar);

		Span userSapn = new Span("Vikrant Thakur");

		Button logoutButton = new Button("Logout", VaadinIcon.SIGN_OUT.create());
		logoutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

		userHLayout.add(userSapn, logoutButton);
	}
}
