 
package com.sogeti.rental.ui.addons;

import javax.annotation.PostConstruct;


import org.eclipse.e4.core.contexts.IEclipseContext;

import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.core.helpers.RentalAgencyGenerator;



public class RentalUIAddons {

	@PostConstruct
	public void init(IEclipseContext context) {
		context.set(RentalAgency.class, RentalAgencyGenerator.createSampleAgency());
	}

}
