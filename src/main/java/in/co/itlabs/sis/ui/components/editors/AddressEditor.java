package in.co.itlabs.sis.ui.components.editors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import in.co.itlabs.sis.business.entities.Address;
import in.co.itlabs.sis.business.entities.Address.Type;
import in.co.itlabs.sis.business.entities.District;
import in.co.itlabs.sis.business.entities.State;
import in.co.itlabs.sis.business.services.AddressService;

public class AddressEditor extends VerticalLayout implements Editor {

	private ComboBox<Type> typeCombo;
	private ComboBox<State> stateCombo;
	private ComboBox<District> districtCombo;
	private TextArea addressField;
	private TextField pinCodeField;

	private Button saveButton;
	private Button cancelButton;

	private Binder<Address> binder;

	private AddressService addressService;

	public AddressEditor(AddressService addressService) {
		this.addressService = addressService;

		typeCombo = new ComboBox<Address.Type>("Address type");
		configureTypeCombo();

		stateCombo = new ComboBox<State>();
		configureStateCombo();

		districtCombo = new ComboBox<District>();
		configureDistrictCombo();

		addressField = new TextArea("Address");
		configureAddressField();

		pinCodeField = new TextField("Pincode");
		configurePincodeField();

		binder = new Binder<>(Address.class);

		binder.forField(typeCombo).asRequired("Type can not be blank").bind("type");
		binder.forField(stateCombo).asRequired("State can not be blank").bind("state");
		binder.forField(districtCombo).asRequired("District can not be blank").bind("district");
		binder.forField(addressField).asRequired("Address can not be blank").bind("address");
		binder.forField(pinCodeField).asRequired("Pin code can not be blank").bind("pinCode");

		saveButton = new Button("OK", VaadinIcon.CHECK.create());
		cancelButton = new Button("Cancel", VaadinIcon.CLOSE.create());

		HorizontalLayout actionBar = buildActionBar();
		actionBar.setWidthFull();

		add(typeCombo, addressField, districtCombo, stateCombo, pinCodeField, actionBar);
	}

	private void configureTypeCombo() {
		typeCombo.setWidthFull();
		typeCombo.setReadOnly(true);
		typeCombo.setItems(Address.Type.Permanent, Address.Type.Correspondence, Address.Type.Local_Guardian);
	}

	private void configureStateCombo() {
		stateCombo.setWidthFull();
		stateCombo.setItemLabelGenerator(state -> {
			return state.getName();
		});
		stateCombo.setItems(addressService.getAllStates());
		stateCombo.addValueChangeListener(e -> {
			districtCombo.clear();
			if (e.getValue() != null) {
				districtCombo.setItems(addressService.getDistricts(e.getValue().getId()));
			}
		});
	}

	private void configureDistrictCombo() {
		districtCombo.setWidthFull();
		districtCombo.setItemLabelGenerator(district -> {
			return district.getName();
		});
	}

	private void configureAddressField() {
		addressField.setWidthFull();
		addressField.getElement().getStyle().set("minHeight", "120px");
	}

	private void configurePincodeField() {
		// TODO Auto-generated method stub
		pinCodeField.setWidthFull();
	}

	public void setAddress(Address address) {
		binder.setBean(address);
	}

	private HorizontalLayout buildActionBar() {
		HorizontalLayout root = new HorizontalLayout();

		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton.addClickListener(e -> {
			if (binder.validate().isOk()) {

				binder.getBean().setDistrictId(binder.getBean().getDistrict().getId());

				fireEvent(new SaveEvent(this, binder.getBean()));
			}
		});

		cancelButton.addClickListener(e -> {
			fireEvent(new CancelEvent(this, binder.getBean()));
		});

		Span blank = new Span();

		root.add(saveButton, blank, cancelButton);
		root.expand(blank);

		return root;
	}

	public static abstract class AddressEvent extends ComponentEvent<AddressEditor> {
		private Address address;

		protected AddressEvent(AddressEditor source, Address address) {

			super(source, false);
			this.address = address;
		}

		public Address getAddress() {
			return address;
		}
	}

	public static class SaveEvent extends AddressEvent {
		SaveEvent(AddressEditor source, Address address) {
			super(source, address);
		}
	}

	public static class CancelEvent extends AddressEvent {
		CancelEvent(AddressEditor source, Address address) {
			super(source, address);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {

		return getEventBus().addListener(eventType, listener);
	}

	@Override
	public void setEditingEnabled(boolean enabled) {
//		typeCombo.setReadOnly(!enabled);
		stateCombo.setReadOnly(!enabled);
		districtCombo.setReadOnly(!enabled);
		addressField.setReadOnly(!enabled);
		pinCodeField.setReadOnly(!enabled);

		saveButton.setVisible(enabled);
		cancelButton.setVisible(enabled);
	}

}