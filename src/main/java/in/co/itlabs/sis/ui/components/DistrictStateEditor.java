package in.co.itlabs.sis.ui.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import in.co.itlabs.sis.business.entities.Address;
import in.co.itlabs.sis.business.entities.District;
import in.co.itlabs.sis.business.entities.State;
import in.co.itlabs.sis.business.services.AddressService;

public class DistrictStateEditor extends VerticalLayout {

	private ComboBox<State> stateCombo;
	private ComboBox<District> districtCombo;

	private Binder<Address> binder;

	private AddressService addressService;

	public DistrictStateEditor(AddressService addressService) {

		this.addressService = addressService;

		setPadding(false);

		stateCombo = new ComboBox<State>("State");
		configureStateCombo();

		districtCombo = new ComboBox<District>("District");
		configureDistrictCombo();

		binder = new Binder<>(Address.class);

		binder.forField(stateCombo).asRequired("State can not be blank").bind("state");
		binder.forField(districtCombo).asRequired("District can not be blank").bind("district");

		var actionBar = buildActionBar();

		add(stateCombo, districtCombo, actionBar);

	}

	private void configureStateCombo() {
		stateCombo.setWidthFull();
		stateCombo.setItemLabelGenerator(state -> {
			return state.getName();
		});
		stateCombo.setItems(addressService.getAllStates());
		stateCombo.addValueChangeListener(event -> {
			districtCombo.clear();
			if (event.getValue() != null) {
				districtCombo.setItems(addressService.getDistricts(event.getValue().getId()));
			}
		});
	}

	private void configureDistrictCombo() {
		districtCombo.setWidthFull();
		districtCombo.setItemLabelGenerator(district -> {
			return district.getName();
		});
	}

	public void setAddress(Address address) {
		binder.setBean(address);
		stateCombo.focus();
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

	public static abstract class AddressEvent extends ComponentEvent<DistrictStateEditor> {
		private Address address;

		protected AddressEvent(DistrictStateEditor source, Address address) {

			super(source, false);
			this.address = address;
		}

		public Address getAddress() {
			return address;
		}
	}

	public static class SaveEvent extends AddressEvent {
		SaveEvent(DistrictStateEditor source, Address address) {
			super(source, address);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {

		return getEventBus().addListener(eventType, listener);
	}
}
