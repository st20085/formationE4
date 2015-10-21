
package com.sogeti.rental.ui.addons;



import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.opcoach.e4.preferences.ScopedPreferenceStore;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.core.helpers.RentalAgencyGenerator;
import com.sogeti.rental.ui.views.RentalUIConstants;

public class RentalUIAddons implements RentalUIConstants {

	@PostConstruct
	public void init(IEclipseContext context) {
		context.set(RentalAgency.class, RentalAgencyGenerator.createSampleAgency());
		context.set(RENTAL_UI_IMGREGISTRY, getLocalImageRegistry());
		
		context.set(IPreferenceStore.class, new ScopedPreferenceStore(InstanceScope.INSTANCE, "com.sogeti.rental.ui"));
		
	}

	ImageRegistry getLocalImageRegistry() {
		ImageRegistry reg = new ImageRegistry();

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		reg.put(ICON_CUSTOMERS, ImageDescriptor.createFromURL(bundle.getEntry(ICON_CUSTOMERS)));
		reg.put(ICON_RENTAL_OBJECTS, ImageDescriptor.createFromURL(bundle.getEntry(ICON_RENTAL_OBJECTS)));
		reg.put(ICON_RENTALS, ImageDescriptor.createFromURL(bundle.getEntry(ICON_RENTALS)));
		reg.put(ICON_AGENCY, ImageDescriptor.createFromURL(bundle.getEntry(ICON_AGENCY)));

		return reg;
	}
}
