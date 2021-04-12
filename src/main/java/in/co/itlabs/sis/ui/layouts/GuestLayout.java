package in.co.itlabs.sis.ui.layouts;

import java.util.Objects;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;

@CssImport("./layouts/guest-layout.css")
public class GuestLayout extends VerticalLayout implements RouterLayout {

	private VerticalLayout content;

	public GuestLayout() {
		addClassName("guest-layout");
		
		setMargin(false);
		setPadding(false);
		setSpacing(false);
		
		setHeightFull();
		
		setJustifyContentMode(JustifyContentMode.CENTER);
		
		content = new VerticalLayout();
		content.setMargin(false);
		content.setPadding(false);
		content.setSpacing(false);
		
		content.addClassName("content");
		content.getStyle().set("margin", "auto");
		content.setWidth("800px");

		add(content);
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
