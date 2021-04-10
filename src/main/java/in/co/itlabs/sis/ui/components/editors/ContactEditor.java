package in.co.itlabs.sis.ui.components.editors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import in.co.itlabs.sis.business.entities.Contact;

public class ContactEditor extends VerticalLayout implements Editor {

	private TextField nameField;
	private TextField mobileNoField;
	private TextField whatsappNoField;
	private EmailField emailIdField;

	private Button saveButton;
	private Button cancelButton;

	private Binder<Contact> binder;

	public ContactEditor() {

		nameField = new TextField();
		configureNameField();

		mobileNoField = new TextField("Mobile No");
		configureMobileNoField();

		whatsappNoField = new TextField("WhatsApp No");
		configureWhatsappNoField();

		emailIdField = new EmailField("Email Id");
		configureEmailIdField();

		binder = new Binder<>(Contact.class);

//		binder.forField(typeCombo).asRequired("Type can not be blank").bind("type");
		binder.forField(mobileNoField).asRequired("Mobile No can not be blank").bind("mobileNo");
		binder.forField(whatsappNoField).asRequired("WhatsApp No can not be blank").bind("whatsappNo");
		binder.forField(emailIdField).asRequired("Email No can not be blank").bind("emailId");

		saveButton = new Button("OK", VaadinIcon.CHECK.create());
		cancelButton = new Button("Cancel", VaadinIcon.CLOSE.create());

		FlexLayout flex = new FlexLayout();
		configureFlex(flex);
		flex.add(nameField, mobileNoField, whatsappNoField, emailIdField);

		HorizontalLayout actionBar = buildActionBar();
		actionBar.setWidthFull();

		add(flex, actionBar);
	}

	private void configureFlex(FlexLayout flexLayout) {
		flexLayout.setFlexWrap(FlexWrap.WRAP);
		flexLayout.getStyle().set("gap", "16px");
	}

	private void configureNameField() {
		nameField.setWidth("150px");
		nameField.setReadOnly(true);
	}

	private void configureMobileNoField() {
		mobileNoField.setWidth("110px");
	}

	private void configureWhatsappNoField() {
		whatsappNoField.setWidth("110px");
	}

	private void configureEmailIdField() {
		emailIdField.setWidth("220px");
	}

	public void setContact(Contact contact) {
		binder.setBean(contact);
		nameField.clear();
		nameField.setLabel(contact.getType().toString());
		if (contact.getName() != null) {
			nameField.setValue(contact.getName());
		}
	}

	private HorizontalLayout buildActionBar() {
		HorizontalLayout root = new HorizontalLayout();

		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton.addClickListener(e -> {
			if (binder.validate().isOk()) {
				fireEvent(new SaveEvent(this, binder.getBean()));
			}
		});

		cancelButton.addClickListener(e -> {
			fireEvent(new CancelEvent(this, binder.getBean()));
		});

		root.add(saveButton, cancelButton);

		return root;
	}

	public static abstract class ContactEvent extends ComponentEvent<ContactEditor> {
		private Contact contact;

		protected ContactEvent(ContactEditor source, Contact contact) {

			super(source, false);
			this.contact = contact;
		}

		public Contact getContact() {
			return contact;
		}
	}

	public static class SaveEvent extends ContactEvent {
		SaveEvent(ContactEditor source, Contact contact) {
			super(source, contact);
		}
	}

	public static class CancelEvent extends ContactEvent {
		CancelEvent(ContactEditor source, Contact contact) {
			super(source, contact);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {

		return getEventBus().addListener(eventType, listener);
	}

	@Override
	public void setEditingEnabled(boolean enabled) {
//		typeCombo.setReadOnly(!enabled);
		mobileNoField.setReadOnly(!enabled);
		whatsappNoField.setReadOnly(!enabled);
		emailIdField.setReadOnly(!enabled);

		saveButton.setVisible(enabled);
		cancelButton.setVisible(enabled);
	}

}