package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import in.co.itlabs.sis.business.entities.Contact;

public class MobileNoEditor extends VerticalLayout {

	private TextField mobileNoField;

	private Binder<Contact> binder;

	public MobileNoEditor() {

		setPadding(false);

		mobileNoField = new TextField("Mobile");
		configureMobileNoField();

		binder = new Binder<>(Contact.class);

		binder.forField(mobileNoField).asRequired("Mobile no can not be blank").bind("mobileNo");

		var actionBar = buildActionBar();

		add(mobileNoField, actionBar);

	}

	private void configureMobileNoField() {
		mobileNoField.setWidthFull();
	}

	public void setContact(Contact contact) {
		binder.setBean(contact);
		mobileNoField.focus();
	}

	private HorizontalLayout buildActionBar() {

		Button submitButton = new Button("OK", VaadinIcon.CHECK.create());
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		submitButton.addClickListener(event -> {

			if (binder.validate().isOk()) {
				fireEvent(new SaveEvent(this, binder.getBean()));
			}

		});

		Button resetButton = new Button("Reset", VaadinIcon.CLOSE.create());

		Span blank = new Span();

		HorizontalLayout root = new HorizontalLayout();
		root.setWidthFull();
		root.add(submitButton, blank, resetButton);
		root.expand(blank);

		return root;
	}

	public static abstract class ContactEvent extends ComponentEvent<MobileNoEditor> {
		private Contact contact;

		protected ContactEvent(MobileNoEditor source, Contact contact) {

			super(source, false);
			this.contact = contact;
		}

		public Contact getContact() {
			return contact;
		}
	}

	public static class SaveEvent extends ContactEvent {
		SaveEvent(MobileNoEditor source, Contact contact) {
			super(source, contact);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {

		return getEventBus().addListener(eventType, listener);
	}
}
