package in.co.itlabs.sis.ui.layouts;

import java.util.Objects;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;

import in.co.itlabs.sis.ui.components.Header;
import in.co.itlabs.sis.ui.components.Navigation;

@CssImport("./layouts/app-layout.css")
public class AppLayout extends FlexLayout implements RouterLayout {

	private Header header;
	private Navigation navigation;
	private Div content;
	private Div footer;

	public AppLayout() {
		
		addClassName("app-layout");
		
		setFlexDirection(FlexDirection.ROW);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setAlignItems(Alignment.STRETCH);
		setHeightFull();

		VerticalLayout root = new VerticalLayout();
		
		
		root.setWidth("1100px");
		root.setHeightFull();
		
//		header
//		navigation
//		content

		header = new Header();
		header.setWidthFull();
		
		navigation = new Navigation();
		navigation.setWidthFull();
		
		content = new Div();
		content.setWidthFull();
		content.addClassName("content");
		footer = new Div();
		
//		header.add(new H1("Header"));
//		navigation.add(new H2("Navigation"));
		footer.add(new H4("Footer"));

		root.add(header, navigation, content, footer);
		root.expand(content);
		
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
