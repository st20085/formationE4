package com.sogeti.rental.ui.views;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;

/**
 * La classe <b>RentalProvider</b>.<br>
 */
public class RentalProvider extends LabelProvider implements ITreeContentProvider, RentalUIConstants {

	ImageRegistry localImageRegistry;

	/**
	 * Constructor
	 * 
	 */
	public RentalProvider(ImageRegistry localImageRegistry) {
		this.localImageRegistry = localImageRegistry;
	}

	public String getText(Object element) {
		if (element instanceof RentalAgency) {
			RentalAgency rentalAgency = (RentalAgency) element;
			return rentalAgency.getName();
		}
		if (element instanceof Customer) {
			Customer customer = (Customer) element;
			return customer.getDisplayName();
		}
		if (element instanceof Rental) {
			Rental rental = (Rental) element;
			return rental.getRentedObject().getName() + " [" + rental.getStartDate() + " -> " + rental.getEndDate()
					+ "] loué à " + rental.getCustomer().getDisplayName();
			// return rental.toString();
		}
		if (element instanceof RentalObject) {
			RentalObject rentalObject = (RentalObject) element;
			return rentalObject.getName();
		}

		return super.getText(element);
	}

	/*
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	/*
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		List<RentalAgency> rentalAgencyList = (List<RentalAgency>) inputElement;
		return rentalAgencyList.toArray();
	}

	/*
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof RentalAgency) {
			RentalAgency rentalAgency = (RentalAgency) parentElement;
			// return rentalAgency.getCustomers().toArray();

			Node customers = new Node(CUSTOMERS, rentalAgency);

			Node rentals = new Node(RENTALS, rentalAgency);

			Node objectRentals = new Node(OBJECT_RENTALS, rentalAgency);

			return new Object[] { customers, rentals, objectRentals };
		}
		if (parentElement instanceof Node) {
			Node node = (Node) parentElement;
			Object[] childs = node.getElements();
			return childs;
		}
		return null;
	}

	/*
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.
	 * Object)
	 */
	@Override
	public Object getParent(Object element) {
		return null;
	}

	/*
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof RentalAgency) {
			RentalAgency rentalAgency = (RentalAgency) element;
			return !rentalAgency.getCustomers().isEmpty();
		}
		if (element instanceof Node) {
			Node node = (Node) element;
			return node.hasChildren();
		}
		return false;
	}

	/**
	 * 
	 * @author clagarde
	 *
	 */
	public class Node {
		/*
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			// result = prime * result + getOuterType().hashCode();
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((rentalAgency == null) ? 0 : rentalAgency.hashCode());
			return result;
		}

		/*
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			// if (!getOuterType().equals(other.getOuterType()))
			// return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (rentalAgency == null) {
				if (other.rentalAgency != null)
					return false;
			} else if (!rentalAgency.equals(other.rentalAgency))
				return false;
			return true;
		}

		String name;
		RentalAgency rentalAgency;

		/**
		 * Constructor
		 * 
		 * @param name
		 */
		public Node(String name, RentalAgency rentalAgency) {
			super();
			this.name = name;
			this.rentalAgency = rentalAgency;
		}

		public Object[] getElements() {
			switch (name) {
			case CUSTOMERS:
				return rentalAgency.getCustomers().toArray();
			case RENTALS:
				return rentalAgency.getRentals().toArray();
			case OBJECT_RENTALS:
				return rentalAgency.getObjectsToRent().toArray();
			}
			return new Object[0];
		}

		public boolean hasChildren() {
			return getElements().length != 0;
		}

		@Override
		public String toString() {
			// boolean rentalCount =
			// RentalUIActivator.getDefault().getPreferenceStore().getBoolean(PREF_RENTAL_COUNT);
			// int count = getElements().length;
			// if (rentalCount)
			// return name+" ["+count+"]";
			return name;
		}

		// /**
		// * @return
		// */
		// public Color getForeground()
		// {
		// switch(name)
		// {
		// case CUSTOMERS:
		// return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED);
		// case RENTALS:
		// return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
		// case OBJECT_RENTALS:
		// return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE);
		// }
		// return null;
		// }
		//
		/**
		 * @return
		 */
		public Image getImage() {
			switch (name) {
			case CUSTOMERS:
				return localImageRegistry.get(ICON_CUSTOMERS);
			case RENTALS:
				return localImageRegistry.get(ICON_RENTALS);
			case OBJECT_RENTALS:
				return localImageRegistry.get(ICON_RENTAL_OBJECTS);
			}
			return null;
		}
		//
		// private RentalProvider getOuterType()
		// {
		// return RentalProvider.this;
		// }

	}

	/*
	 * @see
	 * org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	// @Override
	// public Color getForeground(Object element)
	// {
	// String paletteName =
	// RentalUIActivator.getDefault().getPreferenceStore().getString(PREF_PALETTE);
	// Palette palette =
	// RentalUIActivator.getDefault().getPaletteManager().get(paletteName);
	//
	// return palette.getColorProvider().getForeground(element);
	//
	//
	//// if (element instanceof Node)
	//// {
	//// Node node = (Node) element;
	//// return node.getForeground();
	//// }
	//// if (element instanceof Customer)
	//// {
	//// String textColor =
	// RentalUIActivator.getDefault().getPreferenceStore().getString(PREF_CUSTOMERS_COLOR);
	//// return getAColor(textColor);
	////// return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	//// }
	//// if (element instanceof Rental)
	//// {
	//// String textColor =
	// RentalUIActivator.getDefault().getPreferenceStore().getString(PREF_RENTAL_COLOR);
	//// return getAColor(textColor);
	////// return Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
	//// }
	//// if (element instanceof RentalObject)
	//// {
	//// String textColor =
	// RentalUIActivator.getDefault().getPreferenceStore().getString(PREF_OBJECTS_COLOR);
	//// return getAColor(textColor);
	////// return Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
	//// }
	//// return null;
	// }
	//
	// private Color getAColor(String rgbKey)
	// {
	// ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
	//
	// Color color = colorRegistry.get(rgbKey);
	// if (color == null)
	// {
	// colorRegistry.put(rgbKey, StringConverter.asRGB(rgbKey));
	// color = colorRegistry.get(rgbKey);
	// }
	//
	// return color;
	// }
	//
	// /*
	// * @see
	// org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	// */
	// @Override
	// public Color getBackground(Object element)
	// {
	// return null;
	// }
	//
	/*
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof RentalAgency) {
			return localImageRegistry.get(ICON_AGENCY);
		}

		if (element instanceof Node) {
			Node node = (Node) element;
			return node.getImage();
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// // if (element instanceof Customer)
		// // {
		// // return
		// RentalUIActivator.getDefault().getImageRegistry().get(ICON_CUSTOMERS);
		// // }
		// // if (element instanceof Rental)
		// // {
		// // return
		// RentalUIActivator.getDefault().getImageRegistry().get(ICON_RENTALS);
		// // }
		// // if (element instanceof RentalObject)
		// // {
		// // return
		// RentalUIActivator.getDefault().getImageRegistry().get(ICON_RENTAL_OBJECTS);
		// // }
		return super.getImage(element);
	}

}
