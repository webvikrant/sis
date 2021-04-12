package in.co.itlabs.sis.ui.layouts;

import java.util.Objects;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;

import in.co.itlabs.sis.ui.components.Header;
import in.co.itlabs.sis.ui.components.Navigation;

@CssImport("./layouts/app-layout.css")
public class AppLayout extends VerticalLayout implements RouterLayout {

	private Header header;
	private Navigation navigation;
	private VerticalLayout content;
	private HorizontalLayout footer;

	public AppLayout() {

		addClassName("app-layout");

		header = new Header();
		header.setWidthFull();

		navigation = new Navigation();
		navigation.setWidthFull();

		content = new VerticalLayout();
		
		content.setMargin(false);
		content.setPadding(false);
		content.setSpacing(false);
		
		content.addClassName("content");
		content.setWidthFull();

		footer = new HorizontalLayout();
		footer.setWidthFull();

		VerticalLayout root = new VerticalLayout();
		
		root.getStyle().set("margin", "auto");
		root.setPadding(false);
		root.setSpacing(false);
		root.setWidth("1000px");

		root.add(header, navigation, content, footer);

		add(root);
	}

	@Override
	public void removeRouterLayoutContent(HasElement oldContent) {
		// TODO Auto-generated method stub
		content.getElement().removeAllChildren();
	}

	@Override
	public void showRouterLayoutContent(HasElement newContent) {
		// TODO Auto-generated method stub
		if (newContent != null) {
			content.getElement().appendChild(Objects.requireNonNull(newContent.getElement()));
		}
	}
}
