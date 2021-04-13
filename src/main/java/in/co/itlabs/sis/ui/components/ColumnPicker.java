package in.co.itlabs.sis.ui.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dnd.GridDragEndEvent;
import com.vaadin.flow.component.grid.dnd.GridDragStartEvent;
import com.vaadin.flow.component.grid.dnd.GridDropEvent;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

public class ColumnPicker extends HorizontalLayout {

	private Grid<String> availableItemsGrid = new Grid<>(String.class);
	private Grid<String> selectedItemsGrid = new Grid<>(String.class);

	private List<String> draggedItems;
	private Grid<String> dragSource;

	public ColumnPicker(List<String> availableColumns) {

		availableItemsGrid.setItems(availableColumns);

		availableItemsGrid.setHeightFull();
		selectedItemsGrid.setHeightFull();
		
		add(availableItemsGrid, selectedItemsGrid);

		ComponentEventListener<GridDragStartEvent<String>> dragStartListener = event -> {
			draggedItems = event.getDraggedItems();
			dragSource = event.getSource();
			availableItemsGrid.setDropMode(GridDropMode.BETWEEN);
			selectedItemsGrid.setDropMode(GridDropMode.BETWEEN);
		};

		ComponentEventListener<GridDragEndEvent<String>> dragEndListener = event -> {
			draggedItems = null;
			dragSource = null;
			availableItemsGrid.setDropMode(null);
			selectedItemsGrid.setDropMode(null);
		};

		ComponentEventListener<GridDropEvent<String>> dropListener = event -> {
			Optional<String> target = event.getDropTargetItem();

			if (target.isPresent() && draggedItems.contains(target.get())) {
				return;
			}

			// Remove the items from the source grid
			@SuppressWarnings("unchecked")
			ListDataProvider<String> sourceDataProvider = (ListDataProvider<String>) dragSource.getDataProvider();
			List<String> sourceItems = new ArrayList<>(sourceDataProvider.getItems());
			sourceItems.removeAll(draggedItems);
			dragSource.setItems(sourceItems);

			// Add dragged items to the target Grid
			Grid<String> targetGrid = event.getSource();
			@SuppressWarnings("unchecked")
			ListDataProvider<String> targetDataProvider = (ListDataProvider<String>) targetGrid.getDataProvider();
			List<String> targetItems = new ArrayList<>(targetDataProvider.getItems());

			int index = target.map(
					person -> targetItems.indexOf(person) + (event.getDropLocation() == GridDropLocation.BELOW ? 1 : 0))
					.orElse(0);

			targetItems.addAll(index, draggedItems);
			targetGrid.setItems(targetItems);
		};

		availableItemsGrid.removeAllColumns();
		availableItemsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
		availableItemsGrid.addDropListener(dropListener);
		availableItemsGrid.addDragStartListener(dragStartListener);
		availableItemsGrid.addDragEndListener(dragEndListener);
		availableItemsGrid.setRowsDraggable(true);
		availableItemsGrid.addColumn(s -> {
			return s;
		}).setHeader("Available columns");

		selectedItemsGrid.removeAllColumns();
		selectedItemsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
		selectedItemsGrid.addDropListener(dropListener);
		selectedItemsGrid.addDragStartListener(dragStartListener);
		selectedItemsGrid.addDragEndListener(dragEndListener);
		selectedItemsGrid.setRowsDraggable(true);
		selectedItemsGrid.addColumn(s -> {
			return s;
		}).setHeader("Selected columns");
	}

	public List<String> getSelectedColumns() {
		return selectedItemsGrid.getListDataView().getItems().collect(Collectors.toList());
	}
}
